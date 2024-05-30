package kr.co.tastyroad.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/member/verifyCode.do")
public class CodeVerificationController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CodeVerificationController() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        HttpSession session = request.getSession();
        String verificationCode = (String) session.getAttribute("verificationCode");

        if (verificationCode != null && verificationCode.equals(code)) {
            response.getWriter().write("verified");
        } else {
            response.getWriter().write("error");
        }
    }
}
