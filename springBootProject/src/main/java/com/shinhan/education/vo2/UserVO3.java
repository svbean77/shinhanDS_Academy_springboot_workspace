package com.shinhan.education.vo2;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_user3")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO3 {
	@Id
	@Column(name = "user_id")
	private String userid;
	private String username;
	
	// 양방향 참조: 주 테이블의 PK를 식별자로 사용하는 경우
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	UserCellPhoneVO3 phone;
}
