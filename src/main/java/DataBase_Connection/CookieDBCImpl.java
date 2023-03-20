package DataBase_Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CookieDBCImpl {
   
	private static CookieDBCImpl cookie;

	private CookieDBCImpl() {

	}

	public static CookieDBCImpl getInstance() {
		if (cookie == null) {
			cookie = new CookieDBCImpl();
		}
		return cookie;
	}
	
	public int addCookies(String Session_ID,String Phone_Number) throws SQLException
	{
		 int result = 0;

			String query = "insert into Sessions values(?, ?)";
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
			pstmt.setString(1, Session_ID);
			pstmt.setString(2, Phone_Number);
			result = pstmt.executeUpdate();
			
		    return result;
	}
	
	public String getPhoneNumber(String Session_ID) throws SQLException
	{
		
			String query = "select * from Sessions where Session_ID = ?";
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
			pstmt.setString(1, Session_ID);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				return rs.getString(2);
			}
		return null;
	}
	
	public int deleteCookie(String Session_ID) throws SQLException
	{
		 int result = 0;

			String query = "delete from Sessions where Session_ID = ?";
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
			pstmt.setString(1, Session_ID);
			result = pstmt.executeUpdate();
			
		return result;
	}
}
