import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import simplewebapp.Comment;
import simplewebapp.CommentDAO;

import java.io.IOException;

public class CommentPage extends Page {
    public static void editComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int articleID = Integer.parseInt(request.getParameter("article"));
        int commentID = Integer.parseInt(request.getParameter("commentID"));

        String body = request.getParameter("commentBox");

        Comment comment = CommentDAO.getInstance().getComment(commentID);

        if (body.length() > 200) {
            printError(request, "Comment may not be longer than 200 characters.");
        }

        if (!body.equals("") && request.getParameter("edited") != null && request.getParameter("delete") == null) {
            comment.setBody(body);
            CommentDAO.getInstance().updateComment(comment);
            response.sendRedirect(request.getContextPath() + "?page=article&article=" + articleID);
            return;
        } else if (request.getParameter("delete") != null && !request.getParameter("delete").equals("")) {
            CommentDAO.getInstance().deleteComment(comment);
            response.sendRedirect(request.getContextPath() + "?page=article&article=" + articleID);
            return;
        }

        request.setAttribute("comment", comment);

        navigate("/WEB-INF/editComment.jsp", request, response);
    }
}
