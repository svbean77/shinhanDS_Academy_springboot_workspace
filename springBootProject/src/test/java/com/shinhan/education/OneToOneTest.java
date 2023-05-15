package com.shinhan.education;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.education.repository.UserCellPhoneRepository;
import com.shinhan.education.repository.UserCellPhoneRepository2;
import com.shinhan.education.repository.UserVO3Repository;
import com.shinhan.education.repository.UserVORepository;
import com.shinhan.education.vo2.UserCellPhoneVO;
import com.shinhan.education.vo2.UserCellPhoneVO2;
import com.shinhan.education.vo2.UserCellPhoneVO3;
import com.shinhan.education.vo2.UserVO;
import com.shinhan.education.vo2.UserVO2;
import com.shinhan.education.vo2.UserVO3;

import lombok.extern.java.Log;

@SpringBootTest
@Log
public class OneToOneTest {
	@Autowired
	UserVORepository urepo;
	@Autowired
	UserCellPhoneRepository prepo;
	
	@Autowired
	UserCellPhoneRepository2 prepo2;
	
	@Autowired
	UserVO3Repository urepo3;
	
//	@Test
	// 주테이블에서 참조: user에 데이터 insert
	void test1() {
		UserCellPhoneVO phone = UserCellPhoneVO.builder().phoneNumber("010-1111-1111").model("갤럭시").build();
		UserCellPhoneVO savedPhone = prepo.save(phone);
		
		UserVO user = UserVO.builder().userid("good").username("홍길동").phone(savedPhone).build();
		urepo.save(user);
	}
	
//	@Test
	// 대상테이블에서 참조: user2에 데이터 insert
	void test2() {
		UserVO2 user = UserVO2.builder().userid("good").username("홍길동").build();
		UserCellPhoneVO2 phone = UserCellPhoneVO2.builder().phoneNumber("010-2222-2222").model("갤럭시").user(user).build();
		
		prepo2.save(phone);
	}
	
	@Test
	// 양방향 참조: user3에 데이터 insert 
	void test3() {
		UserCellPhoneVO3 phone = UserCellPhoneVO3.builder().phoneNumber("010-0000-0000").model("갤럭시").build();
		UserVO3 user = UserVO3.builder().username("김영희").userid("hello").phone(phone).build();
		
		// 양방향이기 때문에 phone에도 user를 넣어줘야 함
		phone.setUser(user);
		urepo3.save(user);
	}
}
