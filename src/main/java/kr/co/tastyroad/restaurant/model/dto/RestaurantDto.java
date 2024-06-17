package kr.co.tastyroad.restaurant.model.dto;

public class RestaurantDto {
private String tag;
public String getTag() {
	return tag;
}
public void setTag(String tag) {
	this.tag = tag;
}

private double distance;


public double getDistance() {
	return distance;
}
public void setDistance(double distance) {
	this.distance = distance;
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
private int category;
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
public int getCategory() {
	return category;
}
public void setCategory(int category) {
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

public String getFoodName() {
	return foodName;
}
public void setFoodName(String foodName) {
	this.foodName = foodName;
}

private String foodName;
private double latitude;
private double longitude;
public double getLatitude() {
	return latitude;
}
public void setLatitude(double latitude) {
	this.latitude = latitude;
}
public double getLongitude() {
	return longitude;
}
public void setLongitude(double longitude) {
	this.longitude = longitude;
}


}