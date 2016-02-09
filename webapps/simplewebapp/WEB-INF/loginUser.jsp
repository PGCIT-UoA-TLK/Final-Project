<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Login</title>

    <%@ include file="include/specialIncludeFiles.jsp" %>
</head>
<body>

<%@include file="include/userBar.jsp" %>
<div class="container">
    <div class="col-xs-12">
        <form class="form-horizontal">
            <fieldset class="login">
                <legend class="login">Login</legend>

                <%@include file="include/alerts.jsp" %>

                <div class="form-group">
                    <label for="username" class="col-sm-2 control-label">Username</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="username" name="username">
                    </div>
                </div>

                <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">Password</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" id="password" name="password">
                    </div>
                </div>
                <input type="hidden" name="page" value="loginUser">
                <input type="submit" class="btn btn-default pull-right" value="Log In">
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>
