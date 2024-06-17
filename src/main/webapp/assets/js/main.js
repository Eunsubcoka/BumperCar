document.addEventListener('DOMContentLoaded', function() {
	const carousels = document.querySelectorAll('.unique-carousel');

	carousels.forEach(carousel => {
		const leftButton = carousel.querySelector('.unique-left');
		const rightButton = carousel.querySelector('.unique-right');
		const carouselImages = carousel.querySelector('.unique-carousel-images');

		leftButton.addEventListener('click', function() {
			carouselImages.scrollBy({ left: -200, behavior: 'smooth' });
		});

		rightButton.addEventListener('click', function() {
			carouselImages.scrollBy({ left: 200, behavior: 'smooth' });
		});
	});
});

let slideIndices = {
	slide1: 0,
	slide2: 0,
	slide3: 0,
	slide4: 0,
	slide5: 0
};

function changeSlide(slideId, n) {
	showSlides(slideId, slideIndices[slideId] += n);
}

function showSlides(slideId, n) {
	const slides = document.querySelectorAll(`.unique-food-category#${slideId} .slide`);
	if (slides.length === 0) return; // 슬라이드가 없을 경우 반환
	if (n >= slides.length) { slideIndices[slideId] = 0; }
	if (n < 0) { slideIndices[slideId] = slides.length - 1; }
	slides.forEach(slide => {
		slide.style.display = 'none';
	});
	slides[slideIndices[slideId]].style.display = 'flex';
}

// 각 카테고리의 첫 번째 슬라이드 출력
['slide1', 'slide2', 'slide3', 'slide4','slide5'].forEach(slideId => {
	showSlides(slideId, slideIndices[slideId]);
});

function scrollToSection(sectionId) {
	document.getElementById(sectionId).scrollIntoView({ behavior: 'smooth' });
    const currentUrl = window.location.pathname + window.location.search;
    history.replaceState(null, null, currentUrl);
}

function scrollToTop() {
    document.getElementById('main-top').scrollIntoView({ behavior: 'smooth' });
    const currentUrl = window.location.pathname + window.location.search;
    history.replaceState(null, null, currentUrl);
}
