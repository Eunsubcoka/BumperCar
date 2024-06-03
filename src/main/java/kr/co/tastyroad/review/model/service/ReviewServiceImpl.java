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
	public ArrayList<ReviewDto> getReviewList(){
		return reviewDao.getReviewList();
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
		
		 reviewDao.editFileUpdate(reviewDto); 
			
		return reviewDao.editUpdate(reviewDto);

 	}
	
	// 리뷰 삭제
	public int reviewDelete(ReviewDto reviewDto) {
		
		reviewDao.reviewFileDelete(reviewDto);
		
		return reviewDao.reviewDelete(reviewDto);
	}
	
	// 파일 하나씩만 가져오기
	@Override
	public ArrayList<ReviewDto> uploadListOnce(){
		return reviewDao.uploadListOnce();
	}
}

