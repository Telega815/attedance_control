<%--
  Created by IntelliJ IDEA.
  User: Telega
  Date: 26.01.2019
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="mainHeaderName" value="Учет сотрудников ННБ"/>
<div id=header>
    <h1 id=mainName>${mainHeaderName}</h1>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <a href="${pageContext.request.contextPath}/admin">ADMIN</a>
        <a href="${pageContext.request.contextPath}/registration">REGISTRATION</a>
    </sec:authorize>
</div>