var mapContainer = document.getElementById('map');
var map;
var userInfoWindow; // 내 위치 인포윈도우 변수
var userMarker; // 내 위치 마커 변수
var markers = []; // 모든 마커를 저장할 배열
var overlays = []; // 모든 오버레이를 저장할 배열
var userLocation; // 사용자의 위치
var initialLoad = true; // 초기 로드 여부

// Haversine 공식을 사용하여 거리 계산 함수 추가
function calculateDistance(lat1, lon1, lat2, lon2) {
    const R = 6371; // 지구의 반지름 (단위: km)
    const dLat = (lat2 - lat1) * Math.PI / 180;
    const dLon = (lon2 - lon1) * Math.PI / 180;
    const a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
              Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
              Math.sin(dLon / 2) * Math.sin(dLon / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c; // 두 좌표 간의 거리 (단위: km)
}

// 사용자 위치를 가져옴
function initializeMap() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var lat = position.coords.latitude; // 위도
            var lon = position.coords.longitude; // 경도
            userLocation = new kakao.maps.LatLng(lat, lon); // -> 나의 (위도, 경도)

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
            var userInfoContent = '<div class="user-info-content">내 위치</div>';

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

            // 지도에 다른 마커들 추가, 초기 로딩 시 최신순으로 표시
            addMarkers('latest');
        }, function(error) {
            console.error('Error occurred. Error code: ' + error.code);
            initializeDefaultMap();
        });
    } else {
        console.error('Geolocation is not supported by this browser.');
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

    // 지도에 다른 마커들 추가, 초기 로딩 시 최신순으로 표시
    addMarkers('latest');
}

// 지도에 마커 추가
function addMarkers(sortOrder = 'latest', page = 1) {
    var geocoder = new kakao.maps.services.Geocoder();
    var searchMain = document.getElementById('search-main');
    var locations = JSON.parse(searchMain.dataset.locations);

    var distances = [];
    var itemsPerPage = 5;
    var startIndex = (page - 1) * itemsPerPage;
    var endIndex = startIndex + itemsPerPage;

    locations.forEach(function(location) {
        geocoder.addressSearch(location.location, function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                var distance = calculateDistance(userLocation.getLat(), userLocation.getLng(), coords.getLat(), coords.getLng());
                distances.push({
                    location: location,
                    coords: coords,
                    distance: distance
                });

                if (distances.length === locations.length) {
                    if (sortOrder === 'distance') {
                        distances.sort(function(a, b) {
                            return a.distance - b.distance;
                        });
                    } else {
                        distances.sort(function(a, b) {
                            return b.location.restaurantNo - a.location.restaurantNo;
                        });
                    }

                    distances.forEach(function(item) {
                        var marker = new kakao.maps.Marker({
                            map: map,
                            position: item.coords
                        });
                        markers.push(marker);

                        var tags = item.location.tags.join(', ');
                        if (tags.length > 20) {
                            tags = tags.substring(0, 20) + '...';
                        }

                        var imgSrc = item.location.imgName ? '/assets/image/' + item.location.imgName : 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/thumnail.png';

                        var content = document.createElement('div');
                        content.className = 'wrap';
                        content.innerHTML = 
                            '<div class="info">' +
                            '    <div class="title">' +
                            item.location.name +
                            '        <div class="close" title="닫기"></div>' +
                            '    </div>' +
                            '    <div class="body">' +
                            '        <div class="img">' +
                            '            <img src="' + imgSrc + '" width="73" height="70">' +
                            '        </div>' +
                            '        <div class="desc">' +
                            '            <div class="ellipsis">' + item.location.location + '</div>' +
                            '            <div class="tags ellipsis">' + tags + '</div>' +
                            '            <div><a href="/restaurantDetail.do?restaurantId=' + item.location.restaurantNo + '" target="_blank" class="link">상세보기</a></div>' +
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
                    });

                    updateRestaurantList(distances.slice(0, endIndex), sortOrder); // 첫 페이지 리스트로 업데이트

                    if (distances.length > endIndex) {
                        showLoadMoreButton(); // 더보기 버튼 표시
                    } else {
                        hideLoadMoreButton(); // 더보기 버튼 숨기기
                    }
                }
            }
        });
    });
}

function showLoadMoreButton() {
    const loadMoreButton = document.getElementById('load-more-restaurants');
    loadMoreButton.style.display = 'block';
}

function hideLoadMoreButton() {
    const loadMoreButton = document.getElementById('load-more-restaurants');
    loadMoreButton.style.display = 'none';
}

// 레스토랑 리스트 업데이트 함수
function updateRestaurantList(distances, sortOrder) {
    const restaurantList = document.querySelector('#restaurant-list');
    restaurantList.innerHTML = ''; // 기존 리스트 초기화

    distances.forEach(function(item) {
        const restaurantItem = document.createElement('li');
        restaurantItem.className = 'restaurant-item';
        restaurantItem.innerHTML = `
            <div class="restaurant-image">
                ${item.location.imgName ? `<img src="/assets/image/${item.location.imgName}" alt="${item.location.name}">` : '<p>사진 추가 예정</p>'}
            </div>
            <div class="restaurant-info">
                <div style="display:inline-block;">
                    <a href="/restaurantDetail.do?restaurantId=${item.location.restaurantNo}">${item.location.name}</a>
                    <div style="display:inline-block;" class="font-down">
                    <i class="fas fa-star"></i>
                    ${item.location.ratings.toFixed(1)}
                    </div>
                </div>
                <div class="font-down">태그: ${item.location.tags.join(', ')}</div>
                <div class="font-down">위치: ${item.location.location}</div>
                <div class="font-down">거리: ${item.distance.toFixed(2)} km</div>
                <button class="toggle-review-btn" onclick="toggleReview(this)">리뷰 열기</button>
                <div class="review-box font-down-2" style="display: none;" onclick="navigateToReviews(${item.location.restaurantNo})">
                    ${item.location.reviews.length > 0 ? item.location.reviews.map(review => `
                        <div class="font-down">
                            ${review.reviewTitle}
                            <i class="fas fa-star"></i>
                            ${review.ratings}
                        </div>
                        <p class="review-content">
                            ${review.reviewContent.length > 30 ? review.reviewContent.substring(0, 30) + '...' : review.reviewContent}
                        </p>
                    `).join('') : '<p style="font-size: 20px;">리뷰가 없습니다</p>'}
                </div>
            </div>
        `;

        restaurantList.appendChild(restaurantItem);
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
    const sortOrder = document.getElementById('sortOrder').value;

    fetch(`/search.do?cpage=${nextPage}&search-text=${searchText}&tag=${tag}&sortOrder=${sortOrder}`)
        .then(response => response.text())
        .then(html => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');
            const newRestaurants = doc.querySelectorAll('#restaurant-list .restaurant-item');
            const restaurantList = document.querySelector('#restaurant-list');
            newRestaurants.forEach(restaurant => {
                const locationElement = restaurant.querySelector('.restaurant-info > .font-down:nth-child(4)');
                if (locationElement) {
                    const locationText = locationElement.textContent.split(': ')[1];
                    const coords = new kakao.maps.LatLng(locationText.split(', ')[0], locationText.split(', ')[1]);
                    const distance = calculateDistance(userLocation.getLat(), userLocation.getLng(), coords.getLat(), coords.getLng());
                    const distanceElement = restaurant.querySelector('.font-down:nth-child(5)');
                    if (distanceElement) {
                        distanceElement.textContent = `거리: ${distance.toFixed(2)} km`;
                    }
                }
                restaurantList.appendChild(restaurant);
            });

            if (newRestaurants.length < 5) {
                hideLoadMoreButton(); // 더보기 버튼 숨기기
            } else {
                loadMoreButton.dataset.page = nextPage;
            }
        })
        .catch(error => {
            console.error('Error loading more restaurants:', error);
            hideLoadMoreButton(); // 에러 발생 시 더보기 버튼 숨기기
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

// sortResults 함수 수정
function sortResults(sortOrder) {
    document.getElementById('sortOrder').value = sortOrder;
    const searchText = encodeURIComponent(document.querySelector('#load-more-restaurants').dataset.searchText || '');
    const tag = encodeURIComponent(document.querySelector('#load-more-restaurants').dataset.tag || '');
    const cpage = 1;

    let url = `/search.do?cpage=${cpage}&search-text=${searchText}&tag=${tag}&sortOrder=${sortOrder}&userLat=${userLocation.getLat()}&userLon=${userLocation.getLng()}`;
    fetch(url)
        .then(response => response.text())
        .then(html => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');
            const newRestaurants = doc.querySelectorAll('#restaurant-list .restaurant-item');
            const restaurantList = document.querySelector('#restaurant-list');
            restaurantList.innerHTML = '';
            newRestaurants.forEach(restaurant => {
                const locationElement = restaurant.querySelector('.restaurant-info > .font-down:nth-child(4)');
                if (locationElement) {
                    const locationText = locationElement.textContent.split(': ')[1];
                    const coords = new kakao.maps.LatLng(locationText.split(', ')[0], locationText.split(', ')[1]);
                    const distance = calculateDistance(userLocation.getLat(), userLocation.getLng(), coords.getLat(), coords.getLng());
                    const distanceElement = restaurant.querySelector('.font-down:nth-child(5)');
                    if (distanceElement) {
                        distanceElement.textContent = `거리: ${distance.toFixed(2)} km`;
                    }
                }
                restaurantList.appendChild(restaurant);
            });

            if (sortOrder === 'distance') {
                const distances = Array.from(newRestaurants).map(restaurant => {
                    const locationElement = restaurant.querySelector('.restaurant-info > .font-down:nth-child(4)');
                    if (locationElement) {
                        const locationText = locationElement.textContent.split(': ')[1];
                        const coords = new kakao.maps.LatLng(locationText.split(', ')[0], locationText.split(', ')[1]);
                        const distance = calculateDistance(userLocation.getLat(), userLocation.getLng(), coords.getLat(), coords.getLng());
                        return { restaurant, distance };
                    }
                }).filter(item => item !== undefined);
                distances.sort((a, b) => a.distance - b.distance).forEach(({ restaurant }) => restaurantList.appendChild(restaurant));
            } else {
                const restaurantsArray = Array.from(newRestaurants);
                restaurantsArray.sort((a, b) => {
                    const aNo = parseInt(a.querySelector('a').href.split('=')[1]);
                    const bNo = parseInt(b.querySelector('a').href.split('=')[1]);
                    return bNo - aNo;
                });
                restaurantsArray.forEach(restaurant => restaurantList.appendChild(restaurant));
            }

            if (newRestaurants.length > 0) {
                showLoadMoreButton(); // 정렬의 경우도 더보기 버튼 표시
            } else {
                hideLoadMoreButton(); // 더 이상 항목이 없을 때 더보기 버튼 숨기기
            }
        })
        .catch(error => {
            console.error('Error sorting restaurants:', error);
        });
}
