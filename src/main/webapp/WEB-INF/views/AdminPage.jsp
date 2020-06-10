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
    <title>Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/AdminPage.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.3.1.js" ></script>
    <sec:csrfMetaTags />
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/csrfHeader.js" ></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/AdminPage.js" ></script>
</head>
<body>
<main>
    <c:set var="mainHeaderName" value="Админка"/>
    <jsp:include page="parts/header.jsp"/>
        <div class="AdminStyleP"><p>Добавление отдела</p></div>
        <div id="idcreateDepartment">
            <p>Новый отдел:</p>
            <input type="text" id="newDepName" placeholder="Name">
            <input id="createButton" onclick="createDepartment()" type="button" value="Create">
        </div>
    <div class="AdminStyleP"><p>Регистрацтя сотрудника</p></div>
    <div id="idcreateEmployee">
        <div id="idImputCreateEmployeeName">
            <p></p>
            <p>Фамилия:</p>
            <p>Имя:</p>
            <p>Отчество:</p>
            <p>Ключ:</p>
        </div>
        <div id="idImputCreateEmployee">
            <select class="SelectDepartmentClass" id="selectDepartment"></select>

            <input class="AdminEmployeeInput" type="text" >
            <input class="AdminEmployeeInput" type="text" >
            <input class="AdminEmployeeInput" type="text" >
            <input class="AdminEmployeeInput" type="number" >
            <input id="createEmployeeButton" onclick="createEmployee()" type="button" value="Create">
        </div>
    </div>
    <p id="successLabel" style="display: none">Success!!!</p>

    <div class="AdminStyleP"><p>Изменение данных</p></div>
<div id="editIdCountainer">
    <div id="editId">
    <select onchange="onchangeTest(event)" id="selectDepartment2" class="SelectDepartmentClass" ></select>
        <div>
            <ul id="adminEmployeeList">
            </ul>
        </div>

        <div id="idDepartmentChange">
            <p>Отдел:</p>
            <select id="selectDepartment3" class="SelectDepartmentClass" ></select>
            <input id="changeDepartmentButtonK" onclick="changeDepartment()" type="button" value="Save">
        </div>

        <div id="idImputCreateEmployeeK">
            <p>Ключ:</p>
            <input id="inputKeyField"  type="number" >
            <input id="createEmployeeButtonK" onclick="saveNewKey()" type="button" value="Save">
        </div>

        <form id="ifFormDay">
            <span>Посещаемость за <input type="date" name="calendar" id="inputAttendanceOfDay" ></span>
            <input id="ifFormDayButton" type="button" onclick="getAttendanceOfDay()" value="show">
        </form>
        <div id="timeEdit">
            <ul id="adminAttendanceOfDay"></ul>
            <input id="idDeleteButton" type="button" onclick="deleteAttendanceOfDay()" value="Удалить">
        </div>
    </div>
    <div id="idDivInput">

    <form>
        <span>с <input type="date" name="calendar" id="inputEmpMin" ></span>
        <span>по <input type="date" name="calendar" id="inputEmpMax" ></span>
        <input class="ButtonButton" type="button" onclick="getAttendance()" value="show">
    </form>

    <div>
        <input type="datetime-local" id="adminAddAttendanceInput">
        <input class="ButtonButton" type="button" value="write" onclick="writeAttendance()">
    </div>
         <div id="adminAttendanceList"></div>
    </div>
</div>
    <div id="idcheckmark">
    <div class="check_mark">
        <div class="hide sa-icon sa-success animate">
            <span class="sa-line sa-tip animateSuccessTip"></span>
            <span class="sa-line sa-long animateSuccessLong"></span>
            <div class="sa-placeholder"></div>
            <div class="sa-fix"></div>
        </div>
    </div>
    </div>

    <div class="AdminStyleP"><p>Суперчекалка</p></div>

    <div>
        <br>
        <span style="margin-right: 10px" class="Unselected" id="spanAutoEmpName">Unselected</span>
        <br>
        <p style="margin-right: 10px">Отмечать автоматом:</p>
        <input style="margin-right: 10px" id="inputAutoCheck"  type="checkbox" >
        <input style="background-color: #4CAF50; border: 1px solid black" id="buttonSaveAutoCheck" onclick="autoCheck(event)" type="button" value="Save">
<%--        <input type="button" id="ssssssss" value="ass">--%>
        <br>
        <br>
        <br>
    </div>

</main>
</body>
</html>
