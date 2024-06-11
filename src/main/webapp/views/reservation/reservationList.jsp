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
        <div class="reservation-list">
        <h1 class="reservation-header">예약 현황</h1>
            <c:forEach var="reservation" items="${reservationList}">
            <div class="reservation-items" onclick="openPopup(${reservation.resNo}, '${reservation.userName}', '${reservation.phone}', '${reservation.restaurantName}', '${reservation.resPhone}', '${reservation.date}', ${reservation.headCount}, '${reservation.paymentStatus}', ${reservation.restaurantNo})">
                <h5>예약 ${reservation.resNo}</h5>
                <div>가게 이름 : ${reservation.restaurantName}</div>
                <div>날짜 및 시간 : ${reservation.date}</div>        
                <div>인원 : ${reservation.headCount}</div>        
            </div>
            </c:forEach>
        </div>
    </main>

    <div id="popup" class="popup" style="display: none;">
        <div class="popup-content">
            <span class="close" onclick="closePopup()">&times;</span>
            <h5>예약 정보</h5>
            <div>예약 번호: <span id="popupResNo"></span></div>
            <div>예약자명 : <span id="popupUserName"></span></div>
            <div>예약자 전화번호: <span id="popupPhone"></span></div>
            <div>가게 이름: <span id="popupRestaurantName"></span></div>
            <div>가게 전화번호: <span id="popupRestaurantPhone"></span></div>
            <div>날짜 및 시간: <span id="popupDate"></span></div>
            <div>인원: <span id="popupHeadCount"></span></div>
            <div>결제 여부: <span id="popupPaymentStatus"></span></div>
            <input type="hidden" id="popupRestaurantNo">
            <button class="delete-btn" onclick="deleteReservation()">예약 삭제</button>
            <button class="detail-btn" onclick="gotoRestaurant()">레스토랑으로 이동</button>
        </div>
    </div>

    <%@ include file="/views/common/footer.jsp"%>

    <script src="/assets/js/bootstrap.bundle.min.js"></script>
    <script src="/assets/js/main.js"></script>
    <script src="/assets/js/reservationList.js"></script>

</body>
</html>
