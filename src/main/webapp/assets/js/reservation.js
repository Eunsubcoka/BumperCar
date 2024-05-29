const date = document.getElementById("dateOut");
const count = document.getElementById("countOut");


// 날짜
$("#datepick").on("propertychange change keyup paste input", function() {
    var currentVal = $(this).val();
    if(currentVal == oldVal) {
        return;
    }
    oldVal = currentVal;
 	console.log(oldVal);
	date.value = oldVal;
    alert("changed!");
});
// 날짜
$("#headCount").on("propertychange change keyup paste input", function() {
    var currentVal = $(this).val();
    if(currentVal == oldVal) {
        return;
    }
 
    oldVal = currentVal;
	count.textContent = oldVal;
    alert("changed!");
});
