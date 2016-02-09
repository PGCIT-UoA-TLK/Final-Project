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
        String query = "SELECT * FROM article WHERE active = true";

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
        String query = "SELECT * FROM article WHERE article_id = ? AND active = true";
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
        String query = "UPDATE ARTICLE SET active = false WHERE article_id = ?";
        try {
            databaseDAO.runParametisedQuery(query, article.getID());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
