<%@ page import="simplewebapp.User" %>
<%@ page import="simplewebapp.UserDAO" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Registration</title>

    <%@ include file="include/specialIncludeFiles.jsp" %>
</head>
<body>

<%@include file="include/userBar.jsp" %>

<div class="container">
    <div class="col-xs-12">
        <form class="form-horizontal">
            <fieldset>
                <legend>User Registration</legend>

                <c:choose>
                    <%--@elvariable id="errorMessage" type="java.lang.String"--%>
                    <c:when test="${not empty errorMessage}">
                        <c:set var="hasErrorString">has-error</c:set>
                        <div class="alert alert-danger col-sm-offset-2 col-sm-10" role="alert">
                            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                            <span class="sr-only">Error:</span> ${errorMessage}
                        </div>
                    </c:when>
                </c:choose>

                <div class="form-group">
                    <label for="input-username" class="col-sm-2 control-label">Username</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="input-username" name="username" required pattern="^[a-zA-Z0-9._%+-]{1,15}"><br/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="input-password" class="col-sm-2 control-label">Password</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" id="input-password" name="password" required pattern="^[a-zA-Z0-9._%+-]{1,15}"><br/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="input-firstname" class="col-sm-2 control-label">First Name</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="input-firstname" name="firstname"><br/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="input-lastname" class="col-sm-2 control-label">Last Name</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="input-lastname" name="lastname"><br/>
                    </div>
                </div>

                <div class="radio" id="input-icon_name">
                    <label>
                        <input type="radio" name="optionsRadios" id="optionsRadios1" value="1" checked>
                        <img src="/images/IMG1.jpg" alt="Image is not available" class="img-thumbnail" WIDTH=89 HEIGHT=89>
                    </label>
                </div>
                <div class="radio">
                    <label>
                        <input type="radio" name="optionsRadios" id="optionsRadios2" value="2">
                        <img src="/images/IMG2.jpg" alt="Image is not available" class="img-thumbnail" WIDTH=89 HEIGHT=119>
                    </label>
                </div>

                <input type="hidden" name="page" value="addUser">
                <input type="submit" class="btn btn-default pull-right" value="Register">
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>
