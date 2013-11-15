<!doctype html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Description</title>

    <link href="${pageContext.request.contextPath}/resources/css/describe.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/jquery_ui_1.10.3.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/comment/comment.css" rel="stylesheet">

    <jsp:include page="bootstrap.jsp"/>

    <meta name="viewport" content="initial-scale=1.0, user-scalable=no"/>
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
    <jsp:include page="/resources/comment/comment_teamplates.jsp"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/mustache.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-ui_1_10_3.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/comment/comment.js"></script>

    <script type="text/javascript">
        var geocoder;
        var map;
        function initialize() {
            geocoder = new google.maps.Geocoder();
            var latlng = new google.maps.LatLng(${enterpriseDescription.latitude}, ${enterpriseDescription.longitude});
            var myOptions = {
                zoom: 15,
                center: latlng,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            }
            map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

            myMarker = new google.maps.Marker({ position: latlng, map: map, title: "About.com Headquarters" });
        }

        function codeAddress() {
            var address = document.getElementById('address').value;
            geocoder.geocode({ 'address': address}, function (results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    map.setCenter(results[0].geometry.location);
                    var marker = new google.maps.Marker({
                        map: map,
                        position: results[0].geometry.location
                    });
                } else {
                    alert('Geocode was not successful for the following reason: ' + status);
                }
            });
        }
        google.maps.event.addDomListener(window, 'load', initialize);
    </script>

    <script type="text/javascript">
        function initializePage(enterpriseId) {
            initializeComments(enterpriseId, '${pageContext.request.contextPath}');
            initialize();
        }
    </script>

</head>
<body onload="initializePage('${enterpriseDescription.id}')">
<jsp:include page="header.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <h3 align="center">
                ${enterpriseDescription.name}
            </h3>
        </div>
    </div>

    <div class="row">
        <div class="col-md-3">
            <img alt="140x140" src="${pageContext.request.contextPath}/image/${enterpriseDescription.id}" width="140"
                 height="140"
                 onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/resources/images/logos/nologo.jpg';"
                    />
        </div>
        <div class="col-md-9">
            <div class="well">
                <p>
                    ${enterpriseDescription.description}
                </p>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <table class="table table-condensed">
                <thead>
                <tr>
                    <th>
                        Category name
                    </th>
                    <th>
                        Location
                    </th>
                    <th>
                        Min discount
                    </th>
                    <th>
                        Max discount
                    </th>
                    <th>
                        Contact
                    </th>
                </tr>
                </thead>
                <tbody>
                <tr class="info">
                    <td>
                        ${enterpriseDescription.category.name}
                    </td>
                    <td>
                        ${enterpriseDescription.city.name},
                        <br>
                        ${enterpriseDescription.address}
                    </td>
                    <td>
                        ${enterpriseDescription.discountMin} %
                    </td>
                    <td>
                        ${enterpriseDescription.discountMax} %
                    </td>
                    <td>
                        mail: ${enterpriseDescription.mail}
                        <br>
                        website: <a href="javascript: document.location.href = 'http://${enterpriseDescription.webSite}';">${enterpriseDescription.webSite}</a>

                        <br>
                        <abbr title="Phone">P:</abbr> ${enterpriseDescription.phone}
                    </td>
                </tr>
                </tbody>
            </table>

        </div>
    </div>

    <div id="map_canvas" style="width:1100px; height:320px;"></div>
    <security:authorize access="hasRole('ROLE_ADMIN')">
        <div id="panel" class="form">
            <div class="form-group">
                <label for="address"> Address of enterprise:</label>
                <input id="address" type="textbox" value="${enterpriseDescription.address}" class="form-control"
                       placeholder="Address">
            </div>
            <input type="button" value="Show on map" onclick="codeAddress()" class="btn btn-default">
        </div>
    </security:authorize>
    <div id="map-canvas"></div>

    <div id="comments" class="row">


    </div>
    <security:authorize access="isAuthenticated()">
        <div id="add_comment" class="commentWell" style="margin: 0 auto">
            <form id="form_add_comment">
                <input type="hidden" name="enterprise_id" value="${enterpriseDescription.id}">
                <textarea name="content" class="form-control"></textarea>
                <input class="btn btn-success" type="button" value="add" onclick="addComment()">
            </form>
        </div>
    </security:authorize>


</div>


</body>
</html>