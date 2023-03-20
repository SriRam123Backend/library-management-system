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

import Service.UserServiceImpl;

/**
 * Servlet implementation class BankDetails
 */
@WebServlet("/filter/BankDetails")
public class BankDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BankDetails() {
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
		  BufferedReader reader = request.getReader();
		  String ReturnDetails = reader.readLine();
		  JSONObject object = null;
		  JSONParser parser = new JSONParser();
		  
		  try
		  {
		    object = (JSONObject) parser.parse(ReturnDetails);
		  }catch(ParseException err)
		  {
			  err.getMessage();
		  }
		  
		  int Status = UserServiceImpl.getInstance().Return_Books(object, Phone_Number);
		  
		  JSONObject responseObject = new JSONObject();
		  if(Status != 0)
		  {
			  responseObject.put("Message","Successfull");
		  }
		  else
		  {
			  responseObject.put("Message","Something went wrong");
		  }
		  
		  response.getWriter().append(responseObject.toString());
	}

}
