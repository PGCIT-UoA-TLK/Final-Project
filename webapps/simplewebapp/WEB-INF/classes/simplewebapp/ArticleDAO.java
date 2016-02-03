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

    // TODO: VERY BADLY THOUGHT OUT METHOD NEEDS REWRITE
    private List<Article> doQuery(String query) {
        // Creating a list to store the results in
        List<Article> l = new ArrayList<>();
        try {
            ResultSet rs = databaseDAO.doQuery(query);
            while (rs.next()) {
                // Converting the results into a Article object
                int id = rs.getInt("article_id");
                String title = rs.getString("title");
                String body = rs.getString("body");
                // Adding the post object to the list
                l.add(new Article(id, title, body));
            }
        } catch (SQLException e) {
            System.err.println(String.valueOf(e));
        }
        // Return the list
        return l;
    }

    public List<Article> getAll() {
        // Creating the query
        String query = "SELECT * FROM article";

        // Execute the query and return the result
        return doQuery(query);
    }

    public Article getByArticleID(int articleID) {
        String query = "SELECT * FROM article WHERE article_id = " + articleID;
        List<Article> l = doQuery(query);

        //Getting the first item from the returned list
        Article a = null;
        if (!l.isEmpty() && l.size() > 0) {
            a = l.get(0);
        }
        return a;
    }

    public boolean addNewArticle(String newTitle, String newText) {
        // KL - Crating the add new article query and calling the updateQuery method
        String query = "INSERT INTO article (title, body) VALUES (?,?)";
        try {
            databaseDAO.runParametisedQuery(query, newTitle, newText);
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
            List<Article> results = doQuery("SELECT * FROM article WHERE article_id = " + article.getID());
            if (results.size() <= 0) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean addNewComment(String newComment, int articleID) {
        String query = "INSERT INTO comments (article_id, body) VALUES (?,?)";
        try {
            databaseDAO.runParametisedQuery(query, articleID, newComment);
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
                String commentBody = rs.getString("body");

                Comment c = new Comment(cID, aID, commentBody);
                System.out.println(c.getBody());
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
                String commentBody = rs.getString("body");

                c = new Comment(cID, aID, commentBody);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    public void updateComment(Comment comment) {
        String query = "UPDATE comments SET body = ? WHERE article_id = ? AND comment_id = ?";
        try {
            databaseDAO.runParametisedQuery(query, comment.getBody(), comment.getArticle_id(), comment.getComment_id());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public boolean deleteComment(Comment comment) {
        String query = "DELETE FROM comments WHERE comment_id = ?";
        try {
            databaseDAO.runParametisedQuery(query, comment.getComment_id());
            List<Article> results = doQuery("SELECT * FROM comments WHERE comment_id = " + comment.getComment_id());
            if (results.size() <= 0) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
