package simplewebapp;

import java.sql.ResultSet;
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
                String embeddedContent = result.getString("embeddedContent");

                Article article = new Article(id, userID, title, body, embeddedContent);
                addArticleExtras(article);

                // Adding the post object to the list
                articleList.add(article);
            }
        } catch (Exception e) {
            System.out.println("ArticleDAO.getAll: " + e.getMessage());
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
            String embeddedContent = result.getString("embeddedContent");

            Article article = new Article(id, userID, title, body, embeddedContent);
            addArticleExtras(article);

            // Adding the post object to the list
            return article;
        } catch (Exception e) {
            System.out.println("ArticleDAO.getByArticleID: " + e.getMessage());
        }

        return null;
    }


    public int addNewArticleWithId(int userId, String newTitle, String newText, String embeddedContent){
        // KL - Crating the add new article query and calling the updateQuery method
        String query = "INSERT INTO article (user_id, title, body, embeddedContent) VALUES (?,?,?,?)";
        try {
            ResultSet resultSet = databaseDAO.runParametisedQuery(query, userId, newTitle, newText, embeddedContent);

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

            return 0;
        } catch (Exception e) {
            System.out.println("ArticleDAO.addNewArticle: " + e.getMessage());
        }

        return 0;
    }

    public boolean updateArticle(Article article) {
        String query = "UPDATE ARTICLE SET %s = ? WHERE ARTICLE_ID = ?";
        try {
            databaseDAO.runParametisedQuery(String.format(query, "TITLE"), article.getTitle(), article.getArticleId());
            databaseDAO.runParametisedQuery(String.format(query, "BODY"), article.getBody(), article.getArticleId());
            return true;
        } catch (Exception e) {
            System.out.println("ArticleDAO.updateArticle: " + e.getMessage());
        }

        return false;
    }

    public boolean deleteArticle(Article article) {
        String query = "UPDATE ARTICLE SET active = false WHERE article_id = ?";
        try {
            databaseDAO.runParametisedQuery(query, article.getArticleId());
            return true;
        } catch (Exception e) {
            System.out.println("ArticleDAO.deleteArticle: " + e.getMessage());
        }

        return false;
    }

    private static void addArticleExtras(Article article) {
        article.setComments(CommentDAO.getInstance().getCommentsByArticleID(article.getArticleId()));
    }
}
