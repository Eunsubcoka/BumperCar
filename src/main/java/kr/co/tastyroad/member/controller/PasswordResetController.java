package kr.co.tastyroad.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.tastyroad.member.model.service.MemberServiceImpl;

@WebServlet("/resetPassword.do")
public class PasswordResetController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MemberServiceImpl memberService = new MemberServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        boolean isValid = memberService.verifyResetToken(token);
        if (isValid) {
            request.setAttribute("token", token);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/member/resetPassword.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/views/member/invalidToken.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        String newPassword = request.getParameter("newPassword");

        boolean isReset = memberService.resetPassword(token, newPassword);

        request.setAttribute("isReset", isReset);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/member/passwordResetResult.jsp");
        dispatcher.forward(request, response);
    }
}