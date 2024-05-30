package kr.co.tastyroad.member.controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.tastyroad.common.EmailUtil;

@WebServlet("/member/sendVerificationCode.do")
public class EmailVerificationController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EmailVerificationController() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String verificationCode = generateVerificationCode();
        
        // 이메일 전송
        boolean isSent = EmailUtil.sendEmail(email, "인증 코드", "인증 코드는 " + verificationCode + " 입니다.");

        if (isSent) {
            HttpSession session = request.getSession();
            session.setAttribute("verificationCode", verificationCode);
            response.getWriter().write("sent");
        } else {
            response.getWriter().write("error");
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(999999);
        return String.format("%06d", code);
    }
}

