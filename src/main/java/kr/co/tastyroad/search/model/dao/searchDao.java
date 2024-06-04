package kr.co.tastyroad.search.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import kr.co.tastyroad.common.DatabaseConnection;
import kr.co.tastyroad.notice.model.dto.noticeDto;

public class searchDao {
	private Connection con;
	private DatabaseConnection dc;
	private PreparedStatement pstmt;

	public searchDao() {
		dc = new DatabaseConnection();
		con = dc.connDB();
	}
	
	public ArrayList<noticeDto> searchNotices(String searchText) {
        ArrayList<noticeDto> noticeList = new ArrayList<>();
        String query = "SELECT * FROM notice WHERE noticeTitle LIKE ? ORDER BY noticeDate DESC FETCH FIRST 5 ROWS ONLY";
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, "%" + searchText + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                noticeDto notice = new noticeDto();
                String date = "";
				try {
					SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
					date = targetFormat.format(originalFormat.parse(rs.getString("noticeDate")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
                
                notice.setNoticeNo(rs.getInt("noticeNo"));
                notice.setNoticeTitle(rs.getString("noticeTitle"));
                notice.setNoticeContent(rs.getString("noticeContent"));
                notice.setNoticeView(rs.getInt("noticeView"));
                notice.setNoticeDate(date);
                notice.setUserNo(rs.getInt("user_no"));
                noticeList.add(notice);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return noticeList;
    }

}
