<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<%@page contentType="text/html" %>
<%@ page import="java.util.List" %>
<%@ page import="simplewebapp.*" %>
<html>
<head>
    <title>Simple Blog</title>
</head>

<%@ page language="java" %>

<body>

<section id="view" class="container">

    <%@include file="include/userDetails.jsp" %>

    <%!
        private void addComment(String newComment, int articleID, int userID) {
            ArticleDAO.getInstance().addNewComment(newComment, articleID, userID);
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
        int userID = user.getId();
    %>

    <section class="article">
        <h1><%=articleTitle%>
        </h1>
        <p><%=articleBody%>
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
            addComment(newComment, articleID, userID);
            response.sendRedirect("/simplewebapp/?page=article&article=" + articleID);
        } else {

        }

        List<Comment> articleComments = getComments(articleID);
        for (Comment c : articleComments) {
            String body = c.getBody();
            int commentID = c.getComment_id();
            int commentUserID = c.getUser_id();
            User poster = UserDAO.getInstance().getUser(commentUserID);
            String username = poster.getUsername();

    %>
    <section class="comments">
        <p> <%=username%>: <%=body%> </p>
        <form>
            <input type="hidden" name="page" value="editComment"/>
            <input type="hidden" name="articleID" value="<%= articleID %>"/>
            <input type="hidden" name="commentID" value="<%= commentID %>"/>
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
