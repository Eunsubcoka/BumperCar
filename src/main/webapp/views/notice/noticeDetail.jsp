<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
<%@ include file="/views/common/head.jsp"%>
<link rel="stylesheet" href="/assets/css/notice.css">
</head>
<body>

	<%@ include file="/views/common/header.jsp"%>


	<%@ include file="/views/common/nav.jsp"%>
	<main class="notice-main">

		<h1>공지사항</h1>
		<hr>


		<section class="container" style="height: 70vh">
			<div class="card text-center" style="height: 100%">
				<form action="/notice/delete.do" method="POST">
					<input type="hidden" name="noticeNo" value="${result.noticeNo}">
					<input type="hidden" name="fileNo" value="${result.fileNo }">
					<input type="hidden" name="fileName" value="${result.fileName }">

					<div class="card-header">
						<h2 id="noticeTitle">${result.noticeTitle }</h2>
					</div>
					<div class="card-body">
						<div class="d-flex justify-content-center mb-3">
							
							<div class="mx-3">
								작성일: <span id="fb-date">${result.noticeDate}</span>
							</div>
							<div class="mx-3">
								조회수: <span id="fb-views">${result.noticeView}</span>
							</div>
						</div>
						<hr>
						<div style="margin-top: 20px; margin-bottom: 20px;">
							
							<p class="card-text">${result.noticeContent }
							<br><br><br><br><br><br><br><br></p>
						</div>
					</div>
					<div class="card-footer d-flex justify-content-center">
						<button type="button" class="btn btn-secondary mx-2"
							onclick="window.history.back()">뒤로가기</button>
							<!-- 나중에 합쳤을때 주석부분 풀면 로그인한 유저의 타입이 A일때 버튼 등장  -->
						<%-- <c:if test="${sessionScope.userType == 'A'}"> --%>
							<button type="button" class="btn btn-primary mx-2"
								onclick="location.href='/tastyForm/editForm.do?boardno=${result.noticeNo}'">수정</button>
							<button type="submit" class="btn btn-danger mx-2">삭제</button>
						<%-- </c:if> --%>
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

