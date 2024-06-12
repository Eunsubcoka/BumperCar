<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내 정보 변경</title>
<%@ include file="/views/common/head.jsp"%>
<link rel="stylesheet" href="/assets/css/profile.css">
</head>
<body>
	<%@ include file="/views/common/header.jsp"%>
	<section>
		<div class="profile-form">
			<h2>내 정보 변경</h2>
			<form action="/profile.do" method="post"
				enctype="multipart/form-data" onsubmit="return validateForm()">
				<input type="hidden" name="userId" value="${member.userId}" />

				<c:if test="${member.profileImageUrl == null}">
					<img id="profilePreview" src="/assets/image/default-profile.png"
						alt="프로필 사진 미리보기">
				</c:if>
				<c:if test="${member.profileImageUrl != null}">
					<img id="profilePreview" src="/assets/image/member_profile/${member.profileImageUrl}"
						alt="프로필 사진 미리보기">
				</c:if>

				<label for="profileImage">프로필 사진:</label> <input type="file"
					id="profileImage" name="profileImage" accept="image/*"> <label
					for="userName">이름:</label> <input type="text" id="userName"
					name="userName" value="${member.userName}" required> <span
					id="userName-msg" class="error-msg"></span> <label for="userEmail">이메일:</label>
				<input type="email" id="userEmail" name="userEmail"
					value="${member.userEmail}" required> <span
					id="userEmail-msg" class="error-msg"></span> <label
					for="userAddress">주소:</label> <input type="text" id="new-address"
					name="new-address" value="${member.userAddress}" required>
				<input type="text" id="new-address1" name="new-address1" placeholder="입력 필요 X 주소 변경시 입력">
				<button type="button" class="check-btn" onclick="searchAddress()">주소찾기</button>
				<span id="userAddress-msg" class="error-msg"></span> <label
					for="userPhone">전화번호:</label> <input type="text" id="userPhone"
					name="userPhone" value="${member.userPhone}"> <span
					id="userPhone-msg" class="error-msg"></span>

				<button type="submit">정보 수정</button>
			</form>
		</div>
	</section>
	<%@ include file="/views/common/footer.jsp"%>
	<script src="/assets/js/bootstrap.bundle.min.js"></script>
	
	
	<script>
	    document.getElementById('profileImage').addEventListener('change', function(event) {
	        const file = event.target.files[0];
	        const reader = new FileReader();
	        reader.onload = function(e) {
	            document.getElementById('profilePreview').setAttribute('src', e.target.result);
	        }
	        reader.readAsDataURL(file);
	    });
	    function validateUsername() {
	        const username = document.getElementById('userName').value.trim();
	        const usernameMsg = document.getElementById('userName-msg');
	        const koreanPattern = /^[가-힣]+$/;
	        const invalidPattern = /[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ]/;
	        if (!koreanPattern.test(username) || invalidPattern.test(username)) {
	            usernameMsg.innerText = "한글만 입력 가능합니다.";
	            usernameMsg.style.color = "red";
	            return false;
	        } else {
	            usernameMsg.innerText = "";
	            return true;
	        }
	    }
	    function validateEmail() {
	        const email = document.getElementById('userEmail').value.trim();
	        const emailMsg = document.getElementById('userEmail-msg');
	        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
	        if (!emailPattern.test(email)) {
	            emailMsg.innerText = "유효한 이메일 주소를 입력해 주세요.";
	            emailMsg.style.color = "red";
	            return false;
	        } else {
	            emailMsg.innerText = "";
	            return true;
	        }
	    }
	    function validatePhone() {
	        const phone = document.getElementById('userPhone').value.trim();
	        const phoneMsg = document.getElementById('userPhone-msg');
	        const phonePattern = /^\d{2,3}-\d{3,4}-\d{4}$/;
	        if (!phonePattern.test(phone)) {
	            phoneMsg.innerText = "유효한 전화번호를 입력해 주세요. \n예) 010-1234-5678";
	            phoneMsg.style.color = "red";
	            return false;
	        } else {
	            phoneMsg.innerText = "";
	            return true;
	        }
	    }
	    function validateForm() {
	        const isUsernameValid = validateUsername();
	        const isEmailValid = validateEmail();
	        const isPhoneValid = validatePhone();
	        return isUsernameValid && isEmailValid && isPhoneValid;
	    }
	    function searchAddress() {
	        new daum.Postcode({
	            oncomplete : function(data) {
	                const address = data.address;
	                document.getElementById("userAddress").value = address;
	                getCoordinates(address);
	            }
	        }).open();
	    }
	    function getCoordinates(address) {
	        $.ajax({
	            type : "GET",
	            url : `https://dapi.kakao.com/v2/local/search/address.json?query=${address}`,
	            headers : {
	                "Authorization" : "KakaoAK YOUR_API_KEY"
	            },
	            success : function(res) {
	                if (res.documents.length > 0) {
	                    const location = res.documents[0];
	                    document.getElementById("new-latitude").value = location.y;
	                    document.getElementById("new-longitude").value = location.x;
	                }
	            },
	            error : function(err) {
	                console.log(err);
	            }
	        });
	    }
	    function loadScript(url, callback) {
	        const script = document.createElement("script");
	        script.type = "text/javascript";
	        if (script.readyState) {
	            script.onreadystatechange = function() {
	                if (script.readyState == "loaded" || script.readyState == "complete") {
	                    script.onreadystatechange = null;
	                    callback();
	                }
	            };
	        } else {
	            script.onload = function() {
	                callback();
	            };
	        }
	        script.src = url;
	        document.getElementsByTagName("head")[0].appendChild(script);
	    }
	    loadScript("https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js", function() {
	        // Callback function after script is loaded
	    });
	    document.getElementById('userName').addEventListener('input', validateUsername);
	    document.getElementById('userEmail').addEventListener('input', validateEmail);
	    document.getElementById('userPhone').addEventListener('input', validatePhone);
	</script>
</body>
</html>
