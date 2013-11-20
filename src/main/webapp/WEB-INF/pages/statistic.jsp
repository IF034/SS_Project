<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" media="screen" href="http://silviomoreto.github.io/bootstrap-select/stylesheets/bootstrap-select.css">
<title>Statistic</title>

<jsp:include page="bootstrap.jsp"/>

<script src="${pageContext.request.contextPath}/resources/js/highcharts.js"></script>
<script type='text/javascript'>

var charts = [];

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
    series: []
};

var lineOptions = {
    chart: {
        renderTo: 'chart3',
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
            text: 'Quantity'
        }
    },
    tooltip: {
        enabled: false,
        formatter: function () {
            return '<b>' + this.series.name + '</b><br/>' +
                    this.x + ': ' + this.y + '°C';
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

var usernames = []
<c:forEach items="${userList}" var="user">
usernames.push('${user.name}');
</c:forEach>

var negativeOption = {
    chart: {
        renderTo: 'chart4',
        type: 'bar'
    },
    title: {
        text: 'User usefulness'
    },
    subtitle: {
        text: 'based on user feedback usefulness'
    },
    xAxis: [
        {
            categories: usernames,
            reversed: false
        },
        { // mirror axis on right side
            opposite: true,
            reversed: false,
            categories: usernames,
            linkedTo: 0
        }
    ],
    yAxis: {
        title: {
            text: null
        },
        labels: {
            formatter: function () {
                return (Math.abs(this.value));
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
            return '<b>'+ this.series.name +', user '+ this.point.category +'</b><br/>'+
                         Highcharts.numberFormat(Math.abs(this.point.y), 0) + 'votes';
        }
    },

    series: []
}

var seriesA = {
    name: '${categoryTest}',
    data: [${enterpriseCount}]
};

<c:forEach items="${categoryPairs}" var="targetPair">
var seria = {
    name: '${targetPair.key}',
    data: [${targetPair.value}]
};
options1.series.push(seria);
</c:forEach>

var getChartConfig = function (renderId, title, categories, yaxistext, data) {
    var config = {};
    config.chart = {
        renderTo: renderId,
        defaultSeriesType: 'column',
        margin: [50, 50, 100, 80]
    };
    config.title = title;
    config.xAxis = {
        categories: categories,
        labels: {
            rotation: -45,
            align: 'right',
            style: {
                font: 'normal 13px Verdana, sans-serif'
            }
        }
    };
    config.yAxis = {
        min: 0,
        title: {
            text: yaxistext
        }
    };
    config.legend = { enabled: false };

    config.series = data;

    return config;
};


basicData = []
<c:forEach items="${EnterpriseVotePair}" var="votePair">
var buffer = {
    name: '${votePair.key}',
    data: [${votePair.value}]
};
basicData.push(buffer);
</c:forEach>

<c:forEach items="${cityList}" var="city">
lineOptions.xAxis.categories.push('${city.name}');
</c:forEach>

$(document).ready(function () {
    var chart1 = new Highcharts.Chart(options1);
    var cities = ['Enterprises'];

    charts.push(new Highcharts.Chart(
            getChartConfig('chart2', 'User feedback', cities, 'User feedback', basicData)
    ))

    var lineSeria = {
        name: 'Cities',
        data: []
    }

    <c:forEach items="${cityCounts}" var="vote">
    lineSeria.data.push(${vote});
    </c:forEach>

    lineOptions.series.push(lineSeria);

    var positiveSeria = {
        name: 'Positive ratings',
        data: []
    }

    var negativeSeria = {
        name: 'Negative ratings',
        data: []
    }

    <c:forEach items="${positiveRatings}" var="positiveVote">
    positiveSeria.data.push(${positiveVote});
    </c:forEach>

    <c:forEach items="${negativeRatings}" var="negativeVote">
    negativeSeria.data.push(${negativeVote});
    </c:forEach>

    negativeOption.series.push(negativeSeria);
    negativeOption.series.push(positiveSeria);

    var chart3 = new Highcharts.Chart(lineOptions);
    var chart4 = new Highcharts.Chart(negativeOption);

});

function getRatings(catId) {
    $.ajax({
        type: "GET",
        url: "${pageContext.request.contextPath}/getRatings",
        data: "categoryId=" + catId,
        dataType: "json",
        success: function (data) {
            $.each(data, function(index, value) {
                alert( index + ": " + value );
            });
        }
    });
}
;
</script>
</head>

<body>

<jsp:include page="header.jsp"/>

<div class="container">
    <ul class="nav nav-tabs">
        <li><a href="${pageContext.request.contextPath}/view">Enterprises</a></li>
        <li><a href="${pageContext.request.contextPath}/category">Categories</a></li>
        <li><a href="${pageContext.request.contextPath}/city">Cities</a></li>
        <li><a href="${pageContext.request.contextPath}/user">Users</a></li>
        <li class="active"><a href="${pageContext.request.contextPath}/statistic">Statistic</a></li>
    </ul>


<security:authorize access="hasRole('ROLE_ADMIN')">

    <form action="${pageContext.request.contextPath}/logs" method="post">
        <select class="selectpicker" name="logType" >
            <option value="WarnErorLog.log">WarnErorLog.log</option>
            <option value="ORM.log">ORM.log</option>
            <option value="application.log">application.log</option>
        </select>
        <input type="submit" value="Show log" class="btn btn-default">
    </form>

</security:authorize>

</div>
<script type="text/javascript">
    $(document).ready(function(e) {
        $('.selectpicker').selectpicker();
    });
</script>
<div class="container">
    <div id="chart" style="width:100%; height:400px;"></div>
</div>

<div class="container" id="chart2container">
    <div id="chart2" style="width:100%; height:400px;"></div>
</div>

<div class="container" id="chart3container">
    <div id="chart3" style="width:100%; height:400px;"></div>
</div>

<div class="container" id="chart4container">
    <div id="chart4" style="width:100%; height:400px;"></div>
</div>

<div class="container" id="chart5container">
    <div id="chart5" style="width:100%; height:400px;"></div>
</div>

<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
    Select category
    <span class="caret"></span>
</button>
<ul class="dropdown-menu">
    <li>
        <a>All</a>
    </li>
    <c:forEach items="${categoryList}" var="category">
        <li>
            <a onclick="getRatings('${category.id}')">${category.name}</a>
        </li>
    </c:forEach>
</ul>

<div class="container" id="chart6container">
    <div id="chart6" style="width:100%; height:400px;"></div>
</div>

</body>
</html>