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
        <form action="/review/reviewEdit.do" method="POST">
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
				<div class="image_container" id="image_container">
					<c:forEach var="fileList" items="${fileList}">
					qqq : ${fileList }
					<input type="hidden" name="fileName" value="${fileList.fileName}"/>
					<input type="hidden" name="filePath" value="${fileList.filePath}"/>
						<c:if test="${fileList.reviewNo == result.reviewNo}">
							<div class="review-photo">
								<img class="close" id="closeImg" src="/assets/image/close.png" onclick="imageClose(event)">
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
	<script type="text/javascript" src="/assets/js/reviewEnroll.js"></script> 
</body>
</html>

<script>
//별점 기능 함수
function executeRating(stars, ratingValueElement, initialRating) {
    const starClassActive = "fas fa-star"; // 활성화된 별 아이콘 클래스
    const starClassInactive = "far fa-star"; // 비활성화된 별 아이콘 클래스

    // 초기 별점 값 설정
    for (let i = 0; i < initialRating; i++) {
        stars[i].className = starClassActive;
    }

    stars.forEach((star, index) => {
        star.onclick = () => {
            // 클릭한 별의 인덱스
            const clickedIndex = index;

            // 클릭한 별을 포함하여 이전 별들을 활성화 또는 비활성화 상태로 변경
            stars.forEach((s, i) => {
                if (i <= clickedIndex) {
                    s.className = starClassActive; // 클릭한 별 이하의 별은 활성화 상태로 변경
                } else {
                    s.className = starClassInactive; // 클릭한 별보다 이후의 별은 비활성화 상태로 변경
                }
            });

            // 별점 값 업데이트
            ratingValueElement.textContent = (clickedIndex + 1); // 클릭한 별의 인덱스에 1을 더한 값을 별점으로 설정
        };
    });
}

// 페이지 로드 시 실행
window.onload = function() {
    const ratingStars = document.querySelectorAll(".rating i"); // 별점 아이콘 요소들 가져옴
    const ratingValueElement = document.getElementById("rating-value"); // 별점 값을 표시할 요소
    const initialRating = parseInt(document.getElementById("rating-value").textContent); // 초기 별점 값 가져옴

    // 별점 기능 실행
    executeRating(ratingStars, ratingValueElement, initialRating);
};
</script>

