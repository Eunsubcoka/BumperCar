document.addEventListener("DOMContentLoaded", function() {
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

    // 카카오맵 마커 표시
    var mapContainer = document.getElementById('map'),
        mapOption = {
            center: new kakao.maps.LatLng(37.4003260238437, 126.922178588099), // 중심좌표
            level: 5 // 확대 레벨
        };

    var map = new kakao.maps.Map(mapContainer, mapOption);

    var geocoder = new kakao.maps.services.Geocoder();

    locations.forEach(function(location) {
        geocoder.addressSearch(location.location, function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                var marker = new kakao.maps.Marker({
                    map: map,
                    position: coords
                });

                var infowindow = new kakao.maps.InfoWindow({
                    content: '<div style="width:150px;text-align:center;padding:6px 0;">'+location.name+'</div>'
                });
                infowindow.open(map, marker);
            }
        });
    });
});
