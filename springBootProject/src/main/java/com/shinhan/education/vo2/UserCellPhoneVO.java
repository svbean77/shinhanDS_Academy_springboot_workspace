package com.shinhan.education.vo2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_usercellphone")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCellPhoneVO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "phone_id")
	private Long phoneId;
	private String phoneNumber;
	private String model;
}
