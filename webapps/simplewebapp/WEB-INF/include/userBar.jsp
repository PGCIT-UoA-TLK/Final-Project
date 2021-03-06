<%--@elvariable id="contextPath" type="java.lang.String"--%>
<%--@elvariable id="user" type="simplewebapp.User"--%>

<%--suppress HtmlUnknownTarget --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="user-bar" class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${contextPath}?">Latin Enthusiasts</a>

            <img src="/images/logoimage.jpg" alt ="Latin" class="img-thumbnail thumbnail-logo">
        </div>

        <div class="collapse navbar-collapse navbar-right" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <c:choose>
                    <c:when test="${not empty user.username}">
                        <li><a href="${contextPath}?page=editUser">${user.username}</a></li>
                        <li><img src="/images/${user.image}" alt="icon" class="img-thumbnail thumbnail-usericon" id="input-icon"></li>
                        <li> <a href="?page=addArticle&addArticle=1">Add a New Article</a></li>
                        <li><a href="${contextPath}?logout">Logout</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${contextPath}?page=loginUser">Login</a></li>
                        <li><a href="${contextPath}?page=addUser">Register</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</div>