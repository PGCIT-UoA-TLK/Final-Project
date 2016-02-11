package simplewebapp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileDAO {

    private static FileDAO instance;
    private static DatabaseDAO databaseDAO;

    private FileDAO() {
        databaseDAO = DatabaseDAO.getInstance();
    }

    public static FileDAO getInstance() {
        if (instance == null) {
            instance = new FileDAO();
        }
        return instance;
    }

    public List<File> getAll() {
        // Creating the query
        String query = "SELECT * FROM uploadedFiles";

        // Creating a list to store the results in
        List<File> files = new ArrayList<>();

        try {
            ResultSet result = databaseDAO.getParametisedQuery(query);

            files = processFilesFromResultSet(result);
        } catch (Exception e) {
            System.out.println("FileDAO.getAll: " + e.getMessage());
        }

        // Execute the query and return the result
        return files;
    }

    public List<File> getByArticleID(int articleID) {
        String query = "SELECT * FROM uploadedFiles WHERE article_id = ?";
        List<File> files = new ArrayList<>();
        try {
            ResultSet result = databaseDAO.getParametisedQuery(query, articleID);

            files = processFilesFromResultSet(result);
        } catch (Exception e) {
            System.out.println("FileDAO.getByArticleID: " + e.getMessage());
        }

        // Execute the query and return the result
        return files;
    }

    private List<File> processFilesFromResultSet(ResultSet result) throws SQLException {
        List<File> files = new ArrayList<>();
        while (result.next()) {
            int fileId = result.getInt("file_id");
            int articleId = result.getInt("article_id");
            String filepath = result.getString("filepath");

            // Adding the post object to the list
            files.add(new File(fileId, articleId, filepath));
        }
        return files;
    }

    public boolean addNewFile(int article_id, String filePath) {
        // KL - Crating the add new article query and calling the updateQuery method
        String query = "INSERT INTO uploadedFiles (article_id, filePath) VALUES (?,?)";
        try {
            databaseDAO.runParametisedQuery(query, article_id, filePath);
            return true;
        } catch (Exception e) {
            System.out.println("FileDAO.addNewArticle: " + e.getMessage());
        }

        return false;
    }
}