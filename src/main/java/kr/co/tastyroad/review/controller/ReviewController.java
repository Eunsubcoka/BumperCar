package kr.co.tastyroad.review.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.tastyroad.review.model.dto.ReviewDto;
import kr.co.tastyroad.review.model.service.ReviewServiceImpl;

@WebServlet("/review/review.do")
public class ReviewController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReviewController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ReviewServiceImpl reviewService = new ReviewServiceImpl();

		ArrayList<ReviewDto> list = new ArrayList<ReviewDto>();
		list = reviewService.getReviewList(); // 게시글 리스트

		// uploadList = 각각의 게시글에 대한 파일명, 게시글 번호
		ArrayList<ReviewDto> fileList = new ArrayList<ReviewDto>();
		fileList = reviewService.uploadList(); // 게시글 리스트

		System.out.println(fileList);
		
		request.setAttribute("list", list);
		request.setAttribute("fileList", fileList);
		int restaurantNo = Integer.parseInt(request.getParameter("restaurantNo"));
		
		request.setAttribute("restaurantNo", restaurantNo);
		
		RequestDispatcher view = request.getRequestDispatcher("/views/review/review.jsp");
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
