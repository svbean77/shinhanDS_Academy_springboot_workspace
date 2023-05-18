package com.shinhan.education.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.querydsl.core.types.Predicate;
import com.shinhan.education.repository.WebBoardRepository;
import com.shinhan.education.vo3.PageMaker;
import com.shinhan.education.vo3.PageVO;
import com.shinhan.education.vo3.WebBoard;

import lombok.extern.java.Log;

@Controller
@RequestMapping("/webboard")
@Log
public class WebBoardController {
	@Autowired
	WebBoardRepository brepo;
	
	@GetMapping("/list.do") // 파일을 만들 때 .do는 무시 -> list.html 파일이 존재하면 됨
	public void selectAll(PageVO pageVO, Model model, HttpServletRequest request) {
		if(pageVO == null) {
			pageVO = new PageVO();
			pageVO.setPage(1);
		}
		
//		// 자바에서 사용하지 않는다면 필요 없음 -> 알아서 전달되기 때문
//		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
//		if (flashMap != null) {
//			Object message = flashMap.get("msg");
//			log.info("입력/삭제/수정 결과 message -> " + message);
//		}
		
		Predicate pre = brepo.makePredicate(pageVO.getType(), pageVO.getKeyword()); 
		Pageable paging = pageVO.makePageable(pageVO.getPage(), "bno");
//		Pageable paging = PageRequest.of(0, 10, Sort.Direction.DESC, "bno"); // 위의 방법과 같음! 단 조건 검색, 페이지네이션을 적용하지 않은 단순한 페이징
		Page<WebBoard> result = brepo.findAll(pre, paging);
//	
//		List<WebBoard> blist = result.getContent();
//		log.info("전체 " + result.getTotalPages() + "페이지가 있다");
//		log.info("전체 " + result.getTotalElements() + "건의 데이터가 있다");
		PageMaker<WebBoard> pageMaker = new PageMaker<>(result, pageVO.getSize());
		model.addAttribute("blist", pageMaker);
		
		Page<WebBoard> p_result = pageMaker.getResult();
		log.info(p_result.getContent().toString());
	}
	
	@GetMapping("/view.do")
	public void selectById(Long bno, Model model, PageVO pageVO) {
		brepo.findById(bno).ifPresent(board -> {
			model.addAttribute("board", board);
			model.addAttribute("pageVO", pageVO);
		});
	}
	
	@GetMapping("/modify.do")
	public void updateOrDelete(Long bno, Model model, PageVO pageVO) {
		brepo.findById(bno).ifPresent(board -> {
			model.addAttribute("board", board);
			model.addAttribute("pageVO", pageVO);
		});
	}
	@PostMapping("/modify.do")
	public String updatePost(WebBoard board, PageVO pageVO, RedirectAttributes attr) {
		WebBoard savedBoard = brepo.save(board); // 수정된 게시글이 들어오니까 그대로 board를 작성하면 됨!
		if(savedBoard == null) {
			attr.addFlashAttribute("msg", "게시글 수정 작업 실패..");
		} else {
			attr.addFlashAttribute("msg", "게시글 수정 작업 완료~!");
		}
		attr.addAttribute("bno", board.getBno());
		attr.addAttribute("page", pageVO.getPage());
		attr.addAttribute("size", pageVO.getSize());
		attr.addAttribute("keyword", pageVO.getKeyword());
		attr.addAttribute("type", pageVO.getType());
		
		log.info(board.toString());
		log.info(savedBoard.toString());
		return "redirect:view.do";
	}
	@PostMapping("/delete.do")
	public String deletePost(WebBoard board, PageVO pageVO, RedirectAttributes attr) {
		brepo.delete(board); // update 하면서 코드 에러로 regdate가 사라졌었음 -> 삭제하려는 객체가 없어 에러
//		brepo.deleteById(bno); // 따라서 regdate parsing 에러를 피하기 위해 board가 아닌 bno로 parameter를 받음(인데 다음날 해보니 되네)
		// addFlashAttribute: 새로고침하면 사라짐 -> 일회성 (새로고침을 하면 msg가 없음)
		// addAttribute: 새로고침해도 유지
		attr.addFlashAttribute("msg", "게시글 삭제 작업 완료~!");
//		attr.addAttribute("pageVO", pageVO); // 객체로 전송이 불가 -> 내가 알아서 이름을 같게 attr를 보내면 pageVO 형태로 가져와줌
		attr.addAttribute("page", pageVO.getPage());
		attr.addAttribute("size", pageVO.getSize());
		attr.addAttribute("keyword", pageVO.getKeyword());
		attr.addAttribute("type", pageVO.getType());
		return "redirect:list.do";
	}
	
	@GetMapping("/register.do")
	public void registerGet() {
		
	}
	@PostMapping("/register.do")
	public String registerPost(WebBoard board, RedirectAttributes attr) {
		WebBoard savedBoard = brepo.save(board);
		if(savedBoard != null) {
			attr.addFlashAttribute("msg", "게시글 작성 작업 완료~!");
		} else {
			attr.addFlashAttribute("msg", "게시글 작성 작업 실패...");
		}
		
		return "redirect:list.do";
	}
}
