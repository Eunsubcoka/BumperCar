<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/common/head.jsp"%>
 <link rel="stylesheet" href="/assets/css/reservation.css">
 <script type="text/javascript" src="/assets/js/reservation.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript"
        src="https://cdn.jsdelivr.net/npm/@emailjs/browser@4/dist/email.min.js">
</script>
<script type="text/javascript">
   (function(){
      emailjs.init("S6EHWOxVhPquonzsg");
   })();
</script>

<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>

</head>
<body>
	<%@ include file="/views/common/header.jsp"%>
	<%@ include file="/views/common/nav.jsp"%>


<form class="quick-reservation" action="/reservation/resEnroll.do" method = "get">
   
<div class="container">
   <header class="quick-reservation__header">
      <h2 class="title">
         가계명
      </h2>
      
      <div class="close-icon" onclick="location.href='/views/restaurant/restaurantDetail.jsp'">
         <span></span>
         <span></span>
      </div>
   </header>
   <div class="quick-reservation__form">
      <section class="form__content">
      <div class="row-wrapper">
      <div class="ele date">
               <label for="date">날짜</label>
			<input type="text" id="datepicker" name="datepicker">
            </div>
            </div>
         <div class="row-wrapper">
            <div class="ele first-name">
               <label for="firstName">인원</label>
               <input type="text" name="headCount" placeholder="" id="headCount">
            </div>
            <div class="ele first-name">
               <label for="firstName">예약자명</label>
               <input type="text" name="name" placeholder="" id="firstName">
            </div>
         </div>
         
         <div class="row-wrapper">
            <div class="ele email-address">
               <label for="emailAddress">이메일</label>
               <input type="text" name="email" value="" placeholder="" id="emailAddress">
            </div>
            <div class="ele phone-number">
               <label for="phoneNumber">전화번호</label>
               <input type="text" name="phone" value="" placeholder="" id="phoneNumber">
            </div> 
            
         </div>
         
         <div class="row-wrapper more-options">

            <label for="single" class="radio">
               <input type="radio" name="plan" id="single" class="hidden"/>
               <span class="label"></span>Single
            </label>
            
            <label for="group" class="radio">
               <input type="radio" name="plan" id="group" class="hidden"/>
               <span class="label"></span>Group
            </label>
            
            
         </div>
         
      </section>
   </div>
   
   <div class="reservation-info">
      <div class="ele data">
         <h4 class="data__head">날짜</h4>
         <p class="data__description" id="dateOut">Deluxe</p>
      </div>
      <div class="ele data">
         <h4 class="data__head">날짜</h4>
         <p class="data__description">May 28 - Jun 2</p>
      </div>
      <div class="ele data">
         <h4 class="data__head">인언</h4>
         <p class="data__description" id="countOut" >May 28 - Jun 2</p>
      </div>
      <div class="ele data">
         <h4 class="data__head">Price</h4>
         <p class="data__description">$45/night<br> Total $270</p>
      </div>
   </div>
   
   <footer class="form__footer">
      <div class="footer-wrapper">
         <input type="submit" value="예약하기" onclick="sendEmail()" class="">
         <!-- <input type="button" value="이메일" onclick="sendEmail()"> -->
      </div>
   </footer>
</div>
   
</form>

<p class="after" style="display: none; color: #777; font-weight: 200;">Reload the browser window everytime you close the reservation form to show up again.</p>




	<%@ include file="/views/common/footer.jsp"%>
<div class="bg"></div>

<script>
//• Based on & inspired from dribbble: https://dribbble.com/shots/4630196-Quick-Reservation-UI-Design

//• JS Code Reference: http://jsfiddle.net/BB3JK/47/

//• May 30, 2018 
//• Front-end Daily Practice
//• frontenddaily.blogspot.com

//CustoSelectm 


$(function() {
$('.quick-reservation').hide();
$('.quick-reservation').fadeIn(1000);
$('.close-icon').click(function (e) {
   $('.quick-reservation').fadeOut(500);
   e.stopPropagation();
   $('.after').show(1000);
   e.stopPropagation();
});
});

 
 
const sendEmail = () => {
    let templateParams  = {
        name : document.getElementById('firstName').value,
        phone : document.getElementById('phoneNumber').value,
        email : document.getElementById('emailAddress').value,
        headCount : document.getElementById('headCount').value,
        date : document.getElementById('datepicker').value,
    }
    console.log(templateParams);
    emailjs.send('service_wg52763', 'template_0490si8', templateParams).then(function(response){
        console.log('Success!', response.status, response.text);
    }, function(error){
        console.log('Failed...', error);
    })
}

 flatpickr("#datepicker", {
	  enableTime: true,
	  dateFormat: "Y-m-d H:i",
	  minDate: "today",
	  enableTime: true,
	    minTime: "09:00",
	    maxTime: "23:30"
	});
 
 
 
</script>


</body>
</html>