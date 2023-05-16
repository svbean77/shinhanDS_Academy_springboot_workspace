package com.shinhan.education;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.shinhan.education.repository.FreeBoardRepository;
import com.shinhan.education.repository.FreeRepliesRepository;
import com.shinhan.education.vo.FreeBoard;
import com.shinhan.education.vo.FreeBoardReply;

import lombok.extern.java.Log;

@SpringBootTest
@Log
public class OneToManyToOneTest {
	@Autowired
	FreeBoardRepository brepo;
	@Autowired
	FreeRepliesRepository rrepo;

//	@Test
	// board에 게시글 100개 insert
	void test1() {
		// freeboard에 100건 insert
		IntStream.rangeClosed(1, 100).forEach(idx -> {
			FreeBoard board = FreeBoard.builder().title("게시글제목" + idx).writer("user" + (idx % 10)).content("게시글~~~~~~")
					.build();

			brepo.save(board);
		});
	}

	@Test
	// 리스트 사용해 n번 게시글에 댓글 10개 insert
	void test2() {
		Long[] arr = { 1L, 10L, 50L, 100L };
		Arrays.stream(arr).forEach(bno -> {
			brepo.findById(bno).ifPresent(board -> {
				IntStream.range(1, 11).forEach(idx -> {
					FreeBoardReply reply = FreeBoardReply.builder().reply("댓글이다" + idx).replyer("user" + (idx % 2))
							.board(board).build();

					rrepo.save(reply);
				});
			});
		});
	}

//	@Test
	// 전체 board 조회 (1:n 사용)
	void test3() {
		// fetch: EAGER일 때
		brepo.findAll().forEach(board -> {
			log.info(board.toString());
			// board 번호와 댓글 개수를 알고 싶다
			log.info(board.getBno() + "번 게시글: " + board.getReplies().size() + "개의 댓글");
		});

	}

//	@Test
	// 전체 댓글 조회 (n:1 사용)
	void test4() {
		rrepo.findAll().forEach(reply -> {
			log.info(reply.toString());
			// 해당 댓글의 게시글 정보를 알고 싶다
			FreeBoard board = reply.getBoard();
			log.info(board.getBno() + ": " + board.getTitle() + "(" + board.getWriter() + ")");
		});
	}

//	@Test
	// 게시글 번호가 15번인 댓글만 조회
	void test5() {
		brepo.findById(15L).ifPresent(board -> {
			log.info(board.getBno() + "번 게시글의 댓글들");
			rrepo.findByBoard(board).forEach(reply -> {
				log.info(reply.getBoard().getBno() + ": " + reply.toString());
			});
		});
	}

//	@Test
	// 게시글 번호가 n 이상인 게시글들 조회, paging 추가
	void test6() {
		Pageable paging = PageRequest.of(3, 10, Sort.Direction.DESC, "bno");

		brepo.findByBnoGreaterThan(55L, paging).forEach(board -> log
				.info(board.getBno() + " - " + board.getTitle() + ": " + board.getReplies().size() + "개 댓글"));
	}

//	@Test
	// 게시글 번호 100번, 77번을 삭제
	void test7() {
		brepo.deleteById(100L);
		brepo.findById(77L).ifPresent(board -> { // cascade.ALL이기 때문에 댓글도 같이 지워짐
			brepo.delete(board);
		});
	}
}
