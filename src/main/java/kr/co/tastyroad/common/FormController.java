package kr.co.tastyroad.common;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.tastyroad.review.model.dto.ReviewDto;
import kr.co.tastyroad.review.model.service.ReviewServiceImpl;

@WebServlet("/tastyForm/*")
public class FormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FormController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html; charset-utf-8");
		
		String action = request.getPathInfo();
		String nextPage = "";
		
		System.out.println("a : " + action);
		if(action.equals("/registerForm.do")) {
			nextPage = "/views/member/register.jsp";
		} else if(action.equals("/restaurantDetail.do")) {
			nextPage = "/views/restaurant/restaurant.jsp";
		}
		 else if(action.equals("/reservation.do")) {
				nextPage = "/views/reservation/reservation.jsp";
			}
		else if(action.equals("/editReviewForm.do")) { 
//			int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
//			
//			ReviewServiceImpl reviewService = new ReviewServiceImpl();
//			ReviewDto result = reviewService.ReviewEditForm(reviewNo);
//			
//			request.setAttribute("result", result);
			nextPage = "/views/review/reviewEdit.jsp";
		}
		RequestDispatcher view = request.getRequestDispatcher(nextPage);
		view.forward(request, response); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
