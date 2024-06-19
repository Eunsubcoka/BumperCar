<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<link href="/assets/css/register.css" rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<title>회원가입 | TastyRoad</title>
<%@ include file="/views/common/head.jsp"%>
</head>
<body>
	<%@ include file="/views/common/header.jsp"%>
	<%@ include file="/views/common/nav.jsp"%>

	<section>
		<div class="signup-form">
			<h2>회원가입</h2>
			<form id="register-form" action="/register.do"
				method="POST">
				<input type="hidden" id="duplicateCheck" name="duplicateCheck" />
				<div class="input-container">
					<label for="new-username">이름</label> <input type="text"
						id="new-username" name="new-username" required> <span
						class="error-msg" id="username-msg"></span>
				</div>

				<div class="input-container">
					<label for="new-userid">아이디</label> <input type="text"
						id="new-userid" name="new-userid" required>
					<button type="button" class="check-btn" onclick="duplicateId()">중복확인</button>
					<span class="error-msg" id="id-msg"></span>
				</div>

				<div class="input-container">
					<label for="new-email">이메일</label> <input type="text"
						id="new-email" name="new-email" required> 
						<button type="button" class="check-btn" onclick="sendVerificationCode()">인증코드 보내기</button>
						<span class="error-msg" id="email-msg"></span>
				</div>
				
	            <div class="input-container">
                    <label for="verify-code">이메일 인증 코드:</label>
                    <input type="text" id="verify-code" name="verify-code" required>
                    <button type="button" class="check-btn" onclick="verifyCode()">인증확인</button>
                    <span id="verify-msg" class="msg"></span>
                </div>

				<div class="input-container">
					<label for="new-address">주소</label> <input type="text"
						id="new-address" name="new-address" required> <input
						type="text" id="new-address1" name="new-address1" required>
					<button type="button" class="check-btn" onclick="searchAddress()">주소
						찾기</button>
					<input type="hidden" id="new-latitude" name="new-latitude">
					<input type="hidden" id="new-longitude" name="new-longitude">
					<span class="error-msg" id="address-msg"></span>
				</div>

				<div class="input-container">
					<label for="new-phone">전화번호</label> <input type="text"
						id="new-phone" name="new-phone" required
						pattern="^\d{2,3}-\d{3,4}-\d{4}$"> <span class="error-msg"
						id="phone-msg"></span>
				</div>

				<div class="input-container">
					<label for="new-password">비밀번호</label> <input type="password"
						id="new-password" name="new-password" required minlength="8">
					<span class="error-msg" id="password-msg"></span>
				</div>

				<div class="input-container">
					<label for="confirm-password">비밀번호 확인</label> <input
						type="password" id="confirm-password" name="confirm-password"
						required> <span class="error-msg"
						id="confirm-password-msg"></span>
				</div>

				<button type="submit">회원가입</button>
			</form>
		</div>
	</section>
	<%@ include file="/views/common/footer.jsp"%>
</body>
</html>

<script>
	function duplicateId() {
		const userId = document.getElementById("new-userid").value.trim();
		const idMsg = document.getElementById("id-msg");
		const duplicateCheck = document.getElementById("duplicateCheck");

		if (/[\u3131-\uD79D]/.test(userId)) {
			idMsg.style.color = "red";
			idMsg.innerHTML = "아이디에는 한글을 포함할 수 없습니다.";
			return;
		}

		if (userId === "") {
			duplicateCheck.value = "unavailable";
			idMsg.style.color = "red";
			idMsg.innerHTML = "아이디를 입력해 주세요.";
			return;
		}

		$.ajax({
			type : "POST",
			url : "/member/duplicateId.do",
			data : {
				userId : userId
			},
			success : function(res) {
				if (res === "available") {
					duplicateCheck.value = "available";
					idMsg.style.color = "green";
					idMsg.innerHTML = "사용 가능한 아이디 입니다.";
				} else {
					duplicateCheck.value = "unavailable";
					idMsg.style.color = "red";
					idMsg.innerHTML = "중복된 아이디입니다.";
				}
			},
			error : function(err) {
				console.log(err);
				idMsg.innerHTML = "오류가 발생했습니다. 다시 시도해주세요.";
			}
		});
	}

	function searchAddress() {
		new daum.Postcode({
			oncomplete : function(data) {
				var address = data.address;
				document.getElementById("new-address").value = address;
				getCoordinates(address);
			}
		}).open();
	}

	function getCoordinates(address) {
		$
				.ajax({
					type : "GET",
					url : `https://dapi.kakao.com/v2/local/search/address.json?query=${address} `,
					headers : {
						"Authorization" : "b57304e32c7237fdd33fc966d2ca828b"
					},
					success : function(res) {
						if (res.documents.length > 0) {
							var location = res.documents[0];
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
		var script = document.createElement("script");
		script.type = "text/javascript";
		if (script.readyState) {
			script.onreadystatechange = function() {
				if (script.readyState == "loaded"
						|| script.readyState == "complete") {
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

	loadScript(
			"https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js",
			function() {

			});

	function validateUsername() {
		const username = document.getElementById('new-username').value.trim();
		const usernameMsg = document.getElementById('username-msg');
		const koreanPattern = /^[가-힣]+$/;
		const invalidPattern = /[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ]/;
		if (!koreanPattern.test(username) || invalidPattern.test(username)) {
			usernameMsg.innerText = "한글만 입력 가능합니다.";
			usernameMsg.style.color = "red";
		} else {
			usernameMsg.innerText = "";
		}
	}

	function validateUserid() {
		const userid = document.getElementById('new-userid').value.trim();
		const idMsg = document.getElementById('id-msg');
		const idPattern = /^[a-zA-Z0-9]+$/;
		if (userid === "") {
			idMsg.innerText = "아이디를 입력해 주세요.";
			idMsg.style.color = "red";
		} else if (!idPattern.test(userid)) {
			idMsg.innerText = "아이디는 영문과 숫자만 허용됩니다.";
			idMsg.style.color = "red";
		} else {
			idMsg.innerText = "";
		}
	}
	function validateEmail() {
		const email = document.getElementById('new-email').value.trim();
		const emailMsg = document.getElementById('email-msg');
		const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
		if (!emailPattern.test(email)) {
			emailMsg.innerText = "유효한 이메일 주소를 입력해 주세요.";
			emailMsg.style.color = "red";
		} else {
			emailMsg.innerText = "";
		}
	}
	
	function sendVerificationCode() {
	    const email = document.getElementById("new-email").value.trim();
	    const emailMsg = document.getElementById("email-msg");

	    if (email === "") {
	        emailMsg.style.color = "red";
	        emailMsg.innerHTML = "이메일을 입력해 주세요.";
	        return;
	    }

	    $.ajax({
	        type: "POST",
	        url: "/member/sendVerificationCode.do",
	        data: { email: email },
	        success: function(res) {
	            if (res === "sent") {
	                emailMsg.style.color = "green";
	                emailMsg.innerHTML = "인증코드가 전송되었습니다.";
	            } else {
	                emailMsg.style.color = "red";
	                emailMsg.innerHTML = "이메일 전송에 실패했습니다. 다시 시도해주세요.";
	            }
	        },
	        error: function(err) {
	            console.log(err);
	            emailMsg.innerHTML = "오류가 발생했습니다. 다시 시도해주세요.";
	        }
	    });
	}

	function verifyCode() {
	    const email = document.getElementById("new-email").value.trim();
	    const code = document.getElementById("verify-code").value.trim();
	    const verifyMsg = document.getElementById("verify-msg");

	    if (code === "") {
	        verifyMsg.style.color = "red";
	        verifyMsg.innerHTML = "인증코드를 입력해 주세요.";
	        return;
	    }

	    $.ajax({
	        type: "POST",
	        url: "/member/verifyCode.do",
	        data: { email: email, code: code },
	        success: function(res) {
	            if (res === "verified") {
	                verifyMsg.style.color = "green";
	                verifyMsg.innerHTML = "이메일 인증이 완료되었습니다.";
	            } else {
	                verifyMsg.style.color = "red";
	                verifyMsg.innerHTML = "인증코드가 올바르지 않습니다.";
	            }
	        },
	        error: function(err) {
	            console.log(err);
	            verifyMsg.innerHTML = "오류가 발생했습니다. 다시 시도해주세요.";
	        }
	    });
	}

	function validatePhone() {
		const phone = document.getElementById('new-phone').value.trim();
		const phoneMsg = document.getElementById('phone-msg');
		const phonePattern = /^\d{2,3}-\d{3,4}-\d{4}$/;
		if (!phonePattern.test(phone)) {
			phoneMsg.innerText = "유효한 전화번호를 입력해 주세요. \n예) 010-1234-5678";
			phoneMsg.style.color = "red";
		} else {
			phoneMsg.innerText = "";
		}
	}

	function validatePassword() {
		const password = document.getElementById('new-password').value.trim();
		const passwordMsg = document.getElementById('password-msg');
		if (password.length < 8) {
			passwordMsg.innerText = "비밀번호는 최소 8자 이상이어야 하며, 대문자와 특수문자(!@)를 포함해야 합니다.";
			passwordMsg.style.color = "red";
		} else {
			passwordMsg.innerText = "";
		}
	}

	function validateConfirmPassword() {
		const password = document.getElementById('new-password').value.trim();
		const confirmPassword = document.getElementById('confirm-password').value
				.trim();
		const confirmPasswordMsg = document
				.getElementById('confirm-password-msg');
		if (password !== confirmPassword) {
			confirmPasswordMsg.innerText = "비밀번호가 일치하지 않습니다.";
			confirmPasswordMsg.style.color = "red";
		} else {
			confirmPasswordMsg.innerText = "";
		}
	}

	function validateAddress() {
		const address = document.getElementById('new-address').value.trim();
		const address1 = document.getElementById('new-address1').value.trim();
		const addressMsg = document.getElementById('address-msg');
		if (address === "" || address1 === "") {
			addressMsg.innerText = "주소를 입력해 주세요.";
			addressMsg.style.color = "red";
		} else {
			addressMsg.innerText = "";
		}
	}

	document.getElementById('new-username').addEventListener('input',
			validateUsername);
	document.getElementById('new-userid').addEventListener('input',
			validateUserid);
	document.getElementById('new-email').addEventListener('input',
			validateEmail);
	document.getElementById('new-phone').addEventListener('input',
			validatePhone);
	document.getElementById('new-password').addEventListener('input',
			validatePassword);
	document.getElementById('confirm-password').addEventListener('input',
			validateConfirmPassword);
	document.getElementById('new-address').addEventListener('input',
			validateAddress);
	document.getElementById('new-address1').addEventListener('input',
			validateAddress);
</script>
