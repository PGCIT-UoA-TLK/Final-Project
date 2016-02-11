<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<html>
<head>
    <title>Simple Blog</title>
    <%@include file="include/specialIncludeFiles.jsp" %>
</head>
<style>
    body.cover-page {
        background: url("/images/cover-background.jpg") no-repeat center center fixed;
        -webkit-background-size: cover;
        -moz-background-size: cover;
        -o-background-size: cover;
        background-size: cover;
    }
</style>
<body class="cover-page">
<div class="site-wrapper">
    <div class="site-wrapper-inner">
        <div class="cover-container">
            <div class="inner cover">
                <h1 class="cover-heading">Welcome!</h1>
                <p class="lead">Welcome to our Blogging System.</p>
                <p class="lead">Although you will see it populated with filler text, it is what could fill that space that matters.</p>
                <p class="lead">We will now walk you through each of the reusable, reliable and secure features of our system.</p>
                <p class="lead">
                    <a href="#" class="btn btn-lg btn-default">Learn more</a>
                </p>
            </div>
        </div>
    </div>
</div>
</body>
</html>