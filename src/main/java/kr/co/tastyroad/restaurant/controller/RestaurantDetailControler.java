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
