package kr.co.tastyroad.restaurant.model.service;

import java.util.ArrayList;

import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;

public interface RestaurantService {

	public RestaurantDto getRestaurant(int No);
	public ArrayList<RestaurantDto> getMenuList(int No);
	public float ratings(int No);
	public ArrayList<RestaurantDto> getRestaurantList(int category);
	
}
