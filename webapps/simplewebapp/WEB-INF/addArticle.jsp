<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<%@page contentType="text/html" %>
<%@ page import="simplewebapp.ArticleDAO" %>

<html>

<head>
    <title>Add a New Article</title>

    <%@ include file="include/specialIncludeFiles.jsp" %>
</head>

<%@ page language="java" %>

<body>

<%@include file="include/userBar.jsp" %>

<form>
    <fieldset>
        <legend>Add a New Article:</legend>
        <label for="articleTitle">Title: </label>
        <input type="text" id="articleTitle" name="articleTitle" placeholder="Article Title" required pattern="^\S.*?\S$"><br><br>
        <label for="articleText">Article Text: </label>
        <textarea name="articleText" rows="15" cols="50" placeholder="Article Text" id="articleText" required></textarea><br><br>
        <input type="hidden" name="page" value="addArticle"/>
        <input type="submit" value="Submit New Article">
    </fieldset>
</form>
<%

    if (request.getParameter("articleTitle") != null) {
        String newTitle = request.getParameter("articleTitle");
        String articleText = request.getParameter("articleText");

        ArticleDAO.getInstance().addNewArticle(user.getId(), newTitle, articleText);

        response.sendRedirect("/simplewebapp/");
    }

%>
</body>
</html>
