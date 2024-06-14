package kr.co.tastyroad.search.model.service;

import java.util.ArrayList;
import kr.co.tastyroad.notice.model.dto.noticeDto;
import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;
import kr.co.tastyroad.review.model.dto.ReviewDto;
import kr.co.tastyroad.search.model.dao.searchDao;

public class searchServiceImpl implements searchService {
    private searchDao searchDao;

    public searchServiceImpl() {
        searchDao = new searchDao();
    }

    @Override
    public ArrayList<noticeDto> searchNotices(String searchText) {
        return searchDao.searchNotices(searchText);
    }

    @Override
    public ArrayList<RestaurantDto> searchRestaurants(String searchText, String sortOrder, double userLat, double userLon) {
        ArrayList<RestaurantDto> restaurantList = searchDao.searchRestaurants(searchText, sortOrder);

        if ("distance".equals(sortOrder)) {
            for (RestaurantDto restaurant : restaurantList) {
                // 좌표 변환 후 거리 계산
                double distance = calculateDistance(userLat, userLon, restaurant.getLatitude(), restaurant.getLongitude());
                restaurant.setDistance(distance);
            }
            restaurantList.sort((r1, r2) -> Double.compare(r1.getDistance(), r2.getDistance()));
        }

        return restaurantList;
    }

    @Override
    public ArrayList<ReviewDto> getReviewsRestaurant(int restaurantNo) {
        return searchDao.getReviewsRestaurant(restaurantNo);
    }

    @Override
    public ArrayList<String> getTagsForRestaurant(int restaurantNo) {
        return searchDao.getTagsForRestaurant(restaurantNo);
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // 지구의 반지름 (단위: km)
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // 두 좌표 간의 거리 (단위: km)
    }
}
