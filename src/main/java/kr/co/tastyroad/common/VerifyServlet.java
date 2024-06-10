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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=UTF-8");
        String token = request.getParameter("token");

        if (token == null || token.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("토큰이 누락되었습니다.");
            return;
        }

        MemberServiceImpl memberService = new MemberServiceImpl();
        try {
            Member member = memberService.getMemberByToken(token);

            if (member != null && !member.isVerified()) {
                memberService.verifyMember(member.getUserId());
                response.getWriter().write("이메일 인증이 완료되었습니다.");
            } else if (member == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("유효하지 않은 토큰입니다.");
            } else {
                response.getWriter().write("이미 인증된 이메일입니다.");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("서버 오류가 발생했습니다. 나중에 다시 시도해 주세요.");
            e.printStackTrace();
        }
    }
}
