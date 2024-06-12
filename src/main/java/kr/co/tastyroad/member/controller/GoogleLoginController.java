package kr.co.tastyroad.member.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import kr.co.tastyroad.member.model.dto.Member;
import kr.co.tastyroad.member.model.service.MemberServiceImpl;

@WebServlet("/tastyForm/googleLogin.do")
public class GoogleLoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String credential = request.getParameter("credential");
        NetHttpTransport transport = new NetHttpTransport();
        JacksonFactory jsonFactory = new JacksonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(java.util.Collections.singletonList("YOUR_CLIENT_ID")) // 구글 클라이언트 ID로 교체해야 합니다.
                .build();

        try {
            GoogleIdToken idToken = verifier.verify(credential);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                String userId = payload.getSubject(); // 고유 사용자 ID
                String email = payload.getEmail();
                String name = (String) payload.get("name");

                // 사용자 정보를 조회하거나 새로 생성
                MemberServiceImpl memberService = new MemberServiceImpl();
                Member member = memberService.findOrCreateMember(userId, email, name);

                HttpSession session = request.getSession();
                session.setAttribute("userNo", member.getUserNo());
                session.setAttribute("userName", member.getUserName());
                session.setAttribute("userType", member.getUserType());

                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "구글 인증 실패");
            }
        } catch (GeneralSecurityException e) {
            throw new ServletException("구글 인증 오류", e);
        }
    }
}
