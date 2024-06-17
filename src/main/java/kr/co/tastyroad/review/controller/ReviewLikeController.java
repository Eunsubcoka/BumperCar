package kr.co.tastyroad.review.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

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
	    HttpSession session = request.getSession();
	    int userNo = (int) session.getAttribute("userNo");

	    ReviewDto reviewDto = new ReviewDto();
	    reviewDto.setReviewNo(reviewNo);
	    reviewDto.setUserNo(userNo);

	    ReviewServiceImpl reviewService = new ReviewServiceImpl();

	    // 이미 좋아요를 했는지 체크
	    boolean alreadyLiked = reviewService.checkIfLiked(reviewDto);

	    int resultLike;

	    if (!alreadyLiked) {
	        // 좋아요 추가
	        resultLike = reviewService.addLike(reviewDto);
	    } else {
	        // 좋아요 취소
	        resultLike = reviewService.removeLike(reviewDto);
	    }

	    // 좋아요 수를 다시 가져오기
	    int likeCount = reviewService.getLikeCount(reviewNo);

	    // JSON 응답 생성
	    JsonObject jsonResponse = new JsonObject();
	    jsonResponse.addProperty("success", resultLike == 1);
	    jsonResponse.addProperty("likeCount", likeCount);
	    jsonResponse.addProperty("alreadyLiked", alreadyLiked);

	    // 응답 전송
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(jsonResponse.toString());
	    
	    // 좋아요 수를 request에 설정
	    request.setAttribute("likeCount", likeCount);
	}
}