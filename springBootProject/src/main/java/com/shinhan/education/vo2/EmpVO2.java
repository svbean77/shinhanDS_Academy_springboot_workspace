package com.shinhan.education.vo2;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_emp2")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpVO2 {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer employee_id;
	private String first_name;
	private String last_name;
	private String email;
	private String phone_number;
	private Date hire_date;
	private String job_id;
	private Integer salary;
	private Double commission_pct;
	private Integer manager_id;
//	private Integer department_id; // JoinColumn으로 생성되기 때문에 따로 만들지 않아도 됨
}
