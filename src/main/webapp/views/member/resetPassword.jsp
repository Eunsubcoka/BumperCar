<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>비밀번호 재설정 | TastyRoad</title>
    <%@ include file="/views/common/head.jsp"%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/idPassword.css">
</head>
<body>
    <%@ include file="/views/common/header.jsp"%>
    <%@ include file="/views/common/nav.jsp"%>
    <div class="form-container">
        <h2>비밀번호 재설정</h2>
        <form action="${pageContext.request.contextPath}/resetPassword.do" method="post" onsubmit="return validateForm()">
            <input type="hidden" name="token" value="${param.token}">
            <label for="newPassword">새 비밀번호:</label>
            <input type="password" id="newPassword" name="newPassword" required>
            <div id="password-msg"></div>
            <label for="confirmPassword">새 비밀번호 확인:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required>
            <div id="confirm-password-msg"></div>
            <button type="submit">비밀번호 재설정</button>
        </form>
    </div>
    <%@ include file="/views/common/footer.jsp"%>
</body>
</html>
<script>
    function validatePassword() {
        const password = document.getElementById('newPassword').value.trim();
        const passwordMsg = document.getElementById('password-msg');
        const passwordPattern = /^(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$/;
        if (!passwordPattern.test(password)) {
            passwordMsg.innerText = "비밀번호는 최소 8자 이상이어야 하며, 대문자와 특수문자(!@#$%^&*)를 포함해야 합니다.";
            passwordMsg.style.color = "red";
            return false;
        } else {
            passwordMsg.innerText = "";
            return true;
        }
    }

    function validateConfirmPassword() {
        const password = document.getElementById('newPassword').value.trim();
        const confirmPassword = document.getElementById('confirmPassword').value.trim();
        const confirmPasswordMsg = document.getElementById('confirm-password-msg');
        if (password !== confirmPassword) {
            confirmPasswordMsg.innerText = "비밀번호가 일치하지 않습니다.";
            confirmPasswordMsg.style.color = "red";
            return false;
        } else {
            confirmPasswordMsg.innerText = "";
            return true;
        }
    }

    function validateForm() {
        return validatePassword() && validateConfirmPassword();
    }

    document.getElementById('newPassword').addEventListener('input', validatePassword);
    document.getElementById('confirmPassword').addEventListener('input', validateConfirmPassword);
</script>
