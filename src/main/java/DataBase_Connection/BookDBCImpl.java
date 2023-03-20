package DataBase_Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import Model.Book_Details;
import Model.Book_Floor_Details;

public class BookDBCImpl implements BookDBC{
   
	private static BookDBCImpl instance;

	private BookDBCImpl() {

	}

	public static BookDBCImpl getInstance() {
		if (instance == null) {
			instance = new BookDBCImpl();
		}
		return instance;
	}


	@Override
	public ResultSet getAllBooks(){
		
	ResultSet result = null;
		try
		{
			String query ="select * from Book_Details order by S_No desc";
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
			result = pstmt.executeQuery();
		}catch (SQLException e) {
			e.getMessage();
		}
	  return result;
	}
	
	public ResultSet getAllBooks(int ISBN_No){
		
	ResultSet result = null;
		try
		{
			String query ="select * from Book_Details where ISBN_No = ? order by S_No desc";
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
			pstmt.setInt(1, ISBN_No);
		    result = pstmt.executeQuery();
		 
		}catch (SQLException e) {
			e.getMessage();
		}
	  return result;
	}


	@Override
	public ResultSet searchBooks(String TypeOfSearch,String category,String Book_Specifications){
	  
		ResultSet result = null;
		 try
		  {
			String query = null;
			if(TypeOfSearch.equals("ISBN_No"))
			{
				query = "select * from Book_Details where ISBN_No like ? and Category like ? order by S_No desc";
			}
			else
			{
				query = "select * from Book_Details where Book_Name like ? and Category like ? order by S_No desc";	
			}
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
			
			System.out.println(TypeOfSearch);
			System.out.println(category);
			System.out.println(Book_Specifications);

			if(TypeOfSearch.equals("ISBN_No"))
			{
			     pstmt.setInt(1,Integer.parseInt(Book_Specifications));
			}
			else
			{
			  pstmt.setString(1,Book_Specifications);
			}
			pstmt.setString(2,category.toUpperCase());
			result = pstmt.executeQuery();
		  }catch(SQLException e)
		 	{
			   e.printStackTrace();  
		    }
		 System.out.println(result);
	  return result; 
	}
	
	@Override
	public int addBooks(Book_Details Book,Book_Floor_Details Floor_Details){
	 int result = 0;
		 try
		  {
			String query = "insert into Book_Details(ISBN_No,Book_Name,Author_Name,Pages,Published_Date,Category,Available_Count) values(?, ?, ?, ?, ?, ?, ?)";
			String query1 = "insert into Book_Floor_Details (ISBN_No,Floor_No,Department,Shelve_Number) values(?, ?, ?, ?)";
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
			PreparedStatement pstmt1 = DbConnection.dbConnection.prepareStatement(query1);
			SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
			
			pstmt.setInt(1,Book.getISBN_No());
			pstmt.setString(2,Book.getBookName());
			pstmt.setString(3,Book.getAuthorName());
			pstmt.setInt(4,Book.getPages());
			try {
			pstmt.setString(5,sdfrmt.format(Book.getPublishedDate()));
			}catch(NullPointerException er)
			{
				er.getMessage();
			}
			pstmt.setString(6,Book.getCategory().toString());
			pstmt.setInt(7,Book.getAvailableCount());

			pstmt1.setInt(1,Book.getISBN_No());
			pstmt1.setString(2,Floor_Details.getFloorNo().toString());
			pstmt1.setString(3,Floor_Details.getDepartment().toString());
			pstmt1.setInt(4,Floor_Details.getShelveNo());
			
			int result1 = pstmt.executeUpdate();
			int result2 = pstmt1.executeUpdate();
			
			if(result1==1 && result2 == 1)
			{
				result = 1;
			}
		 }catch(SQLException e)
		 {
			   System.out.println(e.getMessage());  
			   
		 }catch(Exception er)
		 {
			 System.out.println(er.getMessage());
		 }
		 return result;
	}

	@Override
	public int updateBooks(Book_Details Book,Book_Floor_Details Floor_Details,int OriginalISBN_No){
		
	  int result = 0;
		 try
		  {
			String query = "update Book_Details set ISBN_No = ? ,Book_Name = ? ,Author_Name = ? ,Pages = ? ,Published_Date = ? ,Category= ?, Available_Count = ? where ISBN_No = ?";
			String query1 = "update Book_Floor_Details set ISBN_No = ?,Floor_No = ?,Department = ?,Shelve_Number = ? where ISBN_No = ?";
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
			PreparedStatement pstmt1 = DbConnection.dbConnection.prepareStatement(query1);
			SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yyyy");
			 
			System.out.println("Happy");
			pstmt.setInt(1,Book.getISBN_No());
			pstmt.setString(2,Book.getBookName());
			pstmt.setString(3,Book.getAuthorName());
			pstmt.setInt(4,Book.getPages());
			pstmt.setString(5,sdfrmt.format(Book.getPublishedDate()));
			pstmt.setString(6,Book.getCategory().toString());
			pstmt.setInt(7,Book.getAvailableCount());	
			pstmt.setInt(8,OriginalISBN_No);
			System.out.println(pstmt);
			
			pstmt1.setInt(1,Book.getISBN_No());
			pstmt1.setString(2,Floor_Details.getFloorNo().toString());
			pstmt1.setString(3,Floor_Details.getDepartment().toString());
			pstmt1.setInt(4,Floor_Details.getShelveNo());
			pstmt1.setInt(5,OriginalISBN_No);
			System.out.println(pstmt1);
			
			int result1 = pstmt.executeUpdate();
			int result2 = pstmt1.executeUpdate();
			
			if(result1==1 && result2 == 1)
			{
				result = 1;
			}
			
		  }catch(SQLException e)
		 	{
			   e.getMessage();  
		    }
		 return result;
	}

	@Override
	public int deleteBooks(int ISBN_No){
		
	 int result = 0;
		 try
		  {
			String query ="delete from Book_Details where ISBN_No = ?";
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
			result = pstmt.executeUpdate();
		  }catch(SQLException e)
		 	{
			   e.getMessage();  
		    }
		 return result;
	}
	
	public ResultSet getCountOfAllBooks()
	{
		 ResultSet result = null;
		 try
		  {
			String query ="select count(*) from Book_Details";
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
			result = pstmt.executeQuery();
		  }catch(SQLException e)
		 	{
			   e.getMessage();  
		    }
		 return result;
	}
	
	public ResultSet getFloorDetails(int ISBN_No)
	{
		 ResultSet result = null;
		 try
		  {
			String query ="select * from Book_Floor_Details where ISBN_No = ?";
			PreparedStatement pstmt = DbConnection.dbConnection.prepareStatement(query);
			pstmt.setInt(1, ISBN_No);
			result = pstmt.executeQuery();
		  }catch(SQLException e)
		 	{
			   e.getMessage();  
		    }
		 return result;
	}
}
