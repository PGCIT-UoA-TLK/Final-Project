package simplewebapp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        List<Article> l = new ArrayList<Article>();
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
            System.err.println(e);
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

    public void addNewArticle(String titles, String newText) {
        // KL - Crating the add new article query and calling the updateQuery method
        String query = "INSERT INTO article (title, body) VALUES ('" + titles + "','" + newText + "')";
        databaseDAO.updateQuery(query);
    }

    public boolean updateArticle(Article article) {
        String query = "UPDATE article SET title = ? AND body = ? WHERE article_id = ?;";
        try {
            databaseDAO.runParametisedQuery(query, article.getTitle(), article.getBody(), article.getID());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteArticle(Article article) {
        String query = "DELETE FROM article WHERE article_id = ?;";
        try {
            databaseDAO.runParametisedQuery(query, article.getID());
            List<Article> results = doQuery("SELECT * FROM article WHERE article_id = " + article.getID());
            if (results.size() <= 0) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}