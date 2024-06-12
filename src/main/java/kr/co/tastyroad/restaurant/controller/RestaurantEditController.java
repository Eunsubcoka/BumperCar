package kr.co.tastyroad.restaurant.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;
import kr.co.tastyroad.restaurant.model.service.RestaurantServiceImpl;

/**
 * Servlet implementation class RestaurantEditController
 */
@WebServlet("/restaurantEdit.do")
public class RestaurantEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantEditController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
//		int resNo = 14;
		int resNo = Integer.parseInt(request.getParameter("resNo"));
		
		int count =1;
		String food = request.getParameter("menu"+count);
		int price = Integer.parseInt(request.getParameter("price"+count));
		String name = request.getParameter("restaurantName");
		int category = Integer.parseInt(request.getParameter("category"));
		String phone = request.getParameter("phone");
		String addr = request.getParameter("addr");
		RestaurantDto restaurant = new RestaurantDto();
		
		restaurant.setRestaurantName(name);
		restaurant.setCategory(category);
		restaurant.setRestaurantPhone(phone);
		restaurant.setLocation(addr);
		
		
		RestaurantServiceImpl resService = new RestaurantServiceImpl();
		
		resService.deleteMenu(resNo);
		resService.deleteTag(resNo);
		
		resService.updateRestaurant(restaurant);
		
	ArrayList<RestaurantDto> menu = new ArrayList<RestaurantDto>(); // 메뉴 리스트
		
		
		do {
			RestaurantDto obj = new RestaurantDto();
			obj.setMenu(food);
			obj.setPrice(price);
			obj.setRestaurantNo(resNo);
			menu.add(obj);
			count++;
			if(request.getParameter("menu"+count) == null) {
				break;
			}
			else {				
				food = request.getParameter("menu"+count);
				price = Integer.parseInt(request.getParameter("price"+count));
			}
		}
		while(food != null);
		
		resService.addMenu(menu);
		
		ArrayList<RestaurantDto> tag = new ArrayList<RestaurantDto>(); // 메뉴 리스트
		int cnt = 1;
		String tagEle = request.getParameter("tag"+cnt);
		
		do {
			RestaurantDto obj = new RestaurantDto();
			obj.setTag(tagEle);
			obj.setRestaurantNo(resNo);
			tag.add(obj);
			cnt++;
			if(request.getParameter("tag"+cnt) == null) {
				break;
			}
			else {				
				tagEle = request.getParameter("tag"+cnt);
			}
		}
		while(tagEle != null);
		resService.addTag(tag);
		

		
		response.sendRedirect("/index.jsp");
		
		
		
		
		
	}

}
