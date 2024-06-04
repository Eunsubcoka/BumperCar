function initializeSmartEditor(editorId) {
    if (typeof nhn !== 'undefined' && nhn.husky && nhn.husky.EZCreator) {
        var oEditors = [];
        nhn.husky.EZCreator.createInIFrame({
            oAppRef: oEditors,
            elPlaceHolder: editorId,
            sSkinURI: "/assets/smarteditor2/SmartEditor2Skin.html",
            fCreator: "createSEditor2"
        });

        return oEditors;
    } else {
        console.error("SmartEditor2 라이브러리를 찾을 수 없습니다.");
        return null;
    }
}

function submitContents(oEditors, elClickedObj) {
    oEditors.getById[elClickedObj.id].exec("UPDATE_CONTENTS_FIELD", []); // 에디터의 내용이 textarea에 적용됩니다.
    elClickedObj.form.submit();
}

function deleteFile(boardNo) {
    $.ajax({
        url: '/notice/deleteFile.do',
        type: 'POST',
        data: { boardno: boardNo },
        success: function(response) {
            if (response === 'success') {
                alert('파일이 삭제되었습니다.');
                location.reload(); // 페이지를 새로고침하여 변경 사항을 반영
            } else {
                alert('파일 삭제에 실패했습니다.');
            }
        },
        error: function() {
            alert('서버와의 통신 중 오류가 발생했습니다.');
        }
    });
}
