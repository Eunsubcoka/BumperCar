<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>아이디 찾기 결과 | TastyRoad</title>
        <%@ include file="/views/common/head.jsp"%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/idPassword.css">
</head>
<body>
<%@ include file="/views/common/header.jsp"%>
<%@ include file="/views/common/nav.jsp"%>
    <div class="form-container">
        <h2>아이디 찾기 결과</h2>
        <c:choose>
            <c:when test="${userId != null}">
                <p>찾으시는 아이디는: ${userId} 입니다.</p>
            </c:when>
            <c:otherwise>
                <p>입력하신 이메일로 등록된 아이디가 없습니다.</p>
            </c:otherwise>
        </c:choose>
        <a href="${pageContext.request.contextPath}/findId.do">돌아가기</a>
    </div>
        	<%@ include file="/views/common/footer.jsp"%>
</body>
</html>
