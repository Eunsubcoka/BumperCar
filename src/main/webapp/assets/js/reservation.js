const dateOut = document.getElementById("dateOut");
const countOut = document.getElementById("countOut");
const nameOut = document.getElementById("nameOut");
const phoneOut = document.getElementById("phoneOut");




function printName()  {
let name = document.getElementById('firstName').value; 
 nameOut.innerHTML = name;
}
function printPhone()  {  
const phone = document.getElementById('phoneNumber').value; 
phoneOut.innerHTML = phone;

}
function printCount()  {  
const count = document.getElementById('headCount').value; 
countOut.innerHTML = count;
console.log("함수");
}
function printDate()  {  
const date = document.getElementById('datepicker').value; 
dateOut.innerHTML = date;
}






