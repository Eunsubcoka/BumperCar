package kr.co.tastyroad.review.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.co.tastyroad.common.DatabaseConnection;
import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;
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
		String query = "INSERT INTO reviews VALUES(reviewsSeq.nextval, ?, ?, default, ?, ?, ?)";
	
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, reviewDto.getReviewTitle());
			pstmt.setString(2, reviewDto.getReviewContent());
			pstmt.setInt(3, reviewDto.getRatings());
			pstmt.setInt(4, reviewDto.getUserNo());
			pstmt.setInt(5, reviewDto.getRestaurantNo());
			
			int result = pstmt.executeUpdate();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public ReviewDto reviewDetail(ReviewDto reviewDto) {
		String query = "select * from reviews r "
					 + "JOIN TASTY_MEMBER tm ON "
					 + "r.user_no = tm.user_no "
					 + "where reviewNo = ? ";
					 
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, reviewDto.getReviewNo());
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("reviewNo");
				String reviewTitle = rs.getString("reviewTitle");
				String reviewContent =rs.getString("reviewContent");
				String reviewDate = rs.getString("reviewDate");
				int ratings = rs.getInt("ratings"); 
				int userNo = rs.getInt("user_no"); 
				String profile = rs.getString("profile");
				
				
				ReviewDto reviewResult = new ReviewDto();
				reviewResult.setReviewNo(no);
				reviewResult.setReviewTitle(reviewTitle);
				reviewResult.setReviewContent(reviewContent);
				reviewResult.setReviewDate(reviewDate);
				reviewResult.setRatings(ratings);
				reviewResult.setUserNo(userNo);
				reviewResult.setProfile(profile);
				
				

				return reviewResult;
				
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
    public ArrayList<ReviewDto> getReviewList(int restaurantNo) {
    ArrayList<ReviewDto> result = new ArrayList<>();
    
   
    String query = "select r.reviewNo, r.reviewTitle, r.reviewContent, r.reviewDate, r.ratings, r.user_no, r.restaurantNo, res.restaurantname, tm.profile, tm.user_name "
    			 + "from reviews r "
                 + "join TASTY_MEMBER tm on r.user_no = tm.user_no "
                 + "join restaurant res on r.restaurantNo = res.restaurantNo "
                 + "where r.restaurantNo = ?"
                 + "order by reviewNo desc";
    
    try {
        pstmt = con.prepareStatement(query);
        pstmt.setInt(1, restaurantNo);
        
        ResultSet rs = pstmt.executeQuery();
        
        while(rs.next()) {
            int reviewNo = rs.getInt("reviewNo");
            String reviewTitle = rs.getString("reviewTitle");
            String reviewContent =rs.getString("reviewContent");
            String reviewDate = rs.getString("reviewDate");
            int ratings = rs.getInt("ratings"); 
			int userNo = rs.getInt("user_no"); 
			int resNo = rs.getInt("restaurantNo");
			String resName = rs.getString("restaurantname");
			String profile = rs.getString("profile");
			String userName = rs.getString("user_name");
            
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setReviewNo(reviewNo);
            reviewDto.setReviewTitle(reviewTitle);
            reviewDto.setReviewContent(reviewContent);
            reviewDto.setReviewDate(reviewDate);
            reviewDto.setRatings(ratings);
			reviewDto.setUserNo(userNo);
			reviewDto.setRestaurantNo(resNo);
			reviewDto.setRestaurantName(resName);
			reviewDto.setProfile(profile);
			reviewDto.setUserName(userName);
            
            result.add(reviewDto);
            
        }
        return result;
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return result;
    }
    
    //파일리스트 가져오기
    public ArrayList<ReviewDto> uploadList(ArrayList<ReviewDto> list) {
    	ArrayList<ReviewDto> result = new ArrayList<>();
    	
		String query ="select * from review_upload where reviewNo = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			for(ReviewDto review : list) {
				pstmt.setInt(1, review.getReviewNo());
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int reviewNo = rs.getInt("reviewNo");
					String reviewFilePath = rs.getString("review_upload_path");
					String reviewFileName = rs.getString("review_upload_name");
					
					ReviewDto reviewDto = new ReviewDto();
					
					reviewDto.setReviewNo(reviewNo);
					reviewDto.setFilePath(reviewFilePath);
					reviewDto.setFileName(reviewFileName);
					
					result.add(reviewDto); 
				}
			}
			return result;
				
		}catch (SQLException e) {
			e.printStackTrace();
		} 
		return result;
	}

	// 리뷰 수정
	public int editUpdate(ReviewDto reviewDto) {
		String query = "update reviews set reviewTitle = ?, reviewContent = ?, ratings = ?, reviewDate = sysdate "
					 + "where reviewNo = ? ";
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, reviewDto.getReviewTitle());
			pstmt.setString(2, reviewDto.getReviewContent());
			pstmt.setInt(3, reviewDto.getRatings());
			pstmt.setInt(4, reviewDto.getReviewNo());
			int result = pstmt.executeUpdate();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return 0;
	}
    
	// 리뷰 업로드 파일 수정
	public int editFileUpdate(ReviewDto reviewDto) {
		String query = "update review_upload set review_upload_path = ?, review_upload_name = ? "
				+ "where reviewNo = ?";
		
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
	
	// 수정 업로드 파일 삭제
	public int delete(ReviewDto reviewDto, String removeImageName) {
		String query = "delete from review_upload where reviewNo = ? and review_upload_name = ? ";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, reviewDto.getReviewNo());
			pstmt.setString(2, removeImageName);
			
			int result = pstmt.executeUpdate();
			
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	// 리뷰 삭제
	public int reviewDelete(ReviewDto reviewDto) {
		String query = "delete from reviews where reviewNo = ? and restaurantNo = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, reviewDto.getReviewNo());
			pstmt.setInt(2, reviewDto.getRestaurantNo());
			
			int result = pstmt.executeUpdate();
			
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	// 리뷰에 업로드된 파일 삭제
	public int reviewFileDelete(ReviewDto reviewDto) {
		String query = "delete from review_upload where reviewNo = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, reviewDto.getReviewNo());
			
			int result = pstmt.executeUpdate();
			
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	
	
	// 파일 하나씩 가져오기
	//파일리스트 가져오기
    public ArrayList<ReviewDto> uploadListOnce() {
    	ArrayList<ReviewDto> result = new ArrayList<>();
    	
		String query ="select distinct * from review_upload";
		try {
			pstmt = con.prepareStatement(query);
			
			ResultSet rs = pstmt.executeQuery();
				
			while(rs.next()) {
				int reviewNo = rs.getInt("reviewNo");
				String reviewFilePath = rs.getString("review_upload_path");
				String reviewFileName = rs.getString("review_upload_name");
					
				ReviewDto reviewDto = new ReviewDto();
					
		        reviewDto.setReviewNo(reviewNo);
		        reviewDto.setFilePath(reviewFilePath);
		        reviewDto.setFileName(reviewFileName);
		            
		        result.add(reviewDto); 
			}
			return result;
				
		}catch (SQLException e) {
			e.printStackTrace();
		} 
		return result;
	}

  // 리뷰 하나씩
    public ArrayList<ReviewDto> getReviewListOnce(ArrayList<RestaurantDto> restaurantList) {
    ArrayList<ReviewDto> result = new ArrayList<>();
    
   
    String query = " select distinct r.reviewNo, r.reviewTitle, r.reviewContent, r.reviewDate, r.ratings, r.user_no, r.restaurantNo, res.restaurantname "
    			 + " from reviews r "
                 + " join TASTY_MEMBER s on r.user_no = s.user_no "
                 + " join restaurant res on r.restaurantNo = res.restaurantNo "
                 + " where r.restaurantNo = ? "
                 + " order by reviewNo desc ";
    
    try {
        pstmt = con.prepareStatement(query);
        for(RestaurantDto item : restaurantList) {
        	
        pstmt.setInt(1, item.getRestaurantNo());
        
        ResultSet rs = pstmt.executeQuery();
        
        while(rs.next()) {
            int reviewNo = rs.getInt("reviewNo");
            String reviewTitle = rs.getString("reviewTitle");
            String reviewContent =rs.getString("reviewContent");
            String reviewDate = rs.getString("reviewDate");
            int ratings = rs.getInt("ratings"); 
			int userNo = rs.getInt("user_no"); 
			int resNo = rs.getInt("restaurantNo");
			String resName = rs.getString("restaurantname");
            
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setReviewNo(reviewNo);
            reviewDto.setReviewTitle(reviewTitle);
            reviewDto.setReviewContent(reviewContent);
            reviewDto.setReviewDate(reviewDate);
            reviewDto.setRatings(ratings);
			reviewDto.setUserNo(userNo);
			reviewDto.setRestaurantNo(resNo);
			reviewDto.setRestaurantName(resName);
            
            result.add(reviewDto);
            
        }
        }
        return result;
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return result;
    }
    
    
    
    // 좋아요 구현중
    
    public boolean checkIfLiked(ReviewDto reviewDto) {
        // 이미 좋아요를 했는지 확인하는 메서드 구현
        String query = "SELECT * FROM likes WHERE user_no = ? AND reviewNo = ?";
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, reviewDto.getUserNo());
            pstmt.setInt(2, reviewDto.getReviewNo());
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // 결과가 있으면 true (이미 좋아요 한 상태)
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int addLike(ReviewDto reviewDto) {
        // 좋아요 추가하는 메서드 구현
        String query = "INSERT INTO likes (user_no, reviewNo) VALUES (?, ?)";
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, reviewDto.getUserNo());
            pstmt.setInt(2, reviewDto.getReviewNo());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int removeLike(ReviewDto reviewDto) {
        // 좋아요 취소하는 메서드 구현
        String query = "DELETE FROM likes WHERE user_no = ? AND reviewNo = ?";
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, reviewDto.getUserNo());
            pstmt.setInt(2, reviewDto.getReviewNo());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getLikeCount(int reviewNo) {
        // 좋아요 수를 가져오는 메서드는 이전과 동일
        String query = "SELECT COUNT(*) AS likeCount FROM likes WHERE reviewNo = ?";
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, reviewNo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("likeCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}

    

    
