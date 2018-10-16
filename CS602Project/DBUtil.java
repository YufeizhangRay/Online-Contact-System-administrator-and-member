package Yufei;
import java.sql.*;

public class DBUtil {
	private static final String URL = "";
	private static final String USER = "";
	private static final String PASSWORD = "";
	private static Connection con = null;
	static {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con =  DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

	}
	public static Connection getConnection() {
		return con;
	}
}
