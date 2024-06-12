package kr.co.tastyroad.search.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.co.tastyroad.common.DatabaseConnection;
import kr.co.tastyroad.notice.model.dto.noticeDto;
import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;
import kr.co.tastyroad.review.model.dto.ReviewDto;
import kr.co.tastyroad.search.model.dto.searchDto;

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
		String query = "SELECT * FROM notice WHERE noticeTitle LIKE ? OR noticeContent LIKE ? ORDER BY noticeDate DESC";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "%" + searchText + "%");
			pstmt.setString(2, "%" + searchText + "%");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				noticeDto notice = new noticeDto();
				notice.setNoticeNo(rs.getInt("noticeNo"));
				notice.setNoticeTitle(rs.getString("noticeTitle"));
				notice.setNoticeContent(rs.getString("noticeContent"));
				notice.setNoticeView(rs.getInt("noticeView"));
				notice.setNoticeDate(rs.getString("noticeDate"));
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
	    String query = "SELECT r.restaurantNo, r.restaurantName, r.category, r.location, ri.imgName, "
	                 + "LISTAGG(t.tag, ',') WITHIN GROUP (ORDER BY t.tag) AS tags "
	                 + "FROM restaurant r "
	                 + "LEFT JOIN res_tag t ON r.restaurantNo = t.restaurantNo "
	                 + "LEFT JOIN res_img ri ON ri.restaurantNo = r.restaurantNo "
	                 + "WHERE r.restaurantName LIKE ? OR r.category LIKE ? OR t.tag LIKE ? OR r.location LIKE ? "
	                 + "GROUP BY r.restaurantNo, r.restaurantName, r.category, r.location, ri.imgName "
	                 + "ORDER BY r.restaurantNo asc";

	    try {
	        pstmt = con.prepareStatement(query);
	        pstmt.setString(1, "%" + searchText + "%");
	        pstmt.setString(2, "%" + searchText + "%");
	        pstmt.setString(3, "%" + searchText + "%");
	        pstmt.setString(4, "%" + searchText + "%");
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            RestaurantDto restaurant = new RestaurantDto();
	            restaurant.setRestaurantName(rs.getString("restaurantName"));
	            restaurant.setRestaurantNo(rs.getInt("restaurantNo"));
	            restaurant.setCategory(rs.getInt("category"));
	            restaurant.setLocation(rs.getString("location"));
	            restaurant.setImgName(rs.getString("imgName"));
	            restaurant.setTag(rs.getString("tags"));
	            restaurantList.add(restaurant);
	        }
	        rs.close();
	        pstmt.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return restaurantList;
	}


	public ArrayList<ReviewDto> getReviewsRestaurant(int restaurantNo) {
		ArrayList<ReviewDto> result = new ArrayList<>();
		String query = "SELECT * FROM reviews WHERE restaurantNo = ? ORDER BY reviewDate DESC FETCH FIRST 3 ROWS ONLY";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, restaurantNo);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				ReviewDto reviewDto = new ReviewDto();
				reviewDto.setReviewNo(rs.getInt("reviewNo"));
				reviewDto.setReviewTitle(rs.getString("reviewTitle"));
				reviewDto.setReviewContent(rs.getString("reviewContent"));
				reviewDto.setReviewDate(rs.getString("reviewDate"));
				reviewDto.setRatings(rs.getInt("ratings"));
				reviewDto.setUserNo(rs.getInt("user_no"));
				reviewDto.setRestaurantNo(rs.getInt("restaurantNo"));

				result.add(reviewDto);
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public ArrayList<String> getTagsForRestaurant(int restaurantNo) {
		ArrayList<String> tags = new ArrayList<>();
		String query = "SELECT tag FROM res_tag WHERE restaurantNo = ?";

		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, restaurantNo);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				tags.add(rs.getString("tag"));
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tags;
	}
	
	

}
