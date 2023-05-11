package com.shinhan.education.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.education.vo.BoardVO;

// interface 설계: CRUD 작업을 위해
// 우리는 설계까지만! 구현은 JPA가 한다.
public interface BoardRepository extends CrudRepository<BoardVO, Long>{
	// 조건 조회를 추가하고 싶다! (기본 CRUD가 아닌 향상 sql문을 위해 interface에 메소드 추가)
	public List<BoardVO> findByTitle(String title);
	public List<BoardVO> findByContent(String cont);
	public List<BoardVO> findByWriter(String writer);
	public List<BoardVO> findByWriterAndTitle(String w, String t); // 파라미터 순서와 함수 이름의 순서가 같아야 함
	
	public List<BoardVO> findByTitleContaining(String title); // where title like '%?%'
	public List<BoardVO> findByTitleStartingWith(String title); // where title like '?%'
	public List<BoardVO> findByTitleEndingWith(String title); // where title like '%?'
	
	public List<BoardVO> findByTitleContainingAndBnoGreaterThan(String title, Long bno); // where title like '%?%' and bno > ?
	
	// where UPPER(title) = '%'||UPPER(val)||'%'
	// and bno between start and end
	// order by writer desc
	public List<BoardVO> findByTitleIgnoreCaseContainingAndBnoBetweenOrderByWriterDesc(String title, Long start, Long end);
	
	public List<BoardVO> findByContentNull(); // where content is null
} 
