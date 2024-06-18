<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
    <title>검색 결과 | TastyRoad</title>
    <%@ include file="/views/common/head.jsp"%>
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=597a12321ce91d26c9101324b5955ebd&libraries=services"></script>
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
    <main class="search-main" id="search-main"
          data-locations='[
            <c:forEach var="restaurant" items="${allRestaurantList}" varStatus="status">
                {"location": "${restaurant.location}", "name": "${restaurant.restaurantName}", "tags": [<c:forEach var="tag" items="${tagsMap[restaurant.restaurantNo]}" varStatus="tagStatus">"${tag}"<c:if test="${!tagStatus.last}">,</c:if></c:forEach>], "restaurantNo": ${restaurant.restaurantNo}, "imgName": "${restaurant.imgName}", "ratings": ${ratingsMap[restaurant.restaurantNo]}, "reviews": [<c:forEach var="review" items="${top3ReviewsMap[restaurant.restaurantNo]}" varStatus="reviewStatus">{"reviewTitle": "${review.reviewTitle}", "reviewContent": "${review.reviewContent}", "ratings": ${review.ratings}}<c:if test="${!reviewStatus.last}">,</c:if></c:forEach>]}<c:if test="${!status.last}">,</c:if>
            </c:forEach>
          ]'>
        <input type="hidden" id="userLat" name="userLat">
        <input type="hidden" id="userLon" name="userLon">
        <input type="hidden" id="totalNotices" value="${totalNotices}">

        <div class="search-header">
            <h1>검색 결과</h1>
        </div>

        <div id="map" style="width:100%;height:450px;"></div>

        <div class="search-tab" id="restaurant">
            <h3>레스토랑</h3>
            <div class="sort-options">
                거리순으로 50개까지만 표시됩니다.
                <!-- <button onclick="sortResults('latest')">최신순</button>
                <button onclick="sortResults('distance')">거리순</button> -->
            </div>
            <!-- 추가된 부분 -->
            <input type="hidden" id="sortOrder" value="distance">
            <c:if test="${not empty restaurantList}">
                <div class="search-results">
                    <ul class="restaurant-list" id="restaurant-list">
                        <c:forEach var="restaurant" items="${restaurantList}">
                            <li class="restaurant-item">
                                
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>
            <c:if test="${empty restaurantList}">
                <p>검색 결과가 없습니다.</p>
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
    <c:if test="${totalNotices > cpage * 5}">
        <div class="d-flex justify-content-end">
            <button id="load-more-notices" data-page="${cpage}" data-search-text="${searchText}" data-category="notice" onclick="loadMoreNotices()">>>더보기</button>
        </div>
    </c:if>
</div>


    </main>

    <%@ include file="/views/common/footer.jsp"%>

    <script src="/assets/js/main.js"></script>
    <script src="/assets/js/search.js"></script>
    <script>
        // 내 위치 정보를 가져와서 숨겨진 필드에 설정
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(position) {
                document.getElementById('userLat').value = position.coords.latitude;
                document.getElementById('userLon').value = position.coords.longitude;
/*                 console.log(position.coords.latitude);
                console.log(position.coords.longitude); */
            });
        }
        
    </script>
</body>
</html>
