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
    <title>Учет сотрудников ННБ</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/MainPage.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.3.1.js" ></script>
    <sec:csrfMetaTags />
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/csrfHeader.js" ></script>
</head>
<body>
<header></header>
<main>

    <c:set var="mainHeaderName" value="Учет сотрудников ННБ"/>
    <jsp:include page="parts/header.jsp"/>
    <%--<div id=header>--%>
        <%--<h1 id=mainName>Учет сотрудников ННБ</h1>--%>
    <%--</div>--%>
    <div id=mainContainer>
        <div id=leftBlock>
            <div class="search">
                <input id="searchInput" oninput="searchChanged(event)" type="search" name="searchInput" placeholder="Введите ФИО">
                <img id="searchImage" class="searchImage" src="${pageContext.request.contextPath}/resources/media/MainPage/search.png">
            </div>
            <ul id="searchList" class="map" >

            </ul>
            <ul id="departments" class="map">
                ${depNames}
            </ul>
        </div>


        <div id="rightBlockWraper">
            <div id="rightBlockHeader">
                <h3 id="departmentName">Автоматизация</h3>
            </div>
            <div id="rbMiddle">
                <div class="sort" id="blockSelectDateDep">
                    ${selectDate}
                </div>
                <div class="sortUser" id="blockSelectDateEmp">
                    <form>
                        <span>с <input type="date" name="calendar" id="inputEmpMin" ></span>
                        <span>по <input type="date" name="calendar" id="inputEmpMax" ></span>
                    </form>
                </div>
                <button id="showButton" class="MyButton" onclick="showButtonClick()">Показать</button>
                <button id="printButton" class="MyButton" onclick="printButtonClick()">Печать</button>
            </div>
            <div id=rightBlock>
            </div>
        </div>
    </div>
</main>
<footer></footer>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mainPage/MainPage.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mainPage/ajaxContentRequest.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mainPage/select.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mainPage/search.js"></script>

</body>
</html>
