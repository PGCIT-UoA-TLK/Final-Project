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




<section id="view" class="container">
    <% if(user != null){ %>
    <p>
        <a href="?page=addArticle&addArticle=1">Add a New Article</a>
    </p>
    <% }

        List<Article> articles = (List<Article>) request.getAttribute("Articles");

        for (Article a : articles) {
            String articleTitle = a.getTitle();
            int articleID = a.getID();
            String linkURI = String.format("?page=article&article=%d", articleID);
    %>
    <section class="article">
        <p>
            <a href="<%= linkURI%>">
                <%= articleTitle %>
            </a>
        </p>
    </section>
    <%
        }
    %>
</section>
</body>
</html>
