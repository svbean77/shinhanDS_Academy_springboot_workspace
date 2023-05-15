package com.shinhan.education.vo2;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.shinhan.education.vo.MemberRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_enum")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnumTypeVO {
	@Id
	private String mid;
	private String mpassword;
	private String mname;
	
	// 한 명의 member(=EnumTypeVO)가 여러 개의 권한을 가지고 있다! 
	// tbl_enum_mroles에 member의 권한들이 저장 -> 이 때의 key가 mid이다
	// (저 테이블에는 mid, mrole이 들어있음, 같은 key에 대해 여러 개의 mrole 들어감 -> PK가 없으니까) -> tbl_enum_mroles이라는 테이블을 따로 만들어 저장하는 것!
	@ElementCollection(targetClass = MemberRole.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "tbl_enum_mroles", joinColumns = @JoinColumn(name = "mid"))
	@Enumerated(EnumType.STRING)
	Set<MemberRole> mrole;
}
