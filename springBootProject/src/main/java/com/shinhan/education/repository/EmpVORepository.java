package com.shinhan.education.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.education.vo2.DeptVO;
import com.shinhan.education.vo2.EmpVO;

public interface EmpVORepository extends CrudRepository<EmpVO, Integer>{
	List<EmpVO> findByDept(DeptVO dept);
}
