<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
<title>레스토랑 추가 | TastyRoad</title>
<%@ include file="/views/common/head.jsp"%>

	<link rel="stylesheet" href="/assets/css/reviewEnroll.css">
	<link rel="stylesheet" href="/assets/css/restaurantAdd.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css">


</head>
<body>

	<%@ include file="/views/common/header.jsp"%>
	<%@ include file="/views/common/nav.jsp"%>

<!-- review enroll section -->
<section>
    <div class="container-review-box">
        <form action="/restaurantAdd.do" method="POST" enctype="multipart/form-data">
        
        <h1>가게 등록</h1>
        <div class="container-review">
            <div class="review">
            <input type="hidden" name="userNo" value="${sessionScope.userNo}">
                 <!-- 버튼 -->
                <div id="btn">
                    <p>대표 사진 하나를 넣어주세요.</p>
                    <div id="image_container"></div>
                    <input type="hidden" name="removeImageName-${status.count}" id="removeImageName-${status.count}" value="${fileList.fileName}"/>
					        <input type="hidden" name="removeImageStatus-${status.count}" id="removeImageStatus-${status.count}" value="false"/>
                    <label for="file" class="btn-upload">이미지추가</label>		<!-- accept 특정 파일 유형만 허용, multiple 속성을 추가하면 2개 이상의 파일을 추가 -->
                    <input type="file" name="file" id="file" onchange="getImageFiles(event, '${status.count}');" multiple>
                    <button type="submit">작성</button>
                </div>                
                <div class="input-container">
                    <label for="title">상호명:</label>
                    <input type="text" id="name" name="restaurantName" required>
                </div>
                <div class="input-container">
                    <label for="title">카테고리:</label>
                      <select class="res_category" name="category">
          <option value = "1">한식</option>
          <option value = "2">중식</option>
          <option value = "3">양식</option>
          <option value = "4">일식</option>
        </select>
                </div>
                <div class="input-container">
                    <label for="title">가게 전화번호:</label>
                    <input type="text" id="phone" name="phone" required>
                </div>
                <div class="input-container">
                    <label for="title">태그:</label>
				 <div class="input_wrap1">
        <div class="input_list1">
            <input type="text" name="tag1" placeholder="태그" /><br>
            
        </div>
    </div>
    <button class="add_field1">추가하기</button>
                </div>
                <div class="input-container">
                    <label for="author">주소:</label>
                    <input type="text" id="sample5_address" name="addr" placeholder="주소">

					<input type="button" onclick="sample5_execDaumPostcode()" value="주소 검색"><br>

			<div id="map" style="width:300px;height:300px;margin-top:10px;display:none"></div>
                    
                </div>                

                <div class="content" style="overflow:hidden; height:auto;">
                	<label for="content">메뉴:</label>
				 <div class="input_wrap">
        <div class="input_list">
            <input type="text" name="menu1" placeholder="음식명" /><br>
            <input type="text" name="price1" placeholder="가격" />
            
        </div>
    </div>
    <button class="add_field">추가하기</button>
				
				
                </div>
            </div>
        </div>
        </form>
	</div>
</section> 


	<%@ include file="/views/common/footer.jsp"%>


	<script src="../assets/dist/js/bootstrap.bundle.min.js"></script>
	<script src="/assets/js/restaurantAdd.js"></script>
	<script type="text/javascript" src="/assets/js/reviewEnroll.js"></script> 
	
	
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=597a12321ce91d26c9101324b5955ebd&libraries=services"></script>
<script>
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
            level: 5 // 지도의 확대 레벨
        };

    //지도를 미리 생성
    var map = new daum.maps.Map(mapContainer, mapOption);
    //주소-좌표 변환 객체를 생성
    var geocoder = new daum.maps.services.Geocoder();
    //마커를 미리 생성
    var marker = new daum.maps.Marker({
        position: new daum.maps.LatLng(37.537187, 127.005476),
        map: map
    });


    function sample5_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                var addr = data.address; // 최종 주소 변수

                // 주소 정보를 해당 필드에 넣는다.
                document.getElementById("sample5_address").value = addr;
                // 주소로 상세 정보를 검색
                geocoder.addressSearch(data.address, function(results, status) {
                    // 정상적으로 검색이 완료됐으면
                    if (status === daum.maps.services.Status.OK) {

                        var result = results[0]; //첫번째 결과의 값을 활용

                        // 해당 주소에 대한 좌표를 받아서
                        var coords = new daum.maps.LatLng(result.y, result.x);
                        // 지도를 보여준다.
                        mapContainer.style.display = "block";
                        map.relayout();
                        // 지도 중심을 변경한다.
                        map.setCenter(coords);
                        // 마커를 결과값으로 받은 위치로 옮긴다.
                        marker.setPosition(coords)
                    }
                });
            }
        }).open();
    }
</script>
	
</body>
</html>

	

