package kr.co.tastyroad.search.model.service;

import java.util.ArrayList;

import kr.co.tastyroad.notice.model.dto.noticeDto;
import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;
import kr.co.tastyroad.review.model.dto.ReviewDto;
import kr.co.tastyroad.search.model.dao.searchDao;

public class searchServiceImpl implements searchService {
    searchDao searchDao;

    public searchServiceImpl() {
        searchDao = new searchDao();
    }

    @Override
    public ArrayList<noticeDto> searchNotices(String searchText) {
        return searchDao.searchNotices(searchText);
    }

    @Override
    public ArrayList<RestaurantDto> searchRestaurants(String searchText) {
        return searchDao.searchRestaurants(searchText);
    }

    @Override
    public ArrayList<ReviewDto> getReviewsRestaurant(int restaurantNo) {
        return searchDao.getReviewsRestaurant(restaurantNo);
    }


    @Override
    public ArrayList<String> getTagsForRestaurant(int restaurantNo) {
        return searchDao.getTagsForRestaurant(restaurantNo);
    }
}
