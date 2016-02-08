<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<%@page contentType="text/html" %>
<%@ page import="simplewebapp.Article" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Simple Blog</title>

    <%@include file="include/specialIncludeFiles.jsp"%>
</head>

<%@ page language="java" %>

<body>

<%@include file="include/userBar.jsp"%>




    <div id="view" class="jumbotron">
        <div class="container">
            <%

                List<Article> articles = (List<Article>) request.getAttribute("Articles");

                for (Article a : articles) {
                    String articleTitle = a.getTitle();
                    int articleID = a.getID();
                    String linkURI = String.format("?page=article&article=%d", articleID);
            %>

            <div class="row">
                <div class="col-sm">

                        <a href="<%= linkURI%>">
                            <%= articleTitle %>
                        </a>

                </div>
            </div>


        <%
            }
        %>

        </div>
    </div>

</body>
</html>
