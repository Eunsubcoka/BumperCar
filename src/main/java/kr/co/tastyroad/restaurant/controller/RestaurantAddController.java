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
 * Servlet implementation class RestaurantAddController
 */
@WebServlet("/restaurantAdd.do")
public class RestaurantAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RestaurantAddController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		int count =1;
		String food = request.getParameter("menu"+count);
		int price = Integer.parseInt(request.getParameter("price"+count));
		String name = request.getParameter("restaurantName");
		String category = request.getParameter("category");
		String phone = request.getParameter("phone");
		String addr = request.getParameter("addr");
		System.out.println(category);
		RestaurantDto restaurant = new RestaurantDto();
		
		restaurant.setRestaurantName(name);
		restaurant.setCategory(category);
		restaurant.setRestaurantPhone(phone);
		restaurant.setLocation(addr);
		
		
		RestaurantServiceImpl resService = new RestaurantServiceImpl();

		int no = resService.addRestaurant(restaurant);
		
		
		ArrayList<RestaurantDto> menu = new ArrayList<RestaurantDto>(); // 메뉴 리스트
		
		
		do {
			RestaurantDto obj = new RestaurantDto();
			obj.setMenu(food);
			obj.setPrice(price);
			obj.setRestaurantNo(no);
			menu.add(obj);
			count++;
			if(request.getParameter("menu"+count) == null) {
				break;
			}
			else {				
				food = request.getParameter("menu"+count);
				price = Integer.parseInt(request.getParameter("price"+count));
			}
			System.out.println(price);
		}
		while(food != null);
		
		resService.addMenu(menu);
		
		ArrayList<RestaurantDto> tag = new ArrayList<RestaurantDto>(); // 메뉴 리스트
		int cnt = 1;
		String tagEle = request.getParameter("tag"+cnt);
		
		do {
			RestaurantDto obj = new RestaurantDto();
			obj.setTag(tagEle);
			obj.setRestaurantNo(no);
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
