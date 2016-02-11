<%--@elvariable id="comment" type="simplewebapp.Comment"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>

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

                <div class="col-sm-offset-2 col-sm-10">
                    <%@include file="include/alerts.jsp" %>
                </div>

                <div class="form-group">
                    <div class="col-sm-12">
                        <textarea name="commentBox" class="form-control" id="commentBox" rows="5" cols="40" title="Comment Box" placeholder="200 Character Limit">${comment.body}</textarea><br><br>
                    </div>
                </div>
                <input type="hidden" name="article" value="${comment.articleId}" />
                <input type="hidden" name="commentID" value="${comment.commentId}" />
                <input type="hidden" name="page" value="editComment" />
                <input type="hidden" name="edited" value="1" />
                <input type="submit" class="btn btn-success pull-right" value="Submit" />
                <input type="submit" class="btn btn-danger pull-right" value="Delete Comment" onclick="confirmDelete('editCommentForm')" />
                <input type="hidden" id="delete" name="delete">
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>
