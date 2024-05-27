/**
 * 
 */

function openPop() {
	const popEle = document.getElementsByClassName("layer_pop")[0];
	const bg = document.getElementsByClassName("bg")[0];
	const body = document.getElementById("body");
	popEle.style.display = "block";
	body.style.overflow = "hidden";
	bg.style.display= "block";
	bg.style.zIndex= "10";
	
}
function closePop() {
	const popEle = document.getElementsByClassName("layer_pop")[0];
	const body = document.getElementById("body");
	const bg = document.getElementsByClassName("bg")[0];
	popEle.style.display = "none";
	body.style.overflow = "auto";
	bg.style.display = "none";
}


function shareTwitter() {
    var sendText = "띵호"; // 전달할 텍스트
    var sendUrl = "http://localhost/views/restaurant/restaurantDetail.jsp#"; // 전달할 URL
    window.open("https://twitter.com/intent/tweet?text=" + sendText + "&url=" + sendUrl);
}

  function shareMessage() {
   Kakao.Share.sendScrap({
  requestUrl: 'http://localhost/views/restaurant/restaurantDetail.jsp#',
});
  }
