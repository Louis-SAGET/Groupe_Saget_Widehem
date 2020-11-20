package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Database.ConnectionSingleton;

public class Article {

	private String titre;

	private String resume;

	private String typeArticle;

	public Article(String t) {
		ConnectionSingleton cs = ConnectionSingleton.getInstance();
		Connection c = cs.getConnection();

		try {
			PreparedStatement ps = c.prepareStatement("select * from article " + "where titre = ?");

			ps.setString(1, t);

			ResultSet res = ps.executeQuery();
			
			res.next();

			this.titre = res.getString("titre");
			this.resume = res.getString("resume");
			this.typeArticle = res.getString("typearticle");
			
			res.close();
			ps.close();
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
	}
	
	public String toString() {
		return this.titre + " " + this.typeArticle;
	}
}
