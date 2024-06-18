<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!doctype html>
<html lang="en">
<head>
    <%@ include file="/views/common/head.jsp"%>
</head>
<body>

    <%@ include file="/views/common/header.jsp"%>
    <%@ include file="/views/common/nav.jsp"%>
    
    <!-- 플로팅 네비게이션 바 -->
    <div class="floating-nav">
        <a onclick="scrollToSection('slide1')">한식</a>
        <a onclick="scrollToSection('slide2')">일식</a>
        <a onclick="scrollToSection('slide3')">중식</a>
        <a onclick="scrollToSection('slide4')">디저트</a>
        <a onclick="scrollToSection('slide5')">패스트푸드</a>
        <a onclick="scrollToTop()">맨 위로</a>
    </div>
    
    <main class="unique-main" style="min-width: 1100px;">
        <section class="unique-food-category" id="slide1">
            <div class="sli_con">
                <h2 onclick="location.href='/category.do?category=1'">#한식</h2>
            </div>
            <div class="slider">
                <div class="slides">
                    <c:forEach var="restaurant" items="${koreanRestaurants}" varStatus="status">
                        <c:if test="${status.index % 3 == 0}">
                            <div class="slide">
                        </c:if>
                        <a href="/restaurantDetail.do?restaurantId=${restaurant.restaurantNo}" data-value="${restaurant.restaurantNo}">
                            <c:if test="${restaurant.imgName != null && fn:contains(restaurant.imgName, 'https')}">
                                <img src="${restaurant.imgName}" alt="${restaurant.restaurantName}">
                            </c:if>
                            <c:if test="${restaurant.imgName != null && !fn:contains(restaurant.imgName, 'https')}">
                                <img src="/assets/image/restaurant_images/${restaurant.imgName}" alt="${restaurant.restaurantName}">
                            </c:if>
                            <div>${restaurant.restaurantName}</div>
                        </a>
                        <c:if test="${status.index % 3 == 2 || status.last}">
                            </div>
                        </c:if>
                    </c:forEach>
                    <button class="prev" onclick="changeSlide('slide1', -1)">❮</button>
                    <button class="next" onclick="changeSlide('slide1', 1)">❯</button>
                </div>
            </div>
        </section>

        <!-- Similar sections for other categories (일식, 중식, 디저트, 패스트푸드) -->

        <section class="unique-food-category" id="slide2">
            <div class="sli_con">
                <h2 onclick="location.href='/category.do?category=2'">#일식</h2>
            </div>
            <div class="slider">
                <div class="slides">
                    <c:forEach var="restaurant" items="${japaneseRestaurants}" varStatus="status">
                        <c:if test="${status.index % 3 == 0}">
                            <div class="slide">
                        </c:if>
                        <a href="/restaurantDetail.do?restaurantId=${restaurant.restaurantNo}" data-value="${restaurant.restaurantNo}">
                            <c:if test="${restaurant.imgName != null && fn:contains(restaurant.imgName, 'https')}">
                                <img src="${restaurant.imgName}" alt="${restaurant.restaurantName}">
                            </c:if>
                            <c:if test="${restaurant.imgName != null && !fn:contains(restaurant.imgName, 'https')}">
                                <img src="/assets/image/restaurant_images/${restaurant.imgName}" alt="${restaurant.restaurantName}">
                            </c:if>
                            <div>${restaurant.restaurantName}</div>
                        </a>
                        <c:if test="${status.index % 3 == 2 || status.last}">
                            </div>
                        </c:if>
                    </c:forEach>
                    <button class="prev" onclick="changeSlide('slide2', -1)">❮</button>
                    <button class="next" onclick="changeSlide('slide2', 1)">❯</button>
                </div>
            </div>
        </section>

        <!-- Add similar sections for 중식, 디저트, 패스트푸드 -->

        <section class="unique-food-category" id="slide3">
            <div class="sli_con">
                <h2 onclick="location.href='/category.do?category=3'">#중식</h2>
            </div>
            <div class="slider">
                <div class="slides">
                    <c:forEach var="restaurant" items="${chineseRestaurants}" varStatus="status">
                        <c:if test="${status.index % 3 == 0}">
                            <div class="slide">
                        </c:if>
                        <a href="/restaurantDetail.do?restaurantId=${restaurant.restaurantNo}" data-value="${restaurant.restaurantNo}">
                            <c:if test="${restaurant.imgName != null && fn:contains(restaurant.imgName, 'https')}">
                                <img src="${restaurant.imgName}" alt="${restaurant.restaurantName}">
                            </c:if>
                            <c:if test="${restaurant.imgName != null && !fn:contains(restaurant.imgName, 'https')}">
                                <img src="/assets/image/restaurant_images/${restaurant.imgName}" alt="${restaurant.restaurantName}">
                            </c:if>
                            <div>${restaurant.restaurantName}</div>
                        </a>
                        <c:if test="${status.index % 3 == 2 || status.last}">
                            </div>
                        </c:if>
                    </c:forEach>
                    <button class="prev" onclick="changeSlide('slide3', -1)">❮</button>
                    <button class="next" onclick="changeSlide('slide3', 1)">❯</button>
                </div>
            </div>
        </section>

        <section class="unique-food-category" id="slide4">
            <div class="sli_con">
                <h2 onclick="location.href='/category.do?category=4'">#디저트</h2>
            </div>
            <div class="slider">
                <div class="slides">
                    <c:forEach var="restaurant" items="${dessertRestaurants}" varStatus="status">
                        <c:if test="${status.index % 3 == 0}">
                            <div class="slide">
                        </c:if>
                        <a href="/restaurantDetail.do?restaurantId=${restaurant.restaurantNo}" data-value="${restaurant.restaurantNo}">
                            <c:if test="${restaurant.imgName != null && fn:contains(restaurant.imgName, 'https')}">
                                <img src="${restaurant.imgName}" alt="${restaurant.restaurantName}">
                            </c:if>
                            <c:if test="${restaurant.imgName != null && !fn:contains(restaurant.imgName, 'https')}">
                                <img src="/assets/image/restaurant_images/${restaurant.imgName}" alt="${restaurant.restaurantName}">
                            </c:if>
                            <div>${restaurant.restaurantName}</div>
                        </a>
                        <c:if test="${status.index % 3 == 2 || status.last}">
                            </div>
                        </c:if>
                    </c:forEach>
                    <button class="prev" onclick="changeSlide('slide4', -1)">❮</button>
                    <button class="next" onclick="changeSlide('slide4', 1)">❯</button>
                </div>
                
            </div>
        </section>

        <section class="unique-food-category" id="slide5">
            <div class="sli_con">
                <h2 onclick="location.href='/category.do?category=5'">#패스트푸드</h2>
            </div>
            <div class="slider">
                <div class="slides">
                    <c:forEach var="restaurant" items="${fastFoodRestaurants}" varStatus="status">
                        <c:if test="${status.index % 3 == 0}">
                            <div class="slide">
                        </c:if>
                        <a href="/restaurantDetail.do?restaurantId=${restaurant.restaurantNo}" data-value="${restaurant.restaurantNo}">
                            <c:if test="${restaurant.imgName != null && fn:contains(restaurant.imgName, 'https')}">
                                <img src="${restaurant.imgName}" alt="${restaurant.restaurantName}">
                            </c:if>
                            <c:if test="${restaurant.imgName != null && !fn:contains(restaurant.imgName, 'https')}">
                                <img src="/assets/image/restaurant_images/${restaurant.imgName}" alt="${restaurant.restaurantName}">
                            </c:if>
                            <div>${restaurant.restaurantName}</div>
                        </a>
                        <c:if test="${status.index % 3 == 2 || status.last}">
                            </div>
                        </c:if>
                    </c:forEach>
                    <button class="prev" onclick="changeSlide('slide5', -1)">❮</button>
                    <button class="next" onclick="changeSlide('slide5', 1)">❯</button>
                </div>
            </div>
        </section>
    </main>
    
    <%@ include file="/views/common/footer.jsp"%>

    <script src="/assets/js/bootstrap.bundle.min.js"></script>
    <script src="/assets/js/main.js"></script>
</body>
</html>
