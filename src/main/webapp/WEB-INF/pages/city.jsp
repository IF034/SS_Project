<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>
        <h2>City management </h2>
    </title>
    <%--<!-- Подключаем таблицу стилей Bootstrap -->
    <link href="/resources/css/jquery.rating.css" rel="stylesheet" type="text/css" />
    <link href="/resources/css/bootstrap.css" rel="stylesheet" media="screen">
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/css/styles.css" rel="stylesheet">
    <!-- Подключаем библиотеку jquery -->
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <!-- Подключаем скрипти bootstrap.js -->
    <script src="/resources/js/bootstrap.js"></script>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.min.js"></script>

    <script type="text/javascript">
        window.jQuery || document.write('<script type="text/javascript" src="resources/jquery-1.6.2.min.js"><\/script>');
    </script>--%>

    <jsp:include page="bootstrap.jsp"/>
    <script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
    <style>
        .error{
            color : red ;
            padding-left : 3px;
        }
    </style>
    <script type='text/javascript'>
        $(document).ready(function(){

            $("#addCategoryForm").validate({

                rules:{

                    name:{
                        required: true,
                        minlength: 4,
                        maxlength: 16
                    }

                },

                messages:{

                    name:{
                        required: "This field is required",
                        minlength: "Category name must be at least 4 characters long",
                        maxlength: "Maximum number of characters 16"
                    }

                }

            });

        });
    </script>
    <script type="text/javascript">

           var cityId;
           var cityName;
       function del(delId){
            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/cityDelete",
                data: "city="+delId,
                dataType: "json",
                success: function()
                {
                    $("#del_"+delId).remove();
                 }
            });
        };
        function edit(editId){
            cityId=editId;
            $("h3").text('Edit city '+$('#city_'+editId).html());
            document.getElementById('inp').value = $('#city_'+editId).html();
            document.getElementById('inp').focus();
            document.getElementById('add_edit').value = 'Edit';
        };

        function addedit(){


            if($("#addCategoryForm").valid()){

               if (document.getElementById('add_edit').value=='Add') {
                   var name = $('#inp').val()
               $.ajax({
                   type: "GET",
                   url: "${pageContext.request.contextPath}/cityAdd",
                   data: "name="+name ,
                   dataType: "json",
                   dataType: "json",
                   success: function(data)
                   {
                       cityId=data.idCity;

                       $('#cities > tbody:last').append(' <tr id="del_'+ cityId+'">'+
                               '<td id="city_'+cityId+'" >'+name+'</td>' +
                               '<td><a id="edit_'+cityId+'" class="btn btn-info"  ' +
                               'onclick="edit('+"'"+cityId+"'"+')">Edit</a></td>' +
                               '<td><a id="delete_'+cityId+'" class="btn btn-danger" onclick="del('+"'"+cityId+"'"+')">Delete</a></td> '+
                               '</tr>'
                       );

                       document.getElementById('inp').value = '';
                   }
               });
               }
               if (document.getElementById('add_edit').value=='Edit') {

                   cityName = document.getElementById('inp').value;
                   $.ajax({
                       type: "GET",
                       url: "${pageContext.request.contextPath}/cityUpdate",
                       data: "id="+cityId+'&name='+cityName ,
                       dataType: "json",
                       success: function(data)
                       {
                           $("h3").text('Add new city');
                           $('#city_'+cityId).html(cityName);
                           document.getElementById('add_edit').value = 'Add';
                           document.getElementById('inp').value = '';
                       }
                   });
               }
            }
           };


    </script>

</head>
<body>
<jsp:include page="header.jsp"/>

<div class="container">
    <ul class="nav nav-tabs">
        <li><a href="${pageContext.request.contextPath}/view">Enterprises</a></li>
        <li><a href="${pageContext.request.contextPath}/category">Categories</a></li>
        <li class="active"><a href="${pageContext.request.contextPath}/city">Cities</a></li>
        <li><a href="${pageContext.request.contextPath}/user">Users</a></li>
        <li><a href="${pageContext.request.contextPath}/statistic">Statistic</a></li>
    </ul>
</div>

<div class="container">
    <div class="row">

        <div class="col-sm-4 col-md-3">

            <form:form id="addCategoryForm" commandName="city" class="form-inline">

                <h3>Add new city</h3>

                <div class="form-group">
                    <form:hidden path="id"/>
                    <form:input id="inp" path="name" placeholder="City" class="form-control"/>
                </div>
                <div class="form-group">
                    <input id="add_edit" type="button" value="Add" class="btn btn-info" onclick="addedit()">
                </div>
            </form:form>
        </div>

        <form:form id="managementCategoryForm" commandName="citiesContainer">

            <div class="col-sm-4 col-md-3">
                <table class="table table-striped" id="cities">
                    <tr><thead><h4>Cities</h4></thead></tr>
                    <tr>
                        <th>City</th>
                        <th></th>
                        <th></th>
                    </tr>

                    <c:forEach items="${sourceList}" var="city">

                        <tr id="del_${city.id}">
                            <td id="city_${city.id}" >${city.name}</td>
                            <td><a id="edit_${city.id}" class="btn btn-info"  onclick="edit('${city.id}')">Edit</a></td>
                            <td><a id="delete_${city.id}" class="btn btn-danger" onclick="del('${city.id}')">Delete</a></td>
                        </tr>

                    </c:forEach>

                </table>

            </div>

        </form:form>

    </div>

</div>

</body>
</html>