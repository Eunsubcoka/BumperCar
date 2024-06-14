package kr.co.tastyroad.reservation.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.tastyroad.reservation.dto.ReservationDto;
import kr.co.tastyroad.reservation.service.ReservationServiceImpl;

@WebServlet("/reservation/resList.do")
public class ReservationListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ReservationListController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
        	HttpSession session = request.getSession();
    		int userNo = (int) session.getAttribute("userNo");
    		
    		ReservationServiceImpl resService = new ReservationServiceImpl();
    		List<ReservationDto> reservationList = resService.getReservations(userNo);
    		
    		request.setAttribute("reservationList", reservationList);
    		RequestDispatcher view = request.getRequestDispatcher("/views/reservation/reservationList.jsp");
    		view.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/views/error.html");
        }
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
