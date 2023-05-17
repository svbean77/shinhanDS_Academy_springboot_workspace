package com.shinhan.education.vo3;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@AllArgsConstructor
@Builder
@ToString
public class PageVO {
	// 페이지를 이동해도 페이지 정보, 검색한 조건 등을 모두 저장해두기 위해 직접 만든 VO -> 내 마음대로 만들어도 됨
	private static final int DEFAULT_SIZE = 10;
	private static final int DEFAULT_MAX_SIZE = 50;	
	private int page;
	private int size;	
	//검색조건처리위해 field추가
	private String keyword;
	private String type;		
	public PageVO() {
		this.page = 1; // 처음은 1페이지부터 시작
		this.size = DEFAULT_SIZE;
	}
	public void setSize(int size) {
		this.size = size<DEFAULT_SIZE || size > DEFAULT_MAX_SIZE?
				DEFAULT_SIZE:size;
	}
	public Pageable makePageable(int direction, String ...prop) {
		Sort.Direction dir = direction==0?Sort.Direction.DESC:Sort.Direction.ASC; // 0: desc, 1: asc
		return PageRequest.of(this.page-1, this.size, dir, prop); // 어떤 칼럼을 기준으로 정렬할지 칼럼은 여러 개 올 수 있으니까 전개 연산자 ...
		// 실제 페이지는 0부터 시작하니까 page-1
	}
	public void setPage(int page) {
		this.page = page;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
