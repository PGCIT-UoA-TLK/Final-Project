import simplewebapp.Article;
import simplewebapp.ArticleDAO;

import java.io.*;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

public class SimpleBlogServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = (request.getParameter("page") != null ? request.getParameter("page") : "");
        switch (page) {
            case "article": displayArticle(request, response); break;
            case "addArticle": displayAddArticle(request, response); break;

            default: displayHome(request, response); break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void displayHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Getting the instance of the ArticleDAO
        ArticleDAO articleDAO = ArticleDAO.getInstance();

        // Getting all the articles as a list
        List<Article> listOfArticles = articleDAO.getAll();

        request.setAttribute("Articles", listOfArticles);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/home.jsp");
        dispatcher.forward(request, response);
    }

    protected void displayArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Getting the instance of the ArticleDAO
        ArticleDAO articleDAO = ArticleDAO.getInstance();

        //Parsing the parameter to an int
        int id = Integer.parseInt(request.getParameter("article"));

        // Getting all the articles as a list
        Article a = articleDAO.getByArticleID(id);

        request.setAttribute("Article", a);

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/article.jsp");
        dispatcher.forward(request, response);
    }

    protected void displayAddArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/addArticle.jsp");
        dispatcher.forward(request, response);
    }
}