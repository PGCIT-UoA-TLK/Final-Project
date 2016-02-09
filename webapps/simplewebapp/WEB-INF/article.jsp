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

<%@include file="include/userBar.jsp" %>

<%!
    private void addComment(String newComment, int articleID, int userID) {
        CommentDAO.getInstance().addNewComment(newComment, articleID, userID);
    }

    Comment getComment(int articleID, int commentID) {
        return CommentDAO.getInstance().getCommentByArticleIDAndCommentID(articleID, commentID);
    }

    private List<Comment> getComments(int articleID) {
        return CommentDAO.getInstance().getCommentsByArticleID(articleID);
    }

    void deleteComment(Comment comment) {
        CommentDAO.getInstance().deleteComment(comment);
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

        <div class="articleTitle"><h1><%=articleTitle%></h1></div>
        <div class="articleText"><p><%=articleBody%> </p></div>

        </div>


<div class="col-xs-12">
    <form class="form-select-button pull-right">
        <input type="hidden" name="page" value="editArticle"/>
        <input type="hidden" name="articleID" value="<%=articleID%>"/>
        <%
            if(user != null){
                if (user.getId()== a.getUserID()) {
        %>
        <input type="submit"  class="btn btn-default"  value="Edit Article"/>
        <%
                }
            }
        %>
    </form>
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

<div class ="col-xs-12">
    <p class="comment"> <%=poster.getUsername() %>: <%=body%></p>
</div>

            <form class="form-horizontal pull-right">

                <input type="hidden" name="article" value="<%= articleID %>"/>
                <input type="hidden" name="commentID" value="<%= commentID %>"/>
                <%
                    if(user != null){
                        if (user.getId()== commentUserID) {
                %>
                <input type="hidden" name="page" value="editComment"/>
                <input type="submit"  class="btn btn-default" value="Edit Comment"/>
                <%
                }else if (user.getId() == a.getUserID()){
                %>
                <input type="hidden" name="page" value="article">
                <input type="hidden" name="delete" value="1"/>
                <input type="submit"  class="btn btn-default" value="Delete Comment">
                <%
                        }
                    }

                %>

            </form>



<%
    }
    if(user != null){
        if (request.getParameter("commentBox") != null) {
            String newComment = request.getParameter("commentBox");
            addComment(newComment, articleID, user.getId());
            response.sendRedirect("/simplewebapp/?page=article&article=" + articleID);
        }
%>

    <div class="col-xs-12">
        <form class="form-horizontal">
            <fieldset>
                <legend>Comment</legend>
                <div class="form-group">
                    <div class="col-sm-12">
                <textarea name="commentBox" class="form-control" id="commentBox" rows="5" cols="40"
                          placeholder="Comments" required></textarea>
                    </div>
                </div>
                <input type="hidden" name="article" value="<%= articleID %>">
                <input type="hidden" name="page" value="article">
                <input type="submit" class="btn btn-default pull-right" value="Submit">
            </fieldset>
        </form>
    </div>

<% } %>
</div>
</body>
</html>
