// assets/js/notice.js

var oEditors = [];
nhn.husky.EZCreator.createInIFrame({
    oAppRef: oEditors,
    elPlaceHolder: "editorTxt",
    sSkinURI: "/assets/smarteditor2/SmartEditor2Skin.html",
    fCreator: "createSEditor2"
});

function submitContents(elClickedObj) {
    oEditors.getById["editorTxt"].exec("UPDATE_CONTENTS_FIELD", []); // 에디터의 내용이 textarea에 적용됩니다.
    elClickedObj.form.submit();
}

function deleteFile(boardNo) {
    if (confirm("파일을 삭제하시겠습니까?")) {
        console.log("AJAX 요청을 전송합니다. boardNo:", boardNo); // 콘솔 로그 추가
        $.ajax({
            url: '/notice/deleteFile.do',
            type: 'POST',
            data: { boardNo: boardNo },
            success: function(response) {
                console.log("AJAX 요청이 성공적으로 처리되었습니다."); // 콘솔 로그 추가
                alert("파일이 삭제되었습니다.");
                location.reload();
            },
            error: function() {
                console.log("AJAX 요청이 실패하였습니다."); // 콘솔 로그 추가
                alert("파일 삭제에 실패하였습니다.");
            }
        });
    }
}
