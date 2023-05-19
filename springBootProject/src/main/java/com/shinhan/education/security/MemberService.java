package com.shinhan.education.security;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shinhan.education.repository.MemberRepository;
import com.shinhan.education.vo.MemberDTO;

@Service
public class MemberService implements UserDetailsService {
	@Autowired
	private HttpSession httpSession;

	@Autowired
	private MemberRepository mrepo;

	@Autowired
	PasswordEncoder passwordEncoder; // security config에서 Bean생성
	
	// 회원가입
	// @Transactional
	public MemberDTO joinUser(MemberDTO member) {
		// 비밀번호 암호화...암호화되지않으면 로그인되지않는다.
		// ex) 1234 -> encode에 의해 암호화
		String pwd = passwordEncoder.encode(member.getMpassword());
		member.setMpassword(pwd);
		System.out.println("암호화된 pass:" + pwd);
		return mrepo.save(member);
	}

	// !!!!반드시 구현해야한다.
	// 인증처리할 때 항상 사용 -> 아이디가 있는지 확인 후 securityUser로 바꿈
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String mid) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername mid:" + mid);
		
		// filter는 조건에 맞는것만 선택
		// map: 가지고 있는 데이터를 변형한다.
		UserDetails member = mrepo.findById(mid).filter(m -> m != null).map(m -> new SecurityUser(m)).get();
		System.out.println("UserDetails member:" + member);
		httpSession.setAttribute("user", member); // 페이지가 이동되어도 로그인 유저 정보는 저장되도록
		httpSession.setAttribute("member", mrepo.findById(mid).get());
		return member;
	}

}
