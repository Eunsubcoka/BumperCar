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
        String searchText = request.getParameter("search-text");
        String tag = request.getParameter("tag");
        int cpage = 1;

        if (request.getParameter("cpage") != null) {
            cpage = Integer.parseInt(request.getParameter("cpage"));
        }

        searchServiceImpl searchService = new searchServiceImpl();
        ArrayList<noticeDto> noticeList = searchService.searchNotices(searchText);
        ArrayList<RestaurantDto> restaurantList = new ArrayList<>();

        if (searchText != null && !searchText.isEmpty()) {
            restaurantList = searchService.searchRestaurants(searchText);
        }

        int startIndex = (cpage - 1) * 5;
        int endIndexNotices = Math.min(startIndex + 5, noticeList.size());
        int endIndexRestaurants = Math.min(startIndex + 5, restaurantList.size());

       
//        기존에 5개만 출력 / 5개보다 많을때 버튼 클릭시 이후 인덱스 출력 
        if (startIndex < noticeList.size()) {
            ArrayList<noticeDto> paginatedNoticeList = new ArrayList<>(noticeList.subList(startIndex, endIndexNotices));
            request.setAttribute("noticeList", paginatedNoticeList);
        } else {
            request.setAttribute("noticeList", new ArrayList<noticeDto>());
        }

        if (startIndex < restaurantList.size()) {
            ArrayList<RestaurantDto> paginatedRestaurantList = new ArrayList<>(restaurantList.subList(startIndex, endIndexRestaurants));
            request.setAttribute("restaurantList", paginatedRestaurantList);
        } else {
            request.setAttribute("restaurantList", new ArrayList<RestaurantDto>());
        }

        RestaurantServiceImpl resService = new RestaurantServiceImpl();

        HashMap<Integer, Float> ratingsMap = new HashMap<>();
        HashMap<Integer, ArrayList<ReviewDto>> top3ReviewsMap = new HashMap<>();
        HashMap<Integer, ArrayList<String>> tagsMap = new HashMap<>();
        ArrayList<String> addressList = new ArrayList<>();  // 주소 리스트 추가

        for (RestaurantDto restaurant : restaurantList) {
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
        request.setAttribute("ratingsMap", ratingsMap);
        request.setAttribute("top3ReviewsMap", top3ReviewsMap);
        request.setAttribute("tagsMap", tagsMap);
        request.setAttribute("totalNotices", noticeList.size());
        request.setAttribute("totalRestaurants", restaurantList.size());
        request.setAttribute("addressList", addressList);  // 주소 리스트 설정
        request.setAttribute("cpage", cpage);

        RequestDispatcher view = request.getRequestDispatcher("/views/search/search_main.jsp");
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
