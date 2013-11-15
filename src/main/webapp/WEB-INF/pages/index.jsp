<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Soft Serve Discount Program</title>
    <jsp:include page="bootstrap.jsp"/>

    <link href="${pageContext.request.contextPath}/resources/css/jquery.rating.css" rel="stylesheet" type="text/css"/>
    <link href="${pageContext.request.contextPath}/resources/css/styles.css" rel="stylesheet">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/resources/js/jquery.rating-2.0.min.js"></script>


</head>
<body>

<jsp:include page="header.jsp"/>


<div class="page-header" style="text-align: center">
    <h2>Top Enterprises</h2>
</div>

<div class="container">

    <div class="col-md-2"></div>
    <div class="col-md-8">
        <div id="myCarousel" class="carousel slide" style="width: 780px; float:right; right: 20px ; margin: 0 auto">
            <!-- Indicators -->
            <ol class="carousel-indicators">
                <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                <c:forEach items="${topList}" var="toplist" varStatus="theCount">
                    <li data-target="#myCarousel" data-slide-to="${theCount.count}"></li>
                </c:forEach>
            </ol>

            <!-- Wrapper for slides -->
            <div class="carousel-inner">
                <div class="item active" style="margin:0 auto">
                    <img src="${pageContext.request.contextPath}/resources/images/img.jpg" alt="..."
                         style="margin:0 auto; width:330px; height:183px;">

                    <div class="carousel-caption">
                    </div>
                </div>
                <c:forEach items="${topList}" var="topEnterprise">
                    <div class="item">

                        <center><a href="${pageContext.request.contextPath}/describe/${topEnterprise.id}"><img
                                src="${pageContext.request.contextPath}/image/${topEnterprise.id}" alt="${topEnterprise.name}" style="display:inline-block;
    width:330px;
    height:183px;
    overflow:hidden;
    vertical-align:top"></a></center>
                        <div class="carousel-caption">
                            <p style="margin:20%;color:red;font-size:15px"> ${topEnterprise.name} </p>

                        </div>
                    </div>

                </c:forEach>
            </div>

            <a class="left carousel-control" href="#myCarousel" data-slide="prev">
                <span class="icon-prev"></span>
            </a>
            <a class="right carousel-control" href="#myCarousel" data-slide="next">
                <span class="icon-next"></span>
            </a>
        </div>
    </div>
    <div class="col-md-2"></div>
</div>

<script type="text/javascript">
    function orderByDiscount() {
        location.href = "${pageContext.request.contextPath}/params?CategoryId=${CategoryId}&TownId=${TownId}&OrderByRatio=${OrderByRatio}&currentPageNumber=1&numberOfPages=${numberOfPages}&startPage=${startPage}&OrderByDiscount=" + !Boolean(${OrderByDiscount});
    }
    function orderByRatio() {
        location.href = "${pageContext.request.contextPath}/params?CategoryId=${CategoryId}&TownId=${TownId}&currentPageNumber=1&numberOfPages=${numberOfPages}&startPage=${startPage}&OrderByRatio=" + !Boolean(${OrderByRatio}) + "&OrderByDiscount=${OrderByDiscount}";
    }
</script>

<div class="carouselDivider"></div>

<div class="container">
    <div class="row">

        <div class="col-md-2 well">
            <ul class="nav nav-pills nav-stacked">
                <c:choose>
                    <c:when test="${(CategoryId == 0) || (CategoryId == '')}">
                        <li class="active">
                            <a href="${pageContext.request.contextPath}/params?CategoryId=&TownId=${TownId}&OrderByRatio=${OrderByRatio}&OrderByDiscount=${OrderByDiscount}&currentPageNumber=1&numberOfPages=${numberOfPages}&startPage=${startPage}">
                                <span class="badge pull-right">${AllEnterprisesCount}</span>
                                All
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <a href="${pageContext.request.contextPath}/params?CategoryId=&TownId=${TownId}&OrderByRatio=${OrderByRatio}&OrderByDiscount=${OrderByDiscount}&currentPageNumber=1&numberOfPages=${numberOfPages}&startPage=${startPage}">
                                <span class="badge pull-right">${AllEnterprisesCount}</span>
                                All
                            </a>
                        </li>
                    </c:otherwise>
                </c:choose>
                <c:forEach items="${enterprisesCount}" var="categoryCountPair">
                    <c:choose>
                        <c:when test="${CategoryId == categoryCountPair.key.id}">
                            <li class="active">
                                <a href="${pageContext.request.contextPath}/params?CategoryId=${categoryCountPair.key.id}&TownId=${TownId}&OrderByRatio=${OrderByRatio}&OrderByDiscount=${OrderByDiscount}&currentPageNumber=1&numberOfPages=${numberOfPages}&startPage=${startPage}">
                                    <span class="badge pull-right">${categoryCountPair.value}</span>
                                        ${categoryCountPair.key.name}
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li>
                                <a href="${pageContext.request.contextPath}/params?CategoryId=${categoryCountPair.key.id}&TownId=${TownId}&OrderByRatio=${OrderByRatio}&OrderByDiscount=${OrderByDiscount}&currentPageNumber=1&numberOfPages=${numberOfPages}&startPage=${startPage}">
                                    <span class="badge pull-right">${categoryCountPair.value}</span>
                                        ${categoryCountPair.key.name}
                                </a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
        </div>

        <div class="col-md-10 enterprisesWell">
            <div class="row" style="text-align: center">
                <div class="container" style="margin-top: 12px; margin-bottom: 20px">
                    <div class="col-md-3"></div>
                    <div class="col-md-6">
                        <div class="dropdown">
                            <button class="btn dropdown-toggle sr-only" type="button" id="dropdownMenu1" data-toggle="dropdown">
                                <span class="caret"> ${selectedCity}</span>
                            </button>
                            <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
                                <li role="presentation">
                                    <a href="${pageContext.request.contextPath}/params?CategoryId=${CategoryId}&TownId=0&OrderByRatio=${OrderByRatio}&OrderByDiscount=${OrderByDiscount}&currentPageNumber=1&numberOfPages=${numberOfPages}&startPage=${startPage}">All</a>
                                </li>
                                <c:forEach items="${cities}" var="city">
                                    <li role="presentation">
                                        <a href="${pageContext.request.contextPath}/params?CategoryId=${CategoryId}&TownId=${city.id}&OrderByRatio=${OrderByRatio}&OrderByDiscount=${OrderByDiscount}&currentPageNumber=1&numberOfPages=${numberOfPages}&startPage=${startPage}">${city.name}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>

                        <button type="button" value="Order by discount" onclick="orderByDiscount()"
                                class="btn btn-default">
                            Order by discount
                        </button>

                        <button type="button" disabled value="Order by ratio" onclick="orderByRatio()"
                                class="btn btn-default disabled">
                            Order by ratio
                        </button>

                    </div>

                    <div class="col-md-3"></div>
                </div>
            </div>
            <div class="container">
                <ul id="topPaginator" style="margin-left: 40%"></ul>
                <div class="pageDivider"></div>
                <c:if test="${!empty enterprises}">
                    <jsp:useBean id="contentHelper" class="com.springapp.mvc.utils.ContentHelper"/>
                    <c:forEach items="${enterprises}" var="enterprise">
                        <div class="row">
                            <div class="col-md-4">
                                <a class="reference"
                                   href="${pageContext.request.contextPath}/describe/${enterprise.id}">
                                    <img src="${pageContext.request.contextPath}/image/${enterprise.id}" height="140" width="140" align="left"
                                         alt="No logo"
                                         onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/resources/images/logos/nologo.jpg';"/>
                                </a>

                            </div>
                            <div class="col-md-6">
                                <div class="row" style="margin-bottom: 15px">
                                    <div class="col-md-12">
                                        <a class="reference"
                                           href="${pageContext.request.contextPath}/describe/${enterprise.id}">${enterprise.name}</a>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12 well" style="max-height: 120px; overflow-y: auto">
                                        <jsp:setProperty name="contentHelper" property="content"
                                                         value="${enterprise.description}"/>
                                        <jsp:getProperty name="contentHelper" property="content"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-3"></div>
                                    <div class="col-md-9">
                                        <div id="rating_${enterprise.id}">
                                            <input id='val_${enterprise.id}' type="hidden" name="val">
                                            <input id='votes_${enterprise.id}' type="hidden" name="votes"
                                                   value=${votes}>
                                            <input type="hidden" name="enterprise-id" value="${enterprise.id}">
                                            <input type="hidden" name="user-id" value="${user.id}">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <security:authorize access="hasRole('ROLE_ADMIN')">
                                <div class="col-md-2">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <a href="${pageContext.request.contextPath}/enterprises/delete/${enterprise.id}"
                                               onclick="return confirm('Are you sure want to delete');">
                                                <img class="img-thumbnail" width="40" height="40" alt="lorem"
                                                     src="${pageContext.request.contextPath}/resources/images/delete.jpg">
                                            </a>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <a href="${pageContext.request.contextPath}/enterprises/edit/${enterprise.id}">
                                                <img class="img-thumbnail" width="40" height="40" alt="lorem"
                                                     src="${pageContext.request.contextPath}/resources/images/edit.jpg">
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </security:authorize>
                        </div>


                        <%--<div id="rating_${enterprise.id}">
                            <input id='val_${enterprise.id}' type="hidden" name="val" >
                            <input id='votes_${enterprise.id}' type="hidden" name="votes" value=${votes}>
                            <input type="hidden" name="enterprise-id" value="${enterprise.id}">
                            <input type="hidden" name="user-id" value="${user.id}">
                        </div>--%>

                        <div class="pageDivider"></div>

                        <script type="text/javascript">
                            var r;
                            var val;
                            var votes;
                            $.ajax({
                                type: "GET",
                                url: "${pageContext.request.contextPath}/ajaxRatio",
                                data: "user=${user.id}&enterprise=${enterprise.id}",
                                dataType: "json",
                                async: false,
                                success: function (data) {
                                    r = data.vis;
                                    val = data.value;
                                    votes = data.votes;
                                    document.getElementById('val_${enterprise.id}').value = val;

                                    document.getElementById('votes_${enterprise.id}').value = votes;
                                    document.getElementById('votes_${enterprise.id}').value = votes;

                                }
                            });
                            <%--alert(${enterprise.id}+': '+r);--%>
                            $('#rating_${enterprise.id}').rating({
                                fx: 'full',
                                image: '${pageContext.request.contextPath}/resources/images/stars.png',
                                loader: '${pageContext.request.contextPath}/resources/images/ajax-loader.gif',
                                minimal: 0.6,
                                type: 'get',
                                readOnly: r,
                                url: '${pageContext.request.contextPath}/ajaxParams'
//                                          callback: function (responce) {
//
//                                        alert(r) ;
//                                         }
                            });
                        </script>
                    </c:forEach>
                </c:if>

                <ul id="bottomPaginator" style="margin-left: 40%"></ul>
                <jsp:include page="paginator.jsp"/>

            </div>
        </div>
    </div>

</div>

<script>
    $('.carousel').carousel({
        interval: 5000
    })
</script>
</body>
</html>