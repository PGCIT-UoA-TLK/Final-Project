<%@ page import="simplewebapp.UserDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>

    <%@ include file="include/specialIncludeFiles.jsp" %>
</head>
<body>

<%@include file="include/userBar.jsp"%>
<div class="container">
    <div class="col-xs-12">
        <form class="form-horizontal">
            <fieldset class="login">
                <legend class="login">Login</legend>
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
<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    if (username != null && password != null) {
        UserDAO userDAO = UserDAO.getInstance();

        User thisUser = userDAO.loginUser(username, password);

        if (thisUser != null) {
            session.setAttribute("user", thisUser);

            String returnPage = "";
            if (request.getParameter("backpage") != null) {
                returnPage = "&page=" + request.getParameter("backpage");
            }

            response.sendRedirect("/simplewebapp/?loginSuccess=1" + returnPage);
        } else {
            out.println("Incorrect Username or Password");
        }
    }
%>
</body>
</html>
