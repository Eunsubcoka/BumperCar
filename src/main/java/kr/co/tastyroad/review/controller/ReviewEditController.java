package kr.co.tastyroad.review.controller;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import kr.co.tastyroad.review.model.dto.ReviewDto;
import kr.co.tastyroad.review.model.service.ReviewServiceImpl;

@WebServlet("/review/reviewEdit.do")
public class ReviewEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReviewEditController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

        	request.setCharacterEncoding("UTF-8"); // 사용자가 보낸 데이터를 UTF-8로 인코딩
    		response.setContentType("text/html; charset=UTF-8"); 
    		
    		int reviewNo = Integer.parseInt(request.getParameter("reviewNo"));
    		int restaurantNo = Integer.parseInt(request.getParameter("restaurantNo"));
    		int ratings = Integer.parseInt(request.getParameter("ratings"));
    		String reviewTitle = request.getParameter("reviewTitle");
    		String reviewContent = request.getParameter("reviewContent");
    		String fileName = request.getParameter("fileName");
    		String filePath = request.getParameter("filePath");
    		
    		
    		ReviewDto reviewDto = new ReviewDto();
    		reviewDto.setReviewNo(reviewNo);
    		reviewDto.setRestaurantNo(restaurantNo);
    		reviewDto.setRatings(ratings);
    		reviewDto.setReviewTitle(reviewTitle);
    		reviewDto.setReviewContent(reviewContent);
    		reviewDto.setFileName(fileName);
    		reviewDto.setFilePath(filePath);

    		
    		ReviewServiceImpl reviewService = new ReviewServiceImpl();
    		int result = reviewService.editUpdate(reviewDto);
    		
    		if(result == 1) {  
    			//파일 업로드 설정
    			Collection<Part> parts = request.getParts();
    			String uploadDirectory = request.getServletContext().getRealPath("/assets/uploads/review");
    			
    			// 이미 업로드 되어있는 이미지 삭제,  Edit.jsp에서 maxIndex 변수선언
    			int maxIndex = Integer.parseInt(request.getParameter("maxIndex"));
    			for(int i = 1; i <= maxIndex; i++) {
    				// removeImageName-, removeImageStatus- 받기
    				String removeImageName = request.getParameter("removeImageName-" + i);
    				try {
    					String removeImageStatus = request.getParameter("removeImageStatus-" + i);
    					// removeImageStatus- 가 true인 애가 있는지 체크
    					// 만약 있으면 삭제를 해야 되는데 uploadDirectory + File.separator + removeImageName
    					if(!removeImageStatus.equals("none")) {
    						File file = new File(uploadDirectory + File.separator + removeImageStatus);
    						if (file.exists()) {
    							file.delete();
    						}
    						// 데이터베이스에서도 해당 파일 삭제
    						reviewService.delete(reviewDto, removeImageStatus);
    					}
    				} catch(NullPointerException e) {
    					
    				}
    			}

    			//파일 업로드 디렉토리가 존재하지 않으면 생성
    			File editFilePath = new File(uploadDirectory);
    			if(!editFilePath.exists()) {
    				editFilePath.mkdirs();
    			}
    			
    			for(Part part : parts) {
    				fileName = getFileName(part);
    				if(fileName != null && !fileName.isEmpty()) {
    					part.write(editFilePath + File.separator + fileName);  // 실질적으로 파일을 업로드 해주는 코드
    					
    					reviewDto.setFilePath(uploadDirectory);
    					reviewDto.setFileName(fileName);
    					reviewDto.setReviewNo(reviewNo);
    					// 새로운 이미지 레코드 삽입
    					reviewService.fileUpload(reviewDto);
    				}
    			}
    			
    			response.sendRedirect("/review/review.do?reviewNo=" + reviewNo + "&restaurantNo=" + restaurantNo);
    		}
    		else {
    			response.sendRedirect("/");
    		}
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/views/error.html");
        }
		
	}
//	 파일 이름을 추출하는 메서드
   private String getFileName(Part part) {
       String contentDisposition = part.getHeader("content-disposition");
       String[] tokens = contentDisposition.split(";");
       for (String token : tokens) {
           if (token.trim().startsWith("filename")) {
               return token.substring(token.indexOf('=') + 2, token.length() - 1);
           }
       }
       return null;
   }
	
}
