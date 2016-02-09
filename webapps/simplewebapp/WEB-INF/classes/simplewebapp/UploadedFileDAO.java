package simplewebapp;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UploadedFileDAO {

    private static UploadedFileDAO instance;
    private static DatabaseDAO databaseDAO;

    private UploadedFileDAO() {
        databaseDAO = DatabaseDAO.getInstance();
    }

    public static UploadedFileDAO getInstance() {
        if (instance == null) {
            instance = new UploadedFileDAO();
        }
        return instance;
    }

    public List<File> getAll() {
        // Creating the query
        String query = "SELECT * FROM uploadedFiles";

        // Creating a list to store the results in
        List<File> fileList = new ArrayList<>();

        try {
            ResultSet result = databaseDAO.getParametisedQuery(query);

            while (result.next()) {
                // Converting the results into a Article object
                int file_id = result.getInt("file_id");
                int article_id = result.getInt("article_id");
                String image = result.getString("image");
                String audio = result.getString("audio");

                // Adding the post object to the list
                fileList.add(new File(file_id, article_id, image, audio));
            }
        } catch (Exception e) {
            System.out.println("UploadedFileDAO.getAll: " + e.getMessage());
        }

        // Execute the query and return the result
        return fileList;
    }

    public File getByArticleID(int articleID) {
        String query = "SELECT * FROM uploadedFiles WHERE article_id = ?";
        try {
            ResultSet result = databaseDAO.getParametisedQuery(query, articleID);
            result.next();

            int file_id = result.getInt("file_id");
            int article_id = result.getInt("article_id");
            String image = result.getString("image");
            String audio = result.getString("audio");

            // Adding the post object to the list
            return new File(file_id, article_id, image, audio);
        } catch (Exception e) {
            System.out.println("uploadedFileDAO.getByArticleID: " + e.getMessage());
        }

        return null;
    }

    public boolean addNewFile(int article_id, String newImage, String newAudio) {
        // KL - Crating the add new article query and calling the updateQuery method
        String query = "INSERT INTO uploadedFiles (article_id, image, audio) VALUES (?,?,?)";
        try {
            databaseDAO.runParametisedQuery(query, article_id, newImage, newAudio);
            return true;
        } catch (Exception e) {
            System.out.println("ArticleDAO.addNewArticle: " + e.getMessage());
        }

        return false;
    }

      public boolean deleteFile(File file) {
        String query = "DROP INDEX FROM uploadedFiles WHERE article_id = ? AND file_id = ?";
        try {
            databaseDAO.runParametisedQuery(query, file.getArticle_id(), file.getFile_id());
            return true;
        } catch (Exception e) {
            System.out.println("ArticleDAO.deleteArticle: " + e.getMessage());
        }

        return false;
    }
}