let oEditors = [];

smartEditor = function() {
    nhn.husky.EZCreator.createInIFrame({
        oAppRef: oEditors,
        elPlaceHolder: "editorTxt",
        sSkinURI: "/assets/smarteditor2/SmartEditor2Skin.html",
        fCreator: "createSEditor2",
        htParams: {
            bUseToolbar: true,
            bUseVerticalResizer: true,
            bUseModeChanger: true,
            fOnBeforeUnload: function() {},
            SE2M_Configuration: {
                Toolbar: {
                    bShowToolbar: true,
                    aAdditionalButtons: [
                        ["Bold", "Italic", "Underline", "StrikeThrough", "JustifyLeft", "JustifyCenter", "JustifyRight", "Outdent", "Indent", "ForeColor", "BackColor", "FontSize", "Table", "FullScreen", "HyperLink", "UnLink"]
                    ],
                    aDeleteButtons: ["PhotoUpload", "ImageEditor"] // 사진 업로드 버튼 제거
                }
            }
        }
    });
};

$(document).ready(function() {
    smartEditor();
});

function save() {
    oEditors.getById["editorTxt"].exec("UPDATE_CONTENTS_FIELD", []);
    return;
}
