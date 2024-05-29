package kr.co.tastyroad.reservation.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.tastyroad.reservation.dto.ReservationDto;
import kr.co.tastyroad.reservation.service.ReservationServiceImpl;

/**  
 * Servlet implementation class ReservationEnrollController
 */
@WebServlet("/reservation/resEnroll.do")
public class ReservationEnrollController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReservationEnrollController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		ReservationDto resDto = new ReservationDto();
		
		HttpSession session = request.getSession();
		int memberNo = (int)session.getAttribute("userNo");

		
		String name = request.getParameter("name");
		resDto.setUserName(name); 
		
		
		int headCount = Integer.parseInt(request.getParameter("headCount"));
		resDto.setHeadCount(headCount); 
		String email = request.getParameter("email");
		resDto.setUserEmail(email); 
		String phone = request.getParameter("phone");
		resDto.setPhone(phone);
		String date = request.getParameter("datepicker");
		System.out.println(date);
		resDto.setDate(date);
		resDto.setUserNo(memberNo);
		
		ReservationServiceImpl resService = new ReservationServiceImpl();
		
		int result = resService.resEnroll(resDto);
		
		if(result == 1) {
			RequestDispatcher view = request.getRequestDispatcher("/views/restaurant/restaurantDetail.jsp");
			view.forward(request,response);
			
		}
		else {
			RequestDispatcher view = request.getRequestDispatcher("/views/reservation/reservation.jsp");
			view.forward(request,response);
			
		}
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
