<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>
        Log In
    </title>
    <link href="${pageContext.request.contextPath}/resources/css/registration.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet">
    <jsp:include page="bootstrap.jsp"/>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery.validate.min.js"></script>
    <script type='text/javascript'>
        $(document).ready(function () {

            $("#form-signin").validate({

                rules: {

                    username: {
                        required: true,
                        minlength: 4,
                        maxlength: 16
                    },

                    password: {
                        required: true,
                        minlength: 6,
                        maxlength: 16
                    }

                },

                messages: {

                    username: {
                        required: "This field is required",
                        minlength: "Username must be at least 4 characters long",
                        maxlength: "Maximum number of characters 16"
                    },

                    password: {
                        required: "This field is required",
                        minlength: "Password must be at least 4 characters long",
                        maxlength: "Maximum number of characters 16"
                    }


                }

            });

        });
    </script>
</head>
<body>
<jsp:include page="header.jsp"/>

<form id="form-signin" name="f" action="${pageContext.request.contextPath}/j_spring_security_check" method="POST"
      class="form-signin">
    <div>
        <fieldset>
            <div class="form-group">

                <c:if test="${not empty error}">
                    <div class="alert-danger">
                        <p>${error} :
                                ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}.
                        </p>
                    </div>
                </c:if>

                <p><label for="username" class="form-signin-heading">Nickname</label>
                    <input id="username" type="text" name="j_username" size="30" maxlength="30" class="form-control"/>
                </p>

                <p><label for="password" class="form-signin-heading">Password</label>
                    <input id="password" type="password" name="j_password" size="30" maxlength="30"
                           class="form-control"/>
                </p>

                <p><input name="submit" type="submit" value="Submit" class="btn btn-success"/></p>
            </div>
        </fieldset>
    </div>
</form>

</body>
</html>