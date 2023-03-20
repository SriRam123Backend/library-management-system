package Controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import Model.User_Details;
import Service.UserServiceImpl;

/**
 * Servlet implementation class ReturnBooks
 */
@WebServlet("/filter/ReturnBooks")
public class ReturnBooks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReturnBooks() {
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
	@SuppressWarnings({ "unused", "unchecked" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		String Phone_Number = (String) request.getAttribute("Phone_Number");
		String Password = (String) request.getParameter("Password");
		System.out.println(Phone_Number);
		System.out.println(Password);
		String ISBN_No = (String) request.getParameter("ISBN_No");
		JSONObject user = new JSONObject();

		try {
		    
			User_Details User = UserServiceImpl.getInstance().findUserRoleByPhoneNumber(Phone_Number, Password);
			
			if(User != null)
			{
                int status = UserServiceImpl.getInstance().Return_Books(Integer.parseInt(ISBN_No),Phone_Number);
                user.put("Message","Successfull");
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
