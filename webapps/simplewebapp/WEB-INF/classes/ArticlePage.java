import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplewebapp.Article;
import simplewebapp.ArticleDAO;

import java.io.IOException;

public class ArticlePage extends Page {
    protected static void article(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Getting the instance of the ArticleDAO
        ArticleDAO articleDAO = ArticleDAO.getInstance();

        //Parsing the parameter to an int
        int id = Integer.parseInt(request.getParameter("article"));

        // Getting all the articles as a list
        Article a = articleDAO.getByArticleID(id);

        request.setAttribute("Article", a);

        navigate("/WEB-INF/article.jsp", request, response);
    }

    protected static void addArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        navigate("/WEB-INF/addArticle.jsp", request, response);
    }

    protected static void editArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        navigate("/WEB-INF/editArticle.jsp", request, response);
    }

    public static void displayEditComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        navigate("/WEB-INF/editComment.jsp", request, response);
    }

}
