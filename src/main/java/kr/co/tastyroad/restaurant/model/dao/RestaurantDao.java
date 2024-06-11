package kr.co.tastyroad.restaurant.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.co.tastyroad.common.DatabaseConnection;
import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;
import kr.co.tastyroad.review.model.dto.ReviewDto;


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
		
		try {
			System.out.println("여기는 겟레스 실행문입니다.");
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, No);
			
			ResultSet rs = pstmt.executeQuery();

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
		}
		
		return result;
		
	}
	
	// 리뷰 리스트 로직
    public ArrayList<RestaurantDto> getMenuList(int No) {
    	ArrayList<RestaurantDto> result = new ArrayList<RestaurantDto>();
    
   
    String query = "select r.restaurantNo,m.foodNo,m.foodName,m.price from restaurant r "
                 + "JOIN menu m ON m.restaurantNo = r.RESTAURANTNO where r.restaurantNo = ?";
    
    try {
		pstmt = con.prepareStatement(query);
		pstmt.setInt(1, No);
		
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			RestaurantDto menu = new RestaurantDto();
			String foodName = rs.getString("foodName");
			int foodNo = rs.getInt("foodNo");
			int price = rs.getInt("price");
			menu.setRestaurantNo(No);
			menu.setMenu(foodName);
			menu.setPrice(price);
			menu.setFoodNo(foodNo);
			result.add(menu) ;
		}
        return result;
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return result;
    }
	
    public float ratings(int No) {
    	String query = "SELECT AVG(r2.RATINGS) FROM RESTAURANT r " 
    			+ "JOIN REVIEWS r2  ON r2.RESTAURANTNO = ?"; 

    	float result = 0;
    	try {
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, No);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				result = rs.getFloat("AVG(r2.RATINGS)");
				return result;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return 0;
    }
    public ArrayList<RestaurantDto> ratingsList(ArrayList<RestaurantDto> resDto) {
    	String query = "SELECT AVG(r2.RATINGS) FROM RESTAURANT r " 
    			+ "JOIN REVIEWS r2  ON r2.RESTAURANTNO = ?"; 

    	
    	try {
    		for(RestaurantDto item : resDto) {
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1, item.getRestaurantNo());
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				
				item.setRatings(rs.getFloat("AVG(r2.RATINGS)"));
				
				
			}
			}
			return resDto;
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    public ArrayList<RestaurantDto> getRestaurantList(int category) {
    	ArrayList<RestaurantDto> result = new ArrayList<RestaurantDto>();
    
   
    String query = "select r.restaurantNo ,r.restaurantName, r2.imgName from restaurant r "
                 + " join res_img r2 on r2.restaurantNo = r.restaurantNo  where r.category= ?";
    
    try {
		pstmt = con.prepareStatement(query);
		pstmt.setInt(1, category);
		
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			RestaurantDto resList = new RestaurantDto();
			String imgName =rs.getString("imgName");
			int resNo = rs.getInt("restaurantNo");
			String Name = rs.getString("restaurantName");
			resList.setRestaurantName(Name);
			resList.setImgName(imgName);
			resList.setCategory(category);
			resList.setRestaurantNo(resNo);
			result.add(resList) ;
			
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
			
			 for(RestaurantDto item : menu) {
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, item.getMenu());
			pstmt.setInt(2, item.getPrice());
			pstmt.setInt(3, item.getRestaurantNo());
			
			pstmt.executeUpdate();
			 }
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
    public int addTag(ArrayList<RestaurantDto> tag) {
    	String query = "insert into res_tag values(?,?)";
    	int result = 0;
    	try {
    		
    		for(RestaurantDto item : tag) {
    			
    			pstmt = con.prepareStatement(query);
    			pstmt.setString(1, item.getTag());
    			pstmt.setInt(2, item.getRestaurantNo());
    			
    			pstmt.executeUpdate();
    		}
    		return result;
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return 0;
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
		}
		
		return 0;
	}
    public int addResNo() {
    	String query = "SELECT max(RESTAURANTNO) as no FROM RESTAURANT r";
    	try {
    		int result = 0;
    		pstmt = con.prepareStatement(query);
    		
    		 
    		ResultSet rs = pstmt.executeQuery();
    		 while(rs.next()) {
    			 result = rs.getInt("no");
    			 
    		 }
    		return result;
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return 0;
    }
		
    public int updateRestaurant(RestaurantDto restaurant) {
		String query = "Update restaurant set category = ?, location= ?, restaurantPhone =?, restaurantName = ?";
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
		}
		
		return 0;
	}

    public int deleteTag(int resNo) {
    	String query = "delete from res_tag where restaurantNo = ?";
    	int result = 0;
    	try {
    			pstmt = con.prepareStatement(query);
    			pstmt.setInt(1, resNo);
    			
    			pstmt.executeUpdate();
    		return result;
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return 0;
    }
    
    public int deleteMenu(int resNo) {
  		String query = "Delete from menu where restaurantNo = ?";
  		int result = 0;
  		try {
  			
  			
  			pstmt = con.prepareStatement(query);
  			pstmt.setInt(1, resNo);
  			
  			pstmt.executeUpdate();
  			return result;
  		} catch (SQLException e) {
  			e.printStackTrace();
  		}
  		
  		return 0;
  	}
    public ArrayList<RestaurantDto> getTag(ArrayList<RestaurantDto> resDto) {
    	String query = "select * from res_tag where restaurantNo = ?";
    	try {
    			pstmt = con.prepareStatement(query);
    			for(RestaurantDto item : resDto) {
    			pstmt.setInt(1,item.getRestaurantNo() );
    			
    			ResultSet rs = pstmt.executeQuery();
    			
    			while(rs.next()) {
    				item.setTag(rs.getString("tag"));
    			}
    			}
    		return resDto;
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return null;
    }
    public ArrayList<String> getTag(int resNo) {
    	String query = "select * from res_tag where restaurantNo = ?";
    	ArrayList<String> result = new ArrayList<String>();
    	try {
    			pstmt = con.prepareStatement(query);
    			pstmt.setInt(1,resNo);
    			
    			ResultSet rs = pstmt.executeQuery();
    			
    			while(rs.next()) {
    				result.add(rs.getString("tag"));
    			}
    			
    		return result;
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return null;
    }
    
    public int fileUpload(RestaurantDto resDto) {
		String query = "Insert into res_img "
					  +" Values(?,?)";
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, resDto.getFileName());
			pstmt.setInt(2, resDto.getRestaurantNo());
			
			int result = pstmt.executeUpdate();
			
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return 0;
	}
   
//    public void getFileName(RestaurantDto result) {
//		String query = "Select restaurantNo from restaurant "
//					+  " where restaurantNo = ? ";
//		try {
//			pstmt = con.prepareStatement(query);
//			pstmt.setInt(1, result.No());
//			ResultSet rs = pstmt.executeQuery();
//			
//			while(rs.next()) {
//				int no = rs.getInt("FBU_NO");
//				String name = rs.getString("fbu_name");
//				
//				result.setFileNo(no);
//				result.setFileName(name);
//				System.out.println(name);
//				System.out.println(no);
//			}
//		
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//	}
    
}
