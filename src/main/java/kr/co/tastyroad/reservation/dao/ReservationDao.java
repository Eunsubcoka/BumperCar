package kr.co.tastyroad.reservation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.tastyroad.common.DatabaseConnection;
import kr.co.tastyroad.reservation.dto.ReservationDto;
import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;

public class ReservationDao {
	
	private Connection con;
	private DatabaseConnection dc;
	private PreparedStatement pstmt;

	public ReservationDao() {
		dc = new DatabaseConnection();
		con = dc.connDB();
	}
	
	public int resEnroll(ReservationDto resDto) {
		int result = 0;
		String query = "Insert into reservation "
				+ "values (reservationSeq.nextval,?,?,?,default,default,?)";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, resDto.getHeadCount());
			pstmt.setString(2, resDto.getDate());
			pstmt.setString(3, resDto.getPhone());
			pstmt.setInt(4, resDto.getUserNo());
			
			result = pstmt.executeUpdate();
			return result;
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
		return result;
	}

	

}
