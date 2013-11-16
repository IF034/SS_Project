<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>
        User management
    </title>
    <link href="${pageContext.request.contextPath}/resources/css/registration.css" rel="stylesheet">
    <jsp:include page="bootstrap.jsp"/>
    <script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
    <script type='text/javascript'>
        $(document).ready(function(){

            $("#user").validate({

                rules:{

                    name:{
                        required: true,
                        minlength: 4,
                        maxlength: 16
                    },
                    surname:{
                        required: true,
                        minlength: 4,
                        maxlength: 16
                    },
                    nickname:{
                        required: true,
                        minlength: 4,
                        maxlength: 16
                    },

                    password:{
                        required: true,
                        minlength: 6,
                        maxlength: 16
                    },
                    mail:{
                        required: true,
                        email: true,
                        minlength: 4,
                        maxlength: 30
                    }
                },

                messages:{

                    name:{
                        required: "This field is required",
                        minlength: "Username must be at least 4 characters long",
                        maxlength: "Maximum number of characters 16"
                    },
                    surname:{
                        required: "This field is required",
                        minlength: "Surname must be at least 4 characters long",
                        maxlength: "Maximum number of characters 16"
                    },
                    nickname:{
                        required: "This field is required",
                        minlength: "Nickname must be at least 4 characters long",
                        maxlength: "Maximum number of characters 16"
                    },
                    password:{
                        required: "This field is required",
                        minlength: "Password must be at least 4 characters long",
                        maxlength: "Maximum number of characters 16"
                    },
                    mail:{
                        required: "This field is required",
                        minlength: "E-mail must be at least 4 characters long",
                        maxlength: "Maximum number of characters 30"
                    }

                }

            });

        });
    </script>

</head>
<body>
<jsp:include page="header.jsp"/>

<div class="container">
    <ul class="nav nav-tabs">
        <li><a href="${pageContext.request.contextPath}/view">Enterprises</a></li>
        <li><a href="${pageContext.request.contextPath}/category">Categories</a></li>
        <li><a href="${pageContext.request.contextPath}/city">Cities</a></li>
        <li class="active"><a href="${pageContext.request.contextPath}/user">Users</a></li>
        <li><a href="${pageContext.request.contextPath}/statistic">Statistic</a></li>
    </ul>
</div>

<form:form id="user" method="POST" modelAttribute="user" cssClass="form-signin">
               <p><form:label path="name" cssClass="form-signin-heading">Name</form:label>
               <form:input id="name" path="name" size="30" maxlength="30" cssClass="form-control"/>
               <form:errors path="name" cssClass="alert alert-danger"/>
                   </p>
             <form:hidden path="id"/>
        <p><form:label path="surname" cssClass="form-signin-heading">Surname</form:label>
            <form:input id="surname" path="surname" size="30" maxlength="30" cssClass="form-control"/>
            <form:errors path="surname" cssClass="alert alert-danger"/></p>

        <p><form:label path="nickname" cssClass="form-signin-heading">Nickname</form:label>
            <form:input id="nickname" path="nickname" size="30" maxlength="30" cssClass="form-control"/>
            <form:errors path="nickname" cssClass="alert alert-danger"/></p>

        <p><form:label path="password" cssClass="form-signin-heading">Password</form:label>
            <form:input id="password" path="password" size="30" maxlength="30" cssClass="form-control"/>
            <form:errors path="password" cssClass=" alert alert-danger"/></p>

        <p><form:label path="mail" cssClass="form-signin-heading">Email</form:label>
            <form:input id="mail" path="mail" size="30" maxlength="30" cssClass="form-control"/>
            <form:errors path="mail" cssClass="alert alert-danger"/></p>

        <span>Role</span> <br>
        <form:radiobuttons path="role.id" items="${sourceRoles}" name="groop1"
                           itemValue="id" itemLabel="name"/>
        <p>
        <input id="submitButton" type="submit" value="${action}"
               formaction="${pageContext.request.contextPath}/user/${action}" class="btn btn-success">
        </p>
    </div>

</form:form>

<div class="container">
<form:form id="managementUserForm" method="get">
    <table class="table table-striped">
        <tr><thead>Users</thead></tr>
        <tr>
            <th>Nickname</th>
            <th>Name</th>
            <th>Surname</th>
            <th>E-mail</th>
            <th>Role</th>
            <th></th>
            <th></th>
        </tr>

        <c:forEach items="${userList}" var="user" varStatus="index">
            <tr>
                <td class="btn btn-default btn-block"><a href="${pageContext.request.contextPath}/user/profile/${user.nickname}">${user.nickname}</a></td>
                <td>${user.name}</td>
                <td>${user.surname}</td>
                <td>${user.mail}</td>
                <td>${user.role.name}</td>
                <td><a class="btn btn-danger"  href="${pageContext.request.contextPath}/user/delete/${user.id}">Delete</a></td>
                <td><a class="btn btn-info" href="${pageContext.request.contextPath}/user/edit/${user.id}">Edit</a></td>
            </tr>
        </c:forEach>

    </table>
</form:form>
</div>
</body>
</html>