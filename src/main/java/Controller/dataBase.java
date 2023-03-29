
package Controller;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import DataBase_Connection.DbConnection;

/**
 * Servlet implementation class dataBase
 */
@WebServlet(name="database",value="/dataBase",loadOnStartup=0)
public class dataBase extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	public void init()
	{
		System.out.println("Data Base");
		ServletContext context =  getServletContext();
		
		String databaseName = context.getInitParameter("database_name");
		String databaseUserName = context.getInitParameter("database_username");
		String databasePassword = context.getInitParameter("database_password");
		
		System.out.println(databaseName);
		System.out.println(databaseUserName);
		System.out.println(databasePassword);
		
		try
		{
			DbConnection.getDBConnection(databaseName, databaseUserName, databasePassword);
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
        if(DbConnection.getDbConnection() == null) {
            System.out.println("FATAL: Please check Data Base Connection Issue");
            System.exit(0);
        }
	}
    public dataBase() {
        super();
        // TODO Auto-generated constructor stub
    }
}
