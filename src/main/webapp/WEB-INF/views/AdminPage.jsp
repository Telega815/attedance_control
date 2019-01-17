<%--
  Created by IntelliJ IDEA.
  User: Telega
  Date: 17.01.2019
  Time: 14:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>AdminPage</title>
    <%--<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/MainPage.css">--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.3.1.js" ></script>
    <sec:csrfMetaTags />
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/csrfHeader.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/AdminPage.js" ></script>
</head>
<body>
        <div>
            <input type="text" id="newDepName">
            <input onclick="createDepartment()" type="button" value="new">
        </div>
        <div>
            <select onchange="onchangeTest(event)" id="selectDepartment">
            </select>
        </div>

        <div>
            <input class="AdminEmployeeInput" type="text" placeholder="surname">
            <input class="AdminEmployeeInput" type="text" placeholder="name">
            <input class="AdminEmployeeInput" type="text" placeholder="patronymic">
            <input class="AdminEmployeeInput" type="number" placeholder="key">
            <input onclick="createEmployee()" type="button" value="create">
        </div>
    <p id="successLabel" style="display: none">Success!!!</p>


        <div>
            <ul id="adminEmployeeList" style="border: 2px solid red; width: 400px">
            </ul>
        </div>

        <form>
            <span>с <input type="date" name="calendar" id="inputEmpMin" ></span>
            <span>по <input type="date" name="calendar" id="inputEmpMax" ></span>
        </form>

        <div style="border: 2px solid red; width: 800px" id="adminAttendanceList">

        </div>

</body>
</html>
