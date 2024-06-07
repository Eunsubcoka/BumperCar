package kr.co.tastyroad.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.tastyroad.member.model.service.MemberServiceImpl;

@WebServlet("/findId.do")
public class FindIdController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MemberServiceImpl memberService = new MemberServiceImpl();

    public FindIdController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/member/findId.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userEmail = request.getParameter("userEmail");
        String userId = memberService.findIdByEmail(userEmail);
        request.setAttribute("userId", userId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/member/findIdResult.jsp");
        dispatcher.forward(request, response);
    }
}
