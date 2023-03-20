package Controller;

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
        DbConnection.getDBConnection();
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
