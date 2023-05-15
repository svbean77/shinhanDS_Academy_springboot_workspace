package com.shinhan.education.vo;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter 
@ToString(exclude = "board") 
@Builder
@AllArgsConstructor @NoArgsConstructor
@Entity @Table(name = "tbl_free_replies")
@EqualsAndHashCode(of= {"rno"})
public class FreeBoardReply {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long rno;
	private String reply;
	private String replyer;
	@CreationTimestamp
	private Timestamp regDate;
	@UpdateTimestamp
	private Timestamp updateDate;
	
	// 연관관계 설정: n대 1 -> tbl_free_replies 테이블에 칼럼이 board_bno로 생김 (FK)
	@ManyToOne
	FreeBoard board;
}
