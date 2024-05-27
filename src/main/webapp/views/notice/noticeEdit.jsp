<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
<%@ include file="/views/common/head.jsp"%>

<script type="text/javascript" src="/assets/smarteditor2/js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript" src="/assets/js/common/smarteditor.js" charset="utf-8"></script>

</head>
<body>

    <%@ include file="/views/common/header.jsp"%>

    <nav class="unique-nav">
        <a href="#">카테고리</a> <a href="#">공지사항</a>
    </nav>
    <main class="notice-main">

        <section class="container mt-4 d-flex justify-content-center align-items-center" style="height: 70vh;">
            <div class="card text-center" style="width: 100%; max-width: 800px;">
                <form action="/notice/edit.do" method="post">

                    <input type="hidden" name="boardNo" value="${result.boardNo }" />

                    <div class="card-header">
                        <label for="title">제목:</label>
                        <input type="text" name="title" style="width: 100%;" value="${result.boardTitle}" class="form-control"/>
                    </div>
                    <div class="card-body">
                        <div class="d-flex justify-content-center mb-3">

                            <div class="mx-3">
                                작성일: <span id="fb-date">${result.boardIndate}</span>
                            </div>
                            <div class="mx-3">
                                조회수: <span id="fb-views">${result.boardViews}</span>
                            </div>
                        </div>
                        <hr>
                        <div id="smarteditor" class="d-flex justify-content-center">
                            <textarea id="editorTxt" name="content" rows="4" style="width: 100%;" class="form-control" required>${result.boardContent}</textarea>
                        </div>
                    </div>
                    <div class="card-footer d-flex justify-content-center">
                        <button class="btn btn-secondary mx-2" type="button" onclick="window.history.back()">뒤로가기</button>
                        <c:if test="${sessionScope.userNo == result.memberNo}">
                            <button type="submit" class="btn btn-primary mx-2" onclick="save()">수정</button>
                        </c:if>
                    </div>
                </form>
            </div>
        </section>

    </main>

    <%@ include file="/views/common/footer.jsp"%>

    <script src="/assets/js/bootstrap.bundle.min.js"></script>
    <script src="/assets/js/main.js"></script>

</body>
</html>
