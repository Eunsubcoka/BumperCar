package kr.co.tastyroad.restaurant.controller;

import java.io.File;
import java.io.IOException;
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

@WebServlet("/restaurantEdit.do")
@MultipartConfig
public class RestaurantEditController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RestaurantEditController() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        request.setCharacterEncoding("utf-8");

        int resNo = Integer.parseInt(request.getParameter("resNo"));
        String name = request.getParameter("restaurantName");
        int category = Integer.parseInt(request.getParameter("category"));
        String phone = request.getParameter("phone");
        String addr = request.getParameter("addr");

        RestaurantDto restaurant = new RestaurantDto();
        restaurant.setRestaurantNo(resNo);
        restaurant.setRestaurantName(name);
        restaurant.setCategory(category);
        restaurant.setRestaurantPhone(phone);
        restaurant.setLocation(addr);

        RestaurantServiceImpl resService = new RestaurantServiceImpl();
        resService.updateRestaurant(restaurant);

        Collection<Part> parts = request.getParts();
        int fileCount = 0;
        String uploadDirectory = getServletContext().getRealPath("/assets/image/");
        File filePath = new File(uploadDirectory);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        for (Part part : parts) {
            if (part.getName().startsWith("file") && part.getSubmittedFileName() != null && !part.getSubmittedFileName().isEmpty()) {
                String fileName = getFileName(part);
                if (fileName != null) {
                    part.write(uploadDirectory + File.separator + fileName);
                    RestaurantDto fileDto = new RestaurantDto();
                    fileDto.setFilePath(uploadDirectory);
                    fileDto.setFileName(fileName);
                    fileDto.setRestaurantNo(resNo);
                    resService.fileUpload(fileDto);
                    fileCount++;
                }
            }
            if (fileCount >= 2) {
                break;
            }
        }

        response.sendRedirect("/index.jsp");
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
