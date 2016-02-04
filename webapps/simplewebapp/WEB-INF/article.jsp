<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<%@page contentType="text/html" %>
<%@ page import="java.util.List" %>
<%@ page import="simplewebapp.*" %>
<html>
<head>
    <title>Simple Blog</title>

    <%@ include file="include/specialIncludeFiles.jsp" %>
</head>

<%@ page language="java" %>

<body>

<section id="view" class="container">

    <%@include file="include/userBar.jsp" %>

    <%!
        private void addComment(String newComment, int articleID, int userID) {
            ArticleDAO.getInstance().addNewComment(newComment, articleID, userID);
        }

        Comment getComment(int articleID, int commentID) {
            return ArticleDAO.getInstance().getCommentByArticleIDAndCommentID(articleID, commentID);
        }

        private List<Comment> getComments(int articleID) {
            return ArticleDAO.getInstance().getCommentsByArticleID(articleID);
        }

        void deleteComment(Comment comment) {
            ArticleDAO.getInstance().deleteComment(comment);
        }
    %>

    <%
        Article a = (Article) request.getAttribute("Article");
        String articleTitle = a.getTitle();
        String articleBody = a.getBody();
        int articleID = a.getID();

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
                <%
                    if(user != null){
                        if (user.getId()== a.getUserID()) {
                %>
                <input type="submit" value="Edit"/>
                <%
                        }
                    }
                %>
            </form>
        </div>
    </section>

    <%
        if(request.getParameter("delete") != null) {
            Comment c = getComment(articleID,Integer.parseInt(request.getParameter("commentID")));
            deleteComment(c);
            response.sendRedirect("/simplewebapp/?page=article&article=" + articleID);
        }

        List<Comment> articleComments = getComments(articleID);
        for (Comment c : articleComments) {
            String body = c.getBody();
            int commentID = c.getComment_id();
            int commentUserID = c.getUser_id();
            User poster = UserDAO.getInstance().getUser(commentUserID);

    %>
    <section class="comments">
        <p> <%=poster.getUsername() %>: <%=body%> </p>
        <form>

            <input type="hidden" name="article" value="<%= articleID %>"/>
            <input type="hidden" name="commentID" value="<%= commentID %>"/>
            <%
                if(user != null){
                    if (user.getId()== commentUserID) {
            %>
            <input type="hidden" name="page" value="editComment"/>
            <input type="submit" value="Edit"/>
            <%
            }else if (user.getId() == a.getUserID()){
            %>
            <input type="hidden" name="page" value="article">
            <input type="hidden" name="delete" value="1"/>
            <input type="submit"  value="Delete">
            <%
                    }
                }

            %>

        </form>
    </section>
    <%
        }
        if(user != null){
            if (request.getParameter("commentBox") != null) {
                String newComment = request.getParameter("commentBox");
                addComment(newComment, articleID, user.getId());
                response.sendRedirect("/simplewebapp/?page=article&article=" + articleID);
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
    <% } %>
</section>
</body>
</html>
