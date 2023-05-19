package kr.or.ddit.vo;

import lombok.Data;

@Data
public class NoticeVO {
	private int boNo; 				//공지사항 번호
	private String boTitle;			//공지사항 제목
	private String boWriter;		//공지사항 작성자
	private String boContent;		//공지사항 내용
	private String boDate;			//공지사항 작성일
	private int boHit;				//공지사항 조회수
	
}
