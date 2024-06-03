// assets/js/notice.js

$(document).ready(function() {
    var oEditors = [];
    nhn.husky.EZCreator.createInIFrame({
        oAppRef: oEditors,
        elPlaceHolder: "editorTxt",
        sSkinURI: "/assets/smarteditor2/SmartEditor2Skin.html",
        fCreator: "createSEditor2"
    });

    window.submitContents = function(elClickedObj) {
        oEditors.getById["editorTxt"].exec("UPDATE_CONTENTS_FIELD", []); // 에디터의 내용이 textarea에 적용됩니다.
        elClickedObj.form.submit();
    }

    window.deleteFile = function(boardNo) {
        if (confirm("파일을 삭제하시겠습니까?")) {
            $.ajax({
                url: '/notice/deleteFile.do',
                type: 'POST',
                data: { boardNo: boardNo },
                success: function(response) {
                    alert("파일이 삭제되었습니다.");
                    location.reload();
                },
                error: function() {
                    alert("파일 삭제에 실패하였습니다.");
                }
            });
        }
    }
});
