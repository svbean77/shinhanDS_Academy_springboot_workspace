package com.shinhan.education.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	// 모든 return 타입이 동일하기 때문에 board 만들기~return까지 하나의 함수로 묶기
	private ResponseEntity<List<WebReply>> makeReturn(Long bno, HttpStatus status) {
		WebBoard board = WebBoard.builder().bno(bno).title("temp").build(); // VO에서 title을 nonnull로 설정했기 때문에 사용하지는 않지만 채워줘야 함
		List<WebReply> replies = rrepo.findByBoardOrderByRnoDesc(board);
		
		return new ResponseEntity<List<WebReply>>(replies, status);
	}
	
	@GetMapping("/{bno}") // parameter가 아님! path의 값을 가져온 것!
	public ResponseEntity<List<WebReply>> selectAllReply(@PathVariable("bno") Long bno) {
		System.out.println("모든 댓글을 조회한다: " + bno);
		return makeReturn(bno, HttpStatus.OK);
	}
	
	@PostMapping("/{bno}")
	public ResponseEntity<List<WebReply>> insertReply(@RequestBody WebReply reply, @PathVariable("bno") Long bno) {
		WebBoard board = WebBoard.builder().bno(bno).title("temp").build(); // VO에서 title을 nonnull로 설정했기 때문에 사용하지는 않지만 채워줘야 함
		reply.setBoard(board);
		rrepo.save(reply);
		
		return makeReturn(bno, HttpStatus.CREATED);
	}
	
	@PutMapping("/{bno}")
	public ResponseEntity<List<WebReply>> updateReply(@RequestBody WebReply reply, @PathVariable("bno") Long bno) {
		WebBoard board = WebBoard.builder().bno(bno).title("temp").build(); // VO에서 title을 nonnull로 설정했기 때문에 사용하지는 않지만 채워줘야 함
		reply.setBoard(board); // board를 설정하지 않으면 bno가 null이기 때문에 없어져버림 (어제 regdate처럼)
		rrepo.save(reply);
		
		return makeReturn(bno, HttpStatus.OK);
	}
	
	@DeleteMapping("/{bno}/{rno}")
	public ResponseEntity<List<WebReply>> deleteReply(@PathVariable("bno") Long bno, @PathVariable("rno") Long rno) {
		rrepo.deleteById(rno);
		
		return makeReturn(bno, HttpStatus.OK);
	}
}
