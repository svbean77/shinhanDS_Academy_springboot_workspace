package com.shinhan.education.vo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_pdsboard")
@EqualsAndHashCode(of = "pid") // pid만 같으면 같은 인스턴스
public class PDSBoard {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long pid;
	private String pname;
	private String pwriter;
	// cascade:영속성전이 PDSBoard변경시 PDSFile변경)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // 즉시로딩
	// JPA는 default로 지연로딩을 사용한다.(PDSBoard조회시 PDSFile 조인하지않음)
	// 1)fetch = FetchType.LAZY ....PDSFile과 연결불가, @Query로 해결  (default)
	// 2)fetch = FetchType.EAGER....PDSFile과 연결가능
	// OneToMany인 경우 -> one에서 many를 사용할 수 있음
	// 사용하는 방법 1. LAZY: 가져올 때 many는 가져오지 않음 -> 
	@JoinColumn(name = "pdsno") // 실제로는 PDSFile칼럼에 생성 // 책 p.164: 중간 테이블을 만드는 것이 더 쉬운 방법!
	private List<PDSFile> files2;  
}
