package kr.co.tastyroad.notice.model.service;

import kr.co.tastyroad.notice.model.dao.noticeDao;
import kr.co.tastyroad.notice.model.dto.noticeDto;

import java.util.ArrayList;

import kr.co.tastyroad.common.PageInfo;

public class noticeServiceImpl implements noticeService{
	
	noticeDao noticeDao;
	
	public noticeServiceImpl() {
		noticeDao = new noticeDao();
	}
	
	@Override
	public ArrayList<noticeDto> getList(PageInfo pi, String category, String searchText){
		return noticeDao.getList(pi, category, searchText);
	}
	
	@Override
	public int getListCount(String category, String searchText) {
		return noticeDao.getListCount(category, searchText);
	}
	
	@Override
	public int enroll(noticeDto noticeDto) {
		return noticeDao.enroll(noticeDto);
	}
	
	@Override
	public noticeDto getDetail(int noticeNo) {
		noticeDto result = noticeDao.getDetail(noticeNo);
		
		noticeDao.getWriter(result);
		
		int resultView = noticeDao.setView(result.getNoticeNo());
		
		if(resultView ==1) {
			return result;
		}
		
		return null;
	}
	
	@Override
	public noticeDto getEditForm(int noticeNo) {
		noticeDto result = noticeDao.getDetail(noticeNo);
		noticeDao.getWriter(result);
		
		return result;
	}
	
	
	@Override
	public int setEdit(noticeDto noticeDto) {
		return noticeDao.setEdit(noticeDto);
	}
	
	@Override
	public int setDelete(int noticeNo) {
		return noticeDao.setDelete(noticeNo);
	}
	
	@Override
	public noticeDto selectNo(noticeDto noticeDto) {
		return noticeDao.selectNo(noticeDto);
	}
}
