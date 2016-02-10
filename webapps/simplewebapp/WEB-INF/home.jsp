<%--@elvariable id="articles" type="java.util.List<simplewebapp.Article>"--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>

<html>
<head>
    <title>Simple Blog</title>

    <%@include file="include/specialIncludeFiles.jsp" %>
</head>
<body>

<%@include file="include/userBar.jsp" %>
<div class="container">
    <div class="list-group">
        <c:forEach items="${articles}" var="article">
            <div class="col-xs-12">
                <a class="list-group-item" href="<c:url value="?page=article&article=${article.articleId}"/>">
                        ${article.title}
                    <c:set var="numberOfComments" value="${article.comments.size()}"/>
                    <c:choose>
                        <c:when test="${numberOfComments > 1}">
                            <span class="badge">${numberOfComments} Comments</span>
                        </c:when>
                        <c:when test="${numberOfComments > 0}">
                            <span class="badge">${numberOfComments} Comment</span>
                        </c:when>
                    </c:choose>
                    <br/>
                    <small class="pull-right">${article.body}</small>
                    <div class="clearfix"></div>
                </a>
            </div>
        </c:forEach>
    </div>
</div>

</body>
</html>
