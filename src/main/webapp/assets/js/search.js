var mapContainer = document.getElementById('map');
var map;
var userInfoWindow; // 내 위치 인포윈도우 변수
var userMarker; // 내 위치 마커 변수
var markers = []; // 모든 마커를 저장할 배열
var overlays = []; // 모든 오버레이를 저장할 배열
var userLocation; // 사용자의 위치

// 사용자 위치를 가져옴
function initializeMap() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var lat = position.coords.latitude; //위도
            var lon = position.coords.longitude; //경도
            userLocation = new kakao.maps.LatLng(lat, lon); //-> 나의 (위도,경도)

            map = new kakao.maps.Map(mapContainer, {
                center: userLocation,
                level: 5 
            });

            // 사용자 위치에 마커 추가
            userMarker = new kakao.maps.Marker({
                map: map,
                position: userLocation
            });

            // 사용자 위치에 인포윈도우 추가
            var userInfoContent = '<div class = "user-info-content">내 위치</div>'; 

            userInfoWindow = new kakao.maps.InfoWindow({
                position: userLocation,
                content: userInfoContent,
                removable: false
            });

            userInfoWindow.open(map, userMarker);

            // 사용자 위치 마커 클릭 이벤트 추가
            kakao.maps.event.addListener(userMarker, 'click', function() {
                if (userInfoWindow.getMap()) {
                    userInfoWindow.close();
                } else {
                    userInfoWindow.open(map, userMarker);
                }
            });

            // 지도에 다른 마커들 추가
            addMarkers();
        }, function(error) {
            console.error("Error occurred. Error code: " + error.code);
            initializeDefaultMap();
        });
    } else {
        console.error("Geolocation is not supported by this browser.");
        initializeDefaultMap();
    }
}

// 내 위치가 입력되지 않았을 때 기본 중심 좌표로 지도 초기화
function initializeDefaultMap() {
    userLocation = new kakao.maps.LatLng(37.3987966098124, 126.920790701382);
    map = new kakao.maps.Map(mapContainer, {
        center: userLocation,
        level: 5 // 확대 레벨
    });

    // 지도에 다른 마커들 추가
    addMarkers();
}

// 지도에 마커 추가
function addMarkers() {
    var geocoder = new kakao.maps.services.Geocoder();

    // search-main 요소에서 locations 데이터를 가져옴
    var searchMain = document.getElementById('search-main');
    var locations = JSON.parse(searchMain.dataset.locations);

    // 검색된 list의 각각의 위치를 표시 
    locations.forEach(function(location) {
        geocoder.addressSearch(location.location, function(result, status) { // addressSearch -> 주소로 입력하면, 해당 주소를 위도 경도로 변환해줌
            if (status === kakao.maps.services.Status.OK) {
                var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                var marker = new kakao.maps.Marker({
                    map: map,
                    position: coords
                });
                markers.push(marker);

                // 태그를 한 줄로 표시하고 20글자 이상이면 ... 처리
                var tags = location.tags.join(', ');
                if (tags.length > 20) {
                    tags = tags.substring(0, 20) + '...';
                }

                // 이미지가 있는지 확인하고, 없으면 기본 이미지 사용
                var imgSrc = location.imgName ? "/assets/image/" + location.imgName : "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/thumnail.png";

                var content = document.createElement('div');
                content.className = 'wrap';
                content.innerHTML = 
                    '<div class="info">' +
                    '    <div class="title">' +
                    location.name +
                    '        <div class="close" title="닫기"></div>' +
                    '    </div>' +
                    '    <div class="body">' +
                    '        <div class="img">' +
                    '            <img src="' + imgSrc + '" width="73" height="70">' +
                    '        </div>' +
                    '        <div class="desc">' +
                    '            <div class="ellipsis">' + location.location + '</div>' +
                    '            <div class="tags ellipsis">' + tags + '</div>' +
                    '            <div><a href="/restaurantDetail.do?restaurantId=' + location.restaurantNo + '" target="_blank" class="link">상세보기</a></div>' +
                    '        </div>' +
                    '    </div>' +
                    '</div>';

                var overlay = new kakao.maps.CustomOverlay({
                    content: content,
                    map: null,
                    position: marker.getPosition()
                });
                overlays.push(overlay);

                kakao.maps.event.addListener(marker, 'click', function() {
                    if (overlay.getMap()) {
                        overlay.setMap(null);
                    } else {
                        overlay.setMap(map);
                    }
                });

                var closeBtn = content.querySelector('.close');
                closeBtn.addEventListener('click', function() {
                    overlay.setMap(null);
                });
            }
        });
    });
}

// 지도 초기화
initializeMap();

function loadMoreRestaurants() {
    const loadMoreButton = document.querySelector('#load-more-restaurants');
    const currentPage = parseInt(loadMoreButton.dataset.page || 1);
    const nextPage = currentPage + 1;
    const searchText = encodeURIComponent(loadMoreButton.dataset.searchText || '');
    const tag = encodeURIComponent(loadMoreButton.dataset.tag || '');
    const category = loadMoreButton.dataset.category || 'restaurant';

    fetch(`/search.do?cpage=${nextPage}&search-text=${searchText}&tag=${tag}`)
        .then(response => response.text())
        .then(html => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');
            const newRestaurants = doc.querySelectorAll('#restaurant-list .restaurant-item');
            newRestaurants.forEach(restaurant => {
                document.querySelector('#restaurant-list').appendChild(restaurant);
            });
            if (newRestaurants.length < 5) {
                loadMoreButton.style.display = 'none';
            } else {
                loadMoreButton.dataset.page = nextPage;
            }
        })
        .catch(error => {
            console.error('Error loading more restaurants:', error);
            loadMoreButton.style.display = 'none';
        });
}

function loadMoreNotices() {
    const loadMoreButton = document.querySelector('#load-more-notices');
    const currentPage = parseInt(loadMoreButton.dataset.page || 1);
    const nextPage = currentPage + 1;
    const searchText = encodeURIComponent(loadMoreButton.dataset.searchText || '');
    const category = loadMoreButton.dataset.category || 'notice';

    fetch(`/search.do?cpage=${nextPage}&search-text=${searchText}&category=${category}`)
        .then(response => response.text())
        .then(html => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');
            const newNotices = doc.querySelectorAll('#notice-list .notice-item');
            newNotices.forEach(notice => {
                document.querySelector('#notice-list').appendChild(notice);
            });
            if (newNotices.length < 5) {
                loadMoreButton.style.display = 'none';
            } else {
                loadMoreButton.dataset.page = nextPage;
            }
        })
        .catch(error => {
            console.error('Error loading more notices:', error);
            loadMoreButton.style.display = 'none';
        });
}

function navigateToReviews(restaurantNo) {
    window.location.href = "/review/review.do?restaurantNo=" + restaurantNo;
}

function toggleReview(btn) {
    const reviewBox = btn.nextElementSibling;
    if (reviewBox.style.display === "none") {
        reviewBox.style.display = "block";
        btn.textContent = "리뷰 닫기";
    } else {
        reviewBox.style.display = "none";
        btn.textContent = "리뷰 열기";
    }
}

function sortResults() {
    const sortOrder = document.getElementById('sortOrder').value;
    const searchText = encodeURIComponent(document.querySelector('#load-more-restaurants').dataset.searchText || '');
    const tag = encodeURIComponent(document.querySelector('#load-more-restaurants').dataset.tag || '');
    const cpage = 1;
    const lat = userLocation.getLat();
    const lon = userLocation.getLng();

    let url = `/search.do?cpage=${cpage}&search-text=${searchText}&tag=${tag}&sortOrder=${sortOrder}`;
    if (sortOrder === 'distance') {
        url += `&lat=${lat}&lon=${lon}`;
    }

    fetch(url)
        .then(response => response.text())
        .then(html => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');
            const newRestaurants = doc.querySelectorAll('#restaurant-list .restaurant-item');
            const restaurantList = document.querySelector('#restaurant-list');
            restaurantList.innerHTML = '';
            newRestaurants.forEach(restaurant => {
                restaurantList.appendChild(restaurant);
            });
        })
        .catch(error => {
            console.error('Error sorting restaurants:', error);
        });
}
