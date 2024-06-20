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
let fd = new FormData();
let fdCount = 0; 

function getImageFiles(event) {
    const maxImages = 3; // 최대 이미지 개수

    // 현재 업로드된 이미지 개수 확인
    const currentImagesCount = document.querySelectorAll("div#image_container img.photo").length;

    // 새로 선택한 파일 개수와 현재 업로드된 이미지 개수를 합산하여 최대 개수 확인
    if (currentImagesCount + event.target.files.length > maxImages) {
        alert(`이미지는 ${maxImages}장까지 업로드할 수 있습니다.`);
        return;
    }
	// 선택한 파일들을 반복문으로 처리
    for (let image of event.target.files) {
        // 현재 이미지 개수 확인
        if (currentImagesCount >= maxImages) {
            alert(`이미지는 ${maxImages}장까지 업로드할 수 있습니다.`);
            break; // 반복문 종료;
        }
        
		const imageName = `image_${fdCount}`; // 고유한 이미지 이름 생성
		fd.append(imageName, image); 
		fdCount++;

        let img = document.createElement("img"); //img요소 생성
        const reader = new FileReader();
        reader.onload = function(event) {
            img.setAttribute("src", event.target.result); // 생성된 img 요소에 src 속성 추가하기
			img.classList.add("photo"); //img태그에 class명 추가 photo
			img.id = imageName; // 이미지에 고유한 id 부여
			
            // 이미지를 감싸는 div 요소 생성
            const imageDiv = document.createElement("div"); //div요소 생성
            imageDiv.classList.add("review-photo");
			imageDiv.appendChild(img); // 이미지 추가

	        // 삭제 버튼 생성
            const closeButton  = document.createElement("img");
            closeButton.classList.add("close");
            closeButton.setAttribute("src", "/assets/image/close.png");
            closeButton.onclick = function() {
                imageDiv.remove();
            };
            
            imageDiv.appendChild(closeButton);
            imageDiv.appendChild(img); // 이미지 추가

            // 이미지를 표시할 컨테이너에 추가 -> <div><imageDiv></imageDiv></div>
            document.querySelector("div#image_container").appendChild(imageDiv);
			
        }
        reader.readAsDataURL(image);
		
		
    }
}

// 폼 유효성 검사 함수
function validateForm() {
	const title = document.getElementById("title").value;
	const reviewContent = document.getElementById("reviewContent").value;
	const currentImageCount = document.querySelectorAll("div#image_container img.photo").length;

	if (!title) {
		alert("제목을 입력해주세요.");
		return false;
	}

	if (!reviewContent) {
		alert("내용을 입력해주세요.");
		return false;
	}
	    // 이미지 개수 확인
    if (currentImageCount == 0) {
        alert(`이미지는 1장이상 업로드해야 합니다.`);
        return false;
    }
	return true;
}

// 작성 버튼 클릭 이벤트 핸들러
document.getElementById("submitReviewButton").addEventListener("click", function() {
	if (validateForm()) {
		submitReview(); // 유효성 검사 후 imageCheck 함수 호출
	}
});


// "리뷰 수정" 버튼 클릭 시 실행되는 함수
function submitReview() {
    // FormData에 추가할 데이터 가져오기
    const reviewTitle = document.getElementById("title").value;
    const reviewContent = document.getElementById("reviewContent").value;
    const ratings = document.getElementById("ratingHidden").value;
    const reviewNo = document.getElementById("reviewNo").value;
    const restaurantNo = document.getElementById("restaurantNo").value;
    const maxIndex = document.getElementById("maxIndex").value;

    fd.append("reviewTitle", reviewTitle);
    fd.append("reviewContent", reviewContent);
    fd.append("ratings", ratings);
    fd.append("reviewNo", reviewNo);
    fd.append("restaurantNo", restaurantNo);
    fd.append("maxIndex", maxIndex);
	fd.get("removeImageStatus-1")

    $.ajax({
        url: '/review/reviewEdit.do',
        type: 'POST',
        data: fd,
        processData: false,
        contentType: false,
        success: function(response) {
            console.log(response);
            // 성공 메시지 표시
            alert("리뷰가 성공적으로 수정되었습니다!");
            // 페이지 이동
            location.href = '/review/review.do?reviewNo=' + reviewNo + '&restaurantNo=' + restaurantNo;
        },
        error: function(error) {
            console.error("Error:", error);
            // 실패 메시지 표시
            alert("리뷰 수정에 실패했습니다. 다시 시도해주세요.");
        }
    });
}

// 이미지 삭제 함수 (Edit)
function imageClose(event, index) {
    // 이벤트가 발생한 요소(event.target) - close 
	// 가장 가까운 조상 요소 (closest(".review-photo"))  -> review-photo 클래스를 가진
    const reviewPhoto = event.target.closest(".review-photo");  // --> 실제로 가리키는 코드 <div class="review-photo">
	const removeImageStatusName = "removeImageStatus-" + index;
	
	// 삭제된 이미지를 removeImageStatus 필드를 통해 서버에 알려줌
	let fileName = document.getElementById("removeImageName-" + index);
	
    // review-photo 클래스를 가진 요소가 존재한다면
    if (reviewPhoto) {
        // 해당 요소를 제거합니다.
        reviewPhoto.remove();
		// removeImageStatus.value = "true";
		fd.append(removeImageStatusName, fileName.value);
    } else {
		fd.append(removeImageStatusName, "none");
	}
}

