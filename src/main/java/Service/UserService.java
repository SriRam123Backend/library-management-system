package Service;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import Model.BorrowBook_Details;
import Model.User_Details;

public interface UserService {
     
	User_Details findUserRoleByPhoneNumber(String Phone_Number,String Password);
	
	User_Details NewUserRegistration(JSONObject User);
	
	ArrayList<User_Details> getAllUser();
	
	ArrayList<User_Details> getAllUser(String User_Name);
	
	User_Details ViewProfile(String Phone_Number);
	
	boolean UpdateProfile(JSONObject User);
	
	int getTotalFineOfUser(String Phone_Number);
	
	int getCountOfAllUser();
	
	BorrowBook_Details Borrow_Books(int ISBN_No,String Return_Date,String Phone_Number);
	
	User_Details UpdateUserDetails(JSONObject User,String Phone_Number);
	
	int Return_Books(int ISBN_No,String Phone_Number);
	
	
	int Return_Books(JSONObject Return_Details,String Phone_Number);
	
	int totalLoginUser();
	
}
