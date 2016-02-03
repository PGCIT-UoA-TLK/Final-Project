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
    Article a = getArticleText(articleID);

    String articleTitle;
    if (request.getParameter("articleTitle") != null) {
        articleTitle = request.getParameter("articleTitle");
    } else {
        articleTitle = a.getTitle();
    }

    String articleBody;
    if(request.getParameter("articleText") != null){
        articleBody = request.getParameter("articleText");
    }else{
        articleBody = a.getBody();
    }
%>

<%@ page language="java"%>

<body>

<form>
    <fieldset>
        <legend>Edit the Body of an Article:</legend>
        <label for="articleTitle">Title: </label><input type="text" id ="articleTitle" name="articleTitle" value="<%=articleTitle%>"><br><br>
        <label for="articleText">Article Text: </label><textarea name="articleText" rows="15" cols="50" id="articleText"><%=articleBody%></textarea><br><br>
        <input type="hidden" name="page" value="editArticle">
        <input type="hidden" name="articleID" value="<%=articleID%>">
        <input type="submit" value="Submit Changes">
    </fieldset>
</form>

<%!
    private Article getArticleText(int articleID){
        return ArticleDAO.getInstance().getByArticleID(articleID);
    }

    private void editArticle(Article toUpdate){
        ArticleDAO.getInstance().updateArticle(toUpdate);
    }
%><%

    if (!a.getTitle().equals(articleTitle) || !a.getBody().equals(articleBody)) {
        String newTitle = request.getParameter("articleTitle");
        String articleText = request.getParameter("articleText");
        a.setBody(articleText);
        a.setTitle(newTitle);
        editArticle(a);
        //        response.sendRedirect("/simplewebapp/");
    }else{

    }

%>
</body>
</html>
