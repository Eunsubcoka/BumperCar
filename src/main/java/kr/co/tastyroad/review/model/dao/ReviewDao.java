package kr.co.tastyroad.review.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.co.tastyroad.common.DatabaseConnection;
import kr.co.tastyroad.review.model.dto.ReviewDto;

public class ReviewDao {
	private Connection con;
	private DatabaseConnection dc;
	private PreparedStatement pstmt;

	public ReviewDao() {
		dc = new DatabaseConnection();
		con = dc.connDB();
	}
	
	public ArrayList<ReviewDto> getReviewList() {
		ArrayList<ReviewDto> result = new ArrayList<>();
		
		String query = "select * from reviews";
		
		try {
			pstmt = con.prepareStatement(query);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int reviewNo = rs.getInt("reviewNo");
				String reviewTitle = rs.getString("reviewTitle");
				String reviewContent =rs.getString("reviewContent");
				String reviewDate = rs.getString("reviewDate");
				int ratings = rs.getInt("ratings"); 
				int userNo = rs.getInt("userNo"); 
				int restaurantNo = rs.getInt("restaurantNo"); 
				
				ReviewDto reviewDto = new ReviewDto();
				reviewDto.setReviewNo(reviewNo);
				reviewDto.setReviewTitle(reviewTitle);
				reviewDto.setReviewContent(reviewContent);
				reviewDto.setReviewDate(reviewDate);
				reviewDto.setRatings(ratings);
				reviewDto.setUserNo(userNo);
				reviewDto.setRestaurantNo(restaurantNo);
				
				result.add(reviewDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	public ReviewDto reviewDetail(int reviewNo) {
		String query = "select * from reviews r "
					 + "join semiMember m on m.userNo = r.userNo "
					 + "where reviewNo = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, reviewNo);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("reviewNo");
				String reviewTitle = rs.getString("reviewTitle");
				String reviewContent =rs.getString("reviewContent");
				String reviewDate = rs.getString("reviewDate");
				int ratings = rs.getInt("ratings"); 
				int userNo = rs.getInt("userNo"); 
				int restaurantNo = rs.getInt("restaurantNo"); 
				
				ReviewDto reviewDto = new ReviewDto();
				reviewDto.setReviewNo(no);
				reviewDto.setReviewTitle(reviewTitle);
				reviewDto.setReviewContent(reviewContent);
				reviewDto.setReviewDate(reviewDate);
				reviewDto.setRatings(ratings);
				reviewDto.setUserNo(userNo);
				reviewDto.setRestaurantNo(restaurantNo);
				
				return reviewDto;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
