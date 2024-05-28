package kr.co.tastyroad.notice.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.tastyroad.common.PageInfo;
import kr.co.tastyroad.common.Pagination;
import kr.co.tastyroad.notice.model.dto.noticeDto;
import kr.co.tastyroad.notice.model.service.noticeServiceImpl;

@WebServlet("/notice/list.do")
public class noticeListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public noticeListController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		noticeServiceImpl noticeService = new noticeServiceImpl();
		
		int cpage = Integer.parseInt(request.getParameter("cpage"));
		String category = request.getParameter("category");
		String searchText = request.getParameter("search-text");
		
		
		int listCount = noticeService.getListCount(category, searchText);
		
		int pageLimit = 5;
		
		int boardLimit = 5;
		
		PageInfo pi = Pagination.getPageInfo(listCount, cpage, pageLimit, boardLimit);
		
		ArrayList<noticeDto> list = noticeService.getList(pi, category, searchText);
		
		int row = listCount - (cpage-1) * boardLimit;
		
		request.setAttribute("list", list);
		request.setAttribute("row", row);
		request.setAttribute("pi", pi);
		
		RequestDispatcher view = request.getRequestDispatcher("/views/notice/noticeList.jsp");
		view.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
