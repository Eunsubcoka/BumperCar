<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>공지사항 상세페이지 | TastyRoad</title>
<%@ include file="/views/common/head.jsp"%>
<link rel="stylesheet" href="/assets/css/notice.css">
</head>
<body>
	<%@ include file="/views/common/header.jsp"%>
	<%@ include file="/views/common/nav.jsp"%>
	<main class="notice-main">
	<div class="notice-header">
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
        <h2 id="noticeTitle" style="text-align: center;">${result.noticeTitle}</h2>
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
                <img src="${result.filePath}${result.fileName}" alt="이미지" class="notice-image">
            </c:if>

            <p class="card-text">${result.noticeContent}</p>
        </div>
    </div>
    <div class="card-footer d-flex justify-content-center" style="background-color: white;">
        <button type="button" class="btn btn-secondary mx-2" onclick="window.history.back()">뒤로가기</button>
        <c:if test="${sessionScope.userType == 'admin'}">
            <button type="button" class="btn btn-primary mx-2" onclick="location.href='/tastyForm/noticeEditForm.do?boardno=${result.noticeNo}'">수정</button>
            <button type="submit" class="btn btn-danger mx-2">삭제</button>
        </c:if>
    </div>
</div>

<!-- 최신 공지사항 목록 표로 출력 -->
<div class="latest-notices">
    <h3>최신 공지사항</h3>
    <table class="table table-hover notice-table" style="margin-top: 10px; min-width:850px;">
        <thead>
            <tr>
                <th scope="col" class="title title-center">제목</th>
                <th scope="col" class="date">작성일</th>
                <th scope="col" class="views">조회수</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${latestNotices}">
                <tr onclick="location.href='/notice/detail.do?boardno=${item.noticeNo}'">
                    <td class="title title-padding">${item.noticeTitle}</td>
                    <td class="date" style="font-size:18px;">${item.noticeDate}</td>
                    <td class="views">${item.noticeView}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
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
