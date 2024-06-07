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
@MultipartConfig(
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

        Member member = memberService.getUserProfile(userNo);
        request.setAttribute("member", member);

        request.getRequestDispatcher("/views/member/profile.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        int userNo = (int) session.getAttribute("userNo");
        String userId = request.getParameter("userId");
        String userName = request.getParameter("userName");
        String userEmail = request.getParameter("userEmail");
        String userAddress = request.getParameter("new-address");
        String userAddress1 = request.getParameter("new-address1");
        String Addr = userAddress + " " + userAddress1;
        String userPhone = request.getParameter("userPhone");

        Member member = new Member();
        member.setUserId(userId);
        member.setUserName(userName);
        member.setUserEmail(userEmail);
        member.setUserAddress(Addr);
        member.setUserPhone(userPhone);
        member.setUserNo(userNo);

        int result = memberService.userUpdate(member);


        String uploadDirectory = "C:\\dev\\work-space\\semiProject\\BumperCar\\src\\main\\webapp\\assets\\image\\member_profile";
        Part filePart = request.getPart("profileImage");

        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            File uploadDir = new File(uploadDirectory);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            member.setFileName(fileName);
            String filePath = uploadDirectory + File.separator + fileName;
            filePart.write(filePath);

            String profileImageUrl = fileName;
            member.setProfileImageUrl(profileImageUrl);
        } else {
            member.setProfileImageUrl(null);
        }
        memberService.updateUserProfile(member);

        request.setAttribute("member", member);

        RequestDispatcher view = request.getRequestDispatcher("/views/member/profile.jsp");
        view.forward(request, response);
    }
}