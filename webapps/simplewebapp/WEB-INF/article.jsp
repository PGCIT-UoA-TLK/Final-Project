<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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


        private void addComment(String newComment, int articleID ){
            ArticleDAO.getInstance().addNewComment(newComment, articleID);
        }

        private List<Comment> getComments(int articleID) {
            List<Comment> comments = ArticleDAO.getInstance().getCommentsByArticleID(articleID);
            return comments;
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
    </section>
    <%
        if(request.getParameter("commentBox") != null) {
            String newComment = request.getParameter("commentBox");
            addComment(newComment, articleID);
            response.sendRedirect("/simplewebapp/?page=article&article=" + articleID);
        }else{

        }

        List<Comment> articleComments = getComments(articleID);
        for(Comment c: articleComments){
            String body = c.getBody();
            System.out.println(body);

    %>

    <section class="comments">
        <%= body %>
    </section>
    <%
        }
    %>


    <section class="addComment">
        <form>
            <fieldset>
                <legend>Add a Comment</legend>
                <label for="commentBox">Comments: </label>
                <textarea name="commentBox" id="commentBox" rows="5" cols="40" placeholder="Comments"></textarea><br><br>
                <input type="hidden" name="article" value="<%= articleID %>">
                <input type="hidden" name="page" value="article">
                <input type="submit" value="Submit">
            </fieldset>
        </form>
    </section>
</section>
</body>
</html>
