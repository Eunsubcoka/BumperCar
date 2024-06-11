package kr.co.tastyroad.restaurant.model.dto;

public class RestaurantDto {
private String tag;
public String getTag() {
	return tag;
}
public void setTag(String tag) {
	this.tag = tag;
}


public int fileNo;
public String filePath;
public String fileName;
public int getFileNo() {
	return fileNo;
}
public void setFileNo(int fileNo) {
	this.fileNo = fileNo;
}
public String getFilePath() {
	return filePath;
}
public void setFilePath(String filePath) {
	this.filePath = filePath;
}
public String getFileName() {
	return fileName;
}
public void setFileName(String fileName) {
	this.fileName = fileName;
}


private int restaurantNo;
private String category;
private String location;
private String restaurantPhone;
private String restaurantName;
private String menu;
private int price;
private int foodNo;
private String imgName;
private float ratings;
public float getRatings() {
	return ratings;
}
public void setRatings(float ratings) {
	this.ratings = ratings;
}
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
