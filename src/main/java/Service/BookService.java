package Service;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;

import Model.Book_Details;
import Model.Book_Floor_Details;


public interface BookService {
   
	ArrayList<Book_Details> getAllBooks();
	
    ArrayList<Book_Details> searchBooks(JSONObject BookName);
	
	HashMap<String,Object> addBooks(JSONObject BookDetails);
	
    HashMap<String,Object> updateBooks(JSONObject BookDetails);
	
	String deleteBooks(int ISBN_No);
	
	Book_Details showBookDeatils(int ISBN_No);
	
	int getCountOfAllBooks();
     
	Book_Floor_Details getFloorDetails(int ISBN_No);
}

