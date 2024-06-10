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
	public float ratings(int No) {
		return restaurantDao.ratings(No);
	}
	 public ArrayList<RestaurantDto> getRestaurantList(int category){
		 return restaurantDao.getRestaurantList(category);
	 }
	 public int addMenu(ArrayList<RestaurantDto> menu) {
	    	return restaurantDao.addMenu(menu);
	  }
	 
	 public int addRestaurant(RestaurantDto restaurant) {
		 	restaurantDao.addRestaurant(restaurant);
		 	return restaurantDao.addResNo();
	 }
	    public int addTag(ArrayList<RestaurantDto> tag) {
	    	return restaurantDao.addTag(tag);
	    }
	 
}
