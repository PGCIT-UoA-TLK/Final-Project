<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<%@page contentType="text/html" %>
<%@ page import="simplewebapp.Article" %>
<%@ page import="simplewebapp.ArticleDAO" %>
<html>

<head>
    <title>Edit Article</title>

    <%@ include file="include/specialIncludeFiles.jsp" %>
</head>

<%@ page language="java" %>

<body>

<%@ include file="include/userBar.jsp" %>

<%!
    private void deleteArticle(Article article) {
        ArticleDAO.getInstance().deleteArticle(article);
    }
%>
<%
    int articleID = Integer.parseInt(request.getParameter("articleID"));
    Article article = ArticleDAO.getInstance().getByArticleID(articleID);

    String newTitle = request.getParameter("articleTitle");
    String newBody = request.getParameter("articleBody");

    String articleTitle = (newTitle != null ? newTitle : article.getTitle());
    String articleBody = (newBody != null ? newBody : article.getBody());
%>
<div class="container">
    <div class="col-xs-12">
        <form class="form-horizontal" id="editArticleForm">
            <fieldset>
                <legend>Edit your Article:</legend>
                <div class="form-group">
                    <label for="articleTitle" class="col-sm-2 control-label">Title: </label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="articleTitle" name="articleTitle" value="<%=articleTitle%>" pattern="^\S.*?\S$"><br><br>
                    </div>
                </div>
                <div class="form-group">
                    <label for="articleBody" class="col-sm-2 control-label">Article Body: </label>
                    <div class="col-sm-10" id="titleDiv">
                        <textarea name="articleBody" class="form-control" rows="15" cols="50" id="articleBody"><%=articleBody%></textarea><br><br>
                    </div>
                </div>
                <input type="hidden" name="page" value="editArticle">
                <input type="hidden" name="articleID" value="<%=articleID%>">
                <input type="submit" class="btn btn-default pull-right"  value="Submit Changes">
                <input type="button" class="btn btn-default pull-right"  value="Delete Article" onclick="confirmDelete('editArticleForm')">
                <input type="hidden" id="delete" name="delete">
            </fieldset>
        </form>

            <%
    if (!article.getTitle().equals(articleTitle) || !article.getBody().equals(articleBody)) {
        article.setTitle(newTitle);
        article.setBody(newBody);

        ArticleDAO.getInstance().updateArticle(article);

        response.sendRedirect("/simplewebapp/?page=article&article=" + article.getID());
    }

    if (request.getParameter("delete") != null && !request.getParameter("delete").equals("")) {
        deleteArticle(article);
        response.sendRedirect("/simplewebapp/");
    }
%>
</body>
</html>
