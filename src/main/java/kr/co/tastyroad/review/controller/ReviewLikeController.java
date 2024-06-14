package kr.co.tastyroad.review.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.tastyroad.review.model.dto.ReviewDto;
import kr.co.tastyroad.review.model.service.ReviewServiceImpl;

@WebServlet("/review/reviewLike.do")
public class ReviewLikeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReviewLikeController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
		
		ReviewDto reviewDto = new ReviewDto();
		reviewDto.setReviewNo(reviewNo);
		
		ReviewServiceImpl reviewService = new ReviewServiceImpl();
		
		int resultLIke = reviewService.reviewLike(reviewDto);
		
		if(resultLIke == 1) {
			response.sendRedirect("/views/review/review.jsp");
		}
		
		
	}

}
