var mapContainer = document.getElementById('map'),
    mapOption = {
        center: new kakao.maps.LatLng(37.3987966098124, 126.920790701382), // 중심좌표
        level: 5 // 확대 레벨
    };

var map = new kakao.maps.Map(mapContainer, mapOption);

var geocoder = new kakao.maps.services.Geocoder();

// search-main 요소에서 locations 데이터를 가져옵니다
var searchMain = document.getElementById('search-main');
var locations = JSON.parse(searchMain.dataset.locations);

locations.forEach(function(location) {
    geocoder.addressSearch(location.location, function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
            var marker = new kakao.maps.Marker({
                map: map,
                position: coords
            });

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
                '            <img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/thumnail.png" width="73" height="70">' +
                '        </div>' +
                '        <div class="desc">' +
                '            <div class="ellipsis">' + location.location + '</div>' +
                '            <div class="category ellipsis">' + location.category + '</div>' +
                '            <div><a href="/restaurantDetail.do?restaurantId=' + location.restaurantNo + '" target="_blank" class="link">상세보기</a></div>' +
                '        </div>' +
                '    </div>' +
                '</div>';

            var overlay = new kakao.maps.CustomOverlay({
                content: content,
                map: null,
                position: marker.getPosition()
            });

            kakao.maps.event.addListener(marker, 'click', function() {
                overlay.setMap(map);
            });

            var closeBtn = content.querySelector('.close');
            closeBtn.addEventListener('click', function() {
                overlay.setMap(null);
            });
        }
    });
});

function loadMoreRestaurants() {
    const loadMoreButton = document.querySelector('#load-more-restaurants');
    const currentPage = parseInt(loadMoreButton.dataset.page || 1);
    const nextPage = currentPage + 1;
    const searchText = encodeURIComponent(loadMoreButton.dataset.searchText || '');
    const tag = encodeURIComponent(loadMoreButton.dataset.tag || '');
    const category = loadMoreButton.dataset.category || 'restaurant';

    fetch(`/search.do?cpage=${nextPage}&search-text=${searchText}&tag=${tag}&category=${category}`)
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
