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
 * Servlet implementation class AddBooks
 */
@WebServlet("/AddBooks")
public class AddBooks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddBooks() {
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
	@SuppressWarnings({"unchecked"})
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	  BufferedReader reader = request.getReader();
	  String BookDetails = reader.readLine();
	  JSONObject Book = null;
	  
	  JSONParser parser = new JSONParser();
	  try
	  {
		  Book = (JSONObject) parser.parse(BookDetails);
	  }catch(ParseException err)
	  {
		  err.getMessage();
	  }
	  
	  HashMap<String,Object> map = BookServiceImpl.getInstance().addBooks(Book);
	  Book_Details book = (Book_Details) map.get("NewBook");
	  
	  JSONObject addedBook = new JSONObject();
	  SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
	  
    	  if(book == null)
    	  {
    		  addedBook.put("Message","Invalid Input Please Check it");
    	  }
    	  
    	  else if(book!=null)
          {
    		addedBook.put("ISBN_No",book.getISBN_No());
    		addedBook.put("Book_Name",book.getBookName());
    		addedBook.put("Author_Name",book.getAuthorName());
    		addedBook.put("Pages",book.getPages());
    		addedBook.put("Published_Date",sdfrmt.format(book.getPublishedDate()));
  			addedBook.put("Category",book.getCategory().toString());
  			addedBook.put("Available_Count",book.getAvailableCount());
  			
  			Book_Floor_Details Floor_Details = (Book_Floor_Details) book.getFloorDetails();
  			
  			addedBook.put("Floor_No",Floor_Details.getFloorNo().toString());
  			addedBook.put("Department",Floor_Details.getDepartment().toString());
  			addedBook.put("Shelve_Number",Floor_Details.getShelveNo());
  			addedBook.put("Status","NOT_BORROWED");
  			addedBook.put("Message","Successfully Book Added");
          } 
    	  
	  response.getWriter().append(addedBook.toString());
	}

}
