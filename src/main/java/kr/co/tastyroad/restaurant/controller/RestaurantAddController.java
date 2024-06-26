package kr.co.tastyroad.restaurant.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;
import kr.co.tastyroad.restaurant.model.service.RestaurantServiceImpl;

@WebServlet("/restaurantAdd.do")
@MultipartConfig(
	    fileSizeThreshold = 1024 * 1024 * 1, // 1MB
	    maxFileSize = 1024 * 1024 * 50,      // 50MB
	    maxRequestSize = 1024 * 1024 * 100   // 100MB (필요에 따라 조정 가능)
	)

public class RestaurantAddController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RestaurantAddController() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        request.setCharacterEncoding("utf-8");

        String name = request.getParameter("restaurantName");
        int category = Integer.parseInt(request.getParameter("category"));
        String phone = request.getParameter("phone");
        String addr = request.getParameter("addr");

        RestaurantDto restaurant = new RestaurantDto();
        restaurant.setRestaurantName(name);
        restaurant.setCategory(category);
        restaurant.setRestaurantPhone(phone);
        restaurant.setLocation(addr);

        RestaurantServiceImpl resService = new RestaurantServiceImpl();
        int no = resService.addRestaurant(restaurant);

        ArrayList<RestaurantDto> menuList = new ArrayList<>();
        int count = 1;
        while (request.getParameter("menu" + count) != null) {
            String food = request.getParameter("menu" + count);
            int price = Integer.parseInt(request.getParameter("price" + count));

            RestaurantDto menu = new RestaurantDto();
            menu.setMenu(food);
            menu.setPrice(price);
            menu.setRestaurantNo(no);
            menuList.add(menu);
            count++;
        }
        resService.addMenu(menuList);

        ArrayList<RestaurantDto> tagList = new ArrayList<>();
        int tagCount = 1;
        while (request.getParameter("tag" + tagCount) != null) {
            String tag = request.getParameter("tag" + tagCount);

            RestaurantDto tagDto = new RestaurantDto();
            tagDto.setTag(tag);
            tagDto.setRestaurantNo(no);
            tagList.add(tagDto);
            tagCount++;
        }
        resService.addTag(tagList);

        Collection<Part> fileParts = request.getParts();
        int imageCount = 0;
        for (Part filePart : fileParts) {
            if (filePart.getName().equals("file") && filePart.getSubmittedFileName() != null && !filePart.getSubmittedFileName().isEmpty()) {
                if (imageCount >= 2) break; // 최대 2개의 이미지 처리
                String fileName = getFileName(filePart);
                String uploadDirectory = getServletContext().getRealPath("/assets/image/");
                File filePath = new File(uploadDirectory);
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }
                filePart.write(uploadDirectory + File.separator + fileName);

                RestaurantDto fileDto = new RestaurantDto();
                fileDto.setFilePath(uploadDirectory);
                fileDto.setFileName(fileName);
                fileDto.setRestaurantNo(no);

                resService.fileUpload(fileDto);
                imageCount++;
            }
        }

        response.sendRedirect("/");
    }

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