package Database;

import java.sql.*;

public class ConnectionSingleton {
	private Connection c;
	private static ConnectionSingleton s;

	private ConnectionSingleton(String url, String user, String password) {
		try {			
			c = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
	}

	public static ConnectionSingleton getInstance(String url, String user, String password) {
		if (s == null) {
			s = new ConnectionSingleton(url, user, password);
		} else {
			try {
				if (s.c != null && s.c.isClosed()) {
					s = new ConnectionSingleton(url, user, password);
				}
			} catch (SQLException e) {
				System.err.println("SQLException: " + e.getMessage());
			}
		}
		return s;
	}
	
	public static ConnectionSingleton getInstance() {
		return s;
	}
	/*
	public void open(String url, String user, String mdp) {
		try {
			if (s.c.isClosed()) {
				s.login = new StringBuffer(user);
				s.password = new StringBuffer(mdp);
				s.url = new StringBuffer(url);
				ConnectionSingleton.getInstance();
			} else {
				s.close();
				s.login = new StringBuffer(user);
				s.password = new StringBuffer(mdp);
				s.url = new StringBuffer(url);
				ConnectionSingleton.getInstance();
			}
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
		
	}

	public void close() {
		try {
			if (s.login.length() > 0 && s.password.length() > 0) {
				this.login.delete(0, this.login.length() - 1);
				this.password.delete(0, this.password.length() - 1);
			}			
			
			if (!s.c.isClosed()) {
				s.c.close();
			}			
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
	}*/

	public Connection getConnection() {
		return s.c;
	}
}
