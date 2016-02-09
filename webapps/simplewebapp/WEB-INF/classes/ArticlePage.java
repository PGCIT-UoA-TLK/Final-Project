import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplewebapp.Article;
import simplewebapp.ArticleDAO;
import simplewebapp.Comment;
import simplewebapp.CommentDAO;
import simplewebapp.User;
import simplewebapp.UserDAO;

import java.io.IOException;
import java.util.List;

public class ArticlePage extends Page {
    protected static void article(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Getting the instance of the ArticleDAO
        ArticleDAO articleDAO = ArticleDAO.getInstance();

        //Parsing the parameter to an int
        int id;
        try {
            id = Integer.parseInt(request.getParameter("article"));
        } catch (Exception ignored) {
            response.sendRedirect("/simplewebapp/");
            return;
        }

        // Getting all the articles as a list
        Article article = articleDAO.getByArticleID(id);

        request.setAttribute("article", article);

        if (request.getParameter("delete") != null) {
            Comment c = CommentDAO.getInstance().getCommentByArticleIDAndCommentID(article.getArticleId(), Integer.parseInt(request.getParameter("commentID")));
            CommentDAO.getInstance().deleteComment(c);
            response.sendRedirect("/simplewebapp/?page=article&article=" + article.getArticleId());
            return;
        }

        List<Comment> comments = CommentDAO.getInstance().getCommentsByArticleID(article.getArticleId());
        List<User> users = UserDAO.getInstance().getAll();

        request.setAttribute("comments", comments);
        request.setAttribute("users", users);

        User user = (User) request.getAttribute("user");
        if (user != null && request.getParameter("commentBox") != null) {
            String newComment = request.getParameter("commentBox");
            CommentDAO.getInstance().addNewComment(newComment, article.getArticleId(), user.getUserId());
            response.sendRedirect("/simplewebapp/?page=article&article=" + article.getArticleId());
            return;
        }

        navigate("/WEB-INF/article.jsp", request, response);
    }

    protected static void addArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getAttribute("user");
        if (request.getParameter("articleTitle") != null) {
            String newTitle = request.getParameter("articleTitle");
            String articleText = request.getParameter("articleText");

            ArticleDAO.getInstance().addNewArticle(user.getUserId(), newTitle, articleText);

            response.sendRedirect("/simplewebapp/");
            return;
        }

        navigate("/WEB-INF/addArticle.jsp", request, response);
    }

    protected static void editArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int articleID = Integer.parseInt(request.getParameter("articleID"));

        Article article = ArticleDAO.getInstance().getByArticleID(articleID);

        String newTitle = request.getParameter("articleTitle");
        String newBody = request.getParameter("articleBody");

        String articleTitle = (newTitle != null ? newTitle : article.getTitle());
        String articleBody = (newBody != null ? newBody : article.getBody());

        if (!article.getTitle().equals(articleTitle) || !article.getBody().equals(articleBody)) {
            article.setTitle(newTitle);
            article.setBody(newBody);

            ArticleDAO.getInstance().updateArticle(article);

            response.sendRedirect("/simplewebapp/?page=article&article=" + article.getArticleId());
            return;
        }

        if (request.getParameter("delete") != null && !request.getParameter("delete").equals("")) {
            ArticleDAO.getInstance().deleteArticle(article);
            response.sendRedirect("/simplewebapp/");
            return;
        }

        request.setAttribute("article", article);

        navigate("/WEB-INF/editArticle.jsp", request, response);
    }

    public static void editComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int articleID = Integer.parseInt(request.getParameter("article"));
        int commentID = Integer.parseInt(request.getParameter("commentID"));

        Comment comment = CommentDAO.getInstance().getCommentByArticleIDAndCommentID(articleID, commentID);

        if (request.getParameter("edited") != null && request.getParameter("delete") == null) {
            comment.setBody(request.getParameter("commentBox"));
            CommentDAO.getInstance().updateComment(comment);
            response.sendRedirect("/simplewebapp/?page=article&article=" + articleID);
            return;
        } else if (request.getParameter("delete") != null) {
            CommentDAO.getInstance().deleteComment(comment);
            response.sendRedirect("/simplewebapp/?page=article&article=" + articleID);
            return;
        }

        request.setAttribute("comment", comment);

        navigate("/WEB-INF/editComment.jsp", request, response);
    }

    /*
    private static void addComment(String newComment, int articleID, int userID) {
        CommentDAO.getInstance().addNewComment(newComment, articleID, userID);
    }

    private static Comment getComment(int articleID, int commentID) {
        return CommentDAO.getInstance().getCommentByArticleIDAndCommentID(articleID, commentID);
    }

    private static void deleteComment(Comment comment) {
        CommentDAO.getInstance().deleteComment(comment);
    }

    private static void editComment(Comment comment){
        CommentDAO.getInstance().updateComment(comment);
    }
    //*/
}
