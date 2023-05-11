package com.shinhan.education.vo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity // @Entity: JPA가 관리
@Table(name = "t_boards") // @Table: DB에 테이블로 만들 것
@Builder @NoArgsConstructor @AllArgsConstructor
public class BoardVO {
	@Id @GeneratedValue(strategy = GenerationType.AUTO) // key값이 됨 + 자동 생성
	private Long bno;
	@NonNull // 생성 시 반드시 값이 존재해야 함 (lombok) -> 이거는 db와 관련 X
	@Column(nullable = false) // DB 칼럼이 not null
	private String title;
	@Column(length = 100) // varchar2(100)
	private String content;
	private String writer;
	@CreationTimestamp // insert 시 시각이 자동으로 입력
	private Timestamp regdate;
	@UpdateTimestamp // update 시 시각이 자동으로 입력
	private Timestamp updatedate;
	
	// 칼럼명을 예약어로 사용: invalid identifier (에러)
	// 카멜 표기법 사용: db에서 대문자 앞에 _ 추가됨 ex) updateDate -> update_date
}
