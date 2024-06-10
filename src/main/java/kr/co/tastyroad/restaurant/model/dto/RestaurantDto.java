package kr.co.tastyroad.restaurant.model.dto;

public class RestaurantDto {
private int restaurantNo;
private String category;
private String location;
private String restaurantPhone;
private String restaurantName;
private String menu;
private int price;
private int foodNo;
private String imgName;

private String tags;

public String getTags() {
	return tags;
}
public void setTags(String tags) {
	this.tags = tags;
}
public String getImgName() {
	return imgName;
}
public void setImgName(String imgName) {
	this.imgName = imgName;
}
public String getMenu() {
	return menu;
}
public void setMenu(String menu) {
	this.menu = menu;
}
public int getFoodNo() {
	return foodNo;
}
public void setFoodNo(int foodNo) {
	this.foodNo = foodNo;
}

public int getPrice() {
	return price;
}
public void setPrice(int price) {
	this.price = price;
}
public int getRestaurantNo() {
	return restaurantNo;
}
public void setRestaurantNo(int restaurantNo) {
	this.restaurantNo = restaurantNo;
}
public String getCategory() {
	return category;
}
public void setCategory(String category) {
	this.category = category;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
public String getRestaurantPhone() {
	return restaurantPhone;
}
public void setRestaurantPhone(String restaurantPhone) {
	this.restaurantPhone = restaurantPhone;
}
public String getRestaurantName() {
	return restaurantName;
}
public void setRestaurantName(String restaurantName) {
	this.restaurantName = restaurantName;
}






}
