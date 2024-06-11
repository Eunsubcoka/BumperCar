<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
    <%@ include file="/views/common/head.jsp"%>
    <link rel="stylesheet" href="/assets/css/search.css">
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=597a12321ce91d26c9101324b5955ebd&libraries=services"></script>
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
    <main class="search-main" id="search-main"
          data-locations='[
            <c:forEach var="restaurant" items="${restaurantList}" varStatus="status">
                {"location": "${restaurant.location}", "name": "${restaurant.restaurantName}", "category": "${restaurant.category}", "restaurantNo": ${restaurant.restaurantNo}}<c:if test="${!status.last}">,</c:if>
            </c:forEach>
          ]'>
        <div class="search-header">
            <h1>검색 결과</h1>
        </div>

        <div id="map" style="width:100%;height:350px;"></div>

        <div class="search-tab" id="restaurant">
            <h3>레스토랑</h3>
            <c:if test="${not empty restaurantList}">
                <div class="search-results">
                    <ul class="restaurant-list" id="restaurant-list">
                        <c:forEach var="restaurant" items="${restaurantList}">
                            <li class="restaurant-item">
                                <div class="restaurant-image">
                                    <c:choose>
                                        <c:when test="${not empty restaurant.imgName}">
                                            <img src="/assets/image/${restaurant.imgName}" alt="${restaurant.restaurantName}">
                                        </c:when>
                                        <c:otherwise>
                                            <p>사진 추가 예정</p>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="restaurant-info">
                                    <div style="display:inline-block;">
                                        <a href="/restaurantDetail.do?restaurantId=${restaurant.restaurantNo}">${restaurant.restaurantName}</a>
                                        <div style="display:inline-block;" class= "font-down">
                                        <i class="fas fa-star"></i>
                                        <fmt:formatNumber value="${ratingsMap[restaurant.restaurantNo]}" type="number" minFractionDigits="1" maxFractionDigits="1" />
                                        </div>
                                    </div>
                                    
                                    <div class="font-down">카테고리: ${restaurant.category}</div>
                                    <div class="font-down">태그: 
                                        <c:set var="tagString" value="" />
                                        <c:forEach var="tag" items="${tagsMap[restaurant.restaurantNo]}" varStatus="status">
                                            <c:choose>
                                                <c:when test="${status.last}">
                                                    <c:set var="tagString" value="${tagString}${tag}" />
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="tagString" value="${tagString}${tag}, " />
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                        <c:if test="${fn:length(tagString) > 20}">
                                            ${fn:substring(tagString, 0, 20)}...
                                        </c:if>
                                        <c:if test="${fn:length(tagString) <= 20}">
                                            ${tagString}
                                        </c:if>
                                    </div>
                                    <div class="font-down">위치: ${restaurant.location}</div>
                                    
                                    <button class="toggle-review-btn" onclick="toggleReview(this)">리뷰 열기</button>
                                    <div class="review-box font-down-2" style="display: none;" onclick="navigateToReviews(${restaurant.restaurantNo})">
                                        <c:choose>
                                            <c:when test="${not empty top3ReviewsMap[restaurant.restaurantNo]}">
                                                <c:forEach var="review" items="${top3ReviewsMap[restaurant.restaurantNo]}">
                                                    <div class="font-down">
                                                        ${review.reviewTitle}
                                                        <i class="fas fa-star"></i>
                                                        ${review.ratings}
                                                    </div>
                                                    <p class="review-content">
                                                        <c:choose>
                                                            <c:when test="${fn:length(review.reviewContent) > 30}">
                                                                ${fn:substring(review.reviewContent, 0, 30)}...
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${review.reviewContent}
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </p>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <p style="font-size: 20px;">리뷰가 없습니다</p>
                                            </c:otherwise>
                                        </c:choose>
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
            <c:if test="${totalRestaurants > (cpage * 5)}">
                <div class="d-flex justify-content-end">
                    <button id="load-more-restaurants" data-page="${cpage}" data-search-text="${searchText}" data-tag="${tag}" data-category="restaurant" onclick="loadMoreRestaurants()">>>더보기</button>
                </div>
            </c:if>
        </div>

        <div class="search-tab" id="notice">
            <h3>공지사항</h3>
            <c:if test="${not empty noticeList}">
                <ul class="notice-list" id="notice-list">
                    <c:forEach var="notice" items="${noticeList}">
                        <li class="notice-item">
                            <div>
                                <a href="/notice/detail.do?boardno=${notice.noticeNo}">${notice.noticeTitle}</a>
                            </div>
                            <div class="notice-date">작성일: ${notice.noticeDate} &nbsp;&nbsp;&nbsp; 조회수: ${notice.noticeView}</div>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
            <c:if test="${empty noticeList}">
                <p>검색 결과가 없습니다.</p>
            </c:if>
            <c:if test="${totalNotices > (cpage * 5)}">
                <div class="d-flex justify-content-end">
                    <button id="load-more-notices" data-page="${cpage}" data-search-text="${searchText}" data-category="notice" onclick="loadMoreNotices()">>>더보기</button>
                </div>
            </c:if>
        </div>
    </main>

    <%@ include file="/views/common/footer.jsp"%>

    <script src="/assets/js/search.js"></script>
</body>
</html>
