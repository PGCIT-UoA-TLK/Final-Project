<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<%@page contentType="text/html" %>
<%@ page import="simplewebapp.Article" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Simple Blog</title>

    <%@include file="include/specialIncludeFiles.jsp"%>
</head>

<%@ page language="java" %>

<body>

<%@include file="include/userBar.jsp"%>


<ul>

<div id="view" class="jumbotron">

    <% if(user != null){ %>

        <li class="articleList"><a href="?page=addArticle&addArticle=1">Add a New Article</a></li>

    <% }

        List<Article> articles = (List<Article>) request.getAttribute("Articles");

        for (Article a : articles) {
            String articleTitle = a.getTitle();
            int articleID = a.getID();
            String linkURI = String.format("?page=article&article=%d", articleID);
    %>
    <div class="container">
        <div class="row"></div>
             <div class="col-sm-4"></div>
        <li class="articleList">
            <a href="<%= linkURI%>">
                <%= articleTitle %>
            </a>
        </li>
    </div>

    <%
        }
    %>
</div>
</ul>
</body>
</html>
