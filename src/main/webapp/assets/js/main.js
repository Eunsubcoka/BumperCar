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
    
    
    
    
    // slide1 카테고리에 대한 슬라이드 함수
    let oneSlideIndex = 0;

    function changeOneSlide(n) {
        showOneSlides(oneSlideIndex += n);
    }

    function showOneSlides(n) {
    	//id = slide1인 unique-food-category
        const slides = document.querySelectorAll('.unique-food-category#slide1 .slide');
        if (n >= slides.length) { oneSlideIndex = 0 }
        if (n < 0) { oneSlideIndex = slides.length - 1 }
        for (let slide of slides) {
            slide.style.display = 'none';
        }
        slides[oneSlideIndex].style.display = 'flex';
    }
    
   
    // slide1 카테고리의 첫번째 슬라이드 출력
    showOneSlides(oneSlideIndex);
 //---------------------------------------

    // slide2 카테고리에 대한 슬라이드 함수
    let twoSlideIndex = 0;

    function changeTwoSlide(n) {
        showTwoSlides(twoSlideIndex += n);
    }

    function showTwoSlides(n) {
    	//id = slide2인 unique-food-category
        const slides = document.querySelectorAll('.unique-food-category#slide2 .slide');
        if (n >= slides.length) { twoSlideIndex = 0 }
        if (n < 0) { twoSlideIndex = slides.length - 1 }
        for (let slide of slides) {
            slide.style.display = 'none';
        }
        slides[twoSlideIndex].style.display = 'flex';
    }

    // 양식 카테고리의 첫번째 슬라이드 출력
    showTwoSlides(twoSlideIndex);
    
    //----------------------------------
    
    // slide3 카테고리에 대한 슬라이드 함수
    let threeSlideIndex = 0;

    function changeThreeSlide(n) {
        showThreeSlides(threeSlideIndex += n);
    }

    function showThreeSlides(n) {
    	//id = slide3인 unique-food-category
        const slides = document.querySelectorAll('.unique-food-category#slide3 .slide');
        if (n >= slides.length) { threeSlideIndex = 0 }
        if (n < 0) { threeSlideIndex = slides.length - 1 }
        for (let slide of slides) {
            slide.style.display = 'none';
        }
        slides[threeSlideIndex].style.display = 'flex';
    }
    
   
    // slide3 카테고리의 첫번째 슬라이드 출력
    showThreeSlides(threeSlideIndex);
    
    //-----------------------------------
    
    
    // slide4 카테고리에 대한 슬라이드 함수
    let fourSlideIndex = 0;

    function changeFourSlide(n) {
        showFourSlides(fourSlideIndex += n);
    }

    function showFourSlides(n) {
    	//id = slide4인 unique-food-category
        const slides = document.querySelectorAll('.unique-food-category#slide4 .slide');
        if (n >= slides.length) { fourSlideIndex = 0 }
        if (n < 0) { fourSlideIndex = slides.length - 1 }
        for (let slide of slides) {
            slide.style.display = 'none';
        }
        slides[fourSlideIndex].style.display = 'flex';
    }
    
   
    // slide4 카테고리의 첫번째 슬라이드 출력
    showFourSlides(fourSlideIndex);