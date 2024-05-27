package kr.co.tastyroad.review.model.service;

import java.util.ArrayList;

import kr.co.tastyroad.review.model.dto.ReviewDto;

public interface ReviewService {
	//리뷰 리스트 조회
	public ArrayList<ReviewDto> getReviewList();
	
	
	//리뷰 수정 폼
	public ReviewDto ReviewEditForm(int reviewNo);
}
