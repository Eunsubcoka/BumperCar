package kr.co.tastyroad.review.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		int userNo = (int)session.getAttribute("userNo");


        // 서비스 클래스를 통해 좋아요 처리를 합니다.
        ReviewServiceImpl reviewService = new ReviewServiceImpl();
        boolean isLiked = reviewService.likeReview(reviewNo, userNo);
        
        int likeCount = reviewService.getLikeCount(reviewNo);

        // JSON 형식으로 반환합니다.
        String jsonResponse = "{\"liked\":" + isLiked + ", \"likeCount\":" + likeCount + "}";
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse);
	}
}