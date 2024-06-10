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
<!-- 카카오  -->
<div class="map_wrap">
    <div id="map" style="width:100%;height:100%;position:relative;overflow:hidden;"></div>
</div>
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
<c:forEach var="reviewList" items="${reviewList}">

</c:forEach>

</div>

</div>
<!-- for each -->
						</c:forEach>
</div>






<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=597a12321ce91d26c9101324b5955ebd"></script>
<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div  
    mapOption = { 
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
 
// 마커를 표시할 위치와 title 객체 배열입니다 
var positions = [
    {
        title: '서울역', 
        latlng: new kakao.maps.LatLng(37.5546788, 126.9706069)
    },
    {
        title: '서울특별시청', 
        latlng: new kakao.maps.LatLng(37.5668260, 126.9786567)
    },
    {
        title: '을지로입구역',
        latlng: new kakao.maps.LatLng(37.5660373, 126.9821930)
    },
    {
        title: '덕수궁',
        latlng: new kakao.maps.LatLng(37.5655638, 126.97489)
    }
];

// 마커 이미지의 이미지 주소입니다
var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
    
for (var i = 0; i < positions.length; i ++) {
    
    // 마커 이미지의 이미지 크기 입니다
    var imageSize = new kakao.maps.Size(24, 35); 
    
    // 마커 이미지를 생성합니다    
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize); 
    
    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: positions[i].latlng, // 마커를 표시할 위치
        title : positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        image : markerImage // 마커 이미지 
    });
}
</script>
 

 
<%@ include file="/views/common/footer.jsp"%> 
</body>
</html>
