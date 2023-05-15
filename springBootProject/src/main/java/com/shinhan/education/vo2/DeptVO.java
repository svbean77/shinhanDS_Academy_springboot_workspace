package com.shinhan.education.vo2;

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
@Table(name = "tbl_dept")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeptVO {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer department_id;
	private String department_name;
	private Integer manager_id;
	private Integer location_id;
}
