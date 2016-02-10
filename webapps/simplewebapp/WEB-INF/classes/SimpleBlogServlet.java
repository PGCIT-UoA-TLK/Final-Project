import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import simplewebapp.User;

import java.io.IOException;

public class SimpleBlogServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        setUser(request);

        getMessage(request);

        String page = (request.getParameter("page") != null ? request.getParameter("page") : "");
        switch (page) {
            // Article Pages
            case "article": ArticlePage.article(request, response); break;
            case "addArticle": ArticlePage.addArticle(request, response); break;
            case "editArticle": ArticlePage.editArticle(request, response); break;

            // Comment Page
            case "editComment": CommentPage.editComment(request, response); break;

            // User Pages
            case "addUser": UserPage.addUser(request, response); break;
            case "loginUser": UserPage.loginUser(request, response); break;
            case "editUser": UserPage.editUser(request, response); break;

            case "": Page.displayHome(request, response); break;
        }
    }

    private void getMessage(HttpServletRequest request) {
        if (request.getParameter("registerSuccess") != null) {
            request.setAttribute("successMessage", "Thank you for signing up!");
        } else if (request.getParameter("loginSuccess") != null) {
            request.setAttribute("successMessage", "Welcome back!");
        } else if (request.getParameter("logoutSuccess") != null) {
            request.setAttribute("successMessage", "You have been logged out, come back later!");
        } else if (request.getParameter("deleteSuccess") != null) {
            request.setAttribute("successMessage", "Your account has been deleted. Thank you for visiting our site!");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void setUser(HttpServletRequest request) {
        User user = null;
        HttpSession session = request.getSession();
        if (request.getParameter("logout") != null) {
            session.setAttribute("user", null);
        } else if (session.getAttribute("user") != null) {
            user = (User) session.getAttribute("user");
        }
        request.setAttribute("user", user);
    }
}