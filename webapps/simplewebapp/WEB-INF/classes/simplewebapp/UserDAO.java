package simplewebapp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnnecessaryLocalVariable")
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
            String query = "" +
                    "SELECT user_id, username, password, passwordSalt, firstname, lastname, age, gender, icon_name " +
                    "FROM users WHERE user_id = ? AND active = true";
            ResultSet result = databaseDAO.getParametisedQuery(query, id);
            result.next();


            // Converting the results into a Article object
            String username = result.getString("username");
            byte[] password = result.getBytes("password");
            String passwordSalt = result.getString("passwordSalt");
            String firstname = result.getString("firstname");
            String lastname = result.getString("lastname");
            String age = result.getString("age");
            String gender = result.getString("gender");
            int icon = result.getInt("icon_name");

            User user = new User(id, username, password, passwordSalt, firstname, lastname, gender, age, icon);
            return user;
        } catch (Exception ignored) {
        }

        return null;
    }

    public User getUserByUsername(String username) {
        try {
            String query = "" +
                    "SELECT * " +
                    "FROM users WHERE username = ? AND active = true";
            ResultSet result = databaseDAO.getParametisedQuery(query, username);
            result.next();

            // Converting the results into a Article object
            int id = result.getInt("user_id");
            byte[] password = result.getBytes("password");
            String passwordSalt = result.getString("passwordSalt");
            String firstname = result.getString("firstname");
            String lastname = result.getString("lastname");
            String age = result.getString("age");
            String gender = result.getString("gender");
            int icon = result.getInt("icon_name");

            User user = new User(id, username, password, passwordSalt, firstname, lastname, gender, age, icon);
            return user;
        } catch (Exception ignored) {
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
                byte[] password = result.getBytes("password");
                String passwordSalt = result.getString("passwordSalt");
                String firstname = result.getString("firstname");
                String lastname = result.getString("lastname");
                String age = result.getString("age");
                String gender = result.getString("gender");
                int icon = result.getInt("icon_name");

                User user = new User(id, username, password, passwordSalt, firstname, lastname, gender, age, icon);

                // Adding the post object to the list
                users.add(user);
            }
        } catch (Exception e) {
            System.out.println("UserDAO.getAll: " + e.getMessage());
        }

        // Execute the query and return the result
        return users;
    }

    public User addUser(String username, byte[] password, String passwordSalt, String firstname, String lastname, String gender, String age, int icon) {
        try {
            String query = "INSERT INTO users (USERNAME, PASSWORD, PASSWORDSALT, FIRSTNAME, LASTNAME, GENDER, AGE, ICON_NAME) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            ResultSet result = databaseDAO.runParametisedQuery(query, username, password, passwordSalt, firstname, lastname, gender, age, icon);

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
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateUser(User user) {
        try {
            String query = "UPDATE users SET %s = ? WHERE user_id = ?";
            databaseDAO.runParametisedQuery(String.format(query, "firstname"), user.getFirstname(), user.getUserId());
            databaseDAO.runParametisedQuery(String.format(query, "lastname"), user.getLastname(), user.getUserId());

            return true;
        } catch (Exception e) {
            System.out.println("UserDAO.updateUser: " + e.getMessage());
        }

        return false;
    }

    public boolean deleteUser(User user) {
        String query = "UPDATE users SET active = false WHERE user_id = ?";
        System.err.println("Deleting User: " + user.getUserId());
        try {
            databaseDAO.runParametisedQuery(query, user.getUserId());

            return true;
        } catch (Exception e) {
            System.out.println("UserDAO.deleteUser: " + e.getMessage());
        }

        return false;
    }
}
