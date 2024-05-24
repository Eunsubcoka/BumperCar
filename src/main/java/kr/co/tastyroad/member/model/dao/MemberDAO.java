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
        String query = "INSERT INTO member (M_NO, M_NAME, M_ID, M_PWD, M_EMAIL, M_ADDRESS, M_PHONE,) "
                     + "VALUES (member_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";
        int result = 0;

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, member.getUserName());
            pstmt.setString(2, member.getUserId());
            pstmt.setString(3, member.getUserPwd());
            pstmt.setString(4, member.getUserEmail());
            pstmt.setString(5, member.getUserAddress());
            pstmt.setString(6, member.getUserPhone());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return result;
    }

    public Member login(Member member) {
        String query = "SELECT M_NO, M_NAME, M_TYPE FROM MEMBER WHERE M_ID = ? AND M_PWD = ?";
        Member result = null;

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, member.getUserId());
            pstmt.setString(2, member.getUserPwd());

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = new Member();
                result.setUserNo(rs.getInt("M_NO"));
                result.setUserName(rs.getString("M_NAME"));
                result.setUserType(rs.getString("M_TYPE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return result;
    }

    public int duplicateId(String id) {
        String query = "SELECT count(*) AS cnt FROM member WHERE M_ID = ?";
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
        String query = "SELECT M_PWD, M_NO, M_NAME, M_TYPE FROM member WHERE M_ID = ?";
        Member result = null;

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                result = new Member();
                result.setUserNo(rs.getInt("M_NO"));
                result.setUserName(rs.getString("M_NAME"));
                result.setUserPwd(rs.getString("M_PWD"));
                result.setUserType(rs.getString("M_TYPE"));
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
