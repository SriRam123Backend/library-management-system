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
import Model.Book_Floor_Details;
import Service.BookServiceImpl;
import Service.BookTransactionsServiceImpl;

/**
 * Servlet implementation class SearchBooks
 */
@WebServlet("/filter/SearchBooks")
public class SearchBooks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchBooks() {
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
		  String SearchingBooks = reader.readLine();
		  JSONObject BookSpecifications = null;
		  JSONArray specificBooks = new JSONArray();
		  JSONParser parser = new JSONParser();
		  SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
		  
		  try
		  {
			BookSpecifications = (JSONObject) parser.parse(SearchingBooks);
		  }catch(ParseException err)
		  {
			  err.getMessage();
		  }

		 ArrayList<Book_Details> SearchedBooks = BookServiceImpl.getInstance().searchBooks(BookSpecifications);
		 
		 for(Book_Details books : SearchedBooks)
		 {
			 JSONObject book = new JSONObject();
			 
			  book.put("ISBN_No",books.getISBN_No());
		      book.put("Book_Name",books.getBookName());
			  book.put("Author_Name",books.getAuthorName());
			  book.put("Pages",books.getPages());
			  book.put("Published_Date",sdfrmt.format(books.getPublishedDate()));
			  book.put("Category",books.getCategory().toString());
			  book.put("Available_Count",books.getAvailableCount());
	          Boolean status = BookTransactionsServiceImpl.getInstance().CurrentUserBook(books.getISBN_No(),(String)request.getAttribute("Phone_Number"));
			  book.put("Boolean",status.toString());
			  
		      Book_Floor_Details Details = BookServiceImpl.getInstance().getFloorDetails(books.getISBN_No());
		      
		      book.put("Department", Details.getDepartment().toString());
		      book.put("Floor_Number",Details.getFloorNo().toString());
		      book.put("Shelve_No",Details.getShelveNo());
			 specificBooks.add(book);
		 }
		  response.getWriter().append(specificBooks.toString());
	}

}
