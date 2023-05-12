package com.shinhan.education.vo;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
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
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_members")
public class MemberDTO {
	@Id
	String mid;
	String mpassword;
	String mname;

	@Enumerated(EnumType.STRING) // DB에 enum의 순서가 아닌 값으로 저장되었으면 좋겠다 -> EnumType.STRING
	MemberRole mrole;

}
