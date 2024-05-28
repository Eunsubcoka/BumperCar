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
	<main class="notice-main" style = "height : 85vh">
		
		<h1>공지사항</h1>
		<hr>
		<div class="input-group mb-3" style="width: 100%; margin-top: 40px">
		
			<form action="/notice/list.do" method="GET">
				<input type="hidden" name="cpage" value="1">
				
				<select class="form-select" id="inputGroupSelect02" name="category"
					style="width: 100px; height: 46px; flex: 0 0 auto;">
					<option value="noticeTitle" selected>제목</option>
					<option value="noticeContent">내용</option>
				</select>
				 <input type="text" style="width: 300px; flex: 0 0 auto;"
					class="form-control" name="search-text" placeholder="검색어를 입력해주세요."
					aria-label="Recipient's username" aria-describedby="button-addon2">
				<button class="btn btn-outline-secondary" type="submit"
					id="button-addon2" style="height: 46px;">검색</button>
			</form>

			<%-- <c:choose>
				<c:when test="${sessionScope.userName != null}"> --%>
					<button onclick="window.location.href = '/tastyForm/enrollForm.do'"
						style="position: absolute; right: 4%; background-color : #ebb842;">등록</button>
 				<%-- </c:when>
			</c:choose> --%>

		</div>
		<div class="notice-container">
			<table class="table table-hover" style="margin-top: 10px;">
				<thead>
					<tr>
						<th scope="col" class = "num">번호</th>
						<th scope="col" class = "title">제목</th>
						<!-- <th scope="col">작성자</th> -->
						<th scope="col" class = "date">작성일</th>
						<th scope="col" class = "views">조회수</th>
					</tr>
				</thead>
				<tbody>

					<c:choose>
						<c:when test="${empty list}">
							<tr>
								<td colspan="5">등록된 글이 없습니다.</td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:forEach var="item" items="${list}">
								<tr onclick="location.href='/notice/detail.do?boardno=${item.noticeNo}'">				
									<td scope="row" class = "num">${row}</td>
									<td class = "title">${item.noticeTitle}</td>
									<td class = "date">${item.noticeDate}</td>
									<td class = "views">${item.noticeView}</td>
								</tr>
								<c:set var="row" value="${row-1}" />
							</c:forEach>
						</c:otherwise>
					</c:choose>

				</tbody>
			</table>
		</div>

		<nav aria-label="Page navigation example"
			style="background-color: white;">

			<ul class="pagination" style="justify-content: center;">
				<!-- 왼쪽 버튼 -->
				<c:choose>
					<c:when test="${pi.cpage == 1}">
						<li class="page-item" style="margin-right: 0px"><a
							class="page-link" href="#" aria-label="Previous"> <span
								aria-hidden="true">&laquo;</span>
						</a></li>
					</c:when>

					<c:otherwise>
						<li class="page-item" style="margin-right: 0px"><a
							class="page-link" href="/notice/list.do?cpage=${pi.cpage-1}&category=noticeTitle&search-text="
							aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
						</a></li>
					</c:otherwise>
				</c:choose>

				<!-- 
					페이지 버튼 
					for(int i=0; i<10; i++) {  } 
				-->
				<c:forEach var="page" begin="${pi.startPage}" end="${pi.endPage}">
					<li class="page-item" style="margin-right: 0px"><a
						class="page-link" href="/notice/list.do?cpage=${page}&category=noticeTitle&search-text=">${page}</a></li>
				</c:forEach>

				<!-- 오른쪽 버튼 -->
				<c:choose>
					<c:when test="${pi.cpage == pi.maxPage}">
						<li class="page-item" style="margin-right: 0px"><a
							class="page-link" href="#" aria-label="Next"> <span
								aria-hidden="true">&raquo;</span>
						</a></li>
					</c:when>

					<c:otherwise>
						<li class="page-item" style="margin-right: 0px"><a
							class="page-link" href="/notice/list.do?cpage=${pi.cpage+1}&category=noticeTitle&search-text="
							aria-label="Next"> <span aria-hidden="true">&raquo;</span>
						</a></li>
					</c:otherwise>
				</c:choose>

			</ul>
		</nav>
		
		
	</main>


	


	<%@ include file="/views/common/footer.jsp"%>

	<script src="/assets/js/bootstrap.bundle.min.js"></script>
	<script src = "/assets/js/notice.js"></script>

</body>
</html>

