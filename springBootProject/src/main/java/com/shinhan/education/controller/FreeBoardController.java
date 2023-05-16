package com.shinhan.education.controller;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shinhan.education.repository.FreeBoardRepository;
import com.shinhan.education.repository.FreeRepliesRepository;

@Controller
public class FreeBoardController {
	@Autowired
	FreeBoardRepository brepo;
	@Autowired
	FreeRepliesRepository rrepo;
	
	@GetMapping("/firstzone1")
	public void test1(Model model) {
		model.addAttribute("greeting", "하이~");
		model.addAttribute("company", "신한금융");
		// 요청 주소와 html 파일명이 같으면 return이 없어도 됨
	}
	
	@GetMapping("/firstzone2")
	public String test2(Model model) {
		model.addAttribute("greeting", "헬로~");
		model.addAttribute("company", "신한");
		return "firstzone1"; // 요청 주소와 html 파일명이 다르면 forward 하고자 하는 페이지 이름을 return 
	}
	
	@GetMapping("/aa/firstzone3")
	public void test3(Model model) {
		model.addAttribute("greeting", "폴더 안에 있어");
		model.addAttribute("company", "만들기~");
		// 경로가 폴더 안에 존재하지만 return을 하고싶지 않다면 페이지를 해당 folder 안에 만들어야 함 
	}
	
	@GetMapping("/freeboard/detail")
	// @RequestParam("bno")는 생략 가능: parameter 이름이 동일하기 때문
	public void freeboard1(@RequestParam("bno") Long bno, Model model) {
		brepo.findById(bno).ifPresent(board -> {
			model.addAttribute("board", board);
		});
	}
	
	@GetMapping("/freeboard/selectAll")
	public String freeboard2(Model dataScope) {
		dataScope.addAttribute("boardList", brepo.findAll());
		
		return "freeboard/list";
	}
	
	@GetMapping("/freeboard/eltest")
	public void freeboard3(Model dataScope, HttpSession session) {
		dataScope.addAttribute("boardList", brepo.findAll());
		session.setAttribute("userName", "hong길동");
		
		// 오후 수업: expression utility object
		dataScope.addAttribute("now", new Date());
		dataScope.addAttribute("price", 123456789);
		dataScope.addAttribute("title", "This is a just sample");
		dataScope.addAttribute("options", Arrays.asList("apple", "banana", "peach"));
	}
} 
