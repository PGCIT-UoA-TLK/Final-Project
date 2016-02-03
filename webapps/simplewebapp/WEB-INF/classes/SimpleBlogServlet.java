import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SimpleBlogServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = (request.getParameter("page") != null ? request.getParameter("page") : "");
        switch (page) {
            // Article Pages
            case "article": ArticlePage.article(request, response); break;
            case "addArticle": ArticlePage.addArticle(request, response); break;
            case "editComment": ArticlePage.displayEditComment(request,response); break;
            case "editArticle": ArticlePage.editArticle(request, response); break;

            // User Pages
            case "loginUser": UserPage.loginUser(request, response);
            case "registerUser": UserPage.registerUser(request, response);

            default: Page.displayHome(request, response); break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}