<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>아이디 찾기 | TastyRoad</title>
    <%@ include file="/views/common/head.jsp"%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/idPassword.css">
</head>
<body>
		<%@ include file="/views/common/header.jsp"%>
		<%@ include file="/views/common/nav.jsp"%>
    <div class="form-container">
        <h2>아이디 찾기</h2>
        <form action="${pageContext.request.contextPath}/findId.do" method="post">
            <label for="userEmail">이메일:</label>
            <input type="email" id="userEmail" name="userEmail" required>
            <button type="submit">아이디 찾기</button>
        </form>
    </div>
    
    	<%@ include file="/views/common/footer.jsp"%>
</body>
</html>
