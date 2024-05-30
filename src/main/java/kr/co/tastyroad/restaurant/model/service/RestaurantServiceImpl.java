package kr.co.tastyroad.restaurant.model.service;

import java.util.ArrayList;

import kr.co.tastyroad.restaurant.model.dao.RestaurantDao;
import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;

public class RestaurantServiceImpl implements RestaurantService{
	
	RestaurantDao restaurantDao = new RestaurantDao();
	
	
	public RestaurantDto getRestaurant(int No) {
		
		return restaurantDao.getRestaurant(No);
				
	}
	public ArrayList<RestaurantDto> getMenuList(int No){
		return restaurantDao.getMenuList(No);
	}
}
