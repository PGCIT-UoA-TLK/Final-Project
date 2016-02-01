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
      List<Article> articles = (List<Article>) request.getAttribute("Articles");

      for (Article a : articles) {
      String articleTitle = a.getTitle();
      int articleID = a.getID();
      String linkURI = String.format("?article=%d",articleID);
      %>
      <section class ="article">
        <p>
          <a href="<%= linkURI%>"><%= articleTitle %></a>
        </p>
      </section>
      <%
      }
      %>
    </section>
  </body>
</html>
