package kr.co.tastyroad.notice.controller;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import kr.co.tastyroad.notice.model.dto.noticeDto;
import kr.co.tastyroad.notice.model.service.noticeServiceImpl;

@WebServlet("/notice/enroll.do")
public class noticeEnrollController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public noticeEnrollController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String title = request.getParameter("title");
		String content = request.getParameter("content");

		HttpSession session = request.getSession();
		int userNo = (int) session.getAttribute("userNo");
		String userType = (String) session.getAttribute("userType");

		noticeDto noticeDto = new noticeDto();
		noticeDto.setNoticeTitle(title);
		noticeDto.setNoticeContent(content);
		
		noticeDto.setUserNo(userNo);
		noticeDto.setUserType(userType);

		Collection<Part> parts = request.getParts();
		String uploadDirectory = "C:\\dev\\work-space\\7. Servlet\\Semiproject\\BumperCar\\src\\main\\webapp\\assets\\uploads\\notice";

		File filePath = new File(uploadDirectory);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		String fileName = null;

		noticeServiceImpl noticeService = new noticeServiceImpl();
		int result = noticeService.enroll(noticeDto);

		noticeDto resultDto = noticeService.selectNo(noticeDto);

		for (Part part : parts) {
			fileName = getFileName(part);
			if (fileName != null) {
				part.write(filePath + File.separator + fileName);

				noticeDto.setFilePath(uploadDirectory);
				noticeDto.setFileName(fileName);

				int resultUpload = noticeService.fileUpload(noticeDto);
			}		
		}
		if (result == 1) {
			response.sendRedirect("/notice/list.do?cpage=1&category=fb_title&search-text=");
		}
	}
	
	private String getFileName(Part part) {
		String contentDisposition = part.getHeader("content-disposition");
		String[] tokens = contentDisposition.split(";");
		for (String token : tokens) {
			if(token.trim().startsWith("filename")) {
				return token.substring(token.indexOf('=') + 2, token.length() -1);
			}
		}
		
		return null;
		
	}

}
