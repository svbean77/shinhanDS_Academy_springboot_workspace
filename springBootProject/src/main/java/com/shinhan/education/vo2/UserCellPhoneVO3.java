package com.shinhan.education.vo2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_usercellphone3") 
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCellPhoneVO3 {
	// 식별자로 참조하기 (참조하는 key를 PK로 사용)
	@Id
	private String useridaaa;

	// 실제로는 존재하지 않음! userid를 이런 형태의 key로 설정하겠다는 의미
	@MapsId // 이 값을 Id(userid)와 매핑시키겠다 (FK를 PK로 지정할 때 사용) -> DB에는 user_id라는 이름으로 생성, 자바에서는 userid라는 이름으로 사용
	@OneToOne
	@JoinColumn(name = "user_id")
	UserVO3 user;

	private String phoneNumber;
	private String model;
}
