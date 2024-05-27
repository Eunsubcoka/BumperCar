package kr.co.tastyroad.notice.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.co.tastyroad.common.PageInfo;
import kr.co.tastyroad.notice.model.dto.noticeDto;
import kr.co.tastyroad.common.DatabaseConnection;

public class noticeDao {
	private Connection con;
	private DatabaseConnection dc;
	private PreparedStatement pstmt;
	
	public noticeDao() {
		dc = new DatabaseConnection();
		con = dc.connDB();
	}
	
	public ArrayList<noticeDto> getList(PageInfo pi, String category, String searchText){
		
		
		ArrayList<noticeDto> result = new ArrayList<>();
		String query = "SELECT * FROM notice nt "
				+ "JOIN member m ON m.userNo = nt.userNo "
				+ "WHERE " + category + " LIKE '%'||?||'%'"
				+ "ORDER BY nt.noticeDate DESC "
				+ "OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, searchText);
			pstmt.setInt(2, pi.getOffset());
			pstmt.setInt(3, pi.getBoardLimit());
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt("noticeNo");
				String title = rs.getString("noticeTitle");
				String content = rs.getString("noticeContent");
				int views = rs.getInt("noticeView");
				String date = rs.getString("noticeDate");
				int memberNo = rs.getInt("userNo");
				String memberName = rs.getString("userName");
				
				noticeDto noticeDto = new noticeDto();
				noticeDto.setNoticeNo(no);
				noticeDto.setNoticeTitle(title);
				noticeDto.setNoticeContent(content);
				noticeDto.setNoticeView(views);
				noticeDto.setNoticeDate(date);
				noticeDto.setMemberNo(userNo);
				noticeDto.setMemberName(userName);
				
				result.add(noticeDto);
			}
			
			pstmt.close();
			con.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public int getListCount(String category, String searchText) {
		String query = "SELECT count(*) AS cnt FROM notice nt "
				+ "JOIN member m ON nt.userNo = m.userNo "
				+ "WHERE " + category + " LIKE '%'||?||'%'";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, searchText);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int result = rs.getInt("cnt");
				return result;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int enroll(noticeDto noticeDto) {
		String query = "INSERT INTO notice VLAUES(notice_seq.nextval,?, ?, default, default, ?)";
		int result = 0;
		
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, noticeDto.getNoticeTitle());
			pstmt.setString(2, noticeDto.getNoticeContent());
			pstmt.setInt(3, noticeDto.getUserNo());
			
			result = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		
		return result;
	}
	
	public noticeDto getDetail(int noticeNo) {
		String query = "SELECT * FROM notice WHERE noticeNo = ?";
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, noticeNo);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int ntNo = rs.getInt("noticeNo");
				String ntTitle = rs.getString("noticeTitle");
				String ntContent = rs.getString("noticeContent");
				String ntDate = rs.getString("noticeDate");
				int ntView = rs.getInt("noticeView");
				int mNo = rs.getInt("userNo");
				
				noticeDto noticeDto = new noticeDto();
				noticeDto.setNoticeNo(noticeNo);
				noticeDto.setNoticeTitle(ntTitle);
				noticeDto.setNoticeContent(ntContent);
				noticeDto.setNoticeDate(ntDate);
				noticeDto.setNoticeView(noticeNo);
				noticeDto.setUserNo(mNo);
				return noticeDto;
				
				
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void getWriter(noticeDto noticeDto) {
		String query = "SELECT userName FROM member WHERE userNo = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, noticeDto.getUserNo());
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String name = rs.getString("userName");
				noticeDto.setUserName(name);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int setView(int noticeNo) {
		String query = "UPDATE notice SET noticeView = noticeView+1 WHERE noticeNo = ?";
		int result = 0;
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, noticeNo);
			return pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
		
	}

	public int setEdit(noticeDto noticeDto) {
		String query = "UPDATE notice "
				+ "SET 	noticeTitle = ?,"
				+ " 	noticeContent = ?,"
				+ "WHERE noticeNo = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, noticeDto.getNoticeTitle());
			pstmt.setString(1, noticeDto.getNoticeContent());
			pstmt.setInt(1, noticeDto.getNoticeNo());
			
			int result = pstmt.executeUpdate();
			return result;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public int setDelete(int noticeNo) {
		String query = "DELETE FROM notice WHERE noticeNo = ?";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, noticeNo);
			
			int result =pstmt.executeUpdate();
			
			return result;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public noticeDto selectNo(noticeDto noticeDto) {
		String query = "SELECT noticeNo FROM notice "
				+ "WHERE noticeNo = (SELECT MAX(noticeNo) FROM notice "
				+ "WHERE noticeNo = ?)";
		
		try {
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, noticeDto.getUserNo());
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int noticeNo = rs.getInt("noticeNo");
				noticeDto.setNoticeNo(noticeNo);
				return noticeDto;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
