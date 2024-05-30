<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="kr.co.tastyroad.notice.model.dto.noticeDto" %>
<%@ page import="kr.co.tastyroad.notice.model.service.noticeService" %>
<%@ page import="kr.co.tastyroad.notice.model.service.noticeServiceImpl" %>

<style>

</style>

<nav class="unique-nav">
    <div class="nav-align-center">
        <a href="/">카테고리</a>
        <a href="/notice/list.do?cpage=1&category=noticeTitle&search-text=">공지사항</a>
    </div>
    <%-- 최신 공지사항 제목 출력 --%>
    <div class="nav-align-right">
        <%
            noticeService service = new noticeServiceImpl();
            noticeDto latestNotice = service.getLatestNotice();
            if (latestNotice != null) {
        %>
            <span>최신 공지사항: </span>
            <a href="/notice/detail.do?boardno=<%= latestNotice.getNoticeNo() %>"><%= latestNotice.getNoticeTitle() %></a>
        <% } %>
    </div>
</nav>
