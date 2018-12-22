<%--
  Created by IntelliJ IDEA.
  User: Telega
  Date: 25.10.2018
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Private page!</title>
    <c:url value="/logout" var="logoutUrl"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/MainPage.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.3.1.js" ></script>
    <sec:csrfMetaTags />
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/csrfHeader.js" ></script>
</head>
<body>
<header></header>
<main>
    <div id=header>
        <h1 id=mainName>Учет сотрудников ННБ</h1>
    </div>
    <div id=mainContainer>
        <div id=leftBlock>
            <div class="search">
                <input id="searchInput" type="search" name="searchInput" placeholder="Введите ФИО">
                <img id="searchImage" class="searchImage" src="${pageContext.request.contextPath}/resources/media/MainPage/search.png">
            </div>
            <ul id="departments" class="map">
                ${depNames}
            </ul>
        </div>


        <div id="rightBlockWraper">
            <div id="rightBlockHeader">
                <h3 id="departmentName">Автоматизация</h3>
            </div>
            <div class="sort">
                ${selectDate}
            </div>
            <div id=rightBlock>
            </div>
        </div>
    </div>
</main>
<footer></footer>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/MainPage.js"></script>

</body>
</html>
