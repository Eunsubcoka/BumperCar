<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
    <%@ include file="/views/common/head.jsp"%>
    <link rel="stylesheet" href="/assets/css/search.css">
</head>
<body>

    <%@ include file="/views/common/header.jsp"%>
    <%@ include file="/views/common/nav.jsp"%>
    
    <!-- 플로팅 네비게이션 바 -->
    <div class="floating-nav">
        <a href="#notice" onclick="scrollToSection('notice')">공지사항</a>
        <a href="#review" onclick="scrollToSection('review')">리뷰</a>
        <a href="#restaurant" onclick="scrollToSection('restaurant')">레스토랑</a>
        <a href="#" onclick="scrollToTop()">맨 위로</a>
    </div>
    <main class="search-main">
        <div class="search-header">
            <h1>검색 결과</h1>
        </div>
        
        <div class="search-tab" id="notice">
            <h3>공지사항</h3>
            <c:if test="${not empty noticeList}">
                <ul>
                    <c:forEach var="notice" items="${noticeList}">
                        <li>
                            <a href="/notice/detail.do?boardno=${notice.noticeNo}">${notice.noticeTitle}</a>
                            <div style="font-size:12px;">작성일: ${notice.noticeDate}</div>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
            <c:if test="${empty noticeList}">
                <p>검색 결과가 없습니다.</p>
            </c:if>
            <c:if test="${fn:length(noticeList) >5}">
            <div class="d-flex justify-content-end">
                <a href="/notice/list.do?cpage=1&category=noticeTitle&search-text=${searchText}"> >>더보기</a>
            </div>
            </c:if>
            
        </div>
        
        <div class="search-tab" id="review">
            <h3>리뷰</h3>
            <div class="d-flex justify-content-end">
                <a href="#"> >>더보기</a>
            </div>
        </div>
        
        <div class="search-tab" id="restaurant">
            <h3>레스토랑</h3>
            <div class="d-flex justify-content-end">
                <a href="#"> >>더보기</a>
            </div>
        </div>
    </main>
    
    <%@ include file="/views/common/footer.jsp"%>

    <script src="/assets/js/bootstrap.bundle.min.js"></script>
    <script src="/assets/js/main.js"></script>
    <script src="/assets/js/search.js"></script>
</body>
</html>
