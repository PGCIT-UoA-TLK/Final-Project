package simplewebapp;

import java.sql.*;

public class DatabaseDAO {
	private static Connection connection;

	private static DatabaseDAO instance = null;

	protected static ResultSet doQuery(String query) {
		try {
			Statement sqlStatement = connection.createStatement();
			ResultSet sqlResultSet = sqlStatement.executeQuery(query);
			return sqlResultSet;
		} catch (SQLException e) {
			System.err.println(e);
		}
		return null;
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
			String DB_CONNSTRING = "jdbc:"+dbMode+"://" + host + "/" + db;

			connection = DriverManager.getConnection(DB_CONNSTRING);

		} catch(ClassNotFoundException e) {
			System.err.println(e);
		} catch(SQLException e) {
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