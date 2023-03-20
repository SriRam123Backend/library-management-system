package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import DataBase_Connection.CookieDBCImpl;
import Model.User_Details;
import Service.UserServiceImpl;

/**
 * Servlet implementation class SignUp_Page
 */
@WebServlet("/SignUp_Page")
public class SignUp_Page extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUp_Page() {
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
		BufferedReader reader  = request.getReader();
		String response_Object = reader.readLine();
		JSONObject User_Details = null;
		JSONObject user = new JSONObject();
		JSONParser parser = new JSONParser();
		
		  try
		  {
			  User_Details = (JSONObject) parser.parse(response_Object);
		  }catch(ParseException err)
		  {
			  err.getMessage();
		  }
		try {
				UUID uuid = UUID.randomUUID();
				String Session_ID = uuid.toString();

				User_Details User = UserServiceImpl.getInstance().NewUserRegistration(User_Details);
				
				if(User != null)
				{
					System.out.println(User.getPhoneNumber());
					user.put("Name",User.getFirstName()+" "+User.getLastName());
					user.put("Role", User.getRole().toString());			
					user.put("Gender",User.getGender().toString());
					Cookie cookie = new Cookie("Session_ID", Session_ID);
					System.out.println(Session_ID);
					System.out.println(User.getPhoneNumber());
					int result = CookieDBCImpl.getInstance().addCookies(Session_ID,User.getPhoneNumber());
					if(result != 0)
					{
					  user.put("Message","Successfull");
					  response.addCookie(cookie);
					}
					else
					{
					  user.put("Message","Something went wrong");
					}
				}
				else {
					user.put("Message","User is not Found");
				}
					
	            response.getWriter().append(user.toString());
				
			} catch (SQLException e) {
				e.getMessage();
			}
	}

}
