package com.shinhan.education;

import lombok.extern.java.Log;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.education.repository.DeptVO2Repository;
import com.shinhan.education.repository.DeptVORepository;
import com.shinhan.education.repository.EmpVORepository;
import com.shinhan.education.vo2.DeptVO;
import com.shinhan.education.vo2.EmpVO;

@SpringBootTest
@Log
public class EmpDeptTest {
	@Autowired
	DeptVORepository drepo1;
	@Autowired
	EmpVORepository erepo1;
	@Autowired
	DeptVO2Repository drepo2;
	
	// repo1: @ManyToOne
//	@Test
	// dept에 데이터 삽입
	void test1() {
		DeptVO dept = DeptVO.builder().department_name("마케팅부").build();
		drepo1.save(dept);
	}
	
//	@Test
	// 150번 부서에 emp에 데이터 삽입
	void test2() {
		String[] fname = {"김", "이", "박"};
		String[] lname = {"길동", "철수", "영희"};
		drepo1.findById(158).ifPresent(dept -> {
			IntStream.range(0, 3).forEach(idx -> {
				EmpVO emp = EmpVO.builder().first_name(fname[idx]).last_name(lname[idx]).email("user" + (idx + 1) + "@email.com")
						.phone_number("010-0000-000" + idx).job_id("IT_PROG").salary((idx + 1) * 1000).dept(dept).build();
				
				erepo1.save(emp);
			});
		});
	}
	
//	@Test
	// 모든 부서, 사원을 출력
	void test3() {
		log.info("-- 모든 부서 조회 --");
		drepo1.findAll().forEach(dept -> log.info(dept.toString()));
		
		log.info("-- 모든 직원 조회 --");
		erepo1.findAll().forEach(emp -> log.info(emp.toString()));
	}
	
	@Test
	// 부서 번호가 n번인 사원 불러오기
	void test4() {
		drepo1.findById(158).ifPresent(dept -> {
			erepo1.findByDept(dept).forEach(emp -> log.info(emp.toString()));
		});
	}
	
	// repo2: @OneToMany
	@Test
	// 
	void test5() {
		
	}
}
