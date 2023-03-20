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

import Model.Book_Details;
import Model.BorrowBook_Details;
import Model.User_Details;
import Service.BookServiceImpl;
import Service.BookTransactionsServiceImpl;
import Service.UserServiceImpl;

/**
 * Servlet implementation class BorrowBookDetails
 */
@WebServlet("/BorrowBookDetails")
public class BorrowBookDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorrowBookDetails() {
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
		  String BookName = reader.readLine();
		  JSONObject object = null;
		  JSONParser parser = new JSONParser();
		  JSONArray BorrowedBookDetails = new JSONArray();
		  SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
		  
		  try
		  {
		    object = (JSONObject) parser.parse(BookName);
		  }catch(ParseException err)
		  {
			  err.getMessage();
		  }
			String Book_Name = (String) object.get("bookName");
			
			ArrayList<BorrowBook_Details> Book = BookTransactionsServiceImpl.getInstance().getAllBookTransactions(Book_Name+"%");
			
			for(BorrowBook_Details books: Book)
			{
				JSONObject book = new JSONObject();
							 
				 Book_Details bookDetails = BookServiceImpl.getInstance().showBookDeatils(books.getISBN_No());
			     book.put("Book_Name",bookDetails.getBookName());
			     
			     book.put("ISBN_No",books.getISBN_No());
			     
			     User_Details user = UserServiceImpl.getInstance().ViewProfile(books.getPhone_Number());
				 book.put("Name",user.getName());
				 
				 book.put("Phone_Number",books.getPhone_Number());
				 book.put("From_Date",sdfrmt.format(books.getDate_Borrowed()));
				 book.put("Return_Date",sdfrmt.format(books.getReturn_Date()));
				 book.put("Fine",books.getFine_Amount());
				 book.put("Status",books.getUStatus().toString());
				 
				 BorrowedBookDetails.add(book);
			}
			
		  response.getWriter().append(BorrowedBookDetails.toString());
	}

}
