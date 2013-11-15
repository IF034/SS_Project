<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<head>
    <title>
        <h2>Enterprise management </h2>
    </title>
    <link href="${pageContext.request.contextPath}/resources/css/new-enterprise.css" rel="stylesheet">
    <jsp:include page="bootstrap.jsp"/>
    <script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
    <script type='text/javascript'>
        $(document).ready(function () {

            $("#manageEnterpriseForm").validate({

                rules: {

                    name: {
                        required: true,
                        minlength: 4,
                        maxlength: 16
                    },
                    discountMin: {
                        required: true,
                        digits: true,
                        min: 0,
                        max: 100
                    },
                    discountMax: {
                        required: true,
                        digits: true,
                        min: 0,
                        max: 100
                    },

                    Description: {
                        required: true,
                        minlength: 5,
                        maxlength: 800
                    },
                    mail: {
                        required: true,
                        email: true,
                        minlength: 4,
                        maxlength: 30
                    },
                    webSite: {
                        required: true,
                        minlength: 5,
                        maxlength: 60
                    },
                    latitude: {
                        required: true,
                        number: true
                    },
                    longitude: {
                        required: true,
                        number: true
                    },
                    phone: {
                        required: true,
                        minlength: 5,
                        maxlength: 20

                    },
                    address: {
                        required: true,
                        minlength: 5,
                        maxlength: 200
                    }
                },

                messages: {

                    name: {
                        required: "This field is required",
                        minlength: "Name must be at least 4 characters long",
                        maxlength: "Maximum number of characters 16"
                    },
                    discountMin: {
                        required: "This field is required",
                        digits: "Input digital characters"

                    },
                    discountMax: {
                        required: "This field is required",
                        digits: "Input digital characters"
                    },
                    Description: {
                        required: "This field is required",
                        minlength: "Description must be at least 5 characters long",
                        maxlength: "Maximum number of characters 800"
                    },
                    mail: {
                        required: "This field is required",
                        minlength: "E-mail must be at least 4 characters long",
                        maxlength: "Maximum number of characters 30"
                    },
                    webSite: {
                        required: "This field is required",
                        minlength: "Website must be at least 5 characters long",
                        maxlength: "Maximum number of characters 60"
                    },
                    latitude: {
                        required: "This field is required",
                        digits: "Input digital characters"
                    },
                    longitude: {
                        required: "This field is required",
                        digits: "Input digital characters"
                    },
                    phone: {
                        required: "This field is required",
                        minlength: "Phone must be at least 5 characters long",
                        maxlength: "Maximum number of characters 20"
                    },
                    address: {
                        required: "This field is required",
                        minlength: "Address must be at least 5 characters long"

                    }

                }

            });

        });
    </script>
    <style>
        .thumb {
            height: 75px;
            border: 1px solid #000;
            margin: 10px 5px 0 0;
        }
    </style>
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="container well">
    <h3>Management of enterprises</h3>


    <form method = "POST" enctype="multipart/form-data">

        <div class="container">
            <div class="row-fluid">
                <div class="control-group">
                    <label class="control-label">Your Logo:</label>

                    <div class="controls clearfix">
                        <span class="btn btn-success btn-file">
                            <i class="icon-plus"></i> <span>Choose picture...</span>
                            <input type="file" name="image" id="photo"/>
                        </span>

                        <input type="button" class="btn btn-primary" Value="Upload" onclick="performAjaxSubmit('${pageContext.request.contextPath}')"/>
                        <output id="list"></output>
                    </div>
                </div>
            </div>
        </div>

        <style>
            body {
                padding-top: 50px;
            }

            .btn-file {
                position: relative;
                overflow: hidden;
                margin-right: 4px;
            }

            .btn-file input {
                position: absolute;
                top: 0;
                right: 0;
                margin: 0;
                opacity: 0;
                filter: alpha(opacity = 0);
                transform: translate(-300px, 0) scale(4);
                font-size: 23px;
                direction: ltr;
                cursor: pointer;
            }

            * + html .btn-file {
                padding: 2px 15px;
                margin: 1px 0 0 0;
            }
        </style>


    </form>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/fileUpload.js"></script>

    <form:form id="manageEnterpriseForm" method="POST" commandName="enterprise" class="form-horisontal">

        <div class="row">
            <div class="col-sm-4">
                <h4>Select category</h4>
                <form:select path="category.id" class="form-control">
                    <form:options items="${categoryList}" itemLabel="name" itemValue="id"/>
                </form:select>
            </div>
            <div class="col-sm-4">
                <h4>Select city</h4>
                <form:select path="city.id" class="form-control">
                    <form:options items="${cityList}" itemLabel="name" itemValue="id"/>
                </form:select>
            </div>
        </div>

        <form:hidden path="id"/>
        <div class="form-group">
            <h4>Enter name</h4>
            <form:input id="name" path="name" class="form-control"/>
            <form:errors path="name" class="form-control alert alert-danger"/>
        </div>
        <div class="form-group">
            <h4>Min discount </h4>
            <form:input id="discountMin" path="discountMin" class="form-control" type="number"/>
            <form:errors path="discountMin" class="form-control alert alert-danger"/>
        </div>

        <div class="form-group">
            <h4> Max discount </h4>
            <form:input id="discountMax" path="discountMax" class="form-control" type="number"/>
            <form:errors path="discountMax" class="form-control alert alert-danger"/>
        </div>
        <div class="form-group">
            <h4>Description </h4>
            <form:textarea id="Description" class="form-control" path="Description"/>
            <form:errors path="Description" class="form-control alert alert-danger"/>
        </div>
        <div class="form-group">
            <h4> e-mail </h4>
            <form:input class="form-control" path="mail"/>
            <form:errors path="mail" class="form-control alert alert-danger"/>
        </div>
        <div class="form-group">
            <h4> Website </h4>
            <form:input id="webSite" class="form-control" path="webSite"/>
            <form:errors path="webSite" class="form-control alert alert-danger"/>
        </div>

        <div class="form-group">
            <h4> Enter latitude </h4>
            <form:input id="latitude" class="form-control" path="latitude"/>
            <form:errors path="latitude" class="form-control alert alert-danger"/>
        </div>
        <div class="form-group">
            <h4> Enter longitude </h4>
            <form:input id="longitude" class="form-control" path="longitude"/>
            <form:errors path="longitude" class="form-control alert alert-danger"/>
        </div>
        <div class="form-group">
            <h4> Phone </h4>
            <form:input id="phone" class="form-control" path="phone"/>
            <form:errors path="phone" class="form-control alert alert-danger"/>
        </div>
        <div class="form-group">
            <h4> Address </h4>
            <form:input id="address" class="form-control" path="address"/>
            <form:errors path="address" class="form-control alert alert-danger"/>
        </div>

        <input type="submit" class="btn btn-primary" value="${action}" formaction="${pageContext.request.contextPath}/enterprises/${action}"/>
        <form:hidden path="summaryRatio" value="0"/>

    </form:form>

</div>
</body>
</html>