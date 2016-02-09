<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>

<html>
<head>
    <title>Add a New Article</title>

    <%@ include file="include/specialIncludeFiles.jsp" %>
</head>
<body>

<%@include file="include/userBar.jsp" %>
<div class="container">
    <div class="col-xs-12">
        <form class="form-horizontal">
            <fieldset>
                <legend>Add a New Article:</legend>
                <div class="form-group">
                    <label for="articleTitle" class="col-sm-2 control-label">Title: </label>
                    <div class="col-sm-10" id="titleDiv">
                        <input type="text" id="articleTitle" class="form-control" name="articleTitle" placeholder="Article Title" required pattern="^\S.*?\S$"><br><br>
                    </div>
                </div>
                <div class="form-group">
                    <label for="articleText" class="col-sm-2 control-label">Article Text: </label>
                    <div class="col-sm-10">
                        <textarea name="articleText" class="form-control" rows="15" cols="50" placeholder="Article Text" id="articleText" required></textarea><br><br>
                    </div>
                </div>
                <input type="hidden" name="page" value="addArticle"/>
                <input type="submit" class="btn btn-default pull-right" value="Submit New Article">
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>
