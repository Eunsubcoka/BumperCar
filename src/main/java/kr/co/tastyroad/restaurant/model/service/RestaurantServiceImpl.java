package kr.co.tastyroad.restaurant.model.service;

import java.util.ArrayList;

import kr.co.tastyroad.restaurant.model.dao.RestaurantDao;
import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;

public class RestaurantServiceImpl implements RestaurantService {

	RestaurantDao restaurantDao = new RestaurantDao();

	@Override
	public RestaurantDto getRestaurant(int No) {

		return restaurantDao.getRestaurant(No);

	}

	@Override
	public ArrayList<RestaurantDto> getMenuList(int No) {
		return restaurantDao.getMenuList(No);
	}

	@Override
	public float ratings(int No) {
		return restaurantDao.ratings(No);
	}

	@Override
	public ArrayList<RestaurantDto> getRestaurantList(int category) {
		return restaurantDao.getRestaurantList(category);
	}

	@Override
	public int addMenu(ArrayList<RestaurantDto> menu) {
		return restaurantDao.addMenu(menu);
	}

	@Override
	public int addRestaurant(RestaurantDto restaurant) {
		restaurantDao.addRestaurant(restaurant);
		return restaurantDao.addResNo();
	}

	@Override
	public int addTag(ArrayList<RestaurantDto> tag) {
		return restaurantDao.addTag(tag);
	}

	@Override
	public int updateRestaurant(RestaurantDto restaurant) {
		return restaurantDao.updateRestaurant(restaurant);
	}

	@Override
	public int deleteTag(int resNo) {
		return restaurantDao.deleteTag(resNo);
	}

	@Override
	public int deleteMenu(int resNo) {
		return restaurantDao.deleteMenu(resNo);
	}
	@Override
	 public ArrayList<String> getTag(int resNo) {
		return restaurantDao.getTag(resNo);
	}
	@Override
	 public ArrayList<RestaurantDto> getTag(ArrayList<RestaurantDto> resDto) {
		return restaurantDao.getTag(resDto);
	}
	@Override
    public int fileUpload(RestaurantDto resDto) {
	return restaurantDao.fileUpload(resDto);
	}
	@Override
    public ArrayList<RestaurantDto> ratingsList(ArrayList<RestaurantDto> resDto){
    	return restaurantDao.ratingsList(resDto);
    }

}
