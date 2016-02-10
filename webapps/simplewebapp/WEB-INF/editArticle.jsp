<%--@elvariable id="article" type="simplewebapp.Article"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>

<html>

<head>
    <title>Edit Article</title>

    <%@ include file="include/specialIncludeFiles.jsp" %>
</head>
<body>

<%@ include file="include/userBar.jsp" %>

<div class="container">
    <div class="col-xs-12">
        <form class="form-horizontal" id="editArticleForm">
            <fieldset>
                <legend>Edit your Article:</legend>

                <div class="col-sm-offset-2 col-sm-10">
                    <%@include file="include/alerts.jsp" %>
                </div>

                <div class="form-group">
                    <label for="articleTitle" class="col-sm-2 control-label">Title: </label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="articleTitle" name="articleTitle" value="${article.title}" pattern="^\S.*?\S$"><br><br>
                    </div>
                </div>
                <div class="form-group">
                    <label for="articleBody" class="col-sm-2 control-label">Article Body: </label>
                    <div class="col-sm-10" id="titleDiv">
                        <textarea name="articleBody" class="form-control" rows="15" cols="50" id="articleBody">${article.body}</textarea><br><br>
                    </div>
                </div>
                <input type="hidden" name="page" value="editArticle">
                <input type="hidden" name="articleID" value="${article.articleId}">
                <input type="submit" class="btn btn-default pull-right" value="Submit Changes">
                <input type="button" class="btn btn-default pull-right" value="Delete Article" onclick="confirmDelete('editArticleForm')">
                <input type="hidden" id="delete" name="delete">
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>
