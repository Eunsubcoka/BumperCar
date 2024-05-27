<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%>
<header> 
  <div class="navbar navbar-dark bg-white shadow-sm">
    <div class="container">
      <a href="#" class="navbar-brand d-flex align-items-center">
        <img src="/assets/image/logo.png" width="220" height="100" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" aria-hidden="true" class="me-2" viewBox="0 0 24 24"></img>
      </a>
      <form action="/freeBoard/list.do" method = "get" >
		<input type = "hidden" name = "cpage" value = "1">
			 <input type="text" style="width: 500px; flex: 0 0 auto;"
				class="form-control" name="search-text" placeholder="검색어를 입력해주세요."
				aria-label="Recipient's username" aria-describedby="button-addon2">
		</form>
	<div class="login-signup">
     <!--  <a href="/views/member/login.jsp">로그인</a> --> 
    <!--  <a href="/views/member/register.jsp">회원가입</a> -->  
    	<c:choose>
		<c:when test ="${sessionScope.userName == null}">
      <a href="/views/member/login.jsp">로그인</a> 
      <a href="/views/member/register.jsp">회원가입</a>
		
		</c:when>
		<c:otherwise>
      <a href="/member/logout.do">로그아웃</a> 
		</c:otherwise>
		</c:choose>
    
    
    </div>
    
    </div>
  </div>



</header>