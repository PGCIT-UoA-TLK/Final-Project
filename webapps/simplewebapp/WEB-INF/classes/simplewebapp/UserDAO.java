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
            String query = "SELECT * FROM USERS WHERE USER_ID = ?";
            ResultSet result = databaseDAO.getParametisedQuery(query, id);
            result.next();

            return new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public User loginUser(String username, String password) {
        try {
            String query = "SELECT * FROM USERS WHERE USERNAME = ? AND PASSWORD = ?";
            ResultSet result = databaseDAO.getParametisedQuery(query, username, password);
            result.next();

            return new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public User addUser(String username, String password, String firstname, String lastname) {
        try {
            String query = "INSERT INTO USERS (USERNAME, PASSWORD, FIRSTNAME, LASTNAME) VALUES (?, ?, ?, ?)";
            ResultSet result = databaseDAO.runParametisedQuery(query, username, password, firstname, lastname);

            User user;
            if (result == null) {
                return null;
            } else {
                result.next();
                int id = result.getInt(1);
                user = UserDAO.getInstance().getUser(id);
            }

            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
