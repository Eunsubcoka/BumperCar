package kr.co.tastyroad.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.tastyroad.member.model.dto.Member;
import kr.co.tastyroad.member.model.service.MemberServiceImpl;

@WebServlet("/verify")
public class VerifyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public VerifyServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");

        MemberServiceImpl memberService = new MemberServiceImpl();
        Member member = memberService.getMemberByToken(token);

        if (member != null && !member.isVerified()) {
            memberService.verifyMember(member.getUserId());
            response.getWriter().write("이메일 인증이 완료되었습니다.");
        } else {
            response.getWriter().write("유효하지 않은 토큰이거나 이미 인증된 이메일입니다.");
        }
    }
}
