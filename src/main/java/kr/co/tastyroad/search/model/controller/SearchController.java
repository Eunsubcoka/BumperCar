package kr.co.tastyroad.search.model.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.tastyroad.notice.model.dto.noticeDto;
import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;
import kr.co.tastyroad.review.model.dto.ReviewDto;
import kr.co.tastyroad.restaurant.model.service.RestaurantServiceImpl;
import kr.co.tastyroad.search.model.service.searchServiceImpl;

@WebServlet("/search.do")
public class SearchController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SearchController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String searchText = request.getParameter("search-text");
            String tag = request.getParameter("tag");
            String sortOrder = request.getParameter("sortOrder");
            String location = request.getParameter("location");

            // 위도와 경도를 파라미터로 받기
            String latParam = request.getParameter("lat");
            String lonParam = request.getParameter("lon");
            double userLat = 0;
            double userLon = 0;

            if (latParam != null && lonParam != null) {
                try {
                    userLat = Double.parseDouble(latParam.trim());
                    userLon = Double.parseDouble(lonParam.trim());
                } catch (NumberFormatException e) {
                    // 유효하지 않은 위도, 경도 값 처리 (기본값 사용)
                }
            }

            // sortOrder가 null 또는 비어 있는 경우 기본값 설정
            if (sortOrder == null || sortOrder.trim().isEmpty()) {
                sortOrder = "latest";
            }

            int cpage = 1;
            if (request.getParameter("cpage") != null) {
                cpage = Integer.parseInt(request.getParameter("cpage"));
            }

            searchServiceImpl searchService = new searchServiceImpl();
            ArrayList<noticeDto> noticeList = searchService.searchNotices(searchText);
            int totalNotices = noticeList.size();  // 총 공지사항 개수 계산
            ArrayList<RestaurantDto> allRestaurantList = new ArrayList<>();
            ArrayList<RestaurantDto> paginatedRestaurantList = new ArrayList<>();

            if (searchText != null && !searchText.isEmpty()) {
                allRestaurantList = searchService.searchRestaurants(searchText, sortOrder, userLat, userLon);
            }

            // 페이징 로직
            int startIndex = (cpage - 1) * 5;
            int endIndexNotices = Math.min(startIndex + 5, totalNotices);
            int endIndexRestaurants = Math.min(startIndex + 1000, allRestaurantList.size());

            if (startIndex < totalNotices) {
                ArrayList<noticeDto> paginatedNoticeList = new ArrayList<>(noticeList.subList(startIndex, endIndexNotices));
                request.setAttribute("noticeList", paginatedNoticeList);
            } else {
                request.setAttribute("noticeList", new ArrayList<noticeDto>());
            }

            if (startIndex < allRestaurantList.size()) {
                paginatedRestaurantList = new ArrayList<>(allRestaurantList.subList(startIndex, endIndexRestaurants));
                request.setAttribute("restaurantList", paginatedRestaurantList);
            } else {
                request.setAttribute("restaurantList", new ArrayList<RestaurantDto>());
            }

            // 기타 설정
            RestaurantServiceImpl resService = new RestaurantServiceImpl();
            HashMap<Integer, Float> ratingsMap = new HashMap<>();
            HashMap<Integer, ArrayList<ReviewDto>> top3ReviewsMap = new HashMap<>();
            HashMap<Integer, ArrayList<String>> tagsMap = new HashMap<>();
            HashMap<Integer, ArrayList<String>> locationMap = new HashMap<>();
            ArrayList<String> addressList = new ArrayList<>();  // 주소 리스트 추가

            for (RestaurantDto restaurant : allRestaurantList) {
                int resNo = restaurant.getRestaurantNo();
                float ratings = resService.ratings(resNo);
                ratingsMap.put(resNo, ratings);

                ArrayList<ReviewDto> top3Reviews = searchService.getReviewsRestaurant(resNo);
                top3ReviewsMap.put(resNo, top3Reviews);

                ArrayList<String> tags = searchService.getTagsForRestaurant(resNo);
                tagsMap.put(resNo, tags);
                
                ArrayList<String> locations = searchService.getLocationForRestaurant(resNo);
                locationMap.put(resNo, locations);
                
                addressList.add(restaurant.getLocation());  // 주소 추가
            }

            request.setAttribute("searchText", searchText);
            request.setAttribute("tag", tag);
            request.setAttribute("location", location);
            request.setAttribute("sortOrder", sortOrder); // 정렬 기준 설정
            request.setAttribute("ratingsMap", ratingsMap);
            request.setAttribute("top3ReviewsMap", top3ReviewsMap);
            request.setAttribute("tagsMap", tagsMap);
            request.setAttribute("totalNotices", totalNotices);  // 총 공지사항 개수 설정
            request.setAttribute("totalRestaurants", allRestaurantList.size());
            request.setAttribute("addressList", addressList);  // 주소 리스트 설정
            request.setAttribute("cpage", cpage);
            request.setAttribute("allRestaurantList", allRestaurantList); // 모든 레스토랑 리스트 전달

            RequestDispatcher view = request.getRequestDispatcher("/views/search/search_main.jsp");
            view.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/views/error.html");
        }
    }
}
