<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>
        <h2>Category management </h2>
    </title>
    <jsp:include page="bootstrap.jsp"/>
    <script type="text/javascript"
            src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
    <script type='text/javascript'>
        $(document).ready(function () {

            $("#addCategoryForm").validate({

                rules: {

                    name: {
                        required: true,
                        minlength: 4,
                        maxlength: 16
                    }

                },

                messages: {

                    name: {
                        required: "This field is required",
                        minlength: "Category name must be at least 4 characters long",
                        maxlength: "Maximum number of characters 16"
                    }

                }

            });

        });
    </script>

    <script type="text/javascript">

        var categoryId;
        var categoryName;
        function del(delId) {
            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/categoryDelete",
                data: "category=" + delId,
                dataType: "json",
                success: function () {
                    $("#del_" + delId).remove();
                }
            });
        }
        ;
        function edit(editId, editName) {
            categoryId = editId;
            $("h3").text('Edit category ' + $('#category_' + editId).html());
            document.getElementById('inp').value = $('#category_' + editId).html();
            document.getElementById('inp').focus();
            document.getElementById('add_edit').value = 'Edit';
        }
        ;

        function addedit() {
            if ($("#addCategoryForm").valid()) {
                if (document.getElementById('add_edit').value == 'Add') {
                    var name = $('#inp').val()
                    $.ajax({
                        type: "GET",
                        url: "${pageContext.request.contextPath}/categoryAdd",
                        data: "name=" + name,
                        dataType: "json",
                        success: function (data) {
                            categoryId = data.idCategory;

                            $('#categories > tbody:last').append(' <tr id="del_' + categoryId + '">' +
                                    '<td id="category_' + categoryId + '" >' + name + '</td>' +
                                    '<td><a id="edit_' + categoryId + '" class="btn btn-info"  ' +
                                    'onclick="edit(' + "'" + categoryId + "'" + ')">Edit</a></td>' +
                                    '<td><a id="delete_' + categoryId + '" class="btn btn-danger" onclick="del(' + "'" + categoryId + "'" + ')">Delete</a></td> ' +
                                    '</tr>'
                            );

                            document.getElementById('inp').value = '';
                        }
                    });
                }
                if (document.getElementById('add_edit').value == 'Edit') {

                    categoryName = document.getElementById('inp').value;
                    $.ajax({
                        type: "GET",
                        url: "${pageContext.request.contextPath}/categoryUpdate",
                        data: "id=" + categoryId + '&name=' + categoryName,
                        dataType: "json",
                        success: function (data) {
                            $("h3").text('Add new category');
                            $('#category_' + categoryId).html(categoryName);
                            document.getElementById('add_edit').value = 'Add';
                            document.getElementById('inp').value = '';
                        }
                    });
                }
            }
        }
        ;


    </script>

</head>
<body>
<jsp:include page="header.jsp"/>

<div class="container">
    <ul class="nav nav-tabs">
        <li><a href="${pageContext.request.contextPath}/view">Enterprises</a></li>
        <li class="active"><a href="${pageContext.request.contextPath}/category">Categories</a></li>
        <li><a href="${pageContext.request.contextPath}/city">Cities</a></li>
        <li><a href="${pageContext.request.contextPath}/user">Users</a></li>
        <li><a href="${pageContext.request.contextPath}/statistic">Statistic</a></li>
    </ul>
</div>

<div class="container">

    <div class="row">
        <div class="col-sm-4">
            <form:form id="addCategoryForm" commandName="category" class="form-inline">

                <h3>Add new category</h3>

                <div class="form-group">
                    <form:hidden path="id"/>
                    <form:input id="inp" path="name" placeholder="Category" class="form-control"/>
                    <form:errors path="name" cssclass="error"></form:errors>
                </div>
                <div class="form-group">
                    <input id="add_edit" type="button" value="Add" class="btn btn-info" onclick="addedit()">
                </div>
            </form:form>
        </div>


        <form:form id="managementCategoryForm" commandName="categoriesContainer">

        <div class="col-sm-4">
            <table class="table table-striped" id="categories">
                <tr>
                    <thead><h4>Categories</h4></thead>
                </tr>
                <tr>
                    <th>Category</th>
                    <th></th>
                    <th></th>
                </tr>

                <c:forEach items="${sourceList}" var="category">
                    <tr id="del_${category.id}">
                        <td id="category_${category.id}">${category.name}</td>
                        <td><a id="edit_${category.id}" class="btn btn-info" onclick="edit('${category.id}')">Edit</a>
                        </td>
                        <td id="delete_${category.id}"><a class="btn btn-danger"
                                                          onclick="del('${category.id}')">Delete</a></td>
                    </tr>
                </c:forEach>

            </table>
        </div>
    </div>
    </form:form>
</div>
</body>
</html>