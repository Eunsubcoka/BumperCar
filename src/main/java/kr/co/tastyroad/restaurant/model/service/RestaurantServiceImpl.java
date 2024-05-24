package kr.co.tastyroad.restaurant.model.service;

import kr.co.tastyroad.restaurant.model.dao.RestaurantDao;
import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;

public class RestaurantServiceImpl implements RestaurantService{
	
	RestaurantDao restaurantDao;  
	
	public RestaurantDto getRestaurant(int No) {
		
		return restaurantDao.getRestaurant(No);
				
	}
}
