package kr.co.tastyroad.notice.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import kr.co.tastyroad.notice.model.dao.noticeDao;

@WebServlet("/notice/editServlet.do")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
                 maxFileSize=1024*1024*10,      // 10MB
                 maxRequestSize=1024*1024*50)   // 50MB
public class noticeEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String SAVE_DIR = "uploadImages";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 파일 업로드 경로 설정
        String appPath = request.getServletContext().getRealPath("");
        String savePath = appPath + File.separator + SAVE_DIR;

        // 디렉토리가 없으면 생성
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }

        String imagePath = null;
        boolean deleteImage = "true".equals(request.getParameter("deleteImage"));

        // 이미지 파일 처리
        if (!deleteImage) {
            for (Part part : request.getParts()) {
                if (part.getName().equals("image") && part.getSize() > 0) {
                    String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                    part.write(savePath + File.separator + fileName);
                    imagePath = SAVE_DIR + File.separator + fileName;
                }
            }
        }

        // 게시글 수정 로직 (DB 업데이트 등)
        String content = request.getParameter("content");
        String postId = request.getParameter("postId");

        // 데이터베이스에서 해당 게시글을 업데이트
        noticeDao noticeDao = new noticeDao();
        noticeDao.updateNotice(postId, content, deleteImage ? null : imagePath);

        // 수정된 게시글과 이미지 경로를 함께 반환
        request.setAttribute("imagePath", imagePath);
        request.setAttribute("content", content);
        request.setAttribute("postId", postId);
        request.getRequestDispatcher("noticeEdit.jsp").forward(request, response);
    }
}
