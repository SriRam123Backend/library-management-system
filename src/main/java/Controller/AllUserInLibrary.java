package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Model.User_Details;
import Service.BookTransactionsServiceImpl;
import Service.UserServiceImpl;

/**
 * Servlet implementation class AllUserInLibrary
 */
@WebServlet("/AllUserInLibrary")
public class AllUserInLibrary extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllUserInLibrary() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		  BufferedReader reader = request.getReader();
		  String UserName = reader.readLine();
		  JSONObject object = null;
		  JSONParser parser = new JSONParser();
		  JSONArray AllUserDetails = new JSONArray();
		  SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
		  
		  try
		  {
		    object = (JSONObject) parser.parse(UserName);
		  }catch(ParseException err)
		  {
			  err.getMessage();
		  }
		  String userName = (String) object.get("UserName");
	      
		  ArrayList<User_Details> User_Details = UserServiceImpl.getInstance().getAllUser(userName+"%");
		  
		  System.out.println(User_Details.size());
		  
		  
		  for(User_Details User: User_Details)
		  {
			  JSONObject user = new JSONObject();
			  
				user.put("Name",User.getFirstName()+" "+User.getLastName());
				user.put("Date_Of_Birth",sdfrmt.format(User.getDob()));
				user.put("Phone_Number",User.getPhoneNumber());
				user.put("Gender",User.getGender().toString());
				
				int TotalBooksIssued = BookTransactionsServiceImpl.getInstance().getCountOfPerUserIssuedBooks(User.getPhoneNumber());
				user.put("TotalBooksIssued",TotalBooksIssued);	
				
				int TotalFineOfUser = UserServiceImpl.getInstance().getTotalFineOfUser(User.getPhoneNumber());
				user.put("TotalFineOfUser",TotalFineOfUser);
				
				AllUserDetails.add(user);
		  }
		  
	        response.getWriter().append(AllUserDetails.toString());
	}

}
