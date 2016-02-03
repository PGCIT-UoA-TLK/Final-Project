<%@ page import="simplewebapp.Comment" %>
<%@ page import="simplewebapp.ArticleDAO" %><%--
  Created by IntelliJ IDEA.
  User: klev006
  Date: 3/02/2016
  Time: 4:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Comment</title>
</head>
<body>

<%@include file="includePages/userDetails.jsp"%>

<%
    int articleID = Integer.parseInt(request.getParameter("articleID"));
    int commentID = Integer.parseInt(request.getParameter("commentID"));
    Comment c = getComment(articleID,commentID);

    if(request.getParameter("edited") != null){
        c.setBody(request.getParameter("commentBox"));
        editComment(c);
        response.sendRedirect("/simplewebapp/?page=article&article=" + articleID);
    }else if(request.getParameter("delete") != null){
        deleteComment(c);
    }
%>
<%! Comment getComment(int articleID, int commentID) {
    return ArticleDAO.getInstance().getCommentByArticleIDAndCommentID(articleID, commentID);
}
    void editComment(Comment comment){
        ArticleDAO.getInstance().updateComment(comment);
    }
    void deleteComment(Comment comment) {
        ArticleDAO.getInstance().deleteComment(comment);
    }

%>

<form>
    <fieldset>
        <legend>Comment</legend>
        <textarea name="commentBox" id="commentBox" rows="5" cols="40" title="Comment Box"><%=c.getBody()%></textarea><br><br>
        <input type="hidden" name="articleID" value="<%= articleID %>" />
        <input type="hidden" name="commentID" value="<%=commentID%>" />
        <input type="hidden" name="page" value="editComment">
        <input type="hidden" name="edited" value="1">
        <input type="submit" value="Submit">
        <input type="submit" value="Delete Comment" name="delete" />
    </fieldset>
</form>
</body>
</html>
