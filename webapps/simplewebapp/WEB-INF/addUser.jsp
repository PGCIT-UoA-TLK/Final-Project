<%--
  Created by IntelliJ IDEA.
  User: tommo
  Date: 3/02/2016
  Time: 11:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Signup</title>
</head>
<body>
<form>
    Add new User
    <label for="input-username">Username</label><input type="text" id="input-username" name="username"><br/>
    <label for="input-password">Password</label><input type="password" id="input-password" name="password"><br/>
    <label for="input-firstname">First Name</label><input type="text" id="input-firstname" name="firstname"><br/>
    <label for="input-lastname">Last Name</label><input type="text" id="input-lastname" name="lastname"><br/>
    <input type="hidden" name="page" value="userAdd">
</form>

<%
    if (request.getParameter("username") != null && !request.getParameter("username").equals("") &&
            request.getParameter("password") != null && !request.getParameter("password").equals("") &&
            request.getParameter("firstname") != null && !request.getParameter("firstname").equals("") &&
            request.getParameter("lastname") != null && !request.getParameter("lastname").equals("")) {
%><%=request.getParameter("username")%>, <%=request.getParameter("password")%>, <%=request.getParameter("firstname")%>
, <%=request.getParameter("lastname")%><%


    }
%>
</body>
</html>
