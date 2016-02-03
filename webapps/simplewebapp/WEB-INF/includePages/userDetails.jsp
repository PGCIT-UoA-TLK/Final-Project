<%@ page import="simplewebapp.User" %>
<%
    User user = null;
    if (request.getParameter("logout") != null) {
        session.setAttribute("user", null);
    } else if (session.getAttribute("user") != null) {
        user = (User) session.getAttribute("user");
    }

    String currentPage = request.getParameter("page");
    if (currentPage != null) {
        currentPage = "page=" + currentPage;
    }
%>
<div id="user-bar">
    <% if (user != null) { %>
    <a href="/simplewebapp/?page=editUser">My Account</a>
    <a href="/simplewebapp/?<%if(currentPage != null){%><%=currentPage%>&<%}%>logout">Logout</a>
    <% } else { %>
    <a href="/simplewebapp/?page=loginUser">Login</a>
    <a href="/simplewebapp/?page=addUser">Register</a>
    <% } %>
</div>