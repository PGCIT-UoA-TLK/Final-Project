<%@ page import="simplewebapp.UserDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account Information</title>

    <%@include file="include/specialIncludeFiles.jsp"%>
</head>
<body>

<%@include file="include/userBar.jsp"%>

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
<div class="container">
    <div class="col-xs-12">
        <form class="form-horizontal" id="editUserForm">
            <fieldset>
                <legend>Account Information</legend>
                <div class="form-group">
                    <label for="input-username" class="col-sm-2 control-label">Username</label>
                    <div class="col-sm-10">
                    <input type="text" id="input-username" class="form-control" name="username" value="<%=user.getUsername()%>" disabled><br/>
                </div>
                    </div>
                <div class="form-group">
                    <label for="input-password" class="col-sm-2 control-label">Password</label>
                    <div class="col-sm-10">
                        <input type="text" id="input-password" name="password" class="form-control" value="Password Hidden" disabled><br/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="input-firstname" class="col-sm-2 control-label">First Name</label>
                    <div class="col-sm-10">
                        <input type="text" id="input-firstname" name="firstname" class="form-control" value="<%=user.getFirstname()%>"><br/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="input-lastname" class="col-sm-2 control-label">Last Name</label>
                    <div class="col-sm-10">
                        <input type="text" id="input-lastname" name="lastname" class="form-control" value="<%=user.getLastname()%>"><br/>
                    </div>
                </div>
                <input type="hidden" name="page" value="editUser">
                <input type="submit" class="btn btn-default pull-right" value="Change details">
                <input type="button" class="btn btn-default pull-right" value="Delete account" onclick="confirmDelete('editUserForm')">
                <input type="hidden" id="delete" name="delete">
            </fieldset>
        </form>
    </div>
</div>
<%
    if (request.getParameter("delete") != null && !request.getParameter("delete").equals("")) {
        UserDAO.getInstance().deleteUser(user);
        response.sendRedirect("/simplewebapp/?logout=1");
    }

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
%>

</body>
</html>
