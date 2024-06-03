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
		System.out.println(category);
		RestaurantServiceImpl resService = new RestaurantServiceImpl();
		RestaurantDto result = new RestaurantDto();
		ArrayList<RestaurantDto> restaurantList = new ArrayList<RestaurantDto>();
		
		restaurantList = resService.getRestaurantList(category);
		
		System.out.println(restaurantList.size());
		
		request.setAttribute("restaurantList", restaurantList);
		
		
		RequestDispatcher view = request.getRequestDispatcher("/views/restaurant/restaurantList.jsp");
		view.forward(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
