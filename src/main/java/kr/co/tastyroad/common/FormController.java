package kr.co.tastyroad.common;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/tastyForm/*")
public class FormController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
     public FormController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html; charset-utf-8");
		
		String action = request.getPathInfo();
		String nextPage = "";
		
		System.out.println("a : " + action);
		if(action.equals("/registerForm.do")) {
			nextPage = "/views/member/register.jsp";
		} else if(action.equals("/restaurantDetail.do")) {
			nextPage = "/views/member/register.jsp";
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
