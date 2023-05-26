package com.shinhan.education.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;
import com.shinhan.education.repository.WebBoardRepository;
import com.shinhan.education.vo3.PageMaker;
import com.shinhan.education.vo3.PageVO;
import com.shinhan.education.vo3.WebBoard;

@RestController
@RequestMapping("/rest/webboard")

public class WebBoardRestController {
	@Autowired
	WebBoardRepository brepo;

	@GetMapping("/list.do")
	public List<WebBoard> selectAll(PageVO pageVO, Model model, HttpServletRequest request) {
		if (pageVO == null) {
			pageVO = new PageVO();
			pageVO.setPage(1);
		}

		Predicate pre = brepo.makePredicate(pageVO.getType(), pageVO.getKeyword());
		Pageable paging = pageVO.makePageable(pageVO.getPage(), "bno");
		Page<WebBoard> result = brepo.findAll(pre, paging);
		PageMaker<WebBoard> pageMaker = new PageMaker<>(result, pageVO.getSize());
		model.addAttribute("blist", pageMaker);

		Page<WebBoard> p_result = pageMaker.getResult();

		return (List<WebBoard>) brepo.findAll(Sort.by(Direction.DESC, "bno"));
	}

	@GetMapping("/view.do/{bno}")
	public WebBoard selectById(@PathVariable Long bno) {
		return brepo.findById(bno).orElse(null);
	}

	@GetMapping("/modify.do/{bno}")
	public WebBoard updateOrDelete(@PathVariable Long bno) {
		return brepo.findById(bno).orElse(null);
	}

	@PutMapping(value = "/modify.do", consumes = "application/json")
	public WebBoard updatePost(@RequestBody WebBoard board) {
		WebBoard savedBoard = brepo.save(board);
		return savedBoard;
	}

	// 주소창에 bno가 전달됨 ->PathVariable
	@DeleteMapping(value = "/delete.do/{bno}", produces = "text/plain;charset=utf-8")
	public String deletePost(@PathVariable Long bno) {
		brepo.deleteById(bno);
		return "삭제작업 완료! delete";
	}

	@GetMapping("/register.do")
	public void registerGet() {
 
	}

	// 요청문서로 board가 전달됨 -> @RequestBody
	@PostMapping(value = "/register.do", consumes = "application/json")
	public WebBoard registerPost(@RequestBody WebBoard board) {
		WebBoard savedBoard = brepo.save(board);
		return savedBoard;
	}
}
