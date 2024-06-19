package kr.co.tastyroad.reservation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.tastyroad.common.DatabaseConnection;
import kr.co.tastyroad.reservation.dto.ReservationDto;
import kr.co.tastyroad.restaurant.model.dto.RestaurantDto;

public class ReservationDao {
    private Connection con;
    private DatabaseConnection dc;
    private PreparedStatement pstmt;

    public ReservationDao() {
        dc = new DatabaseConnection();
        con = dc.connDB();
    }

    public int resEnroll(ReservationDto resDto) {
        int result = 0;
        String query = "Insert into reservation values (reservationSeq.nextval,?,?,?,default,default,?)";

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, resDto.getHeadCount());
            pstmt.setString(2, resDto.getDate());
            pstmt.setString(3, resDto.getPhone());
            pstmt.setInt(4, resDto.getUserNo());

            result = pstmt.executeUpdate();
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<ReservationDto> getReservations(int userNo) {
        List<ReservationDto> reservationList = new ArrayList<>();
        String query = "SELECT * FROM reservation res JOIN restaurant ret ON res.restaurantNo = ret.restaurantNo JOIN TASTY_MEMBER tm ON res.USER_NO = tm.USER_NO WHERE res.user_no = ? ORDER BY res.reservationno ASC";

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, userNo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ReservationDto resDto = new ReservationDto();
                resDto.setRestaurantName(rs.getString("RESTAURANTNAME"));
                resDto.setResNo(rs.getInt("RESERVATIONNO"));
                resDto.setHeadCount(rs.getInt("HEADCOUNT"));
                resDto.setDate(rs.getString("RESERVATIONDATE"));
                resDto.setPhone(rs.getString("RESERVATIONPHONE"));
                resDto.setUserNo(rs.getInt("USER_NO"));
                resDto.setPaymentStatus(rs.getString("PAYMENTSTATUS"));
                resDto.setUserName(rs.getString("USER_NAME"));
                resDto.setResPhone(rs.getString("RESTAURANTPHONE"));
                resDto.setRestaurantNo(rs.getInt("RESTAURANTNO"));

                reservationList.add(resDto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservationList;
    }

    public boolean deleteReservation(int resNo) {
        String query = "DELETE FROM reservation WHERE reservationno = ?";
        int result = 0;

        try {
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, resNo);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result > 0;
    }
}
