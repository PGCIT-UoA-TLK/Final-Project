<%@ page import="simplewebapp.User" %>
<%@ page import="simplewebapp.UserDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Registration</title>
</head>
<body>
<form>
    <fieldset>
        <legend>User Registration</legend>
        <label for="input-username">Username</label><input type="text" id="input-username" name="username"><br/>
        <label for="input-password">Password</label><input type="password" id="input-password" name="password"><br/>
        <label for="input-firstname">First Name</label><input type="text" id="input-firstname" name="firstname"><br/>
        <label for="input-lastname">Last Name</label><input type="text" id="input-lastname" name="lastname"><br/>
        <input type="hidden" name="page" value="addUser">
        <input type="submit" value="Register">
    </fieldset>
</form>

<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String firstname = request.getParameter("firstname");
    String lastname = request.getParameter("lastname");

    if (username != null && !username.equals("") && password != null && !password.equals("") &&
            firstname != null && !firstname.equals("") && lastname != null && !lastname.equals("")) {
        UserDAO userDAO = UserDAO.getInstance();

        User user = userDAO.addUser(username, password, firstname, lastname);

        if (user != null) {
            session.setAttribute("user", user);
            response.sendRedirect("/simplewebapp/");
        } else {

        }
    }
%>
</body>
</html>
