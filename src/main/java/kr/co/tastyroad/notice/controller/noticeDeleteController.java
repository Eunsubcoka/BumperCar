package kr.co.tastyroad.notice.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.tastyroad.notice.model.service.noticeServiceImpl;

@WebServlet("/notice/delete.do")
public class noticeDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public noticeDeleteController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));
		
		noticeServiceImpl noticeService = new noticeServiceImpl();
		boolean deleted = noticeService.deleteNotice(noticeNo);
		
		 if (deleted) {
	            response.sendRedirect("/notice/list.do?cpage=1&category=noticeTitle&search-text=");
	        
	        }
	}

}
