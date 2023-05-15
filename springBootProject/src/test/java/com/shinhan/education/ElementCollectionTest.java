package com.shinhan.education;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.education.repository.EnumTypeVORepository;
import com.shinhan.education.vo.MemberRole;
import com.shinhan.education.vo2.EnumTypeVO;

import lombok.extern.java.Log;

@SpringBootTest
@Log
public class ElementCollectionTest {
	@Autowired
	EnumTypeVORepository erepo;
	
//	@Test
	// 여러 개의 권한 줘서 insert
	void test1() {
		Set<MemberRole> roles = new HashSet<>();
		roles.add(MemberRole.ADMIN);
		roles.add(MemberRole.USER);
		EnumTypeVO vo = EnumTypeVO.builder().mid("happy55").mpassword("1234").mname("김철수").mrole(roles).build();
		
		erepo.save(vo);
	}
	
//	@Test
	// 멤버 조회
	void test2() {
		erepo.findAll().forEach(m -> log.info(m.toString()));
	}
	
//	@Test
	// mid가 n인 멤버 조회
	void test3() {
		erepo.findById("happy11").ifPresent(m -> {
			log.info(m.toString());
			log.info(m.getMrole().toString());
			for(MemberRole role: m.getMrole()) {
				log.info(role.name());
			}
		});
	}
}
