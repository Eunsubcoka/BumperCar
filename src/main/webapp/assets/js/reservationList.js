function openPopup(resNo, userName, phone, restaurantName, resPhone, date, headCount, paymentStatus, restaurantNo) {
    document.getElementById('popupResNo').innerText = resNo;
    document.getElementById('popupUserName').innerText = userName;
    document.getElementById('popupPhone').innerText = phone;
    document.getElementById('popupRestaurantName').innerText = restaurantName;
    document.getElementById('popupRestaurantPhone').innerText = resPhone;
    document.getElementById('popupDate').innerText = date;
    document.getElementById('popupHeadCount').innerText = headCount;
    document.getElementById('popupPaymentStatus').innerText = paymentStatus;
    document.getElementById('popupRestaurantNo').value = restaurantNo;

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

function deleteReservation() {
    const resNo = document.getElementById('popupResNo').innerText;

    fetch('/reservation/delete.do', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: `resNo=${resNo}`
    }).then(response => {
        if (response.ok) {
            alert('예약이 삭제되었습니다.');
            location.reload();
        } else {
            alert('예약 삭제에 실패했습니다.');
        }
    }).catch(error => {
        console.error('Error:', error);
    });
}

function gotoRestaurant() {
    const restaurantNo = document.getElementById('popupRestaurantNo').value;
    window.location.href = `/restaurantDetail.do?restaurantId=${restaurantNo}`;
}
