package kr.co.tastyroad.review.controller;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import kr.co.tastyroad.review.model.dto.ReviewDto;
import kr.co.tastyroad.review.model.service.ReviewServiceImpl;


@WebServlet("/review/reviewEnroll.do")
public class ReviewEnrollController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReviewEnrollController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 사용자가 보낸 데이터를 UTF-8로 인코딩
		response.setContentType("text/html; charset=UTF-8");
		
		String reviewTitle = request.getParameter("reviewTitle");
		String reviewContent = request.getParameter("reviewContent");
		int reviewRating = Integer.parseInt(request.getParameter("ratingStars"));
		int restaurantNo = Integer.parseInt(request.getParameter("restaurantNo"));

		
		//어떤 회원이 글작성했는지 
		HttpSession session = request.getSession();
		int userNo = (int)session.getAttribute("userNo");

		ReviewDto reviewDto = new ReviewDto();
		reviewDto.setReviewTitle(reviewTitle);
		reviewDto.setReviewContent(reviewContent);
		reviewDto.setUserNo(userNo);
		reviewDto.setRatings(reviewRating);
		reviewDto.setRestaurantNo(restaurantNo);
		
		//파일 업로드
		Collection<Part> parts = request.getParts();
		String uploadDirectory = "C:\\dev\\work-space\\semiProject\\BumperCar\\src\\main\\webapp\\assets\\uploads\\review";
		
		//파일 업로드 디렉토리가 존재하지 않으면 생성
		File filePath = new File(uploadDirectory);
		if(!filePath.exists()) {
			filePath.mkdirs();
		}
		
		String fileName = null;
		
		ReviewServiceImpl reviewService = new ReviewServiceImpl();
		int result = reviewService.enroll(reviewDto);
		

		ReviewDto resultDto = reviewService.selectNo(reviewDto);

		for(Part part : parts) {
			fileName = getFileName(part);
			if(fileName != null) {
				part.write(filePath + File.separator + fileName);  // 실질적으로 파일을 업로드 해주는 코드
				
				resultDto.setFilePath(uploadDirectory);
				resultDto.setFileName(fileName);
				
				int resultUpload = reviewService.fileUpload(resultDto);
			}
		}
		
		if(result == 1) {
			response.sendRedirect("/review/review.do?restaurantNo="+restaurantNo);
		}
		
	}
	
//	 파일 이름을 추출하는 메서드
    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        
        System.out.println("contentDisposition : " + contentDisposition);
        String[] tokens = contentDisposition.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 2, token.length() - 1);
            }
        }
        return null;
    }

}
