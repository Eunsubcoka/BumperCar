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
import javax.servlet.http.Part;

import kr.co.tastyroad.notice.model.dto.noticeDto;
import kr.co.tastyroad.notice.model.service.noticeServiceImpl;

@WebServlet("/notice/edit.do")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class noticeEditController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public noticeEditController() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	try {
    		request.setCharacterEncoding("UTF-8");
            int boardNo = Integer.parseInt(request.getParameter("boardno"));
            String noticeTitle = request.getParameter("title");
            String noticeContent = request.getParameter("content");

            noticeDto noticeDto = new noticeDto();
            noticeDto.setNoticeNo(boardNo);
            noticeDto.setNoticeTitle(noticeTitle);
            noticeDto.setNoticeContent(noticeContent);

            noticeServiceImpl noticeService = new noticeServiceImpl();
            int result = noticeService.setEdit(noticeDto);

            // 파일 업로드 처리
            Part uploadFilePart = request.getPart("uploadFile");
            boolean deleteFile = request.getParameter("deleteFile") != null;
            String uploadDirectory = request.getServletContext().getRealPath("/assets/uploads/notice/");
            File filePath = new File(uploadDirectory);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }

            if (deleteFile) {
                int fileNo = noticeDto.getFileNo();
                if (fileNo > 0) {
                    noticeService.setFileDelete(boardNo);
                    
                    // 실제 파일 시스템에서 파일 삭제
                    String existingFileName = "boardNo_" + boardNo + ".jpg"; 
                    File existingTempFile = new File(filePath, existingFileName);
                    if (existingTempFile.exists()) {
                        existingTempFile.delete();
                    }

                    String realDirectory = "C:/dev/workspace/semiProject/BumperCar/src/main/webapp/assets/uploads/notice/";
                    File existingRealFile = new File(realDirectory, existingFileName);
                    if (existingRealFile.exists()) {
                        existingRealFile.delete();
                    }
                    
                    noticeDto.setFileName(null);
                    noticeDto.setFilePath(null);
                }
            } else if (uploadFilePart != null && uploadFilePart.getSize() > 0) {
                String fileName = "boardNo_" + boardNo + ".jpg"; //파일 이름 지정 
                String tempFilePath = new File(filePath, fileName).getAbsolutePath();

                // 임시 디렉토리에 파일 저장
                uploadFilePart.write(tempFilePath);

                // 프로젝트의 실제 경로에 파일 저장
                String realDirectory = "C:/dev/workspace/semiProject/BumperCar/src/main/webapp/assets/uploads/notice/";
                File realFilePath = new File(realDirectory);
                if (!realFilePath.exists()) {
                    realFilePath.mkdirs();
                }
                Files.copy(Paths.get(tempFilePath), Paths.get(realFilePath.getAbsolutePath(), fileName), StandardCopyOption.REPLACE_EXISTING);

                noticeDto.setFileName(fileName);
                noticeDto.setFilePath("/assets/uploads/notice/");
                noticeService.fileUpload(noticeDto);
            }

            if (result == 1) {
                response.sendRedirect("/notice/detail.do?boardno=" + boardNo);
            }
        }catch(Exception e) {
            e.printStackTrace();
            response.sendRedirect("/views/error.html");
        }
        
    }
}
