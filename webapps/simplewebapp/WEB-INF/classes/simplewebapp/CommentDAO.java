package simplewebapp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {

    private static CommentDAO instance;
    private static DatabaseDAO databaseDAO;

    private CommentDAO() {
        databaseDAO = DatabaseDAO.getInstance();
    }

    public static CommentDAO getInstance() {
        if (instance == null) {
            instance = new CommentDAO();
        }
        return instance;
    }

    public boolean addNewComment(String newComment, int articleID, int userID) {
        String query = "INSERT INTO comments (article_id, user_id, body) VALUES (?,?,?)";
        try {
            databaseDAO.runParametisedQuery(query, articleID, userID, newComment);
            return true;
        } catch (Exception e) {
            System.out.println("CommentDAO.addNewComment: " + e.getMessage());
        }

        return false;
    }

    public List<Comment> getCommentsByArticleID(int articleID) {
        String query = "SELECT * FROM comments WHERE active = true AND article_id = ?";
        ResultSet results = null;
        List<Comment> comments = new ArrayList<>();
        try {
            results = databaseDAO.getParametisedQuery(query, articleID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (results == null) {
            System.out.println("null");
            return comments;
        }
        try {
            while (results.next()) {
                int commentId = results.getInt("comment_id");
                int articleId = results.getInt("article_id");
                int userId = results.getInt("user_id");
                String commentBody = results.getString("body");

                Comment comment = new Comment(commentId, articleId, userId, commentBody);

                addCommentExtras(comment);

                comments.add(comment);
            }
        } catch (SQLException e) {
            System.out.println("CommentDAO.getCommentsByArticleID: " + e.getMessage());
        }
        return comments;
    }

    public Comment getComment(int commentID) {
        String query = "SELECT * FROM comments WHERE active = true AND comment_id = ?";
        Comment comment = null;
        ResultSet results = null;
        try {
            results = databaseDAO.getParametisedQuery(query, commentID);
        } catch (Exception e) {
            System.out.println("CommentDAO.getComment: " + e.getMessage());
        }

        if (results == null) {
            System.out.println("null");
            return null;
        }

        try {
            while (results.next()) {
                int commentId = results.getInt("comment_id");
                int articleId = results.getInt("article_id");
                int userId = results.getInt("user_id");
                String commentBody = results.getString("body");

                comment = new Comment(commentId, articleId, userId, commentBody);
                addCommentExtras(comment);
            }
        } catch (SQLException e) {
            System.out.println("CommentDAO.getComment: " + e.getMessage());
        }

        return comment;
    }

    public boolean updateComment(Comment comment) {
        String query = "UPDATE comments SET body = ? WHERE article_id = ? AND comment_id = ?";
        try {
            databaseDAO.runParametisedQuery(query, comment.getBody(), comment.getArticleId(), comment.getCommentId());
            return true;
        } catch (Exception e) {
            System.out.println("CommentDAO.updateComment: " + e.getMessage());
        }

        return false;
    }

    public boolean deleteComment(Comment comment) {
        String query = "UPDATE comments SET active = false WHERE comment_id = ?";
        try {
            databaseDAO.runParametisedQuery(query, comment.getCommentId());
            return true;
        } catch (Exception e) {
            System.out.println("CommentDAO.deleteComment: " + e.getMessage());
        }

        return false;
    }

    public static void addCommentExtras(Comment comment){
        comment.setUser(UserDAO.getInstance().getUser(comment.getUserId()));
    }
}
