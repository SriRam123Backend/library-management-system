package Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONObject;

import DataBase_Connection.UserDBC;
import DataBase_Connection.UserDBCImpl;
import Enums.Gender;
import Enums.Role;
import Enums.User_Status;
import Model.Book_Details;
import Model.BorrowBook_Details;
import Model.User_Details;

public class UserServiceImpl implements UserService{
   
	private static UserServiceImpl User = null;
	
	private UserServiceImpl()
	{
		
	}
	
	public static UserServiceImpl getInstance()
	{
		if(User==null)
		{
			User = new UserServiceImpl(); 
		}
		return User;
	}
	

	  public ArrayList<User_Details> getAllUser() { 
		 
		  UserDBC dao =UserDBCImpl.getInstance();
		  ArrayList<User_Details> allUserDetails= new ArrayList<User_Details>();
		  
		  try { 
			  
			 ResultSet rs = dao.getAllUser();
	  
			 while(rs.next()) { 
				 User_Details user = new User_Details();
	             user.setFirstName(rs.getString(2)); 
	             user.setLastName(rs.getString(3));
	             user.setPassword(rs.getString(4));
	             
	             try { 
	            	 user.setDob(new SimpleDateFormat("dd/mm/yyyy").parse(rs.getString(5))); 
	              } catch(ParseException e) {
	            	  e.getMessage(); 
	              }
	              user.setPhoneNumber(rs.getString(6).toString());
	              user.setGender(Gender.valueOf(rs.getString(7).toString().toUpperCase()));
	              user.setRole(Role.USER); allUserDetails.add(user); 
	              } 
			 }catch(SQLException er)
	          { 
				 er.getMessage(); 
			  } 
		  return allUserDetails; 
	}
	 
	
	
	public ArrayList<User_Details> getAllUser(String User_Name)
	{
		UserDBC dao = UserDBCImpl.getInstance();
		ArrayList<User_Details> allUserDetails= new ArrayList<User_Details>();
		try
		{
			ResultSet rs = dao.getAllUser(User_Name.trim());
			while(rs.next())
			{
				User_Details user = new User_Details();
		    	user.setFirstName(rs.getString(2));
		    	user.setLastName(rs.getString(3));
		    	user.setPassword(rs.getString(4));
				try {
					user.setDob(new SimpleDateFormat("dd/mm/yyyy").parse(rs.getString(5)));
				} catch (ParseException e) {
					e.getMessage();
				}
		    	user.setPhoneNumber(rs.getString(6).toString());
		    	user.setGender(Gender.valueOf(rs.getString(7).toString().toUpperCase()));
		    	user.setRole(Role.USER);
		    	allUserDetails.add(user);
			}
		}catch(SQLException er)
		{
			er.getMessage();
		}
		return allUserDetails;
	}
	
	public int getCountOfAllUser() {
		
		UserDBC dao = UserDBCImpl.getInstance();
		int TotalUser = 0; 
		try
		{
			ResultSet rs = dao.getCountOfAllUser();
			if(rs.next())
			{		 
				TotalUser = rs.getInt(1);
			}
		}catch(SQLException er)
		{
			er.getMessage();
		}
		return TotalUser;
		
		
	}
	
	
	public User_Details findUserRoleByPhoneNumber(String Phone_Number,String Password)
	{
		UserDBC dao = UserDBCImpl.getInstance();
		User_Details User = new User_Details();
		try
		{
			ResultSet rs = dao.findUserRoleByPhoneNumber(Phone_Number, Password);
			if(rs.next())
			{		
				User.setFirstName(rs.getString(2));
				User.setLastName(rs.getString(3));
				User.setGender(Gender.valueOf(rs.getString(7).toUpperCase()));
				User.setRole(Role.valueOf(rs.getString(8).toUpperCase()));
				
				return User;
			}
		}catch(SQLException er)
		{
			er.getMessage();
		}
		return null;
	}
	
	public User_Details NewUserRegistration(JSONObject User)
	{
		UserDBC dao = UserDBCImpl.getInstance();
		User_Details user = null;
		int result = 0;
	    try
	    {
	    	user = new User_Details();
	    	user.setFirstName(User.get("First_Name").toString());
	    	user.setLastName(User.get("Last_Name").toString());
	    	user.setPassword(User.get("Password").toString());
			try {
				user.setDob(new SimpleDateFormat("yyyy-MM-dd").parse(User.get("Date_Of_Birth").toString()));
			} catch (ParseException e) {
				e.getMessage();
			}
	    	user.setPhoneNumber(User.get("Phone_Number").toString());
	    	user.setGender(Gender.valueOf(User.get("Gender").toString().toUpperCase()));
	    	user.setRole(Role.USER);
	    	
	    	result = dao.NewUserRegistration(user);
	    	System.out.println(result);
	    	if(result != 0)
	    	{
	    		return user;
	    	}
	    }catch(SQLException er)
	    {
	    	er.getMessage();
	    	
	    }
	   return user; 
	}
		
	public User_Details ViewProfile(String Phone_Number) {
		
		UserDBC dao = UserDBCImpl.getInstance();
		User_Details User = new User_Details();
		
		try {
			ResultSet rs = dao.ViewProfile(Phone_Number);
			if(rs.next())
			{
				User.setFirstName(rs.getString(2));
				User.setLastName(rs.getString(3));
				User.setPassword(rs.getString(4));
				try {
					User.setDob(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString(5)));
				} catch (ParseException e) {
					e.getMessage();
				}
				User.setGender(Gender.valueOf(rs.getString(7).toUpperCase()));
				User.setPhoneNumber(Phone_Number);
				User.setRole(Role.valueOf(rs.getString(8).toUpperCase()));
				
				return User;
			 }
		}catch(SQLException er)
		{
			er.getMessage();
		}
		
		return null;
	}
	
	public boolean UpdateProfile(JSONObject User) {
		
		UserDBC dao = UserDBCImpl.getInstance();
		int result = 0;
	    try
	    {
	    	User_Details user = new User_Details();
	    	user.setFirstName(User.get("First_Name").toString());
	    	user.setLastName(User.get("Last_Name").toString());
	    	user.setPassword(User.get("Password").toString());
			try {
				user.setDob(new SimpleDateFormat("yyyy-MM-dd").parse(User.get("Date_Of_Birth").toString()));
			} catch (ParseException e) {
				e.getMessage();
			}
	    	user.setPhoneNumber(User.get("Phone_Number").toString());
	    	user.setGender(Gender.valueOf(User.get("Gender").toString().toUpperCase()));
	    	user.setRole(Role.USER);
	    	
	    	result = dao.UpdateProfile(user);
	    	if(result != 0)
	    	{
	    		return true;
	    	}
	    }catch(SQLException er)
	    {
	    	er.getMessage();
	    	
	    }
	   return false; 
	}
	
	public int getTotalFineOfUser(String Phone_Number)
	{
		UserDBC dao = UserDBCImpl.getInstance();
		int TotalFine = 0;
		try
		{
		   ResultSet rs = dao.getTotalFineOfUser(Phone_Number);
		   if(rs.next())
		   {
			TotalFine = rs.getInt(1); 
		   }
		}catch(SQLException er)
		{
			er.getMessage();
		}
	   return TotalFine;
	}
	
	public synchronized BorrowBook_Details Borrow_Books(int ISBN_Number,String Return_Date,String Phone_Number)
	
	{	
		BorrowBook_Details books = null;
		UserDBC dao = UserDBCImpl.getInstance();
		
		LocalDateTime ldt = LocalDateTime.now();
		
		try {
			
			Book_Details book = BookServiceImpl.getInstance().showBookDeatils(ISBN_Number);
		    books = new BorrowBook_Details();
			books.setISBN_No(book.getISBN_No());
			books.setDate_Borrowed(Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant()));
			System.out.println((books.getDate_Borrowed()));
			try {
				books.setReturn_Date(new SimpleDateFormat("yyyy-MM-dd").parse(Return_Date));
			} catch (ParseException e) {
				e.getMessage();
			}
			books.setPhone_Number(Phone_Number);
			if(book.getAvailableCount()>0)
			{
			book.setAvailableCount(book.getAvailableCount()-1);
			}
			else
			{
				return null;
			}
			System.out.println(book.getAvailableCount());
			int rs = dao.Borrow_Books(books,book);
			if(rs != 0)
			{
				return books;
			}
		}catch(SQLException err)
		{
			err.getMessage();
		}
		
		return books;	
	}
	
	
	public User_Details UpdateUserDetails(JSONObject User,String Phone_Number)
	{
		UserDBC dao = UserDBCImpl.getInstance();
		User_Details user = null;
		int result = 0;
	    try
	    {
	    	user = new User_Details();
	    	user.setFirstName(User.get("First_Name").toString());
	    	user.setLastName(User.get("Last_Name").toString());
	    	user.setPassword(User.get("Password").toString());
			try {
				user.setDob(new SimpleDateFormat("yyyy-MM-dd").parse(User.get("Date_Of_Birth").toString()));
			} catch (ParseException e) {
				e.getMessage();
			}
	    	user.setPhoneNumber(User.get("Phone_Number").toString());
	    	System.out.println(User.get("Phone_Number").toString());
	    	user.setGender(Gender.valueOf(User.get("Gender").toString().toUpperCase()));
	    	user.setRole(Role.USER);
	    	System.out.println(Phone_Number);
	    	result = dao.UpdateUserDetails(user,Phone_Number);
	    	System.out.println(result);
	    	if(result != 0)
	    	{
	    		return user;
	    	}
	    }catch(SQLException er)
	    {
	    	er.getMessage();
	    	
	    }
	   return user; 
	}
	
	public int Return_Books(int ISBN_No,String Phone_Number)
	{
		UserDBC dao = UserDBCImpl.getInstance();
		
		try{
			int returned = dao.returnBooks(ISBN_No,Phone_Number);
			
			return returned;
		   
		}catch(SQLException err)
		{
			err.getMessage();
		}
		
		return 0;	
	}
	
	public int Return_Books(JSONObject Return_Details,String Phone_Number)
	{
		UserDBC dao = UserDBCImpl.getInstance();
		LocalDateTime ldt = LocalDateTime.now();
		
		try {
			BorrowBook_Details returnDetails = new BorrowBook_Details(); 
			
			returnDetails.setFined_Date(Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant()));
			returnDetails.setISBN_No(Integer.parseInt(Return_Details.get("ISBN_No").toString()));
			returnDetails.setPhone_Number(Phone_Number);
			returnDetails.setFine_Amount(Integer.parseInt(Return_Details.get("Fine").toString()));
			returnDetails.setUStatus(User_Status.valueOf("RETURNED"));
			int returned = dao.returnBooks(returnDetails);
			
			if(returned != 0)
			{
				return 1;
			}
		   
		}catch(SQLException err)
		{
			err.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public int totalLoginUser() {
		
		UserDBC dao = UserDBCImpl.getInstance();
		int result = 0;
		try {
			ResultSet rs = dao.totalLoginUser();
			if(rs.next())
			{
				result = rs.getInt(1);
			}
		}catch(SQLException err)
		{
			err.getMessage();
		}
		return result;
	}

}
