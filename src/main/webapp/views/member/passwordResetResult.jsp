<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>비밀번호 재설정 결과</title>
    <%@ include file="/views/common/head.jsp"%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/idPassword.css">
</head>
<body>
    <%@ include file="/views/common/header.jsp"%>
    <div class="form-container">
        <h2>비밀번호 재설정 결과</h2>
        <c:choose>
            <c:when test="${isReset}">
                <p>비밀번호가 성공적으로 재설정되었습니다.</p>
            </c:when>
            <c:otherwise>
                <p>비밀번호 재설정에 실패했습니다. 다시 시도해 주세요.</p>
            </c:otherwise>
        </c:choose>
        <a href="${pageContext.request.contextPath}/tastyForm/login.do">로그인 페이지로 이동</a>
    </div>
    <%@ include file="/views/common/footer.jsp"%>
</body>
</html>
 