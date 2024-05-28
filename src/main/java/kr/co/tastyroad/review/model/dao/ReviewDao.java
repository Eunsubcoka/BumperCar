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

	public ReviewDao() { // 리뷰 등록 쿼리문
		dc = new DatabaseConnection();
		con = dc.connDB();
	}
	
	
	public int enroll(ReviewDto reviewDto) {
		String query = "INSERT INTO reviews VALUES(reviewsSeq.nextval, ?, ?, default, ?, ?, 3)";
	
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, reviewDto.getReviewTitle());
			pstmt.setString(2, reviewDto.getReviewContent());
			pstmt.setInt(3, reviewDto.getRatings());
			pstmt.setInt(4, reviewDto.getUserNo());
//			pstmt.setInt(5, reviewDto.getRestaurantNo());
			
			int result = pstmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public ReviewDto reviewDetail(int userNo) {
		String query = "select * from reviews where user_no = ? ";
					 
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, userNo);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int reviewNo = rs.getInt("reviewNo");
				String reviewTitle = rs.getString("reviewTitle");
				String reviewContent =rs.getString("reviewContent");
				String reviewDate = rs.getString("reviewDate");
				int ratings = rs.getInt("ratings"); 
				int no = rs.getInt("user_no"); 
				
				ReviewDto reviewDto = new ReviewDto();
				reviewDto.setReviewNo(reviewNo);
				reviewDto.setReviewTitle(reviewTitle);
				reviewDto.setReviewContent(reviewContent);
				reviewDto.setReviewDate(reviewDate);
				reviewDto.setRatings(ratings);
				reviewDto.setUserNo(no);
				
				return reviewDto;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ReviewDto selectNo(ReviewDto reviewDto) {
		String query = "SELECT user_no FROM reviews "
				 	 + "WHERE user_no = (SELECT MAX(user_no) FROM reviews WHERE user_no = ?)"; //가장 최근에 작성된 게시물
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, reviewDto.getUserNo());
		
			ResultSet rs = pstmt.executeQuery();
		
			while(rs.next()) {
				int userNo = rs.getInt("user_no");
				reviewDto.setUserNo(userNo);
				return reviewDto; 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
