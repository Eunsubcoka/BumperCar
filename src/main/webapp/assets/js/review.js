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
//window.onload -> 페이지가 완전히 로드된 후에 실행되는 함수
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
            } 
			else {
                heartEmpty.style.display = 'inline'; // 빈 하트 표시
                heartFull.style.display = 'none'; // 꽉 찬 하트 숨김
            }
        });
    });
};

/* 좋아요 기능 */
//window.onload -> 페이지가 완전히 로드된 후에 실행되는 함수
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
            } 
			else {
                heartEmpty.style.display = 'inline'; // 빈 하트 표시
                heartFull.style.display = 'none'; // 꽉 찬 하트 숨김
            }
        });
    });
};


$(document).ready(function() {
    // 초기화: 페이지 로드 시 좋아요 정보 가져오기
    $(".heartA").each(function() {
        var reviewNo = $(this).data("reviewno");
        var $heartCount = $(this).closest('.user-container').find('.heartCount');

        // AJAX를 통해 서버에 좋아요 정보 요청 보내기
        $.ajax({
            url: '/review/getLikeInfo.do',
            type: 'POST',
            data: { "reviewNo": reviewNo },
            dataType: 'json',
            success: function(response) {
                if (response.success) {
                    // 좋아요 수 업데이트
                    $heartCount.text(' like ' + response.likeCount);
                    // 이미 좋아요를 한 경우 버튼 상태 업데이트
                    if (response.alreadyLiked) {
                        $(".heartA[data-reviewno='" + reviewNo + "']").addClass("active");
                        $(".heartA[data-reviewno='" + reviewNo + "'] .heartEmpty").hide();
                        $(".heartA[data-reviewno='" + reviewNo + "'] .heartFull").show();
                    }
                    // 좋아요 정보를 데이터 속성으로 저장
                    $(".heartA[data-reviewno='" + reviewNo + "']").data("alreadyliked", response.alreadyLiked);
                } else {
                    alert("좋아요 정보를 가져오지 못했습니다.");
                }
            },
            error: function(error) {
                console.error(error);
                alert("서버 오류 발생!");
            }
        });
    });

    // 좋아요 버튼 클릭 시 이벤트 핸들러
    $(".heartA").click(function() {
        var reviewNo = $(this).data("reviewno");
        var alreadyLiked = $(this).data("alreadyliked");
        var $heartCount = $(this).closest('.user-container').find('.heartCount');

        // 좋아요가 이미 눌려있는 경우 처리하지 않음
        if (alreadyLiked) {
            return;
        }

        // AJAX를 통해 서버에 좋아요 요청 보내기
        $.ajax({
            url: '/review/reviewLike.do',
            type: 'POST',
            data: { "reviewNo": reviewNo },
            dataType: 'json',
            success: function(response) {
                if (response.success) {
                    // 좋아요 수 업데이트
                    $heartCount.text(' like ' + response.likeCount);
                    // 버튼 상태 업데이트
                    $(".heartA[data-reviewno='" + reviewNo + "']").addClass("active");
                    $(".heartA[data-reviewno='" + reviewNo + "'] .heartEmpty").hide();
                    $(".heartA[data-reviewno='" + reviewNo + "'] .heartFull").show();
                    $(".heartA[data-reviewno='" + reviewNo + "']").data("alreadyliked", true);
                } else {
                    alert("좋아요 처리 실패!");
                }
            },
            error: function(error) {
                console.error(error);
                alert("서버 오류 발생!");
            }
        });
    });
});
// 좋아요 버튼 클릭 이벤트 핸들러
/*$(document).ready(function() {
	$("#likeBtn").click(function() {
        // AJAX를 통해 서버에 좋아요 요청 보내기
        $.ajax({
            url: '/review/reviewLike.do', // 좋아요를 처리할 서버의 URL
            type: 'POST',
            data: { "reviewNo": $("#reviewNo").val()},
            success: function(response) {
                // 서버 응답을 받아 처리
                console.log(response);
            }, 
            error: function(error) {
                console.error(error);
                alert("좋아요 실패!!");
            }
		});
	});
})*/

/*document.querySelectorAll('.heartA').forEach(function(likeBtn) {
    likeBtn.addEventListener('click', function() {
        // 좋아요를 누른 리뷰번호 가져오기
        const reviewNo = likeBtn.dataset.reviewNo;

        // AJAX를 통해 서버에 좋아요 요청 보내기
        $.ajax({
            url: '/review/reviewLike.do', // 좋아요를 처리할 서버의 URL
            type: 'POST',
            data: { reviewNo: reviewNo },
            success: function(response) {
                // 서버 응답을 받아 처리
                console.log(response);
            }, 
            error: function(error) {
                console.error(error);
                alert("좋아요 실패!!");
            }
        });
    });
});
*/







