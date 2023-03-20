package Service;

import java.util.ArrayList;

import Model.BorrowBook_Details;

public interface BookTransactionsService {
   
	ArrayList<BorrowBook_Details> getAllBookTransactions(String Book_Name);
	
	ArrayList<BorrowBook_Details> getSpecificPersonsBookTransactions(String Phone_Number);
	
	int getCountOfPerUserIssuedBooks(String Phone_Number);
	
	Boolean CurrentUserBook (int ISBN_No,String Phone_Number);
	
}
