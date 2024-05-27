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

		<h1>공지사항</h1>
		<hr>


		<section class="container" style="height: 70vh">
			<div class="card text-center" style="height: 100%">
				<form action="/notice/delete.do" method="POST">
					<input type="hidden" name="boardNo" value="${result.boardNo}">
					<input type="hidden" name="fileNo" value="${result.fileNo }">
					<input type="hidden" name="fileName" value="${result.fileName }">

					<div class="card-header">
						<h2 id="fb-title">${result.boardTitle }</h2>
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
						<div style="margin-top: 20px; margin-bottom: 20px;">
							
							<p class="card-text">${result.boardContent }
							<br><br><br><br><br><br><br><br></p>
						</div>
					</div>
					<div class="card-footer d-flex justify-content-center">
						<button type="button" class="btn btn-secondary mx-2"
							onclick="window.history.back()">뒤로가기</button>
						<c:if test="${sessionScope.userNo == result.memberNo}">
							<button type="button" class="btn btn-primary mx-2"
								onclick="location.href='/form/editForm.do?boardNo=${result.boardNo}'">수정</button>
							<button type="submit" class="btn btn-danger mx-2">삭제</button>
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

