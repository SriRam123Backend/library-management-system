package DataBase_Connection;

import java.sql.ResultSet;

import Model.Book_Details;
import Model.Book_Floor_Details;

public interface BookDBC {
	
	ResultSet getAllBooks();
	
	ResultSet getAllBooks(int ISBN_No);
	
	ResultSet searchBooks(String TypeOfSearch,String category,String BookSpecifications);
	
	int addBooks(Book_Details Book,Book_Floor_Details Floor_Details);
	
	int updateBooks(Book_Details Book,Book_Floor_Details Floor_Details,int OriginalISBN_No);
	
	int deleteBooks(int ISBN_No);
	
	ResultSet getCountOfAllBooks();
	
	ResultSet getFloorDetails(int ISBN_No);
	
}
