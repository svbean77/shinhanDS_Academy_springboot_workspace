package com.shinhan.education.repository;

import java.util.List;

import javax.persistence.FetchType;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shinhan.education.vo.PDSBoard;

public interface PDSBoardRepository extends CrudRepository<PDSBoard, Long> {
	// fetch = FetchType.LAZY인 경우 PDSFile를 join하지 않음 -> 직접 쿼리문을 작성해야 PDSFile 조회 가능
	// 방법 1. JPQL
	@Query("select b.pname, b.pwriter, f.pdsfilename from PDSBoard b left outer join b.files2 f where b.pid = ?1 order by b.pid ")
	public List<Object[]> getFilesInfo(long pid);
	// 방법 2. nativeQuery
	@Query(value = "select board.pname, count(*) from tbl_pdsboard board "
			+ "left outer join tbl_pdsfiles f on(f.pdsno = board.pid) "
			+ "group by board.pname", nativeQuery = true)
	public List<Object[]> getFilesInfo2();
}
