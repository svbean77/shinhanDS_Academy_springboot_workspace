package com.shinhan.education.vo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "t_jobs")
@Entity  
public class JobVO {
	@Id // @Id는 pk이기 때문에 not null, unique가 들어감
	private String jobId;
	@NonNull @Column(nullable = false, length = 35, unique = true)
	private String jobTitle;
	private int minSalary;
	private int maxSalary;
	@CreationTimestamp
	private Timestamp regDate;
	@UpdateTimestamp
	private Timestamp updateDate;
}
