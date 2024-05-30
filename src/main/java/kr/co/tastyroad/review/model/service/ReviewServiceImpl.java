package kr.co.tastyroad.review.model.service;



import java.util.ArrayList;

import kr.co.tastyroad.review.model.dao.ReviewDao;
import kr.co.tastyroad.review.model.dto.ReviewDto;

public class ReviewServiceImpl implements ReviewService {
	ReviewDao reviewDao;
	
	public ReviewServiceImpl() {
		reviewDao = new ReviewDao();
	}
	
	//리뷰 수정 폼
	@Override
	public ReviewDto ReviewEditForm(int userNo) {
		return reviewDao.reviewDetail(userNo);
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
	public ArrayList<ReviewDto> getReviewList(){
		ArrayList<ReviewDto> result = new ArrayList<ReviewDto>();
		result = reviewDao.getReviewList();
		reviewDao.getFileName(result);
		
		return result;
		
	}



}
