package simplewebapp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    // TODO: VERY BADLY THOUGHT OUT METHOD NEEDS REWRITE
    private List<User> doQuery(String query) {
        // Creating a list to store the results in
        List<User> users = new ArrayList<>();
        try {
            ResultSet rs = databaseDAO.doQuery(query);
            while (rs.next()) {
                // Converting the results into a Article object
                int id = rs.getInt("user_id");
                String username = rs.getString("username");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");

                // Adding the post object to the list
                users.add(new User(id, username, firstname, lastname));
            }
        } catch (SQLException e) {
            System.err.println(String.valueOf(e));
        }
        // Return the list
        return users;
    }

    public List<User> getAll() {
        // Creating the query
        String query = "SELECT * FROM USERS";

        // Execute the query and return the result
        return doQuery(query);
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

    public boolean updateUser(User user) {
        try {
            String query = "UPDATE USERS SET %s = ? WHERE USER_ID = ?";
            databaseDAO.runParametisedQuery(String.format(query, "FIRSTNAME"), user.getFirstname());
            databaseDAO.runParametisedQuery(String.format(query, "LASTNAME"), user.getLastname());

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteUser(User user) {
        String query = "DELETE FROM USERS WHERE USER_ID = ?";
        System.err.println("Deleting User: " + user.getId());
        try {
            databaseDAO.runParametisedQuery(query, user.getId());

            return true;
        } catch (Exception e) {
            System.err.println("deleteUser");
            e.printStackTrace();
        }

        return false;
    }
}
