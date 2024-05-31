function getImageFiles(event){
    const maxImages = 3; // 최대 이미지 개수

    // 이미지 개수 제한 확인
    if (event.target.files.length > maxImages) {
        alert(`이미지는 ${maxImages}장까지 업로드할 수 있습니다.`);
        return;
    }

    for(let image of event.target.files){
        // 이미지 개수가 최대 개수에 도달했을 경우 추가 중단
        if (document.querySelectorAll("div#image_container img").length >= maxImages) {
            alert(`이미지는 ${maxImages}장까지 업로드할 수 있습니다.`);
            return;
        }

        let img = document.createElement("img");
        const reader = new FileReader();
        reader.onload = function(event){
            img.setAttribute("src", event.target.result);
        }
        reader.readAsDataURL(image);
        document.querySelector("div#image_container").appendChild(img);
    }
}