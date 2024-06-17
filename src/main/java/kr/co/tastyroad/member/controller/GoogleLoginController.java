package kr.co.tastyroad.member.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import kr.co.tastyroad.member.model.dto.Member;
import kr.co.tastyroad.member.model.service.MemberServiceImpl;

@WebServlet("/googleLogin.do")
public class GoogleLoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String CLIENT_ID = "553555922743-ld3bj5fceveid5bdlo69ss1uhmqetevt.apps.googleusercontent.com";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idToken = request.getParameter("credential");

        if (idToken == null || idToken.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Credential is missing.");
            return;
        }

        String tokenInfoUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken;
        HttpURLConnection conn = (HttpURLConnection) new URL(tokenInfoUrl).openConnection();
        conn.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            JSONObject tokenInfo = new JSONObject(content.toString());

            if (tokenInfo.has("error_description")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid ID token.");
                return;
            }

            if (!CLIENT_ID.equals(tokenInfo.getString("aud"))) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Audience mismatch.");
                return;
            }

            String googleId = tokenInfo.getString("sub");
            String email = tokenInfo.getString("email");
            String name = tokenInfo.optString("name", "");

            MemberServiceImpl memberService = new MemberServiceImpl();
            Member member = memberService.findOrCreateMember(googleId, email, name);

            HttpSession session = request.getSession();
            session.setAttribute("userNo", member.getUserNo());
            session.setAttribute("userName", member.getUserName());
            session.setAttribute("userType", member.getUserType());

            response.setStatus(HttpServletResponse.SC_OK);
            response.sendRedirect("/");
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Token verification failed.");
            e.printStackTrace();
        }
    }
}
