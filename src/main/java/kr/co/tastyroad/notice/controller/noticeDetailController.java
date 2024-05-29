package kr.co.tastyroad.notice.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.tastyroad.notice.model.dto.noticeDto;
import kr.co.tastyroad.notice.model.service.noticeServiceImpl;

@WebServlet("/notice/detail.do")
public class noticeDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public noticeDetailController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int boardNo = Integer.parseInt(request.getParameter("boardno"));
        
        noticeServiceImpl noticeService = new noticeServiceImpl();
        
        // 해당 boardNo에 대한 상세 정보 조회
        noticeDto result = noticeService.getDetail(boardNo);
        
        if (result != null) { // 결과가 null이 아닌 경우에만 진행
            // 이미지 파일 경로와 파일 이름을 DTO에 설정
            result.setFilePath("/assets/uploads/notice/"); // 이미지 파일이 저장될 경로 지정
            result.setFileName("boardNo_"+boardNo+".jpg"); // 이미지 파일 이름 지정 
            
            noticeService.getFileName(result);
            
            request.setAttribute("result", result);
            
            RequestDispatcher view = request.getRequestDispatcher("/views/notice/noticeDetail.jsp");
            view.forward(request, response);
        } else {
            response.sendRedirect("/error.jsp");
        }
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
