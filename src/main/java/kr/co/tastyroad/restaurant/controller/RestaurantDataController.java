package kr.co.tastyroad.restaurant.controller;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.co.tastyroad.restaurant.model.dao.RestaurantDao;
import kr.co.tastyroad.restaurant.model.dto.RestaurantSampleDto;

@WebServlet("/restaurantData.do")
public class RestaurantDataController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // JSON 파일 경로 설정
            String filePath = getServletContext().getRealPath("/WEB-INF/data/corrected_restaurant_data.json");
            FileReader reader = new FileReader(filePath);
            Gson gson = new Gson();

            // JSON 데이터를 `RestaurantSampleDto` 객체 목록으로 변환
            Type listType = new TypeToken<List<RestaurantSampleDto>>() {}.getType();
            List<RestaurantSampleDto> items = gson.fromJson(reader, listType);

            // 데이터베이스 연결 및 삽입
            RestaurantDao dao = new RestaurantDao();
            dao.addRestaurantsWithDetails(items);

            // 파싱된 데이터를 요청 속성에 추가하고 JSP로 포워딩
            request.setAttribute("items", items);
            request.getRequestDispatcher("/category.do").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
