package kr.co.tastyroad.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.tastyroad.common.DatabaseConnection;
import kr.co.tastyroad.member.model.dto.Member;

public class MemberDAO {
    private Connection con;
    private DatabaseConnection dc;
    private PreparedStatement pstmt;

    public MemberDAO() {
        dc = new DatabaseConnection();
        con = dc.connDB();
    }

    public int register(Member member) {
        String query = "INSERT INTO Tasty_member (user_no, user_name, user_id, user_email, user_address, user_phone, user_pwd) "
                     + "VALUES (Tasty_member_seq.nextval, ?, ?, ?, ?, ?, ?)";
        int result = 0;

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, member.getUserName());
            pstmt.setString(2, member.getUserId());
            pstmt.setString(3, member.getUserEmail());
            pstmt.setString(4, member.getUserAddress());
            pstmt.setString(5, member.getUserPhone());
            pstmt.setString(6, member.getUserPwd());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return result;
    }

    public Member login(Member member) {
        String query = "SELECT user_no, user_name, user_type FROM Tasty_member WHERE user_id = ? AND user_pwd = ?";
        Member result = null;

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, member.getUserId());
            pstmt.setString(2, member.getUserPwd());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = new Member();
                result.setUserNo(rs.getInt("user_no"));
                result.setUserName(rs.getString("user_name"));
                result.setUserType(rs.getString("user_type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return result;
    }
    public int userUpdate(Member member) {
        String query = "update Tasty_member set user_name=? , user_email=?, user_address=?, user_phone=? "
                     + "where user_no = ?";
        int result = 0;
        	
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, member.getUserName());
            pstmt.setString(2, member.getUserEmail());
            pstmt.setString(3, member.getUserAddress());
            pstmt.setString(4, member.getUserPhone());
            pstmt.setInt(5, member.getUserNo());

            
            result = pstmt.executeUpdate();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return result;
    }

    
    public int duplicateId(String id) {
        String query = "SELECT count(*) AS cnt FROM Tasty_member WHERE user_id = ?";
        int result = 0;

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                result = rs.getInt("cnt");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return result;
    }

    public Member getHashPassword(String id) {
        String query = "SELECT user_pwd, user_no, user_name, user_type FROM Tasty_member WHERE user_id = ?";
        Member result = null;

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                result = new Member();
                result.setUserNo(rs.getInt("user_no"));
                result.setUserName(rs.getString("user_name"));
                result.setUserPwd(rs.getString("user_pwd"));
                result.setUserType(rs.getString("user_type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return result;
    }

    private void closeResources() {
        try {
            if (pstmt != null && !pstmt.isClosed()) {
                pstmt.close();
            }
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
