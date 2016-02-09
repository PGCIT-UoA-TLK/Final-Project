import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import simplewebapp.User;

import java.io.IOException;

public class SimpleBlogServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = null;
        HttpSession session = request.getSession();
        if (request.getParameter("logout") != null) {
            session.setAttribute("user", null);
        } else if (session.getAttribute("user") != null) {
            user = (User) session.getAttribute("user");
        }
        request.setAttribute("user", user);

        String page = (request.getParameter("page") != null ? request.getParameter("page") : "");
        switch (page) {
            // Article Pages
            case "article": ArticlePage.article(request, response); break;
            case "addArticle": ArticlePage.addArticle(request, response); break;
            case "editComment": ArticlePage.displayEditComment(request, response); break;
            case "editArticle": ArticlePage.editArticle(request, response); break;

            // User Pages
            case "addUser": UserPage.addUser(request, response); break;
            case "loginUser": UserPage.loginUser(request, response); break;
            case "editUser": UserPage.editUser(request, response); break;

            case "": Page.displayHome(request, response); break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}