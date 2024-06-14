<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
<title>레스토랑 상세페이지 | TastyRoad</title>
<link rel="stylesheet" href="/assets/css/restaurantDetail.css">
<script type="text/javascript" src="/assets/js/restaurant.js"></script>
<script src="https://t1.kakaocdn.net/kakao_js_sdk/2.7.2/kakao.min.js"
	integrity="sha384-TiCUE00h649CAMonG018J2ujOgDKW/kVWlChEuu4jK2vxfAAD0eZxzCKakxg55G4"
	crossorigin="anonymous"></script>
<script>
	Kakao.init('597a12321ce91d26c9101324b5955ebd'); // 사용하려는 앱의 JavaScript 키 입력
</script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css">

<%@ include file="/views/common/head.jsp"%>

</head>
<body id="body" style="font-family: 'GmarketSans'">
	<%@ include file="/views/common/header.jsp"%>
	<%@ include file="/views/common/nav.jsp"%>


	<div class="res_con_wrap">
		<div class="res_content">
			<div class="res_left_container">
				<div class="res_info_wrap">
					<div class="res_header">
						<c:forEach var = "imgList" items="${imgList }">
						<img src="/assets/image/${imgList}" alt="대한옥">
						</c:forEach>
					</div>
					<div class="details">
						<h1 class="">${result.restaurantName }</h1>
						<div class="rating">
							<span class="rating"><i class="fas fa-star"
								style="color: #ff9800"></i>${ratings}점</span>
						</div>
						<div id="share_pop" class="layer_pop">
							<img alt="" src="/assets/image/close.png" onclick="closePop()">
							<div class="share_flex">
								<h4>공유하기</h4>
								<ul>
									<li><img alt="" src="/assets/image/icon-insta.png">
										<p>인스타</p></li>
									<li><img alt="" src="/assets/image/icon-kakao.png"
										onclick="shareMessage()">
										<p>카카오</p></li>
									<li><img alt="" src="/assets/image/icon-twitter.png"
										onclick="shareTwitter()">
										<p>트위터</p></li>
								</ul>
							</div>
						</div>
						<p class="address">&nbsp 주소: ${result.location }</p>
						<p class="contact">&nbsp 전화: ${result.restaurantPhone }</p>
						<div class="share" onclick="openPop()">링크</div>
					</div>

					<div class="recommendations">
						<h2>태그</h2>
						<c:forEach var="tag" items="${tag}">
						<div class="item">
							<div class="info">
								<span>#${tag }</span> 
							</div>
						</div>
						</c:forEach>
					</div>
				</div>
				<div class="res_menu_wrap">

					<div class="details">
						<h1>메뉴 정보</h1>
							<div class="share" onclick="checkLoginAndReserve(${resNo})">
							예약하기</div>
					</div>

					<div class="recommendations">
						<c:forEach var="menuList" items="${menuList}">
							<c:if test="${menuList.restaurantNo == result.restaurantNo}">
								<div class="item">
									<div class="info">
										<span>${menuList.menu }</span> <span>${menuList.price }원</span>
									</div>
								</div>
							</c:if>
						</c:forEach>

					</div>
				</div>

			</div>

			<div class="res_right_container">

				<div class="mapContainer">
					<div id="map" style="width: 200px; height: 200px;"></div>
				</div>

				<div class="res_review_wrap"
					onclick="location.href='/review/review.do?restaurantNo=${resNo}'">
					<c:choose>
						<c:when test="${empty list}">
							<!-- 조건: list객체가 비워져 있을때 -->
							<p>등록된 글이 없습니다.</p>
						</c:when>
						<c:otherwise>
							<c:forEach var="item" items="${list}" begin="0" end="2">
								<div class="res_wrapper">
									<h5>${item.reviewTitle } </h5>
									<div class="res_review_id">${sessionScope.userName}</div>

									<div class="res_review_rating">
										<span class="rating"><i class="fas fa-star"></i>${item.ratings}점</span>
									</div>
									<div class="res_review_img">
										<c:forEach var="fileList" items="${fileList}">
											<c:if test="${fileList.reviewNo == item.reviewNo}">
												<img src="/assets/uploads/review/${fileList.fileName}"
													alt="리뷰 사진 1">
											</c:if>
										</c:forEach>
									</div>

									<div class="res_review_content">
										<a>${item.reviewContent}</a>
									</div>
								</div>
							</c:forEach>
						</c:otherwise>
					</c:choose>
				</div>


				<!--  -->
			</div>
		</div>

	</div>

	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=597a12321ce91d26c9101324b5955ebd&libraries=services"></script>
	<script>
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		mapOption = {
			center : new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
			level : 3 // 지도의 확대 레벨
		};

		// 지도를 생성합니다    
		var map = new kakao.maps.Map(mapContainer, mapOption);

		// 주소-좌표 변환 객체를 생성합니다
		var geocoder = new kakao.maps.services.Geocoder();
		
		// 주소로 좌표를 검색합니다
		geocoder.addressSearch('${result.location }', function(result, status) {
		    // 정상적으로 검색이 완료됐으면 
		    if (status === kakao.maps.services.Status.OK) {
		        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

		        // 결과값으로 받은 위치를 마커로 표시합니다
		        var marker = new kakao.maps.Marker({
		            map: map,
		            position: coords
		        });

		        // 인포윈도우로 장소에 대한 설명을 표시합니다
		        var infowindow = new kakao.maps.InfoWindow({
		            content: '<div style="width:150px;text-align:center;padding:6px 0;">${result.restaurantName}</div>'
		        });
		        infowindow.open(map, marker);

		        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
		        map.setCenter(coords);
		    } 
		});   
		function checkLoginAndReserve(resNo) {
			<c:if test="${empty sessionScope.userNo}">
				alert("로그인이 필요합니다.");
				
			</c:if>
			<c:if test="${!empty sessionScope.userNo}">
				location.href='/tastyForm/reservation.do?restaurantNo=' + resNo;
			</c:if>
		}

	</script>

	<script src="/assets/js/bootstrap.bundle.min.js"></script>

	<div class="bg"></div>
	<%@ include file="/views/common/footer.jsp"%>
</body>
</html>
