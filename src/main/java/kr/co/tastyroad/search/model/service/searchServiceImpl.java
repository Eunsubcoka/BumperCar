package kr.co.tastyroad.search.model.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import kr.co.tastyroad.notice.model.dto.noticeDto;
import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;
import kr.co.tastyroad.review.model.dto.ReviewDto;
import kr.co.tastyroad.search.model.dao.searchDao;
import org.json.JSONArray;
import org.json.JSONObject;

public class searchServiceImpl implements searchService {
    private searchDao searchDao;
    private final String KAKAO_API_KEY = "597a12321ce91d26c9101324b5955ebd";

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
                double[] coords = getCoordinatesFromAddress(restaurant.getLocation());
                if (coords != null) {
                    double distance = calculateDistance(userLat, userLon, coords[0], coords[1]);
                    restaurant.setDistance(distance);
                } else {
                    restaurant.setDistance(Double.MAX_VALUE); // 거리 계산 실패 시 큰 값 설정
                }
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

    @Override
    public ArrayList<String> getLocationForRestaurant(int restaurantNo) {
        return searchDao.getLocationForRestaurant(restaurantNo);
    }

    private double[] getCoordinatesFromAddress(String address) {
        try {
            String apiURL = "https://dapi.kakao.com/v2/local/search/address.json?query=" + address;
            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "KakaoAK " + KAKAO_API_KEY);
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                JSONObject jsonObject = new JSONObject(response.toString());
                JSONArray documents = jsonObject.getJSONArray("documents");
                if (documents.length() > 0) {
                    JSONObject addressInfo = documents.getJSONObject(0);
                    JSONObject addressDetails = addressInfo.getJSONObject("address");
                    double latitude = addressDetails.getDouble("y");
                    double longitude = addressDetails.getDouble("x");
                    return new double[]{latitude, longitude};
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // 좌표 변환 실패 시 null 반환
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
