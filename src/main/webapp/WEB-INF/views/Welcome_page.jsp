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
    <title>Change this!</title>
</head>
<body>
    <c:url value="/login" var="loginUrl"/>

    <form:form id="loginForm" action="${loginUrl}" method="post" modelAttribute="user">
        <form:input path="username" id="usernameInputField" form="loginForm" placeholder="Введите логин"/>
        <form:input path="pwd" id="passwordInputField" form="loginForm" placeholder="Введите пароль"/>
        <input type="submit"/>
    </form:form>

    <a href="${pageContext.request.contextPath}/registration">reg</a>
    <p>Change this!</p>

</body>
</html>
