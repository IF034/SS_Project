<%@ taglib prefix="security"
           uri="http://www.springframework.org/security/tags" %>

<div class="navbar navbar-inverse navbar-fixed-top">

        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <%--&lt;%&ndash;--%><span class="icon-bar"></span><%--&ndash;%&gt;--%>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">SoftServe Discount Program</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">

            <security:authorize access="not isAuthenticated()">
                <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
                <li><a href="${pageContext.request.contextPath}/registration">Registration</a></li>
            </security:authorize>

           <%-- <security:authorize access="isAuthenticated()">
                    &lt;%&ndash;<ul class="nav navbar-text navbar-right">&ndash;%&gt;
                    <li>Logged in as <security:authentication property="principal.mail"/></li>
                    &lt;%&ndash;</ul>&ndash;%&gt;
            </security:authorize>--%>


            <security:authorize access="not isAuthenticated()">
                    <li>
                        <script src="//loginza.ru/js/widget.js" type="text/javascript"></script>
                        <a href="https://loginza.ru/api/widget?token_url=${pageContext.request.scheme}://${pageContext.request.serverName}${pageContext.request.contextPath}/login/openid"
                           class="loginza">Log in via OpenID</a>
                    </li>
            </security:authorize>

            <security:authorize access="hasRole('ROLE_ADMIN')">
                <li><a href="${pageContext.request.contextPath}/view">Management</a></li>
            </security:authorize>

                <li>

                </li>

             <security:authorize access="isAuthenticated()">
                    <li><strong>Logged in as <security:authentication property="principal.mail"/></strong></li>
             </security:authorize>

                <security:authorize access="isAuthenticated()">
                    <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
                </security:authorize>

            </ul>
        </div>
</div>

<div class="page-header"></div>