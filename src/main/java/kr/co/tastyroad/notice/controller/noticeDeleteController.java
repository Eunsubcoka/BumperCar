package kr.co.tastyroad.notice.controller;

import java.io.File;
import java.io.IOException;

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
		try {
			int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));
			
			noticeServiceImpl noticeService = new noticeServiceImpl();
			boolean deleted = noticeService.deleteNotice(noticeNo);
			
			if (deleted) {
				// 실제 파일 시스템에서 파일 삭제
				String uploadDirectory = request.getServletContext().getRealPath("/assets/uploads/notice/");
				String realDirectory = "C:/dev/workspace/semiProject/BumperCar/src/main/webapp/assets/uploads/notice/";
				String fileName = "boardNo_" + noticeNo + ".jpg";
				
				File existingTempFile = new File(uploadDirectory, fileName);
				if (existingTempFile.exists()) {
					existingTempFile.delete();
				}
				
				File existingRealFile = new File(realDirectory, fileName);
				if (existingRealFile.exists()) {
					existingRealFile.delete();
				}
				
	            response.sendRedirect("/notice/list.do?cpage=1&category=noticeTitle&search-text=");
	        }
		}catch(Exception e) {
			e.printStackTrace();
			response.sendRedirect("/views/error.html");
		}
		
		
	}
}
