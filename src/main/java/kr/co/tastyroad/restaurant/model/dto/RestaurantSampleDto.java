package kr.co.tastyroad.restaurant.model.dto;

import java.util.List;

public class RestaurantSampleDto extends RestaurantDto {
    private List<String> imgNames;
    private List<String> tags;
    private List<MenuDto> menus;

    // Getters and Setters for the additional fields
    public List<String> getImgNames() {
        return imgNames;
    }

    public void setImgNames(List<String> imgNames) {
        this.imgNames = imgNames;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<MenuDto> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuDto> menus) {
        this.menus = menus;
    }

    // MenuDto 내부 클래스 정의
    public static class MenuDto {
        private String foodName;
        private String price;

        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
