package DataBase_Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookTransactionsDBCImpl implements BookTransactionsDBC{

	 private static BookTransactionsDBCImpl BookTransactions;
	 
	private BookTransactionsDBCImpl() {

	}

	public static BookTransactionsDBCImpl getInstance() {
	  if (BookTransactions == null) {
		  BookTransactions = new BookTransactionsDBCImpl();
	}
			return BookTransactions;
	}
	
	public ResultSet getAllBookTransactions(String Book_Name) throws SQLException{
		
		 ResultSet rs = null;
		 String query = "select * from Borrow_Details where Book_name like ? order by S_No desc";
		 PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
		 pstmt.setString(1,Book_Name);
		 rs = pstmt.executeQuery();
	     return rs;
	}
	
	public ResultSet getSpecificPersonsBookTransactions(String Phone_Number) throws SQLException{
		
		 ResultSet rs = null;
		 String query = "select * from Borrow_Details where Phone_Number = ? order by S_No desc";
		 PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
		 pstmt.setString(1,Phone_Number);
		 rs = pstmt.executeQuery();
	     return rs;
	}
	
	public ResultSet getCountOfPerUserIssuedBooks(String Phone_Number) throws SQLException
	{
		 ResultSet result = null;
		 String query = "select count(*) from Borrow_Details where Phone_Number = ? order by S_No";
		 PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
		 pstmt.setString(1,Phone_Number);
		 result = pstmt.executeQuery();
	     return result;
	}
	
	public ResultSet isCurrentUserBook(int ISBN_No,String Phone_Number) throws SQLException
	{
		 ResultSet result = null;
		 String query = "select * from Borrow_Details where Phone_Number = ? and ISBN_No = ? and Status = 'NOT RETURNED' order by S_No";
		 PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
		 pstmt.setString(1,Phone_Number);
		 pstmt.setInt(2, ISBN_No);
		 result = pstmt.executeQuery();
	     return result;
	}
}

