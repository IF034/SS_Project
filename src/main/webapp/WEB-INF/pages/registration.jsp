<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>
        Registration
    </title>
    <link href="${pageContext.request.contextPath}/resources/css/registration.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet">
    <jsp:include page="bootstrap.jsp"/>

    <script type='text/javascript'>
        $(document).ready(function(){

        	    $("#regform").validate({

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
                           maxlength: 16
                       }
        	       },

        	       messages:{

        	            name:{
        	                required: "This field is required",
        	                minlength: "Name must be at least 4 characters long",
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
        	                minlength: "Password must be at least 6 characters long",
        	                maxlength: "Maximum number of characters 16"
        	            },
                       mail:{
                           required: "This field is required",
                           minlength: "E-mail must be at least 4 characters long",
                           maxlength: "Maximum number of characters 16"
                       }

        	       }

        	    });

        	});
    </script>
</head>
<body>
<jsp:include page="header.jsp"/>

<sf:form id="regform" method="POST" modelAttribute="user" enctype="multipart/form-data" cssClass="form-signin">
    <div>
        <fieldset>
            <div class="form-group">

                <p><sf:label path="name" cssClass="form-signin-heading">Name</sf:label>
                    <sf:input id="name" path="name" size="30" maxlength="30" cssClass="form-control"/>
                    <sf:errors path="name" cssClass="alert-danger"/></p>

                <p><sf:label path="surname" cssClass="form-signin-heading">Surname</sf:label>
                    <sf:input id="surname" path="surname" size="30" maxlength="30" cssClass="form-control"/>
                    <sf:errors path="surname" cssClass="alert-danger"/></p>

                <p><sf:label path="nickname" cssClass="form-signin-heading">Nickname</sf:label>
                    <sf:input id="nickname" path="nickname" size="30" maxlength="30" cssClass="form-control"/>
                    <sf:errors path="nickname" cssClass="alert-danger"/></p>

                <p><sf:label path="password" cssClass="form-signin-heading">Password</sf:label>
                    <sf:input id="password" type="password" path="password" size="30" maxlength="30" cssClass="form-control"/>
                    <sf:errors path="password" cssClass="alert-danger"/></p>

                <p><sf:label path="mail" cssClass="form-signin-heading">Email</sf:label>
                    <sf:input id="mail" path="mail" size="30" maxlength="30" cssClass="form-control"/>
                    <sf:errors path="mail" cssClass="alert-danger"/></p>

                <p><input name="commit" type="submit" value="Submit registration" class="btn btn-success"/></p>
            </div>
        </fieldset>
    </div>
</sf:form>

</body>
</html>