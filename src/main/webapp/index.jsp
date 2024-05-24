<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
  <head>
    <%@ include file="views/common/head.jsp"%>
  
  </head>
  <body>
    
  <%@ include file="views/common/header.jsp"%>


      <nav class="unique-nav">
        <a href="#">카테고리</a>
        <a href="#">공지사항</a>
    </nav>
    <main class="unique-main">
        <section class="unique-food-category" id="korean">
            <h2>#한식</h2>
            <div class="unique-carousel">
                <button class="unique-carousel-btn unique-left">◀</button>
                <div class="unique-carousel-images">
                    <div class="unique-carousel-item">
                        <div class="unique-image">이미지</div>
                        <div class="unique-title">식당이름</div>
                    </div>
                    <div class="unique-carousel-item">
                        <div class="unique-image">이미지</div>
                        <div class="unique-title">식당이름</div>
                    </div>
                    <div class="unique-carousel-item">
                        <div class="unique-image">이미지</div>
                        <div class="unique-title">식당이름</div>
                    </div>
                </div>
                <button class="unique-carousel-btn unique-right">▶</button>
            </div>
        </section>

        <section class="unique-food-category" id="western">
            <h2>#양식</h2>
            <div class="unique-carousel">
                <button class="unique-carousel-btn unique-left">◀</button>
                <div class="unique-carousel-images">
                    <div class="unique-carousel-item">
                        <div class="unique-image">이미지</div>
                        <div class="unique-title">식당이름</div>
                    </div>
                    <div class="unique-carousel-item">
                        <div class="unique-image">이미지</div>
                        <div class="unique-title">식당이름</div>
                    </div>
                    <div class="unique-carousel-item">
                        <div class="unique-image">이미지</div>
                        <div class="unique-title">식당이름</div>
                    </div>
                </div>
                <button class="unique-carousel-btn unique-right">▶</button>
            </div>
        </section>
                <section class="unique-food-category" id="western">
            <h2>#양식</h2>
            <div class="unique-carousel">
                <button class="unique-carousel-btn unique-left">◀</button>
                <div class="unique-carousel-images">
                    <div class="unique-carousel-item">
                        <div class="unique-image">이미지</div>
                        <div class="unique-title">식당이름</div>
                    </div>
                    <div class="unique-carousel-item">
                        <div class="unique-image">이미지</div>
                        <div class="unique-title">식당이름</div>
                    </div>
                    <div class="unique-carousel-item">
                        <div class="unique-image">이미지</div>
                        <div class="unique-title">식당이름</div>
                    </div>
                </div>
                <button class="unique-carousel-btn unique-right">▶</button>
            </div>
        </section>
                <section class="unique-food-category" id="western">
            <h2>#양식</h2>
            <div class="unique-carousel">
                <button class="unique-carousel-btn unique-left">◀</button>
                <div class="unique-carousel-images">
                    <div class="unique-carousel-item">
                        <div class="unique-image">이미지</div>
                        <div class="unique-title">식당이름</div>
                    </div>
                    <div class="unique-carousel-item">
                        <div class="unique-image">이미지</div>
                        <div class="unique-title">식당이름</div>
                    </div>
                    <div class="unique-carousel-item">
                        <div class="unique-image">이미지</div>
                        <div class="unique-title">식당이름</div>
                    </div>
                </div>
                <button class="unique-carousel-btn unique-right">▶</button>
            </div>
        </section>
                <section class="unique-food-category" id="western">
            <h2>#양식</h2>
            <div class="unique-carousel">
                <button class="unique-carousel-btn unique-left">◀</button>
                <div class="unique-carousel-images">
                    <div class="unique-carousel-item">
                        <div class="unique-image">이미지</div>
                        <div class="unique-title">식당이름</div>
                    </div>
                    <div class="unique-carousel-item">
                        <div class="unique-image">이미지</div>
                        <div class="unique-title">식당이름</div>
                    </div>
                    <div class="unique-carousel-item">
                        <div class="unique-image">이미지</div>
                        <div class="unique-title">식당이름</div>
                    </div>
                </div>
                <button class="unique-carousel-btn unique-right">▶</button>
            </div>
        </section>
                <section class="unique-food-category" id="western">
            <h2>#양식</h2>
            <div class="unique-carousel">
                <button class="unique-carousel-btn unique-left">◀</button>
                <div class="unique-carousel-images">
                    <div class="unique-carousel-item">
                        <div class="unique-image">이미지</div>
                        <div class="unique-title">식당이름</div>
                    </div>
                    <div class="unique-carousel-item">
                        <div class="unique-image">이미지</div>
                        <div class="unique-title">식당이름</div>
                    </div>
                    <div class="unique-carousel-item">
                        <div class="unique-image">이미지</div>
                        <div class="unique-title">식당이름</div>
                    </div>
                </div>
                <button class="unique-carousel-btn unique-right">▶</button>
            </div>
        </section>
    </main>
    

    <script>
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
    </script>


  <%@ include file="views/common/footer.jsp"%>


    <script src="/assets/js/bootstrap.bundle.min.js"></script>

  </body>
</html>
