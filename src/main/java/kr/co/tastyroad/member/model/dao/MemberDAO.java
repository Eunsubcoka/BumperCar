package kr.co.tastyroad.member.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.tastyroad.common.DatabaseConnection;
import kr.co.tastyroad.member.model.dto.Member;

public class MemberDAO {
    private Connection con;
    private DatabaseConnection dc;
    private PreparedStatement pstmt;
    private ResultSet rs;

    private Connection getConnection() throws SQLException {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "webadmin";
        String password = "qwer1234!";
        return DriverManager.getConnection(url, user, password);
    }

    public MemberDAO() {
        dc = new DatabaseConnection();
        con = dc.connDB();
    }

    public int register(Member member) throws SQLException {
        String checkUserIdQuery = "SELECT count(*) FROM Tasty_member WHERE user_id = ?";
        String checkTokenQuery = "SELECT count(*) FROM Tasty_member WHERE token = ?";
        String insertQuery = "INSERT INTO Tasty_member (user_no, user_name, user_id, user_email, user_address, user_phone, user_pwd, token, verified) "
                           + "VALUES (Tasty_member_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";

        int result = 0;

        try {
            // Check for duplicate user_id
            pstmt = con.prepareStatement(checkUserIdQuery);
            pstmt.setString(1, member.getUserId());
            rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new SQLException("Duplicate user_id");
            }

            // Check for duplicate token
            pstmt = con.prepareStatement(checkTokenQuery);
            pstmt.setString(1, member.getToken());
            rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                throw new SQLException("Duplicate token");
            }

            // Insert new member
            pstmt = con.prepareStatement(insertQuery);
            pstmt.setString(1, member.getUserName());
            pstmt.setString(2, member.getUserId());
            pstmt.setString(3, member.getUserEmail());
            pstmt.setString(4, member.getUserAddress());
            pstmt.setString(5, member.getUserPhone());
            pstmt.setString(6, member.getUserPwd());
            pstmt.setString(7, member.getToken());
            pstmt.setBoolean(8, member.isVerified());

            result = pstmt.executeUpdate();
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

            rs = pstmt.executeQuery();
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
        String query = "UPDATE Tasty_member SET user_name=?, user_email=?, user_address=?, user_phone=? WHERE user_no=?";
        int result = 0;

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, member.getUserName());
            pstmt.setString(2, member.getUserEmail());
            pstmt.setString(3, member.getUserAddress());
            pstmt.setString(4, member.getUserPhone());
            pstmt.setInt(5, member.getUserNo());

            result = pstmt.executeUpdate();
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
            rs = pstmt.executeQuery();

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
            rs = pstmt.executeQuery();

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
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
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

    public int saveMember(Member member) {
        String query = "INSERT INTO Tasty_member (userId, userName, userEmail, userPwd, userAddress, userPhone, token, verified) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, member.getUserId());
            pstmt.setString(2, member.getUserName());
            pstmt.setString(3, member.getUserEmail());
            pstmt.setString(4, member.getUserPwd());
            pstmt.setString(5, member.getUserAddress());
            pstmt.setString(6, member.getUserPhone());
            pstmt.setString(7, member.getToken());
            pstmt.setBoolean(8, member.isVerified());

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Member getMemberByToken(String token) {
        String query = "SELECT * FROM Tasty_member WHERE token = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, token);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Member member = new Member();
                    member.setUserId(rs.getString("userId"));
                    member.setUserName(rs.getString("userName"));
                    member.setUserEmail(rs.getString("userEmail"));
                    member.setUserPwd(rs.getString("userPwd"));
                    member.setUserAddress(rs.getString("userAddress"));
                    member.setUserPhone(rs.getString("userPhone"));
                    member.setToken(rs.getString("token"));
                    member.setVerified(rs.getBoolean("verified"));
                    return member;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void verifyMember(String userId) {
        String query = "UPDATE Tasty_member SET verified = 1 WHERE userId = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
