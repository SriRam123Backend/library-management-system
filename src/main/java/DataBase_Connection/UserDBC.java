package DataBase_Connection;

import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Book_Details;
import Model.BorrowBook_Details;
import Model.User_Details;


public interface UserDBC {
	
	ResultSet getAllUser() throws SQLException;
   
	ResultSet getAllUser(String User_Name) throws SQLException;
	
	ResultSet findUserRoleByPhoneNumber(String Phone_Number,String Password) throws SQLException;
	
	int NewUserRegistration(User_Details User) throws SQLException;
	
	ResultSet ViewProfile(String Phone_Number) throws SQLException;
	
	int UpdateProfile(User_Details User) throws SQLException;
	
	ResultSet getTotalFineOfUser(String Phone_Number) throws SQLException;
	
	ResultSet getCountOfAllUser() throws SQLException;
	
	int Borrow_Books(BorrowBook_Details book,Book_Details books) throws SQLException;
	
	int UpdateUserDetails(User_Details User,String Phone_Number) throws SQLException;
	
	int returnBooks(int ISBN_Number,String Phone_Number) throws SQLException;
	
	int returnBooks(BorrowBook_Details book) throws SQLException;
	
	ResultSet totalLoginUser() throws SQLException;
	
}
