<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
<%@ include file="views/common/head.jsp"%>

</head>
<body>

	<%@ include file="views/common/header.jsp"%>


	<nav class="unique-nav">
		<a href="#">카테고리</a> <a href="#">공지사항</a>
	</nav>
	<main class="unique-main">
		<section class="unique-food-category" id="slide1">
			<h2>#한식</h2>
			<div class="slider">
				<div class="slides">
					<div class="slide">
						<img src="/assets/image/restaurant_images/김볶스.jpg" alt="Image 1"> <img src="/assets/image/restaurant_images/24시한방전주콩나물국밥.jpg"
							alt="Image 2"> <img src="/assets/image/restaurant_images/김밥스토리.jpg" alt="Image 3">
					</div>
					<div class="slide">
						<img src="/assets/image/restaurant_images/비아김밥.jpg" alt="Image 4"> <img src="/assets/image/restaurant_images/안양 감자탕.jpg"
							alt="Image 5"> <img src="/assets/image/restaurant_images/형제들감자탕 안양점.jpg" alt="Image 6">
					</div>
					<div class="slide">
						<img src="/assets/image/restaurant_images/하남돼지집.jpg" alt="Image 7"> <img src="/assets/image/restaurant_images/고향맛손칼국수.jpg"
							alt="Image 8"> <img src="/assets/image/restaurant_images/고인돌 김치떡삼겹.jpg" alt="Image 9">
					</div>
					<!-- 필요시 추가 -->
				</div>
				<button class="prev" onclick="changeOneSlide(-1)">&#10094;</button>
				<button class="next" onclick="changeOneSlide(1)">&#10095;</button>
			</div>


			<!-- <button class="unique-carousel-btn unique-right">▶</button> -->

		</section>
		
		<section class="unique-food-category" id="slide2">
		<h2>#양식</h2>
		<div class="slider">
				<div class="slides">
					<div class="slide">
						<img src="/assets/image/restaurant_images/마약떡볶이.jpg" alt="Image 1"> <img src="/assets/image/restaurant_images/신전떡볶이.jpg"
							alt="Image 2"> <img src="/assets/image/restaurant_images/두끼떡볶이.jpg" alt="Image 3">
					</div>
					<div class="slide">
						<img src="/assets/image/restaurant_images/홍미집.jpg" alt="Image 4"> <img src="/assets/image/restaurant_images/역전우동0410 안양일번가점.jpg"
							alt="Image 5"> <img src="/assets/image/restaurant_images/버섯칼국수.jpg" alt="Image 6">
					</div>
					<div class="slide">
						<img src="/assets/image/restaurant_images/로렌스308.jpg" alt="Image 7"> <img src="/assets/image/restaurant_images/모이세분식.jpg"
							alt="Image 8"> <img src="/assets/image/restaurant_images/롤링파스타 안양일번가점.jpg" alt="Image 9">
					</div>
					<!-- 필요시 추가 -->
				</div>
				<button class="prev" onclick="changeTwoSlide(-1)">&#10094;</button>
				<button class="next" onclick="changeTwoSlide(1)">&#10095;</button>
			</div>
			</section>
		
		
		<section class="unique-food-category" id="slide3">
			<h2>#양식</h2>
			<div class="slider">
				<div class="slides">
					<div class="slide">
						<img src="/assets/image/restaurant_images/김볶스.jpg" alt="Image 1"> <img src="/assets/image/restaurant_images/24시한방전주콩나물국밥.jpg"
							alt="Image 2"> <img src="/assets/image/restaurant_images/김밥스토리.jpg" alt="Image 3">
					</div>
					<div class="slide">
						<img src="/assets/image/restaurant_images/비아김밥.jpg" alt="Image 4"> <img src="/assets/image/restaurant_images/안양 감자탕.jpg"
							alt="Image 5"> <img src="/assets/image/restaurant_images/형제들감자탕 안양점.jpg" alt="Image 6">
					</div>
					<div class="slide">
						<img src="/assets/image/restaurant_images/하남돼지집.jpg" alt="Image 7"> <img src="/assets/image/restaurant_images/고향맛손칼국수.jpg"
							alt="Image 8"> <img src="/assets/image/restaurant_images/고인돌 김치떡삼겹.jpg" alt="Image 9">
					</div>
					<!-- 필요시 추가 -->
				</div>
				<button class="prev" onclick="changeThreeSlide(-1)">&#10094;</button>
				<button class="next" onclick="changeThreeSlide(1)">&#10095;</button>
			</div>
		</section>

		<section class="unique-food-category" id="slide4">
			<h2>#양식</h2>
			<div class="slider">
				<div class="slides">
					<div class="slide">
						<img src="/assets/image/restaurant_images/김볶스.jpg" alt="Image 1"> <img src="/assets/image/restaurant_images/24시한방전주콩나물국밥.jpg"
							alt="Image 2"> <img src="/assets/image/restaurant_im	ages/김밥스토리.jpg" alt="Image 3">
					</div>
					<div class="slide">
						<img src="/assets/image/restaurant_images/비아김밥.jpg" alt="Image 4"> <img src="/assets/image/restaurant_images/안양 감자탕.jpg"
							alt="Image 5"> <img src="/assets/image/restaurant_images/형제들감자탕 안양점.jpg" alt="Image 6">
					</div>
					<div class="slide">
						<img src="/assets/image/restaurant_images/하남돼지집.jpg" alt="Image 7"> <img src="/assets/image/restaurant_images/고향맛손칼국수.jpg"
							alt="Image 8"> <img src="/assets/image/restaurant_images/고인돌 김치떡삼겹.jpg" alt="Image 9">
					</div>
					<!-- 필요시 추가 -->
				</div>
				<button class="prev" onclick="changeFourSlide(-1)">&#10094;</button>
				<button class="next" onclick="changeFourSlide(1)">&#10095;</button>
			</div>
		</section>
		
	</main>


	


	<%@ include file="views/common/footer.jsp"%>

	<script src = "/assets/js/main.js"></script>
	<script src="/assets/js/bootstrap.bundle.min.js"></script>

</body>
</html>

