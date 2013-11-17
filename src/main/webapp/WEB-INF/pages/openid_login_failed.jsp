<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>
        Login in via OpenId Failed
    </title>
    <link href="${pageContext.request.contextPath}/resources/css/registration.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet">
    <jsp:include page="bootstrap.jsp"/>

</head>
<body>
<jsp:include page="header.jsp"/>

<sf:form id="regform" method="POST" modelAttribute="openIdUser" enctype="multipart/form-data" cssClass="form-signin">
    <div>
        <fieldset>
            <div class="form-group">

                <div class="form-signin-heading"><p>
                    Some provided openId information is not valid.
                    Check the corresponding fields in your provider's profile settings.
                </p></div>

                <p><sf:label path="name" cssClass="form-signin-heading">Name</sf:label>
                    <sf:input disabled="true" id="name" path="name" size="30" maxlength="30" cssClass="form-control"/>
                    <sf:errors path="name" cssClass="alert-danger"/></p>

                <p><sf:label path="surname" cssClass="form-signin-heading">Surname</sf:label>
                    <sf:input disabled="true" id="surname" path="surname" size="30" maxlength="30"
                              cssClass="form-control"/>
                    <sf:errors path="surname" cssClass="alert-danger"/></p>

                <p><sf:label path="mail" cssClass="form-signin-heading">Email</sf:label>
                    <sf:input disabled="true" id="mail" path="mail" size="30" maxlength="30" cssClass="form-control"/>
                    <sf:errors path="mail" cssClass="alert-danger"/></p>
            </div>
        </fieldset>
    </div>
</sf:form>

</body>
</html>