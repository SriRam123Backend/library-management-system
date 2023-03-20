package Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import DataBase_Connection.BookTransactionsDBC;
import DataBase_Connection.BookTransactionsDBCImpl;
import Enums.User_Status;
import Model.BorrowBook_Details;

public class BookTransactionsServiceImpl implements BookTransactionsService{

	 private static BookTransactionsServiceImpl instance;
	 
	 private BookTransactionsServiceImpl() {
		 
	 }
	 
	 public static BookTransactionsServiceImpl getInstance()
	 {
		 if(instance == null)
		 {
			  instance = new BookTransactionsServiceImpl();
		 }
		 return instance;
	 }
	 
	 public ArrayList<BorrowBook_Details> getAllBookTransactions(String Book_Name){
		 
		 BookTransactionsDBC dao = BookTransactionsDBCImpl.getInstance();
		 ArrayList<BorrowBook_Details> BookTransactions = new ArrayList<BorrowBook_Details>();
		 try
		 {
			ResultSet rs = dao.getAllBookTransactions(Book_Name.trim());
				while(rs.next())
				{
					BorrowBook_Details Book = new BorrowBook_Details();
					
					Book.setISBN_No(rs.getInt(2));
					Book.setPhone_Number(rs.getString(3));
					try {
						Book.setDate_Borrowed(new SimpleDateFormat("dd/mm/yyyy").parse(rs.getString(5)));
					} catch (ParseException e) {
						e.getMessage();
					}
					try {
						Book.setReturn_Date(new SimpleDateFormat("dd/mm/yyyy").parse(rs.getString(6)));
					} catch (ParseException e) {
						e.getMessage();
					}
					Book.setFine_Amount(rs.getInt(7));
					 if(rs.getString(8) != null) { 
						 try { 
							Book.setFined_Date(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString(8)));
						 } 
						 catch (ParseException e) { e.getMessage(); 
					  } 
					}
					Book.setUStatus(User_Status.valueOf(rs.getString(9).replaceAll("\\s", "").toUpperCase()));
					BookTransactions.add(Book);
					
				}
		 }catch(SQLException er)
		 {
			 er.getMessage();
		 }
        return BookTransactions;
	 }
	 
	 public ArrayList<BorrowBook_Details> getSpecificPersonsBookTransactions(String Phone_Number)
	 {
		 BookTransactionsDBC dao = BookTransactionsDBCImpl.getInstance();
		 ArrayList<BorrowBook_Details> PersonsBookTransactions = new ArrayList<BorrowBook_Details>();
		 
		 try
		 {
			ResultSet rs = dao.getSpecificPersonsBookTransactions(Phone_Number);
				while(rs.next())
				{
					BorrowBook_Details Book = new BorrowBook_Details();
					
					Book.setISBN_No(rs.getInt(2));
					Book.setPhone_Number(rs.getString(3));
					try {
						Book.setDate_Borrowed(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString(5)));
					} catch (ParseException e) {
						e.getMessage();
					}
					try {
						Book.setReturn_Date(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString(6)));
					} catch (ParseException e) {
						e.getMessage();
					}					
					if(rs.getString(8) != null) { 
						 try { 
							Book.setFined_Date(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString(8)));
						 } 
						 catch (ParseException e) { e.getMessage(); 
					  } 
					}
					 
					LocalDateTime ldt = LocalDateTime.now();
					Book.setFine_Amount(rs.getInt(7));
					Book.setUStatus(User_Status.valueOf(rs.getString(9).replaceAll("\\s", "").toUpperCase()));
					if(Book.getUStatus().equals(User_Status.valueOf("NOTRETURNED")))
					{
						long todayMillis = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant()).getTime();
						long returnDateMillis = Book.getReturn_Date().getTime();
		    		 	long timeDiff = (todayMillis - returnDateMillis);
		    		 	long numberOfDays = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
		    		 	System.out.println(rs.getInt(2)+" "+numberOfDays);
						if(todayMillis > returnDateMillis && numberOfDays > 0)
						{
							Book.setFined(true);
							Book.setFine_Amount((int)numberOfDays*10);
						}
					}
					PersonsBookTransactions.add(Book);
				}
		 }catch(SQLException er)
		 {
			 er.getMessage();
		 }
        return PersonsBookTransactions;
	 }
	 
	 public int getCountOfPerUserIssuedBooks(String Phone_Number)
	 {
		 BookTransactionsDBC dao = BookTransactionsDBCImpl.getInstance();
		 int TotalBorrowedBooks = 0;
		 
		 try
		 {
			 ResultSet rs = dao.getCountOfPerUserIssuedBooks(Phone_Number);
			 if(rs.next())
			 {
				 TotalBorrowedBooks = rs.getInt(1);
			 }
		 }catch(SQLException er)
		 {
			 er.getMessage();
		 }
		 
		 return TotalBorrowedBooks;
	 }
	 
	 public Boolean CurrentUserBook (int ISBN_No,String Phone_Number)
	 {
		 BookTransactionsDBC dao = BookTransactionsDBCImpl.getInstance();
		 
		 try
		 {
			 ResultSet rs = dao.isCurrentUserBook(ISBN_No,Phone_Number);
			 if(rs.next())
			 {
				return true;
			 }
		 }catch(SQLException er)
		 {
			 er.getMessage();
		 }
		 
		 return false; 
	 }
}
