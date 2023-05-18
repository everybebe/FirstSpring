package kr.or.ddit.board.web;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.PaginationInfoVO;

@Controller
@RequestMapping("/board")
public class BoardRetrieveController {

	@Inject
	private IBoardService boardService;
	
	@RequestMapping(value="/list.do")
	public String boardList(
			
			@RequestParam(name="page", required = false, defaultValue = "1") int currentPage,  //defaultValue 페이지 기본 디폴트 값
			@RequestParam(required = false, defaultValue = "title") String searchType,
			@RequestParam(required = false) String searchWord,
			Model model) {
		//일반적인 게시판 목록 조회(방법1) - 페이징이 구현되어 있지 않다
		//List<BoardVO> boardList = boardService.selectBoardList();
		//model.addAttribute("boardList", boardList);
	
		
		//페이징 및 검색이 적용된 목록 조회(방법2)
		PaginationInfoVO<BoardVO> pagingVO = new PaginationInfoVO<BoardVO>();
		
		pagingVO.setCurrentPage(currentPage);
		// 목록 총 게시글 수 가져오기
		int totalRecord = boardService.selectBoardCount(pagingVO);
		pagingVO.setTotalRecord(totalRecord);
		List<BoardVO> dataList = boardService.selectBoardList2(pagingVO);
		pagingVO.setDataList(dataList);
		model.addAttribute("pagingVO", pagingVO);
		return "board/list";
	}
	
	@RequestMapping(value="/detail.do", method = RequestMethod.GET)
	public String boardDetail(int boNo, Model model) {
		BoardVO boardVO = boardService.selectBoard(boNo);
		model.addAttribute("board", boardVO);
		return "board/view";
	}
}
