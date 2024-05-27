package kr.co.tastyroad.review.model.service;

import java.util.ArrayList;

import kr.co.tastyroad.review.model.dao.ReviewDao;
import kr.co.tastyroad.review.model.dto.ReviewDto;

public class ReviewServiceImpl implements ReviewService {
	ReviewDao reviewDao;
	
	public ReviewServiceImpl() {
		reviewDao = new ReviewDao();
	}
	
	@Override
	public ArrayList<ReviewDto> getReviewList() {
		return reviewDao.getReviewList();
	}
	
	//리뷰 수정 폼
	@Override
	public ReviewDto ReviewEditForm(int reviewNo) {
		return reviewDao.reviewDetail(reviewNo);
	}
}
