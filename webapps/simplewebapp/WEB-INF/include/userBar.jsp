<%--suppress HtmlUnknownTarget --%>
<%@ page import="simplewebapp.User" %>
<%
    User user = null;
    if (request.getParameter("logout") != null) {
        session.setAttribute("user", null);
    } else if (session.getAttribute("user") != null) {
        user = (User) session.getAttribute("user");
    }
%>
<div id="user-bar" class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/simplewebapp/">Latin Enthusiasts</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse navbar-right" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <% if (user != null) { %>
                <li><%=user.getUsername()%></li>
                <% } %>
                <% if (user != null) { %>
                <li><a href="/simplewebapp/?page=editUser">My Account</a></li>
                <li><a href="/simplewebapp/?logout">Logout</a></li>
                <% } else { %>
                <li><a href="/simplewebapp/?page=loginUser">Login</a></li>
                <li><a href="/simplewebapp/?page=addUser">Register</a></li>
                <% } %>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div>
</div>