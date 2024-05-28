package kr.co.tastyroad.restaurant.model.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		String query = "Select * from restaurant where restaurantNo =  ? ";
		
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
}
