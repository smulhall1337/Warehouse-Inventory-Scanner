package wims_v1;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.HashMap;

public class SQL_Handler {
	
	/**
	 * A collection of prepared SQL statements
	 */
	private static HashMap<String, PreparedStatement> sql_statements = populatePreparedStatements();
	/**
	 * Holds a prepared SQL statement
	 */
	private static PreparedStatement stmt;
	/**
	 * Holds results sets from SQL queries
	 */
	private static ResultSet rs;
	
	//This must be moved to a file or private location and loaded in for security
	protected static String salt = "Random$SaltValue#With^ManySpecials(@*&Q$^T(^&#%$";
	
	/**
	 * method used to connect to the database 
	 * @return the connection to the database, null if connection cannot be established
	 */
	public static Connection getConnection() {
		final String SQL_DRIVER = "com.mysql.jdbc.Driver";
		final String URL = "jdbc:mysql://swenggseanmulhall.cdyrbvongw5v.us-east-1.rds.amazonaws.com:3306/swenggdb";
		final String DB_USER = "seansgroup";
		final String DB_PW = "Sssh.It'sasecret";
		
		try {
			Class.forName(SQL_DRIVER);
			Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PW);
			
			//Must remove for final version, left here for testing connection to DB. (No I/O from anything but driver)
			//System.out.println("Connected!"); 
			return conn;
			
		} catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}		
		catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		return null;
	}//getConnection end
	
	/**
	 * Create an MD5 hash value from salted string input
	 * @param input the salted input string
	 * @return returns an MD5 hashed string
	 */
	public static String md5_hash (String input) {
		String md5 = null;
		
		if (input != null) {
			try {
				//Create MessageDigest object for MD5
				MessageDigest digest = MessageDigest.getInstance("md5");
				//Update input string in message digest
				digest.update(input.getBytes(), 0, input.length());
				//Convert message digest value into base 16 (hexadecimal)
				md5 = new BigInteger(1, digest.digest()).toString(16);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return md5;
	}
	
	/**
	 * Check whether the entered employee_id/password combination is valid
	 * @param employee_id the in-bound employee_id
	 * @param pw the inputed password
	 * @return returns true if the employee_id/password combination is valid, false otherwise
	 */
	public static boolean isValidUsernamePassword(String employee_id, String pw) {
		String sql_salt = "";
		rs = getEmpRowByID(employee_id);
		try {
			if (rs.next()) {			// Returns true if the current row is not past the last row
				sql_salt = rs.getString("salt");
				if (sql_salt.equals(md5_hash(pw+salt))) {
					// employee_id found with matching salted pw
					return true;
				}
				else {
					// Invalid employee_id/password combination
					return false;
				}
			}
			else {
				//employee id not found
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Get a row from the employees table in the DB by employee_id(PK)
	 * @param employee_id the in-bound employee_id to match
	 * @return returns the result set of the executed query
	 */
	public static ResultSet getEmpRowByID(String employee_id) {
		// The prepared statement to be executed
		stmt = sql_statements.get("EmpByID");
		try {
			//Set the statements wildcards. Starts @ index 1 and increments by 1
			stmt.setString(1, employee_id);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	/**
	 * Get a salt value from the employees table in the DB by employee_id(PK)
	 * @param employee_id the in-bound employee_id to match
	 * @return returns the result set of the executed query
	 */
	public static ResultSet getEmpSalt(String employee_id) {
		// The prepared statement to be executed
		stmt = sql_statements.get("EmpSalt");
		try {
			//Set statement wildcards. Starts @ index 1 and increments by 1
			stmt.setString(1, employee_id);
			rs = stmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public static void insertNewEmployee(String employee_id, String name, boolean isManagement, 
										 String salt, String warehouse_id) 
	{
		stmt = sql_statements.get("NewEmp");
		try {
			stmt.setString(1, employee_id);
			stmt.setString(2, name);
			stmt.setBoolean(3, isManagement);
			stmt.setString(4, salt);
			stmt.setString(5, warehouse_id);
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Populate a hash map of prepared SQL statements
	 * @return returns a collection of key - prepared SQL statement pairs
	 */
	private static HashMap<String, PreparedStatement> populatePreparedStatements() {
		String stmt_key = "";
		PreparedStatement statement;
		HashMap<String, PreparedStatement> statements = new HashMap<String, PreparedStatement>();
		Connection conn = getConnection();
		
		try {			
			//Key for storage in HashMap
			stmt_key = "EmpByID";
			
			//Prepared SQL statement with wildcard (?)
			statement = conn.prepareStatement("SELECT * FROM employees " +
					  						  "WHERE employee_id = ?");
			//Add the prepared statement to the HashMap
			statements.put(stmt_key, statement);
			
			stmt_key = "EmpSalt";
			statement = conn.prepareStatement("SELECT salt FROM employees " +
											  "WHERE employee_id = ?");
			statements.put(stmt_key, statement);
			
			stmt_key = "NewEmp";
			statement = conn.prepareStatement("INSERT INTO employees (employee_id, name, is_management, salt, warehouse_id)" + 
											  "VALUES (?,?,?,?,?)");
			statements.put(stmt_key, statement);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return statements;
	}
	
	/**
	 * Standard Accessor - for the current result set
	 * @return returns the current result set
	 */
	public ResultSet getResultSet() {
		return rs;
	}
	
	/**
	 * Standard Accessor - for the collection of prepared SQL statements 
	 * @return returns the collection of prepared SQL statements
	 */
	public static HashMap<String, PreparedStatement> getSQLStatements() {
		return sql_statements;
	}
}
