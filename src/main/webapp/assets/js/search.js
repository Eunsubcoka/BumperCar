function toggleReview(button) {
    const reviewBox = button.closest('.restaurant-item').querySelector('.review-box');
    const isVisible = reviewBox.style.display === 'block';
    reviewBox.style.display = isVisible ? 'none' : 'block';
    button.textContent = isVisible ? '리뷰 열기' : '리뷰 닫기';
}

// Prevent parent div click event from triggering link navigation
document.querySelectorAll('.restaurant-item').forEach(item => {
    item.addEventListener('click', (event) => {
        if (!event.target.classList.contains('restaurant-link')) {
            event.preventDefault();
        }
    });
});
