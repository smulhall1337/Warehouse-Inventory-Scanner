
package controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
						
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to handle common SQL operations for the WIMS application
 * 
 * @author Jon Spratt
 * @version WIMS_v1
 */
public abstract class SQL_Handler {

	/**
	 * A collection of prepared SQL statements
	 */
	private static Map<String, PreparedStatement> sql_statements = populatePreparedStatements();
	/**
	 * Holds a prepared SQL statement
	 */
	private static PreparedStatement stmt;
	private static Connection customConnection = getConnection(); //TODO THIS IS BAD AND IS A TIME-CRUNCH FIX
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
		final String SQL_DRIVER = "com.mysql.cj.jdbc.Driver";
		final String URL = "jdbc:mysql://swenggseanmulhall.cdyrbvongw5v.us-east-1.rds.amazonaws.com:3306/swenggdb";
		final String DB_USER = "seansgroup";
		final String DB_PW = "Sssh.It'sasecret";
		
		try {
			Class.forName(SQL_DRIVER);
			Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PW); 
   
																											 
									   
			return conn;
			
		} catch(SQLException e)
		{
			e.printStackTrace();
		}		
		catch (ClassNotFoundException e) {
			e.printStackTrace();
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
	 * Populate a hash map of prepared SQL statements
	 * @return returns a collection of key - prepared SQL statement pairs
	 */
	private static Map<String, PreparedStatement> populatePreparedStatements() {
		String stmt_key = "";
		PreparedStatement statement;
		HashMap<String, PreparedStatement> statements = new HashMap<String, PreparedStatement>();
		Connection conn = getConnection();
		
		try {			
			//#############################################All Entities
			
													   
			//#############################################Employees
			//Key for storage in HashMap
			stmt_key = "EmpByID";
			//Prepared SQL statement with wildcard (?)
			statement = conn.prepareStatement("SELECT * FROM employees WHERE employee_id = ?");
			//Add the prepared statement to the HashMap
			statements.put(stmt_key, statement);
			
			stmt_key = "EmpSalt";
			statement = conn.prepareStatement("SELECT salt FROM employees WHERE employee_id = ?");
			statements.put(stmt_key, statement);
			
			stmt_key = "NewEmp";
			statement = conn.prepareStatement("INSERT INTO employees (employee_id, name, is_management, salt, warehouse_id) " + 
											  "VALUES (?,?,?,?,?)");
			statements.put(stmt_key, statement);
			
			stmt_key = "UpdateEmpPW";
			statement = conn.prepareStatement("UPDATE employees SET salt = ? WHERE employee_id = ?");
			statements.put(stmt_key, statement);
			
			stmt_key = "AllEmp";
			statement = conn.prepareStatement("SELECT * FROM employees");
			statements.put(stmt_key, statement);
			
			stmt_key = "AllEmpWithModifier";
			statement = conn.prepareStatement("SELECT * FROM employees");
			statements.put(stmt_key, statement);
			
			stmt_key = "UpdateEmp";
			statement = conn.prepareStatement("UPDATE employes SET name = ?,"
					+ " is_management = ?, warehouse_id = ? WHERE employee_id = ?");
			statements.put(stmt_key, statement);			  
			//#############################################Items
			stmt_key = "InDB";
			statement = conn.prepareStatement("SELECT * from items WHERE item_number = ?");
			statements.put(stmt_key, statement);
			
			stmt_key = "NewItem";
			statement = conn.prepareStatement("INSERT INTO items (item_number, name, price, weight, current_stock, restock_threshold) " +
											  "VALUES (?,?,?,?,?,?)");
			statements.put(stmt_key, statement);
			
			stmt_key = "FullUpdate";
			statement = conn.prepareStatement("UPDATE swenggdb.items SET name = ?, price = ?, weight = ?, current_stock = ?, restock_threshold = ? WHERE item_number = ?");
			statements.put(stmt_key, statement); 
			
			stmt_key = "ItemType";
			statement = conn.prepareStatement("INSERT INTO swenggdb.items_item_category (item_number, type) VALUES (?, ?)");
			statements.put(stmt_key, statement); 
			
			stmt_key = "GetItemTypes";
			statement = conn.prepareStatement("SELECT * FROM swenggdb.items_item_category WHERE item_number = ?");
			statements.put(stmt_key, statement);
			
			stmt_key = "UpdateItemQty";
			statement = conn.prepareStatement("UPDATE items SET current_stock = ? WHERE item_number = ?");
			statements.put(stmt_key, statement);				
			
			stmt_key = "ItemInfo";
			statement = conn.prepareStatement("SELECT * FROM swenggdb.items WHERE item_number = ?");
			statements.put(stmt_key, statement);
			
			stmt_key = "ItemStock";
			statement = conn.prepareStatement("SELECT current_stock FROM items WHERE item_number = ?");
			statements.put(stmt_key, statement);
			
			stmt_key = "All Items";
			statement = conn.prepareStatement("SELECT * FROM items");
			statements.put(stmt_key, statement);
			
			//#############################################Pallets
			stmt_key = "PalletInDB";
			statement = conn.prepareStatement("SELECT * FROM pallets WHERE pallet_id = ?");
			statements.put(stmt_key, statement);
			
			stmt_key = "NewPallet";
			statement = conn.prepareStatement("INSERT INTO pallets (pallet_id, piece_cound, weight, length, width, height, receival_date, ship_date, notes, order_number, Location_coordinate" + 
												"VALUES (?,?,?,?,?,?,?,?,?,?,?)");
			statements.put(stmt_key, statement);
			
			stmt_key = "GetItemsOnPallet";
			statement = conn.prepareStatement("SELECT * FROM swenggdb.pallets_items WHERE pallet_id = ?");
			statements.put(stmt_key, statement);
			
			stmt_key = "GetItemCountOnPallet";
			statement = conn.prepareStatement("SELECT * FROM swenggdb.pallets_items WHERE pallet_id = ? AND item_number = ?");
			statements.put(stmt_key, statement); 
			
			stmt_key = "UpdatePieceCount";
			statement = conn.prepareStatement("UPDATE swenggdb.pallets SET piece_count = ? WHERE pallet_id = ?");
			statements.put(stmt_key, statement);
			
			stmt_key = "UpdateItemOnPallet";
			statement = conn.prepareStatement("UPDATE swenggdb.pallets_items SET item_quantity = ? WHERE pallet_id = ? AND item_number = ?");
			statements.put(stmt_key, statement);
			
			stmt_key = "AddItemToPallet";
			statement = conn.prepareStatement("INSERT INTO swenggdb.pallets_items (pallet_id, item_number, item_quantity) " +
												"VALUES (?, ?, ?)");
			statements.put(stmt_key, statement);
			
			stmt_key = "GetPalletLocation";
			statement = conn.prepareStatement("SELECT pallet_location FROM swenggdb.pallets WHERE pallet_id = ?");
			statements.put(stmt_key, statement);
			//#############################################Orders
			stmt_key = "OrderInDB";
			statement = conn.prepareStatement("SELECT * FROM orders WHERE order_number = ?");
			statements.put(stmt_key, statement);
			
			stmt_key = "NewOrder";
			statement = conn.prepareStatement("INSERT INTO swenggdb.orders (order_number, origin, destination, received_by_emp_id, shipped_by_emp_id, date_placed, date_shipped, date_delivered) " +
												"VALUES(?, ?,?, ?, ?, ?, ?, ?)");
			statements.put(stmt_key, statement);
			//#############################################Warehouses
			stmt_key = "GetWHNamesIDs";
			statement = conn.prepareStatement("SELECT warehouse_id, name FROM warehouses");
			statements.put(stmt_key, statement);
			
			stmt_key = "GetWHNames";
			statement = conn.prepareStatement("SELECT name FROM warehouses");
			statements.put(stmt_key, statement);
			
			stmt_key = "GetWHIDs";
			statement = conn.prepareStatement("SELECT warehouse_id FROM warehouses");
			statements.put(stmt_key, statement);
			
			stmt_key = "GetWHCities";
			statement = conn.prepareStatement("SELECT city FROM warehouses");
			statements.put(stmt_key, statement);				  
			//#############################################Sublocations		
			stmt_key = "GetAvailableSubLocations";
			statement = conn.prepareStatement("SELECT * FROM swenggdb.sublocation WHERE max_pallet_qty <> current_pallet_qty");
			statements.put(stmt_key, statement);
			
			stmt_key = "GetSubLocationName";
			statement = conn.prepareStatement("SELECT * FROM swenggdb.sublocation where simple_sublo_index = ?");
			statements.put(stmt_key, statement);
			
			stmt_key = "GetSubLocationInfo";
			statement = conn.prepareStatement("SELECT * FROM swenggdb.sublocation where location_coordinate = ?");
			statements.put(stmt_key, statement);
			
			stmt_key = "ChangeSubLocationCurrentQty";
			statement = conn.prepareStatement("UPDATE swenggdb.sublocation SET current_pallet_qty = ? WHERE location_coordinate = ?");
			statements.put(stmt_key, statement);
   
   
		} catch (SQLException e) {
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
	public static Map<String, PreparedStatement> getSQLStatements() {
		return sql_statements;
	}
	
	//#############################################All Entities
	public static ResultSet getAllFromTable(String tableName) throws SQLException{
		String query = "SELECT * FROM " + tableName;
			stmt = customConnection.prepareStatement(query);
			rs = stmt.executeQuery();
			return rs;
	}
	
	public static ResultSet getAllFromTable(String tableName, String fieldName, 
			String fieldModifier, String fieldValue) throws SQLException{
		String query = "SELECT * FROM " + tableName + " WHERE " 
			+ fieldName + getQueryModifierString(fieldModifier, sanitizeInput(fieldValue));
		stmt = customConnection.prepareStatement(query);
		rs = stmt.executeQuery();
		return rs;
	}
	
	//#############################################Employees
	/**
	 * Check whether the entered employee_id/password combination is valid
	 * @param employee_id the in-bound unique employee_id
	 * @param pw the inputed password
	 * @return returns true if the employee_id/password combination is valid, false otherwise
	 */
	public static boolean isValidUsernamePassword(String employee_id, char[] pw) throws SQLException {
		String sql_salt = "";
		String password = String.valueOf(pw);
		rs = getEmpRowByID(employee_id);
		if (rs.next()) {			// Returns true if the current row is not past the last row
			sql_salt = rs.getString("salt");
			if (sql_salt.equals(md5_hash(password+salt))) {
				// employee_id found with matching salted pw
				return true;
			}
		}
		// Employee ID not found or Invalid username/password combo
		return false;
	}
	
	/**
	 * Check whether the entered employee_id/password combination is valid
	 * 
	 * @param employee_id
	 *            the in-bound unique employee_id
	 * @param pw
	 *            the inputed password
	 * @return returns true if the employee_id/password combination is valid,
	 *         false otherwise
	 */
	public static boolean isValidUsernamePassword(String employee_id, String pw) throws SQLException {
		String sql_salt = "";
		rs = getEmpRowByID(employee_id);
		if (rs.next()) { // Returns true if the current row is not past the last
							// row
			sql_salt = rs.getString("salt");
			if (sql_salt.equals(md5_hash(pw + salt))) {
				// employee_id found with matching salted pw
				return true;
			}
		}
		// Employee ID not found or Invalid username/password combo
		return false;
	}
	
	/**
	 * Get a row from the employees table in the DB by employee_id(PK)
	 * @param employee_id the in-bound employee_id to match
	 * @return returns the result set of the executed query
	 */
	public static ResultSet getEmpRowByID(String employee_id) throws SQLException {
		// The prepared statement to be executed
		stmt = sql_statements.get("EmpByID");
		// Set the statements wildcards. Starts @ index 1 and increments by 1
		stmt.setString(1, employee_id);
		rs = stmt.executeQuery();
		return rs;
	}
	
	/**
	 * Get a salt value from the employees table in the DB by employee_id(PK)
	 * @param employee_id the in-bound employee_id to match
	 * @return returns the result set of the executed query
	 */
	//MIGHT NOT EVEN NEED THIS
	public static ResultSet getEmpSalt(String employee_id) throws SQLException {
		// The prepared statement to be executed
		stmt = sql_statements.get("EmpSalt");
		//Set statement wildcards. Starts @ index 1 and increments by 1
		stmt.setString(1, employee_id);
		rs = stmt.executeQuery();
		return rs;
	}
	
	/**
	 * Insert a new employee to the DB
	 * @param employee_id the employee id to set for the new employee
	 * @param name the full name of the new employee
	 * @param isManagement whether or not the new employee is management
	 * @param pw the employee's password
	 * @param warehouse_id the warehouse id that the new employee will be employed
	 */
	public static void insertNewEmployee(String employee_id, String name, boolean isManagement, 
								 String pw, String warehouse_id) throws SQLException
	{
		stmt = sql_statements.get("NewEmp");
		stmt.setString(1, employee_id);
		stmt.setString(2, name);
		stmt.setBoolean(3, isManagement);
		stmt.setString(4, md5_hash(pw+salt));
		stmt.setString(5, warehouse_id);
		stmt.execute();
	}
	
	public static void updateEmployeePW(String employee_id, String pw) throws SQLException {
		stmt = sql_statements.get("UpdateEmpPW");
		stmt.setString(1, md5_hash(pw+salt));
		stmt.setString(2, employee_id);
		stmt.execute();
	}
	
	public static ResultSet getAllEmp() throws SQLException {
		stmt = sql_statements.get("AllEmp");
		rs = stmt.executeQuery();
		return rs;
	}
	
public static boolean employeeExists(String employee_id) throws SQLException {
		rs = getEmpRowByID(employee_id);
		if (!rs.isBeforeFirst() ) {    
		    return false; 
		} 
		return true;
	}
	
	/**
	 * Deletes the employee with the given ID from the database
	 * @param employee_id the ID of the employee to delete
	 * @return true if the employee was successfully deleted, false otherwise
	 * @throws SQLException
	 */
	public static boolean deleteEmployee(String employee_id) throws SQLException{
		stmt = sql_statements.get("DelEmp");
		stmt.setString(1, employee_id);
		rs = stmt.executeQuery();
		return employeeExists(employee_id);
	}
	
	public static String getEmployeeNameByID(String employee_id) throws SQLException{
		rs = getEmpRowByID(employee_id);
		rs.next();
		String name = rs.getString(DBNamesManager.getEmployeeNameDbField());
		return name;
	}
	
	public static String getEmployeeWarehouseByEmpID(String employee_id) throws SQLException{
		rs = getEmpRowByID(employee_id);
		rs.next();
		String name = rs.getString(DBNamesManager.getEmployeeWarehouseIdDbField());
		return name;
	}
	
	public static boolean isEmployeeManager(String employee_id) throws SQLException{
		rs = getEmpRowByID(employee_id);
		rs.next();
		boolean isManager = rs.getBoolean(DBNamesManager.getEmployeeIsManagerDbField());
		return isManager;
	}
	
	/**
	 * Insert a new employee to the DB
	 * @param employee_id the employee id to set for the new employee
	 * @param name the full name of the new employee
	 * @param isManagement whether or not the new employee is management
	 * @param warehouse_id the warehouse id that the new employee will be employed
	 */
	public static void updateEmployee(String employeeID, String new_name, boolean new_isManagement,
			String new_warehouse_id) throws SQLException
	{
		//UPDATE employees name = ?, is_management = ?, warehouse_id = ? WHERE employee_id = ?
		stmt = sql_statements.get("UpdateEmp");
		stmt.setString(1, new_name);
		stmt.setBoolean(2, new_isManagement);
		stmt.setString(3, new_warehouse_id);
		stmt.setString(4, employeeID);
		stmt.execute();
	}
		
 
	//#############################################Items
	public static boolean itemInDB(String itemNumber) throws SQLException {
			stmt = sql_statements.get("InDB");
			stmt.setString(1, itemNumber);
			rs = stmt.executeQuery();
			if (rs.next())
				return true;
			else
				return false;
	}
	
	/**
	 * Insert a new item into the database
	 * @param itemNumber item number to insert
	 * @param name	name of item to insert
	 * @param price price of item to insert
	 * @param weight weight of item to insert
	 * @param currentStock current stock of item to insert
	 * @param restockThreshold restock threshold of item to insert
	 * @throws SQLException when the SQL statement fails to execute
	 */
	public static void insertNewItem(String itemNumber, String name, String price, 
			int weight, int currentStock, int restockThreshold) throws SQLException
	{
		stmt = sql_statements.get("NewItem");
		stmt.setString(1, itemNumber);
		stmt.setString(2, name);
		stmt.setString(3, price);
		stmt.setInt(4, weight);
		stmt.setInt(5, currentStock);
		stmt.setInt(6, restockThreshold);
		stmt.execute();
	}
	
	public static String getItemName(String itemNumber) throws SQLException {
		String name;
		stmt = sql_statements.get("ItemInfo");
		stmt.setString(1, itemNumber);
		rs = stmt.executeQuery();
		rs.next();
		name = rs.getString("name");
		return name;
	}
	
	public static int getItemWeight(String itemNumber) throws SQLException {
		int weight = 0;
		stmt = sql_statements.get("ItemInfo");
		stmt.setString(1, itemNumber);
		rs = stmt.executeQuery();
		rs.next();
		weight = rs.getInt("weight");
		return weight;
	}
	
	public static String getItemPrice(String itemNumber) throws SQLException {
		String price;
		stmt = sql_statements.get("ItemInfo");
		stmt.setString(1, itemNumber);
		rs = stmt.executeQuery();
		rs.next();
		price = rs.getString("price");
		return price;
	}
	
	/**
	 * Get the current stock for an item specified by the item number
	 * @param itemNumber the item number of the item to get current stock for
	 * @return returns the current amount of an item listed in the database
	 * @throws SQLException when the SQL statement cannot be executed
	 */
	public static int getItemCurrentStock(String itemNumber) throws SQLException {
		int currentStock = 0;
		stmt = sql_statements.get("ItemStock");
		stmt.setString(1, itemNumber);
		rs = stmt.executeQuery();
		rs.next();
		currentStock = rs.getInt("current_stock");
		return currentStock;
	}
	
	public static int getItemRestock(String itemNumber) throws SQLException {
		int reStock = 0;
		stmt = sql_statements.get("ItemInfo");
		stmt.setString(1, itemNumber);
		rs = stmt.executeQuery();
		rs.next();
		reStock = rs.getInt("restock_threshold");
		return reStock;
	}
	
	public static void fullItemUpdate(String name, String price, 
			int weight, int currentStock, int restockThreshold, String itemNumber) throws SQLException
	{
		stmt = sql_statements.get("FullUpdate");		
		stmt.setString(1, name);
		stmt.setString(2, price);
		stmt.setInt(3, weight);
		stmt.setInt(4, currentStock);
		stmt.setInt(5, restockThreshold);
		stmt.setString(6, itemNumber);
		stmt.execute();
	}
	
	/**
	 * Update the item quantity for an item specified by the item number
	 * @param amount the amount to adjust the item quantity by
	 * @param itemNumber the item number of the item to adjust quantity for 
	 * @throws SQLException when the SQL statement cannot be executed
	 */
	public static void updateItemQtyByItemNum(int amount, String itemNumber) throws SQLException {
		int currentStock = getItemCurrentStock(itemNumber);
		stmt = sql_statements.get("UpdateItemQty");
		stmt.setInt(1, currentStock + amount);
		stmt.setString(2, itemNumber);
		stmt.execute();
	}
	
	public static void insertItemType(String itemNumber, String itemType) throws SQLException {
		stmt = sql_statements.get("ItemType");
		stmt.setString(1, itemNumber);
		stmt.setString(2, itemType);
		stmt.execute();
	}
	
	/**
	 * Get the itemTypes from the database and return them as a list
	 * @param itemNumber
	 * @return a list of all the items types of itemNumber as strings
	 * @throws SQLException
	 */
	public static ArrayList<String> getItemTypes(String itemNumber) throws SQLException {
		ArrayList<String> itemTypeList = new ArrayList<String>();
		stmt = sql_statements.get("GetItemTypes");
		stmt.setString(1, itemNumber);
		rs = stmt.executeQuery();				//execute
		while (rs.next()) {
			itemTypeList.add(rs.getString("type"));
		}
		return itemTypeList;
	}
	
	public static String getItemTypesAsOneString(String itemNumber) throws SQLException{
		String itemTypesString = "";
		try{
		ArrayList<String> itemTypeList = getItemTypes(itemNumber);
		for(int ndx = 0; ndx < itemTypeList.size(); ndx++){
			itemTypesString = itemTypesString + itemTypeList.get(ndx) + ",";
		}
		itemTypesString = itemTypesString.substring(0, itemTypesString.length() - 1);
		}catch(SQLException sqlEx){
			throw sqlEx;
		}
		return itemTypesString;
	}
	
	/**
	 * Get all items in the database
	 * @return a resultset containing all items in the database (all columns)
	 * @throws SQLException
	 */
	public static ResultSet getAllItems() throws SQLException {
		stmt = sql_statements.get("AllItems");
		rs = stmt.executeQuery();
		return rs;
	}
	
	
	//#############################################Pallets
		public static boolean palletInDB(String palletID) throws SQLException {
		stmt = sql_statements.get("PalletInDB");
		stmt.setString(1, palletID);
		rs = stmt.executeQuery();
		if (rs.next())
			return true;
		else
			return false;
	}
	
	public static int getItemCountOnPallet(String palletID, String itemNumber) throws SQLException {
		int result = 0;
		stmt = sql_statements.get("GetItemCountOnPallet");
		stmt.setString(1, palletID);
		stmt.setString(2, itemNumber);
		rs = stmt.executeQuery();
		rs.next();
		result = rs.getInt("item_quantity");		
		return result;
	}

	public static ArrayList<String> getItemsOnPallet(String palletID) throws SQLException {
		ArrayList<String> result = new ArrayList<String>();
		stmt = sql_statements.get("GetItemsOnPallet");
		stmt.setString(1, palletID);
		rs = stmt.executeQuery();
		while (rs.next()) {
			result.add(rs.getString("item_number"));
		}
		return result;
	}
	
	public static void updateItemOnPallet(String palletID, String itemNumber, int count) throws SQLException {
		stmt = sql_statements.get("UpdateItemOnPallet");
		stmt.setInt(1, count);
		stmt.setString(2, palletID);
		stmt.setString(3, itemNumber);
		stmt.execute();
	}
	
	public static void updatePieceCount(String palletID, int count) throws SQLException {
		stmt = sql_statements.get("UpdatePieceCount");
		stmt.setInt(1, count);
		stmt.setString(2, palletID);
		stmt.execute();
	}
	
	public static void insertNewPallet(String palletID, int pieceCount, int weight, int length, int width, int height, String receiveDate, String shipDate, String notes, String orderNumber, String Location) throws SQLException {
		stmt = sql_statements.get("NewPallet");
		stmt.setString(1,palletID);
		stmt.setInt(2,pieceCount);
		stmt.setInt(3,weight);;
		stmt.setInt(4,length);
		stmt.setInt(5,width);
		stmt.setInt(6,height);
		stmt.setString(7,receiveDate);
		stmt.setString(8,shipDate);
		stmt.setString(9,notes);
		stmt.setString(10,orderNumber);
		stmt.setString(11,Location);
		stmt.execute();
	}
	
	public static void addItemsToPallet(String palletID, String itemNumber, int itemQuantity) throws SQLException {
		stmt = sql_statements.get("AddItemToPallet");
		stmt.setString(1, palletID);
		stmt.setString(2, itemNumber);
		stmt.setInt(3, itemQuantity);
		stmt.execute();		
	}
	
	public static String getPalletLocation(String palletID) throws SQLException {
		stmt = sql_statements.get("GetPalletLocation");
		stmt.setString(1, palletID);
		rs = stmt.executeQuery();
		rs.next();
		String index = rs.getString("pallet_location");
		int temp = Integer.parseInt(index);
		stmt = sql_statements.get("GetSubLocationName");
		stmt.setInt(1, temp);
		rs = stmt.executeQuery();
		rs.next();
		return rs.getString("location_coordinate");
	}																		  
	
	//#############################################Orders
	public static boolean OrderInDB(String OrderNumber) throws SQLException {
		stmt = sql_statements.get("OrderInDB");
		stmt.setString(1, OrderNumber);
		rs = stmt.executeQuery();
		if (rs.next())
			return true;
		else
			return false;
	}
	
	public static void insertNewOrder(int orderNumber, String origin, String destination, String receiveEmployeeID, String shipEmployeeID, String datePlaced, String dateShipped, String dateDelivered) throws SQLException {
		stmt = sql_statements.get("NewOrder");
		stmt.setInt(1, orderNumber);
		stmt.setString(2, origin);
		stmt.setString(3, destination);
		stmt.setString(4, receiveEmployeeID);
		stmt.setString(5, shipEmployeeID);
		stmt.setString(6, datePlaced);
		stmt.setString(7, dateShipped);
		stmt.setString(8, dateDelivered);
		stmt.execute();
	}
		
	//#############################################Warehouses
	public ResultSet getWarehouseNamesAndIDs() throws SQLException {
		stmt = sql_statements.get("GetWHNamesIDs");
		rs = stmt.executeQuery();
		return rs;
	}
	
	public static String[] getWarehouseNames() throws SQLException {
		stmt = sql_statements.get("GetWHNames");
		rs = stmt.executeQuery();
		Object[] array = getColumnAsArray(rs, 1);
		String[] names = Arrays.copyOf(array, array.length, String[].class);
		return names;
	}
	
	public static String[] getWarehouseIDs() throws SQLException {
		stmt = sql_statements.get("GetWHIDs");
		rs = stmt.executeQuery();
		Object[] array = getColumnAsArray(rs, 1);
		String[] IDs = Arrays.copyOf(array, array.length, String[].class);
		return IDs;
	}
	
	public static String[] getWarehouseCities() throws SQLException {
		stmt = sql_statements.get("GetWHCities");
		rs = stmt.executeQuery();
		Object[] array = getColumnAsArray(rs, 1);
		String[] cities = Arrays.copyOf(array, array.length, String[].class);
		return cities;
	}
 
	//#############################################Display	
	/**
	 * @param result
	 *            the result set to convert to a list of arrays
	 * @return a list containing String arrays, where every index in the list is
	 *         a row, where the String arrays each represent a row
	 * @throws SQLException
	 */
	public static List<Object> getColumnAsList(ResultSet result, int col) throws SQLException {
		List<Object> list = new ArrayList<>();
		while( result.next()) {
			Object next = result.getObject(col);
		    list.add(next);
		}
		return list;
	}
	
	public static Object[] getColumnAsArray(ResultSet result, int col) throws SQLException {
		return getColumnAsList(result,col).toArray();
	}
	
	/**
	 * @param result the result set to convert to a list of arrays
	 * @return a list containing String arrays, where every index in the list is a row,
	 * where the String arrays each represent a row
	 * @throws SQLException
	 */
public static List<Object[]> getResultSetAsListOfArrays(ResultSet result) throws SQLException
	{
		int nCol = result.getMetaData().getColumnCount();
		List<Object[]> table = new ArrayList<>();
		while( result.next()) {
			Object[] row = new Object[nCol];
		    for( int iCol = 1; iCol <= nCol; iCol++ ){
		            Object obj = result.getObject( iCol );
		            row[iCol-1] = (obj == null) ?null:obj.toString();
		    }
		    table.add( row );
		}
	
		return table;
	}
	
	public static String[] getColumnNamesFromResultSet(ResultSet result) throws SQLException
	{
		 ResultSetMetaData rsmd = result.getMetaData();
		 int nCol = rsmd.getColumnCount();
		 String[] columnNames = new String[nCol];
		 for(int i = 0; i<nCol; i++)
		 {
			 columnNames[i] = rsmd.getColumnName(i+1);
		 }
		 return columnNames;
	}
	
	/**
	 * 
	 * @param result
	 *            the result set to convert to a list of arrays
	 * @return a list containing String arrays, where every index in the list is
	 *         a row, where the String arrays each represent a row
	 * @throws SQLException
	 */
	public static Object[][] getResultSetAs2DObjArray(ResultSet result) throws SQLException {
		int nCol = result.getMetaData().getColumnCount();
		// set the resultset to the last row
		result.last();
		// save the row number of the last row
		int nRow = result.getRow();
		// set the cursor back to the top
		result.beforeFirst();
		Object[][] data = new Object[nRow][nCol];
		int ndx = 0;
		while (result.next()) {
			Object[] row = new Object[nCol];
			for (int iCol = 1; iCol <= nCol; iCol++) {
				Object obj = result.getObject(iCol);
				row[iCol - 1] = obj;
			}
			data[ndx] = row;
			ndx++;
		}

		result.beforeFirst();
		return data;
	}

	public static void updateColumnNamesToDisplayNames(String[] columnNames) {
		// TODO Auto-generated method stub
		for (int i = 0; i < columnNames.length; i++) {
			columnNames[i] = DBNamesManager.getFieldDisplayNameByDatabaseVariable(columnNames[i]);
		}
	}

	public static String getQueryModifierString(String fieldModifier, String fieldModifierValue) {
		String modifierString = "";
		switch (fieldModifier){
		case DBNamesManager.NUMERIC_FIELD_LESS_THAN: 
			modifierString = " < " + "\'" + fieldModifierValue + "\'";
			break;
		case DBNamesManager.NUMERIC_FIELD_GREATER_THAN: 
			modifierString = " > " + "\'" + fieldModifierValue + "\'";
			break;
		case DBNamesManager.NUMERIC_FIELD_EQUAL_TO:
			modifierString = " = " + "\'" + fieldModifierValue + "\'";
			break;
		case DBNamesManager.STRING_FIELD_STARTING_WITH:
			modifierString = " LIKE " + "\"" + fieldModifierValue + "%" + "\"";
			break;
		case DBNamesManager.STRING_FIELD_ENDING_WITH:
			modifierString = " LIKE " + "\"" + "%" + fieldModifierValue + "\"";
			break;
		case DBNamesManager.STRING_FIELD_CONTAINS:
			modifierString = " LIKE " + "\"" + "%" + fieldModifierValue + "%" + "\""; //TODO check syntaxes for all of these
			break;
		case DBNamesManager.STRING_FIELD_THAT_IS:
			modifierString = " = " + "\'" + fieldModifierValue + "\'";
			break;
		case DBNamesManager.DATE_FIELD_BEFORE:
			modifierString = " < " + "\'" + fieldModifierValue + "\'";
			break;
		case DBNamesManager.DATE_FIELD_AFTER:
			modifierString = " > " + "\'" + fieldModifierValue + "\'";
			break;
		case DBNamesManager.DATE_FIELD_ON:
			modifierString = " = " + "\'" + fieldModifierValue + "\'";
			break;
		case DBNamesManager.FLAG_FIELD_IS:
			modifierString = " = " + "\'" + fieldModifierValue + "\'";
			break;
		case DBNamesManager.FLAG_FIELD_IS_NOT:
			modifierString = " != " + "\'" + fieldModifierValue + "\'";
			break;
		}
		return modifierString;
	}
	
	/**
	 * Sanitize the given input to be used as a query. This is bad but oh well. 
	 * @param userInput the query input to sanitize
	 * @return the sanitized query input
	 */
	private static String sanitizeInput(String userInput) {

		  //Replace all apostrophes with double apostrophes
		  String safeStr = userInput.replace("'", "''");

		  //Replace all backslashes with double backslashes
		  safeStr = safeStr.replace("\\", "\\\\");

		  //Replace all non-alphanumeric and punctuation characters (per ASCII only)
		  safeStr = safeStr.replaceAll("[^\\p{Alnum}\\p{Punct}]", "");

		  return safeStr;
		}
	//#############################################Sublocation
	
	public static ArrayList<String> getAvailableSubLocations() throws SQLException {
		ArrayList<String> subLocationList = new ArrayList<String>();
		stmt = sql_statements.get("GetAvailableSubLocations");
		rs = stmt.executeQuery();				//execute
		while (rs.next()) {
			subLocationList.add(rs.getString("location_coordinate"));
		}
		return subLocationList;
	}
	
	public void incrementSubLocationPalletQuantity(String sublocation) throws SQLException {
		stmt = sql_statements.get("GetSubLocationInfo");
		stmt.setString(1, sublocation);
		rs = stmt.executeQuery();								//get the info
		int currentQuantity = rs.getInt("current_pallet_qty");	//set the variables
		int maxQuantity = rs.getInt("max_pallet_qty");
		if (maxQuantity != currentQuantity) { //if the current isnt maxed out increment
			stmt = sql_statements.get("ChangeSubLocationCurrentQuantity");
			stmt.setInt(1, currentQuantity + 1);
			stmt.setString(2, sublocation);
			stmt.execute();
		}
	}

	public void decrementSubLocationPalletQuantity(String sublocation) throws SQLException {
		stmt = sql_statements.get("GetSubLocationInfo");
		stmt.setString(1, sublocation);
		rs = stmt.executeQuery();
		int currentQuantity = rs.getInt("current_pallet_qty");
		if (currentQuantity != 0) { //if the current isnt 0 decrement
			stmt = sql_statements.get("ChangeSubLocationCurrentQuantity");
			stmt.setInt(1, currentQuantity - 1);
			stmt.setString(2, sublocation);
			stmt.execute();
		}
	}			

}
