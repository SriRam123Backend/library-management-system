package Controller;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import Model.Book_Floor_Details;
import Model.BorrowBook_Details;
import Service.BookServiceImpl;
import Service.UserServiceImpl;

/**
 * Servlet implementation class BorrowBooks
 */
@WebServlet("/filter/BorrowBooks")
public class BorrowBooks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BorrowBooks() {
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
		String Phone_Number = (String) request.getAttribute("Phone_Number");
		String ISBN_No = (String) request.getParameter("ISBN_No");
		String Return_Date = (String) request.getParameter("Return_Date");
		JSONObject BorrowBookDetails = new JSONObject();
		SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
		
		BorrowBook_Details Book = UserServiceImpl.getInstance().Borrow_Books(Integer.parseInt(ISBN_No), Return_Date, Phone_Number);
		
		Book_Floor_Details Details = BookServiceImpl.getInstance().getFloorDetails(Integer.parseInt(ISBN_No));
		
		BorrowBookDetails.put("Date_Borrowed",sdfrmt.format(Book.getDate_Borrowed()));
		BorrowBookDetails.put("Return_Date ",sdfrmt.format(Book.getReturn_Date()));
		BorrowBookDetails.put("ISBN_No",Book.getISBN_No());
		BorrowBookDetails.put("Floor_No",Details.getFloorNo().toString());
		BorrowBookDetails.put("Department",Details.getDepartment().toString());
		BorrowBookDetails.put("Shelve_Number",Details.getShelveNo());
		BorrowBookDetails.put("Status","Book Borrowed");
		
		response.getWriter().append(BorrowBookDetails.toString());
		
	}

}
