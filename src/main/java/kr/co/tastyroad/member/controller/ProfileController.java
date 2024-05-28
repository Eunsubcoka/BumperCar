package kr.co.tastyroad.member.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import kr.co.tastyroad.member.model.dto.Member;
import kr.co.tastyroad.member.model.service.MemberServiceImpl;

@WebServlet("/member/profile.do")
public class ProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ProfileController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("/tastyForm/profile.do");
            return;
        }

        MemberServiceImpl memberService = new MemberServiceImpl();
        Member member = memberService.getMemberById(userId);

        request.setAttribute("member", member);
        RequestDispatcher view = request.getRequestDispatcher("/views/member/profile.jsp");
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String userId = request.getParameter("userId");
        String userName = request.getParameter("userName");
        String userEmail = request.getParameter("userEmail");
        String userAddress = request.getParameter("userAddress");
        String userPhone = request.getParameter("userPhone");

        Member member = new Member();
        member.setUserId(userId);
        member.setUserName(userName);
        member.setUserEmail(userEmail);
        member.setUserAddress(userAddress);
        member.setUserPhone(userPhone);

        MemberServiceImpl memberService = new MemberServiceImpl();
        int result = memberService.updateMember(member);

        if (result == 1) {
            response.sendRedirect("/member/profile.do");
        } else {
            response.getWriter().write("<script>alert('정보 수정에 실패했습니다. 다시 시도해 주세요.'); history.back();</script>");
        }
        
        Part filePart = request.getPart("profileImage");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // 파일 저장 경로 설정
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // 파일 저장
        filePart.write(uploadPath + File.separator + fileName);

        // 파일 경로를 데이터베이스에 저장 (생략)

        // 회원 정보 업데이트 로직 (생략)

        response.sendRedirect("/profile");
        
    }
}
