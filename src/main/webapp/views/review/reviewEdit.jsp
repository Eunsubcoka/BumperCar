<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
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
        <h1>식당이름 리뷰</h1>
        <form action="/review/reviewEdit.do" method="POST" enctype="multipart/form-data">
        <input type="hidden" name="reviewNo" value="${result.reviewNo}"/>
        <input type="hidden" name="restaurantNo" value="${result.restaurantNo}"/>
        <input type="hidden" name="ratings" id="ratingHidden" value="${result.ratings}"/>
        
        <div class="container-review">
            <div class="review">
                <div class="user-container">
                    <img class="user-img" src="/assets/image/bom.jpg" alt="사용자프로필">
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
                    <textarea name="reviewContent" required>${result.reviewContent}</textarea>
                </div>
                     <p>*사진은 최대 3장까지 가능합니다.</p>
				<div class="image_container" id="image_container"> <!-- varStatus : 상태변수 , ${status.count} :1부터 시작 순서-->
					<c:forEach var="fileList" items="${fileList}" varStatus="status">
						<c:if test="${fileList.reviewNo == result.reviewNo}">
							<!-- removeImageName : 삭제할 이미지 이름, removeImageStatus : 삭제 여부  -->
					        <input type="hidden" name="removeImageName-${status.count}" id="removeImageName-${status.count}" value="${fileList.fileName}"/>
					        <input type="hidden" name="removeImageStatus-${status.count}" id="removeImageStatus-${status.count}" value="false"/>
							<input type="hidden" name="fileName" value="${fileList.fileName}"/>
							<input type="hidden" name="filePath" value="${fileList.filePath}"/>
							<div class="review-photo">
								<img class="close" id="closeImg" src="/assets/image/close.png" onclick="imageClose(event,'${status.count}')">
								<img class="photo" src="/assets/uploads/review/${fileList.fileName}" alt="리뷰 사진 1">
							</div>
						</c:if>
					</c:forEach>
				</div>
                <div class="btn">
                     <button type="submit">수정</button>
                     <label for="file" class="btn-upload">이미지추가</label>
                     <input type="file" name="file" id="file" onchange="getImageFiles(event);" multiple>                    
                     <button onclick="window.history.back()">뒤로가기</button>
                </div>
            </div>
        </div>
        </form>
    </div>
</section> 

	<%@ include file="/views/common/footer.jsp"%>


	<script src="../assets/dist/js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript" src="/assets/js/reviewEdit.js"></script> 
</body>
</html>
