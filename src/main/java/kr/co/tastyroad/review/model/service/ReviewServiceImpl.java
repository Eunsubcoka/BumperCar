package kr.co.tastyroad.review.model.service;



import java.util.ArrayList;

import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;
import kr.co.tastyroad.review.model.dao.ReviewDao;
import kr.co.tastyroad.review.model.dto.ReviewDto;

public class ReviewServiceImpl implements ReviewService {
	ReviewDao reviewDao;
	
	public ReviewServiceImpl() {
		reviewDao = new ReviewDao();
	}
	
	//리뷰 수정 폼
	@Override
	public ReviewDto ReviewEditForm(ReviewDto reviewDto) {
		return reviewDao.reviewDetail(reviewDto);
	}
	
	//리뷰 등록
	@Override
	public int enroll(ReviewDto reviewDto) {
		return reviewDao.enroll(reviewDto);
	}
	
	// 등록된 리뷰 no 가져오기
	@Override
	public ReviewDto selectNo(ReviewDto reviewDto) {
		return reviewDao.selectNo(reviewDto);
	}
	
	// 파일 정보 등록
	@Override
	public int fileUpload(ReviewDto reviewDto) {
		return reviewDao.fileUpload(reviewDto);
	}
	
	// 리뷰 리스트 조회
	@Override
	public ArrayList<ReviewDto> getReviewList(int restaurantNo){
		return reviewDao.getReviewList(restaurantNo);
	}
	
	// 리뷰 파일명 가져오기
	@Override
	public ArrayList<ReviewDto> uploadList(ArrayList<ReviewDto> list) {
//		getFileName : 파일명 가져오기
		return reviewDao.uploadList(list);
	}
	
	// 리뷰 수정
	@Override
	public int editUpdate(ReviewDto reviewDto) {
//		reviewDao.editFileUpdate(reviewDto); 
		return reviewDao.editUpdate(reviewDto);
	}
		
	// 수정 업로드 파일 삭제
	@Override
	public int delete(ReviewDto reviewDto, String removeImageName) {
		return reviewDao.delete(reviewDto, removeImageName);
		
	}
	
	// 리뷰 삭제 
	@Override
	public int reviewDelete(ReviewDto reviewDto) {
		
		reviewDao.reviewFileDelete(reviewDto);
		
		return reviewDao.reviewDelete(reviewDto);
	}
	
	
	// 파일 하나씩만 가져오기
	@Override
	public ArrayList<ReviewDto> uploadListOnce(){
		return reviewDao.uploadListOnce();
	}
	@Override
    public ArrayList<ReviewDto> getReviewListOnce(ArrayList<RestaurantDto> restaurantList){
		return reviewDao.getReviewListOnce(restaurantList);
	}
	
	
	@Override
	public boolean likeReview(int reviewNo, int userNo) {
        // 중복 좋아요 체크  (isUserLikedReview -> 유저가 특정 리뷰를 좋아요 했는지 체크하는 메서드)
        if (reviewDao.isUserLikedReview(reviewNo, userNo)) {
        	// 이미 좋아요한 경우 - > 좋아요 취소
        	reviewDao.removeLike(userNo, reviewNo);
        	
        	//리뷰 좋아요 수 업데이트 (-1)
        	reviewDao.updateLikeCount(reviewNo, -1);
        	
            return false; // 좋아요 취소 완료
        }

        // 좋아요 추가
        boolean success = reviewDao.addLike(reviewNo, userNo);
        if (success) {
            // 리뷰의 좋아요 수 업데이트 (+1)
            reviewDao.updateLikeCount(reviewNo, 1);
        }
        return success;
    }
	
	public int getLikeCount(int reviewNo) {
		return reviewDao.getLikeCount(reviewNo);
	}
}



