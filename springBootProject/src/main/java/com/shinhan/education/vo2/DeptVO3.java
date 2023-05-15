package com.shinhan.education.vo2;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_dept3")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeptVO3 {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer department_id;
	private String department_name;
	private Integer manager_id;
	private Integer location_id;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "dept")
	List<EmpVO3> emplist;
}
