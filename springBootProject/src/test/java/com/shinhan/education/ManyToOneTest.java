package com.shinhan.education;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.education.repository.MemberRepository;
import com.shinhan.education.repository.ProfileRepository;
import com.shinhan.education.vo.MemberDTO;
import com.shinhan.education.vo.MemberRole;
import com.shinhan.education.vo.ProfileDTO;

import lombok.extern.java.Log;

@SpringBootTest // Test 환경이 아니면 NullPointerException 발생
@Log
public class ManyToOneTest {
	@Autowired
	MemberRepository mrepo;
	@Autowired
	ProfileRepository prepo;
	
	// 다대일
//	@Test
	// member table에 10명 입력
	void memberInsert() {
		IntStream.rangeClosed(1, 10).forEach(idx -> {
			MemberDTO member = MemberDTO.builder().mid("user" + idx).mname("멤버" + idx).mpassword("1234").build();
			if (idx <= 5 ) {
				member.setMrole(MemberRole.ADMIN);
			} else {
				member.setMrole(MemberRole.USER);
			}
			
			mrepo.save(member);
		});
	}
	
//	@Test
	// 전체 멤버 조회
	void memberSelectAll() {
		mrepo.findAll().forEach(member -> log.info(member.toString()));
	}
	
//	@Test
	// user1의 프로필 5개 생성, user2의 프로필 5개 생성
	void profileInsertTest() {
		MemberDTO member = mrepo.findById("user1").orElse(null);
		if(member != null) {
			log.info("프로필을 생성할 멤버: " + member.toString());
			IntStream.range(1, 6).forEach(idx -> {
				// memberDTO를 주면 member를 타고 올라가 FK 지정됨
				ProfileDTO profile = ProfileDTO.builder().fname("face-" + idx + ".jpg").currentYn(idx == 5 ? true : false).member(member).build();
				
				prepo.save(profile);
			});
			
			prepo.findAll().forEach(profile -> log.info(profile.toString()));
		}
		MemberDTO member2 = mrepo.findById("user2").orElse(null);
		if(member2 != null) {
			log.info("프로필을 생성할 멤버: " + member2.toString());
			IntStream.range(1, 6).forEach(idx -> {
				// memberDTO를 주면 member를 타고 올라가 FK 지정됨
				ProfileDTO profile = ProfileDTO.builder().fname("hair-" + idx + ".jpg").currentYn(idx == 5 ? true : false).member(member2).build();
				
				prepo.save(profile);
			});
			
			prepo.findAll().forEach(profile -> log.info(profile.toString()));
		}
	}
	
//	@Test
	// 특정 멤버의 프로필 조회
	void getProfileByMember() {
		MemberDTO member = mrepo.findById("user2").orElse(null);
		prepo.findByMember(member).forEach(profile -> log.info(profile.toString()));
	}
	
//	@Test
	// 해당 프로필의 멤버 정보 알아내기
	void getMemberByProfile() {
		Long pno = 603L;
		prepo.findById(pno).ifPresent(profile -> {
			log.info("[" + (profile.isCurrentYn() == true ? "현재" : "이전") + "]" + profile + "의 주인공은???");
			MemberDTO member = profile.getMember();
			log.info(member.toString());
			log.info(member.getMname() + ": " + member.getMrole());
		});
	}
	
//	@Test
	// 멤버의 프로필 개수 얻기
	void getProfileCount() {
		List<Object[]> result = prepo.getMemberWithProfileCount("1");
		result.forEach(arr -> log.info(Arrays.toString(arr)));
	}
	
//	@Test
	// 멤버 추가
	void addMember() {
		IntStream.range(1, 4).forEach(idx -> {
			MemberDTO member = MemberDTO.builder().mid("manager-" + idx).mname("매니저" + idx).mpassword("1234").mrole(MemberRole.MANAGER).build();
			mrepo.save(member);
		});
	}
	
	// 일대다
}
