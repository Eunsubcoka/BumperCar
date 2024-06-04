package kr.co.tastyroad.notice.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.co.tastyroad.common.PageInfo;
import kr.co.tastyroad.notice.model.dto.noticeDto;



public interface noticeService {
	//공지글 리스트 
	public ArrayList<noticeDto> getList(PageInfo pi, String category, String searchText);
	//전체 공지글
	public int getListCount(String category, String searchText);
	//공지글 등록하기
	public int enroll(noticeDto noticeDto);
	//공지글 상세보기
	public noticeDto getDetail(int noticeNo);
	//공지글 수정폼
	public noticeDto getEditForm(int noticeNo);
	//공지글 수정
	public int setEdit(noticeDto noticeDto);
	//공지글 삭제
	public int setDelete(int noticeNo);
	//게시글 번호 검색
	public noticeDto selectNo(noticeDto noticeDto);
	
	public int fileUpload(noticeDto noticeDto);
	
	public void getFileName(noticeDto result);
	
	public int setFileDelete(int noticeNo);
	
	public boolean deleteNotice(int noticeNo);
	
	public noticeDto getLatestNotice();
	
	public ArrayList<noticeDto> getNoticeList();
	
	public ArrayList<noticeDto> getLatestNotices(int i);
	
	public ArrayList<noticeDto> searchNotices(String searchText);
}
