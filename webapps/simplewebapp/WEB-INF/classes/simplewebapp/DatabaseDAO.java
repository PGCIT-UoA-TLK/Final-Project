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
            ResultSet sqlResultSet = sqlStatement.executeQuery(query);
            return sqlResultSet;
        } catch (SQLException e) {
            System.err.println(e);
        }
        return null;
    }

    protected boolean updateQuery(String query){
        try{
            Statement sqlStatement = connection.createStatement();
            sqlStatement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    protected boolean runParametisedQuery (String query, Object... arguments) throws Exception {
        try {
            PreparedStatement sqlStatement = connection.prepareStatement(query);

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

            sqlStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
        }

        return false;
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