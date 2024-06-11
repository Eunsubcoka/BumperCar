function openPopup(resNo, userName, phone, restaurantName, resPhone, date, headCount, paymentStatus) {
    document.getElementById('popupResNo').innerText = resNo;
    document.getElementById('popupUserName').innerText = userName;
    document.getElementById('popupPhone').innerText = phone;
    document.getElementById('popupRestaurantName').innerText = restaurantName;
    document.getElementById('popupRestaurantPhone').innerText = resPhone;
    document.getElementById('popupDate').innerText = date;
    document.getElementById('popupHeadCount').innerText = headCount;
    document.getElementById('popupPaymentStatus').innerText = paymentStatus;

    if (paymentStatus === 'Y') {
        document.getElementById('popupPaymentStatus').style.color = 'blue';
    } else {
        document.getElementById('popupPaymentStatus').style.color = 'red';
    }

    document.getElementById('popup').style.display = 'block';
}

function closePopup() {
    document.getElementById('popup').style.display = 'none';
}
