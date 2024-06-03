package kr.co.tastyroad.notice.controller;

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
        int boardNo = Integer.parseInt(request.getParameter("boardNo"));

        noticeServiceImpl noticeService = new noticeServiceImpl();
        int result = noticeService.setFileDelete(boardNo);

        if (result == 1) {
            response.getWriter().write("success");
        } else {
            response.getWriter().write("fail");
        }
    }
}
