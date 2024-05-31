package kr.co.tastyroad.notice.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.tastyroad.notice.model.dao.noticeDao;
import kr.co.tastyroad.notice.model.dto.noticeDto;

import java.io.IOException;

@WebServlet("/noticeEditController")
public class noticeEditController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String postId = request.getParameter("postId");

        // 데이터베이스에서 해당 게시글을 불러오기
        noticeDao noticeDao = new noticeDao();
        noticeDto notice = noticeDao.getNotice(postId);

        // 게시글 내용을 요청에 설정
        request.setAttribute("content", notice.getContent());
        request.setAttribute("imagePath", notice.getImagePath());
        request.setAttribute("postId", notice.getPostId());

        // noticeEdit.jsp로 포워딩
        request.getRequestDispatcher("noticeEdit.jsp").forward(request, response);
    }
}
