package kr.co.tastyroad.member.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

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
@MultipartConfig (
fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
maxFileSize = 1024 * 1024 * 10,      // 10 MB
maxRequestSize = 1024 * 1024 * 15    // 15 MB
)
public class ProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MemberServiceImpl memberService = new MemberServiceImpl();
    
    public ProfileController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
        int userNo = (int) session.getAttribute("userNo"); // 세션에서 userNo 가져오기

        Member member = MemberServiceImpl.getUserProfile(userNo);

        request.setAttribute("member", member);

        request.getRequestDispatcher("/views/member/profile.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
		int userNo = (int)session.getAttribute("userNo");
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
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        String filePath = uploadPath + File.separator + fileName;
        filePart.write(filePath);

        String imageUrl = "uploads/" + fileName;
        
        
        
        MemberServiceImpl userService = new MemberServiceImpl();
        userService.updateUserProfile(userId, userName, userEmail, userAddress, userPhone, fileName);

        response.sendRedirect("/views/member/profile.jsp");
//        if (filePart != null && filePart.getSubmittedFileName() != null && !filePart.getSubmittedFileName().isEmpty()) {
//            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
//
//            // 파일 저장 경로 설정
//            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
//            File uploadDir = new File(uploadPath);
//            if (!uploadDir.exists()) {
//                uploadDir.mkdir();
//            }
//
//            // 파일 저장
//            filePart.write(uploadPath + File.separator + fileName);
//
//            // 파일 경로를 데이터베이스에 저장 (생략)
//
//            // 회원 정보 업데이트 로직 (생략)
//        } else {
//            response.getWriter().write("<script>alert('프로필 이미지를 선택하지 않았습니다.'); history.back();</script>");
//            return; // 오류 발생 시 이후 코드 실행을 막기 위해 return 추가
//        }
//
//        response.sendRedirect("/views/member/profile.jsp");
        
    }
}
