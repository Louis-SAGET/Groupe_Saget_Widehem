package Database;

import java.sql.*;

public class ConnectionSingleton {
	private Connection c;
	private static ConnectionSingleton s;

	private ConnectionSingleton() {
		try {
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			c = DriverManager.getConnection(url, "...", "...");
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
	}

	public static ConnectionSingleton getInstance() {
		if (s == null) {
			s = new ConnectionSingleton();
		} else {
			try {
				if (s.c != null && s.c.isClosed()) {
					s = new ConnectionSingleton();
				}
			} catch (SQLException e) {
				System.err.println("SQLException: " + e.getMessage());
			}
		}
		return s;
	}

	public void close() {
		try {
			this.c.close();
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		}
	}

	public Connection getConnection() {
		return c;
	}
}
