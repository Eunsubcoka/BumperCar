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
        <a onclick="scrollToSection('restaurant')">레스토랑</a>
        <a onclick="scrollToSection('notice')">공지사항</a>
        <a onclick="scrollToTop()">맨 위로</a>
    </div>
    <main class="search-main">
        <div class="search-header">
            <h1>검색 결과</h1>
        </div>
        
        <div class="search-tab" id="restaurant">
            <h3>레스토랑</h3>
            <c:if test="${not empty restaurantList}">
                <div class="search-results">
                    <ul class="restaurant-list">
                        <c:forEach var="restaurant" items="${restaurantList}">
                            <li class="restaurant-item">
                                <div class="restaurant-image">
                                    <p>사진 추가 예정</p>
                                </div>
                                <div class="restaurant-info">
                                    <div class="restaurant-name">
                                        <a href="/restaurantDetail.do?restaurantId=${restaurant.restaurantNo}">${restaurant.restaurantName}</a>
                                    </div>
                                    <div class="font-down">카테고리: ${restaurant.category}</div>
                                    <div class="font-down">위치: ${restaurant.location}</div>
                                    <button class="toggle-review-btn" onclick="toggleReview(this)">리뷰 열기</button>
                                    <div class="review-box">
                                        <p>이곳에 리뷰 내용을 추가할 예정</p>
                                    </div>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>
            <c:if test="${empty restaurantList}">
                <p>검색 결과가 없습니다.</p>
            </c:if>
            <c:if test="${fn:length(restaurantList) > 5}">
                <div class="d-flex justify-content-end">
                    <a href="/restaurant/list.do?cpage=1&category=restaurantName&search-text=${searchText}"> >>더보기</a>
                </div>
            </c:if>
        </div>
        
        <div class="search-tab" id="notice">
            <h3>공지사항</h3>
            <c:if test="${not empty noticeList}">
                <ul class="notice-list">
                    <c:forEach var="notice" items="${noticeList}">
                        <li class="notice-item">
                        <div class="notice-name">
                                        <a href="/notice/detail.do?boardno=${notice.noticeNo}" class="notice-link">${notice.noticeTitle}</a>
                                    </div>
                            
                            <div class="notice-date">작성일: ${notice.noticeDate} &nbsp;&nbsp;&nbsp; 조회수: ${notice.noticeView}</div>                            
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
            <c:if test="${empty noticeList}">
                <p>검색 결과가 없습니다.</p>
            </c:if>
            <c:if test="${fn:length(noticeList) > 5}">
                <div class="d-flex justify-content-end">
                    <a href="/notice/list.do?cpage=1&category=noticeTitle&search-text=${searchText}"> >>더보기</a>
                </div>
            </c:if>
        </div>
    </main>
    <%@ include file="/views/common/footer.jsp"%>

    <script src="/assets/js/bootstrap.bundle.min.js"></script>
    <script src="/assets/js/main.js"></script>
    <script src="/assets/js/search.js"></script>

</body>
</html>