import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {

	public static void main(String[] args) throws Exception {
		
		getConnection();
	}

	
	/**
	 * method used to connect to the database 
	 * @return the connection of the database
	 * @return null if the connection cannot be established
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception {
		
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://swenggseanmulhall.cdyrbvongw5v.us-east-1.rds.amazonaws.com:3306/swenggdb";
			String username = "seansgroup";
			String password = "Sssh.It'sasecret";
			Class.forName(driver);
			
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
	
	
}//class end
