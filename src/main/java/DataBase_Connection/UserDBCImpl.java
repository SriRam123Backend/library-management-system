package DataBase_Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import Model.Book_Details;
import Model.BorrowBook_Details;
import Model.User_Details;

public class UserDBCImpl implements UserDBC{

	 private static UserDBCImpl User = null;
	 
	 private UserDBCImpl()
	 {
		 
	 }
	 
	 public static UserDBCImpl getInstance()
	 {
		 if(User==null)
		 {
			 User = new UserDBCImpl();
		 }
		 return User;
	 }
	 
	 
	 public ResultSet getAllUser() throws SQLException
	 {
		 ResultSet rs = null;
		 String query = "select * from User_Details where Role != 'Admin' order by S_No desc";
		 PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
		 rs = pstmt.executeQuery();
	     return rs;
	 }
	 
	 
	 public ResultSet getCountOfAllUser() throws SQLException
	 {
		 ResultSet rs = null;
		 String query = "select count(*) from User_Details where Role != 'Admin' order by S_No desc";
		 PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
		 rs = pstmt.executeQuery();
	     return rs;
	 }
	 
	 public ResultSet getAllUser(String User_Name) throws SQLException
	 {
		 ResultSet rs = null;
		 String query = "select * from User_Details where First_Name like ? and Role != 'Admin' order by S_No desc";
		 PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
		 pstmt.setString(1,User_Name);
		 rs = pstmt.executeQuery();
	     return rs;
	 }
	 
	 public ResultSet findUserRoleByPhoneNumber(String Phone_Number,String Password) throws SQLException
	 {
		 ResultSet rs = null;
		 
			 String query = "select * from User_Details where Phone_Number=? and Password = ?";
			 PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
			 pstmt.setString(1, Phone_Number);
			 pstmt.setString(2, Password);
			 rs = pstmt.executeQuery();
		     return rs;
	 }
	 
	 public int NewUserRegistration(User_Details User) throws SQLException
	 {
		 int result = 0;

			String query = "insert into User_Details (First_Name,Second_Name,Password,Date_Of_Birth,Phone_Number,Gender,Role) values(?, ?, ?, ?, ?, ?,'User')";
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
			SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
			
			pstmt.setString(1,User.getFirstName());
		    pstmt.setString(2,User.getLastName());
		    pstmt.setString(3,User.getPassword() );
		    pstmt.setString(4,sdfrmt.format(User.getDob()));
			pstmt.setString(5,User.getPhoneNumber());
		    pstmt.setString(6,User.getGender().toString());
		    
		    result = pstmt.executeUpdate();
		    System.out.println(result);
		return result;
	 }
	 	 
	 public ResultSet ViewProfile(String Phone_Number) throws SQLException {
		 
		ResultSet rs = null;
		
		    String query = "select * from User_Details where Phone_Number=?";
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
			pstmt.setString(1,Phone_Number);
			rs = pstmt.executeQuery();
			
	    return rs;
	 }
	 
	 public int UpdateProfile(User_Details User) throws SQLException {
		 
		 int result = 0;
		 
			String query = "update User_Details set First_Name=?,Second_Name=?,Password=?,Date_Of_Birth=?,Phone_Number=?,Gender=? where Phone_Number = ?";
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
			pstmt.setString(7,User.getPhoneNumber());
			result = pstmt.executeUpdate();
				 
		return result;
	 }
	 
	 public ResultSet getTotalFineOfUser(String Phone_Number) throws SQLException {
		 
			ResultSet rs = null;
			
		    String query = "select SUM(FINE) from Borrow_Details where Phone_Number = ?";
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
			pstmt.setString(1,Phone_Number);
			rs = pstmt.executeQuery();
			
		return rs;
	 }
	 
	 public int Borrow_Books(BorrowBook_Details book,Book_Details books) throws SQLException {
		 
			int result = 0;
			
		    String query = "insert into Borrow_Details (ISBN_No,Phone_Number,Book_Name,From_Date,Return_date,Status) values (?,?,?,?,?,'NOT RETURNED')";
		    String query1 = "update Book_Details set Available_Count = ? where ISBN_No = ?";
		    
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
			
			PreparedStatement pstmt1 = DbConnection.dbConnection.prepareStatement(query1);
			pstmt1.setInt(1,books.getAvailableCount());
			System.out.println("booksss"+books.getAvailableCount());
			pstmt1.setInt(2, book.getISBN_No());
			pstmt1.executeUpdate();
			
			SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
			
			pstmt.setInt(1,book.getISBN_No());
			pstmt.setString(2,book.getPhone_Number());
			pstmt.setString(3,books.getBookName());
			pstmt.setString(4,sdfrmt.format(book.getDate_Borrowed()));
			pstmt.setString(5,sdfrmt.format(book.getReturn_Date()));
			result = pstmt.executeUpdate();
		
		return result;
	 }
	 
	 public int UpdateUserDetails(User_Details User,String Phone_Number) throws SQLException
	 {
		 int result = 0;

			String query = "update User_Details set First_Name = ?,Second_Name = ?,Password = ?,Date_Of_Birth = ? ,Phone_Number= ?,Gender = ? where Phone_Number = ?";
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
			SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
			
			pstmt.setString(1,User.getFirstName());
		    pstmt.setString(2,User.getLastName());
		    pstmt.setString(3,User.getPassword() );
		    pstmt.setString(4,sdfrmt.format(User.getDob()));
			pstmt.setString(5,User.getPhoneNumber());
		    pstmt.setString(6,User.getGender().toString());
		    pstmt.setString(7,Phone_Number);
		    
		    System.out.println(User.getPhoneNumber());
		    System.out.println(Phone_Number);
		    System.out.println(pstmt);
		    result = pstmt.executeUpdate();
		    System.out.println(result);
		return result;
	 }
	 
	 public int returnBooks(int ISBN_Number,String Phone_Number) throws SQLException{
		 
		 System.out.println("fhjgjg");
		 
		    int result = 0;
		    SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
		    
			String query = "update Borrow_Details set Status = ?,Return_Date = ? where ISBN_No = ? and Phone_Number = ?";
			String query1 = "update Book_Details set Available_Count = ? where ISBN_No = ?";
			String query2 = "select Available_Count from Book_Details where ISBN_No = ?";

			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
			PreparedStatement pstmt1 = DbConnection.dbConnection.prepareStatement(query1);
			PreparedStatement pstmt2 = DbConnection.dbConnection.prepareStatement(query2);
			
			pstmt2.setInt(1,ISBN_Number);
			ResultSet rs = pstmt2.executeQuery();
			
		    pstmt.setString(1,"RETURNED");
			LocalDateTime ldt = LocalDateTime.now();
			pstmt.setString(2,sdfrmt.format(Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant())));
		    pstmt.setInt(3,ISBN_Number);
		    pstmt.setString(4,Phone_Number);
		    
			int availableStock = 0;
			if(rs.next())
			{
				availableStock = rs.getInt(1);
			}
			System.out.println(availableStock);
		    pstmt1.setInt(1,availableStock+1);
		    pstmt1.setInt(2,ISBN_Number);
			System.out.println(pstmt1);
		    int result2 = pstmt1.executeUpdate();
		    
		    if(result2 == 1)
		    {
		      result = pstmt.executeUpdate();
		    }
		    return result;
	 }
	 
	 public int returnBooks(BorrowBook_Details Book) throws SQLException{
		 
		 int result = 0;
		 SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
		 
		String query = "update Borrow_Details set Status = ?,Fine = ?, Fined_Date = ?,Return_Date = ? where ISBN_No = ? and Phone_Number = ?";
		String query1 = "update Book_Details set Available_Count = ? where ISBN_No = ?";
		String query2 = "select Available_Count from Book_Details where ISBN_No = ?";
		
		PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
		PreparedStatement pstmt1 = DbConnection.dbConnection.prepareStatement(query1);
		PreparedStatement pstmt2 = DbConnection.dbConnection.prepareStatement(query2);
		
	    pstmt2.setInt(1,Book.getISBN_No());
		ResultSet rs = pstmt2.executeQuery();
		
	    pstmt.setString(1,Book.getUStatus().toString());
		pstmt.setInt(2,Book.getFine_Amount());
		pstmt.setString(3, sdfrmt.format(Book.getFined_Date()));
		LocalDateTime ldt = LocalDateTime.now();
		pstmt.setString(4,sdfrmt.format(Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant())));
	    pstmt.setInt(5,Book.getISBN_No());
	    pstmt.setString(6,Book.getPhone_Number());
		    
	    pstmt1.setInt(1,rs.getInt(1)+1);
	    pstmt1.setInt(2,Book.getISBN_No());
	    pstmt1.executeUpdate();
	    
	    System.out.println(pstmt);
		result = pstmt.executeUpdate();
		return result;
	 }
	 
	 public ResultSet totalLoginUser() throws SQLException
	 {
			String query = "select count(*) Sessions where Phone_Number='9443684363' and '7871656361'";
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
		    
		return pstmt.executeQuery();
	 }
}
