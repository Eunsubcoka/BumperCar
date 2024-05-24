<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
<%@ include file="/views/common/head.jsp"%>

</head>
<body>

	<%@ include file="/views/common/header.jsp"%>


	<nav class="unique-nav">
		<a href="#">카테고리</a> <a href="#">공지사항</a>
	</nav>
	<main class="notice-main">

		<section class="container mt-4" style="height: 70vh">
		<div class="card text-center" style="height: 100%">
			<form action="/freeBoard/edit.do" method="post">
			
				<input type="hidden" name="boardNo" value="${result.boardNo }" />
				
				<div class="card-header">
					<input type="text" name="title" value="${result.boardTitle }" />
				</div>
				<div class="card-body">
					<div class="d-flex justify-content-center mb-3">
						
						<div class="mx-3">
							작성일: <span id="fb-date">${result.boardIndate}</span>
						</div>
						<div class="mx-3">
							조회수: <span id="fb-views">${result.boardViews}</span>
						</div>
					</div>
					<hr>
					<textarea id = "editTxt" name="content" style="height: 300px">${result.boardContent }</textarea>							

				</div>
				<div class="card-footer d-flex justify-content-center">
					<button class="btn btn-secondary mx-2"
						onclick="window.history.back()">뒤로가기</button>
					<c:if test="${sessionScope.userNo == result.memberNo}">
						<button type="submit" class="btn btn-primary mx-2">수정</button>
					</c:if>
				</div>
			</form>
		</div>
	</section>

	</main>





	<%@ include file="/views/common/footer.jsp"%>

	<script src="/assets/js/main.js"></script>
	<script src="/assets/js/bootstrap.bundle.min.js"></script>

</body>
</html>

