package simplewebapp;

import java.sql.ResultSet;

public class UserDAO {
    private static UserDAO instance;
    private static DatabaseDAO databaseDAO;

    private UserDAO() {
        databaseDAO = DatabaseDAO.getInstance();
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public User getUser(int id) {
        try {
            String query = "SELECT * FROM USERS WHERE USER_ID = ?;";
            ResultSet result = databaseDAO.getParametisedQuery(query, id);

            return new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean addUser(String username, String password, String firstname, String lastname) {
        try {
            String query = "INSERT INTO USERS (USERNAME, PASSWORD, FIRSTNAME, LASTNAME) VALUES (?, ?, ?, ?)";
            ResultSet result = databaseDAO.runParametisedQuery(query, username, password, firstname, lastname);

            System.err.printf("%s %s %s %s %s", result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5));

            return true;
//            return new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
