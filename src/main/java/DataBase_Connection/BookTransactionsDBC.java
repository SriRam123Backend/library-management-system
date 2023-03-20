package DataBase_Connection;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface BookTransactionsDBC {
   
	ResultSet getAllBookTransactions(String Book_Name) throws SQLException;
	
	ResultSet getSpecificPersonsBookTransactions(String Phone_Number) throws SQLException ;
	
	ResultSet getCountOfPerUserIssuedBooks(String Phone_Number) throws SQLException;
	
	ResultSet isCurrentUserBook(int ISBN_No,String Phone_Number) throws SQLException;
}
