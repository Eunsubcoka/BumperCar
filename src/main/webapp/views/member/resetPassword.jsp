<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>비밀번호 재설정</title>
    <%@ include file="/views/common/head.jsp"%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/idPassword.css">
</head>
<body>
    <%@ include file="/views/common/header.jsp"%>
    <div class="form-container">
        <h2>비밀번호 재설정</h2>
        <form action="${pageContext.request.contextPath}/resetPassword.do" method="post">
            <input type="hidden" name="token" value="${token}">
            <label for="newPassword">새 비밀번호:</label>
            <input type="password" id="newPassword" name="newPassword" required>
            <label for="confirmPassword">새 비밀번호 확인:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required>
            <button type="submit">비밀번호 재설정</button>
        </form>
    </div>
    <%@ include file="/views/common/footer.jsp"%>
</body>
</html>
