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

<%@include file="include/userBar.jsp"%>
<div class="container">
    <div class="col-xs-12">
        <form class="form-horizontal">
            <fieldset>
                <legend>User Registration</legend>
                <div class="form-group">
                    <label for="input-username" class="col-sm-2 control-label">Username</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="input-username" name="username" required pattern="^[a-zA-Z0-9._%+-]{1,15}"><br/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="input-password" class="col-sm-2 control-label">Password</label>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" id="input-password" name="password" required pattern ="^[a-zA-Z0-9._%+-]{1,15}"><br/>
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

                <div class="radio"  id="input-icon_name" >
                    <label>
                        <input type="radio" name="optionsRadios" id="optionsRadios1" value="1" checked>
                        <img src="/Images/IMG1.jpg" alt="Image is not available" class="img-thumbnail"  WIDTH=89 HEIGHT=89>
                    </label>
                </div>
                <div class="radio">
                    <label>


                        <input type="radio" name="optionsRadios" id="optionsRadios2" value="2">
                        <img src="/Images/IMG2.jpg" alt="Image is not available" class="img-thumbnail"  WIDTH=89 HEIGHT=119>
                    </label>
                </div>

                <input type="hidden" name="page" value="addUser">
                <input type="submit" class="btn btn-default pull-right" value="Register">
            </fieldset>
        </form>

            <%
    String username = request.getParameter("username");

    String password = request.getParameter("password");
    String firstname = request.getParameter("firstname");
    String lastname = request.getParameter("lastname");
    Boolean unique = true;
    List<User> allUsers = UserDAO.getInstance().getAll();

    for(User u: allUsers) {
       // username = username.trim();
        if (username != null && u.getUsername() != null && username.equals(u.getUsername())) {
            out.print("That username is taken.");
            unique = false;
            break;
        }
    }

    if (unique && username != null && !username.equals("") && password != null && !password.equals("") &&
            firstname != null && !firstname.equals("") && lastname != null && !lastname.equals("")) {
        UserDAO userDAO = UserDAO.getInstance();

          int icon=0;
        String selection =request.getParameter("optionsRadios");
        if(selection.equals("option1")){
            icon=1;
        }else if(selection.equals("option2")){
            icon=2;
        }
        User newUser = userDAO.addUser(username, password, firstname, lastname, icon);


        if (newUser != null) {
            session.setAttribute("user", newUser);

            String returnPage = "";
            if (request.getParameter("backpage") != null) {
                returnPage = "?page=" + request.getParameter("backpage");
            }

            response.sendRedirect("/simplewebapp/" + returnPage);
        }
    }
%>
</body>
</html>
