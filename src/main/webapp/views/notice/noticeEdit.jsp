<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<%@ include file="/views/common/head.jsp"%>
<link rel="stylesheet" href="/assets/css/notice.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script type="text/javascript" src="/assets/smarteditor2/js/HuskyEZCreator.js" charset="utf-8"></script>
</head>
<body>
<%@ include file="/views/common/header.jsp"%>
<%@ include file="/views/common/nav.jsp"%>
<main class="notice-main">
    <section class="container mt-4 d-flex justify-content-center align-items-center">
        <div class="card text-center" style="width: 100%; max-width: 800px;">
            <form id="editForm" action="/notice/edit.do" method="post" enctype="multipart/form-data">
                <input type="hidden" name="boardno" value="${result.noticeNo}" />
                <div class="card-header">
                    <label for="title">제목:</label>
                    <input type="text" name="title" style="width: 100%;" value="${result.noticeTitle}" class="form-control"/>
                </div>
                <div class="card-body">
                    <div class="d-flex justify-content-center mb-3">
                        <div class="mx-3">
                            작성일: <span id="noticeDate">${result.noticeDate}</span>
                        </div>
                        <div class="mx-3">
                            조회수: <span id="noticeView">${result.noticeView}</span>
                        </div>
                    </div>
                    <hr>
                    <div id="smarteditor" class="d-flex justify-content-center">
                        <textarea id="editorTxt" name="content" rows="4" style="width: 100%;" class="form-control" required>${result.noticeContent}</textarea>
                    </div>
                    <div class="form-group mt-3">
                        <label for="uploadFile">파일 첨부:</label>
                        <input type="file" name="uploadFile" class="form-control">
                        <c:if test="${not empty result.fileName}">
                            <div class="mt-3">
                                <p>현재 파일: <img src="${result.filePath}${result.fileName}" alt="이미지" style="max-height: 30%;"></p>
                                <p><a href="${result.filePath}${result.fileName}" target="_blank">${result.fileName}</a></p>
                                <button type="button" class="btn btn-danger mx-2" onclick="deleteFile(${result.noticeNo})">파일 삭제</button>
                            </div>
                        </c:if>
                    </div>
                </div>
                <div class="d-flex justify-content-center" style="margin-bottom: 20px;">
                    <button class="btn btn-secondary mx-2" type="button" onclick="window.history.back()">뒤로가기</button>
                    <c:if test="${sessionScope.userType == 'admin'}">
                        <button type="submit" class="btn btn-primary mx-2" onclick="submitContents(this)">수정</button>
                    </c:if>
                </div>
            </form>
        </div>
    </section>
</main>
<%@ include file="/views/common/footer.jsp"%>
<script src="/assets/js/main.js"></script>
<script src="/assets/js/notice.js"></script>
<script type="text/javascript">
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
    });
</script>
</body>
</html>
