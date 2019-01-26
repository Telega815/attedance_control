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
<html>
<head>
    <title>Вход</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/Welcome_page.css">
</head>
<body>
    <main>
        <c:set var="mainHeaderName" value="Учет сотрудников ННБ"/>
        <jsp:include page="parts/header.jsp"/>
    <%--<div id=header>--%>
       <%--<h1 id=mainName>Учет сотрудников ННБ</h1>--%>
    <%--</div>--%>
    <c:url value="/login" var="loginUrl"/>

    <form:form id="loginForm" action="${loginUrl}" method="post" modelAttribute="user">
        <form:input path="username" id="usernameInputField" class="InputStyle" form="loginForm" placeholder="Введите логин"/>
        <form:input path="pwd" id="passwordInputField" class="InputStyle" form="loginForm" placeholder="Введите пароль"/>
        <input id="submitStyle" value="Войти" type="submit"/>
    </form:form>
    </main>
</body>
</html>
