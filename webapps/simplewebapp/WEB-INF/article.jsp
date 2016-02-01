<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<%@page contentType="text/html"%>
<%@ page language="java" import="java.sql.*,java.util.List,java.util.ArrayList" %>
<%@ page import="simplewebapp.Article" %>
<html>
  <head>
    <title>Simple Blog</title>
  </head>

<%@ page language="java"%>

  <body>

    <section id="view" class="container">
      <%
      Article a = (Article) request.getAttribute("Article");

      String articleTitle = a.getTitle();
      String articleBody = a.getBody();

      %>

      <section class ="article">
        <h1>
          <%= articleTitle %>
        </h1>  
        <p>
          <%= articleBody %>
        </p>
      </section>
    </section>
  </body>
</html>
