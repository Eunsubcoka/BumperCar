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

/**
 * Servlet implementation class RestaurantDetailControler
 */
@WebServlet("/restaurantDetail.do")
public class RestaurantDetailControler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantDetailControler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int resNo = Integer.parseInt(request.getParameter("restaurantId"));
		
		RestaurantServiceImpl resService = new RestaurantServiceImpl();
		RestaurantDto result = new RestaurantDto();
		result = resService.getRestaurant(resNo);
		
		ArrayList<RestaurantDto> menuList = new ArrayList<RestaurantDto>();
		menuList = resService.getMenuList(resNo);
		
        ReviewServiceImpl reviewService = new ReviewServiceImpl();
		
		ArrayList<ReviewDto> list = new ArrayList<ReviewDto>(); 
		list = reviewService.getReviewList(); // 게시글 리스트
		
		float ratings = resService.ratings(resNo);
		// uploadList = 각각의 게시글에 대한 파일명, 게시글 번호
		ArrayList<ReviewDto> fileList = new ArrayList<ReviewDto>(); 
		fileList = reviewService.uploadListOnce(); // 게시글 리스트
		
		request.setAttribute("list", list);
		request.setAttribute("fileList", fileList);
		
		request.setAttribute("ratings", ratings);
		request.setAttribute("result", result);
		request.setAttribute("menuList", menuList);
		
		RequestDispatcher view = request.getRequestDispatcher("/views/restaurant/restaurantDetail.jsp");
		view.forward(request,response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
