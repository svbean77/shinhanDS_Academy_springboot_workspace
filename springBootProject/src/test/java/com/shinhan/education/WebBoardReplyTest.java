package com.shinhan.education;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.education.repository.WebBoardRepository;
import com.shinhan.education.repository.WebReplyRepository;
import com.shinhan.education.vo3.WebBoard;
import com.shinhan.education.vo3.WebReply;

import lombok.extern.java.Log;

@SpringBootTest
@Log
public class WebBoardReplyTest {
	@Autowired
	WebBoardRepository brepo;
	@Autowired
	WebReplyRepository rrepo;
	
//	@Test
	// board에 게시글 100건 insert
	void test1() {
		IntStream.range(351, 450).forEach(idx -> {
			WebBoard board = WebBoard.builder().title("webBoard" + idx).writer("user" + (idx / 100)).content("SpringBoot Project" + idx).build();
			
			brepo.save(board);
		});
	}
	
//	@Test
	// 5개의 board에 댓글 10개 insert
	void test2() {
		Long[] bnoList = {300L, 333L, 360L, 377L, 380L};
		
		Arrays.stream(bnoList).forEach(bno -> {
			brepo.findById(bno).ifPresent(board -> {
				IntStream.range(20, 30).forEach(idx -> {
					WebReply reply = WebReply.builder().replyText("날씨가 덥다~ 기온 " + idx + "도").replyer("댓글작성자" + idx).board(board).build();
					
					rrepo.save(reply);
				});
			});
		});
	}
	
//	@Test
	// board로 전체 조회
	void test3() {
		brepo.findAll().forEach(board -> {
			log.info(board.toString());
		});
	}
	
	@Test
	// 특정 board의 댓글 조회
	void test4() {
		brepo.findById(141L).ifPresent(board -> {
			// 양방향이기 때문에 board에서 시작한 reply, reply에서 시작한 board를 통한 reply 둘 다 구할 수 있음
			// getReplies, findByBoard 사용해서 각각 구하기 가능
			log.info("board 입장에서 구한 reply");
			List<WebReply> replyList = board.getReplies();
			for(WebReply reply: replyList) {
				log.info(reply.toString());
			}
			
			log.info("reply 입장에서 구함");
			rrepo.findByBoard(board).forEach(reply -> log.info(reply.toString())); 
		});
	}
	
	@Test
	// board별 댓글 수
	void test5() {
		
	}
}
