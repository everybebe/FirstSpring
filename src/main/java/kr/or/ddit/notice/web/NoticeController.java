package kr.or.ddit.notice.web;

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
import kr.or.ddit.notice.service.INoticeService;
import kr.or.ddit.vo.NoticeVO;
import kr.or.ddit.vo.PaginationInfoVO;

@Controller
@RequestMapping("/notice")
public class NoticeController {

	@Inject
	private INoticeService noticeService;
	
	//Retrieve
	@RequestMapping(value="/list.do")
	public String noticeList(
			@RequestParam(name="page", required = false, defaultValue ="1") int currentPage,
			@RequestParam(required = false, defaultValue = "title") String searchType,
			@RequestParam(required = false) String searchWord,
			Model model) {
		
		//일반적인 게시판 목록 조회(방법1) - 페이징이 구현되어 있지 않다
		//List<NoticeVO> noticeList = noticeService.selectNoticeList();
		//model.addAttribute("noticeList", noticeList);
		//return "notice/list";
		
		PaginationInfoVO<NoticeVO> pagingVO = new PaginationInfoVO<NoticeVO>();
		
		
		if(StringUtils.isNoneBlank(searchWord)) {
			pagingVO.setSearchType(searchType);
			pagingVO.setSerchWord(searchWord);
			model.addAttribute("searchType", searchType);
			model.addAttribute("searchWord", searchWord);
		}
		
		pagingVO.setCurrentPage(currentPage);
		
		int totalRecord = noticeService.selectNoticeCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		List<NoticeVO> dataList = noticeService.selectNoticeList2(pagingVO);
		pagingVO.setDataList(dataList);
		model.addAttribute("pagingVO", pagingVO);
		
		return "notice/list";
	}	
	
	@RequestMapping(value ="/detail.do", method = RequestMethod.GET)
	public String noticeDetail(int boNo, Model model) {
		NoticeVO noticeVO = noticeService.selectNotice(boNo);
		model.addAttribute("notice",noticeVO);
		return "notice/view";
	}
	
	//insert	
	@RequestMapping(value="/form.do", method = RequestMethod.GET)
	public String noticeForm() {
		return "notice/form";
	}
	
	@RequestMapping(value="/insert.do", method = RequestMethod.POST)
	public String NoticeInsert(NoticeVO noticeVO, Model model) {
		String goPage = "";
		Map<String, String> errors = new HashMap<String, String>();
		
		if(StringUtils.isAllBlank(noticeVO.getBoTitle())) {
			errors.put("boTitle","제목을 입력해주세요!");
		}
		if(StringUtils.isAllBlank(noticeVO.getBoContent())) {
			errors.put("boContent","내용을 입력해주세요!");
		}
		
		
		if(errors.size() > 0) {
			model.addAttribute("error",errors);
			model.addAttribute("notice",noticeVO);
			goPage = "notice/form";
		} else {
			noticeVO.setBoWriter("깅밍정");
			ServiceResult result = noticeService.insertNotice(noticeVO);
			if(result.equals(ServiceResult.OK)) {  //등록 성공
				goPage = "redirect:/notice/detail.do?boNo=" + noticeVO.getBoNo();
			} else {							   //등록 실패
				errors.put("msg", "서비스에러! 다시 시도해주세요!");
				model.addAttribute("errors", errors);
				goPage = "notice/form";
			}
		}
		return goPage;
	}
	
	
	//update
	@RequestMapping(value="/update.do", method=RequestMethod.GET)
	public String noticeUpdateForm(int boNo, Model model) {
		NoticeVO noticeVO = noticeService.selectNotice(boNo);
		model.addAttribute("notice", noticeVO);
		model.addAttribute("status","u");
		return "notice/form";
	}
	
	@RequestMapping(value="/update.do", method = RequestMethod.POST)
	public String noticeUpdate(NoticeVO noticeVO, Model model) {
		String goPage = "";
		ServiceResult result = noticeService.updateNotice(noticeVO);
		
		if(result.equals(ServiceResult.OK)) {  //등록 성공
			goPage = "redirect:/notice/detail.do?boNo=" + noticeVO.getBoNo();
		} else {							   //등록 실패
			model.addAttribute("notice", noticeVO);
			model.addAttribute("status","u");
			goPage = "notice/form";
		}
		return goPage;
	}
	
	//delete
	
	@RequestMapping(value="/delete.do" , method = RequestMethod.POST)
	public String noticeDelete(int boNo, Model model) {
		String goPage ="";
		ServiceResult result = noticeService.deleteNotice(boNo);
		
		if(result.equals(ServiceResult.OK)) {
			goPage = "redirect:/notice/list.do";
		} else {
			goPage = "redirect:/notice/detail.do?boNo=" + boNo;
		}
		return goPage;
	}
}


