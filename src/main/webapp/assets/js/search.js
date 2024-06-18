var mapContainer = document.getElementById('map');
var map;
var userInfoWindow;
var userMarker;
var markers = [];
var overlays = [];
var userLocation;
var initialLoad = true;

function calculateDistance(lat1, lon1, lat2, lon2) {
    const R = 6371;
    const dLat = (lat2 - lat1) * Math.PI / 180;
    const dLon = (lon2 - lon1) * Math.PI / 180;
    const a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
              Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
              Math.sin(dLon / 2) * Math.sin(dLon / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c;
}

function initializeMap() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var lat = position.coords.latitude;
            var lon = position.coords.longitude;
            userLocation = new kakao.maps.LatLng(lat, lon);

            map = new kakao.maps.Map(mapContainer, {
                center: userLocation,
                level: 5
            });

            userMarker = new kakao.maps.Marker({
                map: map,
                position: userLocation
            });

            var userInfoContent = '<div class="user-info-content">내 위치</div>';

            userInfoWindow = new kakao.maps.InfoWindow({
                position: userLocation,
                content: userInfoContent,
                removable: false
            });

            userInfoWindow.open(map, userMarker);

            kakao.maps.event.addListener(userMarker, 'click', function() {
                if (userInfoWindow.getMap()) {
                    userInfoWindow.close();
                } else {
                    userInfoWindow.open(map, userMarker);
                }
            });

            addMarkers('distance');
        }, function(error) {
            console.error('Error occurred. Error code: ' + error.code);
            initializeDefaultMap();
        });
    } else {
        console.error('Geolocation is not supported by this browser.');
        initializeDefaultMap();
    }
}

function initializeDefaultMap() {
    userLocation = new kakao.maps.LatLng(37.3987966098124, 126.920790701382);
    map = new kakao.maps.Map(mapContainer, {
        center: userLocation,
        level: 5
    });

    addMarkers('distance');
}

function addMarkers(sortOrder = 'distance', page = 1) {
    var geocoder = new kakao.maps.services.Geocoder();
    var searchMain = document.getElementById('search-main');
    var locations = JSON.parse(searchMain.dataset.locations);

    var distances = [];
    var itemsPerPage = 50;
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

                    distances.slice(startIndex, endIndex).forEach(function(item) {
                        var marker = new kakao.maps.Marker({
                            map: map,
                            position: item.coords
                        });
                        markers.push(marker);

                        var tags = item.location.tags.join(', ');
                        if (tags.length > 20) {
                            tags = tags.substring(0, 20) + '...';
                        }

                        var imgSrc = item.location.imgName ? item.location.imgName : 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/thumnail.png';

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

                    updateRestaurantList(distances.slice(0, endIndex), sortOrder);

                }
            } else {
                // If geocoding fails, set a maximum distance to indicate an error
                distances.push({
                    location: location,
                    coords: null,
                    distance: Number.MAX_VALUE
                });
            }
        });
    });
}





function updateRestaurantList(distances, sortOrder) {
    const restaurantList = document.querySelector('#restaurant-list');
    restaurantList.innerHTML = '';

    distances.forEach(function(item) {
        const restaurantItem = document.createElement('li');
        restaurantItem.className = 'restaurant-item';
        restaurantItem.innerHTML = `
            <div class="restaurant-image">
                ${item.location.imgName ? `<img src="${item.location.imgName}" alt="${item.location.name}">` : '<p>사진 추가 예정</p>'}
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
                <div class="font-down">거리: ${item.distance !== Number.MAX_VALUE ? item.distance.toFixed(2) + ' km' : '거리 계산 실패'}</div>
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
                    geocoder.addressSearch(locationText, function(result, status) {
                        if (status === kakao.maps.services.Status.OK) {
                            const coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                            const distance = calculateDistance(userLocation.getLat(), userLocation.getLng(), coords.getLat(), coords.getLng());
                            const distanceElement = restaurant.querySelector('.font-down:nth-child(5)');
                            if (distanceElement) {
                                distanceElement.textContent = `거리: ${distance.toFixed(2)} km`;
                            }
                        }
                    });
                }
                restaurantList.appendChild(restaurant);
            });

            if (newRestaurants.length < 5) {
                hideLoadMoreButton();
            } else {
                loadMoreButton.dataset.page = nextPage;
            }
        })
        .catch(error => {
            console.error('Error loading more restaurants:', error);
            hideLoadMoreButton();
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
            const noticeList = document.querySelector('#notice-list');
            newNotices.forEach(notice => {
                noticeList.appendChild(notice);
            });

            const totalNotices = parseInt(doc.querySelector('#totalNotices').value);
            if (newNotices.length < 5 || noticeList.children.length >= totalNotices) {
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
                    geocoder.addressSearch(locationText, function(result, status) {
                        if (status === kakao.maps.services.Status.OK) {
                            const coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                            const distance = calculateDistance(userLocation.getLat(), userLocation.getLng(), coords.getLat(), coords.getLng());
                            const distanceElement = restaurant.querySelector('.font-down:nth-child(5)');
                            if (distanceElement) {
                                distanceElement.textContent = `거리: ${distance.toFixed(2)} km`;
                            }
                        }
                    });
                }
                restaurantList.appendChild(restaurant);
            });

            if (sortOrder === 'distance') {
                const distances = Array.from(newRestaurants).map(restaurant => {
                    const locationElement = restaurant.querySelector('.restaurant-info > .font-down:nth-child(4)');
                    if (locationElement) {
                        const locationText = locationElement.textContent.split(': ')[1];
                        geocoder.addressSearch(locationText, function(result, status) {
                            if (status === kakao.maps.services.Status.OK) {
                                const coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                                const distance = calculateDistance(userLocation.getLat(), userLocation.getLng(), coords.getLat(), coords.getLng());
                                return { restaurant, distance };
                            }
                        });
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
                hideLoadMoreButton();
            } else if(newRestaurants.length=0) {
                hideLoadMoreButton();
            }else {
                hideLoadMoreButton();
            }
        })
        .catch(error => {
            console.error('Error sorting restaurants:', error);
        });
}
