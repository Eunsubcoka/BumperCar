package kr.co.tastyroad.review.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		
        try {
        	int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
    		int restaurantNo = Integer.parseInt(request.getParameter("restaurantNo"));
    		HttpSession session = request.getSession();
    		int userNo = (int)session.getAttribute("userNo");
    		String fileName = request.getParameter("fileName"); 
    		
    		ReviewDto reviewDto = new ReviewDto();
    		reviewDto.setReviewNo(reviewNo);
    		reviewDto.setFileName(fileName);
    		reviewDto.setRestaurantNo(restaurantNo);
    		reviewDto.setUserNo(userNo);
    		
    		
    		ReviewServiceImpl reviewService = new ReviewServiceImpl();
    		int result = reviewService.reviewDelete(reviewDto);
    		
    		if(result == 1) {
    																					//  // 삭제 성공 시 deleteSuccess=true 매개변수를 추가하여 리다이렉트
    			response.sendRedirect("/review/review.do?reviewNo=" + reviewNo + "&restaurantNo=" + restaurantNo + "&deleteSuccess=true");	
    		}
    		else {
    			response.sendRedirect("/");
    		}

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/views/error.html");
        }			
	
	
	
	}

}
