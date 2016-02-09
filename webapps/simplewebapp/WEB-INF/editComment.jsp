<%--@elvariable id="comment" type="simplewebapp.Comment"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Comment</title>

    <%@ include file="include/specialIncludeFiles.jsp" %>
</head>
<body>

<%@include file="include/userBar.jsp"%>

<div class="container">
    <div class="col-xs-12">
        <form id="editCommentForm" class="form-horizontal">
            <fieldset>
                <legend>Comment</legend>

                <%@include file="include/alerts.jsp" %>

                <div class="form-group">
                    <div class="col-sm-12">
                        <textarea name="commentBox" class="form-control" id="commentBox" rows="5" cols="40" title="Comment Box">${comment.body}</textarea><br><br>
                    </div>
                </div>
                <input type="hidden" name="article" value="${comment.articleId}" />
                <input type="hidden" name="commentID" value="${comment.commentId}" />
                <input type="hidden" name="page" value="editComment" />
                <input type="hidden" name="edited" value="1" />
                <input type="submit" class="btn btn-default pull-right" value="Submit" />
                <input type="submit" value="Delete Comment" class="btn btn-default pull-right" onclick="confirmDelete('editCommentForm')" />
                <input type="hidden" id="delete" name="delete">
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>
