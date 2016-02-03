<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<%@page contentType="text/html" %>
<%@ page language="java" import="java.sql.*,java.util.List,java.util.ArrayList" %>
<%@ page import="simplewebapp.Article" %>
<%@ page import="simplewebapp.ArticleDAO" %>
<%@ page import="simplewebapp.Comment" %>
<html>
<head>
    <title>Simple Blog</title>
</head>

<%@ page language="java" %>

<body>

<section id="view" class="container">
    <%!


        private void addComment(String newComment, int articleID) {
            ArticleDAO.getInstance().addNewComment(newComment, articleID);
        }

        private List<Comment> getComments(int articleID) {
            return ArticleDAO.getInstance().getCommentsByArticleID(articleID);
        }

    %>

    <%
        Article a = (Article) request.getAttribute("Article");

        String articleTitle = a.getTitle();
        String articleBody = a.getBody();
        int articleID = a.getID();
    %>
    <section class="article">
        <h1>
            <%= articleTitle %>
        </h1>
        <p>
            <%= articleBody %>
        </p>
        <div class="editor">
            <form>
                <input type="hidden" name="page" value="editArticle"/>
                <input type="hidden" name="articleID" value="<%=articleID%>"/>
                <input type="submit" value="Edit"/><br/><br/>
            </form>
        </div>
    </section>
    <%
        if (request.getParameter("commentBox") != null) {
            String newComment = request.getParameter("commentBox");
            addComment(newComment, articleID);
            response.sendRedirect("/simplewebapp/?page=article&article=" + articleID);
        } else {

        }

        List<Comment> articleComments = getComments(articleID);
        for (Comment c : articleComments) {
            String body = c.getBody();
            int commentID = c.getComment_id();


    %>

    <section class="comments">
        <%= body %>
        <form>
            <input type="hidden" name="page" value="editComment"/>
            <input type="hidden" name="articleID" value="<%= articleID %>"/>
            <input type="hidden" name="commentID" value="<%= commentID%>"/>
            <input type="submit" value="Edit"/>
        </form>
    </section>
    <%
        }
    %>
    <section class="addComment">
        <form>
            <fieldset>
                <legend>Comment</legend>
                <textarea name="commentBox" id="commentBox" rows="5" cols="40"
                          placeholder="Comments"></textarea><br><br>
                <input type="hidden" name="article" value="<%= articleID %>">
                <input type="hidden" name="page" value="article">
                <input type="submit" value="Submit">
            </fieldset>
        </form>
    </section>
</section>
</body>
</html>
