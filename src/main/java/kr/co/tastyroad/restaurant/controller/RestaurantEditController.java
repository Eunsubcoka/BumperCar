package kr.co.tastyroad.restaurant.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;
import kr.co.tastyroad.restaurant.model.service.RestaurantServiceImpl;

@WebServlet("/restaurantEdit.do")
public class RestaurantEditController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RestaurantEditController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
		
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        request.setCharacterEncoding("utf-8");

        try {
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

            updateMenus(request, resService, resNo);
            updateTags(request, resService, resNo);
            resService.deleteImg(resNo);
            Collection<Part> parts = request.getParts();
            String uploadDirectory = getServletContext().getRealPath("/assets/image/");

            File filePath = new File(uploadDirectory);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }

            for (Part part : parts) {
                String fileName = getFileName(part);
                if (fileName != null) {
                    part.write(uploadDirectory + File.separator + fileName);

                    RestaurantDto fileDto = new RestaurantDto();
                    fileDto.setFilePath(uploadDirectory);
                    fileDto.setFileName(fileName);
                    fileDto.setRestaurantNo(resNo);
                    resService.fileUpload(fileDto);
                }
            }

            
            
            
            response.sendRedirect("/index.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
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
    
    private void updateMenus(HttpServletRequest request, RestaurantServiceImpl resService, int resNo) throws Exception {
        resService.deleteMenu(resNo);
        ArrayList<RestaurantDto> menuList = new ArrayList<>();

        int count = 1;
        String food;
        while ((food = request.getParameter("menu" + count)) != null) {
            int price = Integer.parseInt(request.getParameter("price" + count));
            RestaurantDto menu = new RestaurantDto();
            menu.setMenu(food);
            menu.setPrice(price);
            menu.setRestaurantNo(resNo);
            menuList.add(menu);
            count++;
        }

        resService.addMenu(menuList);
    }

    private void updateTags(HttpServletRequest request, RestaurantServiceImpl resService, int resNo) throws Exception {
        resService.deleteTag(resNo);
        ArrayList<RestaurantDto> tagList = new ArrayList<>();

        int count = 1;
        String tag;
        while ((tag = request.getParameter("tag" + count)) != null) {
            RestaurantDto tagDto = new RestaurantDto();
            tagDto.setTag(tag);
            tagDto.setRestaurantNo(resNo);
            tagList.add(tagDto);
            count++;
        }

        resService.addTag(tagList);
    }
}
