package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionFactory {
	//Load JDBC driver
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	//Database data
	private static final String URL = "jdbc:mysql://localhost:3306/activitymanagement";
	private static final String USER = "root";
	private static final String PASS = "familiabrek";

	//method to create the connection to the database
	public static Connection getConnection() {
		try {
			Class.forName(DRIVER);
			return DriverManager.getConnection(URL, USER, PASS);
		} catch (ClassNotFoundException | SQLException e) {
			throw new RuntimeException("Connection failed: ", e);
		}
	}

	//methods to close the database connection
	public static void closeConnection(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeConnection(Connection con, PreparedStatement stmt) {

		closeConnection(con);

		try {

			if (stmt != null) {
				stmt.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeConnection(Connection con, PreparedStatement stmt, ResultSet rs) {

		closeConnection(con, stmt);

		try {

			if (rs != null) {
				rs.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}


