<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
<%@ include file="/views/common/head.jsp"%>
<link rel="stylesheet" href="/assets/css/review.css">

</head>
<body>

	<%@ include file="/views/common/header.jsp"%>
	<%@ include file="/views/common/nav.jsp"%>

	<!-- review section -->
	<section>
		<div class="container-review-box">
			<h1>식당이름 리뷰</h1>
			<c:choose>
				<c:when test="${empty list}">
					<!-- 조건: list객체가 비워져 있을때 -->
					<p>등록된 글이 없습니다.</p>
				</c:when>
				<c:otherwise>
					<c:forEach var="item" items="${list}">
						<div class="container-review">
							<input type="hidden" name="userNo" value="">
							<div class="review">
								<div class="user-container">
									<img class="user-img" src="/assets/image/bom.jpg" alt="사용자프로필">
									<div class="user-info">
										<span class="user-name"><strong>${sessionScope.userName}</strong></span>
										<span class="rating">${item.ratings}</span>
									</div>
								</div>
								<div class="date">${item.reviewDate}</div>
								<div class="title">
									<h2>${item.reviewTitle}</h2>
								</div>
								<div class="content">
									<p name="reviewContent" required>${item.reviewContent}</p>
								</div>


								<div class="review-photos">
									<div class="review-photo">
										<img class="photo" src="/assets/image/food.jpg" alt="리뷰 사진 1">
									</div>
									<div class="review-photo">
										<img class="photo" src="/assets/image/food.jpg" alt="리뷰 사진 2">
									</div>
									<div class="review-photo">
										<img class="photo" src="/assets/image/food.jpg" alt="리뷰 사진 3">
									</div>
									<div class="review-photo">
										<img class="photo" src="/assets/image/food.jpg" alt="리뷰 사진 3">
									</div>
								</div>
								<div class="btn">
									<!-- <button onclick="window.history.back()">뒤로가기</button> -->
									<%-- <c:if test="${sessionScope.userNo == result.userNo}"> --%>
									<button type="button"
										onclick="location.href='/tastyForm/enrollReviewForm.do'">등록</button>
									<button type="button"
										onclick="location.href='/tastyForm/editReviewForm.do'">수정</button>
									<button>삭제</button>
									<%-- </c:if> --%>
								</div>
							</div>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>





		</div>
	</section>

	<%@ include file="/views/common/footer.jsp"%>


	<script src="../assets/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>

