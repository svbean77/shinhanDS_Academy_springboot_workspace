package com.shinhan.education.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = { "pno" })
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_profile")
public class ProfileDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long pno;
	private String fname;
	private boolean currentYn;
	// false:0 true:1 // default가 not null

	@ManyToOne // join, many(프로필)에서 one(member)로 갔기 때문에 ManyToOne
	private MemberDTO member; // member_mid칼럼이 DB생성된다. (FK)

	// 직원(n) - 부서(1) => n이 1을 참조한다 -> n쪽에 칼럼 생김
	// 같은 의미로 프로필(n) - 멤버(1) => 프로필이 멤버를 참조한다 -> 프로필에 칼럼 생김
	// 직원, 부서
	// 직원이 부서를 참조한다.
	// 직원테이블에 부서의 키를 FK로 생성한다.

	// ProfileDTO가 MemberDTO를 참조한다.
	// tbl_profile테이블에 tbl_members테이블의 키값 mid가 FK로 생성한다.

}