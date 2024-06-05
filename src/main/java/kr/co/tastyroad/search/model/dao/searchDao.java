package kr.co.tastyroad.search.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import kr.co.tastyroad.common.DatabaseConnection;
import kr.co.tastyroad.notice.model.dto.noticeDto;
import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;

public class searchDao {
	private Connection con;
	private DatabaseConnection dc;
	private PreparedStatement pstmt;

	public searchDao() {
		dc = new DatabaseConnection();
		con = dc.connDB();
	}
	
	public ArrayList<noticeDto> searchNotices(String searchText) {
        ArrayList<noticeDto> noticeList = new ArrayList<>();
        String query = "SELECT * FROM notice WHERE noticeTitle LIKE ? ORDER BY noticeDate DESC FETCH FIRST 5 ROWS ONLY";
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, "%" + searchText + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                noticeDto notice = new noticeDto();
                String date = "";
				try {
					SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
					date = targetFormat.format(originalFormat.parse(rs.getString("noticeDate")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
                
                notice.setNoticeNo(rs.getInt("noticeNo"));
                notice.setNoticeTitle(rs.getString("noticeTitle"));
                notice.setNoticeContent(rs.getString("noticeContent"));
                notice.setNoticeView(rs.getInt("noticeView"));
                notice.setNoticeDate(date);
                notice.setUserNo(rs.getInt("user_no"));
                noticeList.add(notice);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return noticeList;
    }

	public ArrayList<RestaurantDto> searchRestaurants(String searchText) {
		ArrayList<RestaurantDto> restaurantList = new ArrayList<>();
		String query = "SELECT * FROM restaurant WHERE restaurantName LIKE ? OR category LIKE ? ORDER BY restaurantNo DESC FETCH FIRST 5 ROWS ONLY";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "%" + searchText + "%");
			pstmt.setString(2, "%" + searchText + "%");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				RestaurantDto restaurant = new RestaurantDto();
                restaurant.setRestaurantName(rs.getString("restaurantName"));
                restaurant.setRestaurantNo(rs.getInt("restaurantNo"));
                restaurant.setCategory(rs.getString("category"));
                restaurant.setLocation(rs.getString("location"));
				restaurantList.add(restaurant);
			}
			rs.close();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return restaurantList;
	}

}
