package kr.co.tastyroad.review.model.service;


import java.util.ArrayList;

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
	public ArrayList<ReviewDto> getReviewList();
	// 리뷰 파일명 가져오기
	public ArrayList<ReviewDto> uploadList(ArrayList<ReviewDto> list);
	// 리뷰 수정
	public int editUpdate(ReviewDto reviewDto);
	// 리뷰 삭제
	public int reviewDelete(ReviewDto reviewDto);
	// 파일 하니씩 가져오기
	public ArrayList<ReviewDto> uploadListOnce();
}
