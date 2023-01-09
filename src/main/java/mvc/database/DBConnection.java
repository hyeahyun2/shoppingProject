package mvc.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection conn = null;
		
		String url = "jdbc:mariadb://localhost:3306/studyjdbc";
		String user = "root";
		String password = "6995";
		
		Class.forName("org.mariadb.jdbc.Driver");
		
		conn = DriverManager.getConnection(url, user, password);
		
		return conn;
	}
}
