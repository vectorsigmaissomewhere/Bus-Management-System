package ETransportationsystem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	
	private String user = "root";
	private String pass = "admin";
	private String url = "jdbc:mysql://localhost/railway_management_system";
	private Statement statement;
	
	public Database() throws SQLException {
		Connection connection = DriverManager.getConnection(url, user, pass);
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
												ResultSet.CONCUR_READ_ONLY);
	}
	
	public Statement getStatement() {
		return statement;
	}

}
