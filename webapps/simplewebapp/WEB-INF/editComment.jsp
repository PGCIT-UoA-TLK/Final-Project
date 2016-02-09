<%@ page import="simplewebapp.Comment" %>
<%@ page import="simplewebapp.CommentDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Comment</title>

    <%@ include file="include/specialIncludeFiles.jsp" %>
</head>
<body>

<%@include file="include/userBar.jsp"%>

<%
    int articleID = Integer.parseInt(request.getParameter("article"));
    int commentID = Integer.parseInt(request.getParameter("commentID"));
    Comment c = getComment(articleID,commentID);

    if(request.getParameter("edited") != null && request.getParameter("delete") == null){
        c.setBody(request.getParameter("commentBox"));
        editComment(c);
        response.sendRedirect("/simplewebapp/?page=article&article=" + articleID);
    }else if(request.getParameter("delete") != null){
        deleteComment(c);
        response.sendRedirect("/simplewebapp/?page=article&article=" + articleID);
    }else{

    }
%>
<%! Comment getComment(int articleID, int commentID) {
    return CommentDAO.getInstance().getCommentByArticleIDAndCommentID(articleID, commentID);
}
    void editComment(Comment comment){
        CommentDAO.getInstance().updateComment(comment);
    }
    void deleteComment(Comment comment) {
        CommentDAO.getInstance().deleteComment(comment);
    }

%>
<div class="container">
    <div class="col-xs-12">
        <form class="form-horizontal">
            <fieldset>
                <legend>Comment</legend>
                <div class="form-group">
                    <div class="col-sm-12">
                        <textarea name="commentBox" class="form-control" id="commentBox" rows="5" cols="40" title="Comment Box"><%=c.getBody()%></textarea><br><br>
                    </div>
                </div>
                <input type="hidden" name="article" value="<%= articleID %>" />
                <input type="hidden" name="commentID" value="<%=commentID%>" />
                <input type="hidden" name="page" value="editComment" />
                <input type="hidden" name="edited" value="1" />
                <input type="submit" class="btn btn-default pull-right" value="Submit" />
                <input type="submit" value="Delete Comment" class="btn btn-default pull-right" name="delete" />
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>
