package com.shinhan.education.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.education.vo.JobVO;

public interface JobsRepository extends CrudRepository<JobVO, String>{

}
