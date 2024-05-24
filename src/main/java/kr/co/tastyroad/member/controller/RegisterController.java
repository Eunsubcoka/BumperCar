package kr.co.tastyroad.member.controller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import kr.co.tastyroad.member.model.dto.Member;
import kr.co.tastyroad.member.model.service.MemberServiceImpl;

@WebServlet("/tastyForm/register.do")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegisterController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // doGet 메서드 필요시 구현
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("new-username");
        String userId = request.getParameter("new-userid");
        String userEmail = request.getParameter("new-email");
        String userAddress = request.getParameter("new-address");
        String userPhone = request.getParameter("new-phone");
        String userPwd = request.getParameter("new-password");
        String confirmPwd = request.getParameter("confirm-password");
        String duplicateCheck = request.getParameter("duplicateCheck");

        if("unavailable".equals(duplicateCheck)) {
            response.sendRedirect("/form/registerForm.do");
            return;
        }

        // 이름 유효성 검사
        String namePattern = "^[가-힣]+$";
        Pattern pattern = Pattern.compile(namePattern);
        Matcher nameMatcher = pattern.matcher(userName);

        // 패스워드 유효성 검사
        String passwordPattern = "^(?=.*[A-Z])(?=.*[!@])[a-zA-Z0-9!@]{6,20}$";
        Pattern pwdPattern = Pattern.compile(passwordPattern);
        Matcher pwdMatcher = pwdPattern.matcher(userPwd);

        // 패스워드 암호화
        String salt = BCrypt.gensalt(12);
        String hashPassword = BCrypt.hashpw(userPwd, salt);

        if(nameMatcher.matches() && pwdMatcher.matches()) {
            Member member = new Member();
            member.setUserName(userName);
            member.setUserId(userId);
            member.setUserEmail(userEmail);
            member.setUserAddress(userAddress);
            member.setUserPhone(userPhone);
            member.setUserPwd(hashPassword);
            member.setConfirmPwd(confirmPwd);

            MemberServiceImpl memberService = new MemberServiceImpl();
            int result = memberService.register(member);

            if(result == 1) { // 성공
                RequestDispatcher view = request.getRequestDispatcher("/views/member/login.jsp");
                view.forward(request, response);
            } else { // 실패
                RequestDispatcher view = request.getRequestDispatcher("/views/member/register.jsp");
                view.forward(request, response);
            }
        } else if(!nameMatcher.matches()) { // 이름이 한글이 아닐 때
            returnAlert(response, "이름은 한글만 가능합니다.");
        } else if(!pwdMatcher.matches()) { // 패스워드 조건에 맞지 않을 때
            returnAlert(response, "패스워드 정책에 맞지 않습니다.");
        }
    }

    private void returnAlert(HttpServletResponse response, String msg) throws IOException {
        response.getWriter().write("<script>"
                + "alert('" + msg + "');"
                + "location.href='/form/registerForm.do';"
                + "</script>");
    }
}
