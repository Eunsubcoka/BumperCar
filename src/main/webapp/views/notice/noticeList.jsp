<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>
    <%@ include file="/views/common/head.jsp"%>
    <link rel="stylesheet" href="/assets/css/notice.css">
</head>
<body>
    <%@ include file="/views/common/header.jsp"%>
    <%@ include file="/views/common/nav.jsp"%>
    <main class="notice-main">
        
        <div class="notice-header">
            <h1>공지사항</h1>
            <div class="search-and-button">
                <c:choose>
                    <c:when test="${sessionScope.userType == 'admin'}">
                        <button onclick="window.location.href = '/tastyForm/noticeEnrollForm.do'"
                            style="background-color : #ebb842;">등록</button>
                    </c:when>
                </c:choose>
                <form class="search-form" action="/notice/list.do" method="GET">
                    <input type="hidden" name="cpage" value="1">
                    <select class="form-select" id="inputGroupSelect02" name="category"
                        style="width: 100px; height: 46px; flex: 0 0 auto;">
                        <option value="noticeTitle" selected>제목</option>
                        <option value="noticeContent">내용</option>
                    </select>
                    <input type="text" style="width: 300px; flex: 0 0 auto;"
                        class="form-control" name="search-text" placeholder="검색어를 입력해주세요."
                        aria-label="Recipient's username" aria-describedby="button-addon2">
                    <button class="btn btn-outline-secondary" type="submit"
                        id="button-addon2" style="height: 46px;">검색</button>
                </form>
            </div>
        </div>
        <hr>
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
                            <c:forEach var="item" items="${list}">
                                <tr onclick="location.href='/notice/detail.do?boardno=${item.noticeNo}'">
                                    <td scope="row" class="num">${row}</td>
                                    <td class="title title-padding">${item.noticeTitle}</td>
                                    <td class="date">${item.noticeDate}</td>
                                    <td class="views">${item.noticeView}</td>
                                </tr>
                                <c:set var="row" value="${row-1}" />
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
        <nav aria-label="Page navigation example" style="background-color: white;">
            <ul class="pagination" style="justify-content: center;">
                <c:choose>
                    <c:when test="${pi.cpage == 1}">
                        <li class="page-item" style="margin-right: 0px"><a
                            class="page-link" href="#" aria-label="Previous"> <span
                                aria-hidden="true">&laquo;</span>
                        </a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item" style="margin-right: 0px"><a
                            class="page-link" href="/notice/list.do?cpage=${pi.cpage-1}&category=noticeTitle&search-text="
                            aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
                        </a></li>
                    </c:otherwise>
                </c:choose>
                <c:forEach var="page" begin="${pi.startPage}" end="${pi.endPage}">
                    <li class="page-item" style="margin-right: 0px"><a
                        class="page-link" href="/notice/list.do?cpage=${page}&category=noticeTitle&search-text=">${page}</a></li>
                </c:forEach>
                <c:choose>
                    <c:when test="${pi.cpage == pi.maxPage}">
                        <li class="page-item" style="margin-right: 0px"><a
                            class="page-link" href="#" aria-label="Next"> <span
                                aria-hidden="true">&raquo;</span>
                        </a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item" style="margin-right: 0px"><a
                            class="page-link" href="/notice/list.do?cpage=${pi.cpage+1}&category=noticeTitle&search-text="
                            aria-label="Next"> <span aria-hidden="true">&raquo;</span>
                        </a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>
    </main>
    <%@ include file="/views/common/footer.jsp"%>
    <script src="/assets/js/bootstrap.bundle.min.js"></script>
    <script src="/assets/js/notice.js"></script>
</body>
</html>
