<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
        <a onclick="scrollToSection('slide2')">양식</a>
        <a onclick="scrollToSection('slide3')">중식</a>
        <a onclick="scrollToSection('slide4')">양식</a>
        <a onclick="scrollToTop()">맨 위로</a>
    </div>
    
    <main class="unique-main" >
        <section class="unique-food-category" id="slide1">
            <div class="sli_con">
                <h2 onclick="location.href='/category.do?category=1'">#한식</h2>
            </div>
            <div class="slider">
                <div class="slides">
                    <div class="slide">
                        <a href="/restaurantDetail.do?restaurantId=1" data-value="1"><img src="/assets/image/restaurant_images/김볶스.jpg" alt="Image 1"></a>
                        <a href="/restaurantDetail.do?restaurantId=2" data-value="2"><img src="/assets/image/restaurant_images/24시한방전주콩나물국밥.jpg" alt="Image 2"></a>
                        <a href="/restaurantDetail.do?restaurantId=3" data-value="3"><img src="/assets/image/restaurant_images/김밥스토리.jpg" alt="Image 3"></a>
                    </div>
                    <div class="slide">
                        <a href="/restaurantDetail.do?restaurantId=4" data-value="4"><img src="/assets/image/restaurant_images/비아김밥.jpg" alt="Image 4"></a>
                        <a href="/restaurantDetail.do?restaurantId=5" data-value="5"><img src="/assets/image/restaurant_images/안양 감자탕.jpg" alt="Image 5"></a>
                        <a href="/restaurantDetail.do?restaurantId=6" data-value="6"><img src="/assets/image/restaurant_images/형제들감자탕 안양점.jpg" alt="Image 6"></a>
                    </div>
                    <div class="slide">
                        <a href="/restaurantDetail.do?restaurantId=7" data-value="7"><img src="/assets/image/restaurant_images/하남돼지집.jpg" alt="Image 7"></a>
                        <a href="/restaurantDetail.do?restaurantId=8" data-value="8"><img src="/assets/image/restaurant_images/고향맛손칼국수.jpg" alt="Image 8"></a>
                        <a href="/restaurantDetail.do?restaurantId=9" data-value="9"><img src="/assets/image/restaurant_images/고인돌 김치떡삼겹.jpg" alt="Image 9"></a>
                    </div>
                    <button class="prev" onclick="changeOneSlide(-1)">&#10094;</button>
                    <button class="next" onclick="changeOneSlide(1)">&#10095;</button>
                </div>
            </div>
        </section>
        
        <section class="unique-food-category" id="slide2">
            <div class="sli_con">
                <h2>#중식</h2>
            </div>
            <div class="slider">
                <div class="slides">
                    <div class="slide">
                        <a href="/restaurantDetail.do?restaurantId=10" data-value="10"><img src="/assets/image/restaurant_images/마약떡볶이.jpg" alt="Image 1"></a>
                        <a href="/restaurantDetail.do?restaurantId=11" data-value="11"><img src="/assets/image/restaurant_images/신전떡볶이.jpg" alt="Image 2"></a>
                        <a href="/restaurantDetail.do?restaurantId=12" data-value="12"><img src="/assets/image/restaurant_images/두끼떡볶이.jpg" alt="Image 3"></a>
                    </div>
                    <div class="slide">
                        <a href="/restaurantDetail.do?restaurantId=13" data-value="13"><img src="/assets/image/restaurant_images/홍미집.jpg" alt="Image 4"></a>
                        <a href="/restaurantDetail.do?restaurantId=14" data-value="14"><img src="/assets/image/restaurant_images/역전우동0410 안양일번가점.jpg" alt="Image 5"></a>
                        <a href="/restaurantDetail.do?restaurantId=15" data-value="15"><img src="/assets/image/restaurant_images/버섯칼국수.jpg" alt="Image 6"></a>
                    </div>
                    <div class="slide">
                        <a href="/restaurantDetail.do?restaurantId=16" data-value="16"><img src="/assets/image/restaurant_images/로렌스308.jpg" alt="Image 7"></a>
                        <a href="/restaurantDetail.do?restaurantId=17" data-value="17"><img src="/assets/image/restaurant_images/모이세분식.jpg" alt="Image 8"></a>
                        <a href="/restaurantDetail.do?restaurantId=18" data-value="18"><img src="/assets/image/restaurant_images/롤링파스타 안양일번가점.jpg" alt="Image 9"></a>
                    </div>
                    <button class="prev" onclick="changeTwoSlide(-1)">&#10094;</button>
                    <button class="next" onclick="changeTwoSlide(1)">&#10095;</button>
                </div>
            </div>
        </section>

        <section class="unique-food-category" id="slide3">
            <div class="sli_con">
                <h2>#양식</h2>
            </div>
            <div class="slider">
                <div class="slides">
                    <div class="slide">
                        <a href="/restaurantDetail.do?restaurantId=19" data-value="19"><img src="/assets/image/restaurant_images/김볶스.jpg" alt="Image 1"></a>
                        <a href="/restaurantDetail.do?restaurantId=20" data-value="20"><img src="/assets/image/restaurant_images/24시한방전주콩나물국밥.jpg" alt="Image 2"></a>
                        <a href="/restaurantDetail.do?restaurantId=21" data-value="21"><img src="/assets/image/restaurant_images/김밥스토리.jpg" alt="Image 3"></a>
                    </div>
                    <div class="slide">
                        <a href="/restaurantDetail.do?restaurantId=22" data-value="22"><img src="/assets/image/restaurant_images/비아김밥.jpg" alt="Image 4"></a>
                        <a href="/restaurantDetail.do?restaurantId=23" data-value="23"><img src="/assets/image/restaurant_images/안양 감자탕.jpg" alt="Image 5"></a>
                        <a href="/restaurantDetail.do?restaurantId=24" data-value="24"><img src="/assets/image/restaurant_images/형제들감자탕 안양점.jpg" alt="Image 6"></a>
                    </div>
                    <div class="slide">
                        <a href="/restaurantDetail.do?restaurantId=25" data-value="25"><img src="/assets/image/restaurant_images/하남돼지집.jpg" alt="Image 7"></a>
                        <a href="/restaurantDetail.do?restaurantId=26" data-value="26"><img src="/assets/image/restaurant_images/고향맛손칼국수.jpg" alt="Image 8"></a>
                        <a href="/restaurantDetail.do?restaurantId=27" data-value="27"><img src="/assets/image/restaurant_images/고인돌 김치떡삼겹.jpg" alt="Image 9"></a>
                    </div>
                    <button class="prev" onclick="changeThreeSlide(-1)">&#10094;</button>
                    <button class="next" onclick="changeThreeSlide(1)">&#10095;</button>
                </div>
            </div>
        </section>

        <section class="unique-food-category" id="slide4">
            <div class="sli_con">
                <h2>#양식</h2>
            </div>
            <div class="slider">
                <div class="slides">
                    <div class="slide">
                        <a href="/restaurantDetail.do?restaurantId=28" data-value="28"><img src="/assets/image/restaurant_images/김볶스.jpg" alt="Image 1"></a>
                        <a href="/restaurantDetail.do?restaurantId=29" data-value="29"><img src="/assets/image/restaurant_images/24시한방전주콩나물국밥.jpg" alt="Image 2"></a>
                        <a href="/restaurantDetail.do?restaurantId=30" data-value="30"><img src="/assets/image/restaurant_images/김밥스토리.jpg" alt="Image 3"></a>
                    </div>
                    <div class="slide">
                        <a href="/restaurantDetail.do?restaurantId=31" data-value="31"><img src="/assets/image/restaurant_images/비아김밥.jpg" alt="Image 4"></a>
                        <a href="/restaurantDetail.do?restaurantId=32" data-value="32"><img src="/assets/image/restaurant_images/안양 감자탕.jpg" alt="Image 5"></a>
                        <a href="/restaurantDetail.do?restaurantId=33" data-value="33"><img src="/assets/image/restaurant_images/형제들감자탕 안양점.jpg" alt="Image 6"></a>
                    </div>
                    <div class="slide">
                        <a href="/restaurantDetail.do?restaurantId=34" data-value="34"><img src="/assets/image/restaurant_images/하남돼지집.jpg" alt="Image 7"></a>
                        <a href="/restaurantDetail.do?restaurantId=35" data-value="35"><img src="/assets/image/restaurant_images/고향맛손칼국수.jpg" alt="Image 8"></a>
                        <a href="/restaurantDetail.do?restaurantId=36" data-value="36"><img src="/assets/image/restaurant_images/고인돌 김치떡삼겹.jpg" alt="Image 9"></a>
                    </div>
                    <button class="prev" onclick="changeFourSlide(-1)">&#10094;</button>
                    <button class="next" onclick="changeFourSlide(1)">&#10095;</button>
                </div>
            </div>
        </section>
    </main>
    
    <%@ include file="/views/common/footer.jsp"%>

    <script src="/assets/js/bootstrap.bundle.min.js"></script>
    <script src="/assets/js/main.js"></script>
</body>
</html>
