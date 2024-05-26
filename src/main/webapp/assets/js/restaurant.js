/**
 * 
 */

function openPop() {
	const popEle = document.getElementsByClassName("layer_pop")[0];
	const body = document.getElementById("body");
	popEle.style.display = "block";
	body.style.backgroundColor = "rgba(0, 0, 0, .7)";

}
function closePop() {
	const popEle = document.getElementsByClassName("layer_pop")[0];
	const body = document.getElementById("body");
	popEle.style.display = "none";
	body.style.backgroundColor = "rgba(0, 0, 0, .7)";

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
