package com.shinhan.education.vo2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
	@Id
	@Column(name = "user_id")
	private String userid;
	private String username;
	
	@OneToOne // 주테이블에 어노테이션 존재! (user에서 phone을 참조)
	@JoinColumn(name = "phone_id")
	UserCellPhoneVO phone;
}
