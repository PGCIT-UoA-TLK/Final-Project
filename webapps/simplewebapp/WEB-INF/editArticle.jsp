<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<%@page contentType="text/html"%>
<%@ page language="java" import="java.sql.*,java.util.List,java.util.ArrayList" %>
<%@ page import="simplewebapp.ArticleDAO" %>
<%@ page import="simplewebapp.Article" %>


<html>

<head>
    <title>Edit Article</title>
</head>
<%
    int articleID = Integer.parseInt(request.getParameter("articleID"));
    Article a = getArticleText(articleID);
    String articleTitle;
    if(request.getParameter("artTitle") != null){
        articleTitle = request.getParameter("artTitle");
    }else{
        articleTitle = a.getTitle();
    }
    String articleBody;
    if(request.getParameter("articleText") != null){
        articleBody = request.getParameter("articleText");
    }else{
        articleBody = a.getBody();
    }
    out.println(articleBody);
    out.println(articleTitle);

%>

<%@ page language="java"%>

<body>

<form>
    <fieldset>
        <legend>Edit the Body of an Article:</legend>
        <label for="artTitle">Title: </label>
        <input type="text" id ="artTitle" name="artTitle" value="<%=articleTitle%>"><br><br>
        <label for="articleText">Article Text: </label>
        <textarea name="articleText" rows="15" cols="50" id="articleText"><%=articleBody%></textarea><br><br>
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
    } %>
<%

   if((!(a.getTitle().
           equals(articleTitle)))
           ||(!(a.getBody().
           equals(articleBody)))){
        String newTitle = request.getParameter("artTitle");
        String articleText = request.getParameter("articleText");
        a.setBody(articleText);
        a.setTitle(newTitle);
       %>dfjf<%
        editArticle(a);
//        response.sendRedirect("/simplewebapp/");
    }else{

    }

%>
</body>
</html>
