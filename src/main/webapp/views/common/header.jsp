<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header> 
  <div class="navbar navbar-dark bg-white shadow-sm" id="main-top" >
    <div class="container" >
      <a href="/" class="navbar-brand d-flex align-items-center">
        <img src="/assets/image/logo.png" width="220" height="100" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" aria-hidden="true" class="me-2" viewBox="0 0 24 24"></img>
      </a>
      <form action="/search.do" method="get">
        <input type="text" style="width: 500px; flex: 0 0 auto;" class="form-control" name="search-text" placeholder="검색어를 입력 후 Enter키를 눌러주세요.">
      </form>
      <div class="login-signup">
        <c:choose>
          <c:when test="${sessionScope.userName == null}">
            <a href="/views/member/login.jsp" class="no-underline">로그인</a> 
            <a href="/views/member/register.jsp" class="no-underline">회원가입</a>
          </c:when>
          <c:otherwise>
            <a href="/member/logout.do" class="no-underline">로그아웃</a>
            <a href="/tastyForm/profile.do" class="no-underline">내 정보</a>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
  </div>
</header>

<style>
  .no-underline {
    text-decoration: none;
  }
</style>
