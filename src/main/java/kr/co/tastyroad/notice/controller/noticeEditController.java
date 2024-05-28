package kr.co.tastyroad.notice.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.tastyroad.notice.model.dto.noticeDto;
import kr.co.tastyroad.notice.model.service.noticeServiceImpl;

@WebServlet("/notice/edit.do")
public class noticeEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public noticeEditController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardNo = Integer.parseInt(request.getParameter("boardno"));
		String noticeTitle = request.getParameter("title");
		String noticeContent = request.getParameter("content");
		
		noticeDto noticeDto =new noticeDto();
		noticeDto.setNoticeNo(boardNo);
		noticeDto.setNoticeTitle(noticeTitle);
		noticeDto.setNoticeContent(noticeContent);
		
		noticeServiceImpl noticeService = new noticeServiceImpl();
		int result = noticeService.setEdit(noticeDto);
		
		if(result ==1) {
			response.sendRedirect("/notice/detail.do?boardno="+boardNo);
		}
	}

}
