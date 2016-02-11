import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplewebapp.Article;
import simplewebapp.ArticleDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Page {
    protected static void displayHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Getting the instance of the ArticleDAO
        ArticleDAO articleDAO = ArticleDAO.getInstance();

        // Getting all the articles as a list
        List<Article> articles = articleDAO.getAll();
        List<Article> modifiedArticles = new ArrayList<>();

        // Make the body 100 or so characters long. Technically we can set back to articles, but we aren't short on memory.
        for (Article article : articles) {
            String body = article.getBody();

            if (body.length() > 100) {
                body = body.substring(0, 100) + " ...";
            }

            article.setBody(body);
            modifiedArticles.add(article);
        }

        request.setAttribute("articles", modifiedArticles);

        navigate("/WEB-INF/home.jsp", request, response);
    }

    protected static void navigate(String address, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    protected static void printSuccess(HttpServletRequest request, String s) {
        request.setAttribute("successMessage", s);
    }

    protected static void printError(HttpServletRequest request, String s) {
        request.setAttribute("errorMessage", s);
    }

    public static void displayWelcome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        navigate("/WEB-INF/welcome.jsp", request, response);
    }
}
