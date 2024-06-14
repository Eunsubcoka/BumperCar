package kr.co.tastyroad.search.model.service;

import java.util.ArrayList;
import kr.co.tastyroad.notice.model.dto.noticeDto;
import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;
import kr.co.tastyroad.review.model.dto.ReviewDto;

public interface searchService {
    ArrayList<noticeDto> searchNotices(String searchText);
    public ArrayList<RestaurantDto> searchRestaurants(String searchText, String sortOrder, double userLat, double userLon);
    ArrayList<ReviewDto> getReviewsRestaurant(int restaurantNo);
    ArrayList<String> getTagsForRestaurant(int restaurantNo);
}
