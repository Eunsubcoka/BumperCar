<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/views/common/head.jsp"%>
    <link rel="stylesheet" href="styles.css">
    <link href="/assets/css/login.css" rel="stylesheet">
    <script src="https://accounts.google.com/gsi/client" async defer></script> <!-- Google Sign-In script -->
</head>
<body>
    <%@ include file="/views/common/header.jsp"%>
    <%@ include file="/views/common/nav.jsp"%>

    <section>
        <div class="login-form">
            <h2>로그인</h2>
            <form action="/tastyForm/login.do" method="post">
                <label for="userid">아이디:</label>
                <input type="text" id="userid" name="userid" required>
                <label for="password">비밀번호:</label>
                <input type="password" id="password" name="password" required>

                <c:if test="${not empty errorMsg}">
                    <p style="color: red;">${errorMsg}</p>
                </c:if>

                <button type="submit">로그인</button>
            </form>
            <form action="/register.do" method="get">
                <button type="submit">회원가입</button>
            </form>
            <div class="find-links">
                <a href="/findId.do" class="no-underline">아이디 찾기</a>
                <a href="/findPassword.do" class="no-underline">비밀번호 찾기</a>
            </div>
            <div id="g_id_onload"
                 data-client_id="553555922743-ld3bj5fceveid5bdlo69ss1uhmqetevt.apps.googleusercontent.com"
                 data-callback="handleCredentialResponse">
            </div>
            <div class="g_id_signin" data-type="standard"></div>
        </div>
    </section>

    <script>
        function handleCredentialResponse(response) {
            const credential = response.credential;
            fetch('/googleLogin.do', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: 'credential=' + encodeURIComponent(credential)
            }).then(response => response.text())
              .then(data => {
                  window.location.href = '/'; // 로그인 후 이동할 페이지
              }).catch(error => {
                  console.error('Error:', error);
              });
        }
    </script>

    <%@ include file="/views/common/footer.jsp"%>
</body>
</html>
