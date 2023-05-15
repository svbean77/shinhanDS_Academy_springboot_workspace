package com.shinhan.education.vo2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_user2")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO2 {
	@Id
	@Column(name = "user_id")
	private String userid;
	private String username;

	// 대상 테이블에서 참조
}
