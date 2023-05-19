package kr.or.ddit.main.dao;

import java.util.List;

import javax.inject.Inject;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.BoardVO;
import kr.or.ddit.vo.FreeVO;
import kr.or.ddit.vo.NoticeVO;

@Repository
public class MainDAOImpl implements IMainDAO{

	@Inject
	private SqlSessionTemplate SqlSession;
	
	
	@Override
	public List<BoardVO> selectBoardList() {
		return SqlSession.selectList("Main.selectBoardList");
	}


	@Override
	public List<NoticeVO> selectNoticeList() {
		return SqlSession.selectList("Main.selectNoticeList");
	}


	@Override
	public List<FreeVO> selectFreeList() {
		return SqlSession.selectList("Main.selectFreeList");
	}


	@Override
	public int boardCount() {
		return SqlSession.selectOne("Main.boardCount");
	}


	@Override
	public int freeCount() {
		return SqlSession.selectOne("Main.freeCount");
	}


	@Override
	public int noticeCount() {
		return SqlSession.selectOne("Main.noticeCount");
	}

}
