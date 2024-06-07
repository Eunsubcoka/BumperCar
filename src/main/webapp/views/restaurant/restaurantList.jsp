<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
<link rel="stylesheet" href="/assets/css/category.css">
<script type="text/javascript" src="/assets/js/restaurant.js"></script>

<%@ include file="/views/common/head.jsp"%>

</head>
<body>
	<%@ include file="/views/common/header.jsp"%>
	<%@ include file="/views/common/nav.jsp"%>

<div class = "category_wrap">
가두기
<!-- for each -->
<c:forEach var="restaurantList" items="${restaurantList}">
<div class ="res_list_wrap">

<div class= "res_list_content">
<img src="/assets/image/${restaurantList.imgName}" class="photo_img" onclick ="location.href='/restaurantDetail.do?restaurantId=${restaurantList.restaurantNo}'">

</div>
<div class="res_list_title">
${restaurantList.restaurantName }
</div>

</div>
<!-- for each -->
						</c:forEach>
</div>



 

 
<%@ include file="/views/common/footer.jsp"%> 
</body>
</html>
