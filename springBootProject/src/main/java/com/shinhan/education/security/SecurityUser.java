package com.shinhan.education.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.shinhan.education.vo.MemberDTO;

import groovy.transform.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class SecurityUser extends User {
	private static final long serialVersionUID = 1L;
	private static final String ROLE_PREFIX = "ROLE_";
	private MemberDTO member;

	public SecurityUser(String name, String password, Collection<? extends GrantedAuthority> authorities) {
		super(name, password, authorities);
	}

	// sucurity에서 제공하는 method 형태로 사용 -> String, GrantedAuthority
	public SecurityUser(MemberDTO member) {
		// mRole은 여러 개 가질 수 있기 때문에 함수로 뺌
		super(member.getMid(), member.getMpassword(), makeRole(member));
		this.member = member;
		System.out.println("SecurityUser member:" + member);
	}

	// Role을 여러개 가질수 있도록 되어있음
	private static List<GrantedAuthority> makeRole(MemberDTO member) {
		// 인증된, 부여된 권한이 여러 개 있다는 의미로 제네릭이 GrantedAuthority
		List<GrantedAuthority> roleList = new ArrayList<>();
		// ROLE_Xxx로 접두사가 붙어야 함 -> 우리는 접두사가 없으니 상수로 만들어 붙여줌
		roleList.add(new SimpleGrantedAuthority(ROLE_PREFIX + member.getMrole()));
		return roleList;
	}

	// User class에서 username필드가 있지만 google 인증시 사용되는 필드는 name
	// 이를 맞추기위해 함수추가함
//	public String getName() {
//		// TODO Auto-generated method stub
//		return super.getUsername();
//	}	
}
