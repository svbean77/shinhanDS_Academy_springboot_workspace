package com.shinhan.education;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.shinhan.education.repository.PDSBoardRepository;
import com.shinhan.education.repository.PDSFileRepository;
import com.shinhan.education.vo.PDSBoard;
import com.shinhan.education.vo.PDSFile;

import lombok.extern.java.Log;

@SpringBootTest
@Log
@Commit
public class OneToManyTest2 {
	@Autowired
	PDSFileRepository frepo;
	@Autowired
	PDSBoardRepository brepo;
	
//	@Test
	// file에 직접 입력 (자식만 insert)
	void test1() {
		PDSFile file = PDSFile.builder().pdsfilename("첨부파일1").build(); // 1을 통해 many의 칼럼에 넣는 것은 가능하지만 many에 직접 넣는 것은 X (pdsno 못넣어)
		frepo.save(file);
	}
	
//	@Test
	// file 전체 조회
	void test2() {
		frepo.findAll().forEach(file -> log.info(file.toString())); // pdsno는 null임 (file에 직접 입력했기 때문)
	}
	
//	@Test
	// board에 직접 입력: file이 없는 경우 (부모만 insert)
	void test3() {
		
		PDSBoard board = PDSBoard.builder().pname("게시글").pwriter("작성자").build();
		brepo.save(board);
	}
	
//	@Test
	// board 전체 조회
	void test4() {
		brepo.findAll().forEach(board -> log.info(board.toString()));
	}
	
//	@Test
	// pdsno가 null인 값을 현재 pid(2L)로 넣기 -> 실제로 db에는 칼럼이 존재하지만 java에는 없음 -> 단순한 setPdsno로는 불가능, 따로 쿼리를 작성해야 함
	void test5() {
		
	}
	
//	@Test
	// 부모(1)에서 자식(n)을 insert
	void test6() {
		List<PDSFile> files = new ArrayList<>();
		PDSFile file1 = PDSFile.builder().pdsfilename("얼굴사진1").build();		
		PDSFile file2 = PDSFile.builder().pdsfilename("얼굴사진2").build();		
		PDSFile file3 = PDSFile.builder().pdsfilename("얼굴사진3").build();	
		files.add(file1);
		files.add(file2);
		files.add(file3);
		
		PDSBoard board = PDSBoard.builder().pname("월요일이다").pwriter("홍길동").files2(files).build();
		
		brepo.save(board);
	}
	
//	@Transactional // LAZY인 경우 자식에게 접근하기 위해 사용
//	@Test
	// 부모(1)를 통해 자식(n)이 잘 들어갔는지 확인
	void test7() {
		brepo.findAll().forEach(board -> log.info(board.toString()));
	}
	
//	@Test 
	// setter로 파일 변경
	void test8() {
		PDSFile file = frepo.findById(1L).orElse(null);
		if(file != null) {
			file.setPdsfilename("수정된 파일");
			
			frepo.save(file);			
		}
	}
	
//	@Test
	// @Modifying ver.1
	void test9() {
		brepo.updateFile(4L, "변경했다(repo에 @)");
	}
//	@Transactional
//	@Test
	// @Modifying ver.2
	// Test 환경에서 @Transaction 작성하면 실행 후 rollback됨 -> class level에 @Commit 추가해야 함 (Test이기 때문에 commit하는 것)
	void test10() {
		brepo.updateFile2(4L, "변경했다(test에 @)");
	}
	
//	@Test
	// board를 이용해 file이름 수정
	void test11() {
		brepo.findById(3L).ifPresent(board -> {
			List<PDSFile> files = board.getFiles2();
			files.forEach(file -> {
				file.setPdsfilename("수정~!");
				frepo.save(file);
			});
		});
	}
	
	@Test
	// board를 이용해 file 추가
	void test12() {
		brepo.findById(3L).ifPresent(board -> {
			List<PDSFile> files = board.getFiles2();
			PDSFile file1 = PDSFile.builder().pdsfilename("추가했다1").build();
			PDSFile file2 = PDSFile.builder().pdsfilename("추가했다2").build();
			
			files.add(file1);
			files.add(file2);
			
			brepo.save(board);
		});
	}
}

