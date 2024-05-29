package kr.co.tastyroad.member.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import kr.co.tastyroad.member.model.dto.Member;
import kr.co.tastyroad.member.model.service.MemberServiceImpl;

@WebServlet("/profile.do")
@MultipartConfig 
public class ProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ProfileController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        String userId = (String) session.getAttribute("userId");
//
//        if (userId == null) {
//            response.sendRedirect("/tastyForm/profile.do");
//            return;
//        }
//
//        MemberServiceImpl memberService = new MemberServiceImpl();
//        Member member = memberService.getMemberById(userId);
//
//        request.setAttribute("member", member);
//        RequestDispatcher view = request.getRequestDispatcher("/views/member/profile.jsp");
//        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
		int userNo = (int)session.getAttribute("userNo");
        System.out.println("프로필 컨트롤러");
        String userId = request.getParameter("userId");
        String userName = request.getParameter("userName");
        String userEmail = request.getParameter("userEmail");
        String userAddress = request.getParameter("new-address");
        String userAddress1 = request.getParameter("new-address1");
        String Addr = userAddress+" "+userAddress1;
        String userPhone = request.getParameter("userPhone");
       

        Member member = new Member();
        member.setUserId(userId);
        member.setUserName(userName);
        member.setUserEmail(userEmail);
        member.setUserAddress(Addr);
        member.setUserPhone(userPhone);
        member.setUserNo(userNo);

        MemberServiceImpl memberService = new MemberServiceImpl();
        int result = memberService.userUpdate(member);

        
        Part filePart = request.getPart("profileImage");
        
        if (filePart != null && filePart.getSubmittedFileName() != null && !filePart.getSubmittedFileName().isEmpty()) {
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
        } else {
            response.getWriter().write("<script>alert('프로필 이미지를 선택하지 않았습니다.'); history.back();</script>");
            return; // 오류 발생 시 이후 코드 실행을 막기 위해 return 추가
        }

        response.sendRedirect("/views/member/profile.jsp");
        
    }
}
