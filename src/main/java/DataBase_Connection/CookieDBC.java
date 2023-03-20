package DataBase_Connection;

public interface CookieDBC {
   
	int addCookie(String Session_ID,String Phone_Number);
	 
	int deleteCookie(String Session_ID);
	
	String getPhoneNumber(String SessionID);
}
