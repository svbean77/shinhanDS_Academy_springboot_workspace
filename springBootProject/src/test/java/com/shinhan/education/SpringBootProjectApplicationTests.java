package com.shinhan.education;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.education.repository.BoardRepository;
import com.shinhan.education.vo.BoardVO;
import com.shinhan.education.vo.CarVO;

// JUNIT으로 단위테스트
@SpringBootTest
class SpringBootProjectApplicationTests {
	Logger logger = LoggerFactory.getLogger(SpringBootProjectApplicationTests.class);
	@Autowired
	BoardRepository brepo; // JPA가 만들어준 구현체가 들어옴
	
	// day055 CarVO, BoardVO
//	@Test
	// 그냥 테스트
	void test1() {
		CarVO car1 = new CarVO();
		car1.setModel("ABC모델");
		car1.setPrice(1000);
		logger.info("car1: " + car1.toString());
		
		CarVO car2 = new CarVO();
		car2.setModel("ABC모델");
		car2.setPrice(1000);
		logger.info("car2: " + car2.toString());
		logger.info("car1.equals(car2)? " + car1.equals(car2));
	}
	
//	@Test
	// builder 써보기
	void test2() {
		CarVO car1 = CarVO.builder().model("CEF모델").price(100).build();
		logger.info("car1: " + car1.toString());
	}

//	@Test
	// repo.save 저장: insert into boards
	void test3() {
		String[] words = {"ApplE", "BanANa", "OrangE", "PeaHc"};
		String[] writers = {"익명1", "홍길동", "김철수", "익명3", "김영희", "익명7"};
		String[] contents = {"맛있어", "그저그래", "음", "좋아", "별로야", "먹고싶다", "비싸", "어제먹음"};
		IntStream.rangeClosed(1, 100).forEach(idx -> {
			BoardVO board = BoardVO.builder().title("제목: " + words[idx % 4]).content("내용은 " + contents[idx % 8]).writer(writers[idx % 6]).build();
			brepo.save(board); // save 함수에 의해 insert
		});
	}
	
//	@Test
	// repo.findAll: select * from 테이블
	void test4() {
		brepo.findAll().forEach(board -> {
			logger.info(board.toString());
		}); 
	}
	
//	@Test
	// repo.selectById: select * from 테이블 where id=?
	void test5() {
		logger.info("101번은 존재하나?");
		brepo.findById(101L).ifPresent(board -> {
			logger.info("101번: " + board.toString());
		});
		logger.info("10번은 존재하나?");
		BoardVO board = brepo.findById(10L).orElse(null);
		logger.info("10번: " + board);
	}
	
//	@Test
	// repo.save 수정: update boards set ? 
	void test6() {
		BoardVO board = brepo.findById(101L).orElse(null);
		if(board != null) {
			board.setContent("내용을 수정했습니다");
			board.setTitle("제목을 수정했습니다");
			board.setWriter("작성자를 수정했습니다");
			brepo.save(board);
			logger.info("수정 완료!");
		}
	}

	// sql문 사용 -> 조건을 추가
//	@Test
	// findByTitle
	void test7() {
		logger.info("Title 조회");
		brepo.findByTitle("제목3").forEach(board -> {
			logger.info(board.toString());
		});
	}
	
//	@Test
	// findByContent
	void test8() {
		logger.info("Content 조회");
		List<BoardVO> blist = brepo.findByContent("내용은 5");
		blist.forEach(board -> {
			logger.info(board.toString());
		});
	}
//	@Test
	// findByWriter
	void test9() {
		logger.info("Writer 조회");
		List<BoardVO> blist = brepo.findByWriter("작성자7");
		blist.forEach(board -> {
			logger.info(board.toString());
		});
	}
	
//	@Test
	// findByWriterAndTitle
	void test10() {
		logger.info("Writer, Title 조회");
		brepo.findByWriterAndTitle("작성자7", "제목3").forEach(board -> {
			logger.info(board.toString());
		});
	}
	
//	@Test
	// findByTitleContaining: where title like '%?%'
	void test11() {
		brepo.findByTitleContaining("목1").forEach(board -> {
			logger.info(board.toString());
		});
	}
	
//	@Test
	// findByTitleStartingWith: where title like '?%'
	void test12() {
		brepo.findByTitleStartingWith("제목2").forEach(board -> {
			logger.info(board.toString());
		});
	}
	
//	@Test
	// findByTitleEndingWith: where title like '%?'
	void test13() {
		brepo.findByTitleEndingWith("7").forEach(board -> {
			logger.info(board.toString());
		});
	}
	
//	@Test
	void tst14() {
		// findByTitleContainingAndBnoGreaterThan: where title like '%?%' and bno > ?
		brepo.findByTitleContainingAndBnoGreaterThan("3", 200L).forEach(board -> {
			logger.info(board.toString());
		});
	}
	
	@Test
	void test15() {
		brepo.findByTitleIgnoreCaseContainingAndBnoBetweenOrderByWriterDesc("Apple", 430L, 500L).forEach(board -> logger.info(board.toString()));
	} 
}
