// 별점 기능
function executeRating(stars) {
  const starClassActive = "rating__star fas fa-star"; // 활성화된 별 클래스
  const starClassInactive = "rating__star far fa-star"; // 비활성화된 별 클래스

  stars.forEach((star, index) => {
      star.onclick = () => {
          // 클릭한 별의 인덱스
          const clickedIndex = index;
	
          
          // 클릭한 별을 포함하여 이전 별들을 활성화 또는 비활성화 상태로 변경
          stars.forEach((s, i) => {
              if (i <= clickedIndex) {
                  s.className = starClassActive; // 클릭한 별 이하의 별은 활성화 상태로 변경
              } else {
                  s.className = starClassInactive; // 클릭한 별보다 이후의 별은 비활성화 상태로 변경
              }
          });
      };
  });
}

// 별점 텍스트 업데이트
function updateRatingText(stars, ratingTexts) {
  stars.forEach((star, index) => {
      star.addEventListener('click', () => {
		  const star = document.getElementById("stars");
          // 클릭한 별의 인덱스를 가져옴
          const clickedIndex = index;

          // 클릭한 별에 해당하는 텍스트를 가져와서 업데이트
          const ratingText = document.querySelector('.rating-text');
          ratingText.textContent = (clickedIndex + 1) + "점(" + ratingTexts[clickedIndex] + ")";
  		  star.value = clickedIndex + 1;
      });
  });
}

// 페이지 로드 시 실행
window.onload = function() {
  const ratingStars = [...document.getElementsByClassName("rating__star")]; // 별점 요소들 가져옴
  const ratingTexts = ["매우 불만족", "불만족", "보통", "만족", "매우 만족"]; // 별점 텍스트
  
  // 별점 기능 실행
  executeRating(ratingStars);
  
  // 별점 텍스트 업데이트 기능 실행
  updateRatingText(ratingStars, ratingTexts);
};




// 이미지 업로드 
function getImageFiles(event) {
    const maxImages = 3; // 최대 이미지 개수
	// 현재 이미지 개수 확인
    const currentImageCount = document.querySelectorAll("div#image_container img.photo").length;


    // 이미지 개수 제한 확인
    if (event.target.files.length + currentImageCount > maxImages) {
        alert(`이미지는 ${maxImages}장까지 업로드할 수 있습니다.`);
        return;
    }

    for (let image of event.target.files) {
        // 현재 이미지 개수 확인
        if (currentImageCount.length >= maxImages) {
            alert(`이미지는 ${maxImages}장까지 업로드할 수 있습니다.`);
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

            // 이미지 삭제 버튼 생성
            const closeButton = document.createElement("img");
            closeButton.classList.add("close");
            closeButton.src = "/assets/image/close.png";
            closeButton.onclick = function() {
                imageDiv.remove(); // 해당 이미지 요소를 삭제
            };
            
            // close 버튼 스타일 추가
            closeButton.style.width = "10px";
            closeButton.style.height = "10px";
            closeButton.style.float = "right";
            closeButton.style.position = "absolute";
            imageDiv.appendChild(closeButton); // 삭제 버튼 추가

            // 이미지를 표시할 컨테이너에 추가
            document.querySelector("div#image_container").appendChild(imageDiv);
        }
        reader.readAsDataURL(image);
    }
}


