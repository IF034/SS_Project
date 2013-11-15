<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>
        ${user.name}
    </title>
    <link href="${pageContext.request.contextPath}/resources/css/registration.css" rel="stylesheet">
    <jsp:include page="bootstrap.jsp"/>
</head>
<body>
<jsp:include page="header.jsp"/>

<form:form id="user" modelAttribute="user" cssClass="form-signin">
    <p><form:label path="name" cssClass="form-signin-heading">Name</form:label>
        <form:label path="name" cssClass="form-control">${user.name}</form:label>


    <p><form:label path="surname" cssClass="form-signin-heading">Surname</form:label>
        <form:label path="surname" cssClass="form-control">${user.surname}</form:label>


    <p><form:label path="nickname" cssClass="form-signin-heading">Nickname</form:label>
        <form:label path="nickname" cssClass="form-control">${user.nickname}</form:label>


    <p><form:label path="mail" cssClass="form-signin-heading">Email</form:label>
        <form:label path="mail" cssClass="form-control">${user.mail}</form:label>


</form:form>

</body>
</html>