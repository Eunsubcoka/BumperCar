package kr.co.tastyroad.search.model.service;

import java.util.ArrayList;

import kr.co.tastyroad.notice.model.dto.noticeDto;
import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;

public interface searchService {

	public ArrayList<noticeDto> searchNotices(String searchText);
	
	public ArrayList<RestaurantDto> searchRestaurants(String searchText);
}
