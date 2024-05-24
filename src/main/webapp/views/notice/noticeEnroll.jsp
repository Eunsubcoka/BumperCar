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


		<section id="post-form">
    <h2>새 글 작성</h2>
	    <form action="/freeBoard/enroll.do" method="POST" enctype="multipart/form-data">
	    	<%-- <input type="hidden" name="memberNo" value="${sessionScope.userNo}"> --%>
	    
	        <label for="title">제목:</label>
	        <input type="text" id="title" name="title" required>
	        
	        <label for="author">글쓴이:</label>
	        <input type="text" id="author" value="${sessionScope.userName}" disabled>
	        
	        <label for="content">내용:</label>
	        
	        <div id="smarteditor">
	        	<textarea id="editorTxt" name="content" rows="4" required></textarea>
	        </div>
	        
	        <!-- <input type="file" name="file"> -->
	        
	        <button type="submit" onclick="save()">작성</button>
	    </form>
	</section>

	</main>





	<%@ include file="/views/common/footer.jsp"%>

	<script src="/assets/js/main.js"></script>
	<script src="/assets/js/bootstrap.bundle.min.js"></script>

</body>
</html>

