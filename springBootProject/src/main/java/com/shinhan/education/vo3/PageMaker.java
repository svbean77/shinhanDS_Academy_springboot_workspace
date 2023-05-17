package com.shinhan.education.vo3;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.java.Log;

@Getter
@ToString(exclude = "pageList")
@Log
public class PageMaker<T> {
	// 내 데이터를 기준으로 101개의 데이터이면 1~11 만드는 것

	private Page<T> result;
	private Pageable prevPage; //이전으로 이동하는데 필요한 정보를 가짐
	private Pageable nextPage;
	private Pageable currentPage;
	private int currentPageNum;  //화면에 보이는 1부터 시작하는 페이지번호
	private int totalPageNum;
	private List<Pageable> pageList; // 이거를 이용해 1~10 이 페이지 만드는 것
	
	public PageMaker(Page<T> result, int pageSize) {
		this.result = result;
		this.currentPage = result.getPageable();
		this.currentPageNum = currentPage.getPageNumber()+1; // page의 시작은 0, 우리가 볼 때는 1페이지 기준이니까 +1 
		this.totalPageNum = result.getTotalPages();
		this.pageList = new ArrayList<Pageable>();
		calcPage(pageSize);
	}
	public void calcPage(int cnt) {
		int tempEndNum = (int)(Math.ceil(currentPageNum/(cnt * 1.0))*cnt); // 10개씩이라면 3페이지: 21~30번
		int startNum = tempEndNum - (cnt - 1);
		Pageable startPage = this.currentPage;
		for(int i = startNum; i<this.currentPageNum; i++) {
			startPage = startPage.previousOrFirst();
		}
		this.prevPage = startPage.getPageNumber()<=0?null:startPage.previousOrFirst();
		log.info("tempEndNum:" + tempEndNum);
		log.info("totalPageNum:" + totalPageNum);
		if(this.totalPageNum<tempEndNum) {
			tempEndNum = this.totalPageNum;
			this.nextPage = null;
		}
		
		for(int i = startNum; i<=tempEndNum; i++) {
			pageList.add(startPage);
			startPage = startPage.next();
		}
		this.nextPage = startPage.getPageNumber()+1 < totalPageNum?startPage:null;
	}
}
