package kr.co.tastyroad.notice.controller;

import java.io.IOException;
import java.util.ArrayList;
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
    	try {

    		int boardNo = Integer.parseInt(request.getParameter("boardno"));

            noticeServiceImpl noticeService = new noticeServiceImpl();

            // 해당 boardNo에 대한 상세 정보 조회
            noticeDto result = noticeService.getDetail(boardNo);

            // 게시글 목록 조회
            ArrayList<noticeDto> list = noticeService.getNoticeList(); 
            
            // 최신 공지사항 5개 조회
            ArrayList<noticeDto> latestNotices = noticeService.getLatestNotices(5);

            if (result != null) { // 결과가 null이 아닌 경우에만 진행
                noticeService.getFileName(result); // 파일 이름과 경로 가져오기

                // 파일 경로와 이름이 null인지 확인하여 설정
                if (result.getFileName() == null || result.getFileName().isEmpty() || result.getFilePath() == null || result.getFilePath().isEmpty()) {
                    result.setFileName("");
                    result.setFilePath("");
                }

                request.setAttribute("result", result);
                request.setAttribute("list", list); 
                request.setAttribute("row", list.size()); 
                request.setAttribute("latestNotices", latestNotices); // 최신 공지사항 추가

                RequestDispatcher view = request.getRequestDispatcher("/views/notice/noticeDetail.jsp");
                view.forward(request, response);
            } else {
                response.sendRedirect("/views/error.html");
            }
        }catch(Exception e) {
            e.printStackTrace();
            response.sendRedirect("/views/error.html");
        }
    	
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
