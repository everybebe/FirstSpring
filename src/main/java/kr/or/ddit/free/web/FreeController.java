
package kr.or.ddit.free.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.free.service.IFreeService;
import kr.or.ddit.vo.FreeVO;
import kr.or.ddit.vo.PaginationInfoVO;

@Controller
@RequestMapping("/free")
public class FreeController {
	
	@Inject
	private IFreeService freeService;
	
	//Retrieve
	@RequestMapping(value="/list.do")
	public String freeList(
			@RequestParam(name="page", required = false, defaultValue ="1") int currentPage,
			@RequestParam(required = false, defaultValue = "title") String searchType,
			@RequestParam(required = false) String searchWord,
			Model model) {
		
		//일반적인 게시판 목록 조회(방법1) - 페이징이 구현되어 있지 않다
		//List<NoticeVO> noticeList = noticeService.selectNoticeList();
		//model.addAttribute("noticeList", noticeList);
		//return "notice/list";
		
		PaginationInfoVO<FreeVO> pagingVO = new PaginationInfoVO<FreeVO>();
		
		
		if(StringUtils.isNoneBlank(searchWord)) {
			pagingVO.setSearchType(searchType);
			pagingVO.setSerchWord(searchWord);
			model.addAttribute("searchType", searchType);
			model.addAttribute("searchWord", searchWord);
		}
		
		pagingVO.setCurrentPage(currentPage);
		
		int totalRecord = freeService.selectFreeCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		List<FreeVO> dataList = freeService.selectFreeList2(pagingVO);
		pagingVO.setDataList(dataList);
		model.addAttribute("pagingVO", pagingVO);
		
		return "free/list";
	}	
	
	@RequestMapping(value ="/detail.do", method = RequestMethod.GET)
	public String freeDetail(int boNo, Model model) {
		FreeVO freeVO = freeService.selectFree(boNo);
		model.addAttribute("free",freeVO);
		return "free/view";
	}
	
	//insert	
	@RequestMapping(value="/form.do", method = RequestMethod.GET)
	public String freeForm() {
		return "free/form";
	}
	
	@RequestMapping(value="/insert.do", method = RequestMethod.POST)
	public String FreeInsert(FreeVO freeVO, Model model) {
		String goPage = "";
		Map<String, String> errors = new HashMap<String, String>();
		
		if(StringUtils.isAllBlank(freeVO.getBoTitle())) {
			errors.put("boTitle","제목을 입력해주세요!");
		}
		if(StringUtils.isAllBlank(freeVO.getBoContent())) {
			errors.put("boContent","내용을 입력해주세요!");
		}
		
		
		if(errors.size() > 0) {
			model.addAttribute("error",errors);
			model.addAttribute("free",freeVO);
			goPage = "free/form";
		} else {
			freeVO.setBoWriter("깅밍정");
			ServiceResult result = freeService.insertFree(freeVO);
			if(result.equals(ServiceResult.OK)) {  //등록 성공
				goPage = "redirect:/free/detail.do?boNo=" + freeVO.getBoNo();
			} else {							   //등록 실패
				errors.put("msg", "서비스에러! 다시 시도해주세요!");
				model.addAttribute("errors", errors);
				goPage = "free/form";
			}
		}
		return goPage;
	}
	
	
	//update
	@RequestMapping(value="/update.do", method=RequestMethod.GET)
	public String freeUpdateForm(int boNo, Model model) {
		FreeVO freeVO = freeService.selectFree(boNo);
		model.addAttribute("free", freeVO);
		model.addAttribute("status","u");
		return "free/form";
	}
	
	@RequestMapping(value="/update.do", method = RequestMethod.POST)
	public String freeUpdate(FreeVO freeVO, Model model) {
		String goPage = "";
		ServiceResult result = freeService.updateFree(freeVO);
		
		if(result.equals(ServiceResult.OK)) {  //등록 성공
			goPage = "redirect:/free/detail.do?boNo=" + freeVO.getBoNo();
		} else {							   //등록 실패
			model.addAttribute("free", freeVO);
			model.addAttribute("status","u");
			goPage = "free/form";
		}
		return goPage;
	}
	
	//delete
	
	@RequestMapping(value="/delete.do" , method = RequestMethod.POST)
	public String freeDelete(int boNo, Model model) {
		String goPage ="";
		ServiceResult result = freeService.deleteFree(boNo);
		
		if(result.equals(ServiceResult.OK)) {
			goPage = "redirect:/free/list.do";
		} else {
			goPage = "redirect:/free/detail.do?boNo=" + boNo;
		}
		return goPage;
	}
}