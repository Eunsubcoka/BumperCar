package kr.co.tastyroad.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import kr.co.tastyroad.common.DatabaseConnection;
import kr.co.tastyroad.member.model.dto.Member;

public class MemberDAO {

    public int register(Member member) throws SQLException {
        String checkUserIdQuery = "SELECT count(*) FROM Tasty_member WHERE user_id = ?";
        String checkGoogleIdQuery = "SELECT count(*) FROM Tasty_member WHERE google_id = ?";
        String insertQuery = "INSERT INTO Tasty_member (user_no, user_name, user_id, user_email, user_address, user_phone, user_pwd, token, verified, google_id, user_type) "
                + "VALUES (Tasty_member_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int result = 0;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement checkUserIdStmt = con.prepareStatement(checkUserIdQuery);
             PreparedStatement checkGoogleIdStmt = con.prepareStatement(checkGoogleIdQuery);
             PreparedStatement insertStmt = con.prepareStatement(insertQuery)) {

            if ("normal".equals(member.getUserType())) {
                checkUserIdStmt.setString(1, member.getUserId());
                try (ResultSet rs = checkUserIdStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        throw new SQLException("Duplicate user_id");
                    }
                }
            } else if ("google".equals(member.getUserType())) {
                checkGoogleIdStmt.setString(1, member.getGoogleId());
                try (ResultSet rs = checkGoogleIdStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        throw new SQLException("Duplicate google_id");
                    }
                }
            }
            insertStmt.setString(1, member.getUserName());
            insertStmt.setString(2, member.getUserId());
            insertStmt.setString(3, member.getUserEmail());
            insertStmt.setString(4, member.getUserAddress());
            insertStmt.setString(5, member.getUserPhone());
            insertStmt.setString(6, member.getUserPwd());
            insertStmt.setString(7, member.getToken());
            insertStmt.setBoolean(8, member.isVerified());
            insertStmt.setString(9, member.getGoogleId());
            insertStmt.setString(10, "user");

            result = insertStmt.executeUpdate();
        }

        return result;
    }

    public Member login(Member member) {
        String query = "SELECT user_no, user_name, user_type FROM Tasty_member WHERE user_id = ? AND user_pwd = ?";
        Member result = null;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, member.getUserId());
            pstmt.setString(2, member.getUserPwd());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    result = new Member();
                    result.setUserNo(rs.getInt("user_no"));
                    result.setUserName(rs.getString("user_name"));
                    result.setUserType(rs.getString("user_type"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Member loginWithGoogle(String googleId) {
        String query = "SELECT user_no, user_name, user_type FROM Tasty_member WHERE google_id = ?";
        Member result = null;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, googleId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    result = new Member();
                    result.setUserNo(rs.getInt("user_no"));
                    result.setUserName(rs.getString("user_name"));
                    result.setUserType(rs.getString("user_type"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int userUpdate(Member member) {
        String query = "UPDATE Tasty_member SET user_name=?, user_email=?, user_address=?, user_phone=? WHERE user_no=?";
        int result = 0;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, member.getUserName());
            pstmt.setString(2, member.getUserEmail());
            pstmt.setString(3, member.getUserAddress());
            pstmt.setString(4, member.getUserPhone());
            pstmt.setInt(5, member.getUserNo());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int duplicateId(String id) {
        String query = "SELECT count(*) AS cnt FROM Tasty_member WHERE user_id = ?";
        int result = 0;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    result = rs.getInt("cnt");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Member getHashPassword(String id) {
        String query = "SELECT user_pwd, user_no, user_name, user_type FROM Tasty_member WHERE user_id = ?";
        Member result = null;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    result = new Member();
                    result.setUserNo(rs.getInt("user_no"));
                    result.setUserName(rs.getString("user_name"));
                    result.setUserPwd(rs.getString("user_pwd"));
                    result.setUserType(rs.getString("user_type"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }

    public int saveMember(Member member) {
        String query = "INSERT INTO Tasty_member (user_id, user_name, user_email, user_pwd, user_address, user_phone, token, verified) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
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

    public Member getMemberByToken(String token) throws SQLException {
        String query = "SELECT user_id, user_name, user_email, user_pwd, user_address, user_phone, token, verified FROM Tasty_member WHERE token = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, token);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Member member = new Member();
                    member.setUserId(rs.getString("user_id"));
                    member.setUserName(rs.getString("user_name"));
                    member.setUserEmail(rs.getString("user_email"));
                    member.setUserPwd(rs.getString("user_pwd"));
                    member.setUserAddress(rs.getString("user_address"));
                    member.setUserPhone(rs.getString("user_phone"));
                    member.setToken(rs.getString("token"));
                    member.setVerified(rs.getBoolean("verified"));
                    return member;
                }
            }
        }
        return null;
    }

    public void verifyMember(String userId) {
        String query = "UPDATE Tasty_member SET verified = 1 WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserProfile(Member member) {
        String query = "UPDATE Tasty_member SET user_name = ?, user_email = ?, user_address = ?, user_phone = ?, profile = ? WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, member.getUserName());
            pstmt.setString(2, member.getUserEmail());
            pstmt.setString(3, member.getUserAddress());
            pstmt.setString(4, member.getUserPhone());
            pstmt.setString(5, member.getFileName());
            pstmt.setString(6, member.getUserId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Member getUserProfile(int userNo) {
        String query = "SELECT * FROM Tasty_member WHERE user_no = ?";
        Member member = null;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, userNo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    member = new Member();
                    member.setUserId(rs.getString("user_id"));
                    member.setUserName(rs.getString("user_name"));
                    member.setUserEmail(rs.getString("user_email"));
                    member.setUserAddress(rs.getString("user_address"));
                    member.setUserPhone(rs.getString("user_phone"));
                    member.setProfileImageUrl(rs.getString("profile"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return member;
    }

    public String findIdByEmail(String email) {
        String query = "SELECT user_id FROM Tasty_member WHERE user_email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("user_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Member findByEmail(String email) {
        String query = "SELECT * FROM Tasty_member WHERE user_email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMember(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateGoogleId(String userId, String googleId) {
        String query = "UPDATE Tasty_member SET google_id = ? WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, googleId);
            pstmt.setString(2, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertMember(Member member) {
        String query = "INSERT INTO Tasty_member (user_name, user_id, user_email, user_pwd, user_address, user_phone, token, verified, google_id, user_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, member.getUserName());
            pstmt.setString(2, member.getUserId());
            pstmt.setString(3, member.getUserEmail());
            // 기본 비밀번호 설정
            pstmt.setString(4, member.getUserPwd() == null ? "default_password" : member.getUserPwd());
            pstmt.setString(5, member.getUserAddress());
            pstmt.setString(6, member.getUserPhone());
            pstmt.setString(7, member.getToken());
            pstmt.setBoolean(8, member.isVerified());
            pstmt.setString(9, member.getGoogleId());
            pstmt.setString(10, member.getUserType());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private Member mapResultSetToMember(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setUserId(rs.getString("user_id"));
        member.setUserName(rs.getString("user_name"));
        member.setUserEmail(rs.getString("user_email"));
        member.setUserPwd(rs.getString("user_pwd"));
        member.setUserAddress(rs.getString("user_address"));
        member.setUserPhone(rs.getString("user_phone"));
        member.setToken(rs.getString("token"));
        member.setVerified(rs.getBoolean("verified"));
        member.setGoogleId(rs.getString("google_id"));
        return member;
    }

    public Member findMemberByIdAndEmail(String userId, String email) {
        String query = "SELECT * FROM Tasty_member WHERE user_id = ? AND user_email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userId);
            pstmt.setString(2, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Member member = new Member();
                    member.setUserId(rs.getString("user_id"));
                    member.setUserEmail(rs.getString("user_email"));
                    return member;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void savePasswordResetToken(String userId, String token) throws SQLException {
        String query = "UPDATE Tasty_member SET token = ? WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, token);
            pstmt.setString(2, userId);
            pstmt.executeUpdate();
        }
    }

    public boolean updateToken(String userId, String token) {
        String query = "UPDATE Tasty_member SET token = ? WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, token);
            pstmt.setString(2, userId);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean verifyToken(String token) throws SQLException {
        String query = "SELECT COUNT(*) FROM Tasty_member WHERE token = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, token);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) == 1;
            }
        }
    }

    public boolean updatePassword(String userId, String hashedPassword) throws SQLException {
        String query = "UPDATE Tasty_member SET user_pwd = ?, token = NULL WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, hashedPassword);
            pstmt.setString(2, userId);
            return pstmt.executeUpdate() > 0;
        }
    }

    public Member findByGoogleId(String googleId) {
        String query = "SELECT * FROM Tasty_member WHERE google_id = ?";
        Member member = null;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, googleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    member = new Member();
                    member.setUserNo(rs.getInt("user_no"));
                    member.setUserId(rs.getString("user_id"));
                    member.setUserName(rs.getString("user_name"));
                    member.setUserEmail(rs.getString("user_email"));
                    member.setUserType(rs.getString("user_type"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return member;
    }

    public void save(Member member) {
        String query = "INSERT INTO Tasty_member (user_id, user_name, user_email, user_type) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
        	
        	System.out.println(member.getUserId());
            pstmt.setString(1, member.getUserId());
            pstmt.setString(2, member.getUserName());
            pstmt.setString(3, member.getUserEmail());
            pstmt.setString(4, member.getUserType());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Member getMemberByGoogleId(String googleId) {
        String query = "SELECT * FROM Tasty_member WHERE google_id = ?";
        Member member = null;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, googleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    member = new Member();
                    member.setUserNo(rs.getInt("user_no"));
                    member.setUserId(rs.getString("user_id"));
                    member.setUserName(rs.getString("user_name"));
                    member.setUserEmail(rs.getString("user_email"));
                    member.setUserType(rs.getString("user_type"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return member;
    }
}
