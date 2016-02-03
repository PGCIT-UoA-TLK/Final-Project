<%@ page import="simplewebapp.User" %>
<%@ page import="simplewebapp.UserDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form>
    <fieldset>
        <legend>Login</legend>
        <label for="username">Username</label><input type="text" id="username" name="username">
        <label for="password">Password</label><input type="password" id="password" name="password">
        <input type="hidden" name="page" value="loginUser">
        <input type="submit" value="Log In">
    </fieldset>
</form>

<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    if (username != null && password != null) {
        UserDAO userDAO = UserDAO.getInstance();

        User user = userDAO.loginUser(username, password);

        if (user != null) {
            session.setAttribute("user", user);
            response.sendRedirect("/simplewebapp/?loginSuccess=1");
        } else {
            out.println("Incorrect Username or Password");
        }
    }
%>
</body>
</html>
