//별점 기능 함수
function executeRating(stars, ratingValueElement, initialRating) {
    const starClassActive = "fas fa-star"; // 활성화된 별 아이콘 클래스
    const starClassInactive = "far fa-star"; // 비활성화된 별 아이콘 클래스

    // 초기 별점 값 설정
    for (let i = 0; i < initialRating; i++) {
        stars[i].className = starClassActive;
    }

    stars.forEach((star, index) => {
        star.onclick = () => {
            // 클릭한 별의 인덱스
            const clickedIndex = index;
            const ratingHidden = document.getElementById("ratingHidden");
    		
  		  	ratingHidden.value = clickedIndex + 1

            // 클릭한 별을 포함하여 이전 별들을 활성화 또는 비활성화 상태로 변경
            stars.forEach((s, i) => {
                if (i <= clickedIndex) {
                    s.className = starClassActive; // 클릭한 별 이하의 별은 활성화 상태로 변경
                } else {
                    s.className = starClassInactive; // 클릭한 별보다 이후의 별은 비활성화 상태로 변경
                }
            });

            // 별점 값 업데이트
            ratingValueElement.textContent = (clickedIndex + 1); // 클릭한 별의 인덱스에 1을 더한 값을 별점으로 설정
        };
    });
}

// 페이지 로드 시 실행
window.onload = function() {
    const ratingStars = document.querySelectorAll(".rating i"); // 별점 아이콘 요소들 가져옴
    const ratingValueElement = document.getElementById("rating-value"); // 별점 값을 표시할 요소
    const initialRating = parseInt(document.getElementById("rating-value").textContent); // 초기 별점 값 가져옴

    // 별점 기능 실행
    executeRating(ratingStars, ratingValueElement, initialRating);
};


// 이미지 업로드 
function getImageFiles(event) {
    const maxImages = 3; // 최대 이미지 개수

    // 이미지 개수 제한 확인
    if (event.target.files.length > maxImages) {
        alert(`이미지는 ${maxImages}장까지 업로드할 수 있습니다.`);
        return;
    }

    for (let image of event.target.files) {
        // 현재 이미지 개수 확인
        if (document.querySelectorAll("div#image_container img.photo").length >= maxImages) {
            alert(`이미지는 ${maxImages}장까지 업로드할 수 있습니다.`);
            break; // 반복문 종료;
        }

        let img = document.createElement("img");
        const reader = new FileReader();
        reader.onload = function(event) {
            img.setAttribute("src", event.target.result);
			img.classList.add("photo");

            // 이미지를 감싸는 div 요소 생성
            const imageDiv = document.createElement("div");
            imageDiv.classList.add("review-photo");
            imageDiv.appendChild(img); // 이미지 추가

            // 이미지를 표시할 컨테이너에 추가
            document.querySelector("div#image_container").appendChild(imageDiv);
        }
        reader.readAsDataURL(image);
    }
}

// 이미지 삭제 함수 (Edit)
function imageClose(event, index) {
    // 클릭된 close 이미지의 부모 요소인 review-photo 클래스를 가진 div 요소
    const reviewPhoto = event.target.closest(".review-photo");
	// 삭제된 이미지를 removeImageStatus 필드를 통해 서버에 알려줌
	const removeImageStatus = document.getElementById("removeImageStatus-"+index);
	
    // review-photo 클래스를 가진 요소가 존재한다면
    if (reviewPhoto) {
        // 해당 요소를 제거합니다.
        reviewPhoto.remove();
		removeImageStatus.value = true;
    }
}
