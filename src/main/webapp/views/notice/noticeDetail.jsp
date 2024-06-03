<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/common/head.jsp"%>
<link rel="stylesheet" href="/assets/css/notice.css">
</head>
<body>
	<%@ include file="/views/common/header.jsp"%>
	<%@ include file="/views/common/nav.jsp"%>
	<main class="notice-main">
	<div class = "notice-header">
		<h1>공지사항</h1>
		</div>
		<section class="container">
			<div class="card text-center" style="height: 100%">
				<form action="/notice/delete.do" method="POST">
					<input type="hidden" name="noticeNo" value="${result.noticeNo}">
					<input type="hidden" name="fileNo" value="${result.fileNo}">
					<input type="hidden" name="fileName" value="${result.fileName}">

					<div class="card-content-wrapper">
						<div class="card-header" style="background-color: white;">
							<h2 id="noticeTitle" style = "text-align : center;">${result.noticeTitle}</h2>
						</div>
						<div class="card-body">
							<div class="d-flex justify-content-center mb-3">
								<div class="mx-3">
									작성일: <span id="noticeDate">${result.noticeDate}</span>
								</div>
								<div class="mx-3">
									조회수: <span id="noticeView">${result.noticeView}</span>
								</div>
							</div>
							<hr>
							<div style="margin-top: 20px; margin-bottom: 20px;">
								<c:if test="${not empty result.fileName and not empty result.filePath}">
									<img src="${result.filePath}${result.fileName}" alt="이미지" style="max-height: 50%;">
								</c:if>


								<p class="card-text">${result.noticeContent}</p>
							</div>
						</div>
						<div class="card-footer d-flex justify-content-center"
							style="background-color: white;">
							<button type="button" class="btn btn-secondary mx-2"
								onclick="window.history.back()">뒤로가기</button>
							<c:if test="${sessionScope.userType == 'admin'}">
								<button type="button" class="btn btn-primary mx-2"
									onclick="location.href='/tastyForm/noticeEditForm.do?boardno=${result.noticeNo}'">수정</button>
								<button type="submit" class="btn btn-danger mx-2">삭제</button>
							</c:if>
						</div>
					</div>
				</form>
			</div>
		</section>
	</main>
	<%@ include file="/views/common/footer.jsp"%>
	<script src="/assets/js/bootstrap.bundle.min.js"></script>
	<script src="/assets/js/main.js"></script>
</body>
</html>