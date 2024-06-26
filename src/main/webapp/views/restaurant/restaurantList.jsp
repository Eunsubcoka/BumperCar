<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

	

<!doctype html>
<html lang="en">
<head>
<title>레스토랑 목록 | TastyRoad</title>
<link rel="stylesheet" href="/assets/css/category.css">
<script type="text/javascript" src="/assets/js/restaurant.js"></script>

<%@ include file="/views/common/head.jsp"%>

</head>
<body>
	<%@ include file="/views/common/header.jsp"%>
	<%@ include file="/views/common/nav.jsp"%>

	<div class="main_con">
			<form id="categoryForm" action="/category.do" method="get">
				<input type="hidden" name="category" value="${category}"> <select
					class="form-select" id="inputGroupSelect02" name="seleType"
					style="width: 200px; height: 46px; flex: 0 0 auto;"
					onchange="this.form.submit()">
					<option value="restaurantName"
						<c:if test="${seleType == 'restaurantName'}">selected</c:if>>가게이름순</option>
					<option value="ratings"
						<c:if test="${seleType == 'ratings'}">selected</c:if>>별점순</option>
				</select>
			<c:choose>
				<c:when test="${sessionScope.userType == 'admin'}">
					<button type="button"
						onclick="location.href='/tastyForm/resAdd.do'" class="left_btn">등록하기</button>
				</c:when>
			</c:choose>
			</form>
		<div class="list_type">
			<h2 class="title02">음식점 목록</h2>

			<ul class="res_ul">
				<c:forEach var="restaurant" items="${restaurantList}">
					<li class="res_li">
						<div class="cont">
							<span class="res_img"> 
							<c:choose>
							<c:when test = "${fn:contains(restaurant.imgName, 'https')}">
							<img src="${restaurant.imgName}" class="photo_img"
								onclick="location.href='/restaurantDetail.do?restaurantId=${restaurant.restaurantNo}'">
							</c:when>
							<c:otherwise>
							<img src="/assets/image/${restaurant.imgName}" class="photo_img"
								onclick="location.href='/restaurantDetail.do?restaurantId=${restaurant.restaurantNo}'">
							</c:otherwise>
							</c:choose>
							</span>
							<div class="cnt">
								<div class="box_tit">${restaurant.restaurantName}</div>
								<div class="score">평점: ${restaurant.ratings }</div>
								<div class="res_tag">
									<c:forEach var="tag" items="${tag}">
										<c:if test="${restaurant.restaurantNo == tag.restaurantNo }">#${tag.tag} </c:if>
									</c:forEach>
								</div>
							</div>
							<c:choose>
								<c:when test="${sessionScope.userType == 'admin'}">
									<button type="button"
										onclick="location.href='/tastyForm/resEdit.do?resNo=${restaurant.restaurantNo}'">수정하기</button>
								</c:when>
							</c:choose>

						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
	
<script async
      data-adbreak-test="on"
      src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-123456789"
      crossorigin="anonymous">
</script>
<script>
   window.adsbygoogle = window.adsbygoogle || [];
   var adBreak = adConfig = function(o) {adsbygoogle.push(o);}
</script>
	<%@ include file="/views/common/footer.jsp"%>
</body>
</html>
