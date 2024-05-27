package kr.co.tastyroad.reservation.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.tastyroad.reservation.dto.ReservationDto;

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
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		ReservationDto resDto = new ReservationDto();
		String name = request.getParameter("name");
		int adults = Integer.parseInt(request.getParameter("adults"));
		int kids = Integer.parseInt(request.getParameter("children"));
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		
		
		
		System.out.println(email);
	}

}
