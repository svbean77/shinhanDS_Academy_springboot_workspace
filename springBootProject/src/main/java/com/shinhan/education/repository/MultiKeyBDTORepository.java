package com.shinhan.education.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.education.vo2.MultiKeyB;
import com.shinhan.education.vo2.MultiKeyBDTO;

public interface MultiKeyBDTORepository extends CrudRepository<MultiKeyBDTO, MultiKeyB>{
	
}
