package kr.co.tastyroad.notice.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, 
        maxFileSize = 1024 * 1024 * 10, 
        maxRequestSize = 1024 * 1024 * 50) 
public class noticeEnrollController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public noticeEnrollController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	try {
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

            noticeServiceImpl noticeService = new noticeServiceImpl();
            int result = noticeService.enroll(noticeDto);

            // 등록한 게시물의 번호 조회
            noticeDto resultDto = noticeService.selectNo(noticeDto);
            int boardNo = resultDto.getNoticeNo();

            // 파일 업로드 처리
            Part uploadFilePart = request.getPart("uploadFile");
            
            if (uploadFilePart != null && uploadFilePart.getSize() > 0) {
                // 파일이 첨부된 경우에만 파일 업로드 처리
                String uploadDirectory = request.getServletContext().getRealPath("/assets/uploads/notice/");
                File filePath = new File(uploadDirectory);
                //경로가 없을경우
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }
                String fileName = "boardNo_" + boardNo + ".jpg"; // 파일이름 지정 
                String tempFilePath = new File(filePath, fileName).getAbsolutePath(); 
                
                // 임시 디렉토리에 파일 저장
                uploadFilePart.write(tempFilePath);

                // 프로젝트의 실제 경로에 파일 저장
                String realDirectory = "C:/dev/workspace/semiProject/BumperCar/src/main/webapp/assets/uploads/notice/";
                File realFilePath = new File(realDirectory);
                //경로가 없을경우
                if (!realFilePath.exists()) {
                    realFilePath.mkdirs();
                }
                // 서버 임시 디렉토리에 저장된 파일들을, 내가 지정한 경로에 다시 저장하는 코드
                Files.copy(Paths.get(tempFilePath), Paths.get(realFilePath.getAbsolutePath(), fileName), StandardCopyOption.REPLACE_EXISTING);

                noticeDto.setFileName(fileName);
                noticeDto.setFilePath("/assets/uploads/notice/");
                noticeService.fileUpload(noticeDto);
            }

            if (result == 1) {
                response.sendRedirect("/notice/list.do?cpage=1&category=noticeTitle&search-text=");
            }
        }catch(Exception e) {
            e.printStackTrace();
            response.sendRedirect("/views/error.html");
        }
        
    }
}
