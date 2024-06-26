<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
 
<!doctype html>
<html lang="en">
<head>
<title>리뷰 목록 | TastyRoad</title>
<%@ include file="/views/common/head.jsp"%>
<link rel="stylesheet" href="/assets/css/review.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css">

</head>
<body>

	<%@ include file="/views/common/header.jsp"%>
	<%@ include file="/views/common/nav.jsp"%>

	<!-- review section -->
	<section>
		<div class="container-review-box">

			<h1>${list[0].restaurantName}</h1>                     
			<button type="button" onclick="location.href='/tastyForm/enrollReviewForm.do?restaurantNo=${restaurantNo}'">등록</button>
			<c:choose>
				<c:when test="${empty list}">
					<!-- 조건: list객체가 비워져 있을때 -->
					<p>등록된 글이 없습니다.</p>
				</c:when>
				<c:otherwise>
					<c:forEach var="item" items="${list}">
					<form action="/review/delete.do" method="POST">
					<input type="hidden" name="restaurantNo" value="${restaurantNo}"/>                                    
					<input type="hidden" name="reviewNo" id="reviewNo" value="${item.reviewNo}"/> 
					<input type="hidden" name="ratings" value="${result.ratings}"/>
						<div class="container-review">
							<div class="review">
								<div class="user-container">
									<c:if test="${item.profile == null}">
										<img class="user-img" src="/assets/image/default-profile.png" alt="프로필 사진 미리보기">
									</c:if>
									<c:if test="${item.profile != null}">
										<img class="user-img" src="/assets/image/member_profile/${item.profile}" alt="사용자프로필">
									</c:if>
									<div class="user-info">
										<span class="user-name"><strong>${item.userName}</strong></span>
										<span class="rating"><i class="fas fa-star"></i>${item.ratings}점</span>
									</div>
									<!-- 좋아요 기능 -->
                                    <div class="heart_area">
                                        <a href="javascript:void(0);" class="heartA" data-reviewNo="${item.reviewNo}">
                                            <span class="heart heartEmpty">♡</span>
                                            <span class="heart heartFull">♥</span>
                                        </a>
                                    </div>
                                    <span class="heartCount"> like ${item.likeCount} </span>
								</div>
								<div class="date">${item.reviewDate}</div>
								<div class="title" required>
									<h2>${item.reviewTitle}</h2>
								</div>
								<div class="content">
									<p name="reviewContent" required>${item.reviewContent}</p>
								</div>
								<div class="review-photos">
								<c:forEach var="fileList" items="${fileList}">
								  <c:if test="${fileList.reviewNo == item.reviewNo}">
									<div class="review-photo">
										<img class="photo" src="/assets/uploads/review/${fileList.fileName}" alt="리뷰 사진 1">
									</div>
								  </c:if>
								</c:forEach>
								</div>
								<div id="btn">
									<button type="button" onclick="window.history.back()">뒤로가기</button>
									<c:if test="${sessionScope.userNo == item.userNo}">
									<button type="button" onclick="location.href='/tastyForm/editReviewForm.do?reviewNo=${item.reviewNo}&restaurantNo=${restaurantNo}&profile=${item.profile}'">수정</button>
									<button type="submit">삭제</button> 
									</c:if>
								</div>
							</div>
						</div>
					</form>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		<c:if test="${not empty list}">
		<a href="#" id="more"> 리뷰 더보기 + </a>
		</c:if>
		</div>
	</section>

	<%@ include file="/views/common/footer.jsp"%>
	
	<script>
	    document.addEventListener("DOMContentLoaded", function() {
	    	// 웹페이지 주소에서 단어를 찾는 도구 사용
	        const urlParams = new URLSearchParams(window.location.search);
	     	// 만약 주소에 'deleteSuccess=true'라는 단어가 있으면,
	        if (urlParams.get('deleteSuccess') === 'true') {
	            alert('리뷰가 성공적으로 삭제되었습니다.');
	        }
	    });
	</script>
	<script src="../assets/dist/js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript" src="/assets/js/review.js"></script> 

</body>
</html>