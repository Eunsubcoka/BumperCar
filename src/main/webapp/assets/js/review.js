/* 리뷰 더보기 기능 */
// 모든 리뷰 숨기기
let items = document.querySelectorAll('.container-review');
let moreBtn = document.getElementById('more');
	for (let i = 0; i < items.length; i++) {
		items[i].style.display = 'none';
	}
//처음 5개의 리뷰만 보여주기
	for (let i = 0; i < items.length; i++) {
		if(i < 5) {
			items[i].style.display = 'block';
		}
	}	
// 더보기리뷰 버튼 클릭 했을때 
	moreBtn.addEventListener('click', function(event) {
	event.preventDefault();
	
	// 숨겨진 리뷰 가져오기
	let hiddenItems = [];
	for(let i = 0; i <items.length; i++) {
		if(items[i].style.display === 'none') {
			hiddenItems.push(items[i]);
		}
	}	
	// 3개의 숨겨진 리뷰를 표시
	for(let i = 0; i < 3; i++) { 
		if(hiddenItems[i]) {
			hiddenItems[i].style.display = 'block';
		}
	}
 	// 더이상 숨겨진 리뷰가 없을때
    if (hiddenItems.length === 0) {
        moreBtn.style.display = 'none';
        alert("더 이상 리뷰가 없습니다.");
    }
})

/* 좋아요 기능 */
// 페이지가 완전히 로드된 후에 실행되는 함수
window.onload = function() {
    // 모든 하트 아이콘 요소를 선택합니다.
    let likeBtns = document.querySelectorAll('.heartA');

    // 각 하트 아이콘에 대해 이벤트 핸들러를 등록합니다.
    likeBtns.forEach(function(likeBtn) {
        let heartEmpty = likeBtn.querySelector('.heartEmpty'); // 빈 하트
        let heartFull = likeBtn.querySelector('.heartFull'); // 꽉 찬 하트

        // 하트 아이콘을 클릭할 때마다 이벤트 핸들러를 등록합니다.
        likeBtn.addEventListener('click', function() {
            // active 클래스를 토글하여 활성/비활성 상태를 전환합니다.
            likeBtn.classList.toggle('active');
            // active 클래스 여부에 따라 하트 아이콘을 변경합니다.
            if (likeBtn.classList.contains('active')) {
                heartEmpty.style.display = 'none'; // 빈 하트 숨김
                heartFull.style.display = 'inline'; // 꽉 찬 하트 표시
            } else {
                heartEmpty.style.display = 'inline'; // 빈 하트 표시
                heartFull.style.display = 'none'; // 꽉 찬 하트 숨김
            }
        });
    });
};
$(document).ready(function() {
    $(".heartA").click(function() {
        let reviewNo = $(this).data("reviewno");
        let clickedButton = $(this); // 클릭된 좋아요 버튼을 변수에 저장
        
        $.ajax({
            url: '/review/reviewLike.do',
            type: 'POST',
            data: { 
                "reviewNo": reviewNo
            },
            success: function(response) {
                if (response.liked) {
                    alert("좋아요 성공!");
                    // 좋아요 버튼의 상태를 업데이트
                    let heartEmpty = clickedButton.find('.heartEmpty');
                    let heartFull = clickedButton.find('.heartFull');
                    heartEmpty.css('display', 'none');
                    heartFull.css('display', 'inline');
                } else {
                    alert("좋아요 취소!");
                    // 좋아요 버튼의 상태를 업데이트
                    let heartEmpty = clickedButton.find('.heartEmpty');
                    let heartFull = clickedButton.find('.heartFull');
                    heartEmpty.css('display', 'inline');
                    heartFull.css('display', 'none');
                }
                
                // 좋아요 수 업데이트
                let likeCountSpan = clickedButton.closest('.user-container').find('.heartCount');
                likeCountSpan.text('like ' + response.likeCount);
            }, 
            error: function(error) {
                console.error(error);
                alert("좋아요 실패!!");
            }
        });
    });
});


