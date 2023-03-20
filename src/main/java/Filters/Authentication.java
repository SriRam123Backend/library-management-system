
package Filters;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import DataBase_Connection.CookieDBCImpl;

/**
 * Servlet Filter implementation class Authentication
 */
@WebFilter("/filter/*")
public class Authentication extends HttpFilter implements Filter {
       
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
         System.out.println("First Filter");
         
         HttpServletRequest req = (HttpServletRequest) request;
         Cookie[] cookie = req.getCookies();
         if(cookie == null)
         {
			JSONObject json = new JSONObject();
			json.put("Message", "Cookie is not Available");
			response.getWriter().append(json.toString());
         }
         else
         {
         for(int i=0;i<cookie.length;i++)
         {
        	 if(cookie[i].getName().equals("Session_ID"))
        	 {
 				try {
 					
					String Phone_Number = CookieDBCImpl.getInstance().getPhoneNumber(cookie[i].getValue());
					if(Phone_Number != null) {
						request.setAttribute("Phone_Number",Phone_Number);
						chain.doFilter(request, response);
					}
					else {
						JSONObject json = new JSONObject();
						json.put("Message", "Cookie is not Available");
						response.getWriter().append(json.toString());
					}
				} 
				catch (SQLException e) {
					e.getMessage();
				}
        	 }
         }
      }
   }
@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
