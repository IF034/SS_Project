<script src="${pageContext.request.contextPath}/resources/js/bootstrap-paginator.min.js"></script>


<script type='text/javascript'>



    var URL = String(window.location.href);
    var returnedAddress="placeholder";
    var currentPageNumber="currentPageNumber";
    if (URL.indexOf(currentPageNumber)!=-1){

        var regex = new RegExp( "\\?" + currentPageNumber + "=[^&]*&?", "gi");
        URL = URL.replace(regex,'?');
        regex = new RegExp( "\\&" + currentPageNumber + "=[^&]*&?", "gi");
        URL = URL.replace(regex,'&');
        URL = URL.replace(/(\?|&)$/,'');
        regex = null;
        returnedAddress = URL;
    } else {
        returnedAddress = "${pageContext.request.contextPath}${startPage}";
    }

    var options = {
        currentPage:${currentPageNumber},
        totalPages:${numberOfPages},
        bootstrapMajorVersion: 3,
        pageUrl: function(type, page, current){
            return returnedAddress+"&currentPageNumber="+page;

        }

    }

    $('#topPaginator').bootstrapPaginator(options);
    $('#bottomPaginator').bootstrapPaginator(options);
</script>

<%--
<script src="../../resources/js/bootstrap-paginator.min.js"></script>


<script type='text/javascript'>
    var options = {
        currentPage: ${currentPageNumber},
        totalPages: ${numberOfPages},
        bootstrapMajorVersion: 3,
        pageUrl: function(type, page, current){

            return ${startPage}+page;

        }

    }

    $('#topPaginator').bootstrapPaginator(options);
    $('#bottomPaginator').bootstrapPaginator(options);
</script>--%>
