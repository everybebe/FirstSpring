package kr.or.ddit.notice.dao;

import java.util.List;

import kr.or.ddit.ServiceResult;
import kr.or.ddit.vo.NoticeVO;
import kr.or.ddit.vo.PaginationInfoVO;

public interface INoticeDAO {

   public int insertNotice(NoticeVO noticeVO);

   public List<NoticeVO> selectNoticeList();

   public NoticeVO selectNotice(int boNo);

   public int updateNotice(NoticeVO noticeVO);

   public void incrementHit(int boNo);

   public int deleteNotice(int boNo);

   public int selectNoticeCount(PaginationInfoVO<NoticeVO> pagingVO);

   public List<NoticeVO> selectNoticeList2(PaginationInfoVO<NoticeVO> pagingVO);




}

