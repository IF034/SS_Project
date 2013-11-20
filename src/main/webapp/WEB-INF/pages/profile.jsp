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

    <script type='text/javascript'>
        var username = ['${user.name}']

        var negativeOption = {
            chart: {
                renderTo : 'chart',
                type: 'bar'
            },
            title: {
                text: 'User usefulness'
            },
            subtitle: {
                text: 'based on user feedback usefulness'
            },
            xAxis: [{
                categories: username,
                reversed: false
            }, { // mirror axis on right side
                opposite: true,
                reversed: false,
                categories: username,
                linkedTo: 0
            }],
            yAxis: {
                title: {
                    text: null
                },
                labels: {
                    formatter: function(){
                        return (Math.abs(this.value)) + 'votes';
                    }
                },
                min: -100,
                max: 100
            },

            plotOptions: {
                series: {
                    stacking: 'normal'
                }
            },

            tooltip: {
                formatter: function(){
                    return '<b>'+ this.series.name +', age '+ this.point.category +'</b><br/>'+
                            'Population: '+ Highcharts.numberFormat(Math.abs(this.point.y), 0);
                }
            },

            series: []
        }

        var positiveSeria = {
            name : 'Positive ratings',
            data : []
        }

        var negativeSeria = {
            name : 'Negative ratings',
            data : []
        }

        positiveSeria.data.push(${positiveRatings});

        negativeSeria.data.push(${negativeRatings});

        negativeOption.series.push(negativeSeria);
        negativeOption.series.push(positiveSeria);

        var lineOptions = {
            chart: {
                renderTo: 'chart2',
                type: 'line'
            },
            title: {
                text: 'Enterprises distribution'
            },
            subtitle: {
                text: 'By city'
            },
            xAxis: {
                categories: []
            },
            yAxis: {
                title: {
                    text: 'Hours'
                }
            },
            tooltip: {
                enabled: false,
                formatter: function() {
                    return '<b>'+ this.series.name +'</b><br/>'+
                            this.x +': '+ this.y;
                }
            },
            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: true
                    },
                    enableMouseTracking: false
                }
            },
            series: []
        }

        <c:forEach items="${lastHours}" var="hour">
            lineOptions.xAxis.categories.push('${hour}');
        </c:forEach>

        var lineSeria = {
            name: 'Last comments',
            data: []
        }

       <%--<c:forEach items="${lastComments}" var="commentQuantity">--%>
        //    lineSeria.data.push(${commentQuantity});
        <%--</c:forEach>--%>

        lineOptions.series.push(lineSeria);

        $(document).ready(function(){
            var chart1 = new Highcharts.Chart(negativeOption);
//            var chart2 = new Highcharts.Chart(lineOptions);
        });

    </script>
</head>
<body>
<jsp:include page="header.jsp"/>

<form:form id="user" modelAttribute="user" cssClass="form-signin">
<p>
    <form:label path="name" cssClass="form-signin-heading">Name</form:label>
        <form:label path="name" cssClass="form-control">${user.name}</form:label>


<p>
    <form:label path="surname" cssClass="form-signin-heading">Surname</form:label>
        <form:label path="surname" cssClass="form-control">${user.surname}</form:label>


<p>
    <form:label path="nickname" cssClass="form-signin-heading">Nickname</form:label>
        <form:label path="nickname" cssClass="form-control">${user.nickname}</form:label>


<p>
    <form:label path="mail" cssClass="form-signin-heading">Email</form:label>
        <form:label path="mail" cssClass="form-control">${user.mail}</form:label>


    </form:form>

<div class="container">
    <div id="chart" style="width:100%; height:400px;"></div>
</div>

<div class="container" id="chart2container">
    <div id="chart2" style="width:100%; height:400px;"></div>
</div>

</body>
</html>