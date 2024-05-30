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
		String query = "SELECT reviewNo FROM reviews "
				 	 + "WHERE reviewNo = (SELECT MAX(reviewNo) FROM reviews WHERE user_no = ?)"; //가장 최근에 작성된 게시물
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, reviewDto.getUserNo());
		
			ResultSet rs = pstmt.executeQuery();
		
			while(rs.next()) {
				int reviewNo = rs.getInt("reviewNo");
				reviewDto.setReviewNo(reviewNo);
				return reviewDto; 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int fileUpload(ReviewDto reviewDto) {
		String query = "insert into review_upload values(?, ?, ?)";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, reviewDto.getFilePath());
			pstmt.setString(2, reviewDto.getFileName());
			pstmt.setInt(3, reviewDto.getReviewNo());
			
			int result = pstmt.executeUpdate();
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	// 리뷰 리스트 로직
    public ArrayList<ReviewDto> getReviewList() {
    ArrayList<ReviewDto> result = new ArrayList<>();
    
   
    String query = "select reviewNo, reviewTitle, reviewContent, reviewDate, ratings from reviews r "
                 + "join TASTY_MEMBER s on r.user_no = s.user_no";
    
    try {
        pstmt = con.prepareStatement(query);
        
        ResultSet rs = pstmt.executeQuery();
        
        while(rs.next()) {
            int reviewNo = rs.getInt("reviewNo");
            String reviewTitle = rs.getString("reviewTitle");
            String reviewContent =rs.getString("reviewContent");
            String reviewDate = rs.getString("reviewDate");
            int ratings = rs.getInt("ratings"); 
            
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setReviewNo(reviewNo);
            reviewDto.setReviewTitle(reviewTitle);
            reviewDto.setReviewContent(reviewContent);
            reviewDto.setReviewDate(reviewDate);
            reviewDto.setRatings(ratings);
            
            result.add(reviewDto);
            
        }
        return result;
        
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return result;
    }
    
    //파일 이름 가져오기
    public void getFileName(ArrayList<ReviewDto> result) {
		String query ="select * from review_upload "
					 + "where reviewNo = ? ";
		
		try {
			pstmt = con.prepareStatement(query);
			for(ReviewDto review : result) {
				pstmt.setInt(1, review.getReviewNo());
				
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int reviewNo = rs.getInt("reviewNo");
					String reviewFilePath = rs.getString("review_upload_path");
					String reviewFileName = rs.getString("review_upload_name");
					
					review.setReviewNo(reviewNo);
					review.setFilePath(reviewFilePath);
					review.setFileName(reviewFileName);
					System.out.println(review.getFilePath());
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
    
