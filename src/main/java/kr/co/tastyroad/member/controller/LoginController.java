package kr.co.tastyroad.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import kr.co.tastyroad.member.model.dto.Member;
import kr.co.tastyroad.member.model.service.MemberServiceImpl;

@WebServlet("/tastyForm/login.do")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			MemberServiceImpl memberService = new MemberServiceImpl();
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			String userId = request.getParameter("userid");
			String userPwd = request.getParameter("password");
			
			Member member = new Member();
			member.setUserId(userId);
			member.setUserPwd(userPwd);
			Member hashPassword = memberService.getHashPassword(userId);

			if(BCrypt.checkpw(userPwd, hashPassword.getUserPwd())) {
				HttpSession session = request.getSession();
				session.setAttribute("userNo", hashPassword.getUserNo());
				session.setAttribute("userName", hashPassword.getUserName());
				session.setAttribute("userType", hashPassword.getUserType());
				
				RequestDispatcher view = request.getRequestDispatcher("/");
				view.forward(request, response);
			} else {
				request.setAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다.");
				RequestDispatcher view = request.getRequestDispatcher("/views/member/login.jsp");
				view.forward(request, response);
			}
		}catch(Exception e) {
			e.printStackTrace();
			response.sendRedirect("/views/error.html");
		}
		
	}
}