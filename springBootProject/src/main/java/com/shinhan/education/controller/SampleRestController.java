package com.shinhan.education.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.BooleanBuilder;
import com.shinhan.education.repository.BoardRepository;
import com.shinhan.education.repository.PDSBoardRepository;
import com.shinhan.education.vo.BoardVO;
import com.shinhan.education.vo.CarVO;
import com.shinhan.education.vo.QBoardVO;

import lombok.extern.java.Log;

@RestController // 현재 jsp, thymeleaf 사용할 것이 아니기 때문에 임시로 restController 사용
@Log
public class SampleRestController {
	@Autowired
	BoardRepository brepo;
	@Autowired
	PDSBoardRepository pbrepo;

	// day055
//	@RequestMapping(value = "/sample1", method = RequestMethod.GET)
	@GetMapping("/sample1")
	public String test1() {
		return "sample1: SpringBoot 연습!";
	}

	@GetMapping("/sample2")
	public String test2() {
		return "sample2: SpringBoot 파이팅";
	}

	@GetMapping("/cartest")
	// Jackson이 JAVA 객체를 JSON으로 만들어 return
	// SpringBoot에서는 consumes, produces로 json이라는 의미를 주지 않아도 됨!
	public CarVO test3() {
		CarVO car1 = CarVO.builder().build(); // default 생성자
		CarVO car2 = CarVO.builder().model("ABC모델").build(); // 중간에 끼는 것은 setXxx
		return car2;
	}

	@GetMapping("/cartest2")
	// Jackson이 JAVA 객체를 JSON으로 만들어 return
	public List<CarVO> test4() {
		List<CarVO> carlist = new ArrayList<>();
		IntStream.rangeClosed(1, 10).forEach(idx -> {
			CarVO car = CarVO.builder().model("A" + idx).price(99 + idx).build();
			carlist.add(car);
		});

		return carlist;
	}

	// day056
	// 단위 테스트가 완료되면 그대로 가져와 프로젝트에서 사용!
	@GetMapping("/boardtest")
	public Map<String, Object> test16() {
		long rowCount = brepo.count();
		log.info(rowCount + "건");

		boolean result = brepo.existsById(100L);
		log.info(result ? "존재O" : "존재X");

		Map<String, Object> map = new HashMap<>();
		map.put("aa", rowCount + "건");
		map.put("100", result ? "존재O" : "존재X");
		map.put("200", brepo.findById(200L).orElse(null));

		return map;
	}

	// querydsl
	@GetMapping("/querydsl")
	public List<BoardVO> test24() {
		String title = "6";
		Long bno = 150L; 
		BooleanBuilder builder = new BooleanBuilder();
		QBoardVO board = QBoardVO.boardVO;

		builder.and(board.title.like("%" + title + "%"));
		builder.and(board.bno.gt(bno)); 
		builder.and(board.writer.eq("작성자6"));

		List<BoardVO> blist = (List<BoardVO>) brepo.findAll(builder);

		return blist;
	}
	
	// day057
	// 실제 환경에서는 @Commig 안해도 되나
	@GetMapping("/controllerCommit") 
	String test9() {
		int result = pbrepo.updateFile(4L, "컨트롤러에서 변경");
		return "성공: " + result + "건";
	}
}
