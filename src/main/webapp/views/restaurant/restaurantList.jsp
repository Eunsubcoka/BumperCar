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

    <div class="main_con">
        <div class="list_type"> 
            <h2 class="title02">음식점 목록</h2>
			<form id="categoryForm" action="/category.do" method="get">
				<input type="hidden" name="category" value="${category}">
			    <select class="form-select" id="inputGroupSelect02" name="seleType"
			            style="width: 200px; height: 46px; flex: 0 0 auto;" onchange="this.form.submit()">
			                            <option value="restaurantName" <c:if test="${seleType == 'restaurantName'}">selected</c:if>>가게이름순</option>
                    <option value="ratings" <c:if test="${seleType == 'ratings'}">selected</c:if>>별점순</option>

			    </select>
			</form>

             <button type="button" onclick="location.href='/tastyForm/resAdd.do'">등록하기</button>
            
            <ul class="res_ul">
                <c:forEach var="restaurant" items="${restaurantList}">
                    <li class="res_li">
                        <div class="cont">
                            <span class="res_img">
                                <img src="/assets/image/${restaurant.imgName}" class="photo_img"
                                    onclick="location.href='/restaurantDetail.do?restaurantId=${restaurant.restaurantNo}'">
                            </span>
                            <div class="cnt">
                                <div class="score">평점: ${restaurant.ratings }</div>
                                <div class="box_tit">${restaurant.restaurantName}</div>
                                <div class="res_tag">${restaurant.tag}</div>
                            </div>
                            
                            <button type="button" onclick="location.href='/tastyForm/resEdit.do?resNo=${restaurant.restaurantNo}'">수정하기</button>
                           <%--  <c:choose>
                            <c:when test="${name eq '김철수'}"> 
                            ... </c:when>
                            
                            <c:otherwise> ..
                            . </c:otherwise>
                            </c:choose> --%>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    
<!-- <script>
function submitFormWithCategory() {
    var form = document.getElementById('categoryForm');
    /* var select = document.getElementById('inputGroupSelect02');
    var selectedValue = select.value;
    var actionUrl = form.action +'?category='+${category}+'&seleType=' + encodeURIComponent(selectedValue);
    
    form.action = actionUrl;
    console.log(form.action); */
    form.submit();
}
</script> -->
    <%@ include file="/views/common/footer.jsp"%>
</body>
</html>
