<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
<%@ include file="/views/common/head.jsp"%>
    <link rel="stylesheet" href="/assets/css/reservationList.css">
</head>
<body>

	<%@ include file="/views/common/header.jsp"%>
	<%@ include file="/views/common/nav.jsp"%>

	<main class="reservation-main">
		<div>
			로그인한 사용자의 reservation list 출력 <br> 
			reservationComplete가 해당 reservation 정보인거같은데, 클릭하면 해당 reservation 정보로 이동할수 있도록 하기 <br> 
			해당 reservation 삭제(취소)기능 추가 <br>
		</div>
		
		<div class="reservation-list">
		    <c:forEach var="reservation" items="${reservationList}">
		    <div class = "reservation-items">
		        <h5>예약 ${reservation.resNo}</h5>
		        <div>가게 이름 : ${reservation.restaurantName}</div>
		        <div>날짜 및 시간 : ${reservation.date}</div>		
		        <div>인원 : ${reservation.headCount}</div>		
		        <div>메뉴 : </div>
		        <div>총 가격 : </div>
		        <div>결제 여부 : ${reservation.paymentStatus }</div>
		        </div>
		    </c:forEach>
		</div>
	</main>

	<%@ include file="/views/common/footer.jsp"%>

	<script src="/assets/js/bootstrap.bundle.min.js"></script>
	<script src="/assets/js/main.js"></script>
</body>
</html>
