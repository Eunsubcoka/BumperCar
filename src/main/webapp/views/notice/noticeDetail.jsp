<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/views/common/head.jsp"%>
<link rel="stylesheet" href="/assets/css/notice.css">
</head>
<body>
    <%@ include file="/views/common/header.jsp"%>
    <%@ include file="/views/common/nav.jsp"%>
    <main class="notice-main">
        <h1>공지사항</h1>
        <hr>
        <section class="container">
            <div class="card text-center" style="height: 100%">
                <form action="/notice/delete.do" method="POST">
                    <input type="hidden" name="noticeNo" value="${result.noticeNo}">
                    <input type="hidden" name="fileNo" value="${result.fileNo}">
                    <input type="hidden" name="fileName" value="${result.fileName}">

                    <div class="card-content-wrapper">
                        <div class="card-header" style="background-color: white;">
                            <h2 id="noticeTitle">${result.noticeTitle}</h2>
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
                            <div style="margin-top: 20px; margin-bottom: 20px;">
                                <c:if test="${not empty result.fileName and not empty result.filePath}">
                                    <img src="${result.filePath}${result.fileName}" alt="이미지" style="max-height: 50%;">
                                </c:if>
                                <p class="card-text">${result.noticeContent}</p>
                                <br><br><br>
                            </div>
                        </div>
                        <div class="card-footer d-flex justify-content-center" style="background-color: white;">
                            <button type="button" class="btn btn-secondary mx-2" onclick="window.history.back()">뒤로가기</button>
                            <c:if test="${sessionScope.userType == 'admin'}">
                                <button type="button" class="btn btn-primary mx-2" onclick="location.href='/tastyForm/noticeEditForm.do?boardno=${result.noticeNo}'">수정</button>
                                <button type="submit" class="btn btn-danger mx-2">삭제</button>
                            </c:if>
                        </div>
                    </div>
                </form>
            </div>
        </section>

        <!-- 게시글 목록 추가 -->
        <div class="notice-container">
            <table class="table table-hover notice-table" style="margin-top: 10px;">
                <thead>
                    <tr>
                        <th scope="col" class="num">번호</th>
                        <th scope="col" class="title title-center">제목</th>
                        <th scope="col" class="date">작성일</th>
                        <th scope="col" class="views">조회수</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty list}">
                            <tr>
                                <td colspan="5">등록된 글이 없습니다.</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:set var="row" value="${fn:length(list)}" />
                            <c:forEach var="item" items="${list}" varStatus="status">
                                <c:if test="${status.index < 5}">
                                    <tr onclick="location.href='/notice/detail.do?boardno=${item.noticeNo}'">
                                        <td scope="row" class="num">${row - status.index}</td>
                                        <td class="title title-padding">${item.noticeTitle}</td>
                                        <td class="date">${item.noticeDate}</td>
                                        <td class="views">${item.noticeView}</td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </main>
    <%@ include file="/views/common/footer.jsp"%>
    <script src="/assets/js/bootstrap.bundle.min.js"></script>
    <script src="/assets/js/main.js"></script>
</body>
</html>
