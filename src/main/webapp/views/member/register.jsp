<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<link href="/assets/css/register.css" rel="stylesheet">
<html>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<%@ include file="/views/common/head.jsp" %>
<body>
<%@ include file="/views/common/header.jsp" %>
<%@ include file="/views/common/nav.jsp" %>

<section>
  <div class="signup-form">
    <h2>회원가입</h2>
    <form action="/member/register.do" method="POST">
      <input type="hidden" id="duplicateCheck" name="duplicateCheck" />
      <div class="input-container">
        <label for="new-username">이름</label>
        <input type="text" id="new-username" name="new-username" required>
      </div>

      <div class="input-container">
        <label for="new-userid">아이디</label>
        <input type="text" id="new-userid" name="new-userid" onkeyup="duplicateId()" required>
        <button type="button" class="check-btn" onclick="duplicateId()">중복확인</button>
        <span id="id-msg"></span>
      </div>

      <div class="input-container">
        <label for="new-email">이메일</label>
        <input type="text" id="new-email" name="new-email" required>
      </div>

      <div class="input-container">
        <label for="new-address">주소</label>
        <input type="text" id="new-address" name="new-address" required>
        <input type="text" id="new-address" name="new-address" required>
        <button type="button" class="check-btn" onclick="searchAddress()">주소 찾기</button>
        <input type="hidden" id="new-latitude" name="new-latitude">
        <input type="hidden" id="new-longitude" name="new-longitude">
      </div>

      <div class="input-container">
        <label for="new-phone">전화번호</label>
        <input type="text" id="new-phone" name="new-phone" required>
      </div>

      <div class="input-container">
        <label for="new-password">비밀번호</label>
        <input type="password" id="new-password" name="new-password" required>
      </div>

      <div class="input-container">
        <label for="confirm-password">비밀번호 확인</label>
        <input type="password" id="confirm-password" name="confirm-password" required>
      </div>

      <button type="submit" >회원가입</button>
    </form>
  </div>
</section>
<%@ include file="/views/common/footer.jsp" %>
</body>
</html>

<script>
function duplicateId(){
  const userId = document.getElementById("new-userid").value;
  const idMsg = document.getElementById("id-msg");
  const duplicateCheck = document.getElementById("duplicateCheck");

  $.ajax({
    type:"POST",
    url:"/member/duplicateId.do",
    data: {userId : userId},
    success: function(res){
      if(res === "available"){
        duplicateCheck.value = "available";
        idMsg.style.color = "green";
        idMsg.innerHTML = "사용 가능한 아이디 입니다.";
      } else {
        duplicateCheck.value = "unavailable";
        idMsg.style.color = "red";
        idMsg.innerHTML = "중복된 아이디입니다.";
      }
    },
    error: function(err) {
      idMsg.style.color = "red";
      idMsg.innerHTML = "오류가 발생했습니다. 다시 시도해주세요.";
    }
  })
}

// 카카오 주소 검색 API를 사용한 주소 검색
function searchAddress() {
  new daum.Postcode({
    oncomplete: function(data) {
      var address = data.address;
      document.getElementById("new-address").value = address;
      getCoordinates(address);
    }
  }).open();
}

// 주소로부터 좌표를 가져오는 함수
function getCoordinates(address) {
  $.ajax({
    type: "GET",
    url: `https://dapi.kakao.com/v2/local/search/address.json?query=${address}`,
    headers: { "Authorization": "KakaoAK YOUR_API_KEY" },
    success: function(res) {
      if (res.documents.length > 0) {
        var location = res.documents[0];
        document.getElementById("new-latitude").value = location.y;
        document.getElementById("new-longitude").value = location.x;
      }
    },
    error: function(err) {
      console.log(err);
    }
  });
}

// 카카오 주소 검색 API 로드
function loadScript(url, callback){
  var script = document.createElement("script")
  script.type = "text/javascript";
  if (script.readyState){  //IE
      script.onreadystatechange = function(){
          if (script.readyState == "loaded" || script.readyState == "complete"){
              script.onreadystatechange = null;
              callback();
          }
      };
  } else {  //Others
      script.onload = function(){
          callback();
      };
  }
  script.src = url;
  document.getElementsByTagName("head")[0].appendChild(script);
}

loadScript("https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js", function(){
  // script is loaded
});
</script>
