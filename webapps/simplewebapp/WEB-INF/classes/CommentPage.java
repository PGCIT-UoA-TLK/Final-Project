import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplewebapp.Comment;
import simplewebapp.CommentDAO;
import simplewebapp.User;

import java.io.IOException;

public class CommentPage extends Page {
    public static void editComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            printError(request, "You must be logged in to do this.");
            response.sendRedirect(request.getContextPath());
            return;
        }

        int articleID = Integer.parseInt(request.getParameter("article"));
        int commentID = Integer.parseInt(request.getParameter("commentID"));

        String body = request.getParameter("commentBox");

        Comment comment = CommentDAO.getInstance().getComment(commentID);

        boolean isDelete = (request.getParameter("delete") != null && !request.getParameter("delete").isEmpty());
        boolean isEdited = (request.getParameter("edited") != null && !request.getParameter("edited").isEmpty());

        if (isEdited && body.length() > 200) {
            printError(request, "Comment may not be longer than 200 characters.");
        }

        if (isEdited && !isDelete && !body.isEmpty()) {
            comment.setBody(body);
            CommentDAO.getInstance().updateComment(comment);
            response.sendRedirect(request.getContextPath() + "?page=article&article=" + articleID);
            return;
        } else if (isDelete) {
            CommentDAO.getInstance().deleteComment(comment);
            response.sendRedirect(request.getContextPath() + "?page=article&article=" + articleID);
            return;
        }

        request.setAttribute("comment", comment);

        navigate("/WEB-INF/editComment.jsp", request, response);
    }
}
