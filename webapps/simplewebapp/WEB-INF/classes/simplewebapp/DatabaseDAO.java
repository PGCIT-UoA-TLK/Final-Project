package simplewebapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseDAO {
    private static Connection connection;

    private static DatabaseDAO instance = null;

    protected ResultSet doQuery(String query) {
        try {
            Statement sqlStatement = connection.createStatement();
            return sqlStatement.executeQuery(query);
        } catch (SQLException e) {
            System.err.println(String.valueOf(e));
        }
        return null;
    }

    protected ResultSet runParametisedQuery (String query, Object... arguments) throws Exception {
        try {
            PreparedStatement sqlStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            addParameters(sqlStatement, arguments);

            sqlStatement.executeUpdate();
            return sqlStatement.getGeneratedKeys();
        } catch (SQLException e) {
            System.err.println("runParametisedQuery");
            System.err.println(String.valueOf(e));
        }

        return null;
    }

    public ResultSet getParametisedQuery(String query, Object... arguments) throws Exception {
        try {
            PreparedStatement sqlStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            addParameters(sqlStatement, arguments);

            return sqlStatement.executeQuery();
        } catch (SQLException e) {
            System.err.println(String.valueOf(e));
        }

        return null;
    }

    private void addParameters(PreparedStatement sqlStatement, Object[] arguments) throws Exception {
        int count = 0;
        for (Object argument : arguments) {
            count++;
            if (argument.getClass().equals(Integer.class)) {
                sqlStatement.setInt(count, (int) argument);
            } else if (argument.getClass().equals(String.class)) {
                sqlStatement.setString(count, (String) argument);
            } else {
                throw new Exception("Incorrect paramter type!");
            }
        }
    }

    //Empty private constructor to prevent other classes calling this
    private DatabaseDAO() {

    }

    private void init() {
        try {
            String dbMode = "derby";
            String dbClassName = "org.apache.derby.jdbc.ClientDriver";
            String host = "localhost:1527";
            String db = "blog";

            Class.forName(dbClassName);

            // Setting the derby connection URI
            String DB_CONNSTRING = "jdbc:" + dbMode + "://" + host + "/" + db;

            connection = DriverManager.getConnection(DB_CONNSTRING);

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e);
        }
    }

    public static DatabaseDAO getInstance() {
        if (instance == null) {
            instance = new DatabaseDAO();
            instance.init();
        }
        return instance;
    }
}