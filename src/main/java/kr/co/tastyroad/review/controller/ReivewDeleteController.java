package kr.co.tastyroad.review.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.tastyroad.review.model.dto.ReviewDto;
import kr.co.tastyroad.review.model.service.ReviewServiceImpl;


@WebServlet("/review/delete.do")
public class ReivewDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ReivewDeleteController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
		int restaurantNo = Integer.parseInt(request.getParameter("restaurantNo"));
		
		String fileName = request.getParameter("fileName"); 
		
		ReviewDto reviewDto = new ReviewDto();
		reviewDto.setReviewNo(reviewNo);
		reviewDto.setFileName(fileName);
		reviewDto.setRestaurantNo(restaurantNo);
		System.out.println("리뷰번호 : " + reviewNo);
		
		ReviewServiceImpl reviewService = new ReviewServiceImpl();
		int result = reviewService.reviewDelete(reviewDto);
		System.out.println("result : " + result);
		
		
		request.setAttribute("restaurantNo", restaurantNo);
		request.setAttribute("reviewNo", reviewNo);
		
		if(result == 1) {
			response.sendRedirect("/review/review.do?reviewNo=" + reviewNo + "&restaurantNo=" + restaurantNo);	
			// 디스패처 왜???????????
		}
		else {
			response.sendRedirect("/");
		}
			
	
	
	
	}

}
