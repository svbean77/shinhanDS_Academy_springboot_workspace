package com.shinhan.education;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.education.repository.MultiKeyAUsingRepository;
import com.shinhan.education.repository.MultiKeyBDTORepository;
import com.shinhan.education.vo2.MultiKeyAUsing;
import com.shinhan.education.vo2.MultiKeyB;
import com.shinhan.education.vo2.MultiKeyBDTO;

import lombok.extern.java.Log;

@SpringBootTest
@Log
public class MultiKeyTest {
	@Autowired
	MultiKeyAUsingRepository arepo;
	@Autowired
	MultiKeyBDTORepository brepo;
	
//	@Test
	// @IdClass: 복합키 사용한 테이블에 insert
	void test1() {
		MultiKeyAUsing child = MultiKeyAUsing.builder().id1(1).id2(2).userName("김길동").address("이대").build();
		arepo.save(child); // 같은 key이면 update가 실행됨
	}
	
	@Test
	// @Embedded: 복합키 사용한 테이블에 insert
	void test2() {
		MultiKeyB keys = new MultiKeyB();
		keys.setId1(1);
		keys.setId2(1);
		MultiKeyBDTO child = MultiKeyBDTO.builder().id(keys).userName("홍길동").address("홍대").build();
		
		brepo.save(child);
	}
}
