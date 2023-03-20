package Controller;

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

import Model.Book_Details;
import Model.BorrowBook_Details;
import Service.BookServiceImpl;
import Service.BookTransactionsServiceImpl;

/**
 * Servlet implementation class User_History
 */
@WebServlet("/filter/User_History")
public class User_History extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public User_History() {
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
        
		
		String Phone_Number = (String) request.getParameter("Phone_Number");
		if(Phone_Number.equals(""))
		{
			Phone_Number = (String) request.getAttribute("Phone_Number");	
		}
		JSONArray HistoryOfUser = new JSONArray();
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
		
		ArrayList<BorrowBook_Details> UsersHistory = BookTransactionsServiceImpl.getInstance().getSpecificPersonsBookTransactions(Phone_Number);
		
		for(BorrowBook_Details books: UsersHistory)
		{
			JSONObject book = new JSONObject();
						 
			 Book_Details bookDetails = BookServiceImpl.getInstance().showBookDeatils(books.getISBN_No());
		     book.put("Book_Name",bookDetails.getBookName());
		     book.put("ISBN_No",books.getISBN_No());
			 book.put("From_Date",sdfrmt.format(books.getDate_Borrowed()));
			 book.put("Return_Date",sdfrmt.format(books.getReturn_Date()));
			 if(books.getFined_Date() != null )
			 {
				 book.put("Fined_Date",sdfrmt.format(books.getFined_Date()));
			 }
			 book.put("Fine",books.getFine_Amount());
			 book.put("Status",books.getUStatus().toString());
			 
			 HistoryOfUser.add(book);
		}
		
	  response.getWriter().append(HistoryOfUser.toString());
	}

}
