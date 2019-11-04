package ds.program.fvhr.vinh.dao_support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	public static Connection getConnection() throws SQLException {
		Connection con = null;

		try {
			Class.forName("oracle.jdbc.OracleDriver");
			con = DriverManager
					.getConnection("jdbc:oracle:thin:@5.1.1.14:1521:fvordb",
							"test", "test123");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

}
