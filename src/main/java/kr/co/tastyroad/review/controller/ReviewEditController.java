package kr.co.tastyroad.review.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.tastyroad.review.model.dto.ReviewDto;
import kr.co.tastyroad.review.model.service.ReviewServiceImpl;

@WebServlet("/review/reviewEdit.do")
public class ReviewEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReviewEditController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 사용자가 보낸 데이터를 UTF-8로 인코딩
		response.setContentType("text/html; charset=UTF-8"); 
		
		int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
		int restaurantNo = Integer.parseInt(request.getParameter("restaurantNo"));
		int ratings = Integer.parseInt(request.getParameter("ratings"));
		String reviewTitle = request.getParameter("reviewTitle");
		String reviewContent = request.getParameter("reviewContent");
		String fileName = request.getParameter("fileName");
		String filePath = request.getParameter("filePath");
		
	
		ReviewDto reviewDto = new ReviewDto();
		reviewDto.setReviewNo(reviewNo);
		reviewDto.setRestaurantNo(restaurantNo);
		reviewDto.setRatings(ratings);
		reviewDto.setReviewTitle(reviewTitle);
		reviewDto.setReviewContent(reviewContent);
		reviewDto.setFileName(fileName);
		reviewDto.setFilePath(filePath);
		
		// update문을 사용하지 않는 방법
		// 1. (있었다면) 기존에 있던 이미지 삭제
		// 2. 기존에 테이블에 들어갓던 컬럼 삭제
		
		// 3. 새로 올라온 파일 업로드
		// 4. 테이블에 insert
		
		ReviewServiceImpl reviewService = new ReviewServiceImpl();
		int result = reviewService.editUpdate(reviewDto);
		
		if(result == 1) {  
			
			response.sendRedirect("/review/review.do?reviewNo=" + reviewNo + "&restaurantNo=" + restaurantNo);
		}
		else {
			response.sendRedirect("/");
		}
	}

}
