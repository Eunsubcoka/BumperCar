package kr.co.tastyroad.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.tastyroad.member.model.service.MemberServiceImpl;

@WebServlet("/findPassword.do")
public class FindPasswordController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MemberServiceImpl memberService = new MemberServiceImpl();

    public FindPasswordController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/member/findPassword.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String userEmail = request.getParameter("userEmail");
        boolean isSent = memberService.sendPasswordResetEmail(userId, userEmail);
        request.setAttribute("isSent", isSent);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/member/findPasswordResult.jsp");
        dispatcher.forward(request, response);
    }
}
