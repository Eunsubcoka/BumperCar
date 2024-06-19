package kr.co.tastyroad.restaurant.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.tastyroad.common.DatabaseConnection;
import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;
import kr.co.tastyroad.restaurant.model.dto.RestaurantSampleDto;

public class RestaurantDao {

    private Connection con;
    private DatabaseConnection dc;
    private PreparedStatement pstmt;

    public RestaurantDao() {
        dc = new DatabaseConnection();
        con = dc.connDB();
    }

    public RestaurantDto getRestaurant(int No) {
        RestaurantDto result = new RestaurantDto();
        String query = "Select * from restaurant r JOIN RES_IMG ri ON ri.RESTAURANTNO = r.RESTAURANTNO  "
                + " where r.restaurantNo = ?";

        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, No);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                int category = rs.getInt("category");
                String location = rs.getString("location");
                String phone = rs.getString("restaurantPhone");
                String name = rs.getString("restaurantName");
                String imgName = rs.getString("imgName");
                result.setRestaurantNo(No);
                result.setCategory(category);
                result.setLocation(location);
                result.setRestaurantPhone(phone);
                result.setRestaurantName(name);
                result.setImgName(imgName);

                return result;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return result;
    }

    public ArrayList<RestaurantDto> getMenuList(int No) {
        ArrayList<RestaurantDto> result = new ArrayList<RestaurantDto>();

        String query = "select r.restaurantNo,m.foodNo,m.foodName,m.price from restaurant r "
                + "JOIN menu m ON m.restaurantNo = r.RESTAURANTNO where r.restaurantNo = ?";

        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, No);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                RestaurantDto menu = new RestaurantDto();
                String foodName = rs.getString("foodName");
                int foodNo = rs.getInt("foodNo");
                int price = rs.getInt("price");
                menu.setRestaurantNo(No);
                menu.setMenu(foodName);
                menu.setPrice(price);
                menu.setFoodNo(foodNo);
                result.add(menu);
            }
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return result;
    }

    public float ratings(int No) {
        String query = "SELECT AVG(r2.RATINGS) FROM RESTAURANT r " + "JOIN REVIEWS r2  ON r2.RESTAURANTNO = ?";

        float result = 0;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, No);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                result = rs.getFloat("AVG(r2.RATINGS)");
                return result;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return 0;
    }

    public ArrayList<RestaurantDto> ratingsList(ArrayList<RestaurantDto> resDto) {
        String query = "SELECT AVG(r2.RATINGS) FROM RESTAURANT r " + "JOIN REVIEWS r2 ON r2.RESTAURANTNO = ?";

        ResultSet rs = null;
        try {
            for (RestaurantDto item : resDto) {
                pstmt = con.prepareStatement(query);
                pstmt.setInt(1, item.getRestaurantNo());

                rs = pstmt.executeQuery();
                while (rs.next()) {
                    float ratings = rs.getFloat("AVG(r2.RATINGS)");
                    item.setRatings(Math.round(ratings * 10) / 10.0f); // 소수점 첫째 자리까지 반올림하여 설정
                }
            }
            return resDto;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<RestaurantDto> getRestaurantList(int category, String seleType) {
        ArrayList<RestaurantDto> result = new ArrayList<RestaurantDto>();

        String orderByClause = "ORDER BY ";
        if ("ratings".equals(seleType)) {
            orderByClause += "ratings DESC";
        } else {
            orderByClause += "NLSSORT(r.restaurantName, 'NLS_SORT=KOREAN_M')";
        }

        String query = "SELECT r.restaurantNo, r.restaurantName, r2.imgName, "
                + "COALESCE(AVG(r3.ratings), 0) AS ratings "
                + "FROM restaurant r "
                + "JOIN (SELECT r2.restaurantNo, MIN(r2.imgName) AS imgName "
                + "FROM res_img r2 "
                + "GROUP BY r2.restaurantNo) r2 ON r2.restaurantNo = r.restaurantNo "
                + "LEFT OUTER JOIN reviews r3 ON r.restaurantNo = r3.restaurantNo "
                + "WHERE r.category = ? "
                + "GROUP BY r.restaurantNo, r.restaurantName, r2.imgName "
                + orderByClause;

        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, category);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                RestaurantDto resList = new RestaurantDto();
                String imgName = rs.getString("imgName");
                int resNo = rs.getInt("restaurantNo");
                String name = rs.getString("restaurantName");
                float ratings = rs.getFloat("ratings");

                resList.setRestaurantName(name);
                resList.setImgName(imgName);
                resList.setCategory(category);
                resList.setRestaurantNo(resNo);
                resList.setRatings(ratings);
                
                result.add(resList);
            }
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return result;
    }


    public int addMenu(ArrayList<RestaurantDto> menu) {
        String query = "insert into menu values(menu_seq.nextval,?,?,?)";
        int result = 0;
        try {
            for (RestaurantDto item : menu) {
                pstmt = con.prepareStatement(query);
                pstmt.setString(1, item.getMenu());
                pstmt.setInt(2, item.getPrice());
                pstmt.setInt(3, item.getRestaurantNo());

                result = pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return result;
    }

    public int addTag(ArrayList<RestaurantDto> tag) {
        String query = "insert into res_tag values(?,?)";
        int result = 0;
        try {
            for (RestaurantDto item : tag) {
                pstmt = con.prepareStatement(query);
                pstmt.setString(1, item.getTag());
                pstmt.setInt(2, item.getRestaurantNo());

                result = pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return result;
    }

    public int addRestaurant(RestaurantDto restaurant) {
        String query = "insert into restaurant values(restaurant_seq.nextval,?,?,?,?)";
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, restaurant.getCategory());
            pstmt.setString(2, restaurant.getLocation());
            pstmt.setString(3, restaurant.getRestaurantPhone());
            pstmt.setString(4, restaurant.getRestaurantName());
            int result = pstmt.executeUpdate();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return 0;
    }

    public int addResNo() {
        String query = "SELECT max(RESTAURANTNO) as no FROM RESTAURANT r";
        ResultSet rs = null;
        try {
            int result = 0;
            pstmt = con.prepareStatement(query);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                result = rs.getInt("no");
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return 0;
    }

    public int updateRestaurant(RestaurantDto restaurant) {
        String query = "Update restaurant set category = ?, location= ?, restaurantPhone =?, restaurantName = ?"
                + " where restaurantNo = ?";
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, restaurant.getCategory());
            pstmt.setString(2, restaurant.getLocation());
            pstmt.setString(3, restaurant.getRestaurantPhone());
            pstmt.setString(4, restaurant.getRestaurantName());
            pstmt.setInt(5, restaurant.getRestaurantNo());

            int result = pstmt.executeUpdate();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return 0;
    }

    public int deleteTag(int resNo) {
        String query = "delete from res_tag where restaurantNo = ?";
        int result = 0;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, resNo);
            result = pstmt.executeUpdate();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return 0;
    }

    public int deleteImg(int resNo) {
        String query = "delete from res_img where restaurantNo = ?";
        int result = 0;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, resNo);
            result = pstmt.executeUpdate();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return 0;
    }

    public int deleteMenu(int resNo) {
        String query = "Delete from menu where restaurantNo = ?";
        int result = 0;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, resNo);
            result = pstmt.executeUpdate();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return 0;
    }

    public ArrayList<RestaurantDto> getTag(ArrayList<RestaurantDto> resDto) {
        String query = "select * from res_tag where restaurantNo = ?";
        ArrayList<RestaurantDto> tag = new ArrayList<RestaurantDto>();
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(query);
            for (RestaurantDto item : resDto) {
                pstmt.setInt(1, item.getRestaurantNo());
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    RestaurantDto tagCon = new RestaurantDto();
                    tagCon.setTag(rs.getString("tag"));
                    tagCon.setRestaurantNo(rs.getInt("restaurantNo"));
                    tag.add(tagCon);
                }
            }
            return tag;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return null;
    }

    public ArrayList<String> getTag(int resNo) {
        String query = "select * from res_tag where restaurantNo = ?";
        ArrayList<String> result = new ArrayList<String>();
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, resNo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(rs.getString("tag"));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return null;
    }

    public int fileUpload(RestaurantDto resDto) {
        String query = "INSERT INTO res_img (imgName, restaurantNo) VALUES (?, ?)";
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, resDto.getFileName());
            pstmt.setInt(2, resDto.getRestaurantNo());
            int result = pstmt.executeUpdate();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return 0;
    }

    public ArrayList<String> getImg(int resNo) {
        String query = "Select * from res_img where restaurantNo = ?";
        ArrayList<String> result = new ArrayList<String>();
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, resNo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(rs.getString("imgName"));
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
//            try { if (con != null) con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
        return null;
    }
    
    
    // data
    
    public int addRestaurants(RestaurantDto restaurant) {
        String query = "insert into restaurant values(restaurant_seq.nextval,?,?,?,?)";
        try (PreparedStatement pstmt = con.prepareStatement(query, new String[]{"restaurantNo"})) {
            pstmt.setInt(1, restaurant.getCategory());
            pstmt.setString(2, restaurant.getLocation());
            pstmt.setString(3, restaurant.getRestaurantPhone());
            pstmt.setString(4, restaurant.getRestaurantName());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating restaurant failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating restaurant failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void addMenus(ArrayList<RestaurantDto> menus) {
        String query = "insert into menu values(menu_seq.nextval,?,?,?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            for (RestaurantDto menu : menus) {
                pstmt.setString(1, menu.getFoodName());
                pstmt.setInt(2, menu.getPrice());
                pstmt.setInt(3, menu.getRestaurantNo());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void uploadImage(RestaurantDto resDto) {
        if (resDto == null) {
            return;
        }

        String query = "INSERT INTO res_img (imgName, restaurantNo) VALUES (?, ?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, resDto.getFileName());
            pstmt.setInt(2, resDto.getRestaurantNo());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(pstmt, null);
        }
    }

    public void addTags(List<RestaurantDto> tags) {
        String query = "insert into res_tag values(?,?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            for (RestaurantDto tag : tags) {
                pstmt.setString(1, tag.getTag());
                pstmt.setInt(2, tag.getRestaurantNo());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addRestaurantsWithDetails(List<RestaurantSampleDto> items) {
        for (RestaurantSampleDto item : items) {
            RestaurantDto restaurant = new RestaurantDto();
            restaurant.setCategory(item.getCategory());
            restaurant.setLocation(item.getLocation());
            restaurant.setRestaurantPhone(item.getRestaurantPhone());
            restaurant.setRestaurantName(item.getRestaurantName());

            int restaurantNo = addRestaurants(restaurant);

            // 메뉴 정보 삽입
            if (item.getMenus() != null) {
                for (RestaurantSampleDto.MenuDto menu : item.getMenus()) {
                    RestaurantDto menuDto = new RestaurantDto();
                    menuDto.setFoodName(menu.getFoodName());
                    menuDto.setPrice(Integer.parseInt(menu.getPrice().replaceAll("[^\\d]", ""))); // 숫자만 추출
                    menuDto.setRestaurantNo(restaurantNo);
                    ArrayList<RestaurantDto> menuList = new ArrayList<>();
                    menuList.add(menuDto);
                    addMenus(menuList);
                }
            }

            // 이미지 정보 삽입
            if (item.getImgName() != null) {
                RestaurantDto imgDto = new RestaurantDto();
                imgDto.setFileName(item.getImgName());
                imgDto.setRestaurantNo(restaurantNo);
                uploadImage(imgDto); // 개별 RestaurantDto 객체 전달
            }
            if (item.getImageName() != null) {
                RestaurantDto imgDto = new RestaurantDto();
                imgDto.setFileName(item.getImageName());
                imgDto.setRestaurantNo(restaurantNo);
                uploadImage(imgDto); // 개별 RestaurantDto 객체 전달
            }

            // 태그 정보 삽입
            if (item.getTags() != null) {
                List<RestaurantDto> tagList = new ArrayList<>();
                for (String tag : item.getTags()) {
                    RestaurantDto tagDto = new RestaurantDto();
                    tagDto.setTag(tag.trim());
                    tagDto.setRestaurantNo(restaurantNo);
                    tagList.add(tagDto);
                }
                addTags(tagList);
            }
        }
    }
    private void closeResources(PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public List<RestaurantDto> getRestaurantListByCategory(int category) {
        List<RestaurantDto> result = new ArrayList<>();
        String query = "SELECT r.restaurantNo, r.category, r.location, r.restaurantPhone, r.restaurantName, ri.imgName "
                     + "FROM restaurant r "
                     + "LEFT JOIN (SELECT restaurantNo, MIN(imgName) AS imgName FROM res_img GROUP BY restaurantNo) ri "
                     + "ON r.restaurantNo = ri.restaurantNo "
                     + "WHERE r.category = ?";

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, category);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                RestaurantDto restaurant = new RestaurantDto();
                restaurant.setRestaurantNo(rs.getInt("restaurantNo"));
                restaurant.setCategory(rs.getInt("category"));
                restaurant.setLocation(rs.getString("location"));
                restaurant.setRestaurantPhone(rs.getString("restaurantPhone"));
                restaurant.setRestaurantName(rs.getString("restaurantName"));
                restaurant.setImgName(rs.getString("imgName"));
                result.add(restaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        
        return result;
    }
}

