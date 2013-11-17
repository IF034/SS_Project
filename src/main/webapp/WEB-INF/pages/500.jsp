<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>
        System error
    </title>
    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet">
    <jsp:include page="bootstrap.jsp"/>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="row">
    <div class="col-md-3"></div>
    <div class="col-md-6">
        <div class="errorHead">
            We are so sorry but something is wrong
        </div>
    </div>
    <div class="col-md-3"></div>
</div>
<div class="errorText">
    <span>Error message:</span> <span>${exception.message}</span>
</div>
</body>
</html>