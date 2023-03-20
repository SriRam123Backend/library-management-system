package Controller;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Model.User_Details;
import Service.UserServiceImpl;

/**
 * Servlet implementation class UpdateUserDetails
 */
@WebServlet("/filter/UpdateUserDetails")
public class UpdateUserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserDetails() {
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
			
		BufferedReader reader  = request.getReader();
		String response_Object = reader.readLine();
		JSONObject User_Details = null;
		JSONObject user = new JSONObject();
		JSONParser parser = new JSONParser();
		String Phone_Number  = (String) request.getAttribute("Phone_Number");
		
		  try
		  {
			  User_Details = (JSONObject) parser.parse(response_Object);
		  }catch(ParseException err)
		  {
			  err.getMessage();
		  }
		  
				
	    	User_Details User = UserServiceImpl.getInstance().UpdateUserDetails(User_Details,Phone_Number);
				
		    if(User != null)
			{	
			  user.put("Message","Successfull");
			}
			else {
				user.put("Message","Something Went Wrong");
			}
					
	         response.getWriter().append(user.toString());
				
    } 

}
