package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {
	private Connection c;
	private static ConnectionSingleton s;

	private ConnectionSingleton() {
		try {
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			c = DriverManager.getConnection(url, "...", "...");
		} catch (SQLException e) {
			System.out.println(e);
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
				e.printStackTrace();
			}
		}
		return s;
	}

	public Connection getConnection() {
		return c;
	}
}
