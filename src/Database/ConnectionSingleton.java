package Database;

import java.sql.*;

public class ConnectionSingleton {
	private Connection c;
	private static ConnectionSingleton s;
	
	private StringBuffer login;
	private StringBuffer password;

	private ConnectionSingleton() {
		try {
			//String url = "jdbc:oracle:thin:@localhost:1521:XE";
			String url = "jdbc:oracle:thin:@charlemagne:1521:infodb";
			c = DriverManager.getConnection(url, new String(this.login), new String(this.password));
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
	
	public void open(String user, String mdp) {
		try {
			if (s.c.isClosed()) {
				s.login = new StringBuffer(user);
				s.login = new StringBuffer(mdp);
				ConnectionSingleton.getInstance();
			} else {
				s.close();
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
	}

	public Connection getConnection() {
		return c;
	}
}
