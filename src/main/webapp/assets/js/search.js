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
