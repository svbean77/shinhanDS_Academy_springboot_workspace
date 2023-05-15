package com.shinhan.education;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.BooleanBuilder;
import com.shinhan.education.repository.BoardRepository;
import com.shinhan.education.vo.BoardVO;
import com.shinhan.education.vo.CarVO;
import com.shinhan.education.vo.QBoardVO;

import lombok.extern.java.Log;

// JUNIT으로 단위테스트
@SpringBootTest
@Log // lombok에 log가 있기 때문에 선언하지 않아도 됨
class BoardTest {
	Logger logger = LoggerFactory.getLogger(BoardTest.class);
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
			BoardVO board = BoardVO.builder().title("제목은: " + words[idx % 4]).content("내용은 " + contents[idx % 8]).writer(writers[idx % 6]).build();
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
	// findByTitleContainingAndBnoGreaterThan: where title like '%?%' and bno > ?
	void tst14() {
		brepo.findByTitleContainingAndBnoGreaterThan("3", 200L).forEach(board -> {
			logger.info(board.toString());
		});
	}
	
//	@Test
	// 기깔난 멋진거 만들어봐
	void test15() {
		brepo.findByTitleIgnoreCaseContainingAndBnoBetweenOrderByWriterDesc("Apple", 430L, 500L).forEach(board -> logger.info(board.toString()));
	} 
	
	// day056
//	@Test
	// select count(*) 
	void test16() {
		long rowCount = brepo.count();
		log.info(rowCount + "건"); // lombok의 log 사용
	}
	
//	@Test
	// where bno exist 
	void test17() {
		boolean result = brepo.existsById(100L);
		log.info(result ? "존재O" : "존재X");
		result = brepo.existsById(200L);
		log.info(result ? "존재O" : "존재X");
	}
	
//	@Test
	// where title like ? order by title desc
	void test18() {
		brepo.findByTitleContainingOrderByTitleDesc("제목은").forEach(board -> logger.info(board.toString()));
	}
	
//	@Test
	// paging
	void test19() {
		// 첫 번째 페이지는 0!!!!!
		Pageable paging = PageRequest.of(24, 10); // 몇 페이지를 볼 것인가, 한 페이지에 개수가 몇 개인가 (한 페이지는 10개씩 있고 24페이지를 보겠다)
		brepo.findByTitleContainingOrderByTitle("제목", paging).forEach(board -> logger.info(board.toString()));
	}
	
//	@Test
	// paging + sort (descending)
	void test20() {
		Sort sort = Sort.by("bno").descending();
		Pageable paging = PageRequest.of(4, 10, sort);
		brepo.findByTitleContaining("제목", paging).forEach(board -> logger.info(board.toString()));
	}
	
//	@Test
	// paging + sort (direction)
	void test21() {
		Sort sort = Sort.by(Sort.Direction.DESC, new String[] {"writer", "bno"}); // order by writer desc bno desc
		Pageable paging = PageRequest.of(24, 10, sort);
		brepo.findByTitleContaining("제목", paging).forEach(board -> logger.info(board.toString()));
	}
	
//	@Test
	// page 타입으로 리턴
	void test22() {
		Sort sort = Sort.by(Sort.Direction.DESC, new String[] {"writer", "bno"});
		Pageable paging = PageRequest.of(24, 10, sort);
		Page<BoardVO> result = brepo.findByBnoGreaterThan(300L, paging); // 페이지를 정보를 얻은 후
		
		log.info("페이지 당 건수: " + result.getSize());
		log.info("페이지 총 수: " + result.getTotalPages());
		log.info("전체 건수: " + result.getTotalElements());
		log.info("다음 페이지 정보: " + result.nextPageable());
		
		List<BoardVO> blist = result.getContent(); // 해당 페이지에 해당하는 데이터만 가져옴
		blist.forEach(board -> log.info(board.toString()));
	}
	
	// JPQL
//	@Test
	void test23() {
		brepo.findByTitle2("ApplE", "맛있어").forEach(board -> log.info(board.toString()));
		brepo.findByTitle3("ApplE", "맛있어").forEach(board -> log.info(board.toString()));
		brepo.findByTitle4("ApplE", "맛있어").forEach(board -> log.info(board.toString()));
		brepo.findByTitle5("ApplE", "맛있어").forEach(obj -> log.info(Arrays.toString(obj)));
		log.info("nativeQuery");
		brepo.findByTitle6("ApplE", "맛있어").forEach(board -> log.info(board.toString()));
	}
	
	// querydsl
	@Test
	void test24() {
		// 원래 있던 조건에 조건을 주고자 한다 -> 모두 and로 연결됨
		String title = "6"; // and title like '%6%'
		Long bno = 150L; // and bno > 150
		BooleanBuilder builder = new BooleanBuilder();
		QBoardVO board = QBoardVO.boardVO;
		
		builder.and(board.title.like("%" + title + "%"));
		builder.and(board.bno.gt(bno)); // greater than
		builder.and(board.writer.eq("작성자6"));
		
		log.info("builder: " + builder.toString());
		
		// findAll: CrudRepository에서 지원
		// findAll(predicate): QuerydslPredicateExecutor에서 지원
		List<BoardVO> blist = (List<BoardVO>) brepo.findAll(builder); // builder에서 정한 조건에 맞는 값만 불러옴
		blist.forEach(b -> log.info(b.toString()));
	}
}
