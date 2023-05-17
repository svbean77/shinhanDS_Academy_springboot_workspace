package com.shinhan.education.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.education.repository.WebBoardRepository;
import com.shinhan.education.repository.WebReplyRepository;
import com.shinhan.education.vo3.WebBoard;
import com.shinhan.education.vo3.WebReply;

@RestController
@RequestMapping("/replies")
public class WebReplyController {
	@Autowired
	WebReplyRepository rrepo;
	
	@GetMapping("/{bno}") // parameter가 아님! path의 값을 가져온 것!
	public List<WebReply> selectAllReply(@PathVariable("bno") Long bno) {
		System.out.println("모든 댓글을 조회한다: " + bno);
		WebBoard board = new WebBoard();
		board.setBno(bno);
		List<WebReply> replies = rrepo.findByBoard(board); // FK를 이용해 검색하는 것이기 때문에 board의 PK만 있으면 됨
		
		return replies;
	}
}
