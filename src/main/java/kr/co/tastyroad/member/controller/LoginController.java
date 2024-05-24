package kr.co.tastyroad.member.controller;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import kr.co.green.member.model.dto.Member;
import kr.co.green.member.model.service.MemberServiceImpl;

@WebServlet("/member/login.do")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberServiceImpl memberService = new MemberServiceImpl();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getParameter("userid");
		String userPwd = request.getParameter("password");
		
		Member member = new Member();
		member.setUserId(userId);
		member.setUserPwd(userPwd);
		
		// 암호화된 패스워드 조회
		Member hashPassword = memberService.getHashPassword(userId);
		
		//
		if(BCrypt.checkpw(userPwd, hashPassword.getUserPwd())) {
			HttpSession session = request.getSession();
			session.setAttribute("userNo", hashPassword.getUserNo());
			session.setAttribute("userName", hashPassword.getUserName());
			session.setAttribute("userType", hashPassword.getUserType());
			
			RequestDispatcher view = request.getRequestDispatcher("/");
			view.forward(request, response);
		}
		// 사용자가 입력한 비밀번호 Vs 데이터베이스에 들어있는 비밀번호
		// select count(*) from member
		// where userId = userid
		// and userPwd = userPwd;
	}
}