package Model;

import java.util.Date;

import Enums.User_Status;

public class BorrowBook_Details {
    
	String Phone_Number;
	int ISBN_No;
	Date Date_Borrowed;
	Date Return_Date;
	boolean isFined;
	int Fine_Amount;
	Date Fined_Date;
	User_Status UStatus;


	public User_Status getUStatus() {
		return UStatus;
	}
	public void setUStatus(User_Status uStatus) {
		UStatus = uStatus;
	}
	public String getPhone_Number() {
		return Phone_Number;
	}
	public void setPhone_Number(String phone_Number) {
		Phone_Number = phone_Number;
	}
	public int getISBN_No() {
		return ISBN_No;
	}
	public void setISBN_No(int iSBN_No) {
		ISBN_No = iSBN_No;
	}
	public Date getDate_Borrowed() {
		return Date_Borrowed;
	}
	public void setDate_Borrowed(Date date_Borrowed) {
		Date_Borrowed = date_Borrowed;
	}
	public Date getReturn_Date() {
		return Return_Date;
	}
	public void setReturn_Date(Date return_Date) {
		Return_Date = return_Date;
	}
	public boolean isFined() {
		return isFined;
	}
	public void setFined(boolean isFined) {
		this.isFined = isFined;
	}
	
	public boolean getFined()
	{
		return isFined;
	}
	
	public int getFine_Amount() {
		return Fine_Amount;
	}
	public void setFine_Amount(int fine_Amount) {
		Fine_Amount = fine_Amount;
	}
	public Date getFined_Date() {
		return Fined_Date;
	}
	public void setFined_Date(Date fined_Date) {
		Fined_Date = fined_Date;
	}
	

}
