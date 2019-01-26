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
    <title>Registration</title>
</head>
<body>
    <form:form id="RegistrationForm" class="inputForm" method="POST" modelAttribute="user">
        <form:input autocomplete="off" path="username" form="RegistrationForm" id="usernameInputField" type="text" placeholder="Логин" />
        <form:input autocomplete="off" path="pwd" form="RegistrationForm" id="passwordInputField" type="password" placeholder="Пароль" />
        <input form="RegistrationForm" id="submitRegForm" type="submit" value="Создать">
    </form:form>
</body>
</html>
