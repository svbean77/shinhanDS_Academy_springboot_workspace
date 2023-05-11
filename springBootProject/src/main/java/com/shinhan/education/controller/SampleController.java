package com.shinhan.education.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.education.vo.CarVO;

@RestController // 현재 jsp, thymeleaf 사용할 것이 아니기 때문에 임시로 restController 사용
public class SampleController {
//	@RequestMapping(value = "/sample1", method = RequestMethod.GET)
	@GetMapping("/sample1")
	public String test1 () {
		return "sample1: SpringBoot 연습!";
	}
	@GetMapping("/sample2")
	public String test2 () {
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
}
