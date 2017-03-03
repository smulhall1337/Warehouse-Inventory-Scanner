package wims_v1;
import java.sql.Connection;
import java.sql.DriverManager;

public class SQL_Handler {
	
	private String sql_driver;
	private String url;
	private String username;
	private String password;
	
	public SQL_Handler() {
		sql_driver = null;
		url = null;
		username = null;
		password = null;
	}
	/**
	 * method used to connect to the database 
	 * @return the connection of the database
	 * @return null if the connection cannot be established
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception {
		
		try {
			sql_driver = "com.mysql.jdbc.Driver";
			url = "jdbc:mysql://swenggseanmulhall.cdyrbvongw5v.us-east-1.rds.amazonaws.com:3306/swenggdb";
			username = "seansgroup";
			password = "Sssh.It'sasecret";
			Class.forName(sql_driver);
			
			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected!");
			
			return conn;
		} catch(Exception e)
		{
			System.out.println(e);
		}//TryCatch end		
		
		return null;
	}//getConnection end
	
	public void insert()
	{
		
	}//insert end
	
	public void remove()
	{
		
	}//remove end
	
	public void edit()
	{
		
	}//edit end
}
