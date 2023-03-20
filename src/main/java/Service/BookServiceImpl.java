package Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;

import DataBase_Connection.BookDBC;
import DataBase_Connection.BookDBCImpl;
import Enums.Categories;
import Enums.FloorNumber;
import Model.Book_Details;
import Model.Book_Floor_Details;

public class BookServiceImpl implements BookService {
  
	private static BookServiceImpl Book = null;
	
	private BookServiceImpl() {};
	
	public static BookServiceImpl getInstance() {
		if(Book==null) {
			Book = new BookServiceImpl();
		}
		return Book;
	}	
	
	public ArrayList<Book_Details> getAllBooks()
	{
		ArrayList<Book_Details> AllBooks = new ArrayList<Book_Details>();
		ResultSet rs = BookDBCImpl.getInstance().getAllBooks();
		try
		{
			while(rs.next())
			{
				Book_Details Book = new Book_Details();
				Book.setISBN_No(rs.getInt(2));
				Book.setBookName(rs.getString(3));
				Book.setAuthorName(rs.getString(4));
				Book.setPages(rs.getInt(5));
				try {
					Book.setPublishedDate(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString(6)));
				} catch (ParseException e) {
					e.getMessage();
				}
				Book.setCategory(Categories.valueOf(rs.getString(7).replaceAll("\\s", "").toUpperCase()));
				Book.setAvailableCount(rs.getInt(8));
				AllBooks.add(Book);
			}
		}catch(SQLException er)
		{
			er.getMessage();
		}
		return AllBooks;
	}
	
	public ArrayList<Book_Details> searchBooks(JSONObject BookSpecifications)
	{
		ArrayList<Book_Details> AllBooks = new ArrayList<Book_Details>();
		String Book_Name = "%"+(String) BookSpecifications.get("bookName");
		String Category = (String)  BookSpecifications.get("category");
		String ISBN_No = (String)  BookSpecifications.get("NoOfTheBook");
		System.out.println(BookSpecifications);
		ResultSet rs = null;
		try
		{
		    if(!ISBN_No.equals(""))
			{
		    	System.out.println("a");
				rs = BookDBCImpl.getInstance().searchBooks("ISBN_No","%",ISBN_No);
			}
		    else if(Book_Name.equals("") && Category.equals("None") && ISBN_No.equals(""))
			{
		    	System.out.println("b");
		    	rs = BookDBCImpl.getInstance().getAllBooks();	
			}
			else if(!Book_Name.equals("") && Category.equals("None") && ISBN_No.equals(""))
			{
				System.out.println("c");
				rs = BookDBCImpl.getInstance().searchBooks("Book_Name","%",Book_Name+"%");
			}
			else if(!Book_Name.equals("") && !Category.equals("None") && ISBN_No.equals(""))
			{   
				System.out.println("d");
				rs = BookDBCImpl.getInstance().searchBooks("Book_Name",Category,Book_Name+"%");
			}
			else if(Book_Name.equals("") && !Category.equals("None") && ISBN_No.equals(""))
			{
				System.out.println("e");
				rs = BookDBCImpl.getInstance().searchBooks("Book_Name",Category,"%");
			}
		    
			while(rs.next())
			{			
		
			  Book_Details Book = new Book_Details();
		      Book.setISBN_No(rs.getInt(2));
			  Book.setBookName(rs.getString(3));
		      Book.setAuthorName(rs.getString(4));
		      Book.setPages(rs.getInt(5)); 
			  try { Book.setPublishedDate(new
			     SimpleDateFormat("dd/MM/yyyy").parse(rs.getString(6))); 
			  }catch(ParseException e) 
	           {
			      e.getMessage(); 
			  }
		      Book.setCategory(Categories.valueOf(rs.getString(7).replaceAll("\\s", "").toUpperCase()));
			  Book.setAvailableCount(rs.getInt(8)); 
			  AllBooks.add(Book);				 
			}
			
		}catch(SQLException er)
		{
			er.getMessage();
		}catch(Exception er)
		{
			er.getMessage();
		}
		
		System.out.println(AllBooks.size());
		return AllBooks;
	}
	
	public HashMap<String,Object> addBooks(JSONObject BookDetails)
	{
		HashMap<String,Object> Details = new HashMap<String,Object>();
		String ISBN_No = (String) BookDetails.get("isbnNo");
		String Book_Name = (String) BookDetails.get("bookName");
		String Author_Name = (String) BookDetails.get("authorName");
		String Page = (String) BookDetails.get("pages");
		String Published_Date = (String) BookDetails.get("publishedDate");  
		String Category = (String) BookDetails.get("category");
		String AvailableCount = (String) BookDetails.get("availableStock");
		String Floor_No = (String) BookDetails.get("floorNo");  
		String Department = (String) BookDetails.get("department");
		String Shelve_Number = (String) BookDetails.get("shelveNo");
	    
		System.out.println(Floor_No);
		System.out.println(Department);
		System.out.println(Shelve_Number);
		
		Book_Details NewBook = new Book_Details();
		Book_Floor_Details Floor_Details = new Book_Floor_Details();
		
		try
		{
		NewBook.setISBN_No(Integer.parseInt(ISBN_No));
		NewBook.setBookName(Book_Name);
		NewBook.setAuthorName(Author_Name);
		NewBook.setPages(Integer.parseInt(Page));
		NewBook.setCategory(Categories.valueOf(Category.replaceAll("\\s", "").toUpperCase()));
		NewBook.setAvailableCount(Integer.parseInt(AvailableCount));
		
		Floor_Details.setDepartment(Categories.valueOf(Department.replaceAll("\\s", "").toUpperCase()));
		Floor_Details.setFloorNo(FloorNumber.valueOf(Floor_No.replaceAll("\\s", "").toUpperCase()));
		Floor_Details.setShelveNo(Integer.parseInt(Shelve_Number));
		
		NewBook.setPublishedDate(new SimpleDateFormat("yyyy-MM-dd").parse(Published_Date));
		}
		catch (ParseException e) {
			e.getMessage();
		}catch(Exception er)
		{
			er.getMessage();
		}

		BookDBC dao = BookDBCImpl.getInstance();
		int result = 0;
		result = dao.addBooks(NewBook,Floor_Details);
		System.out.println(result);
		if(result != 0)
		{			
			Details.put("NewBook",NewBook);
			Details.put("FloorDetails",Floor_Details);
			return Details;
		}
		return null;
	}
	
	public HashMap<String,Object> updateBooks(JSONObject BookDetails)
	{
		
		HashMap<String,Object> Details = new HashMap<String,Object>();
		String ISBN_No = (String) BookDetails.get("isbnNo");
		String Book_Name = (String) BookDetails.get("bookName");
		String Author_Name = (String) BookDetails.get("authorName");
		String Page = (String) BookDetails.get("pages");
		String Published_Date = (String) BookDetails.get("publishedDate"); 
		String Category = (String) BookDetails.get("category");
		String AvailableCount = (String) BookDetails.get("availableStock");
		String Original_ISBNNo = (String) BookDetails.get("originalIsbnNo");
		String Floor_No = (String) BookDetails.get("floorNo");  
		String Department = (String) BookDetails.get("department");
		String Shelve_Number = (String) BookDetails.get("shelveNo");
		  
		Book_Details UpdatedBook = new Book_Details();
		Book_Floor_Details Floor_Details = new Book_Floor_Details();

		try
		{
			UpdatedBook.setISBN_No(Integer.parseInt(ISBN_No));
			UpdatedBook.setBookName(Book_Name);
			UpdatedBook.setAuthorName(Author_Name);
			UpdatedBook.setPages(Integer.parseInt(Page));
			UpdatedBook.setCategory(Categories.valueOf(Category.replaceAll("\\s", "").toUpperCase()));
			UpdatedBook.setAvailableCount(Integer.parseInt(AvailableCount));
		
			Floor_Details.setDepartment(Categories.valueOf(Department.replaceAll("\\s", "").toUpperCase()));
			Floor_Details.setFloorNo(FloorNumber.valueOf(Floor_No.replaceAll("\\s", "").toUpperCase()));
			Floor_Details.setShelveNo(Integer.parseInt(Shelve_Number));
		
	    	UpdatedBook.setPublishedDate(new SimpleDateFormat("yyyy-MM-dd").parse(Published_Date));
		}
		catch (ParseException e) {
			e.getMessage();
		}catch(Exception er)
		{
			er.getMessage();
		}

		BookDBC dao = BookDBCImpl.getInstance();
		int result = 0;
		result = dao.updateBooks(UpdatedBook,Floor_Details,Integer.parseInt(Original_ISBNNo));
		if(result != 0)
		{
			Details.put("UpdatedBook",UpdatedBook);
			Details.put("FloorDetails",Floor_Details);
			return Details;
		}
		return null;
	}
	
	public String deleteBooks(int ISBN_No)
	{
		BookDBCImpl.getInstance().deleteBooks(ISBN_No);
		return "Successfully Removed";
	}
	
	public Book_Details showBookDeatils(int ISBN_No)
	{
		BookDBC dao = BookDBCImpl.getInstance();
		
		Book_Details book = null;
		
		try
		{
			ResultSet rs = dao.getAllBooks(ISBN_No);
			
			if(rs.next())
			{
				  book = new Book_Details();
			      book.setISBN_No(rs.getInt(2));
				  book.setBookName(rs.getString(3));
			      book.setAuthorName(rs.getString(4));
			      book.setPages(rs.getInt(5)); 
				  try { 
					book.setPublishedDate(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString(6))); 
				  }catch(ParseException e) 
		           {
				      e.getMessage(); 
				  }
			      book.setCategory(Categories.valueOf(rs.getString(7).replaceAll("\\s", "").toUpperCase()));
				  book.setAvailableCount(rs.getInt(8)); 			
			}
			
		}catch(SQLException er) {
			er.getMessage();
		}
		
		return book;
	}
	
	public int getCountOfAllBooks()
	{
		BookDBC dao = BookDBCImpl.getInstance();
		
		int TotalBooks = 0;
		
		try {
			
			ResultSet rs = dao.getCountOfAllBooks();
			
			if(rs.next())
			{
				TotalBooks = rs.getInt(1);
			}
			
		}catch(SQLException er)
		{
			er.getMessage();
		}
		
		return TotalBooks;
	}

	public Book_Floor_Details getFloorDetails(int ISBN_No)
	{
		BookDBC dao = BookDBCImpl.getInstance();
		
		Book_Floor_Details book = null;
		
		try
		{
			ResultSet rs = dao.getFloorDetails(ISBN_No);
			
			if(rs.next())
			{
			   book = new Book_Floor_Details();
			   
			   book.setFloorNo(FloorNumber.valueOf(rs.getString(2).toUpperCase()));
		       book.setDepartment(Categories.valueOf(rs.getString(3).replaceAll("\\s", "").toUpperCase()));
		       book.setShelveNo(Integer.parseInt(rs.getString(4)));
			}
			
		}catch(SQLException er) {
			er.getMessage();
		}
		
		return book;
	}
}
