<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<link href="/assets/css/login.css" rel="stylesheet">
<html>
<head>
<%@ include file="/views/common/head.jsp"%>
<link rel="stylesheet" href="styles.css">
</head>
<body>
	<%@ include file="/views/common/header.jsp"%>
	<%@ include file="/views/common/nav.jsp"%>

	<section>
		<div class="login-form">
			<h2>로그인</h2>
			<form action="/tastyForm/login.do" method="post">
				<label for="userid">아이디:</label> <input type="text" id="userid"
					name="userid" required> <label for="password">비밀번호:</label>
				<input type="password" id="password" name="password" required>

				<button type="submit">로그인</button>
			</form>
			<form action="/register.do" method="get">
				<button type="submit">회원가입</button>
			</form>
		     <div class="find-links">
                <a href="/find-id.do">아이디 찾기</a>
                <a href="/find-password.do">비밀번호 찾기</a>
            </div>
		</div>
	</section>

	<%@ include file="/views/common/footer.jsp"%>
</body>
</html>