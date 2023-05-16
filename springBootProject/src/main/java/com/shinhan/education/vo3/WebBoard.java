package com.shinhan.education.vo3;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "replies") // 양방향일 때 ToString에서 제외하는 것이 좋음
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_webboards")
public class WebBoard {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) // oracle:sequence, mysql:identity
	private Long bno;
	@NonNull // WebBoard가 build될때 반드시 setting해야한다.
	@Column(nullable = false) // DB칼럼이 not null: 필수 칼럼이다
	private String title;
	private String writer;
	@Column(length = 1000)
	private String content;

	@CreationTimestamp
	private Timestamp regdate;// yyyy-MM-dd hh:mm:ss

	@UpdateTimestamp // 생성시 생성일자, 수정시 변경된다.
	private Timestamp updatedate;

	@BatchSize(size = 100)
	@JsonIgnore
	@OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<WebReply> replies;
	// @OneToMany와 @ManyToMany는 기본이 지연 로딩(LAZY)이다.
	// @ManyToOne이 EAGER임. 양방향이므로 reply에서 board정보필요하므로 N번 호출됨
	// 그러므로 OneToMany에서 지연로딩으로 변경하여도 N번 쿼리 호출된다.
	// 해결방안은 BatchSize조정 @BatchSize(size = 100)
	
	// fetch=EAGER로 설정하면 게시글 개수만큼 reply에서 select를 진행
	// -> where a=b를 where a in ()로 변경: @BatchSize()
	// -> DB에 갔다 오는 횟수가 줄어듦!

}
