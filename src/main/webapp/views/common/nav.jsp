<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="kr.co.tastyroad.notice.model.dto.noticeDto" %>
<%@ page import="kr.co.tastyroad.notice.model.service.noticeService" %>
<%@ page import="kr.co.tastyroad.notice.model.service.noticeServiceImpl" %>

<style>
/* 스타일 추가 가능 */
</style>

<nav class="unique-nav">
    <div class="nav-align-center">
        <a href="/">카테고리</a>
        <a href="/notice/list.do?cpage=1&category=noticeTitle&search-text=">공지사항</a>
        <c:choose>
            <c:when test="${not empty sessionScope.userNo}">
                <a href="/reservation/resList.do">내 예약</a>
            </c:when>
            <c:otherwise>
                <a href="#" onclick="alertAndRedirect()">내 예약</a>
            </c:otherwise>
        </c:choose>
    </div>
    <!-- 아래 코드는 가장 최근에 올라온 공지사항을 출력하고 클릭시 해당 게시글로 이동하는 코드 -->
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

<script>
function alertAndRedirect() {
    alert("내 예약 현황을 확인하려면 로그인해야합니다");
    window.location.href = "/views/member/login.jsp";
}
</script>
