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
 * Servlet implementation class RestaurantCategoryController
 */
@WebServlet("/category.do")
public class RestaurantCategoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantCategoryController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int category = Integer.parseInt(request.getParameter("category"));
		ReviewServiceImpl reviewService = new ReviewServiceImpl();
		RestaurantServiceImpl resService = new RestaurantServiceImpl();
		
		
		RestaurantDto result = new RestaurantDto();
		ArrayList<RestaurantDto> restaurantList = new ArrayList<RestaurantDto>();
		ArrayList<RestaurantDto> tag = new ArrayList<RestaurantDto>();
		String seleType = request.getParameter("seleType");
		
		
		restaurantList = resService.getRestaurantList(category,seleType);
		
		resService.ratingsList(restaurantList);
		tag = resService.getTag(restaurantList);
		
		request.setAttribute("restaurantList", restaurantList);
		request.setAttribute("category", category);
		request.setAttribute("seleType", seleType);
		request.setAttribute("tag", tag);
//		request.setAttribute("reviewList", reviewList);
		
		RequestDispatcher view = request.getRequestDispatcher("/views/restaurant/restaurantList.jsp");
		view.forward(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
