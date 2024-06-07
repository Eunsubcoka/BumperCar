function toggleReview(button) {
    const reviewBox = button.closest('.restaurant-item').querySelector('.review-box');
    const isVisible = reviewBox.style.display === 'block';
    reviewBox.style.display = isVisible ? 'none' : 'block';
    button.textContent = isVisible ? '리뷰 열기' : '리뷰 닫기';
}


