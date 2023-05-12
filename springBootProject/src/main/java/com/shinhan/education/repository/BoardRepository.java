package com.shinhan.education.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.shinhan.education.vo.BoardVO;

// interface 설계: CRUD 작업을 위해
// 우리는 설계까지만! 구현은 JPA가 한다.
// 1. 기본: findAll(), findById(), save(), count(), exists()
// 2. 규칙에 맞는 메서드 추가: https://docs.spring.io/spring-data/jpa/docs/2.5.1/reference/html/#jpa.query-methods 이름에 맞는 형식
// 3. JPQL 사용: @Query
// 4. JPQL + nativeQuery 사용: @Query(nativeQuery = true) -> 실제 sql문 (DB 구조가 변경되면 직접 수정해야 하기 때문에 유지보수 X)
// 5. Querydsl 사용: 동적으로 SQL문 생성
public interface BoardRepository extends CrudRepository<BoardVO, Long>, QuerydslPredicateExecutor<BoardVO>{  
	// day055
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
	
	// day056
	public List<BoardVO> findByTitleContainingOrderByTitleDesc(String title); // where title like ? order by title desc

	public List<BoardVO> findByTitleContainingOrderByTitle(String title, Pageable paging); // paging -> 몇 페이지 볼건지, 한 페이지에 몇 개
	public List<BoardVO> findByTitleContaining(String title, Pageable paging); 
	
	public Page<BoardVO> findByBnoGreaterThan(Long bno, Pageable paging);
	
	// JPQL
	// 테이블명에 Entity명 적음
	// * 지원하지 않음 -> 모두 가지고 오고 싶다면 별명 이름을 씀
	// ?n으로 몇 번째 파라미터인지 알려주기
	// 기본형 - param 순서 %?1%
	@Query("select b from BoardVO b where b.title like %?1% "
			+ "and b.content like %?2% order by b.bno desc")
	public List<BoardVO> findByTitle2(String title, String content);
	// @Param으로 이름 지정 - param 이름 :tt
	@Query("select b from BoardVO b where b.title like %:tt% "
			+ "and b.content like %:cc% order by b.bno asc")
	public List<BoardVO> findByTitle3(@Param("tt") String title, @Param("cc")String content);
	// entity 이름 가져오기 - #{#emtityName} 
	@Query("select b from #{#entityName} b where b.title like %:tt% "
			+ "and b.content like %:cc% order by b.bno asc")
	public List<BoardVO> findByTitle4(@Param("tt") String title, @Param("cc")String content);
	// 몇 개만 가져오기 - BoardVO 타입으로 만들기 위한 속성이 없음 -> Object[]로 리턴
	@Query("select b.title, b.content, b.writer from #{#entityName} b where b.title like %:tt% "
			+ "and b.content like %:cc% order by b.bno asc")
	public List<Object[]> findByTitle5(@Param("tt") String title, @Param("cc")String content);
	// 실제 sql 문장으로 작성: nativeQuery (남용하지 않기!)
	@Query(value = "select * from t_boards where title like '%'||?1||'%' and content like '%'||?2||'%' order by bno desc", nativeQuery = true)
	public List<BoardVO> findByTitle6(String title, String content);
} 
