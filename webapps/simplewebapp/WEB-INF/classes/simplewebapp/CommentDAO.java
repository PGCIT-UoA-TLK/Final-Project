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
            e.printStackTrace();
        }

        return false;
    }

    public List<Comment> getCommentsByArticleID(int articleID) {
        String query = "SELECT * FROM comments WHERE active = true AND article_id = " + articleID;
        ResultSet rs = null;
        List<Comment> comments = new ArrayList<>();
        try {
            rs = databaseDAO.doQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rs == null) {
            System.out.println("null");
            return comments;
        }
        try {
            while (rs.next()) {
                int cID = rs.getInt("comment_id");
                int aID = rs.getInt("article_id");
                int uID = rs.getInt("user_id");
                String commentBody = rs.getString("body");

                Comment c = new Comment(cID, aID, uID, commentBody);
                comments.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    public Comment getCommentByArticleIDAndCommentID(int articleID, int commentID) {
        String query = "SELECT * FROM comments WHERE active = true AND article_id = " + articleID + "AND comment_id=" + commentID;
        Comment c = null;
        ResultSet rs = null;
        try {
            rs = databaseDAO.doQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rs == null) {
            System.out.println("null");
            return c;
        }
        try {
            while (rs.next()) {
                int cID = rs.getInt("comment_id");
                int aID = rs.getInt("article_id");
                int uID = rs.getInt("user_id");
                String commentBody = rs.getString("body");

                c = new Comment(cID, aID, uID, commentBody);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public boolean updateComment(Comment comment) {
        String query = "UPDATE comments SET body = ? WHERE article_id = ? AND comment_id = ?";
        try {
            databaseDAO.runParametisedQuery(query, comment.getBody(), comment.getArticle_id(), comment.getComment_id());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteComment(Comment comment) {
        String query = "UPDATE comments SET active = false WHERE comment_id = ?";
        try {
            databaseDAO.runParametisedQuery(query, comment.getComment_id());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
