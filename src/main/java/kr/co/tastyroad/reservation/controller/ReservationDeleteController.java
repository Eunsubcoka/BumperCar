package kr.co.tastyroad.reservation.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.tastyroad.reservation.service.ReservationServiceImpl;

@WebServlet("/reservation/delete.do")
public class ReservationDeleteController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReservationDeleteController() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int resNo = Integer.parseInt(request.getParameter("resNo"));
        
        ReservationServiceImpl resService = new ReservationServiceImpl();
        boolean isDeleted = resService.deleteReservation(resNo);
        
        if (isDeleted) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
