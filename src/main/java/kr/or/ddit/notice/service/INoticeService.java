package kr.or.ddit.notice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.vo.NoticeVO;
import kr.or.ddit.vo.PaginationInfoVO;


public interface INoticeService {

	public ServiceResult insertNotice(NoticeVO noticeVO);

	public List<NoticeVO> selectNoticeList();

	public NoticeVO selectNotice(int boNo);

	public ServiceResult updateNotice(NoticeVO noticeVO);

	public ServiceResult deleteNotice(int boNo);

	public int selectNoticeCount(PaginationInfoVO<NoticeVO> pagingVO);

	public List<NoticeVO> selectNoticeList2(PaginationInfoVO<NoticeVO> pagingVO);

}
