package com.shinhan.education.repository;

import java.util.List;

import javax.persistence.FetchType;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shinhan.education.vo.PDSBoard;

public interface PDSBoardRepository extends CrudRepository<PDSBoard, Long> {
	// day056
	// fetch = FetchType.LAZY인 경우 PDSFile를 join하지 않음 -> 직접 쿼리문을 작성해야 PDSFile 조회 가능
	// 방법 1. JPQL
	@Query("select b.pname, b.pwriter, f.pdsfilename from PDSBoard b left outer join b.files2 f where b.pid = ?1 order by b.pid ")
	public List<Object[]> getFilesInfo(long pid);
	// 방법 2. nativeQuery
	@Query(value = "select board.pname, count(*) from tbl_pdsboard board "
			+ "left outer join tbl_pdsfiles f on(f.pdsno = board.pid) "
			+ "group by board.pname", nativeQuery = true)
	public List<Object[]> getFilesInfo2();
	
	// day057: @Modifying
	// @Query는 select만 지원 -> DML을 쓰기 위해 @Modifying 사용해야 함
	// @Modifying을 사용한 경우 @Transactional을 사용해야 함
	// Repository에 @Transactional 존재 -> commit 됨 -> transactional은 service에 존재해야 함! 따라서 repo에 작성하는 것은 바람직하지 X
	@Modifying
	@Transactional
	@Query("update PDSFile f set f.pdsfilename=?2 where f.fno=?1")
	int updateFile(Long fno, String newFileName);
	// Repository에 @Transactional 존재X -> commit 안됨
	@Modifying
	@Query("update PDSFile f set f.pdsfilename=?2 where f.fno=?1")
	int updateFile2(Long fno, String newFileName);
}
