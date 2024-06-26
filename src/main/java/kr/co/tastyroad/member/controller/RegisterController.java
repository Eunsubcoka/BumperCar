package kr.co.tastyroad.member.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import kr.co.tastyroad.common.EmailUtil;
import kr.co.tastyroad.member.model.dto.Member;
import kr.co.tastyroad.member.model.service.MemberServiceImpl;

@WebServlet("/register.do")
public class RegisterController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegisterController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/views/member/register.jsp");
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String userName = request.getParameter("new-username");
        String userId = request.getParameter("new-userid");
        String userEmail = request.getParameter("new-email");
        String userAddress = request.getParameter("new-address");
        String userAddress1 = request.getParameter("new-address1");
        String userPhone = request.getParameter("new-phone");
        String userPwd = request.getParameter("new-password");
        String confirmPwd = request.getParameter("confirm-password");
        String duplicateCheck = request.getParameter("duplicateCheck");
        String fullAddr = userAddress + " " + userAddress1;

        if ("unavailable".equals(duplicateCheck)) {
            returnAlert(response, "아이디가 중복되었습니다. 다른 아이디를 사용해 주세요.");
            return;
        }

        if (!userPwd.equals(confirmPwd)) {
            returnAlert(response, "비밀번호가 일치하지 않습니다.");
            return;
        }

        String namePattern = "^[가-힣]+$";
        Pattern pattern = Pattern.compile(namePattern);
        Matcher nameMatcher = pattern.matcher(userName);

        String passwordPattern = "^(?=.*[A-Z])(?=.*[!@])[a-zA-Z0-9!@]{6,20}$";
        Pattern pwdPattern = Pattern.compile(passwordPattern);
        Matcher pwdMatcher = pwdPattern.matcher(userPwd);

        if (!nameMatcher.matches()) {
            returnAlert(response, "이름은 한글만 가능합니다.");
            return;
        }

        if (!pwdMatcher.matches()) {
            returnAlert(response, "비밀번호는 최소 8자 이상이어야 하며, 대문자와 특수문자(!@)를 포함해야 합니다.");
            return;
        }

        String salt = BCrypt.gensalt(12);
        String hashPassword = BCrypt.hashpw(userPwd, salt);
        String token = UUID.randomUUID().toString();

        Member member = new Member();
        member.setUserName(userName);
        member.setUserId(userId);
        member.setUserEmail(userEmail);
        member.setUserAddress(fullAddr);
        member.setUserPhone(userPhone);
        member.setUserPwd(hashPassword);
        member.setToken(token);
        member.setVerified(false);

        MemberServiceImpl memberService = new MemberServiceImpl();
        int result = 0;

        try {
            result = memberService.register(member);
        } catch (SQLException e) {
            e.printStackTrace();
            returnAlert(response, "회원가입에 실패했습니다. 다시 시도해 주세요.");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            returnAlert(response, "회원가입에 실패했습니다. 다시 시도해 주세요.");
            return;
        }
        if (result == 1) {
            RequestDispatcher view = request.getRequestDispatcher("/views/member/login.jsp");
            view.forward(request, response);
        } else {
            returnAlert(response, "회원가입에 실패했습니다. 다시 시도해 주세요.");
        }
    }


    private void returnAlert(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write("<script>"
                + "alert('" + msg + "');"
                + "location.href='/member/register.do';"
                + "</script>");
    }
}