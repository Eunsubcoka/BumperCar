<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
<title>레스토랑 정보 수정 | TastyRoad</title>
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
        <form action="/restaurantEdit.do" method="POST" enctype="multipart/form-data">
        
        <h1>가게 수정</h1>
        <div class="container-review">
            <div class="review">
            <input type="hidden" name="userNo" value="${sessionScope.userNo}">
            <input type="hidden" value="${resDto.restaurantNo}" id= "restaurantNo">
                 <!-- 버튼 -->
                    <div id="image_container a">
                    <c:forEach var = "imgList" items="${imgList }">
						<img src="/assets/image/${imgList}" alt="">
						</c:forEach>
						</div>
               <div id="btn">
							<p>대표 사진을 두 장 업로드 해주세요.</p>
							<div id="image_container"></div>
							<label for="inputFile" class="btn-upload">이미지추가</label>
							<!-- accept 특정 파일 유형만 허용, multiple 속성을 추가하면 2개 이상의 파일을 추가 -->
							<input type="file" name="file" id="inputFile" onchange="getImageFiles(event);" multiple>
						</div>                
                <div class="input-container">
                    <label for="title">상호명:</label>
                    <input type="text" id="name" name="restaurantName" value="${resDto.restaurantName }" required>
                </div>
                <div class="input-container">
                    <label for="title">카테고리:</label>
         <select class="res_category" name="category" id = "category">
          <option value = "1">중식</option>
          <option value = "2">한식</option>
          <option value = "3">양식</option>
          <option value = "4">양식</option>
        </select>
                </div>
                <div class="input-container">
                    <label for="title">가게 전화번호:</label>
                    <input type="text" id="phone" name="phone" value="${resDto.restaurantPhone }" required>
                </div>
                <div class="input-container">
                    <label for="author">주소:</label>
                    <input type="text" id="sample5_address" name="addr" placeholder="주소" value="${resDto.location }">

					<input type="button" onclick="sample5_execDaumPostcode()" value="주소 검색"><br>

			<div id="map" style="width:300px;height:300px;margin-top:10px;display:none"></div>
                    
                </div>                

                <div class="content" style="overflow:hidden; height:auto;">
                	<label for="content">메뉴:</label>
				 <div class="input_wrap">
				 <c:set var = "menuCnt" value = "0"/>
				 <c:set var = "priceCnt" value = "0"/>
                <c:forEach var="menuList" items="${menuList}">
        <div class="input_list">
				 <c:set var = "menuCnt" value = "${menuCnt + 1 }"/>
				 <c:set var = "priceCnt" value = "${priceCnt + 1 }"/>
            <input type="text" name="menu${menuCnt }" placeholder="음식명" value="${menuList.menu }" ><br>
            <input type="text" name="price${priceCnt }" placeholder="가격" value= "${menuList.price }"><br>
            <a href="javascript:void(0);" class="remove_field">삭제</a>
            
        </div>
        </c:forEach>
    </div>
    <button class="add_field">추가하기</button>
				
				
                </div>
                <div class="input-container">
                    <label for="title">태그:</label>
				 <div class="input_wrap1">
				 <c:set var = "count" value = "0"/>
                <c:forEach var="tag" items="${tag}">
                
        <div class="input_list1">
        
				 <c:set var = "count" value = "${count + 1 }"/>
            <input type="text" name="tag${count }" placeholder="태그" value="${tag }"/><br>
			<a href="javascript:void(0);" class="remove_field2">삭제</a>
            
        </div>
    </c:forEach>
    </div>
    <button class="add_field1">추가하기</button>
                </div>
            </div>
        </div>
        				<button type="button" onclick="imageCheck();">작성</button>
        
        </form>
	</div>
</section> 


	<%@ include file="/views/common/footer.jsp"%>


	<script src="../assets/dist/js/bootstrap.bundle.min.js"></script>
	
	
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
    
    var fd = new FormData();
    let fdCount = 0;
 // 이미지 업로드 
    function getImageFiles(event) {
    	const maxImages = 2; // 최대 이미지 개수
    	// 현재 이미지 개수 확인
        const currentImageCount = document.querySelectorAll("div#image_container img.photo").length;

        // 이미지 개수 제한 확인
        if (event.target.files.length + currentImageCount > maxImages) {
            alert(`이미지는 ${maxImages}장까지 업로드할 수 있습니다.`);
            return;
        }

        for (let image of event.target.files) {
            // 현재 이미지 개수 확인
            if (currentImageCount.length >= maxImages) {
                alert(`이미지는 ${maxImages}장까지 업로드할 수 있습니다.`);
    			break;
            }
    		const imageName = `file`; // 고유한 이미지 이름 생성
    		fd.append(imageName, image); 
    		fdCount++;
    		
            let img = document.createElement("img");
            const reader = new FileReader(); 
            reader.onload = function(event) {
                img.setAttribute("src", event.target.result); //event.target.result는 FileReader 객체가 읽은 파일의 데이터 URL
    			img.classList.add("photo");
    			img.id = imageName + fdCount; // 이미지에 고유한 id 부여

                // 이미지를 감싸는 div 요소 생성
                const imageDiv = document.createElement("div");
                imageDiv.classList.add("review-photo");
                imageDiv.appendChild(img); // 이미지 추가

                // 이미지 삭제 버튼 생성
                const closeButton = document.createElement("img");
                closeButton.classList.add("close");
                closeButton.src = "/assets/image/close.png";
                closeButton.onclick = function() {
                    imageDiv.remove(); // 이미지 요소 삭제
                    fd.delete(imageName); // FormData에서 이미지 삭제
                };
                
                // close 버튼 스타일 추가
                closeButton.style.width = "10px";
                closeButton.style.height = "10px";
                closeButton.style.float = "right";
                closeButton.style.position = "absolute";
                imageDiv.appendChild(closeButton); // 삭제 버튼 추가

                // 이미지를 표시할 컨테이너에 추가
                document.querySelector("div#image_container").appendChild(imageDiv);
            }
            reader.readAsDataURL(image);
        }
    }
    /**
     * 
     */

    const wrapper = $('.input_wrap'); // 입력 필드를 포함하는 컨테이너 선택
    const addButton = $('.add_field'); // 추가 버튼 선택
    const maxFields = 10; // 최대 입력 필드 수 설정
    let fieldCount = 1; // 현재 입력 필드 수

    // '추가하기' 버튼 클릭 시 이벤트
    addButton.click(function(e) {
        e.preventDefault(); // 페이지 리로드 방지
        if (fieldCount < maxFields) { // 최대 필드 수 체크
            fieldCount++; // 필드 수 증가
            // 새 입력 필드 추가
            wrapper.append(`
                <div class="input_list">
                    <input type="text" name="menu`+fieldCount+`" placeholder="메뉴" />
                   <br>
    <input type="text" name="price`+fieldCount+`" placeholder="가격" />
                    <a href="javascript:void(0);" class="remove_field">삭제</a>
                </div>
            `);
        }
    });

    // '삭제' 링크 클릭 시 이벤트
    wrapper.on('click', '.remove_field', function(e) {
        e.preventDefault(); // 페이지 리로드 방지
        $(this).parent('.input_list').remove(); // 필드 제거
        fieldCount--; // 필드 수 감소
    });



    const wrapper1 = $('.input_wrap1'); // 입력 필드를 포함하는 컨테이너 선택
    const addButton1 = $('.add_field1'); // 추가 버튼 선택
    const maxFields1 = 10; // 최대 입력 필드 수 설정
    let fieldCount1 = 1; // 현재 입력 필드 수

    // '추가하기' 버튼 클릭 시 이벤트
    addButton1.click(function(e) {
        e.preventDefault(); // 페이지 리로드 방지
        if (fieldCount1 < maxFields1) { // 최대 필드 수 체크
            fieldCount1++; // 필드 수 증가
            // 새 입력 필드 추가
            wrapper1.append(`
                <div class="input_list1">
                    <input type="text" name="tag`+fieldCount1+`" placeholder="태그" />
                    <a href="javascript:void(0);" class="remove_field2">삭제</a>
                </div>
            `);
        }
    });

    // '삭제' 링크 클릭 시 이벤트
    wrapper1.on('click', '.remove_field2', function(e) {
        e.preventDefault(); // 페이지 리로드 방지
        $(this).parent('.input_list1').remove(); // 필드 제거
        fieldCount1--; // 필드 수 감소
    });

    
  //이미지 함수 + 데이터 전송 함수
    function imageCheck() { 
    	const currentImageCount = document.querySelectorAll("div#image_container img.photo").length;
	console.log("진입");
    	
    	    // 이미지 개수 확인
        if (currentImageCount == 0) {
            alert(`이미지는 1장이상 업로드해야 합니다.`);
            return;
        }
    	
    	const restaurantNo = document.getElementById("restaurantNo").value;
    	const restaurantName = document.getElementById("name").value;
    	const category = document.getElementById("category").value;
    	const phone = document.getElementById("phone").value;
    	const addr = document.getElementById("sample5_address").value;
    	console.log(addr);
    	fd.append("resNo", restaurantNo);
    	fd.append("restaurantName", restaurantName);
    	fd.append("category", category);
    	fd.append("phone", phone);
    	fd.append("addr", addr);
    	
    	for(var i = 1 ; i<=fieldCount1; i++){
    		const tag = document.getElementsByName('tag'+i)[0].value;
    		fd.append('tag'+i,tag);
    	}
    	for(var i = 1 ; i<=fieldCount; i++){
    		const menu = document.getElementsByName('menu'+i)[0].value;
    		const price = document.getElementsByName('price'+i)[0].value;
    		fd.append('menu'+i,menu);
    		fd.append('price'+i,price);
    	}

    	$.ajax({
    		url: '/restaurantEdit.do',
    		type: 'POST',
    		data: fd,
    		processData: false,
        	contentType: false,
    		success: function(response){
    			console.log(response);
    		// 성공 메시지 표시
        	alert("등록 성공!");
    		// 페이지 이동
        	location.href = '/index.jsp';
    		},
    		error: function(error) {
           		console.error("Error:", error);
    		// 실패 메시지 표시
        	alert("등록에 실패했습니다. 다시 시도해주세요.");
       		}
    	});
    }
</script>
	
</body>
</html>