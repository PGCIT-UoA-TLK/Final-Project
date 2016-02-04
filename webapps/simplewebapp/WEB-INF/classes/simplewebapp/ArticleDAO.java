package simplewebapp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ArticleDAO {

    private static ArticleDAO instance;
    private static DatabaseDAO databaseDAO;

    private ArticleDAO() {
        databaseDAO = DatabaseDAO.getInstance();
    }

    public static ArticleDAO getInstance() {
        if (instance == null) {
            instance = new ArticleDAO();
        }
        return instance;
    }

    public List<Article> getAll() {
        // Creating the query
        String query = "SELECT * FROM article";

        // Creating a list to store the results in
        List<Article> articleList = new ArrayList<>();

        try {
            ResultSet result = databaseDAO.getParametisedQuery(query);

            while (result.next()) {
                // Converting the results into a Article object
                int id = result.getInt("article_id");
                int userID = result.getInt("user_id");
                String title = result.getString("title");
                String body = result.getString("body");

                // Adding the post object to the list
                articleList.add(new Article(id, userID, title, body));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Execute the query and return the result
        return articleList;
    }

    public Article getByArticleID(int articleID) {
        String query = "SELECT * FROM article WHERE article_id = ?";
        try {
            ResultSet result = databaseDAO.getParametisedQuery(query, articleID);
            result.next();

            int id = result.getInt("article_id");
            int userID = result.getInt("user_id");
            String title = result.getString("title");
            String body = result.getString("body");

            // Adding the post object to the list
            return new Article(id, userID, title, body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean addNewArticle(int userId, String newTitle, String newText) {
        // KL - Crating the add new article query and calling the updateQuery method
        String query = "INSERT INTO article (user_id, title, body) VALUES (?,?,?)";
        try {
            databaseDAO.runParametisedQuery(query, userId, newTitle, newText);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateArticle(Article article) {
        String query = "UPDATE ARTICLE SET %s = ? WHERE ARTICLE_ID = ?";
        try {
            databaseDAO.runParametisedQuery(String.format(query, "TITLE"), article.getTitle(), article.getID());
            databaseDAO.runParametisedQuery(String.format(query, "BODY"), article.getBody(), article.getID());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteArticle(Article article) {
        String query = "DELETE FROM article WHERE article_id = ?";
        try {
            databaseDAO.runParametisedQuery(query, article.getID());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean addNewComment(String newComment, int articleID, int userID) {
        String query = "INSERT INTO comments (article_id, user_id, body) VALUES (?,?, ?)";
        try {
            databaseDAO.runParametisedQuery(query, articleID, userID, newComment);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Comment> getCommentsByArticleID(int articleID) {
        String query = "SELECT * FROM comments WHERE article_id = " + articleID;
        ResultSet rs = null;
        List<Comment> comments = new ArrayList<>();
        try {
            rs = databaseDAO.doQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(rs == null){
            System.out.println("null");
            return comments;
        }
        try {
            while(rs.next()){
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
        String query = "SELECT * FROM comments WHERE article_id = " + articleID + "AND comment_id=" + commentID;
        Comment c = null;
        ResultSet rs = null;
        try {
            rs = databaseDAO.doQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(rs == null){
            System.out.println("null");
            return c;
        }
        try {
            while(rs.next()){
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
        String query = "DELETE FROM comments WHERE comment_id = ?";
        try {
            databaseDAO.runParametisedQuery(query, comment.getComment_id());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
