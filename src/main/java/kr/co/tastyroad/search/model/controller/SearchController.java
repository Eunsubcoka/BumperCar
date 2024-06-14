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

            if (sortOrder == null || sortOrder.trim().isEmpty()) {
                sortOrder = "latest"; // 기본 정렬 기준을 최신순으로 설정
            }

            int cpage = 1;
            if (request.getParameter("cpage") != null) {
                cpage = Integer.parseInt(request.getParameter("cpage"));
            }

            searchServiceImpl searchService = new searchServiceImpl();
            ArrayList<noticeDto> noticeList = searchService.searchNotices(searchText);
            ArrayList<RestaurantDto> allRestaurantList = new ArrayList<>();
            ArrayList<RestaurantDto> paginatedRestaurantList = new ArrayList<>();

            if (searchText != null && !searchText.isEmpty()) {
                allRestaurantList = searchService.searchRestaurants(searchText, sortOrder);
            }

            // 페이징 로직
            int startIndex = (cpage - 1) * 5;
            int endIndexNotices = Math.min(startIndex + 5, noticeList.size());
            int endIndexRestaurants = Math.min(startIndex + 5, allRestaurantList.size());

            if (startIndex < noticeList.size()) {
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
            ArrayList<String> addressList = new ArrayList<>();  // 주소 리스트 추가

            for (RestaurantDto restaurant : allRestaurantList) {
                int resNo = restaurant.getRestaurantNo();
                float ratings = resService.ratings(resNo);
                ratingsMap.put(resNo, ratings);

                ArrayList<ReviewDto> top3Reviews = searchService.getReviewsRestaurant(resNo);
                top3ReviewsMap.put(resNo, top3Reviews);

                ArrayList<String> tags = searchService.getTagsForRestaurant(resNo);
                tagsMap.put(resNo, tags);

                addressList.add(restaurant.getLocation());  // 주소 추가
            }

            request.setAttribute("searchText", searchText);
            request.setAttribute("tag", tag);
            request.setAttribute("sortOrder", sortOrder); // 정렬 기준 설정
            request.setAttribute("ratingsMap", ratingsMap);
            request.setAttribute("top3ReviewsMap", top3ReviewsMap);
            request.setAttribute("tagsMap", tagsMap);
            request.setAttribute("totalNotices", noticeList.size());
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
