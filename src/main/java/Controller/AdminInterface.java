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
import Model.User_Details;
import Service.BookServiceImpl;
import Service.BookTransactionsServiceImpl;
import Service.UserServiceImpl;

/**
 * Servlet implementation class AdminInterface
 */
@WebServlet("/AdminInterface")
public class AdminInterface extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminInterface() {
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
		    JSONArray DashBoard_Details = new JSONArray();
		    JSONObject Library_Details = new JSONObject();
		    SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
		    
		    
		    int TotalNoOfBooks = BookServiceImpl.getInstance().getCountOfAllBooks();
		    Library_Details.put("TotalBooks",TotalNoOfBooks);
		    
		    int TotalNoOfUsers = UserServiceImpl.getInstance().getCountOfAllUser();
		    Library_Details.put("TotalUser",TotalNoOfUsers);
	
		    DashBoard_Details.add(Library_Details);
		    
		    ArrayList<Book_Details> AllBooks = BookServiceImpl.getInstance().getAllBooks();
		    ArrayList<User_Details> AllUser = UserServiceImpl.getInstance().getAllUser();
		    int TotalLoggedInUser = UserServiceImpl.getInstance().totalLoginUser();
		    int TotalLoggedOutUser = TotalNoOfUsers-TotalLoggedInUser;
		    
		    Library_Details.put("TotalLoggedInUser",TotalLoggedInUser);
		    Library_Details.put("TotalLoggedOutUser",TotalLoggedOutUser);
		    
		    int count = 0;
		    for(User_Details User : AllUser)
		    {   
		    	if(count == 6) {
		    		count = 0;
		    		break;
		    	}
		    	JSONObject user = new JSONObject();
		    	
				user.put("Name",User.getFirstName()+" "+User.getLastName());
				user.put("Date_Of_Birth",sdfrmt.format(User.getDob()));
				user.put("Phone_Number",User.getPhoneNumber());
				user.put("Gender",User.getGender().toString());
				user.put("Role", User.getRole().toString());
				ArrayList<BorrowBook_Details> TotalBookIssuedByUser = BookTransactionsServiceImpl.getInstance().getSpecificPersonsBookTransactions(User.getPhoneNumber());
				user.put("TotalBooksIssued",TotalBookIssuedByUser.size());
				DashBoard_Details.add(user);
				
			    count++;
		    }
		    
			for(Book_Details books : AllBooks)
			 {
				if(count == 6)
				{
					count = 0;
					break;
				}
				 JSONObject book = new JSONObject();
				 
				  book.put("ISBN_No",books.getISBN_No());
			      book.put("Book_Name",books.getBookName());
				  book.put("Author_Name",books.getAuthorName());
				  book.put("Pages",books.getPages());
				  book.put("Published_Date",sdfrmt.format(books.getPublishedDate()));
				  book.put("Category",books.getCategory().toString());
				  book.put("Available_Count",books.getAvailableCount());
					 
				  DashBoard_Details.add(book);
				  
				  count++;
			 }
        response.getWriter().append(DashBoard_Details.toString());  
	}

}
