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
            <a class="list-group-item" href="<c:url value="?page=article&article=${article.articleId}"/>">
                    ${article.title}
            </a>
        </c:forEach>
    </div>
</div>

</body>
</html>
