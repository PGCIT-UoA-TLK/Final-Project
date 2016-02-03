<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<%@page contentType="text/html"%>
<%@ page import="simplewebapp.Article" %>
<%@ page import="simplewebapp.ArticleDAO" %>

<html>

<head>
    <title>Edit Article</title>
</head>
<%
    int articleID = Integer.parseInt(request.getParameter("articleID"));
    Article article = ArticleDAO.getInstance().getByArticleID(articleID);

    String newTitle = request.getParameter("articleTitle");
    String newBody = request.getParameter("articleBody");

    String articleTitle = (newTitle != null ? newTitle : article.getTitle());
    String articleBody = (newBody != null ? newBody : article.getBody());
%>

<%@ page language="java"%>

<body>

<form>
    <fieldset>
        <legend>Edit the Body of an Article:</legend>
        <label for="articleTitle">Title: </label><input type="text" id ="articleTitle" name="articleTitle" value="<%=articleTitle%>"><br><br>
        <label for="articleBody">Article Body: </label><textarea name="articleBody" rows="15" cols="50" id="articleBody"><%=articleBody%></textarea><br><br>
        <input type="hidden" name="page" value="editArticle">
        <input type="hidden" name="articleID" value="<%=articleID%>">
        <input type="submit" value="Submit Changes">
    </fieldset>
</form>

<%

    if (!article.getTitle().equals(articleTitle) || !article.getBody().equals(articleBody)) {
        article.setTitle(newTitle);
        article.setBody(newBody);

        ArticleDAO.getInstance().updateArticle(article);

        response.sendRedirect("/simplewebapp/?page=article&article=" + article.getID());
    }

%>
</body>
</html>
