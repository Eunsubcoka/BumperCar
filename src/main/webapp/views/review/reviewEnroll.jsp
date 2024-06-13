<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
<title>리뷰 등록 | TastyRoad</title>
<%@ include file="/views/common/head.jsp"%>

	<link rel="stylesheet" href="/assets/css/reviewEnroll.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css">


</head>
<body>

	<%@ include file="/views/common/header.jsp"%>
	<%@ include file="/views/common/nav.jsp"%>

<!-- review enroll section -->
<section>
    <div class="container-review-box">
        <form enctype="multipart/form-data">
        <h1>리뷰작성</h1>
        <div class="container-review">
            <div class="review">
            <input type="hidden" name="userNo" value="${sessionScope.userNo}">
            <input type="hidden" name="restaurantNo" id="restaurantNo" value="${restaurantNo}">
            <input type="hidden" name="ratingStars" id="stars" value="">
                <div class="rating-box">
                    <p class="text"><strong>음식점의 만족도를 별점으로 표현해주세요!</strong></p>
                    <p class="rating-text" name="rating">0.0점(만족도)</p>
                    <div class="rating">
                        <span class="rating__result"></span> 
                        <i class="rating__star far fa-star"></i>
                        <i class="rating__star far fa-star"></i>
                        <i class="rating__star far fa-star"></i>
                        <i class="rating__star far fa-star"></i>
                        <i class="rating__star far fa-star"></i>
                    </div>
                </div>
                <div class="input-container">
                    <label for="title">제목:</label>
                    <input type="text" id="title" name="reviewTitle" required>
                </div>
                <div class="input-container">
                    <label for="author">작성자:</label>
                    <input type="text" id="author" value="${sessionScope.userName}" disabled>
                </div>                

                <div class="content">
                	<label for="content">내용:</label>
                    <textarea name="reviewContent" id="reviewContent" required></textarea>
                </div>
                <!-- 버튼 -->
                <div id="btn">
                    <p>*사진은 최대 3장까지 가능합니다.</p>
                    <div id="image_container"></div>
                    <label for="inputFile" class="btn-upload">이미지추가</label>		<!-- accept 특정 파일 유형만 허용, multiple 속성을 추가하면 2개 이상의 파일을 추가 -->
                    <input type="file" name="file" id="inputFile" onchange="getImageFiles(event);"  multiple>
                    <button type="button" onclick="imageCheck();">작성</button>
                </div>
            </div>
        </div>
        </form>
	</div>
</section> 


	<%@ include file="/views/common/footer.jsp"%>


	<script src="../assets/dist/js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript" src="/assets/js/reviewEnroll.js"></script> 
</body>
</html>

	

