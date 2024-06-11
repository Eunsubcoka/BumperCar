package kr.co.tastyroad.common;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.tastyroad.member.model.dto.Member;
import kr.co.tastyroad.member.model.service.MemberServiceImpl;
import kr.co.tastyroad.notice.model.dto.noticeDto;
import kr.co.tastyroad.notice.model.service.noticeServiceImpl;
import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;
import kr.co.tastyroad.restaurant.model.service.RestaurantServiceImpl;
import kr.co.tastyroad.review.model.dto.ReviewDto;
import kr.co.tastyroad.review.model.service.ReviewServiceImpl;

@WebServlet("/tastyForm/*")
public class FormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FormController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html; charset=utf-8");
		
		String action = request.getPathInfo();
		String nextPage = "";
		
		// 성오
		if(action.equals("/registerForm.do")) { // 회원가입	
			nextPage = "/views/member/register.jsp";
		} 
		else if(action.equals("/profile.do")) {  // 프로필
            HttpSession session = request.getSession();
            Integer userNo = (Integer) session.getAttribute("userNo");
            
            if (userNo != null) {
                MemberServiceImpl memberService = new MemberServiceImpl();
                Member member = memberService.getUserProfile(userNo);
                
                request.setAttribute("member", member);
            } else {
                nextPage = "/views/member/login.jsp";
            }

            nextPage = "/views/member/profile.jsp";
		}
		

		
		// 은섭
		else if(action.equals("/reservation.do")) { // 레스토랑 예약
			int restaurantNo = Integer.parseInt(request.getParameter("restaurantNo"));
			
			RestaurantDto result = new RestaurantDto();
			RestaurantServiceImpl resService = new RestaurantServiceImpl();
			
			result = resService.getRestaurant(restaurantNo);
			request.setAttribute("result", result);
			
			HttpSession session = request.getSession();
			Integer userNo = (Integer)session.getAttribute("userNo");
			if(userNo==null) {
				nextPage = "/views/member/login.jsp";
			}else {
				
				request.setAttribute("userNo", userNo);
				nextPage = "/views/reservation/reservation.jsp";
			}
		}
		
		else if(action.equals("/resEdit.do")) { 
			int resNo = Integer.parseInt(request.getParameter("resNo"));
			RestaurantServiceImpl resService = new RestaurantServiceImpl();
			RestaurantDto resDto = new RestaurantDto();
			resDto = resService.getRestaurant(resNo);
			
			request.setAttribute("resNo", resNo);
			request.setAttribute("resDto", resDto);

			nextPage = "/views/restaurant/restaurantEdit.jsp";
		}
		else if(action.equals("/resAdd.do")) {
			nextPage = "/views/restaurant/restaurantAdd.jsp";
		}
		
		// 아태
		else if(action.equals("/noticeEnrollForm.do")) { // 공지 등록
			nextPage = "/views/notice/noticeEnroll.jsp";
		}
		else if(action.equals("/noticeEditForm.do")) { // 공지 수정
			int noticeNo = Integer.parseInt(request.getParameter("boardno"));
			
			noticeServiceImpl noticeService = new noticeServiceImpl();
			noticeDto result = noticeService.getEditForm(noticeNo);
			
			request.setAttribute("result", result);
			nextPage = "/views/notice/noticeEdit.jsp";
		}
		
		
		// 혜미
		else if(action.equals("/enrollReviewForm.do")) { // 리뷰 등록 페이지
			int restaurantNo = Integer.parseInt(request.getParameter("restaurantNo"));
			
			request.setAttribute("restaurantNo", restaurantNo);
			
			nextPage = "/views/review/reviewEnroll.jsp"; 
		}else if(action.equals("/editReviewForm.do")) { // 리뷰 수정 페이지
			
			int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
			int restaurantNo = Integer.parseInt(request.getParameter("restaurantNo"));
			
			ReviewDto reviewDto = new ReviewDto();
			reviewDto.setReviewNo(reviewNo);
			reviewDto.setRestaurantNo(restaurantNo);

			ReviewServiceImpl reviewService = new ReviewServiceImpl();
			ReviewDto result = reviewService.ReviewEditForm(reviewDto);
			result.setRestaurantNo(restaurantNo);
			
			ArrayList<ReviewDto> list = new ArrayList<ReviewDto>();
			list = reviewService.getReviewList(restaurantNo); // 게시글 리스트
			
			ArrayList<ReviewDto> fileList = new ArrayList<ReviewDto>(); 
			fileList = reviewService.uploadList(list);
			request.setAttribute("fileList", fileList);
			request.setAttribute("result", result);
			request.setAttribute("list", list);
			
			nextPage = "/views/review/reviewEdit.jsp";
			
		}

		
		
		if(nextPage != null && !nextPage.isEmpty()) {
			RequestDispatcher view = request.getRequestDispatcher(nextPage);
			view.forward(request, response);
		}else {
			response.sendRedirect("/views/error.html");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
