<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String accountId = request.getParameter("accountID");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>비밀번호 재설정 결과 | TastyRoad</title>
    <%@ include file="/views/common/head.jsp"%>
    <%@ include file="/views/common/nav.jsp"%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/idPassword.css">
</head>
<body>
    <%@ include file="/views/common/header.jsp"%>
    <div class="result-container">
        <h2>비밀번호 재설정 결과</h2>
        <c:choose>
            <c:when test="${isReset}">
                <p>비밀번호가 성공적으로 재설정되었습니다. 이제 로그인할 수 있습니다.</p>
            </c:when>
            <c:otherwise>
                <p>비밀번호 재설정에 실패했습니다. 다시 시도하세요.</p>
            </c:otherwise>
        </c:choose>
        <a href="${pageContext.request.contextPath}/views/member/login.jsp">로그인 페이지로 이동</a>
    </div>
    <%@ include file="/views/common/footer.jsp"%>
</body>
</html>
