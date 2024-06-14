package kr.co.tastyroad.restaurant.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;
import kr.co.tastyroad.restaurant.model.service.RestaurantServiceImpl;
import kr.co.tastyroad.review.model.dto.ReviewDto;
import kr.co.tastyroad.review.model.service.ReviewServiceImpl;

@WebServlet("/category.do")
public class RestaurantCategoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RestaurantCategoryController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int resNo = Integer.parseInt(request.getParameter("restaurantId"));
		
		RestaurantServiceImpl resService = new RestaurantServiceImpl();
		RestaurantDto result = resService.getRestaurant(resNo);
		ArrayList<RestaurantDto> menuList = resService.getMenuList(resNo);
        ReviewServiceImpl reviewService = new ReviewServiceImpl();
		ArrayList<ReviewDto> list = reviewService.getReviewList(resNo);
		ArrayList<String> tag = resService.getTag(resNo);
		float ratings = resService.ratings(resNo);
		ArrayList<ReviewDto> fileList = reviewService.uploadListOnce();
		ArrayList<String> imgList = resService.getImg(resNo);
		
		
		request.setAttribute("imgList", imgList);
		request.setAttribute("list", list);
		request.setAttribute("fileList", fileList);
		request.setAttribute("resNo", resNo);
		request.setAttribute("ratings", ratings);
		request.setAttribute("result", result);
		request.setAttribute("tag", tag);
		request.setAttribute("menuList", menuList);
		
		RequestDispatcher view = request.getRequestDispatcher("/views/restaurant/restaurantDetail.jsp");
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}