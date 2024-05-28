<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
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
        <h1>맛나분식 리뷰</h1>
        <div class="container-review">
            <div class="review">
                <div class="rating-box">
                    <p class="text"><strong>음식점의 만족도를 별점으로 표현해주세요!</strong></p>
                    <p class="rating-text">5.0점(만족)</p>
                    <div class="rating">
                        <span class="rating__result"></span> 
                        <i class="rating__star far fa-star"></i>
                        <i class="rating__star far fa-star"></i>
                        <i class="rating__star far fa-star"></i>
                        <i class="rating__star far fa-star"></i>
                        <i class="rating__star far fa-star"></i>
                    </div>
                    <!-- <span class="rating-star">★★★★★</span> -->
                </div>
                <div class="content">
                    <textarea>
                        이 제품 정말 좋네요! 다음에도 꼭 재구매할게요.이 제품 정말 좋네요! 다음에도 꼭 재구매할게요.이 제품 정말 좋네요! 다음에도 꼭 재구매할게요.이 제품 정말 좋네요! 다음에도 꼭 재구매할게요.이 제품 정말 좋네요! 다음에도 꼭 재구매할게요.이 제품 정말 좋네요! 다음에도 꼭 재구매할게요.이 제품 정말 좋네요! 다음에도 꼭 재구매할게요.이 제품 정말 좋네요! 다음에도 꼭 재구매할게요.
                    </textarea>
                   
                </div>
                <!-- 버튼 -->
                <div class="btn">
                    <label for="file">
                        <div class="btn-upload">이미지추가</div>
                    </label>
                    <input type="file" name="file" id="file">
                    <button>
                       작성
                    </button>
                </div>
            </div>
        </div>

</section> 


	<%@ include file="/views/common/footer.jsp"%>


	<script src="../assets/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>

