package kr.co.tastyroad.review.model.service;


import java.util.ArrayList;

import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;
import kr.co.tastyroad.review.model.dto.ReviewDto;

public interface ReviewService {

	//리뷰 수정 폼
	public ReviewDto ReviewEditForm(ReviewDto reviewDto);
	//리뷰 등록
	public int enroll(ReviewDto reviewDto);
	// 등록된 리뷰 no 가져오기
	public ReviewDto selectNo(ReviewDto reviewDto);
	// 파일 정보 등록
	public int fileUpload(ReviewDto reviewDto);
	// 리뷰 리스트 조회
	public ArrayList<ReviewDto> getReviewList(int restaurantNo);
	// 리뷰 파일명 가져오기
	public ArrayList<ReviewDto> uploadList(ArrayList<ReviewDto> list);
	// 리뷰 수정
	public int editUpdate(ReviewDto reviewDto);
	// 리뷰 삭제
	public int reviewDelete(ReviewDto reviewDto);
	
	// 파일 하니씩 가져오기
	public ArrayList<ReviewDto> uploadListOnce();
	// 리뷰 하나씪 가져오기
    public ArrayList<ReviewDto> getReviewListOnce(ArrayList<RestaurantDto> restaurantList);
	
	// 수정 업로드 파일 삭제
	public int delete(ReviewDto reviewDto, String removeImageName);
	
	// 좋아요 
	public boolean likeReview(int reviewNo, int userNo);
	
}
