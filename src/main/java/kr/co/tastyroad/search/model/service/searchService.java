package kr.co.tastyroad.search.model.service;

import java.util.ArrayList;
import kr.co.tastyroad.notice.model.dto.noticeDto;
import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;
import kr.co.tastyroad.review.model.dto.ReviewDto;

public interface searchService {
    ArrayList<noticeDto> searchNotices(String searchText);
    ArrayList<RestaurantDto> searchRestaurants(String searchText, String sortOrder);
    ArrayList<ReviewDto> getReviewsRestaurant(int restaurantNo);
    ArrayList<String> getTagsForRestaurant(int restaurantNo);
}
