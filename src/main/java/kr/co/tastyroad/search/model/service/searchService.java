package kr.co.tastyroad.search.model.service;

import java.util.ArrayList;

import kr.co.tastyroad.notice.model.dto.noticeDto;
import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;
import kr.co.tastyroad.review.model.dto.ReviewDto;

public interface searchService {

    public ArrayList<noticeDto> searchNotices(String searchText);
    
    public ArrayList<RestaurantDto> searchRestaurants(String searchText);
    
    public ArrayList<ReviewDto> getReviewsRestaurant(int restaurantNo);
    
    
    public ArrayList<String> getTagsForRestaurant(int restaurantNo);
}
