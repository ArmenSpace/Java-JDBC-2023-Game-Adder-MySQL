import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
	public static Connection connect() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbplayers", "root", "admin");
		}
		catch (SQLException e) {
			System.out.println("Not Connected to DB " + e.getMessage());
		}
		return connection;
	}
}
