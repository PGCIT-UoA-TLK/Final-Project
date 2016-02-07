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
    <div class="container">
        <div class="jumbotron">
            <div class = col-md>
                <section class="article">
                    <div class="articleTitle"><h1><%=articleTitle%></h1></div>
                    <div class="articleText"><p><%=articleBody%> </p></div>

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
            </div>
        </div>
    </div>
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
    <div class="container">
        <div class = "row">
            <div class ="col-sm">
                <section class="comments">
                    <p class="comment"> <%=poster.getUsername() %>: <%=body%></p>
        <span><form>

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

        </form></span>

                </section>
            </div>
        </div>
    </div>
    <%
        }
        if(user != null){
            if (request.getParameter("commentBox") != null) {
                String newComment = request.getParameter("commentBox");
                addComment(newComment, articleID, user.getId());
                response.sendRedirect("/simplewebapp/?page=article&article=" + articleID);
            }
    %>
    <div class="container-fluid">

        <div class="commentBox"> <section class="addComment">
            <form>
                <fieldset>
                    <legend>Comment</legend>
                <textarea name="commentBox" id="commentBox" rows="5" cols="40"
                          placeholder="Comments" required></textarea><br><br>
                    <input type="hidden" name="article" value="<%= articleID %>">
                    <input type="hidden" name="page" value="article">
                    <input type="submit" value="Submit">
                </fieldset>
            </form>
        </section></div></div>
    <% } %>
</section>
</body>
</html>
