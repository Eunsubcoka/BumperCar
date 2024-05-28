<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
<%@ include file="/views/common/head.jsp"%>
<link rel="stylesheet" href="/assets/css/notice.css">
<script type="text/javascript" src="/assets/smarteditor2/js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript" src="/assets/js/common/smarteditor.js" charset="utf-8"></script>

</head>
<body>

    <%@ include file="/views/common/header.jsp"%>

    <%@ include file="/views/common/nav.jsp"%>
    <main class="notice-main">

        <h1 class="text-center">공지사항</h1>
        <hr>

        <section id="post-form" class="d-flex justify-content-center">
            <div class="col-md-8">
                <h2 class="text-center">새 글 작성</h2>
                <form action="/notice/enroll.do" method="POST"
                    enctype="multipart/form-data" class="mt-3">
                 <input type="hidden" name="userType" value="${sessionScope.userType}">

                    <div class="form-group">
                        <label for="title">제목:</label>
                        <input type="text" id="title" class="form-control" name="title" required  style=" font-size: 20px; margin-top: 10px; margin-bottom: 10px;">
                    </div>
                    

                    <div class="form-group">
                        <label for="content"></label>
                        <div id="smarteditor">
                            <textarea id="editorTxt" name="content" style = "width : 80%" rows="4" placeholder="내용을 입력해주세요" class="form-control" required></textarea>
                        </div>
                    </div>

                    <!-- <input type="file" name="file"> -->
                    <div class="form-group text-center">
                        <button type="button" class="btn btn-secondary mx-2" onclick="window.history.back()">뒤로가기</button>
                        <button type="submit" class="btn btn-primary" onclick="save()">작성</button>
                    </div>
                </form>
            </div>
        </section>

    </main>

    <%@ include file="/views/common/footer.jsp"%>

    <script src="/assets/js/bootstrap.bundle.min.js"></script>
    <script src="/assets/js/notice.js"></script>

</body>
</html>
