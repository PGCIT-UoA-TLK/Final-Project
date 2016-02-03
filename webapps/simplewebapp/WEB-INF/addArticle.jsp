<%--
  Created by IntelliJ IDEA.
  User: klev006
  Date: 2/02/2016
  Time: 12:38 PM
  To change this template use File | Settings | File Templates.
--%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<%@page contentType="text/html"%>
<%@ page language="java" import="java.sql.*,java.util.List,java.util.ArrayList" %>
<%@ page import="simplewebapp.ArticleDAO" %>
<%@ page import="simplewebapp.Article" %>


<html>

<head>
    <title>Add a New Article</title>
</head>

<%@ page language="java"%>

<body>

<form>
    <fieldset>
        <legend>Add a New Article:</legend>
        <label for="artTitle">Title: </label>
        <input type="text" id ="artTitle" name="artTitle" placeholder="Article Title"><br><br>
        <label for="articleText">Article Text: </label>
        <textarea name="articleText" rows="15" cols="50" placeholder="Article Text" id="articleText"></textarea><br><br>
        <input type="hidden" name="page" value="addArticle"/>
        <input type="submit" value="Submit New Article">
    </fieldset>
</form>

<%!
    private void addArticle(String newTitle, String articleText){
        ArticleDAO.getInstance().addNewArticle(newTitle, articleText);
    } %>
<%

    if(request.getParameter("artTitle") != null) {
        String newTitle = request.getParameter("artTitle");
        String articleText = request.getParameter("articleText");
        addArticle(newTitle, articleText);
        response.sendRedirect("/simplewebapp/");
    }else{

    }

%>
</body>
</html>
