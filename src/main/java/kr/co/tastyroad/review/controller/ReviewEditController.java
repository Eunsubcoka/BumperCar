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
		String reviewTitle = request.getParameter("reviewTitle");
		String reviewContent = request.getParameter("reviewContent");
		
	
		ReviewDto reviewDto = new ReviewDto();
		reviewDto.setReviewNo(reviewNo);
		reviewDto.setRestaurantNo(restaurantNo);
		reviewDto.setReviewTitle(reviewTitle);
		reviewDto.setReviewContent(reviewContent);
		
		ReviewServiceImpl reviewService = new ReviewServiceImpl();
		int result = reviewService.editUpdate(reviewDto);
		
		if(result == 1) {   //?restaurantNo=" +restaurantNo
			response.sendRedirect("/review/review.do?reviewNo=" + reviewNo + "&restaurantNo=" + restaurantNo);
		}
		else {
			response.sendRedirect("/");
		}
	}

}
