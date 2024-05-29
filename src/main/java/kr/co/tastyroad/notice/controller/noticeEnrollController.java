package kr.co.tastyroad.notice.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import kr.co.tastyroad.notice.model.dto.noticeDto;
import kr.co.tastyroad.notice.model.service.noticeServiceImpl;

@WebServlet("/notice/enroll.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class noticeEnrollController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public noticeEnrollController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // doGet()에서의 처리가 필요 없는 경우 비워둡니다.
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String title = request.getParameter("title");
        String content = request.getParameter("content");

        HttpSession session = request.getSession();
        int userNo = (int) session.getAttribute("userNo");
        String userType = (String) session.getAttribute("userType");

        noticeDto noticeDto = new noticeDto();
        noticeDto.setNoticeTitle(title);
        noticeDto.setNoticeContent(content);
        noticeDto.setUserNo(userNo);
        noticeDto.setUserType(userType);

        //절대 경로로 했을 시엔 요렇게
//        String uploadDirectory = "C:\\dev\\workspace\\semiProject\\BumperCar\\src\\main\\webapp\\assets\\uploads\\notice\\";
        //상대 경로로 했을땐 요렇게 -> 상대 경로로 해야 프로젝트를 다른사람한테 넘겨도 그대로 작동 가능 
        String uploadDirectory = request.getServletContext().getRealPath("/assets/uploads/notice/");
        File filePath = new File(uploadDirectory);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        noticeServiceImpl noticeService = new noticeServiceImpl();
        int result = noticeService.enroll(noticeDto);

        // 등록한 게시물의 번호 조회
        noticeDto resultDto = noticeService.selectNo(noticeDto);
        int boardNo = resultDto.getNoticeNo();

        

        boolean fileUploaded = false;
        // 파일 업로드 처리
        Part uploadFilePart = request.getPart("uploadFile");
        System.out.println(uploadFilePart);
        
        String fileName = null;
        if (uploadFilePart != null && uploadFilePart.getSize() > 0) {
            // 파일이 첨부된 경우에만 파일 업로드 처리
            // 이미지 파일의 이름 설정 (게시물 번호와 함께 저장)
            fileName = "boardNo_" + boardNo + ".jpg"; // 예시 파일 이름
            noticeDto.setFileName(fileName);
            uploadFilePart.write(filePath + File.separator + fileName);

            // 파일 업로드 정보를 DB에 저장
            noticeDto.setFilePath(uploadDirectory);
            int resultUpload = noticeService.fileUpload(noticeDto);
            
            fileUploaded = true; // 파일이 업로드되었음을 표시
        } 
            // 파일이 업로드되지 않은 경우 처리
            // 파일 업로드 정보를 비워줌
        if (!fileUploaded) {
            noticeDto.setFileName(null); // 파일명을 비워줌
            noticeDto.setFilePath(null); // 파일 경로를 비워줌
        }

        if (result == 1) {
            response.sendRedirect("/notice/list.do?cpage=1&category=noticeTitle&search-text=");
        }
    }


}
