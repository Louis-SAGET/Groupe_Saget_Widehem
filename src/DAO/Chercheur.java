package DAO;

import java.sql.*;

import Database.ConnectionSingleton;

public class Chercheur {

	private String email;

	private String nomChercheur;

	private String prenomChercheur;

	private String urlChercheur;

	public Chercheur(String mail) {
		ConnectionSingleton cs = ConnectionSingleton.getInstance();
		Connection c = cs.getConnection();

		try {
			PreparedStatement ps = c.prepareStatement("select * from chercheur " + "where email = ?");

			ps.setString(1, mail);

			ResultSet res = ps.executeQuery();

			res.next();

			this.email = res.getString("email");
			this.nomChercheur = res.getString("nomchercheur");
			this.prenomChercheur = res.getString("prenomchercheur");
			this.urlChercheur = res.getString("urlchercheur");

			res.close();
			ps.close();
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
	}

	public String toString() {
		return this.email + " " + this.nomChercheur + " " + this.prenomChercheur + " " + this.urlChercheur;
	}
}
