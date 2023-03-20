package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Model.Book_Details;
import Model.Book_Floor_Details;
import Service.BookServiceImpl;

/**
 * Servlet implementation class UpdateBookDetails
 */
@WebServlet("/UpdateBookDetails")
public class UpdateBookDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateBookDetails() {
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
	@SuppressWarnings({ "unchecked"})
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		  BufferedReader reader = request.getReader();
		  String UpdateBookDetails = reader.readLine();
		  JSONObject BookDetails = null;
		  JSONParser parser = new JSONParser();
		  SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
		  
		  try
		  {
			  BookDetails = (JSONObject) parser.parse(UpdateBookDetails);
		  }catch(ParseException err)
		  {
			  err.getMessage();
		  }
		  
		  HashMap<String,Object> map = BookServiceImpl.getInstance().updateBooks(BookDetails);
		  Book_Details book = (Book_Details) map.get("UpdatedBook");
		  Book_Floor_Details Details = (Book_Floor_Details) map.get("FloorDetails");
		  JSONObject UpdatedBook = new JSONObject();
		  
	    	  if(book == null)
	    	  {
	    		  UpdatedBook.put("Message","Invalid Input Please Check it");
	    	  }
	    	  
	    	  else if(book!=null)
	          {
	    		UpdatedBook.put("ISBN_No",book.getISBN_No());
	    		UpdatedBook.put("Book_Name",book.getBookName());
	    		UpdatedBook.put("Author_Name",book.getAuthorName());
	    		UpdatedBook.put("Pages",book.getPages());
	    		UpdatedBook.put("Published_Date",sdfrmt.format(book.getPublishedDate()));
	  			UpdatedBook.put("Category",book.getCategory().toString());
	  			UpdatedBook.put("Available_Count",book.getAvailableCount());
	    		UpdatedBook.put("Floor_No",Details.getFloorNo().toString());
	  			UpdatedBook.put("Department",Details.getDepartment().toString());
	  			UpdatedBook.put("Shelve_Number",Details.getShelveNo());
	  			UpdatedBook.put("Status","NOT_BORROWED");
	  			UpdatedBook.put("Message","Successfully Book Updated");
	          } 
	    	  
		  response.getWriter().append(UpdatedBook.toString());
	}

}
