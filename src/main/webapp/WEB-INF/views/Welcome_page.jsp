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

    <style type="text/css">
        #tableEmployeeAttendance {
            border-collapse: collapse; /* Убираем двойные линии между ячейками */
            margin-left: auto;
            margin-right: auto;
        }
        #tableEmployeeAttendance TR {
            height: 21px;
        }
        #tableEmployeeAttendance TD, TH {
            padding: 3px; /* Поля вокруг содержимого таблицы */
            border: 1px solid black; /* Параметры рамки */
            width: 110px; text-align: center; height: 21px;
        }
        #tableEmployeeAttendance TH {
            background: #b0e0e6; /* Цвет фона */
        }
    </style>
    <table id="tableEmployeeAttendance"><caption align="top">Якубов</caption>
        <thead>
        <tr>
            <th>Дата</th>
            <th>Приход</th>
            <th>Уход</th>
            <th>Часы</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>1.12.2018</td>
            <td>09:00</td>
            <td>18:00</td>
            <td>9 ч. 0. мин</td>
        </tr>
        <tr>
            <td>2.12.2018</td>
            <td>09:00</td>
            <td>18:00</td>
            <td>9 ч. 0. мин</td>
        </tr>
        </tbody>
    </table>
</body>
</html>
