package inputFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
	public static Connection connect;
	public static Statement statement;
	public static ResultSet result;
	
	public static void setDBConnection(String DBConnectionURL, String DBUserName, String DBPassword) throws Exception {
		try {
			String DBClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			Class.forName(DBClass).newInstance();
			connect = DriverManager.getConnection(DBConnectionURL, DBUserName, DBPassword);
			if(connect != null) {
				System.out.println("Database connection is established");
			}
			else {
				System.out.println("Database connection is not established");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<String> getSQLQuery(String query) throws Exception {		
		statement = connect.createStatement();
		result = statement.executeQuery(query);
		List<String> values = new ArrayList<String>();
		while(result.next()) {
			values.add(result.getString(1).toString().trim());
			System.out.println(result.getString(1));
		}
		return values;
	}
}
