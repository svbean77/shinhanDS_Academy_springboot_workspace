package com.shinhan.education.vo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter 
@ToString 
@Builder
@AllArgsConstructor @NoArgsConstructor
@Entity @Table(name = "tbl_freeboards")
@EqualsAndHashCode(of= {"bno", "title"}) // bno, title이 같으면 같음
public class FreeBoard {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long bno;
	private String title;
	private String writer;
	private String content;
	@CreationTimestamp
	private Timestamp regDate;
	@UpdateTimestamp
	private Timestamp updateDate;
	
	@JsonIgnore // Jackson이 json 만들 때 해당 칼럼을 무시: toString의 exclude와 비슷하다고 생각하면 됨
	// 양방향이기 때문에 무슨 칼럼에 묶여있는지 설정 -> mappedBy (n의 board 칼럼과 연결되었으니 board라고 작성)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "board")
	List<FreeBoardReply> replies;
}
