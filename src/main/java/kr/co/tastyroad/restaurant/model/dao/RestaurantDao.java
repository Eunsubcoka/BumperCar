package kr.co.tastyroad.restaurant.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.co.tastyroad.common.DatabaseConnection;
import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;


public class RestaurantDao {
	
	
	private Connection con;
	private DatabaseConnection dc;
	private PreparedStatement pstmt;

	public RestaurantDao() {
		dc = new DatabaseConnection();
		con = dc.connDB();
	}

	public RestaurantDto getRestaurant(int No) {
		RestaurantDto result = new RestaurantDto();
		String query = "Select * from restaurant "
				+ " where restaurantNo = ?";
		
		try {
			System.out.println("여기는 겟레스 실행문입니다.");
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, No);
			
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String category = rs.getString("category");
				String location = rs.getString("location");
				String phone = rs.getString("restaurantPhone");
				String name = rs.getString("restaurantName");
				
				result.setRestaurantNo(No);
				result.setCategory(category);
				result.setLocation(location);
				result.setRestaurantPhone(phone);
				result.setRestaurantName(name);
				
				return result;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	// 리뷰 리스트 로직
    public ArrayList<RestaurantDto> getMenuList(int No) {
    	ArrayList<RestaurantDto> result = new ArrayList<RestaurantDto>();
    	System.out.println(No);
    
   
    String query = "select r.restaurantNo,m.foodNo,m.foodName,m.price from restaurant r "
                 + "JOIN menu m ON m.restaurantNo = r.RESTAURANTNO where r.restaurantNo = ?";
    
    try {
		pstmt = con.prepareStatement(query);
		pstmt.setInt(1, No);
		
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			RestaurantDto menu = new RestaurantDto();
			String foodName = rs.getString("foodName");
			int foodNo = rs.getInt("foodNo");
			int price = rs.getInt("price");
			menu.setRestaurantNo(No);
			menu.setMenu(foodName);
			menu.setPrice(price);
			menu.setFoodNo(foodNo);
			result.add(menu) ;
		}
        return result;
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return result;
    }
	
	
}
