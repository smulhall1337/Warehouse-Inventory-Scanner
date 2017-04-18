package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller.DateLabelFormatter;

public class MainWindow {

	private JFrame frame;
	
	private static final String ITEM_ENTITY_NAME = "Items";
	private static final String PALLET_ENTITY_NAME = "Pallets";
	private static final String ORDER_ENTITY_NAME = "Orders";
	private static final String[] ENTITIES = {ITEM_ENTITY_NAME, PALLET_ENTITY_NAME, ORDER_ENTITY_NAME};
	
	private static final String ITEM_NAME_FIELD = "Name";
	private static final String ITEM_NUMBER_FIELD = "Item Number";
	private static final String ITEM_PRICE_FIELD = "Price";
	private static final String ITEM_WEIGHT_FIELD = "Weight";
	private static final String ITEM_CURR_STOCK_FIELD = "Current Stock";
	private static final String ITEM_RESTOCK_FIELD = "Restock Threshold";
	
	private static final String PALLET_ID_FIELD = "Pallet ID";
	private static final String PALLET_ORDER_NUM_FIELD = "Order Number";
	private static final String PALLET_LOC_FIELD = "Location";
	private static final String PALLET_PIECE_COUNT_FIELD = "Piece Count";
	private static final String PALLET_WEIGHT_FIELD = "Weight";
	private static final String PALLET_LENGTH_FIELD = "Length";
	private static final String PALLET_WIDTH_FIELD = "Width";
	private static final String PALLET_HEIGHT_FIELD = "Height";
	private static final String PALLET_RECEIVE_DATE_FIELD = "Receival Date";
	private static final String PALLET_SHIP_DATE_FIELD = "Ship Date";
	private static final String PALLET_NOTES_FIELD = "Notes";
	
	private static final String ORDER_NUM_FIELD = "Order Number";
	private static final String ORDER_ORIGIN_FIELD = "Origin";
	private static final String ORDER_DEST_FIELD = "Destination";
	private static final String ORDER_DATE_PLACED_FIELD = "Date Placed";
	private static final String ORDER_DATE_SHIPPED_FIELD = "Date Shipped";
	private static final String ORDER_RECEIVING_EMPLOYEE_FIELD = "Employee Received By";
	private static final String ORDER_SHIPPING_EMPLOYEE_FIELD = "Employee Shipped By";
	private static final String ORDER_MANIFEST_ID_FIELD = "Manifest ID";
	
	private static final String[] PALLET_FIELDS = {PALLET_ID_FIELD, PALLET_ORDER_NUM_FIELD, PALLET_LOC_FIELD,
									PALLET_PIECE_COUNT_FIELD, PALLET_WEIGHT_FIELD, PALLET_LENGTH_FIELD, PALLET_WIDTH_FIELD, 
									PALLET_HEIGHT_FIELD, PALLET_RECEIVE_DATE_FIELD, PALLET_SHIP_DATE_FIELD, PALLET_NOTES_FIELD};
	
	private static final String[] ITEM_FIELDS = {ITEM_NAME_FIELD, ITEM_NUMBER_FIELD, ITEM_PRICE_FIELD,
													ITEM_WEIGHT_FIELD, ITEM_CURR_STOCK_FIELD, ITEM_RESTOCK_FIELD};
	
	private static final String[] ORDER_FIELDS = {ORDER_NUM_FIELD, ORDER_ORIGIN_FIELD, ORDER_DEST_FIELD, ORDER_DATE_PLACED_FIELD,
													ORDER_DATE_SHIPPED_FIELD, ORDER_RECEIVING_EMPLOYEE_FIELD, 
													ORDER_SHIPPING_EMPLOYEE_FIELD,ORDER_MANIFEST_ID_FIELD};
	
	private static final String NUMERIC_FIELD_TYPE_NAME = "numeric";
	private static final String NUMERIC_FIELD_GREATER_THAN = "greater than";
	private static final String NUMERIC_FIELD_LESS_THAN = "less than";
	private static final String NUMERIC_FIELD_EQUAL_TO = "equal to";
	private static final String[] NUMERIC_FIELD_DESCRIPTONS = {NUMERIC_FIELD_GREATER_THAN, NUMERIC_FIELD_LESS_THAN, NUMERIC_FIELD_EQUAL_TO};
	
	private static final String STRING_FIELD_TYPE_NAME = "string";
	private static final String STRING_FIELD_STARTING_WITH = "starting with";
	private static final String STRING_FIELD_ENDING_WITH = "ending with";
	private static final String STRING_FIELD_CONTAINS = "containing";
	private static final String[] STRING_FIELD_DESCRIPTIONS = {STRING_FIELD_STARTING_WITH, STRING_FIELD_ENDING_WITH, STRING_FIELD_CONTAINS};
	private static final int FIELD_OPTION_TEXTBOX_COLUMNS = 10;
	
	private static final String DATE_FIELD_TYPE_NAME = "date";
	private static final String DATE_FIELD_BEFORE = "before";
	private static final String DATE_FIELD_AFTER = "after";
	private static final String DATE_FIELD_ON = "on";
	private static final String[] DATE_FIELD_DESCRIPTIONS = {DATE_FIELD_BEFORE, DATE_FIELD_AFTER, DATE_FIELD_ON};
	
	private static final Font MENUBAR_FONT = new Font("Tahoma", Font.PLAIN, 14);
	private static final Font LABEL_FONT = new Font("Tahoma", Font.PLAIN, 18);
	private static final Font CHECKBOX_FONT = new Font("Tahoma", Font.PLAIN, 14);
	
	private static final int IRRELEVANT_MAX_WIDTH = Integer.MAX_VALUE;
	private static final int DEFAULT_WINDOW_WIDTH = 800;
	private static final int DEFAULT_WINDOW_HEIGHT = 700;
	private static final int MIN_WINDOW_WIDTH = 750;
	private static final int MIN_WINDOW_HEIGHT = 675;
	
	private static final int MIN_MENUBAR_PANEL_WIDTH = MIN_WINDOW_WIDTH;
	private static final int MIN_MENUBAR_PANEL_HEIGHT = 25;
	private static final int MAX_MENUBAR_PANEL_WIDTH = IRRELEVANT_MAX_WIDTH;
	private static final int MAX_MENUBAR_PANEL_HEIGHT = 25;
	
	private static final int MAX_OPTIONS_PANEL_HEIGHT = 265;
	private static final int MAX_OPTIONS_PANEL_WIDTH = 750;
	
//	private static final int MIN_TABLE_PANEL_WIDTH = MIN_WINDOW_WIDTH;
//	private static final int MIN_TABLE_PANEL_HEIGHT = MIN_WINDOW_HEIGHT - MAX_OPTIONS_PANEL_HEIGHT - 50;
//	private static final int MAX_TABLE_PANEL_HEIGHT = 2000;
//	private static final int MAX_TABLE_PANEL_WIDTH = 2000; 
	
	private HashMap<String, JCheckBox> palletCheckboxesMap;
	private HashMap<String, JCheckBox> itemCheckboxesMap;
	private HashMap<String, JCheckBox> orderCheckboxesMap;
	private HashMap<String, String> fieldOptionsMap;
	
	private JComboBox comboBoxField;
	private JComboBox comboBoxFieldOptions;
	private JDatePickerImpl fieldOptionDateField;
	private JTextField fieldOptionStringField;
	private JFormattedTextField fieldOptionNumericField;
	
	private JScrollPane mainTableScrollPane;
	private JTable mainTable;
	private WidthAdjuster TableWidthAdjuster;
	private JPanel entitySelectionOptionsPanel;
	private JPanel entitySelectPanel;
	private JComboBox comboBoxEntityType;
	private JPanel entityOptionsPanel;
	private JPanel tablePanel;
	private JMenuBar menubar;
	private JMenu manageItemsMenu;
	private JMenu ordersMenu;
	private JMenu manageEmployeesMenu;
	private JPanel menubarPanel;
	private JMenuItem addItem;
	private JMenuItem updateItem;
	private JMenuItem createNewOrderMenuItem;
	private JMenuItem scanInOrderMenuItem;
	private JMenuItem manageEmployeesItem;
	private JMenuItem extraMenuItem;
	private JMenu reportsMenu;
	private JMenuItem reportMenu;
	private JMenuItem manageReports;

	private int nextOptionRow; //the index of the next option row in the gridbaglayout
	private static final int STARTING_OPTION_ROW = 1;
	private JPanel optionHeaderPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		nextOptionRow = STARTING_OPTION_ROW;
		initializeAllHashMaps();
		initializeMainFrame();
		initializeMenuBar();
		initializeEntitySelectionOptionsPanel();
		initializeEntitySelectPanel();
		
		//initializeEntityComboBox();
		initializeUpdateButton();
		
		initializeEntityOptionsPanel();
		
		initializeTable();
		initializeBorderStruts();
	}

	private void initializeAllHashMaps()
	{
		palletCheckboxesMap = getHashMap(PALLET_FIELDS);
		itemCheckboxesMap = getHashMap(ITEM_FIELDS);
		orderCheckboxesMap = getHashMap(ORDER_FIELDS);
		initializeFieldOptionsMap();
	}
	
	private void initializeFieldOptionsMap() {
		fieldOptionsMap = new HashMap<String, String>();
		addItemFieldTypesToMap();
		addPalletFieldTypesToMap();
		addOrderFieldTypesToMap();
	}
	
	private void addItemFieldTypesToMap()
	{
		fieldOptionsMap.put(ITEM_PRICE_FIELD, NUMERIC_FIELD_TYPE_NAME);
		fieldOptionsMap.put(ITEM_WEIGHT_FIELD, NUMERIC_FIELD_TYPE_NAME);
		fieldOptionsMap.put(ITEM_CURR_STOCK_FIELD, NUMERIC_FIELD_TYPE_NAME);
		fieldOptionsMap.put(ITEM_RESTOCK_FIELD, NUMERIC_FIELD_TYPE_NAME);

		fieldOptionsMap.put(ITEM_NAME_FIELD, STRING_FIELD_TYPE_NAME);
		fieldOptionsMap.put(ITEM_NUMBER_FIELD, STRING_FIELD_TYPE_NAME);
	}
	
	private void addPalletFieldTypesToMap()
	{
		fieldOptionsMap.put(PALLET_PIECE_COUNT_FIELD, NUMERIC_FIELD_TYPE_NAME);
		fieldOptionsMap.put(PALLET_WEIGHT_FIELD, NUMERIC_FIELD_TYPE_NAME);
		fieldOptionsMap.put(PALLET_LENGTH_FIELD, NUMERIC_FIELD_TYPE_NAME);
		fieldOptionsMap.put(PALLET_WIDTH_FIELD, NUMERIC_FIELD_TYPE_NAME);
		fieldOptionsMap.put(PALLET_HEIGHT_FIELD, NUMERIC_FIELD_TYPE_NAME);
		
		fieldOptionsMap.put(PALLET_ID_FIELD, STRING_FIELD_TYPE_NAME);
		fieldOptionsMap.put(PALLET_ORDER_NUM_FIELD, STRING_FIELD_TYPE_NAME);
		fieldOptionsMap.put(PALLET_LOC_FIELD , STRING_FIELD_TYPE_NAME);
		fieldOptionsMap.put(PALLET_NOTES_FIELD, STRING_FIELD_TYPE_NAME);

		fieldOptionsMap.put(PALLET_RECEIVE_DATE_FIELD, DATE_FIELD_TYPE_NAME);
		fieldOptionsMap.put(PALLET_SHIP_DATE_FIELD , DATE_FIELD_TYPE_NAME);
	}
	
	private void addOrderFieldTypesToMap()
	{	
		fieldOptionsMap.put(ORDER_NUM_FIELD, STRING_FIELD_TYPE_NAME);
		fieldOptionsMap.put(ORDER_ORIGIN_FIELD, STRING_FIELD_TYPE_NAME);
		fieldOptionsMap.put(ORDER_DEST_FIELD, STRING_FIELD_TYPE_NAME);
		fieldOptionsMap.put(ORDER_RECEIVING_EMPLOYEE_FIELD, STRING_FIELD_TYPE_NAME);
		fieldOptionsMap.put(ORDER_SHIPPING_EMPLOYEE_FIELD, STRING_FIELD_TYPE_NAME);
		fieldOptionsMap.put(ORDER_MANIFEST_ID_FIELD, STRING_FIELD_TYPE_NAME);
		
		fieldOptionsMap.put(ORDER_DATE_PLACED_FIELD, DATE_FIELD_TYPE_NAME);
		fieldOptionsMap.put(ORDER_DATE_SHIPPED_FIELD, DATE_FIELD_TYPE_NAME);
	}

	private HashMap<String, JCheckBox> getHashMap(String[] fields) {
		HashMap<String, JCheckBox> map = new HashMap<String, JCheckBox>();
		for(int i = 0; i < fields.length; i++)
		{
			String nextField = fields[i];
			JCheckBox nextBox = new JCheckBox(nextField);
			nextBox.setSelected(true);
			nextBox.setFont(CHECKBOX_FONT);
			map.put(nextField, nextBox);
		}
		return map;
	}

	private void initializeMainFrame()
	{
		frame = new JFrame();
		frame.setTitle("WIMS - Main Window");
		frame.setBounds(100, 100, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
		frame.setMinimumSize(new Dimension(MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
	}
	
	private void initializeMenuBar()
	{
		menubarPanel = new JPanel();
		Dimension minMenubarPanelDim = new Dimension(MIN_MENUBAR_PANEL_WIDTH, MIN_MENUBAR_PANEL_HEIGHT);
		Dimension maxMenubarPanelDim = new Dimension(MAX_MENUBAR_PANEL_WIDTH, MAX_MENUBAR_PANEL_HEIGHT);
		menubarPanel.setMinimumSize(minMenubarPanelDim);
		menubarPanel.setMaximumSize(maxMenubarPanelDim);
		frame.getContentPane().add(menubarPanel);
		menubarPanel.setLayout(new BorderLayout(0, 5));
		menubar = new JMenuBar();
		menubarPanel.add(menubar, BorderLayout.NORTH);
		menubar.setBackground(Color.WHITE);
		
		
		initializeItemsMenu();
		initializeOrdersMenu();
		initializeEmployeesMenu();
		initializeReportsMenu();
	}
	
	private void initializeItemsMenu()
	{
		manageItemsMenu = new JMenu("Manage Items");
		manageItemsMenu.setFont(MENUBAR_FONT);
		menubar.add(manageItemsMenu);
		
		addItem = new JMenuItem("Add Item to Database");
		manageItemsMenu.add(addItem);
		
		updateItem = new JMenuItem("Update Item in Database");
		manageItemsMenu.add(updateItem);
	}
	
	private void initializeOrdersMenu()
	{
		ordersMenu = new JMenu("Orders");
		ordersMenu.setFont(MENUBAR_FONT);
		menubar.add(ordersMenu);
		
		createNewOrderMenuItem = new JMenuItem("Create New Order");
		ordersMenu.add(createNewOrderMenuItem);
		
		scanInOrderMenuItem = new JMenuItem("Scan in Order");
		ordersMenu.add(scanInOrderMenuItem);
	}
	
	private void initializeEmployeesMenu()
	{
		manageEmployeesMenu = new JMenu("Employees");
		manageEmployeesMenu.setFont(MENUBAR_FONT);
		menubar.add(manageEmployeesMenu);
		
		manageEmployeesItem = new JMenuItem("Manage Employees");
		manageEmployeesMenu.add(manageEmployeesItem);
		manageEmployeesItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				ManageEmployees manageEmployeesWindow = new ManageEmployees();
				manageEmployeesWindow.getFrame().setVisible(true);
				//TODO update when manageemployees is updated
			}
		});
		
		extraMenuItem = new JMenuItem("An extra menu item to be filled out later");
		manageEmployeesMenu.add(extraMenuItem);
	}
	
	private void initializeReportsMenu()
	{
		reportsMenu = new JMenu("Reports");
		reportsMenu.setFont(MENUBAR_FONT);
		menubar.add(reportsMenu);
		
		reportMenu = new JMenuItem("Create Reports");
		reportsMenu.add(reportMenu);
		
		manageReports = new JMenuItem("Manage Reports");
		reportsMenu.add(manageReports);
	}
	
	private void initializeEntitySelectionOptionsPanel() {
		JPanel resizingPanelForOptions = new JPanel();
		BoxLayout resizingPanelLayout = new BoxLayout(resizingPanelForOptions, BoxLayout.X_AXIS);
		resizingPanelForOptions.setLayout(resizingPanelLayout);
		resizingPanelForOptions.add(new JPanel());
		
		frame.getContentPane().add(resizingPanelForOptions);
		entitySelectionOptionsPanel = new JPanel();
		entitySelectionOptionsPanel.setMaximumSize(new Dimension(MAX_OPTIONS_PANEL_WIDTH, MAX_OPTIONS_PANEL_HEIGHT));
		entitySelectionOptionsPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		entitySelectionOptionsPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		frame.getContentPane().add(entitySelectionOptionsPanel);
		
		entitySelectionOptionsPanel.setLayout(new BorderLayout(0, 0));
		entitySelectPanel = new JPanel();
		FlowLayout fl_entitySelectPanel = (FlowLayout) entitySelectPanel.getLayout();
		fl_entitySelectPanel.setVgap(10);
		fl_entitySelectPanel.setAlignment(FlowLayout.LEFT);
		entitySelectionOptionsPanel.add(entitySelectPanel, BorderLayout.NORTH);
		
		Border borderEtched = BorderFactory.createEtchedBorder();
		entitySelectionOptionsPanel.setBorder(borderEtched);
		
		resizingPanelForOptions.add(entitySelectionOptionsPanel);
		resizingPanelForOptions.add(new JPanel());
		
		
		optionHeaderPanel = new JPanel();
		entitySelectionOptionsPanel.add(optionHeaderPanel, BorderLayout.WEST);
		JLabel showColumnHeaders = new JLabel("Show columns for:");
		showColumnHeaders.setFont(LABEL_FONT);
		optionHeaderPanel.add(showColumnHeaders);
	}

	private void initializeEntitySelectPanel()
	{
		JLabel lblDisplayInfoFor = new JLabel("Display info for");
		lblDisplayInfoFor.setFont(LABEL_FONT);
		entitySelectPanel.add(lblDisplayInfoFor);
		
		initializeEntityComboBox();
		
		JLabel lblField = new JLabel("with");
		lblField.setFont(LABEL_FONT);
		entitySelectPanel.add(lblField);
		
		initializeFieldsComboBox();
		initializeFieldOptionsComboBox();
	}
	

	private void initializeEntityComboBox()
	{
		comboBoxEntityType = new JComboBox();
		comboBoxEntityType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String currentEntity = (String) comboBoxEntityType.getSelectedItem();
				displayOptionsForEntity(currentEntity);
				updateFieldsComboBox(currentEntity);
			}
		});
		comboBoxEntityType.setFont(LABEL_FONT);
		comboBoxEntityType.setModel(new DefaultComboBoxModel(ENTITIES));
		entitySelectPanel.add(comboBoxEntityType);
	}
	
	/**
	 * initializes the combobox that has the fields for the currently selected entity
	 */
	private void initializeFieldsComboBox()
	{
		comboBoxField = new JComboBox();
		comboBoxField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String currentField = (String) comboBoxField.getSelectedItem();
				updateFieldOptionsComboBox(currentField);
				updateFieldOption(currentField);
			}
		});
		comboBoxField.setFont(LABEL_FONT);
		entitySelectPanel.add(comboBoxField);
		String currentEntity = (String) comboBoxEntityType.getSelectedItem();
		updateFieldsComboBox(currentEntity);
	}
	
	private void updateFieldsComboBox(String currentEntity) {
		switch (currentEntity){
		case ITEM_ENTITY_NAME: 
			comboBoxField.setModel(new DefaultComboBoxModel(ITEM_FIELDS));
			break;
		case PALLET_ENTITY_NAME: 
			comboBoxField.setModel(new DefaultComboBoxModel(PALLET_FIELDS));
			break;
		case ORDER_ENTITY_NAME:
			comboBoxField.setModel(new DefaultComboBoxModel(ORDER_FIELDS));
			break;
		}if(comboBoxFieldOptions != null)
		{
			String currentField = (String) comboBoxField.getSelectedItem();
			updateFieldOptionsComboBox(currentField);
			updateFieldOption(currentField);
		}
	}

	private void initializeFieldOptionsComboBox() {
		comboBoxFieldOptions = new JComboBox();		
		comboBoxFieldOptions.setFont(LABEL_FONT);
		entitySelectPanel.add(comboBoxFieldOptions);

		String currentField = (String) comboBoxField.getSelectedItem();
		
		initializeFieldOptionComponents();
		updateFieldOption(currentField);
		updateFieldOptionsComboBox(currentField);
	}
	
	private void updateFieldOptionsComboBox(String currentField) {
		String fieldType = fieldOptionsMap.get(currentField);
		switch (fieldType){
		case NUMERIC_FIELD_TYPE_NAME: 
			comboBoxFieldOptions.setModel(new DefaultComboBoxModel(NUMERIC_FIELD_DESCRIPTONS));
			break;
		case STRING_FIELD_TYPE_NAME: 
			comboBoxFieldOptions.setModel(new DefaultComboBoxModel(STRING_FIELD_DESCRIPTIONS));
			break;
		case DATE_FIELD_TYPE_NAME:
			comboBoxFieldOptions.setModel(new DefaultComboBoxModel(DATE_FIELD_DESCRIPTIONS));
			break;
		}
	}
	
	private void updateFieldOption(String currentField) {
		clearFieldOptionComponents();
		String fieldType = fieldOptionsMap.get(currentField);
		//TODO add document for text fields
		switch (fieldType){
		case NUMERIC_FIELD_TYPE_NAME: 
			fieldOptionNumericField = new JFormattedTextField();
			fieldOptionNumericField.setColumns(FIELD_OPTION_TEXTBOX_COLUMNS);
			entitySelectPanel.add(fieldOptionNumericField);
			break;
		case STRING_FIELD_TYPE_NAME: 
			fieldOptionStringField = new JTextField();
			fieldOptionStringField.setColumns(FIELD_OPTION_TEXTBOX_COLUMNS);
			entitySelectPanel.add(fieldOptionStringField);
			break;
		case DATE_FIELD_TYPE_NAME:
			fieldOptionDateField = this.getDatePicker();
			entitySelectPanel.add(fieldOptionDateField);
			break;
		}
	}
	
	private void initializeFieldOptionComponents() {
		fieldOptionNumericField = new JFormattedTextField();
		fieldOptionStringField = new JTextField();
		fieldOptionDateField = this.getDatePicker();
	}
	
	private void clearFieldOptionComponents() {
		if(fieldOptionNumericField.getParent() != null)
			entitySelectPanel.remove(fieldOptionNumericField);
		if(fieldOptionStringField.getParent() != null)
			entitySelectPanel.remove(fieldOptionStringField);
		if(fieldOptionDateField.getParent() != null)
			entitySelectPanel.remove(fieldOptionDateField);
	}
	
	private String getFieldOptionValue(String currentField) {
		String fieldType = fieldOptionsMap.get(currentField);
		switch (fieldType){
		case NUMERIC_FIELD_TYPE_NAME: 
			return fieldOptionNumericField.getText();
		case STRING_FIELD_TYPE_NAME: 
			return fieldOptionStringField.getText();
		case DATE_FIELD_TYPE_NAME:
			return fieldOptionDateField.getJFormattedTextField().getText();
		}
		return null; //should never happen
	}

	private void initializeEntityOptionsPanel()
	{
		entityOptionsPanel = new JPanel();
		
		//TODO fix/move this somewhere else, this is just here for right now so the border on
		//the options panel is good
		JPanel emptyPanel = new JPanel();
		Component rigidAreaRight = Box.createRigidArea(new Dimension(100, 100));
		emptyPanel.add(rigidAreaRight);
		
		entitySelectionOptionsPanel.add(emptyPanel, BorderLayout.EAST);
		entitySelectionOptionsPanel.add(entityOptionsPanel, BorderLayout.CENTER);
		GridBagLayout gbl_entityOptionsPanel = new GridBagLayout();
		gbl_entityOptionsPanel.columnWidths = new int[] {20, 100, 100, 100};
		gbl_entityOptionsPanel.rowHeights = new int[] {0, 30, 30, 30, 30, 0};
		gbl_entityOptionsPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_entityOptionsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		entityOptionsPanel.setLayout(gbl_entityOptionsPanel);
		Border borderLoweredBevel = BorderFactory.createLoweredBevelBorder();
		entityOptionsPanel.setBorder(borderLoweredBevel);
		displayOptionsForEntity((String) comboBoxEntityType.getSelectedItem());
	}
	
	private void displayOptionsForEntity(String entityName)
	{
		clearCurrentOptions();
		switch (entityName){
		case ITEM_ENTITY_NAME: 
			displayOptions(itemCheckboxesMap);
			break;
		case PALLET_ENTITY_NAME: 
			displayOptions(palletCheckboxesMap);
			break;
		case ORDER_ENTITY_NAME:
			displayOptions(orderCheckboxesMap);
			break;
		}
	}
	
	private void displayOptions(HashMap<String, JCheckBox> map) {
		Iterator<String> itty = map.keySet().iterator();
		while(itty.hasNext())
		{
			JCheckBox firstBox = map.get(itty.next());
			JCheckBox secondBox = null;
			JCheckBox thirdBox = null;
			if(itty.hasNext())
				secondBox = map.get(itty.next());
			if(itty.hasNext())
				thirdBox = map.get(itty.next());
			addRowToOptions(firstBox, secondBox, thirdBox);
		}
	}

	private void clearCurrentOptions()
	{
		nextOptionRow = STARTING_OPTION_ROW;
		entityOptionsPanel.removeAll();
		entityOptionsPanel.revalidate();
		entityOptionsPanel.repaint();
	}
	
	private void initializeUpdateButton(){
	
		JPanel updateButtonPanel = new JPanel();
		entitySelectionOptionsPanel.add(updateButtonPanel, BorderLayout.SOUTH);
		updateButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

		
		JButton updateButton = new JButton("Update");
		updateButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		updateButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		updateButtonPanel.add(updateButton);
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String entityName = (String) comboBoxEntityType.getSelectedItem();
				String fieldName = (String) comboBoxField.getSelectedItem();
				String fieldOption = (String) comboBoxFieldOptions.getSelectedItem();
				String fieldOptionValue = getFieldOptionValue(fieldName);
				updateTableBasedOnSelection(entityName, fieldName, fieldOption, fieldOptionValue);
			}
		});
		updateButton.setHorizontalAlignment(SwingConstants.RIGHT);
	}
	
	private void updateTableBasedOnSelection(String entityName, String fieldName, String fieldOption, String fieldOptionValue){
		//TODO
	}
	
	private void initializeTable()
	{
		JPanel resizingPanelForTable = new JPanel();
		BoxLayout resizingPanelLayout = new BoxLayout(resizingPanelForTable, BoxLayout.X_AXIS);
		resizingPanelForTable.setLayout(resizingPanelLayout);
		frame.getContentPane().add(resizingPanelForTable);
		tablePanel = new JPanel();
//		Dimension minTablePanelDim = new Dimension(MIN_TABLE_PANEL_WIDTH, MIN_TABLE_PANEL_HEIGHT);
//		tablePanel.setMinimumSize(minTablePanelDim);
		resizingPanelForTable.add(tablePanel);
		tablePanel.setLayout(new BorderLayout(0, 0));
		
		mainTableScrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tablePanel.add(mainTableScrollPane);
		
		mainTable = new JTable();
		TableWidthAdjuster = new WidthAdjuster(mainTable);
		
		Object[][] defaultData = new Object[50][25]; 
		String[] defaultColNames = new String[25];
		//mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		mainTable.setFillsViewportHeight(true);
		//MyTableModel tabelModel = new MyTableModel(defaultData, defaultColNames)
		mainTable.setModel(new DefaultTableModel(defaultData, defaultColNames));
		
//		JTableHeader header = table.getTableHeader();
//		header.setUpdateTableInRealTime(true);
//		header.addMouseListener(tableModel.new ColumnListener(table));
//		header.setReorderingAllowed(true);
		mainTableScrollPane.setViewportView(mainTable);
		
	}
	
	private void addRowToOptions(Component firstColumn, Component secondColumn)
	{
		Component rigidAreaLeft = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidAreaLeft = new GridBagConstraints();
		gbc_rigidAreaLeft.anchor = GridBagConstraints.WEST;
		gbc_rigidAreaLeft.insets = new Insets(0, 0, 5, 5);
		gbc_rigidAreaLeft.gridx = 0;
		gbc_rigidAreaLeft.gridy = nextOptionRow;
		entityOptionsPanel.add(rigidAreaLeft, gbc_rigidAreaLeft);
		
		if(firstColumn != null)
		{
			GridBagConstraints gbc_firstColumn = new GridBagConstraints();
			gbc_firstColumn.anchor = GridBagConstraints.WEST;
			gbc_firstColumn.insets = new Insets(0, 0, 5, 5);
			gbc_firstColumn.gridx = 1;
			gbc_firstColumn.gridy = nextOptionRow;
			entityOptionsPanel.add(firstColumn, gbc_firstColumn);
		}
		
		if(secondColumn != null)
		{
			GridBagConstraints gbc_secondColumn = new GridBagConstraints();
			gbc_secondColumn.fill = GridBagConstraints.HORIZONTAL;
			gbc_secondColumn.insets = new Insets(0, 0, 5, 5);
			gbc_secondColumn.anchor = GridBagConstraints.WEST;
			gbc_secondColumn.gridx = 2;
			gbc_secondColumn.gridy = nextOptionRow;
			entityOptionsPanel.add(secondColumn, gbc_secondColumn);
		}
		nextOptionRow++;
	}
	
	private void addRowToOptions(Component firstColumn, Component secondColumn, Component thirdColumn)
	{
		if(thirdColumn != null)
		{
			GridBagConstraints gbc_thirdColumn = new GridBagConstraints();
			gbc_thirdColumn.anchor = GridBagConstraints.WEST;
			gbc_thirdColumn.insets = new Insets(0, 0, 5, 5);
			gbc_thirdColumn.gridx = 3;
			gbc_thirdColumn.gridy = nextOptionRow;
			entityOptionsPanel.add(thirdColumn, gbc_thirdColumn);
		}
		
		addRowToOptions(firstColumn, secondColumn);
	}
	
	private void initializeBorderStruts()
	{
		Component rigidAreaWest = Box.createRigidArea(new Dimension(20, 20));
		tablePanel.add(rigidAreaWest, BorderLayout.WEST);
		
		Component rigidAreaEast = Box.createRigidArea(new Dimension(20, 20));
		tablePanel.add(rigidAreaEast, BorderLayout.EAST);
		
		Component rigidAreaSouth = Box.createRigidArea(new Dimension(20, 20));
		tablePanel.add(rigidAreaSouth, BorderLayout.SOUTH);
	}
	
	private JDatePickerImpl getDatePicker()
	{
		UtilDateModel model = new UtilDateModel();
    	//model.setDate(20,04,2014);
    	// Need this...
    	Properties p = new Properties();
    	p.put("text.today", "Today");
    	p.put("text.month", "Month");
    	p.put("text.year", "Year");
    	JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
    	// Don't know about the formatter, but there it is...
    	JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
    	datePicker.setTextEditable(true);
    	return datePicker;
	}

}
