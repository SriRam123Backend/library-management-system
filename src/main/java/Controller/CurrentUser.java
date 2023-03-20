package Controller;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import Model.User_Details;
import Service.UserServiceImpl;

/**
 * Servlet implementation class CurrentUser
 */
@WebServlet("/filter/CurrentUser")
public class CurrentUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CurrentUser() {
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
		
		String Phone_Number = (String) request.getAttribute("Phone_Number");		
		JSONObject user = new JSONObject();
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			
			User_Details User = UserServiceImpl.getInstance().ViewProfile(Phone_Number);
			
			if(User != null)
			{
				user.put("Name",User.getFirstName()+" "+User.getLastName());
				user.put("First_Name",User.getFirstName());
				user.put("Last_Name",User.getLastName());
				user.put("Password", User.getPassword());
				user.put("Date_Of_Birth",sdfrmt.format(User.getDob()));
				user.put("Phone_Number",User.getPhoneNumber());
				user.put("Gender",User.getGender().toString());
				user.put("Role", User.getRole().toString());
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
