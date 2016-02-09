import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplewebapp.Article;
import simplewebapp.ArticleDAO;

import java.io.IOException;
import java.util.List;

public class Page {
    protected static void displayHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Getting the instance of the ArticleDAO
        ArticleDAO articleDAO = ArticleDAO.getInstance();

        // Getting all the articles as a list
        List<Article> listOfArticles = articleDAO.getAll();

        request.setAttribute("articles", listOfArticles);

        navigate("/WEB-INF/home.jsp", request, response);
    }

    protected static void navigate(String address, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }
}
