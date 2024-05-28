package kr.co.tastyroad.review.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.tastyroad.review.model.dto.ReviewDto;
import kr.co.tastyroad.review.model.service.ReviewServiceImpl;


@WebServlet("/review/reviewEnroll.do")
public class ReviewEnrollController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReviewEnrollController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 사용자가 보낸 데이터를 UTF-8로 인코딩
		response.setContentType("text/html; charset=UTF-8");
		
		String reviewTitle = request.getParameter("reviewTitle");
		String reviewContent = request.getParameter("reviewContent");
		int reviewRating = Integer.parseInt(request.getParameter("ratingStars"));
		System.out.println(reviewRating);
		
		HttpSession session = request.getSession();
		int userNo = (int)session.getAttribute("userNo");

		
		ReviewDto reviewDto = new ReviewDto();
		reviewDto.setReviewTitle(reviewTitle);
		reviewDto.setReviewContent(reviewContent);
		reviewDto.setUserNo(userNo);
		reviewDto.setRatings(reviewRating);
		
		ReviewServiceImpl reviewService = new ReviewServiceImpl();
		int result = reviewService.enroll(reviewDto);
		
		ReviewDto resultDto = reviewService.selectNo(reviewDto);
		
		if(result == 1) {
			response.sendRedirect("/views/review/review.jsp");
		}
		
	}

}
