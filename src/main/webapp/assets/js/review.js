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

