

package DataBase_Connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
	
	public static Connection dbConnection = null;
	
	public static Connection getDbConnection() {
		return dbConnection;
	}

	public static void setDbConnection(Connection dbConnection) {
		DbConnection.dbConnection = dbConnection;
	}

	public static void getDBConnection() 
	{
		if(dbConnection == null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library_Management_System","sriram","sriram"); 
			}
			catch(Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
}
