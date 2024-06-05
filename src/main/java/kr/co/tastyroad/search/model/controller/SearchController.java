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
import kr.co.tastyroad.notice.model.service.noticeServiceImpl;
import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;
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
        
        searchServiceImpl searchService = new searchServiceImpl();
        ArrayList<noticeDto> noticeList = searchService.searchNotices(searchText);
        ArrayList<RestaurantDto> restaurantList = searchService.searchRestaurants(searchText);

        RestaurantServiceImpl resService = new RestaurantServiceImpl();
        
        // 각 레스토랑의 상세 정보를 가져와 별점을 설정합니다.
        HashMap<Integer, Float> ratingsMap = new HashMap<>();
        for (RestaurantDto restaurant : restaurantList) {
            int resNo = restaurant.getRestaurantNo();
            float ratings = resService.ratings(resNo);
            ratingsMap.put(resNo, ratings);
        }
        
        request.setAttribute("searchText", searchText);
        request.setAttribute("noticeList", noticeList);
        request.setAttribute("restaurantList", restaurantList);
        request.setAttribute("ratingsMap", ratingsMap);
        
        RequestDispatcher view = request.getRequestDispatcher("/views/search/search_main.jsp");
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

