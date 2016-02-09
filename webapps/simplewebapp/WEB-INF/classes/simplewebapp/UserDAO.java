package simplewebapp;

import java.sql.ResultSet;
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
            String query = "SELECT * FROM users WHERE user_id = ? AND active = true";
            ResultSet result = databaseDAO.getParametisedQuery(query, id);
            result.next();

            return new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5),result.getInt(6));
        } catch (Exception e) {
            System.out.println("UserDAO.getUser: " + e.getMessage());
        }

        return null;
    }

    public List<User> getAll() {
        // Creating the query
        String query = "SELECT * FROM users WHERE active = true";

        List<User> users = new ArrayList<>();

        try {
            ResultSet result = databaseDAO.getParametisedQuery(query);

            while (result.next()) {
                // Converting the results into a Article object
                int id = result.getInt("user_id");
                String username = result.getString("username");
                String firstname = result.getString("firstname");
                String lastname = result.getString("lastname");
                int icon = result.getInt("icon_name");

                // Adding the post object to the list
                users.add(new User(id, username, firstname, lastname, icon));
            }
        } catch (Exception e) {
            System.out.println("UserDAO.getAll: " + e.getMessage());
        }

        // Execute the query and return the result
        return users;
    }

    public User loginUser(String username, String password) {
        try {
            String query = "SELECT * FROM users WHERE username = ? AND password = ? AND active = true";
            ResultSet result = databaseDAO.getParametisedQuery(query, username, password);
            result.next();

            return new User(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5),result.getInt(6));
        } catch (Exception e) {
            System.out.println("UserDAO.loginUser: " + e.getMessage());
        }

        return null;
    }

    public User addUser(String username, String password, String firstname, String lastname, int icon) {
        try {
            String query = "INSERT INTO users (USERNAME, PASSWORD, FIRSTNAME, LASTNAME, ICON_NAME) VALUES (?, ?, ?, ?,?)";
            ResultSet result = databaseDAO.runParametisedQuery(query, username, password, firstname, lastname, icon);

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
            System.out.println("UserDAO.addUser: " + e.getMessage());
        }

        return null;
    }

    public boolean updateUser(User user) {
        try {
            String query = "UPDATE users SET %s = ? WHERE user_id = ?";
            databaseDAO.runParametisedQuery(String.format(query, "FIRSTNAME"), user.getFirstname());
            databaseDAO.runParametisedQuery(String.format(query, "LASTNAME"), user.getLastname());

            return true;
        } catch (Exception e) {
            System.out.println("UserDAO.updateUser: " + e.getMessage());
        }

        return false;
    }

    public boolean deleteUser(User user) {
        String query = "UPDATE users SET active = true WHERE user_id = ?";
        System.err.println("Deleting User: " + user.getId());
        try {
            databaseDAO.runParametisedQuery(query, user.getId());

            return true;
        } catch (Exception e) {
            System.out.println("UserDAO.deleteUser: " + e.getMessage());
        }

        return false;
    }
}
