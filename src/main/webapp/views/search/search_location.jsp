<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>주소를 위도와 경도로 변환</title>
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=597a12321ce91d26c9101324b5955ebd&libraries=services"></script>
</head>
<body>
    <h3>주소를 입력하세요:</h3>
    <input type="text" id="address" placeholder="주소 입력">
    <button onclick="searchAddress()">변환</button>

    <div id="result"></div>

    <script>
        // JavaScript 코드
        function searchAddress() {
            var address = document.getElementById('address').value;
            var geocoder = new kakao.maps.services.Geocoder();

            geocoder.addressSearch(address, function(result, status) {
                if (status === kakao.maps.services.Status.OK) {
                    var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                    var lat = result[0].y;
                    var lng = result[0].x;

                    document.getElementById('result').innerHTML = 
                        '<p>위도: ' + lat + '</p><p>경도: ' + lng + '</p>';

                    // 지도 표시를 원한다면 여기서 추가로 코드를 작성할 수 있습니다.
                    var mapContainer = document.createElement('div');
                    mapContainer.style.width = '100%';
                    mapContainer.style.height = '350px';
                    document.getElementById('result').appendChild(mapContainer);

                    var mapOption = {
                        center: coords,
                        level: 3
                    };

                    var map = new kakao.maps.Map(mapContainer, mapOption);

                    var marker = new kakao.maps.Marker({
                        position: coords
                    });
                    marker.setMap(map);
                } else {
                    document.getElementById('result').innerHTML = 
                        '<p>주소 변환에 실패했습니다.</p>';
                }
            });
        }
    </script>
</body>
</html>
