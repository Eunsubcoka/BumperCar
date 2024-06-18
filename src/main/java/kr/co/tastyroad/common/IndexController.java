package kr.co.tastyroad.common;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.tastyroad.restaurant.model.dao.RestaurantDao;
import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;

@WebServlet("")
public class IndexController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            RestaurantDao dao = new RestaurantDao();

            List<RestaurantDto> koreanRestaurants = dao.getRestaurantListByCategory(1);
            List<RestaurantDto> japaneseRestaurants = dao.getRestaurantListByCategory(2);
            List<RestaurantDto> chineseRestaurants = dao.getRestaurantListByCategory(3);
            List<RestaurantDto> dessertRestaurants = dao.getRestaurantListByCategory(4);
            List<RestaurantDto> fastFoodRestaurants = dao.getRestaurantListByCategory(5);

            request.setAttribute("koreanRestaurants", koreanRestaurants);
            request.setAttribute("japaneseRestaurants", japaneseRestaurants);
            request.setAttribute("chineseRestaurants", chineseRestaurants);
            request.setAttribute("dessertRestaurants", dessertRestaurants);
            request.setAttribute("fastFoodRestaurants", fastFoodRestaurants);

            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	    doGet(request,response);
    }
    

}
