package kr.co.tastyroad.search.model.service;

import java.util.ArrayList;

import kr.co.tastyroad.notice.model.dto.noticeDto;
import kr.co.tastyroad.search.model.dao.searchDao;

public class searchServiceImpl implements searchService{
	
    searchDao searchDao;

    public searchServiceImpl() {
        searchDao = new searchDao();
    }


	@Override
	public ArrayList<noticeDto> searchNotices(String searchText) {
        return searchDao.searchNotices(searchText);
    }
}
