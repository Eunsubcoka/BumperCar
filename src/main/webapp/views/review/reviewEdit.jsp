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
        <div class="container-review">
        <input type="hidden" name="userNo" value="${result.userNo}"/>
            <div class="review">
                <div class="user-container">
                    <img class="user-img" src="/assets/image/bom.jpg" alt="사용자프로필">
                        <div class="user-info">
                            <span class="user-name"><strong>${sessionScope.userName}</strong></span>
                            <span class="rating">${result.ratings}점</span>
                        </div>
                </div>           
                <div class="title">
                    	<label for="title">제목:</label>
                    	<input type="text" id="title" name="reviewTitle" value="${result.reviewTitle}" required>
               	</div>
                <div class="content">
                	<label for="content">내용:</label>
                    <textarea name="reviewContent" required>${result.reviewContent}</textarea>
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
                        	<button onclick="window.history.back()">뒤로가기</button>
                        	<button onclick="location.href='/review/review.do'">수정</button>
                        </div>
            </div>
        </div>
        </form>
    </div>
</section> 

	<%@ include file="/views/common/footer.jsp"%>


	<script src="../assets/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>

