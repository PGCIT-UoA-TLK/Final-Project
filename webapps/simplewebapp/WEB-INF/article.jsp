<%@ page import="simplewebapp.DatabaseDAO" %>
<%@ page import="simplewebapp.UploadedFileDAO" %><%--@elvariable id="article" type="simplewebapp.Article"--%>
<%--@elvariable id="comments" type="java.util.List<simplewebapp.Comment>"--%>
<%--@elvariable id="files" type="java.util.List<simplewebapp.File>"--%>
<%--@elvariable id="user" type="simplewebapp.User"--%>
<%--@elvariable id="commentBox" type="java.lang.String"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>

<html>
<head>
    <title>Simple Blog</title>

    <%@ include file="include/specialIncludeFiles.jsp" %>
</head>
<body>

<%@include file="include/userBar.jsp" %>

<div class="container">
    <div class="jumbotron">
        <div class="articleTitle"><h1>${article.title}</h1></div>
        <div class="articleText"><p>${article.body}</p></div>
    </div>



    <c:forEach items="${files}" var="file">
        <c:if test="${article.articleId == file.articleId}">
            <div class="col-xs-4"><img class="img-responsive img-rounded" src="${file.filepath}"></div>
        </c:if>
    </c:forEach>


    <div class="col-xs-12">
        <form class="form-select-button pull-right">
            <input type="hidden" name="page" value="editArticle"/>
            <input type="hidden" name="articleID" value="${article.articleId}"/>
            <c:choose>
                <c:when test="${(not empty user) && (user.userId == article.userId)}">
                    <input type="submit" class="btn btn-default" value="Edit Article"/>
                </c:when>
            </c:choose>
        </form>
    </div>

    <c:forEach items="${comments}" var="comment">
        <blockquote>
            <p>${comment.body}</p>

            <form id="articleCommentForm" class="form-horizontal pull-right">
                <input type="hidden" name="article" value="${article.articleId}"/>
                <input type="hidden" name="commentID" value="${comment.commentId}"/>

                <c:choose>
                    <c:when test="${(not empty comment.userId) && (user.userId == comment.userId)}">
                        <input type="hidden" name="page" value="editComment"/>
                        <input type="submit" class="btn btn-default" value="Edit Comment"/>
                    </c:when>
                    <c:when test="${(article.userId == user.userId)}">
                        <input type="hidden" name="page" value="article">
                        <input type="hidden" name="delete"/>
                        <input type="submit" class="btn btn-default" value="Delete Comment" onclick="confirmDelete('articleCommentForm')">
                    </c:when>
                </c:choose>
            </form>

            <footer>${comment.user.username}</footer>
        </blockquote>
    </c:forEach>

    <c:if test="${(not empty user)}">
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
                    <input type="hidden" name="article" value="${article.articleId}">
                    <input type="hidden" name="page" value="article">
                    <input type="submit" class="btn btn-default pull-right" value="Submit">
                </fieldset>
            </form>
        </div>
    </c:if>
</div>
</body>
</html>
