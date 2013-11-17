<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>View</title>

    <jsp:include page="bootstrap.jsp"/>

    <script type='text/javascript'>

        var options1 = {
            chart: {
                renderTo: 'chart',
                type: 'bar'
            },
            title: {
                text: 'Enterprises distribution'
            },
            xAxis: {
                categories: ['Categories']
            },
            yAxis: {
                title: {
                    text: 'Categories'
                }
            },
            series: [
                {
                    name: 'First Category',
                    data: [7]
                }
            ]
        };

        function find() {
            var name = $("#searchName").val();
            var cityId = $("#searchCitySelect").val();
            var categoryId = $("#searchCategorySelect").val();
            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/searchEnterprises",
                data: "name=" + name + '&cityId=' + cityId + '&categoryId' + categoryId,
                dataType: "json",
                success: function (data) {
                    alert("Ajax successful");
                    var res = data.resultSet.split("|");
                    for (var i = 0; i < res.length; i++) {

                        res[i] = parseInt(res[i]);
                    }
                    $("#searchResult").find("tr:gt(0)").remove();
                    <c:forEach items="${enterpriseList}" var="enterprise">
                    <%--if(res.indexOf(${enterprise.id}) > -1)--%>
//                    {
                    $('#searchResult > tbody').append('<tr>' +
                            '<td> <a href="describe/' + ${enterprise.id} +'" class="reference">' +
                            '<span class="glyphicon glyphicon-share-alt"></span>' + ${enterprise.name} +'</a></td>' +
                            '<td>' + ${enterprise.city.name}+'</td>' +
                            '<td>' + ${enterprise.category.name}+'</td>' +
                            '<td>' + ${enterprise.discountMin}+'</td>' +
                            '<td>' + ${enterprise.discountMax}+'</td>' +
                            '<td><a class="btn btn-info" href="${pageContext.request.contextPath}/enterprises/edit/"' + ${enterprise.id} +'>Edit</a></td> ' +
                            '<td><a class="btn btn-danger" href="${pageContext.request.contextPath}/view/deleteEnterprise/"' + ${enterprise.id} +'>Delete</a></td> ' +
                            '</tr>');
//                    }
                    </c:forEach>
                }
            });
        }
        ;

        var xAxis = {
            categories: []
        };

        <c:forEach items="${allEnterprises}" var="enterpriseQuantity">
        var seria = {
            data: [${enterpriseQuantity}]
        };
        options1.series.push(seria);
        </c:forEach>

        $(document).ready(function () {


            var chart1 = new Highcharts.Chart(options1);


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

            document.body.appendChild(document.createTextNode(getNames()));
        });
    </script>
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="container">
    <ul class="nav nav-tabs">
        <li class="active"><a href="${pageContext.request.contextPath}/view">Enterprises</a></li>
        <li><a href="${pageContext.request.contextPath}/category">Categories</a></li>
        <li><a href="${pageContext.request.contextPath}/city">Cities</a></li>
        <li><a href="${pageContext.request.contextPath}/user">Users</a></li>
        <li><a href="${pageContext.request.contextPath}/statistic">Statistic</a></li>
    </ul>
</div>

<%--<div class="container" id="searchForm">
    <form class="form-inline" role="form">
        <div class="form-group">
            <label class="sr-only" for="searchName">Enterprise</label>
            <input class="form-control" id="searchName" placeholder="Search">
        </div>
        <div class="form-group" id="searchCity">
            <select id="searchCitySelect" class="form-control">
                <option value="0" selected>All</option>
                <c:forEach items="${cityList}" var="city">
                    <option value="${city.id}"> ${city.name} </option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group" id="searchCategory">
            <select id="searchCategorySelect" class="form-control">
                <option selected>All</option>
                <c:forEach items="${categoryList}" var="category">
                    <option value="${category.id}"> ${category.name} </option>
                </c:forEach>
            </select>
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox" id="sortCheck"> Sort by rating
            </label>
        </div>
        <button type="submit" class="btn btn-default" onclick="find()">Search</button>
    </form>
</div>

<div class="container">
    <table class="table table-striped" id="searchResult">
        <tr><thead style="text-align: center"><h3>Search resutlt</h3></thead></tr>
        <tr>
            <th>Enterprise</th>
            <th>City</th>
            <th>Category</th>
            <th>Minimum discount</th>
            <th>Maximum discount</th>
            <th></th>
            <th></th>
        </tr>

    </table>
</div>--%>


<div class="container">
    <div class="container">
        <table class="table table-striped" id="enterpriseTable">
            <tr>
                <thead style="text-align: center"><h3>Enterprises</h3></thead>
                <a class="btn btn-success" href="${pageContext.request.contextPath}/enterprises">Add new</a>
            </tr>
            <tr>
                <th>Enterprise</th>
                <th>City</th>
                <th>Category</th>
                <th>Minimum discount</th>
                <th>Maximum discount</th>
                <th></th>
                <th></th>
            </tr>

            <c:forEach items="${enterpriseList}" var="enterprise">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/describe/${enterprise.id}" class="viewReference">
                        <span class="glyphicon glyphicon-share-alt"></span>
                            ${enterprise.name} </a></td>
                    <td>${enterprise.city.name}</td>
                    <td>${enterprise.category.name}</td>
                    <td>${enterprise.discountMin}</td>
                    <td>${enterprise.discountMax}</td>
                    <td><a class="btn btn-info"
                           href="${pageContext.request.contextPath}/enterprises/edit/${enterprise.id}">Edit</a></td>
                    <td><a class="btn btn-danger"
                           href="${pageContext.request.contextPath}/view/deleteEnterprise/${enterprise.id}">Delete</a>
                    </td>
                </tr>
            </c:forEach>

        </table>
    </div>

    <%--<div id="chart" style="width:100%; height:400px;"></div>--%>
</div>

</body>
</html>