package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class DBNamesManager {
	
	//All of the entity type names for the entity type combobox
	public static final String ITEM_ENTITY_DISPLAYNAME = "Items";
	public static final String PALLET_ENTITY_DISPLAYNAME = "Pallets";
	public static final String ORDER_ENTITY_DISPLAYNAME = "Orders";
	public static final String ALL_ENTITY_SPECIFIER_DISPLAYNAME = "All warehouse entities";
	public static final String[] ENTITY_DISPLAYNAMES = {ITEM_ENTITY_DISPLAYNAME, PALLET_ENTITY_DISPLAYNAME, ORDER_ENTITY_DISPLAYNAME, ALL_ENTITY_SPECIFIER_DISPLAYNAME};
	
	//All of the Item fields
	public static final String ITEM_NAME_FIELD_DISPLAYNAME = "Name";
	public static final String ITEM_NUMBER_FIELD_DISPLAYNAME = "Item Number";
	public static final String ITEM_PRICE_FIELD_DISPLAYNAME = "Price";
	public static final String ITEM_WEIGHT_FIELD_DISPLAYNAME = "Weight";
	public static final String ITEM_CURR_STOCK_FIELD_DISPLAYNAME = "Current Stock";
	public static final String ITEM_RESTOCK_FIELD_DISPLAYNAME = "Restock Threshold";
	//TODO handle item categories--get from SQL handler as 1 string with commas, display in one column
	//Item fields for the fields combobox
	public static final String[] ITEM_FIELD_DISPLAYNAMES = {ITEM_NAME_FIELD_DISPLAYNAME, ITEM_NUMBER_FIELD_DISPLAYNAME, ITEM_PRICE_FIELD_DISPLAYNAME,
													ITEM_WEIGHT_FIELD_DISPLAYNAME, ITEM_CURR_STOCK_FIELD_DISPLAYNAME, ITEM_RESTOCK_FIELD_DISPLAYNAME};
	
	//All of the Pallet fields
	public static final String PALLET_ID_FIELD_DISPLAYNAME = "Pallet ID";
	public static final String PALLET_ORDER_NUM_FIELD_DISPLAYNAME = "Order Number";
	public static final String PALLET_LOC_FIELD_DISPLAYNAME = "Location";
	public static final String PALLET_PIECE_COUNT_FIELD_DISPLAYNAME = "Piece Count";
	public static final String PALLET_WEIGHT_FIELD_DISPLAYNAME = "Weight";
	public static final String PALLET_LENGTH_FIELD_DISPLAYNAME = "Length";
	public static final String PALLET_WIDTH_FIELD_DISPLAYNAME = "Width";
	public static final String PALLET_HEIGHT_FIELD_DISPLAYNAME = "Height";
	public static final String PALLET_RECEIVE_DATE_FIELD_DISPLAYNAME = "Receival Date";
	public static final String PALLET_SHIP_DATE_FIELD_DISPLAYNAME = "Ship Date";
	public static final String PALLET_NOTES_FIELD_DISPLAYNAME = "Notes";
	//Pallet fields for the fields combobox
	public static final String[] PALLET_FIELD_DISPLAYNAMES = {PALLET_ID_FIELD_DISPLAYNAME, PALLET_ORDER_NUM_FIELD_DISPLAYNAME, PALLET_LOC_FIELD_DISPLAYNAME,
									PALLET_PIECE_COUNT_FIELD_DISPLAYNAME, PALLET_WEIGHT_FIELD_DISPLAYNAME, PALLET_LENGTH_FIELD_DISPLAYNAME, PALLET_WIDTH_FIELD_DISPLAYNAME, 
									PALLET_HEIGHT_FIELD_DISPLAYNAME, PALLET_RECEIVE_DATE_FIELD_DISPLAYNAME, PALLET_SHIP_DATE_FIELD_DISPLAYNAME, PALLET_NOTES_FIELD_DISPLAYNAME};
	
	
	//All of the Order fields
	public static final String ORDER_NUM_FIELD_DISPLAYNAME = "Order Number";
	public static final String ORDER_ORIGIN_FIELD_DISPLAYNAME = "Origin";
	public static final String ORDER_DEST_FIELD_DISPLAYNAME = "Destination";
	public static final String ORDER_DATE_PLACED_FIELD_DISPLAYNAME = "Date Placed";
	public static final String ORDER_DATE_SHIPPED_FIELD_DISPLAYNAME = "Date Shipped";
	public static final String ORDER_RECEIVING_EMPLOYEE_FIELD_DISPLAYNAME = "Employee Received By";
	public static final String ORDER_SHIPPING_EMPLOYEE_FIELD_DISPLAYNAME = "Employee Shipped By";
	//Order fields for the fields combobox
	public static final String[] ORDER_FIELD_DISPLAYNAMES = {ORDER_NUM_FIELD_DISPLAYNAME, ORDER_ORIGIN_FIELD_DISPLAYNAME, ORDER_DEST_FIELD_DISPLAYNAME, ORDER_DATE_PLACED_FIELD_DISPLAYNAME,
													ORDER_DATE_SHIPPED_FIELD_DISPLAYNAME, ORDER_RECEIVING_EMPLOYEE_FIELD_DISPLAYNAME, 
													ORDER_SHIPPING_EMPLOYEE_FIELD_DISPLAYNAME};
	
	//All of the entity type names for the entity type combobox
	public static final String ITEM_DB_ENTITY_NAME = "items";
	public static final String PALLET_DB_ENTITY_NAME = "pallets";
	public static final String ORDER_DB_ENTITY_NAME = "orders";
	public static final String[] ALL_DB_ENTITY_NAMES = {ITEM_DB_ENTITY_NAME, 
		PALLET_DB_ENTITY_NAME, ORDER_DB_ENTITY_NAME};
	
	//All of the Item database fields
	public static final String ITEM_NAME_DB_FIELD = "name";
	public static final String ITEM_NUMBER_DB_FIELD = "item_number";
	public static final String ITEM_PRICE_DB_FIELD = "price";
	public static final String ITEM_WEIGHT_DB_FIELD = "weight";
	public static final String ITEM_CURR_STOCK_DB_FIELD = "current_stock";
	public static final String ITEM_RESTOCK_DB_FIELD = "restock_threshold";
	public static final String[] ITEM_DB_FIELDS = {ITEM_NAME_DB_FIELD, ITEM_NUMBER_DB_FIELD, ITEM_PRICE_DB_FIELD,
		ITEM_WEIGHT_DB_FIELD, ITEM_CURR_STOCK_DB_FIELD, ITEM_RESTOCK_DB_FIELD};
	
	
	//All of the Pallet database fields
	public static final String PALLET_ID_DB_FIELD = "pallet_id";
	public static final String PALLET_ORDER_NUM_DB_FIELD = "order_number";
	public static final String PALLET_LOC_DB_FIELD = "location_coordinate";
	public static final String PALLET_PIECE_COUNT_DB_FIELD = "piece_count";
	public static final String PALLET_WEIGHT_DB_FIELD = "weight";
	public static final String PALLET_LENGTH_DB_FIELD = "length";
	public static final String PALLET_WIDTH_DB_FIELD = "width";
	public static final String PALLET_HEIGHT_DB_FIELD = "height";
	public static final String PALLET_RECEIVE_DATE_DB_FIELD = "receival_date";
	public static final String PALLET_SHIP_DATE_DB_FIELD = "ship_date";
	public static final String PALLET_NOTES_DB_FIELD = "notes";
	public static final String[] PALLET_DB_FIELDS = {PALLET_ID_DB_FIELD, PALLET_ORDER_NUM_DB_FIELD, PALLET_LOC_DB_FIELD,
		PALLET_PIECE_COUNT_DB_FIELD, PALLET_WEIGHT_DB_FIELD, PALLET_LENGTH_DB_FIELD, PALLET_WIDTH_DB_FIELD, 
		PALLET_HEIGHT_DB_FIELD, PALLET_RECEIVE_DATE_DB_FIELD, PALLET_SHIP_DATE_DB_FIELD, PALLET_NOTES_DB_FIELD};

	
	//All of the Order database fields
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
	
	//the default value for the field modifier components
	public static final String DEFAULT_FIELD_MODIFIER_VALUE = "";
	
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

	/**
	 * =======================================================================================================
	 * *******************************************************************************************************
	 * *******************************KEY: DATABASE-SIDE FIELD NAME	 *************************************
	 * *******************************VALUE: GUI-SIDE FIELD DISPLAYNAME *************************************
	 * =======================================================================================================
	 */
	private static HashMap<String, String> buildFieldDisplayNameByDBNameMap() {
		HashMap<String, String> buildFieldDBNameToDisplayNameMap = new HashMap<String, String>();
		addItemFieldDisplayNamesToMapByDBName(buildFieldDBNameToDisplayNameMap);
		addPalletFieldDisplayNamesToMapByDBName(buildFieldDBNameToDisplayNameMap);
		addOrderFieldDisplayNamesToMapByDBName(buildFieldDBNameToDisplayNameMap);
		return buildFieldDBNameToDisplayNameMap;
	}
	
	private static void addItemFieldDisplayNamesToMapByDBName(HashMap<String, String> buildFieldDBNameToDisplayNameNameMap) {
		buildFieldDBNameToDisplayNameNameMap.put(ITEM_NAME_DB_FIELD, ITEM_NAME_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameNameMap.put(ITEM_PRICE_DB_FIELD, ITEM_PRICE_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameNameMap.put(ITEM_WEIGHT_DB_FIELD, ITEM_WEIGHT_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameNameMap.put(ITEM_CURR_STOCK_DB_FIELD, ITEM_CURR_STOCK_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameNameMap.put(ITEM_RESTOCK_DB_FIELD, ITEM_RESTOCK_FIELD_DISPLAYNAME);

		buildFieldDBNameToDisplayNameNameMap.put(ITEM_NAME_DB_FIELD, ITEM_NAME_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameNameMap.put(ITEM_NUMBER_DB_FIELD, ITEM_NUMBER_FIELD_DISPLAYNAME);
	}
	
	private static void addPalletFieldDisplayNamesToMapByDBName(HashMap<String, String> buildFieldDBNameToDisplayNameMap) 
	{
		buildFieldDBNameToDisplayNameMap.put(PALLET_PIECE_COUNT_DB_FIELD, PALLET_PIECE_COUNT_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(PALLET_WEIGHT_DB_FIELD, PALLET_WEIGHT_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(PALLET_LENGTH_DB_FIELD, PALLET_LENGTH_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(PALLET_WIDTH_DB_FIELD, PALLET_WIDTH_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(PALLET_HEIGHT_DB_FIELD, PALLET_HEIGHT_FIELD_DISPLAYNAME);
		
		buildFieldDBNameToDisplayNameMap.put(PALLET_ID_DB_FIELD, PALLET_ID_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(PALLET_ORDER_NUM_DB_FIELD, PALLET_ORDER_NUM_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(PALLET_LOC_DB_FIELD, PALLET_LOC_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(PALLET_NOTES_DB_FIELD, PALLET_NOTES_FIELD_DISPLAYNAME);

		buildFieldDBNameToDisplayNameMap.put(PALLET_RECEIVE_DATE_DB_FIELD, PALLET_RECEIVE_DATE_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(PALLET_SHIP_DATE_DB_FIELD, PALLET_SHIP_DATE_FIELD_DISPLAYNAME);
	}
	
	private static void addOrderFieldDisplayNamesToMapByDBName(HashMap<String, String> buildFieldDBNameToDisplayNameMap) 
	{
		buildFieldDBNameToDisplayNameMap.put(ORDER_NUM_DB_FIELD, ORDER_NUM_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(ORDER_ORIGIN_DB_FIELD, ORDER_ORIGIN_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(ORDER_DEST_DB_FIELD, ORDER_DEST_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(ORDER_RECEIVING_EMPLOYEE_DB_FIELD, ORDER_RECEIVING_EMPLOYEE_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(ORDER_SHIPPING_EMPLOYEE_DB_FIELD, ORDER_SHIPPING_EMPLOYEE_FIELD_DISPLAYNAME);
		
		buildFieldDBNameToDisplayNameMap.put(ORDER_DATE_PLACED_DB_FIELD, ORDER_DATE_PLACED_FIELD_DISPLAYNAME);
		buildFieldDBNameToDisplayNameMap.put(ORDER_DATE_SHIPPED_DB_FIELD, ORDER_DATE_SHIPPED_FIELD_DISPLAYNAME);
	}
	
	/**
	 * =======================================================================================================
	 * *******************************************************************************************************
	 * *******************************KEY: DATABASE-SIDE FIELD NAME	 *************************************
	 * *******************************VALUE: GUI-SIDE FIELD DISPLAYNAME *************************************
	 * =======================================================================================================
	 */
	private static HashMap<String, String> buildFieldDBNameByDisplayNameMap() {
		HashMap<String, String> buildLocalFieldNameToDBFieldNameMap = new HashMap<String, String>();
		addItemFieldDBNamesToMapByDisplayName(buildLocalFieldNameToDBFieldNameMap);
		addPalletFieldDBNamesToMapByDisplayName(buildLocalFieldNameToDBFieldNameMap);
		addOrderFieldDBNamesToMapByDisplayName(buildLocalFieldNameToDBFieldNameMap);
		return buildLocalFieldNameToDBFieldNameMap;
	}
	
	private static void addItemFieldDBNamesToMapByDisplayName(HashMap<String, String> buildLocalFieldNameToDBFieldNameMap) {
		buildLocalFieldNameToDBFieldNameMap.put(ITEM_NAME_FIELD_DISPLAYNAME, ITEM_NAME_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(ITEM_PRICE_FIELD_DISPLAYNAME, ITEM_PRICE_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(ITEM_WEIGHT_FIELD_DISPLAYNAME, ITEM_WEIGHT_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(ITEM_CURR_STOCK_FIELD_DISPLAYNAME, ITEM_CURR_STOCK_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(ITEM_RESTOCK_FIELD_DISPLAYNAME, ITEM_RESTOCK_DB_FIELD);

		buildLocalFieldNameToDBFieldNameMap.put(ITEM_NAME_FIELD_DISPLAYNAME, ITEM_NAME_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(ITEM_NUMBER_FIELD_DISPLAYNAME, ITEM_NUMBER_DB_FIELD);
	}
	
	private static void addPalletFieldDBNamesToMapByDisplayName(HashMap<String, String> buildLocalFieldNameToDBFieldNameMap) 
	{
		buildLocalFieldNameToDBFieldNameMap.put(PALLET_PIECE_COUNT_FIELD_DISPLAYNAME, PALLET_PIECE_COUNT_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(PALLET_WEIGHT_FIELD_DISPLAYNAME, PALLET_WEIGHT_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(PALLET_LENGTH_FIELD_DISPLAYNAME, PALLET_LENGTH_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(PALLET_WIDTH_FIELD_DISPLAYNAME, PALLET_WIDTH_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(PALLET_HEIGHT_FIELD_DISPLAYNAME, PALLET_HEIGHT_DB_FIELD);
		
		buildLocalFieldNameToDBFieldNameMap.put(PALLET_ID_FIELD_DISPLAYNAME, PALLET_ID_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(PALLET_ORDER_NUM_FIELD_DISPLAYNAME, PALLET_ORDER_NUM_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(PALLET_LOC_FIELD_DISPLAYNAME, PALLET_LOC_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(PALLET_NOTES_FIELD_DISPLAYNAME, PALLET_NOTES_DB_FIELD);

		buildLocalFieldNameToDBFieldNameMap.put(PALLET_RECEIVE_DATE_FIELD_DISPLAYNAME, PALLET_RECEIVE_DATE_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(PALLET_SHIP_DATE_FIELD_DISPLAYNAME, PALLET_SHIP_DATE_DB_FIELD);
	}
	
	private static void addOrderFieldDBNamesToMapByDisplayName(HashMap<String, String> buildLocalFieldNameToDBFieldNameMap) 
	{
		buildLocalFieldNameToDBFieldNameMap.put(ORDER_NUM_FIELD_DISPLAYNAME, ORDER_NUM_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(ORDER_ORIGIN_FIELD_DISPLAYNAME, ORDER_ORIGIN_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(ORDER_DEST_FIELD_DISPLAYNAME, ORDER_DEST_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(ORDER_RECEIVING_EMPLOYEE_FIELD_DISPLAYNAME, ORDER_RECEIVING_EMPLOYEE_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(ORDER_SHIPPING_EMPLOYEE_FIELD_DISPLAYNAME, ORDER_SHIPPING_EMPLOYEE_DB_FIELD);
		
		buildLocalFieldNameToDBFieldNameMap.put(ORDER_DATE_PLACED_FIELD_DISPLAYNAME, ORDER_DATE_PLACED_DB_FIELD);
		buildLocalFieldNameToDBFieldNameMap.put(ORDER_DATE_SHIPPED_FIELD_DISPLAYNAME, ORDER_DATE_SHIPPED_DB_FIELD);
	}
	
	/**
	 * =======================================================================================================
	 * *******************************************************************************************************
	 * *******************************KEY: DATABASE-SIDE ENTITY NAME	 *************************************
	 * *******************************VALUE: GUI-SIDE ENTITY DISPLAYNAME *************************************
	 * =======================================================================================================
	 */
	private static HashMap<String, String> buildEntityDisplayNameByDBNameMap() {
		HashMap<String, String> buildDBEntityNameToDisplayNameMap = new HashMap<String, String>();
		buildDBEntityNameToDisplayNameMap.put(ITEM_DB_ENTITY_NAME,ITEM_ENTITY_DISPLAYNAME);
		buildDBEntityNameToDisplayNameMap.put(PALLET_DB_ENTITY_NAME,PALLET_ENTITY_DISPLAYNAME);
		buildDBEntityNameToDisplayNameMap.put(ORDER_DB_ENTITY_NAME,ORDER_ENTITY_DISPLAYNAME);
		return buildDBEntityNameToDisplayNameMap;
	}
	
	/**
	 * =======================================================================================================
	 * *******************************************************************************************************
	 * *******************************KEY: GUI-SIDE ENTITY DISPLAYNAME	**************************************
	 * *******************************VALUE: DATABASE-SIDE ENTITY NAME	**************************************
	 * =======================================================================================================
	 */
	private static HashMap<String, String> buildEntityDBNameByDisplayNameMap() {
		HashMap<String, String> buildbuildLocalEntityNameToDBEntityNameMap = new HashMap<String, String>();
		buildbuildLocalEntityNameToDBEntityNameMap.put(ITEM_ENTITY_DISPLAYNAME,ITEM_DB_ENTITY_NAME);
		buildbuildLocalEntityNameToDBEntityNameMap.put(PALLET_ENTITY_DISPLAYNAME,PALLET_DB_ENTITY_NAME);
		buildbuildLocalEntityNameToDBEntityNameMap.put(ORDER_ENTITY_DISPLAYNAME,ORDER_DB_ENTITY_NAME);
		return buildbuildLocalEntityNameToDBEntityNameMap;
	}
	
	/**
	 * =======================================================================================================
	 * *******************************************************************************************************
	 * *******************************KEY: DATABASE-SIDE FIELD NAME		**************************************
	 * *******************************VALUE: FIELD DATA TYPE (AS STRING)**************************************
	 * =======================================================================================================
	 */
	private static HashMap<String, String> buildDataTypeMapByFieldDBNameMap() {
		HashMap<String, String> buildingFieldDBNameToDataTypeMap = new HashMap<String, String>();
		addItemFieldTypesToMapByDBName(buildingFieldDBNameToDataTypeMap);
		addPalletFieldTypesToMapByDBName(buildingFieldDBNameToDataTypeMap);
		addOrderFieldTypesToMapByDBName(buildingFieldDBNameToDataTypeMap);
		return buildingFieldDBNameToDataTypeMap;
	}
	
	private static void addItemFieldTypesToMapByDBName(
		HashMap<String, String> buildingFieldDBNameToDataTypeMap) {
		buildingFieldDBNameToDataTypeMap.put(ITEM_PRICE_DB_FIELD, NUMERIC_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(ITEM_WEIGHT_DB_FIELD, NUMERIC_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(ITEM_CURR_STOCK_DB_FIELD, NUMERIC_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(ITEM_RESTOCK_DB_FIELD, NUMERIC_FIELD_TYPE_NAME);

		buildingFieldDBNameToDataTypeMap.put(ITEM_NAME_DB_FIELD, STRING_FIELD_TYPE_NAME);
		buildingFieldDBNameToDataTypeMap.put(ITEM_NUMBER_DB_FIELD, STRING_FIELD_TYPE_NAME);
	}
	
	private static void addPalletFieldTypesToMapByDBName(
		HashMap<String, String> buildingFieldDBNameToDataTypeMap) {
		buildingFieldDBNameToDataTypeMap.put(PALLET_PIECE_COUNT_DB_FIELD, PALLET_PIECE_COUNT_FIELD_DISPLAYNAME);
		buildingFieldDBNameToDataTypeMap.put(PALLET_WEIGHT_DB_FIELD, PALLET_WEIGHT_FIELD_DISPLAYNAME);
		buildingFieldDBNameToDataTypeMap.put(PALLET_LENGTH_DB_FIELD, PALLET_LENGTH_FIELD_DISPLAYNAME);
		buildingFieldDBNameToDataTypeMap.put(PALLET_WIDTH_DB_FIELD, PALLET_WIDTH_FIELD_DISPLAYNAME);
		buildingFieldDBNameToDataTypeMap.put(PALLET_HEIGHT_DB_FIELD, PALLET_HEIGHT_FIELD_DISPLAYNAME);
		
		buildingFieldDBNameToDataTypeMap.put(PALLET_ID_DB_FIELD, PALLET_ID_FIELD_DISPLAYNAME);
		buildingFieldDBNameToDataTypeMap.put(PALLET_ORDER_NUM_DB_FIELD, PALLET_ORDER_NUM_FIELD_DISPLAYNAME);
		buildingFieldDBNameToDataTypeMap.put(PALLET_LOC_DB_FIELD, PALLET_LOC_FIELD_DISPLAYNAME);
		buildingFieldDBNameToDataTypeMap.put(PALLET_NOTES_DB_FIELD, PALLET_NOTES_FIELD_DISPLAYNAME);

		buildingFieldDBNameToDataTypeMap.put(PALLET_RECEIVE_DATE_DB_FIELD, PALLET_RECEIVE_DATE_FIELD_DISPLAYNAME);
		buildingFieldDBNameToDataTypeMap.put(PALLET_SHIP_DATE_DB_FIELD, PALLET_SHIP_DATE_FIELD_DISPLAYNAME);
	}
	
	private static void addOrderFieldTypesToMapByDBName(
			HashMap<String, String> buildingFieldDBNameToDataTypeMap) {
		// TODO Auto-generated method stub	
	}

	/**
	 * =======================================================================================================
	 * *******************************************************************************************************
	 * *******************************KEY: GUI-SIDE FIELD DISPLAY NAME		**********************************
	 * *******************************VALUE: FIELD DATA TYPE (AS STRING)	**********************************
	 * =======================================================================================================
	 */
	private static HashMap<String, String> buildFieldDisplayNameToDataTypeMap() {
		HashMap<String, String> buildingFieldDisplayNameToDataTypesMap = new HashMap<String, String>();
		addItemFieldTypesToMapByDisplayName(buildingFieldDisplayNameToDataTypesMap);
		addPalletFieldTypesToMapByDisplayName(buildingFieldDisplayNameToDataTypesMap);
		addOrderFieldTypesToMapByDisplayName(buildingFieldDisplayNameToDataTypesMap);
		return buildingFieldDisplayNameToDataTypesMap;
	}
	
	private static void addItemFieldTypesToMapByDisplayName(HashMap<String, String> buildingDataTypesMap)
	{
		buildingDataTypesMap.put(ITEM_PRICE_FIELD_DISPLAYNAME, NUMERIC_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(ITEM_WEIGHT_FIELD_DISPLAYNAME, NUMERIC_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(ITEM_CURR_STOCK_FIELD_DISPLAYNAME, NUMERIC_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(ITEM_RESTOCK_FIELD_DISPLAYNAME, NUMERIC_FIELD_TYPE_NAME);

		buildingDataTypesMap.put(ITEM_NAME_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(ITEM_NUMBER_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
	}

	private static void addPalletFieldTypesToMapByDisplayName(HashMap<String, String> buildingDataTypesMap)
	{
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
		buildingDataTypesMap.put(ORDER_NUM_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(ORDER_ORIGIN_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(ORDER_DEST_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(ORDER_RECEIVING_EMPLOYEE_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(ORDER_SHIPPING_EMPLOYEE_FIELD_DISPLAYNAME, STRING_FIELD_TYPE_NAME);
		
		buildingDataTypesMap.put(ORDER_DATE_PLACED_FIELD_DISPLAYNAME, DATE_FIELD_TYPE_NAME);
		buildingDataTypesMap.put(ORDER_DATE_SHIPPED_FIELD_DISPLAYNAME, DATE_FIELD_TYPE_NAME);
	}
	
	//methods for getting arrays of display names
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
	
	public static String getItemEntityDisplayname() {
		return ITEM_ENTITY_DISPLAYNAME;
	}

	public static String getPalletEntityDisplayname() {
		return PALLET_ENTITY_DISPLAYNAME;
	}

	public static String getOrderEntityDisplayname() {
		return ORDER_ENTITY_DISPLAYNAME;
	}

	public static String getAllEntitySpecifierDisplayname() {
		return ALL_ENTITY_SPECIFIER_DISPLAYNAME;
	}

	public static String[] getEntityDisplaynames() {
		return ENTITY_DISPLAYNAMES;
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

	public static String[] getPalletFieldDisplaynames() {
		return PALLET_FIELD_DISPLAYNAMES;
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

	public static String[] getOrderFieldDisplaynames() {
		return ORDER_FIELD_DISPLAYNAMES;
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

	public static String[] getAllDbEntityNames() {
		return ALL_DB_ENTITY_NAMES;
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

	public static String[] getItemDbFields() {
		return ITEM_DB_FIELDS;
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

	public static String[] getPalletDbFields() {
		return PALLET_DB_FIELDS;
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

	public static String[] getOrderDbFields() {
		return ORDER_DB_FIELDS;
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

	public static String[] getNumericFieldModifierStrings() {
		return NUMERIC_FIELD_MODIFIER_STRINGS;
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

	public static String[] getStringFieldModifierStrings() {
		return STRING_FIELD_MODIFIER_STRINGS;
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

	public static String[] getDateFieldModifierStrings() {
		return DATE_FIELD_MODIFIER_STRINGS;
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
	
	//method for getting specifier of all entities
	/**
	 * Getter for the all entity specifier string.
	 * @return a String, the all entity specifier displayname string
	 */
	public static String getDisplayNameForAllEntitySpecifier() {
		return ALL_ENTITY_SPECIFIER_DISPLAYNAME;
	}

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
}
