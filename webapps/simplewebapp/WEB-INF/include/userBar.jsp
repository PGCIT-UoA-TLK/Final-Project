<%--suppress HtmlUnknownTarget --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="user" scope="request" class="simplewebapp.User"/>

<div id="user-bar" class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/simplewebapp/">Latin Enthusiasts</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse navbar-right" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <c:choose>
                    <c:when test="${not empty user.username}">
                        <li><a href="/simplewebapp/?page=editUser">${user.username}</a></li>
                        <li> <a href="?page=addArticle&addArticle=1">Add a New Article</a></li>
                        <li><a href="/simplewebapp/?logout">Logout</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="/simplewebapp/?page=loginUser">Login</a></li>
                        <li><a href="/simplewebapp/?page=addUser">Register</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div>
</div>