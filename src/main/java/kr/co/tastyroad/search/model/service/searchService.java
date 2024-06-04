package kr.co.tastyroad.search.model.service;

import java.util.ArrayList;

import kr.co.tastyroad.notice.model.dto.noticeDto;

public interface searchService {

	public ArrayList<noticeDto> searchNotices(String searchText);
}
