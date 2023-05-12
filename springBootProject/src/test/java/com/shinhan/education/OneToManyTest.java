package com.shinhan.education;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javax.persistence.CascadeType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.education.repository.PDSBoardRepository;
import com.shinhan.education.repository.PDSFileRepository;
import com.shinhan.education.vo.PDSBoard;
import com.shinhan.education.vo.PDSFile;

import lombok.extern.java.Log;

@SpringBootTest
@Log
public class OneToManyTest {
	@Autowired
	PDSFileRepository frepo;
	@Autowired
	PDSBoardRepository brepo;
	
//	@Test
	// PDSBoard에 insert 
	void insertAll() {
		IntStream.range(20, 30).forEach(i -> {
			List<PDSFile> files = new ArrayList<>();
			IntStream.range(1, 6).forEach(idx -> {
				PDSFile file = PDSFile.builder().pdsfilename("firstzone-" + idx + ".java").build();
				files.add(file); // PDSFile을 save하는 것이 아님! PDSBoard에 넣으면 알아서 PDSFile에 들어감!
			});
			
			PDSBoard board = PDSBoard.builder().pname("스프링 수업" + i).pwriter("이영희").files2(files).build();
			brepo.save(board); // PDSBoard만 저장 -> 알아서 PDSFile에 저장됨
		});
	}
	
//	@Test
	// 모든 보드 조회
	void selectAllBoard() {
		brepo.findAll().forEach(board -> {
			log.info(board.toString());
			log.info(board.getPname() + ": " + board.getPwriter() + "(첨부파일 " + board.getFiles2().size() + "건)");
		});
	}
	
//	@Test
	// 파일 삭제
	void deleteFile() {
		Long[] files = {751L, 765L, 777L, 800L};
		
		Arrays.stream(files).forEach(bno -> {
			frepo.deleteById(bno);
		});
	}
	
//	@Test
	// delete cascade
	void deleteByBoard() {
		Long bno = 804L;
		brepo.deleteById(bno);
	}
	
//	@Test
	// 특정 번호의 파일 정보 가져오기
	void fileInfo() {
		// fetch = LAZY일 때 이 방법으로 실행 (P가 올 때 C가 안와도 됨)
		brepo.getFilesInfo(714).forEach(arr -> log.info(Arrays.toString(arr)));
		
		// fetch = EAGER일 때 이 방법으로 실행 (P가 올 때 C가 와야함)
		PDSBoard board = brepo.findById(714L).orElse(null);
		if(board != null) {
			log.info(board.getPname());
			log.info(board.getPwriter());
			log.info(board.getFiles2().toString());
		}
	}
	
//	@Test
	// board별 file 건수
	void getFileCount() {
		// EAGER인 경우
		brepo.findAll().forEach(board -> {
			System.out.println(board.getPname() + ": " + board.getFiles2().size() + "건");
		});
		
		// LAZY인 경우
		brepo.getFilesInfo2().forEach(arr -> log.info(Arrays.toString(arr)));
	}
}
