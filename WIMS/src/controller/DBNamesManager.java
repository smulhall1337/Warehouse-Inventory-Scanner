package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public abstract class DBNamesManager {
	
	
	/*
	 * =======================================================================================================
	 * *******************************************************************************************************
	 * ******************************************ENTITIES*****************************************************
	 * *******************************************************************************************************
	 * =======================================================================================================
	 */
	//All of the entity type names for the entity type combobox
	public static final String ITEM_ENTITY_DISPLAYNAME = "Items";
	public static final String PALLET_ENTITY_DISPLAYNAME = "Pallets";
	public static final String ORDER_ENTITY_DISPLAYNAME = "Orders";
	public static final String WAREHOUSE_ENTITY_DISPLAYNAME = "Warehouses";
	public static final String SUBLOCATION_ENTITY_DISPLAYNAME = "Sublocations";
	public static final String EMPLOYEE_ENTITY_DISPLAYNAME = "Employees";
	public static final String[] ENTITY_DISPLAYNAMES = {ITEM_ENTITY_DISPLAYNAME, PALLET_ENTITY_DISPLAYNAME, ORDER_ENTITY_DISPLAYNAME, 
			WAREHOUSE_ENTITY_DISPLAYNAME, SUBLOCATION_ENTITY_DISPLAYNAME, EMPLOYEE_ENTITY_DISPLAYNAME};
	
	//All of the entity type names for the entity type combobox
	public static final String ITEM_DB_ENTITY_NAME = "items";
	public static final String PALLET_DB_ENTITY_NAME = "pallets";
	public static final String ORDER_DB_ENTITY_NAME = "orders";
	public static final String WAREHOUSE_DB_ENTITY_NAME = "warehouses";
	public static final String SUBLOCATION_DB_ENTITY_NAME = "sublocation";
	public static final String EMPLOYEE_DB_ENTITY_NAME = "employees";
	public static final String[] ALL_DB_ENTITY_NAMES = {ITEM_DB_ENTITY_NAME, 
		PALLET_DB_ENTITY_NAME, ORDER_DB_ENTITY_NAME, WAREHOUSE_DB_ENTITY_NAME, SUBLOCATION_DB_ENTITY_NAME, EMPLOYEE_DB_ENTITY_NAME};
		
	/*
	 * =======================================================================================================
	 * *******************************************************************************************************
	 * ********************************************ITEMS******************************************************
	 * *******************************************************************************************************
	 * =======================================================================================================
	 */
	//All of the Item fields
	public static final String ITEM_SIMPLE_INDEX_DISPLAYNAME = "Simple Item Index";
	public static final String ITEM_NAME_FIELD_DISPLAYNAME = "Name";
	public static final String ITEM_NUMBER_FIELD_DISPLAYNAME = "Item Number";
	public static final String ITEM_PRICE_FIELD_DISPLAYNAME = "Price (USD)";
	public static final String ITEM_WEIGHT_FIELD_DISPLAYNAME = "Weight (lb)";
	public static final String ITEM_CURR_STOCK_FIELD_DISPLAYNAME = "Current Stock";
	public static final String ITEM_RESTOCK_FIELD_DISPLAYNAME = "Restock Threshold";
	public static final String ITEM_CATEGORIES_FIELD_DISPLAYNAME = "Types";
	public static final String ITEM_SHORTAGES_FIELD_DISPLAYNAME = "Overages/Shortages";
	//TODO handle item categories--get from SQL handler as 1 string with commas, display in one column
	//Item fields for the fields combobox
	public static final String[] ITEM_FIELD_DISPLAYNAMES = {ITEM_SIMPLE_INDEX_DISPLAYNAME, ITEM_NAME_FIELD_DISPLAYNAME, ITEM_NUMBER_FIELD_DISPLAYNAME, ITEM_PRICE_FIELD_DISPLAYNAME,
													ITEM_WEIGHT_FIELD_DISPLAYNAME, ITEM_CURR_STOCK_FIELD_DISPLAYNAME, ITEM_RESTOCK_FIELD_DISPLAYNAME, ITEM_CATEGORIES_FIELD_DISPLAYNAME};
	
	//All of the Item database fields
	public static final String ITEM_SIMPLE_INDEX_DB_FIELD = "simple_item_index";
	public static final String ITEM_NAME_DB_FIELD = "name";
	public static final String ITEM_NUMBER_DB_FIELD = "item_number";
	public static final String ITEM_PRICE_DB_FIELD = "price";
	public static final String ITEM_WEIGHT_DB_FIELD = "weight";
	public static final String ITEM_CURR_STOCK_DB_FIELD = "current_stock";
	public static final String ITEM_RESTOCK_DB_FIELD = "restock_threshold";
	public static final String ITEM_CATEGORIES_DB_FIELD = "type"; //TODO this isnt quite accurate as it requires bridging
	public static final String ITEM_SHORTAGES_DB_FIELD = "overage_shortage";
	public static final String[] ITEM_DB_FIELDS = {ITEM_SIMPLE_INDEX_DB_FIELD, ITEM_NAME_DB_FIELD, ITEM_NUMBER_DB_FIELD, ITEM_PRICE_DB_FIELD,
		ITEM_WEIGHT_DB_FIELD, ITEM_CURR_STOCK_DB_FIELD, ITEM_RESTOCK_DB_FIELD};
		
	

	/*
	 * =======================================================================================================
	 * *******************************************************************************************************
	 * *******************************************PALLETS*****************************************************
	 * *******************************************************************************************************
	 * =======================================================================================================
	 */
	//All of the Pallet fields
	public static final String PALLET_SIMPLE_INDEX_DISPLAYNAME = "Simple Pallet Index";
	public static final String PALLET_ID_FIELD_DISPLAYNAME = "Pallet ID";
	public static final String PALLET_ORDER_NUM_FIELD_DISPLAYNAME = "Order Number";
	public static final String PALLET_LOC_FIELD_DISPLAYNAME = "Location";
	public static final String PALLET_PIECE_COUNT_FIELD_DISPLAYNAME = "Piece Count";
	public static final String PALLET_WEIGHT_FIELD_DISPLAYNAME = "Weight (lb)";
	public static final String PALLET_LENGTH_FIELD_DISPLAYNAME = "Length (in)";
	public static final String PALLET_WIDTH_FIELD_DISPLAYNAME = "Width (in)";
	public static final String PALLET_HEIGHT_FIELD_DISPLAYNAME = "Height (in)";
	public static final String PALLET_RECEIVE_DATE_FIELD_DISPLAYNAME = "Receival Date";
	public static final String PALLET_SHIP_DATE_FIELD_DISPLAYNAME = "Ship Date";
	public static final String PALLET_NOTES_FIELD_DISPLAYNAME = "Notes";
	//Pallet fields for the fields combobox
	public static final String[] PALLET_FIELD_DISPLAYNAMES = {PALLET_SIMPLE_INDEX_DISPLAYNAME, PALLET_ID_FIELD_DISPLAYNAME, PALLET_ORDER_NUM_FIELD_DISPLAYNAME, PALLET_LOC_FIELD_DISPLAYNAME,
									PALLET_PIECE_COUNT_FIELD_DISPLAYNAME, PALLET_WEIGHT_FIELD_DISPLAYNAME, PALLET_LENGTH_FIELD_DISPLAYNAME, PALLET_WIDTH_FIELD_DISPLAYNAME, 
									PALLET_HEIGHT_FIELD_DISPLAYNAME, PALLET_RECEIVE_DATE_FIELD_DISPLAYNAME, PALLET_SHIP_DATE_FIELD_DISPLAYNAME, PALLET_NOTES_FIELD_DISPLAYNAME};
	
	//All of the Pallet database fields
	public static final String PALLET_SIMPLE_INDEX_DB_FIELD = "simple_pallet_index";
	public static final String PALLET_ID_DB_FIELD = "pallet_id";
	public static final String PALLET_ORDER_NUM_DB_FIELD = "order_number";
	public static final String PALLET_LOC_DB_FIELD = "pallet_location";
	public static final String PALLET_PIECE_COUNT_DB_FIELD = "piece_count";
	public static final String PALLET_WEIGHT_DB_FIELD = "weight";
	public static final String PALLET_LENGTH_DB_FIELD = "length";
	public static final String PALLET_WIDTH_DB_FIELD = "width";
	public static final String PALLET_HEIGHT_DB_FIELD = "height";
	public static final String PALLET_RECEIVE_DATE_DB_FIELD = "receival_date";
	public static final String PALLET_SHIP_DATE_DB_FIELD = "ship_date";
	public static final String PALLET_NOTES_DB_FIELD = "notes";
	public static final String[] PALLET_DB_FIELDS = {PALLET_SIMPLE_INDEX_DB_FIELD, PALLET_ID_DB_FIELD, PALLET_ORDER_NUM_DB_FIELD, PALLET_LOC_DB_FIELD,
		PALLET_PIECE_COUNT_DB_FIELD, PALLET_WEIGHT_DB_FIELD, PALLET_LENGTH_DB_FIELD, PALLET_WIDTH_DB_FIELD, 
		PALLET_HEIGHT_DB_FIELD, PALLET_RECEIVE_DATE_DB_FIELD, PALLET_SHIP_DATE_DB_FIELD, PALLET_NOTES_DB_FIELD};
		
	/*
	 * =======================================================================================================
	 * *******************************************************************************************************
	 * ********************************************ORDERS*****************************************************
	 * *******************************************************************************************************
	 * =======================================================================================================
	 */
	//All of the Order fields
	public static final String ORDER_SIMPLE_INDEX_DISPLAYNAME = "Simple Order Index";
	public static final String ORDER_NUM_FIELD_DISPLAYNAME = "Order Number";
	public static final String ORDER_ORIGIN_FIELD_DISPLAYNAME = "Origin";
	public static final String ORDER_DEST_FIELD_DISPLAYNAME = "Destination";
	public static final String ORDER_DATE_PLACED_FIELD_DISPLAYNAME = "Date Placed";
	public static final String ORDER_DATE_SHIPPED_FIELD_DISPLAYNAME = "Date Shipped";
	public static final String ORDER_RECEIVING_EMPLOYEE_FIELD_DISPLAYNAME = "Employee Received By";
	public static final String ORDER_SHIPPING_EMPLOYEE_FIELD_DISPLAYNAME = "Employee Shipped By";
	//Order fields for the fields combobox
	public static final String[] ORDER_FIELD_DISPLAYNAMES = {ORDER_SIMPLE_INDEX_DISPLAYNAME, ORDER_NUM_FIELD_DISPLAYNAME, ORDER_ORIGIN_FIELD_DISPLAYNAME, ORDER_DEST_FIELD_DISPLAYNAME, ORDER_DATE_PLACED_FIELD_DISPLAYNAME,
													ORDER_DATE_SHIPPED_FIELD_DISPLAYNAME, ORDER_RECEIVING_EMPLOYEE_FIELD_DISPLAYNAME, 
													ORDER_SHIPPING_EMPLOYEE_FIELD_DISPLAYNAME};
	
	//All of the Order database fields
	public static final String ORDER_SIMPLE_INDEX_DB_FIELD = "simple_order_index";
	public static final String ORDER_NUM_DB_FIELD = "order_number";
	public static final String ORDER_ORIGIN_DB_FIELD = "origin";
	public static final String ORDER_DEST_DB_FIELD = "destination";
	public static final String ORDER_DATE_PLACED_DB_FIELD = "date_placed";
	public static final String ORDER_DATE_SHIPPED_DB_FIELD = "date_shipped";
	public static final String ORDER_RECEIVING_EMPLOYEE_DB_FIELD = "received_by_emp_id";
	public static final String ORDER_SHIPPING_EMPLOYEE_DB_FIELD = "shipped_by_emp_id";
	public static final String[] ORDER_DB_FIELDS = {ORDER_NUM_DB_FIELD, ORDER_ORIGIN_DB_FIELD, ORDER_DEST_DB_FIELD, ORDER_DATE_PLACED_DB_FIELD,
		ORDER_DATE_SHIPPED_DB_FIELD, ORDER_RECEIVING_EMPLOYEE_DB_FIELD, 
		ORDER_SHIPPING_EMPLOYEE_DB_FIELD};
	
	/*
	 * =======================================================================================================
	 * *******************************************************************************************************
	 * *******************************************WAREHOUSES**************************************************
	 * *******************************************************************************************************
	 * =======================================================================================================
	 */
	//All of the Warehouse fields
	public static final String WAREHOUSE_SIMPLE_INDEX_DISPLAYNAME = "Simple Warehouse Index";
	public static final String WAREHOUSE_ID_FIELD_DISPLAYNAME = "Warehouse ID";
	public static final String WAREHOUSE_CITY_FIELD_DISPLAYNAME = "City";
	public static final String WAREHOUSE_STATE_FIELD_DISPLAYNAME = "State";
	public static final String WAREHOUSE_STREET_ADDRESS_FIELD_DISPLAYNAME = "Street Address";
	public static final String WAREHOUSE_ZIP_FIELD_DISPLAYNAME = "Zip Code";
	public static final String WAREHOUSE_NAME_FIELD_DISPLAYNAME = "Name";
	public static final String WAREHOUSE_PHONE_NUMBER_FIELD_DISPLAYNAME = "Phone Number";
	public static final String WAREHOUSE_EMAIL_FIELD_DISPLAYNAME = "Email Address";
	public static final String[] WAREHOUSE_FIELD_DISPLAYNAMES = {WAREHOUSE_SIMPLE_INDEX_DISPLAYNAME, 
			WAREHOUSE_ID_FIELD_DISPLAYNAME, WAREHOUSE_CITY_FIELD_DISPLAYNAME, WAREHOUSE_STATE_FIELD_DISPLAYNAME, 
			WAREHOUSE_STREET_ADDRESS_FIELD_DISPLAYNAME, WAREHOUSE_ZIP_FIELD_DISPLAYNAME, WAREHOUSE_NAME_FIELD_DISPLAYNAME, 
			WAREHOUSE_PHONE_NUMBER_FIELD_DISPLAYNAME, WAREHOUSE_EMAIL_FIELD_DISPLAYNAME};
			
	//All of the Warehouse database fields
	public static final String WAREHOUSE_SIMPLE_INDEX_DB_FIELD = "simple_warehouse_index";
	public static final String WAREHOUSE_ID_FIELD_DB_FIELD = "warehouse_id";
	public static final String WAREHOUSE_CITY_DB_FIELD = "city";
	public static final String WAREHOUSE_STATE_DB_FIELD = "state";
	public static final String WAREHOUSE_STREET_ADDRESS_DB_FIELD = "street_address";
	public static final String WAREHOUSE_ZIP_DB_FIELD = "zip";
	public static final String WAREHOUSE_NAME_DB_FIELD = "name";
	public static final String WAREHOUSE_PHONE_NUMBER_DB_FIELD = "telephone_number";
	public static final String WAREHOUSE_EMAIL_DB_FIELD = "email_address";
	public static final String[] WAREHOUSE_DB_FIELDS = {WAREHOUSE_SIMPLE_INDEX_DB_FIELD, WAREHOUSE_ID_FIELD_DB_FIELD,
			WAREHOUSE_CITY_DB_FIELD, WAREHOUSE_STATE_DB_FIELD, WAREHOUSE_STREET_ADDRESS_DB_FIELD, 
			WAREHOUSE_ZIP_DB_FIELD, WAREHOUSE_NAME_DB_FIELD, WAREHOUSE_PHONE_NUMBER_DB_FIELD, 
			WAREHOUSE_EMAIL_DB_FIELD};
	
	/*
	 * =======================================================================================================
	 * *******************************************************************************************************
	 * *****************************************SUBLOCATIONS**************************************************
	 * *******************************************************************************************************
	 * =======================================================================================================
	 */
	//All of the Sublocation fields
	public static final String SUBLOCATION_SIMPLE_INDEX_DISPLAYNAME = "Simple Sublocation Index";
	public static final String SUBLOCATION_LOC_COORD_DISPLAYNAME = "Location Coordinate";
	public static final String SUBLOCATION_MAX_PALLET_QTY_DISPLAYNAME = "Max # of Pallets";
	public static final String SUBLOCATION_CURRENT_PALLET_QTY_DISPLAYNAME = "Current # of Pallets";
	public static final String SUBLOCATION_WAREHOUSE_ID_DISPLAYNAME = "Warehouse ID";
	public static final String[] SUBLOCATION_FIELD_DISPLAYNAMES = {SUBLOCATION_SIMPLE_INDEX_DISPLAYNAME, SUBLOCATION_LOC_COORD_DISPLAYNAME, 
			SUBLOCATION_MAX_PALLET_QTY_DISPLAYNAME, SUBLOCATION_CURRENT_PALLET_QTY_DISPLAYNAME, SUBLOCATION_WAREHOUSE_ID_DISPLAYNAME};

	//All of the Sublocation database field names
	public static final String SUBLOCATION_SIMPLE_INDEX_DB_FIELD = "simple_sublo_index";
	public static final String SUBLOCATION_LOC_COORD_DB_FIELD = "location_coordinate";
	public static final String SUBLOCATION_MAX_PALLET_QTY_DB_FIELD = "max_pallet_qty";
	public static final String SUBLOCATION_CURRENT_PALLET_QTY_DB_FIELD = "current_pallet_qty";
	public static final String SUBLOCATION_WAREHOUSE_ID_DB_FIELD = "warehouse_id";
	public static final String[] SUBLOCATION_DB_FIELDS = {SUBLOCATION_SIMPLE_INDEX_DB_FIELD, SUBLOCATION_LOC_COORD_DB_FIELD, 
			SUBLOCATION_MAX_PALLET_QTY_DB_FIELD, SUBLOCATION_CURRENT_PALLET_QTY_DB_FIELD, SUBLOCATION_WAREHOUSE_ID_DB_FIELD};
	
	/*
	 * =======================================================================================================
	 * *******************************************************************************************************
	 * *******************************************EMPLOYEES***************************************************
	 * *******************************************************************************************************
	 * =======================================================================================================
	 */
	//All of the Employee fields
	public static final String EMPLOYEE_SIMPLE_INDEX_DISPLAYNAME = "Simple Employee Index";
	public static final String EMPLOYEE_ID_DISPLAYNAME = "Employee ID";
	public static final String EMPLOYEE_NAME_DISPLAYNAME = "Name";
	public static final String EMPLOYEE_IS_MANAGER_DISPLAYNAME = "Is Manager?";
	public static final String EMPLOYEE_PASSWORD_DISPLAYNAME = "Password";
	public static final String EMPLOYEE_WAREHOUSE_ID_DISPLAYNAME = "Warehouse ID";
	public static final String[] EMPLOYEE_FIELD_DISPLAYNAMES = {EMPLOYEE_SIMPLE_INDEX_DISPLAYNAME, EMPLOYEE_ID_DISPLAYNAME, 
			EMPLOYEE_NAME_DISPLAYNAME, EMPLOYEE_IS_MANAGER_DISPLAYNAME, EMPLOYEE_WAREHOUSE_ID_DISPLAYNAME};

	//All of the Employee database field names
	public static final String EMPLOYEE_SIMPLE_INDEX_DB_FIELD = "simple_employee_index";
	public static final String EMPLOYEE_ID_DB_FIELD = "employee_id";
	public static final String EMPLOYEE_NAME_DB_FIELD = "name";
	public static final String EMPLOYEE_IS_MANAGER_DB_FIELD = "is_management";
	public static final String EMPLOYEE_PASSWORD_DB_FIELD = "password";
	public static final String EMPLOYEE_WAREHOUSE_ID_DB_FIELD = "warehouse_id";
	public static final String[] EMPLOYEE_DB_FIELDS = {EMPLOYEE_SIMPLE_INDEX_DB_FIELD, EMPLOYEE_ID_DB_FIELD, 
			EMPLOYEE_NAME_DB_FIELD, EMPLOYEE_IS_MANAGER_DB_FIELD, EMPLOYEE_WAREHOUSE_ID_DB_FIELD};
	/*
	 * =======================================================================================================
	 * *******************************************************************************************************
	 * ******************************************DATA TYPES***************************************************
	 * **************************************AND FIELD MODIFIERS**********************************************
	 * =======================================================================================================
	 */
	//All of the options for a numeric type field
	public static final String NUMERIC_FIELD_TYPE_NAME = "numeric";
	public static final String NUMERIC_FIELD_GREATER_THAN = "greater than";
	public static final String NUMERIC_FIELD_LESS_THAN = "less than";
	public static final String NUMERIC_FIELD_EQUAL_TO = "equal to";
	//Array of numeric field type options for field options combobox when a numeric type field is selected
	public static final String[] NUMERIC_FIELD_MODIFIER_STRINGS = {NUMERIC_FIELD_GREATER_THAN, NUMERIC_FIELD_LESS_THAN, NUMERIC_FIELD_EQUAL_TO};
	
	//All of the options for a string type field
	public static final String STRING_FIELD_TYPE_NAME = "String";
	public static final String STRING_FIELD_STARTING_WITH = "starting with";
	public static final String STRING_FIELD_ENDING_WITH = "ending with";
	public static final String STRING_FIELD_CONTAINS = "containing";
	public static final String STRING_FIELD_THAT_IS = "that is";
	//Array of numeric field type options for field options combobox when a String type field is selected
	public static final String[] STRING_FIELD_MODIFIER_STRINGS = {STRING_FIELD_STARTING_WITH, STRING_FIELD_ENDING_WITH, STRING_FIELD_CONTAINS, STRING_FIELD_THAT_IS};
	
	//All of the options for a date type field
	public static final String DATE_FIELD_TYPE_NAME = "date";
	public static final String DATE_FIELD_BEFORE = "before";
	public static final String DATE_FIELD_AFTER = "after";
	public static final String DATE_FIELD_ON = "on";
	//Array of numeric field type options for field options combobox when a Date type field is selected
	public static final String[] DATE_FIELD_MODIFIER_STRINGS = {DATE_FIELD_BEFORE, DATE_FIELD_AFTER, DATE_FIELD_ON};
	
	public static final String FLAG_FIELD_TYPE_NAME = "flag";
	public static final String FLAG_FIELD_IS = "is";
	public static final String FLAG_FIELD_IS_NOT = "is not";
	//Array of numeric field type options for field options combobox when a Date type field is selected
	//Do this for easier compatability, even though there is only one field
	public static final String[] FLAG_FIELD_MODIFIER_STRINGS = {FLAG_FIELD_IS, FLAG_FIELD_IS_NOT};
	//the default value for the field modifier components
	public static final String DEFAULT_FIELD_MODIFIER_VALUE = "";
	

	/*
	 * =======================================================================================================
	 * *******************************************************************************************************
	 * ***************************************************HASHMAPS********************************************
	 * *******************************************************************************************************
	 * =======================================================================================================
	 */
	/* HashMap that contains a mapping of all fields to their 'type'
	 * For example, Item Number is a string type, here with a key/value pair of <ITEM_NUMBER_FIELD, STRING_FIELD_TYPE_NAME>
	 * Weight, for example, would be <ITEM_WEIGHT_FIELD,NUMERIC_FIELD_TYPE_NAME> aka <"Weight","numeric">
	 * This allows the combobox for options to know whether to say "less than" for numbers, or "starting with" for strings,
	 * or "before" for dates, etc.
	 */
	private static HashMap<String, String> fieldDisplayNameToDataTypeMap = buildFieldDisplayNameToDataTypeMap();
	private static HashMap<String, String> dataTypeByFieldDBNameMap = buildDataTypeMapByFieldDBNameMap();
	
	/* HashMap that contains a mapping of all buildLocally declared field names to their database variable name
	 * For example, Item Number is a buildLocal field, but in the DB it is item_number.
	 * This mapping would be <ITEM_NAME_FIELD, ITEM_NAME_DB_FIELD>, or <"Item Number","item number">
	 * This allows the update table function to access the database based on UI element values
	 */
	private static HashMap<String, String> fieldDBNameByDisplayNameMap = buildFieldDBNameByDisplayNameMap();
	
	/* HashMap that contains a mapping of all buildLocally declared entity names to their database variable name
	 * For example, Item is a buildLocal field, but in the DB it is item.
	 * This mapping would be <ITEM_ENTITY_FIELD, ITEM_DB_ENTITY_NAME>, or <"Item","item">
	 * This allows the update table function to access the database based on UI element values
	 */
	private static HashMap<String, String> entityDBNameByDisplayNameMap = buildEntityDBNameByDisplayNameMap();
	
	private static HashMap<String, String> entityDisplayNameByDBNameMap = buildEntityDisplayNameByDBNameMap();
	private static HashMap<String, String> fieldDisplayNameByDBNameMap = buildFieldDisplayNameByDBNameMap();

	/*
	 * ===================================================================================================================================
	 * ***********************************************************************************************************************************
	 * ***********************************************************************************************************************************
	 * ***********************************************************************************************************************************
	 * ***********************************************************************************************************************************
	 * ***************************************************BUILD HASHMAPS******************************************************************
	 * ***********************************************************************************************************************************
	 * ***********************************************************************************************************************************
	 * ***********************************************************************************************************************************
	 * ***********************************************************************************************************************************
	 * ===================================================================================================================================
	 */
	
	/*
	 * =======================================================================================================
	 * *******************************************************************************************************
	 * **********************************************FIELDS***************************************************
	 * *******************************BUILD DATABASE NAME TO DISPLAY NAME*************************************
	 * *******************************************************************************************************
	 * =======================================================================================================
	 */
	private static HashMap<String, String> buildFieldDisplayNameByDBNameMap() {
		HashMap<String, String> buildFieldDBNameToDisplayNameMap = new HashMap<String, String>();
		addItemFieldDisplayNamesToMapByDBName(buildFieldDBNameToDisplayNameMap);
		addPalletFieldDisplayNamesToMapByDBName(buildFieldDBNameToDisplayNameMap);
		addOrderFieldDisplayNamesToMapByDBName(buildFieldDBNameToDisplayNameMap);
		addWarehouseFieldDisplayNamesToMapByDBName(buildFieldDBNameToDisplayNameMap);
		addSublocationFieldDisplayNamesToMapByDBName(buildFieldDBNameToDisplayNameMap);
		addEmployeeFieldDisplayNamesToMapByDBName(buildFieldDBNameToDisplayNameMap);
		return buildFieldDBNameToDisplayNameMap;
	}
	
	private static void addItemFieldDisplayNamesToMapByDBName(HashMap<String, String> buildFieldDBNameToDisplayNameNameMap) {
		//NUMERIC TYPES
		buildFieldDBNameToDisplayNameNameMap.put(ITEM_SIMPLE_INDEX_DB_FIELD, ITEM_SIMPLE_INDEX_DISPLAYNAME);
		buildFieldDBNameToDisplayNameNameMap.put(ITEM_NAME_DB_FIELD, ITEM_NAME_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameNameMap.put(ITEM_PRICE_DB_FIELD, ITEM_PRICE_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameNameMap.put(ITEM_WEIGHT_DB_FIELD, ITEM_WEIGHT_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameNameMap.put(ITEM_CURR_STOCK_DB_FIELD, ITEM_CURR_STOCK_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameNameMap.put(ITEM_RESTOCK_DB_FIELD, ITEM_RESTOCK_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameNameMap.put(ITEM_SHORTAGES_DB_FIELD, ITEM_SHORTAGES_FIELD_DISPLAYNAME);
		
		//STRING TYPES
		buildFieldDBNameToDisplayNameNameMap.put(ITEM_NAME_DB_FIELD, ITEM_NAME_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameNameMap.put(ITEM_NUMBER_DB_FIELD, ITEM_NUMBER_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameNameMap.put(ITEM_CATEGORIES_DB_FIELD, ITEM_CATEGORIES_FIELD_DISPLAYNAME);
	}
	
	private static void addPalletFieldDisplayNamesToMapByDBName(HashMap<String, String> buildFieldDBNameToDisplayNameMap) 
	{
		//NUMERIC TYPES
		buildFieldDBNameToDisplayNameMap.put(PALLET_SIMPLE_INDEX_DB_FIELD, PALLET_SIMPLE_INDEX_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(PALLET_PIECE_COUNT_DB_FIELD, PALLET_PIECE_COUNT_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(PALLET_WEIGHT_DB_FIELD, PALLET_WEIGHT_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(PALLET_LENGTH_DB_FIELD, PALLET_LENGTH_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(PALLET_WIDTH_DB_FIELD, PALLET_WIDTH_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(PALLET_HEIGHT_DB_FIELD, PALLET_HEIGHT_FIELD_DISPLAYNAME);
		
		//STRING TYPES
		buildFieldDBNameToDisplayNameMap.put(PALLET_ID_DB_FIELD, PALLET_ID_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(PALLET_ORDER_NUM_DB_FIELD, PALLET_ORDER_NUM_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(PALLET_LOC_DB_FIELD, PALLET_LOC_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(PALLET_NOTES_DB_FIELD, PALLET_NOTES_FIELD_DISPLAYNAME);

		//DATE TYPES
		buildFieldDBNameToDisplayNameMap.put(PALLET_RECEIVE_DATE_DB_FIELD, PALLET_RECEIVE_DATE_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(PALLET_SHIP_DATE_DB_FIELD, PALLET_SHIP_DATE_FIELD_DISPLAYNAME);
	}
	
	private static void addOrderFieldDisplayNamesToMapByDBName(HashMap<String, String> buildFieldDBNameToDisplayNameMap) 
	{
		//NUMERIC TYPES
		buildFieldDBNameToDisplayNameMap.put(ORDER_SIMPLE_INDEX_DB_FIELD, ORDER_SIMPLE_INDEX_DISPLAYNAME);
		
		//STRING TYPES
		buildFieldDBNameToDisplayNameMap.put(ORDER_NUM_DB_FIELD, ORDER_NUM_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(ORDER_ORIGIN_DB_FIELD, ORDER_ORIGIN_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(ORDER_DEST_DB_FIELD, ORDER_DEST_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(ORDER_RECEIVING_EMPLOYEE_DB_FIELD, ORDER_RECEIVING_EMPLOYEE_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(ORDER_SHIPPING_EMPLOYEE_DB_FIELD, ORDER_SHIPPING_EMPLOYEE_FIELD_DISPLAYNAME);
		
		//DATE TYPES
		buildFieldDBNameToDisplayNameMap.put(ORDER_DATE_PLACED_DB_FIELD, ORDER_DATE_PLACED_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(ORDER_DATE_SHIPPED_DB_FIELD, ORDER_DATE_SHIPPED_FIELD_DISPLAYNAME);
	}
	
	private static void addWarehouseFieldDisplayNamesToMapByDBName(HashMap<String, String> buildFieldDBNameToDisplayNameMap) 
	{
		//NUMERIC TYPES
		buildFieldDBNameToDisplayNameMap.put(WAREHOUSE_ZIP_DB_FIELD, WAREHOUSE_ZIP_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(WAREHOUSE_SIMPLE_INDEX_DB_FIELD, WAREHOUSE_SIMPLE_INDEX_DISPLAYNAME);
		
		//STRING TYPES
		buildFieldDBNameToDisplayNameMap.put(WAREHOUSE_CITY_DB_FIELD, WAREHOUSE_CITY_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(WAREHOUSE_STATE_DB_FIELD, WAREHOUSE_STATE_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(WAREHOUSE_STREET_ADDRESS_DB_FIELD, WAREHOUSE_STREET_ADDRESS_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(WAREHOUSE_NAME_DB_FIELD, WAREHOUSE_NAME_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(WAREHOUSE_PHONE_NUMBER_DB_FIELD, WAREHOUSE_PHONE_NUMBER_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(WAREHOUSE_EMAIL_DB_FIELD, WAREHOUSE_EMAIL_FIELD_DISPLAYNAME);
	}
	
	private static void addSublocationFieldDisplayNamesToMapByDBName(HashMap<String, String> buildFieldDBNameToDisplayNameMap) 
	{
		//NUMERIC TYPES
		buildFieldDBNameToDisplayNameMap.put(SUBLOCATION_SIMPLE_INDEX_DB_FIELD, SUBLOCATION_SIMPLE_INDEX_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(SUBLOCATION_MAX_PALLET_QTY_DB_FIELD, SUBLOCATION_MAX_PALLET_QTY_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(SUBLOCATION_CURRENT_PALLET_QTY_DB_FIELD, SUBLOCATION_CURRENT_PALLET_QTY_DISPLAYNAME);
		
		//STRING TYPES
		buildFieldDBNameToDisplayNameMap.put(SUBLOCATION_WAREHOUSE_ID_DB_FIELD, SUBLOCATION_WAREHOUSE_ID_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(SUBLOCATION_LOC_COORD_DB_FIELD, SUBLOCATION_LOC_COORD_DISPLAYNAME);
	}
	
	private static void addEmployeeFieldDisplayNamesToMapByDBName(
			HashMap<String, String> buildFieldDBNameToDisplayNameMap) {
		
		//NUMERIC TYPES
		buildFieldDBNameToDisplayNameMap.put(EMPLOYEE_SIMPLE_INDEX_DB_FIELD, EMPLOYEE_SIMPLE_INDEX_DISPLAYNAME);
		
		//STRING TYPES
		buildFieldDBNameToDisplayNameMap.put(EMPLOYEE_ID_DB_FIELD, EMPLOYEE_ID_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(EMPLOYEE_NAME_DB_FIELD, EMPLOYEE_NAME_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(EMPLOYEE_PASSWORD_DB_FIELD, EMPLOYEE_PASSWORD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(EMPLOYEE_WAREHOUSE_ID_DB_FIELD, EMPLOYEE_WAREHOUSE_ID_DISPLAYNAME);
		
		//FLAG TYPES
		buildFieldDBNameToDisplayNameMap.put(EMPLOYEE_IS_MANAGER_DB_FIELD, EMPLOYEE_IS_MANAGER_DISPLAYNAME);
	}
	
	/*
	 * =======================================================================================================
	 * *******************************************************************************************************
	 * **********************************************FIELDS***************************************************
	 * *******************************BUILD DISPLAYNAMES TO DATABASE NAME*************************************
	 * *******************************************************************************************************
	 * =======================================================================================================
	 */
	private static HashMap<String, String> buildFieldDBNameByDisplayNameMap() {
		HashMap<String, String> buildLocalFieldNameToDBFieldNameMap = new HashMap<String, String>();
		addItemFieldDBNamesToMapByDisplayName(buildLocalFieldNameToDBFieldNameMap);
		addPalletFieldDBNamesToMapByDisplayName(buildLocalFieldNameToDBFieldNameMap);
		addOrderFieldDBNamesToMapByDisplayName(buildLocalFieldNameToDBFieldNameMap);
		addWarehouseFieldDBNamesToMapByDisplayName(buildLocalFieldNameToDBFieldNameMap);
		addSublocationFieldDBNamesToMapByDisplayName(buildLocalFieldNameToDBFieldNameMap);
		addEmployeeFieldDBNamesToMapByDisplayName(buildLocalFieldNameToDBFieldNameMap);
		return buildLocalFieldNameToDBFieldNameMap;
	}

	private static void addItemFieldDBNamesToMapByDisplayName(HashMap<String, String> buildLocalFieldNameToDBFieldNameMap) {
		//NUMERIC TYPES
		buildLocalFieldNameToDBFieldNameMap.put(ITEM_SIMPLE_INDEX_DISPLAYNAME, ITEM_SIMPLE_INDEX_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(ITEM_NAME_FIELD_DISPLAYNAME, ITEM_NAME_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(ITEM_PRICE_FIELD_DISPLAYNAME, ITEM_PRICE_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(ITEM_WEIGHT_FIELD_DISPLAYNAME, ITEM_WEIGHT_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(ITEM_CURR_STOCK_FIELD_DISPLAYNAME, ITEM_CURR_STOCK_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(ITEM_RESTOCK_FIELD_DISPLAYNAME, ITEM_RESTOCK_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(ITEM_SHORTAGES_FIELD_DISPLAYNAME, ITEM_SHORTAGES_DB_FIELD);

		//STRING TYPES
		buildLocalFieldNameToDBFieldNameMap.put(ITEM_NAME_FIELD_DISPLAYNAME, ITEM_NAME_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(ITEM_NUMBER_FIELD_DISPLAYNAME, ITEM_NUMBER_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(ITEM_CATEGORIES_FIELD_DISPLAYNAME , ITEM_CATEGORIES_DB_FIELD);
	}
	
	private static void addPalletFieldDBNamesToMapByDisplayName(HashMap<String, String> buildLocalFieldNameToDBFieldNameMap) 
	{
		//NUMERIC TYPES
		buildLocalFieldNameToDBFieldNameMap.put(PALLET_SIMPLE_INDEX_DISPLAYNAME, PALLET_SIMPLE_INDEX_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(PALLET_PIECE_COUNT_FIELD_DISPLAYNAME, PALLET_PIECE_COUNT_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(PALLET_WEIGHT_FIELD_DISPLAYNAME, PALLET_WEIGHT_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(PALLET_LENGTH_FIELD_DISPLAYNAME, PALLET_LENGTH_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(PALLET_WIDTH_FIELD_DISPLAYNAME, PALLET_WIDTH_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(PALLET_HEIGHT_FIELD_DISPLAYNAME, PALLET_HEIGHT_DB_FIELD);
		
		//STRING TYPES
		buildLocalFieldNameToDBFieldNameMap.put(PALLET_ID_FIELD_DISPLAYNAME, PALLET_ID_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(PALLET_ORDER_NUM_FIELD_DISPLAYNAME, PALLET_ORDER_NUM_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(PALLET_LOC_FIELD_DISPLAYNAME, PALLET_LOC_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(PALLET_NOTES_FIELD_DISPLAYNAME, PALLET_NOTES_DB_FIELD);

		//DATE TYPES
		buildLocalFieldNameToDBFieldNameMap.put(PALLET_RECEIVE_DATE_FIELD_DISPLAYNAME, PALLET_RECEIVE_DATE_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(PALLET_SHIP_DATE_FIELD_DISPLAYNAME, PALLET_SHIP_DATE_DB_FIELD);
	}
	
	private static void addOrderFieldDBNamesToMapByDisplayName(HashMap<String, String> buildLocalFieldNameToDBFieldNameMap) 
	{
		//NUMERIC TYPES
		buildLocalFieldNameToDBFieldNameMap.put(ORDER_SIMPLE_INDEX_DISPLAYNAME, ORDER_SIMPLE_INDEX_DB_FIELD);
		
		//STRING TYPES
		buildLocalFieldNameToDBFieldNameMap.put(ORDER_NUM_FIELD_DISPLAYNAME, ORDER_NUM_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(ORDER_ORIGIN_FIELD_DISPLAYNAME, ORDER_ORIGIN_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(ORDER_DEST_FIELD_DISPLAYNAME, ORDER_DEST_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(ORDER_RECEIVING_EMPLOYEE_FIELD_DISPLAYNAME, ORDER_RECEIVING_EMPLOYEE_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(ORDER_SHIPPING_EMPLOYEE_FIELD_DISPLAYNAME, ORDER_SHIPPING_EMPLOYEE_DB_FIELD);
		
		//DATE TYPES
		buildLocalFieldNameToDBFieldNameMap.put(ORDER_DATE_PLACED_FIELD_DISPLAYNAME, ORDER_DATE_PLACED_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(ORDER_DATE_SHIPPED_FIELD_DISPLAYNAME, ORDER_DATE_SHIPPED_DB_FIELD);
	}
	
	private static void addWarehouseFieldDBNamesToMapByDisplayName(HashMap<String, String> buildFieldDBNameToDisplayNameMap) 
	{
		//NUMERIC TYPES
		buildFieldDBNameToDisplayNameMap.put(WAREHOUSE_ZIP_FIELD_DISPLAYNAME, WAREHOUSE_ZIP_DB_FIELD);
		buildFieldDBNameToDisplayNameMap.put(WAREHOUSE_SIMPLE_INDEX_DISPLAYNAME, WAREHOUSE_SIMPLE_INDEX_DB_FIELD);
		
		//STRING TYPES
		buildFieldDBNameToDisplayNameMap.put(WAREHOUSE_CITY_FIELD_DISPLAYNAME, WAREHOUSE_CITY_DB_FIELD);
		buildFieldDBNameToDisplayNameMap.put(WAREHOUSE_STATE_FIELD_DISPLAYNAME, WAREHOUSE_STATE_DB_FIELD);
		buildFieldDBNameToDisplayNameMap.put(WAREHOUSE_STREET_ADDRESS_FIELD_DISPLAYNAME, WAREHOUSE_STREET_ADDRESS_DB_FIELD);
		buildFieldDBNameToDisplayNameMap.put(WAREHOUSE_NAME_FIELD_DISPLAYNAME, WAREHOUSE_NAME_DB_FIELD);
		buildFieldDBNameToDisplayNameMap.put(WAREHOUSE_PHONE_NUMBER_FIELD_DISPLAYNAME, WAREHOUSE_PHONE_NUMBER_DB_FIELD);
		buildFieldDBNameToDisplayNameMap.put(WAREHOUSE_EMAIL_FIELD_DISPLAYNAME, WAREHOUSE_EMAIL_DB_FIELD);
	}
	
	private static void addEmployeeFieldDBNamesToMapByDisplayName(
			HashMap<String, String> buildLocalFieldNameToDBFieldNameMap) {
		//NUMERIC TYPES
		buildLocalFieldNameToDBFieldNameMap.put(EMPLOYEE_SIMPLE_INDEX_DISPLAYNAME, EMPLOYEE_SIMPLE_INDEX_DB_FIELD);
		
		//STRING TYPES
		buildLocalFieldNameToDBFieldNameMap.put(EMPLOYEE_ID_DISPLAYNAME, EMPLOYEE_ID_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(EMPLOYEE_NAME_DISPLAYNAME, EMPLOYEE_NAME_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(EMPLOYEE_PASSWORD_DISPLAYNAME, EMPLOYEE_PASSWORD_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(EMPLOYEE_WAREHOUSE_ID_DISPLAYNAME, EMPLOYEE_WAREHOUSE_ID_DB_FIELD);
		
		//FLAG TYPES
		buildLocalFieldNameToDBFieldNameMap.put(EMPLOYEE_IS_MANAGER_DISPLAYNAME, EMPLOYEE_IS_MANAGER_DB_FIELD);
		
	}
	
	private static void addSublocationFieldDBNamesToMapByDisplayName(HashMap<String, String> buildFieldDBNameToDisplayNameMap) 
	{
		//NUMERIC TYPES
		buildFieldDBNameToDisplayNameMap.put(SUBLOCATION_SIMPLE_INDEX_DISPLAYNAME, SUBLOCATION_SIMPLE_INDEX_DB_FIELD);
		buildFieldDBNameToDisplayNameMap.put(SUBLOCATION_MAX_PALLET_QTY_DISPLAYNAME, SUBLOCATION_MAX_PALLET_QTY_DB_FIELD);
		buildFieldDBNameToDisplayNameMap.put(SUBLOCATION_CURRENT_PALLET_QTY_DISPLAYNAME, SUBLOCATION_CURRENT_PALLET_QTY_DB_FIELD);
		
		//STRING TYPES
		buildFieldDBNameToDisplayNameMap.put(SUBLOCATION_WAREHOUSE_ID_DISPLAYNAME, SUBLOCATION_WAREHOUSE_ID_DB_FIELD);
		buildFieldDBNameToDisplayNameMap.put(SUBLOCATION_LOC_COORD_DISPLAYNAME, SUBLOCATION_LOC_COORD_DB_FIELD);
	}	
	
	/*
	 * =======================================================================================================
	 * *******************************************************************************************************
	 * ********************************************ENTITIES***************************************************
	 * *******************************BUILD DATABASE NAME TO DISPLAY NAME*************************************
	 * *******************************************************************************************************
	 * =======================================================================================================
	 */
	private static HashMap<String, String> buildEntityDisplayNameByDBNameMap() {
		HashMap<String, String> buildDBEntityNameToDisplayNameMap = new HashMap<String, String>();
		buildDBEntityNameToDisplayNameMap.put(ITEM_DB_ENTITY_NAME,ITEM_ENTITY_DISPLAYNAME);
		buildDBEntityNameToDisplayNameMap.put(PALLET_DB_ENTITY_NAME,PALLET_ENTITY_DISPLAYNAME);
		buildDBEntityNameToDisplayNameMap.put(ORDER_DB_ENTITY_NAME,ORDER_ENTITY_DISPLAYNAME);
		buildDBEntityNameToDisplayNameMap.put(WAREHOUSE_DB_ENTITY_NAME, WAREHOUSE_ENTITY_DISPLAYNAME);
		buildDBEntityNameToDisplayNameMap.put(SUBLOCATION_DB_ENTITY_NAME, SUBLOCATION_ENTITY_DISPLAYNAME);
		buildDBEntityNameToDisplayNameMap.put(EMPLOYEE_DB_ENTITY_NAME, EMPLOYEE_ENTITY_DISPLAYNAME);
		return buildDBEntityNameToDisplayNameMap;
	}
	
	/*
	 * =======================================================================================================
	 * *******************************************************************************************************
	 * *********************************************ENTITIES**************************************************
	 * *******************************BUILD DISPLAYNAMES TO DATABASE NAME*************************************
	 * *******************************************************************************************************
	 * =======================================================================================================
	 */
	private static HashMap<String, String> buildEntityDBNameByDisplayNameMap() {
		HashMap<String, String> buildbuildLocalEntityNameToDBEntityNameMap = new HashMap<String, String>();
		buildbuildLocalEntityNameToDBEntityNameMap.put(ITEM_ENTITY_DISPLAYNAME,ITEM_DB_ENTITY_NAME);
		buildbuildLocalEntityNameToDBEntityNameMap.put(PALLET_ENTITY_DISPLAYNAME,PALLET_DB_ENTITY_NAME);
		buildbuildLocalEntityNameToDBEntityNameMap.put(ORDER_ENTITY_DISPLAYNAME,ORDER_DB_ENTITY_NAME);
		buildbuildLocalEntityNameToDBEntityNameMap.put(WAREHOUSE_ENTITY_DISPLAYNAME,WAREHOUSE_DB_ENTITY_NAME);
		buildbuildLocalEntityNameToDBEntityNameMap.put(SUBLOCATION_ENTITY_DISPLAYNAME,SUBLOCATION_DB_ENTITY_NAME);
		buildbuildLocalEntityNameToDBEntityNameMap.put(EMPLOYEE_ENTITY_DISPLAYNAME, EMPLOYEE_DB_ENTITY_NAME);
		return buildbuildLocalEntityNameToDBEntityNameMap;
	}
	
	/*
	 * =======================================================================================================
	 * *******************************************************************************************************
	 * *******************************************DATA TYPES**************************************************
	 * ********************************BUILD DATABASE NAME TO DATA TYPE***************************************
	 * *******************************************************************************************************
	 * =======================================================================================================
	 */
	private static HashMap<String, String> buildDataTypeMapByFieldDBNameMap() {
		HashMap<String, String> buildingFieldDBNameToDataTypeMap = new HashMap<String, String>();
		addItemFieldTypesToMapByDBName(buildingFieldDBNameToDataTypeMap);
		addPalletFieldTypesToMapByDBName(buildingFieldDBNameToDataTypeMap);
		addOrderFieldTypesToMapByDBName(buildingFieldDBNameToDataTypeMap);
		addWarehouseFieldTypesToMapByDBName(buildingFieldDBNameToDataTypeMap);
		addSublocationFieldTypesToMapByDBName(buildingFieldDBNameToDataTypeMap);
		addEmployeeFieldTypesToMapByDBName(buildingFieldDBNameToDataTypeMap);
		return buildingFieldDBNameToDataTypeMap;
	}

	private static void addEmployeeFieldTypesToMapByDBName(HashMap<String, String> buildingFieldDBNameToDataTypeMap) {
		//NUMERIC TYPES
		buildingFieldDBNameToDataTypeMap.put(EMPLOYEE_SIMPLE_INDEX_DB_FIELD, NUMERIC_FIELD_TYPE_NAME);
		
		//STRING TYPES
		buildingFieldDBNameToDataTypeMap.put(EMPLOYEE_ID_DB_FIELD, STRING_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(EMPLOYEE_NAME_DB_FIELD, STRING_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(EMPLOYEE_PASSWORD_DB_FIELD, STRING_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(EMPLOYEE_WAREHOUSE_ID_DB_FIELD, STRING_FIELD_TYPE_NAME);
		
		//FLAG TYPES
		buildingFieldDBNameToDataTypeMap.put(EMPLOYEE_IS_MANAGER_DB_FIELD, FLAG_FIELD_TYPE_NAME);
	}

	private static void addItemFieldTypesToMapByDBName(
		HashMap<String, String> buildingFieldDBNameToDataTypeMap) {
		buildingFieldDBNameToDataTypeMap.put(ITEM_SIMPLE_INDEX_DB_FIELD, NUMERIC_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(ITEM_PRICE_DB_FIELD, NUMERIC_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(ITEM_WEIGHT_DB_FIELD, NUMERIC_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(ITEM_CURR_STOCK_DB_FIELD, NUMERIC_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(ITEM_RESTOCK_DB_FIELD, NUMERIC_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(ITEM_SHORTAGES_DB_FIELD, NUMERIC_FIELD_TYPE_NAME);
		
		buildingFieldDBNameToDataTypeMap.put(ITEM_NAME_DB_FIELD, STRING_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(ITEM_NUMBER_DB_FIELD, STRING_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(ITEM_CATEGORIES_DB_FIELD, STRING_FIELD_TYPE_NAME);

	}
	
	private static void addPalletFieldTypesToMapByDBName(
		HashMap<String, String> buildingFieldDBNameToDataTypeMap) {
		buildingFieldDBNameToDataTypeMap.put(PALLET_SIMPLE_INDEX_DB_FIELD, NUMERIC_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(PALLET_PIECE_COUNT_DB_FIELD, NUMERIC_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(PALLET_WEIGHT_DB_FIELD, NUMERIC_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(PALLET_LENGTH_DB_FIELD, NUMERIC_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(PALLET_WIDTH_DB_FIELD, NUMERIC_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(PALLET_HEIGHT_DB_FIELD, NUMERIC_FIELD_TYPE_NAME);
		
		buildingFieldDBNameToDataTypeMap.put(PALLET_ID_DB_FIELD, STRING_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(PALLET_ORDER_NUM_DB_FIELD, STRING_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(PALLET_LOC_DB_FIELD, STRING_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(PALLET_NOTES_DB_FIELD, STRING_FIELD_TYPE_NAME);

		buildingFieldDBNameToDataTypeMap.put(PALLET_RECEIVE_DATE_DB_FIELD, DATE_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(PALLET_SHIP_DATE_DB_FIELD, DATE_FIELD_TYPE_NAME);
	}
	
	private static void addOrderFieldTypesToMapByDBName(
			HashMap<String, String> buildingFieldDBNameToDataTypeMap) {
		buildingFieldDBNameToDataTypeMap.put(ORDER_SIMPLE_INDEX_DB_FIELD, NUMERIC_FIELD_TYPE_NAME);

		buildingFieldDBNameToDataTypeMap.put(ORDER_NUM_DB_FIELD, STRING_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(ORDER_ORIGIN_DB_FIELD, STRING_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(ORDER_DEST_DB_FIELD, STRING_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(ORDER_RECEIVING_EMPLOYEE_DB_FIELD, STRING_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(ORDER_SHIPPING_EMPLOYEE_DB_FIELD, STRING_FIELD_TYPE_NAME);
		
		buildingFieldDBNameToDataTypeMap.put(ORDER_DATE_PLACED_DB_FIELD, DATE_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(ORDER_DATE_SHIPPED_DB_FIELD, DATE_FIELD_TYPE_NAME);
	}

	private static void addWarehouseFieldTypesToMapByDBName(HashMap<String, String> buildingFieldDBNameToDataTypeMap) {
		//NUMERIC TYPES
		buildingFieldDBNameToDataTypeMap.put(WAREHOUSE_ZIP_DB_FIELD, NUMERIC_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(WAREHOUSE_SIMPLE_INDEX_DB_FIELD, NUMERIC_FIELD_TYPE_NAME);
		
		//STRING TYPES
		buildingFieldDBNameToDataTypeMap.put(WAREHOUSE_CITY_DB_FIELD, STRING_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(WAREHOUSE_STATE_DB_FIELD, STRING_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(WAREHOUSE_STREET_ADDRESS_DB_FIELD, STRING_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(WAREHOUSE_NAME_DB_FIELD, STRING_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(WAREHOUSE_PHONE_NUMBER_DB_FIELD, STRING_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(WAREHOUSE_EMAIL_DB_FIELD, STRING_FIELD_TYPE_NAME);
	}
	
	private static void addSublocationFieldTypesToMapByDBName(HashMap<String, String> buildingFieldDBNameToDataTypeMap) {
		
		//NUMERIC TYPES
		buildingFieldDBNameToDataTypeMap.put(SUBLOCATION_SIMPLE_INDEX_DB_FIELD, NUMERIC_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(SUBLOCATION_MAX_PALLET_QTY_DB_FIELD, NUMERIC_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(SUBLOCATION_CURRENT_PALLET_QTY_DB_FIELD, NUMERIC_FIELD_TYPE_NAME);
		
		//STRING TYPES
		buildingFieldDBNameToDataTypeMap.put(SUBLOCATION_WAREHOUSE_ID_DB_FIELD, STRING_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(SUBLOCATION_LOC_COORD_DB_FIELD, STRING_FIELD_TYPE_NAME);
	}
	
	/*
	 * =======================================================================================================
	 * *******************************************************************************************************
	 * *******************************************DATA TYPES**************************************************
	 * *********************************BUILD DISPLAY NAME TO DATA TYPE***************************************
	 * *******************************************************************************************************
	 * =======================================================================================================
	 */
	private static HashMap<String, String> buildFieldDisplayNameToDataTypeMap() {
		HashMap<String, String> buildingFieldDisplayNameToDataTypesMap = new HashMap<String, String>();
		addItemFieldTypesToMapByDisplayName(buildingFieldDisplayNameToDataTypesMap);
		addPalletFieldTypesToMapByDisplayName(buildingFieldDisplayNameToDataTypesMap);
		addOrderFieldTypesToMapByDisplayName(buildingFieldDisplayNameToDataTypesMap);
		addWarehouseFieldTypesToMapByDisplayName(buildingFieldDisplayNameToDataTypesMap);
		addSublocationFieldTypesToMapByDisplayName(buildingFieldDisplayNameToDataTypesMap);
		addEmployeeFieldTypesToMapByDisplayName(buildingFieldDisplayNameToDataTypesMap);
		return buildingFieldDisplayNameToDataTypesMap;
	}
	private static void addEmployeeFieldTypesToMapByDisplayName(
			HashMap<String, String> buildingFieldDisplayNameToDataTypesMap) {
		//NUMERIC TYPES
		buildingFieldDisplayNameToDataTypesMap.put(EMPLOYEE_SIMPLE_INDEX_DISPLAYNAME, NUMERIC_FIELD_TYPE_NAME);
		
		//STRING TYPES
		buildingFieldDisplayNameToDataTypesMap.put(EMPLOYEE_ID_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		buildingFieldDisplayNameToDataTypesMap.put(EMPLOYEE_NAME_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		buildingFieldDisplayNameToDataTypesMap.put(EMPLOYEE_PASSWORD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		buildingFieldDisplayNameToDataTypesMap.put(EMPLOYEE_WAREHOUSE_ID_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		
		//FLAG TYPES
		buildingFieldDisplayNameToDataTypesMap.put(EMPLOYEE_IS_MANAGER_DISPLAYNAME, FLAG_FIELD_TYPE_NAME);
		
	}

	/**
	 * @param buildingDataTypesMap
	 */
	private static void addItemFieldTypesToMapByDisplayName(HashMap<String, String> buildingDataTypesMap)
	{
		buildingDataTypesMap.put(ITEM_SIMPLE_INDEX_DISPLAYNAME, NUMERIC_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(ITEM_PRICE_FIELD_DISPLAYNAME, NUMERIC_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(ITEM_WEIGHT_FIELD_DISPLAYNAME, NUMERIC_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(ITEM_CURR_STOCK_FIELD_DISPLAYNAME, NUMERIC_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(ITEM_RESTOCK_FIELD_DISPLAYNAME, NUMERIC_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(ITEM_SHORTAGES_FIELD_DISPLAYNAME, NUMERIC_FIELD_TYPE_NAME);
		
		buildingDataTypesMap.put(ITEM_NAME_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(ITEM_NUMBER_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(ITEM_CATEGORIES_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
	}

	/**
	 * @return the itemCategoriesFieldDisplayname
	 */
	public static String getItemCategoriesFieldDisplayname() {
		return ITEM_CATEGORIES_FIELD_DISPLAYNAME;
	}

	/**
	 * @return the itemCategoriesDbField
	 */
	public static String getItemCategoriesDbField() {
		return ITEM_CATEGORIES_DB_FIELD;
	}

	/**
	 * @return the warehouseIdFieldDbField
	 */
	public static String getWarehouseIdFieldDbField() {
		return WAREHOUSE_ID_FIELD_DB_FIELD;
	}

	/**
	 * @return the employeeFieldDisplaynames
	 */
	public static String[] getEmployeeFieldDisplaynames() {
		return EMPLOYEE_FIELD_DISPLAYNAMES;
	}

	/**
	 * @return the employeeDbFields
	 */
	public static String[] getEmployeeDbFields() {
		return EMPLOYEE_DB_FIELDS;
	}

	/**
	 * @return the flagFieldIsNot
	 */
	public static String getFlagFieldIsNot() {
		return FLAG_FIELD_IS_NOT;
	}

	private static void addPalletFieldTypesToMapByDisplayName(HashMap<String, String> buildingDataTypesMap)
	{
		buildingDataTypesMap.put(PALLET_SIMPLE_INDEX_DISPLAYNAME, NUMERIC_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(PALLET_PIECE_COUNT_FIELD_DISPLAYNAME, NUMERIC_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(PALLET_WEIGHT_FIELD_DISPLAYNAME, NUMERIC_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(PALLET_LENGTH_FIELD_DISPLAYNAME, NUMERIC_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(PALLET_WIDTH_FIELD_DISPLAYNAME, NUMERIC_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(PALLET_HEIGHT_FIELD_DISPLAYNAME, NUMERIC_FIELD_TYPE_NAME);
		
		buildingDataTypesMap.put(PALLET_ID_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(PALLET_ORDER_NUM_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(PALLET_LOC_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(PALLET_NOTES_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);

		buildingDataTypesMap.put(PALLET_RECEIVE_DATE_FIELD_DISPLAYNAME, DATE_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(PALLET_SHIP_DATE_FIELD_DISPLAYNAME, DATE_FIELD_TYPE_NAME);
	}
	
	private static void addOrderFieldTypesToMapByDisplayName(HashMap<String, String> buildingDataTypesMap)
	{	
		buildingDataTypesMap.put(ORDER_SIMPLE_INDEX_DISPLAYNAME, NUMERIC_FIELD_TYPE_NAME);
		
		buildingDataTypesMap.put(ORDER_NUM_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(ORDER_ORIGIN_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(ORDER_DEST_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(ORDER_RECEIVING_EMPLOYEE_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(ORDER_SHIPPING_EMPLOYEE_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		
		buildingDataTypesMap.put(ORDER_DATE_PLACED_FIELD_DISPLAYNAME, DATE_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(ORDER_DATE_SHIPPED_FIELD_DISPLAYNAME, DATE_FIELD_TYPE_NAME);
	}
	
	private static void addWarehouseFieldTypesToMapByDisplayName(HashMap<String, String> buildingFieldDisplayNameToDataTypesMap) {
		//NUMERIC TYPES
		buildingFieldDisplayNameToDataTypesMap.put(WAREHOUSE_ZIP_FIELD_DISPLAYNAME, NUMERIC_FIELD_TYPE_NAME);
		buildingFieldDisplayNameToDataTypesMap.put(WAREHOUSE_SIMPLE_INDEX_DISPLAYNAME, NUMERIC_FIELD_TYPE_NAME);
		
		//STRING TYPES
		buildingFieldDisplayNameToDataTypesMap.put(WAREHOUSE_CITY_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		buildingFieldDisplayNameToDataTypesMap.put(WAREHOUSE_STATE_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		buildingFieldDisplayNameToDataTypesMap.put(WAREHOUSE_STREET_ADDRESS_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		buildingFieldDisplayNameToDataTypesMap.put(WAREHOUSE_NAME_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		buildingFieldDisplayNameToDataTypesMap.put(WAREHOUSE_PHONE_NUMBER_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		buildingFieldDisplayNameToDataTypesMap.put(WAREHOUSE_EMAIL_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
	}
	
	private static void addSublocationFieldTypesToMapByDisplayName(HashMap<String, String> buildingFieldDisplayNameToDataTypesMap) {
		//NUMERIC TYPES
		buildingFieldDisplayNameToDataTypesMap.put(SUBLOCATION_SIMPLE_INDEX_DISPLAYNAME, NUMERIC_FIELD_TYPE_NAME);
		buildingFieldDisplayNameToDataTypesMap.put(SUBLOCATION_MAX_PALLET_QTY_DISPLAYNAME, NUMERIC_FIELD_TYPE_NAME);
		buildingFieldDisplayNameToDataTypesMap.put(SUBLOCATION_CURRENT_PALLET_QTY_DISPLAYNAME, NUMERIC_FIELD_TYPE_NAME);
		
		//STRING TYPES
		buildingFieldDisplayNameToDataTypesMap.put(SUBLOCATION_WAREHOUSE_ID_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		buildingFieldDisplayNameToDataTypesMap.put(SUBLOCATION_LOC_COORD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
	}

	/*
	 * ===================================================================================================================================
	 * ***********************************************************************************************************************************
	 * ***********************************************************************************************************************************
	 * ***********************************************************************************************************************************
	 * ***********************************************************************************************************************************
	 * ****************************************************METHODS TO GET DATA TYPES******************************************************
	 * ***********************************************OR GO FROM DB NAME <---> DISPLAY NAME***********************************************
	 * ***********************************************************************************************************************************
	 * ***********************************************************************************************************************************
	 * ***********************************************************************************************************************************
	 * ===================================================================================================================================
	 */
	/**
	 * Get the display name of an entity by giving its database variable name
	 * @param entityDatabaseVariable the name of the field's database variable
	 * @return the display name of the entity
	 */
	public static String getEntityDisplayNameByDatabaseVariable(String entityDatabaseVariable)
	{
		return entityDisplayNameByDBNameMap.get(entityDatabaseVariable);
	}
	
	/**
	 * Get the data type of a field by giving its database variable name
	 * @param fieldDatabaseVariable the name of the field's database variable
	 * @return the data type of the field
	 */
	public static String getFieldDataTypeByDatabaseVariable(String fieldDatabaseVariable)
	{
		return dataTypeByFieldDBNameMap.get(fieldDatabaseVariable);
	}
	
	/**
	 * Get the display name of a field by giving its database variable name
	 * @param fieldDatabaseVariable the name of the field's database variable
	 * @return the display name of the field
	 */
	public static String getFieldDisplayNameByDatabaseVariable(String fieldDatabaseVariable)
	{
		return fieldDisplayNameByDBNameMap.get(fieldDatabaseVariable);
	}
	
	/**
	 * Get the data type of a field by giving its display name
	 * @param fieldDisplayName the display name of the field
	 * @return the data type of the field
	 */
	public static String getFieldDataTypeByDisplayName(String fieldDisplayName)
	{
		return fieldDisplayNameToDataTypeMap.get(fieldDisplayName);
	}
	
	/**
	 * Get the database variable name for a field by giving its display name
	 * @param fieldDisplayName the display name of the field
	 * @return the database variable name for the field
	 */
	public static String getFieldDatabaseVariableFieldByDisplayName(String fieldDisplayName)
	{
		return fieldDBNameByDisplayNameMap.get(fieldDisplayName);
	}
	
	/**
	 * Get the database variable for an entity by giving its display name
	 * @param entityDisplayName the display name of the entity
	 * @return the database variable name for the entity
	 */
	public static String getEntityDatabaseVariableByDisplayName(String entityDisplayName)
	{
		return entityDBNameByDisplayNameMap.get(entityDisplayName);
	}
	
	//methods for getting arrays of display names
	/*
	 * ===================================================================================================================================
	 * ***********************************************************************************************************************************
	 * ***********************************************************************************************************************************
	 * ***********************************************************************************************************************************
	 * ***********************************************************************************************************************************
	 * ****************************************************GETTERS AND SETTERS************************************************************
	 * ***********************************************************************************************************************************
	 * ***********************************************************************************************************************************
	 * ***********************************************************************************************************************************
	 * ***********************************************************************************************************************************
	 * ===================================================================================================================================
	 */
	
	/*
	 * =======================================================================================================
	 * *******************************************************************************************************
	 * **************************************GET ARRAYS OF NAMES**********************************************
	 * *******************************************************************************************************
	 * =======================================================================================================
	 */
	
	/**
	 * Getter for all entity displaynames.
	 * @return an array of Strings, where each string is an entity's display name
	 */
	public static String[] getEntityDisplayNames(){
		return ENTITY_DISPLAYNAMES;
	}
	
	/**
	 * Getter for all item field displaynames.
	 * @return an array of Strings, where each string is an item field's display name
	 */
	public static String[] getAllItemFieldDisplayNames(){
		return ITEM_FIELD_DISPLAYNAMES;
	}

	public static String[] getEntityDisplaynames() {
		return ENTITY_DISPLAYNAMES;
	}

	/**
	 * @return the warehouseFieldDisplaynames
	 */
	public static String[] getWarehouseFieldDisplaynames() {
		return WAREHOUSE_FIELD_DISPLAYNAMES;
	}

	/**
	 * @return the warehouseDbFields
	 */
	public static String[] getWarehouseDbFields() {
		return WAREHOUSE_DB_FIELDS;
	}

	/**
	 * @return the sublocationFieldDisplaynames
	 */
	public static String[] getSublocationFieldDisplaynames() {
		return SUBLOCATION_FIELD_DISPLAYNAMES;
	}

	/**
	 * @return the sublocationDbFields
	 */
	public static String[] getSublocationDbFields() {
		return SUBLOCATION_DB_FIELDS;
	}
	
	
	/*
	 * =======================================================================================================
	 * *******************************************************************************************************
	 * *********************************GET INDIVIDUAL CONSTANTS*******************************************
	 * *******************************************************************************************************
	 * =======================================================================================================
	 */
	
	public static String getItemEntityDisplayname() {
		return ITEM_ENTITY_DISPLAYNAME;
	}

	public static String getPalletEntityDisplayname() {
		return PALLET_ENTITY_DISPLAYNAME;
	}

	public static String getOrderEntityDisplayname() {
		return ORDER_ENTITY_DISPLAYNAME;
	}
	
	public static String getItemNameFieldDisplayname() {
		return ITEM_NAME_FIELD_DISPLAYNAME;
	}

	public static String getItemNumberFieldDisplayname() {
		return ITEM_NUMBER_FIELD_DISPLAYNAME;
	}

	public static String getItemPriceFieldDisplayname() {
		return ITEM_PRICE_FIELD_DISPLAYNAME;
	}

	public static String getItemWeightFieldDisplayname() {
		return ITEM_WEIGHT_FIELD_DISPLAYNAME;
	}

	public static String getItemCurrStockFieldDisplayname() {
		return ITEM_CURR_STOCK_FIELD_DISPLAYNAME;
	}

	public static String getItemRestockFieldDisplayname() {
		return ITEM_RESTOCK_FIELD_DISPLAYNAME;
	}

	public static String[] getItemFieldDisplaynames() {
		return ITEM_FIELD_DISPLAYNAMES;
	}

	public static String getPalletIdFieldDisplayname() {
		return PALLET_ID_FIELD_DISPLAYNAME;
	}

	public static String getPalletOrderNumFieldDisplayname() {
		return PALLET_ORDER_NUM_FIELD_DISPLAYNAME;
	}

	public static String getPalletLocFieldDisplayname() {
		return PALLET_LOC_FIELD_DISPLAYNAME;
	}

	public static String getPalletPieceCountFieldDisplayname() {
		return PALLET_PIECE_COUNT_FIELD_DISPLAYNAME;
	}

	public static String getPalletWeightFieldDisplayname() {
		return PALLET_WEIGHT_FIELD_DISPLAYNAME;
	}

	public static String getPalletLengthFieldDisplayname() {
		return PALLET_LENGTH_FIELD_DISPLAYNAME;
	}

	public static String getPalletWidthFieldDisplayname() {
		return PALLET_WIDTH_FIELD_DISPLAYNAME;
	}

	public static String getPalletHeightFieldDisplayname() {
		return PALLET_HEIGHT_FIELD_DISPLAYNAME;
	}

	public static String getPalletReceiveDateFieldDisplayname() {
		return PALLET_RECEIVE_DATE_FIELD_DISPLAYNAME;
	}

	public static String getPalletShipDateFieldDisplayname() {
		return PALLET_SHIP_DATE_FIELD_DISPLAYNAME;
	}

	public static String getPalletNotesFieldDisplayname() {
		return PALLET_NOTES_FIELD_DISPLAYNAME;
	}

	public static String getOrderNumFieldDisplayname() {
		return ORDER_NUM_FIELD_DISPLAYNAME;
	}

	public static String getOrderOriginFieldDisplayname() {
		return ORDER_ORIGIN_FIELD_DISPLAYNAME;
	}

	public static String getOrderDestFieldDisplayname() {
		return ORDER_DEST_FIELD_DISPLAYNAME;
	}

	public static String getOrderDatePlacedFieldDisplayname() {
		return ORDER_DATE_PLACED_FIELD_DISPLAYNAME;
	}

	public static String getOrderDateShippedFieldDisplayname() {
		return ORDER_DATE_SHIPPED_FIELD_DISPLAYNAME;
	}

	public static String getOrderReceivingEmployeeFieldDisplayname() {
		return ORDER_RECEIVING_EMPLOYEE_FIELD_DISPLAYNAME;
	}

	public static String getOrderShippingEmployeeFieldDisplayname() {
		return ORDER_SHIPPING_EMPLOYEE_FIELD_DISPLAYNAME;
	}

	

	public static String getItemDbEntityName() {
		return ITEM_DB_ENTITY_NAME;
	}

	public static String getPalletDbEntityName() {
		return PALLET_DB_ENTITY_NAME;
	}

	public static String getOrderDbEntityName() {
		return ORDER_DB_ENTITY_NAME;
	}

	public static String getItemNameDbField() {
		return ITEM_NAME_DB_FIELD;
	}

	public static String getItemNumberDbField() {
		return ITEM_NUMBER_DB_FIELD;
	}

	public static String getItemPriceDbField() {
		return ITEM_PRICE_DB_FIELD;
	}

	public static String getItemWeightDbField() {
		return ITEM_WEIGHT_DB_FIELD;
	}

	public static String getItemCurrStockDbField() {
		return ITEM_CURR_STOCK_DB_FIELD;
	}

	public static String getItemRestockDbField() {
		return ITEM_RESTOCK_DB_FIELD;
	}

	public static String getPalletIdDbField() {
		return PALLET_ID_DB_FIELD;
	}

	public static String getPalletOrderNumDbField() {
		return PALLET_ORDER_NUM_DB_FIELD;
	}

	public static String getPalletLocDbField() {
		return PALLET_LOC_DB_FIELD;
	}

	public static String getPalletPieceCountDbField() {
		return PALLET_PIECE_COUNT_DB_FIELD;
	}

	public static String getPalletWeightDbField() {
		return PALLET_WEIGHT_DB_FIELD;
	}

	public static String getPalletLengthDbField() {
		return PALLET_LENGTH_DB_FIELD;
	}

	public static String getPalletWidthDbField() {
		return PALLET_WIDTH_DB_FIELD;
	}

	public static String getPalletHeightDbField() {
		return PALLET_HEIGHT_DB_FIELD;
	}

	public static String getPalletReceiveDateDbField() {
		return PALLET_RECEIVE_DATE_DB_FIELD;
	}

	public static String getPalletShipDateDbField() {
		return PALLET_SHIP_DATE_DB_FIELD;
	}

	public static String getPalletNotesDbField() {
		return PALLET_NOTES_DB_FIELD;
	}

	public static String getOrderNumDbField() {
		return ORDER_NUM_DB_FIELD;
	}

	public static String getOrderOriginDbField() {
		return ORDER_ORIGIN_DB_FIELD;
	}

	public static String getOrderDestDbField() {
		return ORDER_DEST_DB_FIELD;
	}

	public static String getOrderDatePlacedDbField() {
		return ORDER_DATE_PLACED_DB_FIELD;
	}

	public static String getOrderDateShippedDbField() {
		return ORDER_DATE_SHIPPED_DB_FIELD;
	}

	public static String getOrderReceivingEmployeeDbField() {
		return ORDER_RECEIVING_EMPLOYEE_DB_FIELD;
	}

	public static String getOrderShippingEmployeeDbField() {
		return ORDER_SHIPPING_EMPLOYEE_DB_FIELD;
	}

	public static String getNumericFieldTypeName() {
		return NUMERIC_FIELD_TYPE_NAME;
	}

	public static String getNumericFieldGreaterThan() {
		return NUMERIC_FIELD_GREATER_THAN;
	}

	public static String getNumericFieldLessThan() {
		return NUMERIC_FIELD_LESS_THAN;
	}

	public static String getNumericFieldEqualTo() {
		return NUMERIC_FIELD_EQUAL_TO;
	}

	public static String getStringFieldTypeName() {
		return STRING_FIELD_TYPE_NAME;
	}

	public static String getStringFieldStartingWith() {
		return STRING_FIELD_STARTING_WITH;
	}

	public static String getStringFieldEndingWith() {
		return STRING_FIELD_ENDING_WITH;
	}

	public static String getStringFieldContains() {
		return STRING_FIELD_CONTAINS;
	}

	public static String getStringFieldThatIs() {
		return STRING_FIELD_THAT_IS;
	}

	public static String getDateFieldTypeName() {
		return DATE_FIELD_TYPE_NAME;
	}

	public static String getDateFieldBefore() {
		return DATE_FIELD_BEFORE;
	}

	public static String getDateFieldAfter() {
		return DATE_FIELD_AFTER;
	}

	public static String getDateFieldOn() {
		return DATE_FIELD_ON;
	}
	
	public static String getDefaultFieldModifierValue() {
		return DEFAULT_FIELD_MODIFIER_VALUE;
	}

	/**
	 * Getter for all pallet field displaynames.
	 * @return an array of Strings, where each string is an pallet field's display name
	 */
	public static String[] getAllPalletFieldDisplayNames(){
		return PALLET_FIELD_DISPLAYNAMES;
	}
	
	/**
	 * Getter for all order field displaynames.
	 * @return an array of Strings, where each string is an order field's display name
	 */
	public static String[] getAllOrderFieldDisplayNames(){
		return ORDER_FIELD_DISPLAYNAMES;
	}
	
	//methods for getting arrays of database variable names
	/**
	 * Getter for all entity database variable names
	 * @return an array of Strings, where each string is an entity's database variable name
	 */
	public static String[] getEntityDBNames(){
		return ALL_DB_ENTITY_NAMES;
	}
	
	/**
	 * Getter for all pallet field database variable names
	 * @return an array of Strings, where each string is a pallet field's database variable name
	 */
	public static String[] getAllPalletFieldDBNames(){
		return PALLET_DB_FIELDS;
	}
	
	/**
	 * Getter for all item field database variable names
	 * @return an array of Strings, where each string is an item field's database variable name
	 */
	public static String[] getAllItemFieldDBNames(){
		return ITEM_DB_FIELDS;
	}
	
	/**
	 * Getter for all order field database variable names
	 * @return an array of Strings, where each string is an order field's database variable name
	 */
	public static String[] getAllOrderFieldDBNames(){
		return ORDER_DB_FIELDS;
	}

	//methods for getting arrays of data type modifier strings
	/**
	 * Getter for all numeric field modifier strings.
	 * @return an array of Strings, containing all of the numeric field modifier strings
	 */
	public static String[] getAllNumericFieldModifierStrings(){
		return NUMERIC_FIELD_MODIFIER_STRINGS;
	}
	
	/**
	 * @return the warehouseFieldDisplaynames
	 */
	public static String[] getAllWarehouseFieldDisplayNames() {
		return WAREHOUSE_FIELD_DISPLAYNAMES;
	}
	
	/**
	 * @return the warehouseDbFields
	 */
	public static String[] getAllWarehouseFieldDBNames() {
		return WAREHOUSE_DB_FIELDS;
	}
	
	/**
	 * @return the warehouseFieldDisplaynames
	 */
	public static String[] getAllSublocationFieldDisplayNames() {
		return SUBLOCATION_FIELD_DISPLAYNAMES;
	}
	
	/**
	 * @return the warehouseDbFields
	 */
	public static String[] getAllSublocationFieldDBNames() {
		return SUBLOCATION_DB_FIELDS;
	}
	
	public static String[] getAllEmployeeFieldDisplayNames(){
		return EMPLOYEE_FIELD_DISPLAYNAMES;
	}
	
	public static String[] getAllEmployeeFieldDBNames(){
		return EMPLOYEE_DB_FIELDS;
	}
	
	/**
	 * @return the allDbEntityNames
	 */
	public static String[] getAllDbEntityNames() {
		return ALL_DB_ENTITY_NAMES;
	}

	/**
	 * @return the itemDbFields
	 */
	public static String[] getItemDbFields() {
		return ITEM_DB_FIELDS;
	}

	/**
	 * @return the palletFieldDisplaynames
	 */
	public static String[] getPalletFieldDisplaynames() {
		return PALLET_FIELD_DISPLAYNAMES;
	}

	/**
	 * @return the palletDbFields
	 */
	public static String[] getPalletDbFields() {
		return PALLET_DB_FIELDS;
	}

	/**
	 * @return the orderFieldDisplaynames
	 */
	public static String[] getOrderFieldDisplaynames() {
		return ORDER_FIELD_DISPLAYNAMES;
	}

	/**
	 * @return the orderDbFields
	 */
	public static String[] getOrderDbFields() {
		return ORDER_DB_FIELDS;
	}

	/**
	 * @return the numericFieldModifierStrings
	 */
	public static String[] getNumericFieldModifierStrings() {
		return NUMERIC_FIELD_MODIFIER_STRINGS;
	}

	/**
	 * @return the stringFieldModifierStrings
	 */
	public static String[] getStringFieldModifierStrings() {
		return STRING_FIELD_MODIFIER_STRINGS;
	}

	/**
	 * @return the dateFieldModifierStrings
	 */
	public static String[] getDateFieldModifierStrings() {
		return DATE_FIELD_MODIFIER_STRINGS;
	}

	/**
	 * Getter for all string field modifier strings.
	 * @return an array of Strings, containing all of the string field modifier strings
	 */
	public static String[] getAllStringFieldModifierStrings(){
		return STRING_FIELD_MODIFIER_STRINGS;
	}

	/**
	 * Getter for all date field modifier strings.
	 * @return an array of Strings, containing all of the date field modifier strings
	 */
	public static String[] getAllDateFieldModifierStrings(){
		return DATE_FIELD_MODIFIER_STRINGS;
	}

	/**
	 * @return the warehouseEntityDisplayname
	 */
	public static String getWarehouseEntityDisplayname() {
		return WAREHOUSE_ENTITY_DISPLAYNAME;
	}

	/**
	 * @return the sublocationEntityDisplayname
	 */
	public static String getSublocationEntityDisplayname() {
		return SUBLOCATION_ENTITY_DISPLAYNAME;
	}

	/**
	 * @return the warehouseDbEntityName
	 */
	public static String getWarehouseDbEntityName() {
		return WAREHOUSE_DB_ENTITY_NAME;
	}

	/**
	 * @return the sublocationDbEntityName
	 */
	public static String getSublocationDbEntityName() {
		return SUBLOCATION_DB_ENTITY_NAME;
	}

	/**
	 * @return the itemSimpleIndexDisplayname
	 */
	public static String getItemSimpleIndexDisplayname() {
		return ITEM_SIMPLE_INDEX_DISPLAYNAME;
	}

	/**
	 * @return the itemSimpleIndexDbField
	 */
	public static String getItemSimpleIndexDbField() {
		return ITEM_SIMPLE_INDEX_DB_FIELD;
	}

	/**
	 * @return the palletSimpleIndexDisplayname
	 */
	public static String getPalletSimpleIndexDisplayname() {
		return PALLET_SIMPLE_INDEX_DISPLAYNAME;
	}

	/**
	 * @return the palletSimpleIndexDbField
	 */
	public static String getPalletSimpleIndexDbField() {
		return PALLET_SIMPLE_INDEX_DB_FIELD;
	}

	/**
	 * @return the orderSimpleIndexDisplayname
	 */
	public static String getOrderSimpleIndexDisplayname() {
		return ORDER_SIMPLE_INDEX_DISPLAYNAME;
	}

	/**
	 * @return the orderSimpleIndexDbField
	 */
	public static String getOrderSimpleIndexDbField() {
		return ORDER_SIMPLE_INDEX_DB_FIELD;
	}

	/**
	 * @return the warehouseSimpleIndexDisplayname
	 */
	public static String getWarehouseSimpleIndexDisplayname() {
		return WAREHOUSE_SIMPLE_INDEX_DISPLAYNAME;
	}

	/**
	 * @return the warehouseCityFieldDisplayname
	 */
	public static String getWarehouseCityFieldDisplayname() {
		return WAREHOUSE_CITY_FIELD_DISPLAYNAME;
	}

	/**
	 * @return the warehouseStateFieldDisplayname
	 */
	public static String getWarehouseStateFieldDisplayname() {
		return WAREHOUSE_STATE_FIELD_DISPLAYNAME;
	}

	/**
	 * @return the warehouseStreetAddressFieldDisplayname
	 */
	public static String getWarehouseStreetAddressFieldDisplayname() {
		return WAREHOUSE_STREET_ADDRESS_FIELD_DISPLAYNAME;
	}

	/**
	 * @return the warehouseZipFieldDisplayname
	 */
	public static String getWarehouseZipFieldDisplayname() {
		return WAREHOUSE_ZIP_FIELD_DISPLAYNAME;
	}

	/**
	 * @return the warehouseNameFieldDisplayname
	 */
	public static String getWarehouseNameFieldDisplayname() {
		return WAREHOUSE_NAME_FIELD_DISPLAYNAME;
	}

	/**
	 * @return the warehousePhoneNumberFieldDisplayname
	 */
	public static String getWarehousePhoneNumberFieldDisplayname() {
		return WAREHOUSE_PHONE_NUMBER_FIELD_DISPLAYNAME;
	}

	/**
	 * @return the warehouseEmailFieldDisplayname
	 */
	public static String getWarehouseEmailFieldDisplayname() {
		return WAREHOUSE_EMAIL_FIELD_DISPLAYNAME;
	}

	/**
	 * @return the warehouseSimpleIndexDbField
	 */
	public static String getWarehouseSimpleIndexDbField() {
		return WAREHOUSE_SIMPLE_INDEX_DB_FIELD;
	}

	/**
	 * @return the warehouseCityDbField
	 */
	public static String getWarehouseCityDbField() {
		return WAREHOUSE_CITY_DB_FIELD;
	}

	/**
	 * @return the warehouseStateDbField
	 */
	public static String getWarehouseStateDbField() {
		return WAREHOUSE_STATE_DB_FIELD;
	}

	/**
	 * @return the warehouseStreetAddressDbField
	 */
	public static String getWarehouseStreetAddressDbField() {
		return WAREHOUSE_STREET_ADDRESS_DB_FIELD;
	}

	/**
	 * @return the warehouseZipDbField
	 */
	public static String getWarehouseZipDbField() {
		return WAREHOUSE_ZIP_DB_FIELD;
	}

	/**
	 * @return the warehouseNameDbField
	 */
	public static String getWarehouseNameDbField() {
		return WAREHOUSE_NAME_DB_FIELD;
	}

	/**
	 * @return the warehousePhoneNumberDbField
	 */
	public static String getWarehousePhoneNumberDbField() {
		return WAREHOUSE_PHONE_NUMBER_DB_FIELD;
	}

	/**
	 * @return the warehouseEmailDbField
	 */
	public static String getWarehouseEmailDbField() {
		return WAREHOUSE_EMAIL_DB_FIELD;
	}

	/**
	 * @return the sublocationSimpleIndexDisplayname
	 */
	public static String getSublocationSimpleIndexDisplayname() {
		return SUBLOCATION_SIMPLE_INDEX_DISPLAYNAME;
	}

	/**
	 * @return the sublocationLocCoordDisplayname
	 */
	public static String getSublocationLocCoordDisplayname() {
		return SUBLOCATION_LOC_COORD_DISPLAYNAME;
	}

	/**
	 * @return the sublocationMaxPalletQtyDisplayname
	 */
	public static String getSublocationMaxPalletQtyDisplayname() {
		return SUBLOCATION_MAX_PALLET_QTY_DISPLAYNAME;
	}

	/**
	 * @return the sublocationCurrentPalletQtyDisplayname
	 */
	public static String getSublocationCurrentPalletQtyDisplayname() {
		return SUBLOCATION_CURRENT_PALLET_QTY_DISPLAYNAME;
	}

	/**
	 * @return the sublocationWarehouseIdDisplayname
	 */
	public static String getSublocationWarehouseIdDisplayname() {
		return SUBLOCATION_WAREHOUSE_ID_DISPLAYNAME;
	}

	/**
	 * @return the sublocationSimpleIndexDbField
	 */
	public static String getSublocationSimpleIndexDbField() {
		return SUBLOCATION_SIMPLE_INDEX_DB_FIELD;
	}

	/**
	 * @return the sublocationLocCoordDbField
	 */
	public static String getSublocationLocCoordDbField() {
		return SUBLOCATION_LOC_COORD_DB_FIELD;
	}

	/**
	 * @return the sublocationMaxPalletQtyDbField
	 */
	public static String getSublocationMaxPalletQtyDbField() {
		return SUBLOCATION_MAX_PALLET_QTY_DB_FIELD;
	}

	/**
	 * @return the sublocationCurrentPalletQtyDbField
	 */
	public static String getSublocationCurrentPalletQtyDbField() {
		return SUBLOCATION_CURRENT_PALLET_QTY_DB_FIELD;
	}

	/**
	 * @return the sublocationWarehouseIdDbField
	 */
	public static String getSublocationWarehouseIdDbField() {
		return SUBLOCATION_WAREHOUSE_ID_DB_FIELD;
	}
	
	/**
	 * @return the employeeEntityDisplayname
	 */
	public static String getEmployeeEntityDisplayname() {
		return EMPLOYEE_ENTITY_DISPLAYNAME;
	}

	/**
	 * @return the employeeDbEntityName
	 */
	public static String getEmployeeDbEntityName() {
		return EMPLOYEE_DB_ENTITY_NAME;
	}

	/**
	 * @return the warehouseIdFieldDisplayname
	 */
	public static String getWarehouseIdFieldDisplayname() {
		return WAREHOUSE_ID_FIELD_DISPLAYNAME;
	}

	/**
	 * @return the employeeSimpleIndexDisplayname
	 */
	public static String getEmployeeSimpleIndexDisplayname() {
		return EMPLOYEE_SIMPLE_INDEX_DISPLAYNAME;
	}

	/**
	 * @return the employeeIdDisplayname
	 */
	public static String getEmployeeIdDisplayname() {
		return EMPLOYEE_ID_DISPLAYNAME;
	}

	/**
	 * @return the employeeNameDisplayname
	 */
	public static String getEmployeeNameDisplayname() {
		return EMPLOYEE_NAME_DISPLAYNAME;
	}

	/**
	 * @return the employeeIsManagerDisplayname
	 */
	public static String getEmployeeIsManagerDisplayname() {
		return EMPLOYEE_IS_MANAGER_DISPLAYNAME;
	}

	/**
	 * @return the employeePasswordDisplayname
	 */
	public static String getEmployeePasswordDisplayname() {
		return EMPLOYEE_PASSWORD_DISPLAYNAME;
	}

	/**
	 * @return the employeeWarehouseIdDisplayname
	 */
	public static String getEmployeeWarehouseIdDisplayname() {
		return EMPLOYEE_WAREHOUSE_ID_DISPLAYNAME;
	}

	

	/**
	 * @return the employeeSimpleIndexDbField
	 */
	public static String getEmployeeSimpleIndexDbField() {
		return EMPLOYEE_SIMPLE_INDEX_DB_FIELD;
	}

	/**
	 * @return the employeeIdDbField
	 */
	public static String getEmployeeIdDbField() {
		return EMPLOYEE_ID_DB_FIELD;
	}

	/**
	 * @return the employeeNameDbField
	 */
	public static String getEmployeeNameDbField() {
		return EMPLOYEE_NAME_DB_FIELD;
	}

	/**
	 * @return the employeeIsManagerDbField
	 */
	public static String getEmployeeIsManagerDbField() {
		return EMPLOYEE_IS_MANAGER_DB_FIELD;
	}

	/**
	 * @return the employeePasswordDbField
	 */
	public static String getEmployeePasswordDbField() {
		return EMPLOYEE_PASSWORD_DB_FIELD;
	}

	/**
	 * @return the employeeWarehouseIdDbField
	 */
	public static String getEmployeeWarehouseIdDbField() {
		return EMPLOYEE_WAREHOUSE_ID_DB_FIELD;
	}

	/**
	 * @return the flagFieldTypeName
	 */
	public static String getFlagFieldTypeName() {
		return FLAG_FIELD_TYPE_NAME;
	}

	/**
	 * @return the flagFieldIs
	 */
	public static String getFlagFieldIs() {
		return FLAG_FIELD_IS;
	}

	/**
	 * @return the flagFieldModifierStrings
	 */
	public static String[] getFlagFieldModifierStrings() {
		return FLAG_FIELD_MODIFIER_STRINGS;
	}
}
