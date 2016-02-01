package simplewebapp;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class ArticleDAO {

	private static ArticleDAO instance;
	private static DatabaseDAO databaseDAO;

	private ArticleDAO() {
		databaseDAO = DatabaseDAO.getInstance();
	}

	public static ArticleDAO getInstance() {
		if (instance == null) {
			instance = new ArticleDAO();
		}
		return instance;
	}

	private List<Article> doQuery(String query) {
		// Creating a list to store the results in
		List<Article> l = new ArrayList<Article>();
		try {
			ResultSet rs = databaseDAO.doQuery(query);
			while(rs.next()) {
				// Converting the results into a Article object
				int id = rs.getInt("article_id");
				String title = rs.getString("title");
				String body = rs.getString("body");
				// Adding the post object to the list
				l.add(new Article(id,title,body));
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		// Return the list
		return l;
	}

	public List<Article> getAll() {
		// Creating the query
		String query = "SELECT * FROM article";
		// Execute the query and return the result
		List<Article> l = doQuery(query);
		return l;
	}

	public Article getByArticleID(int articleID) {
		String query = "SELECT * FROM article WHERE article_id = " + articleID;
		List<Article> l = doQuery(query);

		//Getting the first item from the returned list
		Article a = null;
		if(!l.isEmpty() && l.size() > 0) {
			a = l.get(0);
		}
		return a;
	}
}