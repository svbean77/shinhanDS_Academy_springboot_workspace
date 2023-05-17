package com.shinhan.education.vo3;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "board")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_webreplies")
public class WebReply {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO) // oracle:sequence, mysql:identity
	Long rno;
	String replyText;
	String replyer;
	@CreationTimestamp
	private Timestamp regdate;// yyyy-MM-dd hh:mm:ss
	@UpdateTimestamp // 생성시 생성일자, 수정시 변경된다.
	private Timestamp updatedate;

	@JsonIgnore // 댓글을 불러올 때 board 정보가 아닌 댓글만 보여야 함 -> json으로 만들 때 제외시키기
	// @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@ManyToOne(fetch = FetchType.LAZY) // lazy로 변경해도 OneToMany에서 Lazy가 그대로 수행~
	// @JoinColumn(name="board_bno")
	WebBoard board;
	// @ManyToOne, @OneToOne과 같이 @XXXToOne 어노테이션들은 기본이 즉시 로딩(EAGER) 이다.

}
