package Controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import DataBase_Connection.CookieDBCImpl;
import Model.User_Details;
import Service.UserServiceImpl;

/**
 * Servlet implementation class Login_Page
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Login() {
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
		String Phone_Number = request.getParameter("Phone_Number");
		String Password = request.getParameter("Password");
		JSONObject user = new JSONObject();

		try {
			UUID uuid = UUID.randomUUID();
			String Session_ID = uuid.toString();
		    
			User_Details User = UserServiceImpl.getInstance().findUserRoleByPhoneNumber(Phone_Number, Password);
			
			if(User != null)
			{
				user.put("Name",User.getFirstName()+" "+User.getLastName());
				user.put("Role", User.getRole().toString());
				user.put("Gender",User.getGender().toString());
				Cookie cookie = new Cookie("Session_ID", Session_ID);
				System.out.println(Session_ID);
				System.out.println(Phone_Number);
				int result = CookieDBCImpl.getInstance().addCookies(Session_ID, Phone_Number);
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
            
		}catch(Exception ex) {
			
			ex.printStackTrace();
		}
	}
}
