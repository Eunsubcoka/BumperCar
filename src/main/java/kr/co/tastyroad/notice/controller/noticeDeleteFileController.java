package kr.co.tastyroad.notice.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.tastyroad.notice.model.service.noticeServiceImpl;

@WebServlet("/notice/deleteFile.do")
public class noticeDeleteFileController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public noticeDeleteFileController() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int boardNo = Integer.parseInt(request.getParameter("boardno"));

            noticeServiceImpl noticeService = new noticeServiceImpl();
            int result = noticeService.setFileDelete(boardNo);

            if (result == 1) {
                // 실제 파일 시스템에서 파일 삭제
                String uploadDirectory = request.getServletContext().getRealPath("/assets/uploads/notice/");
                String realDirectory = "C:/dev/workspace/semiProject/BumperCar/src/main/webapp/assets/uploads/notice/";
                String fileName = "boardNo_" + boardNo + ".jpg";
                
                File existingTempFile = new File(uploadDirectory, fileName);
                if (existingTempFile.exists()) {
                    existingTempFile.delete();
                }
                
                File existingRealFile = new File(realDirectory, fileName);
                if (existingRealFile.exists()) {
                    existingRealFile.delete();
                }
                
                response.getWriter().write("success");
            } else {
                response.getWriter().write("fail");
            }
        } catch (NumberFormatException e) {
            response.getWriter().write("fail");
        }
    }
}
