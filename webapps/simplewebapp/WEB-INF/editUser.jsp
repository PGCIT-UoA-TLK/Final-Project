<%@ page import="simplewebapp.UserDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account Information</title>
</head>
<body>

<%@include file="include/userDetails.jsp"%>

<%
    if (user == null) {
        response.sendRedirect("/simplewebapp/");
    }

    boolean edited = false;

    String firstname = request.getParameter("firstname");
    if (firstname != null && !firstname.equals("")) {
        user.setFirstname(firstname); edited = true;
    }

    String lastname = request.getParameter("lastname");
    if (lastname != null && !lastname.equals("")) {
        user.setLastname(lastname); edited = true;
    }
%>

<form>
    <fieldset>
        <legend>Account Information</legend>
        <label for="input-username">Username</label><input type="text" id="input-username" name="username" value="<%=user.getUsername()%>" disabled><br/>
        <label for="input-password">Password</label><input type="text" id="input-password" name="password" value="Password Hidden" disabled><br/>
        <label for="input-firstname">First Name</label><input type="text" id="input-firstname" name="firstname" value="<%=user.getFirstname()%>"><br/>
        <label for="input-lastname">Last Name</label><input type="text" id="input-lastname" name="lastname" value="<%=user.getLastname()%>"><br/>
        <input type="hidden" name="page" value="editUser">
        <input type="submit" value="Change details">
        <input type="button" value="Delete account">
    </fieldset>
</form>

<%
    if (edited) {
        UserDAO userDAO = UserDAO.getInstance();

        boolean done = userDAO.updateUser(user);

        if (done) {
            session.setAttribute("user", user);
            response.sendRedirect("/simplewebapp/?page=editUser&success=1");
        } else {
            response.sendRedirect("/simplewebapp/?page=editUser&failure=1");
        }
    }

    if (request.getParameter("success") != null) {
        out.print("Account Details Saved!");
    } else if (request.getParameter("failure") != null) {
        out.print("Something went wrong! Please try again");
    }

    if (request.getParameter("delete") != null) {
        UserDAO.getInstance().deleteUser(user);
        response.sendRedirect("/simplewebapp/");
    }
%>

</body>
</html>
