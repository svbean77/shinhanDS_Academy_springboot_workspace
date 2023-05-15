package com.shinhan.education.vo2;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_usercellphone2")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCellPhoneVO2 {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "phone_id")
	private Long phoneId;
	private String phoneNumber;
	private String model;
	
	// 얘가 PK는 아님 -> 비식별자를 사용한 방법
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	UserVO2 user;
}
