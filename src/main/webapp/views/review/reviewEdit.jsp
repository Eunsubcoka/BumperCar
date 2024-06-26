<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 

<!doctype html>
<html lang="en">
<head>
<title>리뷰 수정 | TastyRoad</title>
<%@ include file="/views/common/head.jsp"%>
<link rel="stylesheet" href="/assets/css/reviewEdit.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css">

</head>
<body>

	<%@ include file="/views/common/header.jsp"%>
	<%@ include file="/views/common/nav.jsp"%>

	<!-- review section -->
<section>
    <div class="container-review-box">
        <h1>${list[0].restaurantName}</h1>
        <!-- <form action="/review/reviewEdit.do" method="POST" enctype="multipart/form-data"> -->
        <input type="hidden" name="reviewNo" id="reviewNo" value="${result.reviewNo}"/>
        <input type="hidden" name="restaurantNo" id="restaurantNo" value="${result.restaurantNo}"/>
        <input type="hidden" name="ratings" id="ratingHidden" value="${result.ratings}"/>
        <div class="container-review">
            <div class="review">
                <div class="user-container">
                    <c:if test="${result.profile == null}">
						<img class="user-img" src="/assets/image/default-profile.png" alt="프로필 사진 미리보기">
					</c:if>
					<c:if test="${result.profile != null}">
						<img class="user-img" src="/assets/image/member_profile/${result.profile}" alt="사용자프로필">
					</c:if>
                        <div class="user-info">
                            <span class="user-name"><strong>${sessionScope.userName}</strong></span>
                            <span class="rating" id="user-rating">
                            	<i class="far fa-star"></i>
                            	<i class="far fa-star"></i>
                            	<i class="far fa-star"></i>
                            	<i class="far fa-star"></i>
                            	<i class="far fa-star"></i>
                            	<span id="rating-value">${result.ratings}</span>점
                            </span>
                        </div>
                </div>           
                <div class="title">
                    	<label for="title">제목:</label> 
                    	<input type="text" id="title" name="reviewTitle" value="${result.reviewTitle}" required>
               	</div>
                <div class="content">
                	<label for="content">내용:</label>
                    <textarea name="reviewContent" id="reviewContent" required>${result.reviewContent}</textarea>
                </div>
                     <p>*사진은 최대 3장까지 가능합니다.</p>
				<div class="image_container" id="image_container"> <!-- varStatus : 상태변수 , ${status.count} :1부터 시작 순서-->
					
					<c:set var="maxIndex" value="0"></c:set> <!-- maxIndex변수선언 값은 0으로 초기화 -->
					
					<c:forEach var="fileList" items="${fileList}" varStatus="status">
						<c:if test="${fileList.reviewNo == result.reviewNo}">
							<!-- removeImageName : 삭제할 이미지 이름, removeImageStatus : 삭제 여부  -->
							<c:set var="maxIndex" value="${status.count}" /> 
					        <input type="hidden" name="removeImageName-${status.count}" id="removeImageName-${status.count}" value="${fileList.fileName}"/>
					        <input type="hidden" name="removeImageStatus-${status.count}" id="removeImageStatus-${status.count}" value="false"/>
							<input type="hidden" name="fileName" id="fileName" value="${fileList.fileName}"/>
							<input type="hidden" name="filePath" id="filePath" value="${fileList.filePath}"/>
							<div class="review-photo">
								<img class="close" src="/assets/image/close.png" onclick="imageClose(event, '${status.count}')">
								<img class="photo" src="/assets/uploads/review/${fileList.fileName}" alt="리뷰 사진">
							</div>
						</c:if>
					</c:forEach>
					<input type="hidden" name="maxIndex" id="maxIndex" value="${maxIndex }"> <!-- 컨트롤러로 값보내기 -->
				</div>
                <div id="btn">
                     <button id="submitReviewButton">수정</button>
                     <!-- <label>태그의 for속성을 사용하여 연결 - input태그의 id값 입력 -->
                     <label for="file" class="btn-upload">이미지추가</label>
                     <input type="file" name="file" id="file" onchange="getImageFiles(event);" multiple>                    
                     <button onclick="window.history.back()">뒤로가기</button>
                </div>
            </div>
        </div>
        <!-- </form> -->
    </div>
</section> 

	<%@ include file="/views/common/footer.jsp"%>


	<script src="../assets/dist/js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript" src="/assets/js/reviewEdit.js"></script> 
</body>
</html>
