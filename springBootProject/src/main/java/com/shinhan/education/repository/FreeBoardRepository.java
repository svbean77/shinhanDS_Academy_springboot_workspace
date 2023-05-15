package com.shinhan.education.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.shinhan.education.vo.FreeBoard;

public interface FreeBoardRepository extends CrudRepository<FreeBoard, Long>{
	List<FreeBoard> findByBnoGreaterThan(Long bno, Pageable page);
}
