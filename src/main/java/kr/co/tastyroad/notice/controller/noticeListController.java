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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            noticeServiceImpl noticeService = new noticeServiceImpl();

            int cpage = Integer.parseInt(request.getParameter("cpage"));
            String category = request.getParameter("category");
            String searchText = request.getParameter("search-text");
            int boardLimit = request.getParameter("boardLimit") != null ? Integer.parseInt(request.getParameter("boardLimit")) : 10; // 게시글 수를 파라미터로 받음

            int listCount = noticeService.getListCount(category, searchText);

            int pageLimit = 5; // 한번에 보여지는 페이지 수 

            PageInfo pi = Pagination.getPageInfo(listCount, cpage, pageLimit, boardLimit);

            ArrayList<noticeDto> list = noticeService.getList(pi, category, searchText);

            int row = listCount - (cpage - 1) * boardLimit;

            // 요청 객체에 값 설정
            request.setAttribute("list", list);
            request.setAttribute("row", row);
            request.setAttribute("pi", pi);
            request.setAttribute("boardLimit", boardLimit); // 현재 선택된 게시글 수를 저장
            request.setAttribute("category", category); // category 값을 설정
            request.setAttribute("searchText", searchText); // searchText 값을 설정

            RequestDispatcher view = request.getRequestDispatcher("/views/notice/noticeList.jsp");
            view.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/views/error.html");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
