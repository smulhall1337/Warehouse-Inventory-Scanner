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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
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
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import controller.DBNamesManager;
import controller.DateLabelFormatter;
import controller.ErrorStatusReportable;
import controller.ImprovedFormattedTextField;
import controller.SQL_Handler;
import controller.WIMSTable;
import controller.WIMSTableModel;
import controller.WidthAdjuster;

public class MainWindow implements ErrorStatusReportable{

	private JFrame frame;

	//number of columns for field textboxes
	private static final int FIELD_OPTION_TEXTBOX_COLUMNS = 10;
	
	//Fonts for different UI elements
	private static final int ENTITYFIELD_SELECTION_FONT_SIZE = 18;
	private static final int SMALLER_COMPONENT_FONT_SIZE = 14;
	private static final int TABLE_FONT_SIZE = 14;
	private static final Font LABEL_FONT = new Font("Tahoma", Font.PLAIN, ENTITYFIELD_SELECTION_FONT_SIZE);
	private static final Font COMBOBOX_FONT = new Font("Tahoma", Font.PLAIN, ENTITYFIELD_SELECTION_FONT_SIZE);
	private static final Font FIELD_MODIFIER_COMPONENT_FONT = new Font("Tahoma", Font.PLAIN, ENTITYFIELD_SELECTION_FONT_SIZE);
	private static final Font MENUBAR_FONT = new Font("Tahoma", Font.PLAIN, SMALLER_COMPONENT_FONT_SIZE);
	private static final Font CHECKBOX_FONT = new Font("Tahoma", Font.PLAIN, SMALLER_COMPONENT_FONT_SIZE);
	private static final Font BUTTON_FONT = new Font("Tahoma", Font.PLAIN, 22);
	private static final Font CHECKBOX_STATUS_FONT = new Font("Tahoma", Font.PLAIN, SMALLER_COMPONENT_FONT_SIZE);
	
	private static final Font TABLE_FONT = new Font("Tahoma", Font.PLAIN, TABLE_FONT_SIZE);
	private static final Font TABLE_HEADER_FONT = new Font("Tahoma", Font.PLAIN, TABLE_FONT_SIZE);
	
	
	//Max width for when only max height is going to be restricted (setMaximumSize requires height&width)
	private static final int IRRELEVANT_MAX_WIDTH = Integer.MAX_VALUE;
	
	//Dimension constants for entire application window
	private static final int DEFAULT_WINDOW_WIDTH = 925;
	private static final int DEFAULT_WINDOW_HEIGHT = 700;
	private static final int MIN_WINDOW_WIDTH = 910;
	private static final int MIN_WINDOW_HEIGHT = 675;
	
	//Dimension constants for top menubar
	private static final int MIN_MENUBAR_PANEL_WIDTH = MIN_WINDOW_WIDTH;
	private static final int MIN_MENUBAR_PANEL_HEIGHT = 25;
	private static final int MAX_MENUBAR_PANEL_WIDTH = IRRELEVANT_MAX_WIDTH;
	private static final int MAX_MENUBAR_PANEL_HEIGHT = 25;
	
	//Dimension constants for options panel (the panel containing checkboxes)
	private static final int MAX_OPTIONS_PANEL_HEIGHT = 265;
	private static final int MAX_OPTIONS_PANEL_WIDTH = 750;
	
	//How many pixels will be between the table panel and the edge of the window
	private static final int TABLE_PANEL_MARGIN = 20;
	
	private static final String NUMERIC_FIELD_ENTRY_REGEXSTRING = "([+-]?\\d*\\.?\\d*)";
	private static final DecimalFormat NUMERIC_FIELD_ENTRY_FORMAT = new DecimalFormat(NUMERIC_FIELD_ENTRY_REGEXSTRING);
	
	//HashMaps that map each field to a checkbox, used for the "show columns for" checkboxes
	//Maps are <Key, Value> = <FieldName, CheckBoxForFieldName>
	private HashMap<String, JCheckBox> palletFieldsCheckBoxesMap;
	private HashMap<String, JCheckBox> itemFieldsCheckBoxesMap;
	private HashMap<String, JCheckBox> orderFieldsCheckBoxesMap;
	
	private JPanel showColumnsForPanel;
	//Combobox to select an entity type to view
	private JComboBox comboBoxEntityType;
	//Combobox to select a field of the currently selected entity
	private JComboBox comboBoxField;
	//Combobox to select "less than", "starting with", etc.
	private JComboBox comboBoxFieldModifier;
	//Labels that go along with comboboxes
	private JLabel lblDisplayInfoFor;
	private JLabel lblFieldWith; 
	
	//DatePicker to select date when a Date type field is selected
	private JDatePickerImpl dateFieldDatePicker;
	//JTextField to enter a search string when a String type field is selected
	private JTextField stringFieldTextField;
	//JFormattedTextField to enter a number when a numeric type field is selected
	private ImprovedFormattedTextField numericFieldTextField;
	
	//Wrapper panel for the table scrollpane
	private JPanel tablePanel;
	//Scrollpane for the table
	private JScrollPane mainTableScrollPane;
	//main table in scrollpane, a custom table class
	private WIMSTable mainTable;
	//width adjuster manager for table
	private WidthAdjuster TableWidthAdjuster;
	//a map where the column header is the key, and the column is the value
	private HashMap <String,TableColumn> headersToDeletedColumnMap; //TODO maybe add a show all columns button
	
	//wrapper panel that contains entity&field selection panel, showcolumnsfor panel,
	//and update button panel
	private JPanel allOptionsPanel;
	//panel that contains entity, field, and field options selection
	private JPanel entityAndFieldSelectPanel;
	
	//components for update button panel
	private JPanel updateButtonPanel;
	private JButton updateButton;
	private JLabel lblErrorStatus;
	//whether error is active, used so multiple threads arent created
	private boolean errorIsActive;
	//how long to display error messages for, in milliseconds
	protected static final int ERROR_DISPLAY_TIME_MS = 7000;
	private SwingWorker<Boolean, Void> updateTableProcess;
	
	
	//Fields for menubar and submenus
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

	//Panel for "Show columns for:" label
	private JPanel showColumnsForLabelPanel;
	
	//the index of the next option row in the gridbaglayout. Used with addRowToOptions()
	//to manage the adding of field name checkboxes to that panel
	private int nextOptionRow;

	private JLabel lblLoadingIcon;
	private String currentTableEntity; //TODO use this to keep track of the entity currently in the table view

	private JLabel lblCheckBoxesStatus;
	//initial starting option row, this is the value the layout defaults to when it is cleared
	private static final int STARTING_OPTION_ROW = 1;

	private static final String LOADING_GIF_ICON_NAME = "loading.gif";

	private static final String BUILDING_QUERY_STATUS_MESSAGE = "Building query...";

	private static final String EXECUTING_QUERY_STATUS_MESSAGE = "Fetching data from database...";

	private static final String UPDATING_TABLE_STATUS_MESSAGE = "Updating table with data...";

	

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
		initializeAllOptionsPanel();
		initializeTablePanel();
	}
	
	/**
	 * Initialize the hashmaps for checkboxes and field data types
	 */
	private void initializeAllHashMaps()
	{
		headersToDeletedColumnMap = new HashMap<String, TableColumn>();
		palletFieldsCheckBoxesMap = getCheckBoxHashMap(DBNamesManager.getAllPalletFieldDisplayNames());
		itemFieldsCheckBoxesMap = getCheckBoxHashMap(DBNamesManager.getAllItemFieldDisplayNames());
		orderFieldsCheckBoxesMap = getCheckBoxHashMap(DBNamesManager.getAllOrderFieldDisplayNames());
	}

	/**
	 * Generic method for getting a HashMap of checkboxes for a given
	 * array of fields
	 * @param fields the fields to create checkboxes for
	 * @return a HashMap, where 
	 * 			key:	a field from the fields[] parameter, and
	 * 			value:	a checkbox for the field denoted by the key
	 */
	private HashMap<String, JCheckBox> getCheckBoxHashMap(String[] fields) {
		HashMap<String, JCheckBox> map = new HashMap<String, JCheckBox>();
		for(int i = 0; i < fields.length; i++)
		{
			String nextField = fields[i];
			JCheckBox nextBox = new JCheckBox(nextField);
			nextBox.setSelected(true);
			nextBox.setFont(CHECKBOX_FONT);
			nextBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {
					AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
					boolean selected = abstractButton.getModel().isSelected();
					TableColumnModel tcm = mainTable.getColumnModel();
					TableColumn correspondingColumn;
					if(!selected)
					{
						correspondingColumn = mainTable.getColumn(nextField);//headersToColumnMap.get(nextField);
						tcm.removeColumn(correspondingColumn);
						headersToDeletedColumnMap.put(nextField, correspondingColumn);
					}else{
						correspondingColumn = headersToDeletedColumnMap.get(nextField);
						tcm.addColumn(correspondingColumn);
					}
				}
				//TODO add toggle hide/show all columns
				//TODO fix bug of when a query for an entity type fails, the checkboxes should reset
			});
			map.put(nextField, nextBox);
		}
		return map;
	}
	
	private void setAreCheckBoxesAreEnabled(boolean areActive)
	{
		HashMap<String, JCheckBox> checkBoxes = getCheckBoxMapForEntity((String) comboBoxEntityType.getSelectedItem());
		Iterator<String> checkBoxItty = checkBoxes.keySet().iterator();
		while(checkBoxItty.hasNext())
		{
			checkBoxes.get(checkBoxItty.next()).setEnabled(areActive);
		}
	}

	/**
	 * Initialize the main window
	 */
	private void initializeMainFrame()
	{
		frame = new JFrame();
		frame.setTitle("WIMS - Main Window");
		frame.setBounds(100, 100, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
		frame.setMinimumSize(new Dimension(MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
	}
	
	/**
	 * Initialize the top menu bar and all of its items
	 */
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
	
	/**
	 * Initialize the items submenu (and all of its JMenuItems) on the menubar
	 */
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
	
	/**
	 * Initialize the orders submenu (and all of its JMenuItems) on the menubar
	 */
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
	
	/**
	 * Initialize the employees submenu (and all of its JMenuItems) on the menubar
	 */
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
	
	/**
	 * Initialize the reports submenu (and all of its JMenuItems) on the menubar
	 */
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
	
	/**
	 * Initialize the panel that contains the entity, field, and field options selection,
	 * as well as the selection of what fields to show columns for, and the update button panel.
	 */
	private void initializeAllOptionsPanel() {
		
		//Initialize the panel that contains the entity/field/search comboboxes
		allOptionsPanel = new JPanel();
		allOptionsPanel.setMaximumSize(new Dimension(MAX_OPTIONS_PANEL_WIDTH, MAX_OPTIONS_PANEL_HEIGHT));
		allOptionsPanel.setLayout(new BorderLayout(0, 0));
		
		//A wrapper panel with a boxlayout so that the maximum size of the entity selection
		//and field columns selection panel is respected. Needed since borderlayout does
		//not respect maximum size.
		JPanel resizingPanelForOptions = new JPanel();
		BoxLayout resizingPanelLayout = new BoxLayout(resizingPanelForOptions, BoxLayout.X_AXIS);
		resizingPanelForOptions.setLayout(resizingPanelLayout);
		//Add a blank panel so there is a panel on the left that can resize to fill the empty space
		//needed to keep the main panel in the middle when the window resizes
		resizingPanelForOptions.add(new JPanel());
		//Add the panel to the middle
		resizingPanelForOptions.add(allOptionsPanel);
		//Add a blank panel so there is a panel on the right that can resize to fill the empty space
		//needed to keep the main panel in the middle when the window resizes
		resizingPanelForOptions.add(new JPanel());
		//Give the panel an etched border
		Border borderEtched = BorderFactory.createEtchedBorder();
		allOptionsPanel.setBorder(borderEtched);
		frame.getContentPane().add(resizingPanelForOptions);
		
		initializeEntityAndFieldSelectPanel();
		initializeShowColumnsForPanel();
		initializeUpdateButtonPanel();
	}
	
	/**
	 * Initialize the panel that contains the "Show columns for:" header label
	 */
	private void initializeShowColumnsForLabelPanel()
	{
		//Initialize the panel for the "show columns for:" label
		showColumnsForLabelPanel = new JPanel();
		allOptionsPanel.add(showColumnsForLabelPanel, BorderLayout.WEST);
		JLabel showColumnHeaders = new JLabel("Show columns for:");
		showColumnHeaders.setFont(LABEL_FONT);
		showColumnsForLabelPanel.add(showColumnHeaders);
	}
	
	private void initializeEntityAndFieldSelectPanel()
	{
		entityAndFieldSelectPanel = new JPanel();
		FlowLayout fl_entitySelectPanel = (FlowLayout) entityAndFieldSelectPanel.getLayout();
		fl_entitySelectPanel.setVgap(10);
		fl_entitySelectPanel.setAlignment(FlowLayout.LEFT);
		allOptionsPanel.add(entityAndFieldSelectPanel, BorderLayout.NORTH);
		
		lblDisplayInfoFor = new JLabel("Display info for");
		lblDisplayInfoFor.setFont(LABEL_FONT);
		entityAndFieldSelectPanel.add(lblDisplayInfoFor);
		
		initializeEntityComboBox();
		
		lblFieldWith = new JLabel("with");
		lblFieldWith.setFont(LABEL_FONT);
		entityAndFieldSelectPanel.add(lblFieldWith);
		
		initializeFieldsComboBox();
		initializeFieldModifierComboBox();
	}
	

	/**
	 * Initialize the combobox of entities.
	 */
	private void initializeEntityComboBox()
	{
		comboBoxEntityType = new JComboBox();
		comboBoxEntityType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String currentEntity = (String) comboBoxEntityType.getSelectedItem();
				//if "all warehouse entities" is selected, we can't show fields
				//so we hide all of the field and field modifier options
				if(currentEntity.equals(DBNamesManager.getDisplayNameForAllEntitySpecifier())){
					setFieldOptionVisibility(false);
				}else{
					//display checkboxes for the newly selected entity's fields
					//and update the fields combobox to display these fields
					setFieldOptionVisibility(true);
					displayFieldCheckBoxesForEntity(currentEntity);
					updateFieldsComboBox(currentEntity);
					if(currentEntity.equals(currentTableEntity))
					{
						setAreCheckBoxesAreEnabled(true); //TODO double check this
						clearColumnHeaderCheckboxesStatus();
					}else{
						setAreCheckBoxesAreEnabled(false);
						displayColumnHeaderCheckboxesStatus("Make a query first to show/hide columns", Color.RED);
					}
				}
			}
		});
		comboBoxEntityType.setFont(COMBOBOX_FONT);
		//display the entities from the entity array
		comboBoxEntityType.setModel(new DefaultComboBoxModel(DBNamesManager.getEntityDisplayNames()));
		if(lblCheckBoxesStatus == null)
			initializeCheckBoxStatusLabel();
		setAreCheckBoxesAreEnabled(false);
		displayColumnHeaderCheckboxesStatus("Make a query first to show/hide columns", Color.RED); //TODO add these to be constants
		entityAndFieldSelectPanel.add(comboBoxEntityType);
	}
	
	private void displayColumnHeaderCheckboxesStatus(String string, Color color) {
		// TODO Auto-generated method stub
		lblCheckBoxesStatus.setText(string);
		lblCheckBoxesStatus.setForeground(color);
	}
	
	private void clearColumnHeaderCheckboxesStatus() {
		displayColumnHeaderCheckboxesStatus("", showColumnsForPanel.getBackground());
	}
	
	private void setFieldOptionVisibility(boolean visibility) {
		
		//set the other comboboxes to the given visibility
		comboBoxField.setVisible(visibility);
		comboBoxFieldModifier.setVisible(visibility);
		
		//set the labels for those comboboxes to the given visibility
		lblDisplayInfoFor.setVisible(visibility);
		lblFieldWith.setVisible(visibility);
		
		//set the field modifier component to the given visibility
		setFieldModifierVisibility(visibility);
		
		//set the show columns for components to the given visibility
		showColumnsForLabelPanel.setVisible(visibility);
		showColumnsForPanel.setVisible(visibility);
	}
	

	/**
	 * Set the visibility of the field modifier components to the given visibilty
	 * @param visibility the visibilty to set the field modifier components to. 
	 * 			true - components are visible
	 * 			false - components are not visible
	 */
	private void setFieldModifierVisibility(boolean visibility) {
		//for each possible field modifier component, if it isn't null,
		//and its parent isn't null (aka it is actually displayed on a window)
		//then set its visibility to the given value
		if(numericFieldTextField != null && numericFieldTextField.getParent() != null)
			numericFieldTextField.setVisible(visibility);
		if(stringFieldTextField.getParent() != null && stringFieldTextField.getParent() != null)
			stringFieldTextField.setVisible(visibility);
		if(dateFieldDatePicker.getParent() != null && dateFieldDatePicker.getParent() != null)
			dateFieldDatePicker.setVisible(visibility);
	}

	/**
	 * Initialize the combobox the combobox of fields for the current entity.
	 */
	private void initializeFieldsComboBox()
	{
		comboBoxField = new JComboBox();
		comboBoxField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String currentField = (String) comboBoxField.getSelectedItem();
				updateFieldModifierComboBox(currentField);
				updateFieldModifierComponent(currentField);
			}
		});
		comboBoxField.setFont(COMBOBOX_FONT);
		entityAndFieldSelectPanel.add(comboBoxField);
		//get the current entity
		String currentEntity = (String) comboBoxEntityType.getSelectedItem();
		//update this fields combobox to display that entity's fields
		updateFieldsComboBox(currentEntity);
	}
	
	/**
	 * Update the fields combobox to display the fields for the given entity
	 * @param entityName the entity whose fields will be displayed in the combobox
	 */
	private void updateFieldsComboBox(String entityName) {
		switch (entityName){
		case DBNamesManager.ITEM_ENTITY_DISPLAYNAME: 
			comboBoxField.setModel(new DefaultComboBoxModel(DBNamesManager.getAllItemFieldDisplayNames()));
			break;
		case DBNamesManager.PALLET_ENTITY_DISPLAYNAME: 
			comboBoxField.setModel(new DefaultComboBoxModel(DBNamesManager.getAllPalletFieldDisplayNames()));
			break;
		case DBNamesManager.ORDER_ENTITY_DISPLAYNAME:
			comboBoxField.setModel(new DefaultComboBoxModel(DBNamesManager.getAllOrderFieldDisplayNames()));
			break;
		}
		//if the combobox for the field modifiers has been initialized, update it 
		//based on the newly selected entity (it will update based on whatever
		//field from the new entity is selected first). This solves the problem
		//of switching entities causing the field modifiers to not make sense for
		//the current entity's first field.
		if(comboBoxFieldModifier != null)
		{
			//grab the currently selected field (which has been updated in the switch(entityName) above)
			String currentField = (String) comboBoxField.getSelectedItem();
			//update the field modifier components based on this field
			updateFieldModifierComboBox(currentField);
			updateFieldModifierComponent(currentField);
		}
	}

	/**
	 * Initialize the combobox for the field modifiers. 
	 */
	private void initializeFieldModifierComboBox() {
		comboBoxFieldModifier = new JComboBox();		
		comboBoxFieldModifier.setFont(COMBOBOX_FONT);
		entityAndFieldSelectPanel.add(comboBoxFieldModifier);

		String currentField = (String) comboBoxField.getSelectedItem();
		
		initializeFieldModifierComponents();
		updateFieldModifierComponent(currentField);
		updateFieldModifierComboBox(currentField);
	}
	
	/**
	 * Update the field modifiers combobox based on the given field
	 * @param fieldDisplayName the field to display modifiers for
	 */
	private void updateFieldModifierComboBox(String fieldDisplayName) {
		//get the data type of the given field
		String fieldType = DBNamesManager.getFieldDataTypeByDisplayName(fieldDisplayName);
		//fill the combobox based on the field data type
		switch (fieldType){
		case DBNamesManager.NUMERIC_FIELD_TYPE_NAME: 
			comboBoxFieldModifier.setModel(new DefaultComboBoxModel(DBNamesManager.getNumericFieldModifierStrings()));
			break;
		case DBNamesManager.STRING_FIELD_TYPE_NAME: 
			comboBoxFieldModifier.setModel(new DefaultComboBoxModel(DBNamesManager.getStringFieldModifierStrings()));
			break;
		case DBNamesManager.DATE_FIELD_TYPE_NAME:
			comboBoxFieldModifier.setModel(new DefaultComboBoxModel(DBNamesManager.getDateFieldModifierStrings()));
			break;
		}
	}
	
	/**
	 * Update the user field modifier component based on the given field. Date types
	 * will display a date picker component, numeric types will have a number
	 * entry component, etc.
	 * @param fieldDisplayName the field to use to determine how to update the field modifier component
	 */
	private void updateFieldModifierComponent(String fieldDisplayName) {
		//clear the current field modifier component
		clearFieldModifierComponent();
		//get the data type of the field to update the component based on
		String fieldType = DBNamesManager.getFieldDataTypeByDisplayName(fieldDisplayName);
		//TODO add documents for text fields
		//Update the component based on the data type of the field
		switch (fieldType){
		case DBNamesManager.NUMERIC_FIELD_TYPE_NAME: 
			//if numeric, display a number entry text field
			numericFieldTextField = new ImprovedFormattedTextField(NUMERIC_FIELD_ENTRY_FORMAT);
			numericFieldTextField.setColumns(FIELD_OPTION_TEXTBOX_COLUMNS);
			numericFieldTextField.setFont(FIELD_MODIFIER_COMPONENT_FONT);
			entityAndFieldSelectPanel.add(numericFieldTextField);
			break;
		case DBNamesManager.STRING_FIELD_TYPE_NAME: 
			//if string, display a text field for the user's search string
			stringFieldTextField = new JTextField();
			stringFieldTextField.setColumns(FIELD_OPTION_TEXTBOX_COLUMNS);
			stringFieldTextField.setFont(FIELD_MODIFIER_COMPONENT_FONT);
			entityAndFieldSelectPanel.add(stringFieldTextField);
			break;
		case DBNamesManager.DATE_FIELD_TYPE_NAME:
			//if date, display a date picker
			dateFieldDatePicker = this.getDatePicker();
			dateFieldDatePicker.setFont(FIELD_MODIFIER_COMPONENT_FONT);
			entityAndFieldSelectPanel.add(dateFieldDatePicker);
			break;
		}
	}
	
	/**
	 * Initialize the components for field modifiers
	 */
	private void initializeFieldModifierComponents() {
		numericFieldTextField = new ImprovedFormattedTextField(NUMERIC_FIELD_ENTRY_FORMAT);
		stringFieldTextField = new JTextField();
		dateFieldDatePicker = this.getDatePicker();
		numericFieldTextField.setFont(FIELD_MODIFIER_COMPONENT_FONT);
		stringFieldTextField.setFont(FIELD_MODIFIER_COMPONENT_FONT);
		dateFieldDatePicker.setFont(FIELD_MODIFIER_COMPONENT_FONT);
	}
	
	/**
	 * Clear the current field modifier component
	 */
	private void clearFieldModifierComponent() {
		if(numericFieldTextField.getParent() != null)
			entityAndFieldSelectPanel.remove(numericFieldTextField);
		if(stringFieldTextField.getParent() != null)
			entityAndFieldSelectPanel.remove(stringFieldTextField);
		if(dateFieldDatePicker.getParent() != null)
			entityAndFieldSelectPanel.remove(dateFieldDatePicker);
	}
	
	/**
	 * Get the value the user entered into the field modifier
	 * @param currentField the field that is currently selected
	 * @return a String containing the user's entry into the field modifier.
	 * 		   	if currentField is numeric type, this will be a number string
	 * 			if currentField is a date type, this will be a string containing the date
	 * 			if currentField is String type, this will be the search string the user entered
	 * 			if the type of the currentField does not match any of the possible data types,
	 * 			this method will return null
	 */
	private String getFieldModifierValue(String currentField) {
		String fieldType = DBNamesManager.getFieldDataTypeByDisplayName(currentField);
		switch (fieldType){
		case DBNamesManager.NUMERIC_FIELD_TYPE_NAME: 
			return numericFieldTextField.getText();
		case DBNamesManager.STRING_FIELD_TYPE_NAME: 
			return stringFieldTextField.getText();
		case DBNamesManager.DATE_FIELD_TYPE_NAME:
			return dateFieldDatePicker.getJFormattedTextField().getText();
		}
		return null; //is not supposed to happen, if this happens there is an error
	}

	private void initializeShowColumnsForPanel()
	{
		//Initialize the "show columns for" label
		initializeShowColumnsForLabelPanel();
		//Create an empty panel for spacing on the right of the showColumnsFor panel
		JPanel emptyPanel = new JPanel();
		Component rigidAreaRight = Box.createRigidArea(new Dimension(100, 100));
		emptyPanel.add(rigidAreaRight);
		allOptionsPanel.add(emptyPanel, BorderLayout.EAST);
		
		//Create the showColumnsForPanel
		showColumnsForPanel = new JPanel();
		//Give the panel a lowered bevel border
		Border borderLoweredBevel = BorderFactory.createLoweredBevelBorder();
		showColumnsForPanel.setBorder(borderLoweredBevel);
		//Set the panel to have a gridbaglayout
		GridBagLayout gbl_entityOptionsPanel = new GridBagLayout();
		gbl_entityOptionsPanel.columnWidths = new int[] {20, 100, 100, 100};
		gbl_entityOptionsPanel.rowHeights = new int[] {0, 30, 30, 30, 30, 0};
		gbl_entityOptionsPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_entityOptionsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		showColumnsForPanel.setLayout(gbl_entityOptionsPanel);
		allOptionsPanel.add(showColumnsForPanel, BorderLayout.CENTER);
		
		if(lblCheckBoxesStatus == null)
			initializeCheckBoxStatusLabel();
		
		//Set the initial state of the panel to update based on the initial state of the entity combobox
		displayFieldCheckBoxesForEntity((String) comboBoxEntityType.getSelectedItem());
	}
	
	private void initializeCheckBoxStatusLabel() {
		lblCheckBoxesStatus = new JLabel("filler text");
		lblCheckBoxesStatus.setForeground(lblCheckBoxesStatus.getBackground());
		lblCheckBoxesStatus.setFont(CHECKBOX_STATUS_FONT);
	}

	/**
	 * Display the field column header checkboxes for the given entity
	 * @param entityName the entity for whose fields checkboxes will be displayed
	 */
	private void displayFieldCheckBoxesForEntity(String entityName)
	{
		//clear the current checkboxes
		clearCurrentCheckBoxes();
		//display checkboxes for fields of the given entity
		switch (entityName){
		case DBNamesManager.ITEM_ENTITY_DISPLAYNAME: 
			displayFieldCheckBoxesOptions(itemFieldsCheckBoxesMap);
			break;
		case DBNamesManager.PALLET_ENTITY_DISPLAYNAME: 
			displayFieldCheckBoxesOptions(palletFieldsCheckBoxesMap);
			break;
		case DBNamesManager.ORDER_ENTITY_DISPLAYNAME:
			displayFieldCheckBoxesOptions(orderFieldsCheckBoxesMap);
			break;
		}
		//TODO check this label
		addRowToFieldCheckBoxes(null,lblCheckBoxesStatus,null);
	}
	
	private HashMap<String,JCheckBox> getCheckBoxMapForEntity(String entityName)
	{
		switch (entityName){
		case DBNamesManager.ITEM_ENTITY_DISPLAYNAME: 
			return itemFieldsCheckBoxesMap;
		case DBNamesManager.PALLET_ENTITY_DISPLAYNAME: 
			return palletFieldsCheckBoxesMap;
		case DBNamesManager.ORDER_ENTITY_DISPLAYNAME:
			return orderFieldsCheckBoxesMap;
		}
		return null; //TODO add error handling or somethin
	}
	
	/**
	 * Display the field checkboxes that are in the given map.
	 * @param map the map containing the checkboxes to display
	 */
	private void displayFieldCheckBoxesOptions(HashMap<String, JCheckBox> map) {
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
			addRowToFieldCheckBoxes(firstBox, secondBox, thirdBox);
		}
	}

	/**
	 * Clear the current checkboxes from the showColumnsForPanel
	 */
	private void clearCurrentCheckBoxes()
	{
		nextOptionRow = STARTING_OPTION_ROW;
		showColumnsForPanel.removeAll();
		showColumnsForPanel.revalidate();
		showColumnsForPanel.repaint();
	}
	
	/**
	 * Initialize the update button panel and its update button.
	 */
	private void initializeUpdateButtonPanel(){
	
		//Create the update button panel and add it
		updateButtonPanel = new JPanel();
		allOptionsPanel.add(updateButtonPanel, BorderLayout.SOUTH);
		updateButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		BoxLayout updatePanelLayout = new BoxLayout(updateButtonPanel, BoxLayout.X_AXIS);
		updateButtonPanel.setLayout(updatePanelLayout);
		
		
		
		//create a new label for error status and make the text invisible at first
		lblErrorStatus = new JLabel("Placeholder text");
		lblErrorStatus.setForeground(updateButtonPanel.getBackground());
		lblErrorStatus.setFont(LABEL_FONT);
		//make the error status stick to the left
		Box errorStatusBox = Box.createHorizontalBox();
	    errorStatusBox.add(lblErrorStatus);
	    errorStatusBox.add(Box.createGlue());
		updateButtonPanel.add(errorStatusBox);
		
		//create a label to have the loading gif as its icon
		lblLoadingIcon = new JLabel("Getting info...");
		ImageIcon loadingGif = new ImageIcon(getClass().getClassLoader().getResource(LOADING_GIF_ICON_NAME));
		//lblLoadingIcon.setForeground(updateButtonPanel.getBackground());
		lblLoadingIcon.setFont(LABEL_FONT);
		lblLoadingIcon.setIcon(loadingGif);
		lblLoadingIcon.setVisible(false);
		lblLoadingIcon.setHorizontalTextPosition(SwingConstants.LEFT);
		updateButtonPanel.add(lblLoadingIcon);
		
		//Create the update button and give it an action listener to gather the
		//selected options from the UI elements and 
		updateButton = new JButton("Update");
		updateButton.setFont(BUTTON_FONT);
		updateButtonPanel.add(updateButton);
		updateButton.addActionListener(new ActionListener() {
			

			@Override
			public void actionPerformed(ActionEvent ae) {
				String entityName = (String) comboBoxEntityType.getSelectedItem();
				String fieldName = (String) comboBoxField.getSelectedItem();
				String fieldModifier = (String) comboBoxFieldModifier.getSelectedItem();
				String fieldModifierValue = getFieldModifierValue(fieldName);
				updateTableProcess = new SwingWorker<Boolean, Void>() {

			        @Override
			        protected Boolean doInBackground() throws Exception {
			        	lblLoadingIcon.setVisible(true);
			            boolean success = updateTableBasedOnSelection(entityName, fieldName, fieldModifier, fieldModifierValue);
			            if(success)
			            {
				            currentTableEntity = entityName;
							setAreCheckBoxesAreEnabled(true); //TODO double check this
							displayColumnHeaderCheckboxesStatus("", lblCheckBoxesStatus.getBackground()); //TODO this is bad
							clearErrorStatus();
							mainTable.updateColumnWidths();
			            }else{
			            	boolean modifierValueEntered = (fieldModifierValue == null) 
									|| (!fieldModifierValue.equals(DBNamesManager.getDefaultFieldModifierValue()));
			            	String error = "There are no results for " + entityName;
							if(modifierValueEntered)
								error = error + " with " + fieldName + " " + fieldModifier + " " + fieldModifierValue;
							error += ".";
							displayErrorStatus(error);
							setAreCheckBoxesAreEnabled(false); //TODO double check this
			            }
			            return success;
			        }

			        @Override
			        protected void done() {
			            System.out.println("you gotta make this work correctly");  
			        	lblLoadingIcon.setVisible(false);
			        	updateFieldModifierComponent(fieldName);
			        }
			    };
			    updateTableProcess.execute();
			}
		});
	}
	
	public void displayErrorStatus(String errorText)
	{
		if(!errorText.equals(lblErrorStatus.getText()))
		{
			lblErrorStatus.setForeground(Color.RED);
    		lblErrorStatus.setText(errorText);
			Timer errorDisplayTimer = new Timer(ERROR_DISPLAY_TIME_MS, new ActionListener() {
			    public void actionPerformed(ActionEvent evt) {
		    		clearErrorStatus();
		    }
		});
			errorDisplayTimer.setRepeats(false);
			errorDisplayTimer.start();
		}
	}
	
	public void clearErrorStatus()
	{
		lblErrorStatus.setText("");
	}
	
	/**
	 * Update the table from the database based on the selected entities, fields, and inputs
	 * @param entityName the type of the entities that will be displayed in the table
	 * @param fieldName the name of the selected field in the field modifier
	 * @param fieldModifier the description in the field modifier, i.e. "less than" or "starting with"
	 * @param fieldModifierValue the value the user entered in the field modifier entry field
	 * @return true if update successful, and false otherwise
	 */
	private boolean updateTableBasedOnSelection(String entityName, String fieldName, String fieldModifier, String fieldModifierValue){
		//TODO finish this functionality to interact with the database
		if (entityName.equals(DBNamesManager.getAllEntitySpecifierDisplayname())) 
		{
			return false; //TODO finish
		} else {
			lblLoadingIcon.setText(BUILDING_QUERY_STATUS_MESSAGE);
			String dbEntityName = DBNamesManager.getEntityDatabaseVariableByDisplayName(entityName);
			String query = "SELECT * FROM " + dbEntityName;
			//if the user has entered a modifier value
			boolean modifierValueEntered = (fieldModifierValue == null) 
										|| (!fieldModifierValue.equals(DBNamesManager.getDefaultFieldModifierValue()));
			if (modifierValueEntered) 
			{
				String dbFieldName = DBNamesManager.getFieldDatabaseVariableFieldByDisplayName(fieldName);
				String modifierString = getQueryModifierString(fieldModifier,
						fieldModifierValue);
				query = query + " WHERE " + dbFieldName + modifierString;
			}
			try {
			lblLoadingIcon.setText(EXECUTING_QUERY_STATUS_MESSAGE);
			ResultSet result = controller.SQL_Handler.executeCustomQuery(query);
			if(result.next())
			{
				lblLoadingIcon.setText(UPDATING_TABLE_STATUS_MESSAGE);
				Object[][] data = controller.SQL_Handler.getResultSetAs2DObjArray(result);			
				String[] columnNames = controller.SQL_Handler.getColumnNamesFromResultSet(result);
				SQL_Handler.updateColumnNamesToDisplayNames(columnNames);
				WIMSTableModel tabelModel = new WIMSTableModel(data, columnNames);
				//headersToColumnMap = mainTable.getTableColumnByHeaderMap();
				mainTable.setModel(tabelModel);
				return true;
			}else{
				return false;
			}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false; //TODO handle returning false on SQL error vs no query results
			}
			
		}
		//TODO make it update the table neatly, probably in another method
		//TODO THISIS BAD
	}
	
	

	private String getQueryModifierString(String fieldModifier, String fieldModifierValue) {
		String modifierString = "";
		switch (fieldModifier){
		case DBNamesManager.NUMERIC_FIELD_LESS_THAN: 
			modifierString = " < " + fieldModifierValue;
			break;
		case DBNamesManager.NUMERIC_FIELD_GREATER_THAN: 
			modifierString = " > " + fieldModifierValue;
			break;
		case DBNamesManager.NUMERIC_FIELD_EQUAL_TO:
			modifierString = " = " + fieldModifierValue;
			break;
		case DBNamesManager.STRING_FIELD_STARTING_WITH:
			modifierString = " LIKE " + fieldModifierValue + "%";
			break;
		case DBNamesManager.STRING_FIELD_ENDING_WITH:
			modifierString = " LIKE " + "%" + fieldModifierValue;
			break;
		case DBNamesManager.STRING_FIELD_CONTAINS:
			modifierString = " LIKE " + "%" + fieldModifierValue + "%"; //TODO check syntaxes for all of these
			break;
		case DBNamesManager.STRING_FIELD_THAT_IS:
			modifierString = " = " + fieldModifierValue;
			break;
		case DBNamesManager.DATE_FIELD_BEFORE:
			modifierString = " < " + "\'" + fieldModifierValue + "\')";
			break;
		case DBNamesManager.DATE_FIELD_AFTER:
			modifierString = " > " + "\'" + fieldModifierValue + "\')";
			break;
		case DBNamesManager.DATE_FIELD_ON:
			modifierString = " = " + "\'" + fieldModifierValue + "\')";
			break;
		}
		return modifierString;
	}
	
	/**
	 * Initialize the table panel and the table within.
	 */
	private void initializeTablePanel()
	{
		JPanel resizingPanelForTable = new JPanel();
		BoxLayout resizingPanelLayout = new BoxLayout(resizingPanelForTable, BoxLayout.X_AXIS);
		resizingPanelForTable.setLayout(resizingPanelLayout);
		frame.getContentPane().add(resizingPanelForTable);
		
		tablePanel = new JPanel();
		resizingPanelForTable.add(tablePanel);
		tablePanel.setLayout(new BorderLayout(0, 0));
		
		//make a new wimstable, override the viewport tracking so autoresize and scroll is utilized
		mainTable = new WIMSTable();
		mainTable.setFont(TABLE_FONT);
		String[] defaultColNames = MainWindow.getTestTableColumnNames();//TODO test data, is only temporary
		Object[][] defaultData = MainWindow.getTestTableData();//TODO test data, is only temporary
		WIMSTableModel tabelModel = new WIMSTableModel(defaultData, defaultColNames);
		
		mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		mainTable.setFillsViewportHeight(true);
		mainTable.setModel(tabelModel);
		mainTable.setRowSorter(new TableRowSorter(tabelModel));
		//TODO investigate use of width adjuster
		TableWidthAdjuster = new WidthAdjuster(mainTable);
	
		mainTable.updateColumnWidths();
		initializeTableScrollPane();
		//mainTableScrollPane.setViewportView(mainTable);
		initializeTablePanelBorderSpacing();
	}
	
	private void initializeTableScrollPane()
	{
		mainTableScrollPane = new JScrollPane(mainTable);
		tablePanel.add(mainTableScrollPane);
		mainTableScrollPane.setViewportView(mainTable);
		
		mainTableScrollPane.addComponentListener(new ComponentAdapter() {
		    @Override
		    public void componentResized(final ComponentEvent e) {
		        if (mainTable.getPreferredSize().width < mainTableScrollPane.getWidth()) {
		        	mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		        } else {
		        	mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		        }
		    }
		});
	}

	/**
	 * Add a row of components to the checkbox display panel. Any or all components can be null,
	 * and (a) blank space(s) will be left in the row.
	 * @param firstColumn	the component to add in the first column of this row
	 * @param secondColumn	the component to add in the second column of this row
	 */
	private void addRowToFieldCheckboxes(Component firstColumn, Component secondColumn)
	{
		//add a rigid area to the left to put a slight left margin before the checkboxes
		Component rigidAreaLeft = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidAreaLeft = new GridBagConstraints();
		gbc_rigidAreaLeft.anchor = GridBagConstraints.WEST;
		gbc_rigidAreaLeft.insets = new Insets(0, 0, 5, 5);
		gbc_rigidAreaLeft.gridx = 0;
		gbc_rigidAreaLeft.gridy = nextOptionRow;
		showColumnsForPanel.add(rigidAreaLeft, gbc_rigidAreaLeft);
		
		//if the first column is null, add nothing
		if(firstColumn != null)
		{
			GridBagConstraints gbc_firstColumn = new GridBagConstraints();
			gbc_firstColumn.anchor = GridBagConstraints.WEST;
			gbc_firstColumn.insets = new Insets(0, 0, 5, 5);
			gbc_firstColumn.gridx = 1; //first column
			gbc_firstColumn.gridy = nextOptionRow; //add on the nextOptionRow. this keeps track of the next row
			showColumnsForPanel.add(firstColumn, gbc_firstColumn);
		}
		
		//if the second column is null, add nothing
		if(secondColumn != null)
		{
			GridBagConstraints gbc_secondColumn = new GridBagConstraints();
			gbc_secondColumn.fill = GridBagConstraints.HORIZONTAL;
			gbc_secondColumn.insets = new Insets(0, 0, 5, 5);
			gbc_secondColumn.anchor = GridBagConstraints.WEST;
			gbc_secondColumn.gridx = 2; //second column
			gbc_secondColumn.gridy = nextOptionRow; //add on the nextOptionRow. this keeps track of the next row
			showColumnsForPanel.add(secondColumn, gbc_secondColumn);
		}
		//now that we are done adding to this row, increment the counter that tracks the next row index
		nextOptionRow++;
	}
	
	/**
	 * Add a row of components to the checkbox display panel. Any or all components can be null,
	 * and (a) blank space(s) will be left in the row.
	 * @param firstColumn	the component to add in the first column of this row
	 * @param secondColumn	the component to add in the second column of this row
	 * @param thirdColumn	the component to add in the third column of this row
	 */
	private void addRowToFieldCheckBoxes(Component firstColumn, Component secondColumn, Component thirdColumn)
	{
		//if the given thirdcolumn is null, just add nothing
		if(thirdColumn != null)
		{
			//get the gridbag layout constraints 
			GridBagConstraints gbc_thirdColumn = new GridBagConstraints();
			gbc_thirdColumn.anchor = GridBagConstraints.WEST;
			gbc_thirdColumn.insets = new Insets(0, 0, 5, 5);
			gbc_thirdColumn.gridx = 3; //3rd column
			gbc_thirdColumn.gridy = nextOptionRow; //add on the nextOptionRow. this keeps track of the next row
			showColumnsForPanel.add(thirdColumn, gbc_thirdColumn);
		}
		
		//add the first two columns to the row
		addRowToFieldCheckboxes(firstColumn, secondColumn);
	}
	
	/**
	 * Initialize the RigidAreas that form the margin around the table view
	 */
	private void initializeTablePanelBorderSpacing()
	{
		Dimension marginDimension = new Dimension(TABLE_PANEL_MARGIN, TABLE_PANEL_MARGIN);
		Component leftMargin = Box.createRigidArea(marginDimension);
		tablePanel.add(leftMargin, BorderLayout.WEST);
		
		Component rightMargin = Box.createRigidArea(marginDimension);
		tablePanel.add(rightMargin, BorderLayout.EAST);
		
		Component bottomMargin = Box.createRigidArea(marginDimension);
		tablePanel.add(bottomMargin, BorderLayout.SOUTH);
	}
	
	/**
	 * Get a date picker component
	 * @return a date picker component
	 */
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
	
	//TODO all of the stuff beneath this should be in some type of controller class
	public static Object[][] getTestTableData(){
		String[] testStringDates = {"4-18-2017", "4-18-2016", "3-25-2014", "7-23-1996", "1-1-2000", "12-25-2005", "10-31-2005"};
		java.util.Date[] testDates = new java.util.Date[testStringDates.length];
		java.sql.Date[] testSQLDates = new java.sql.Date[testStringDates.length];
		SimpleDateFormat sdf1 = new SimpleDateFormat("mm-dd-yyyy"); 
		for(int i = 0; i < testStringDates.length; i++)
		{
			String dateString = testStringDates[i];
			java.util.Date date;
			try {
				date = sdf1.parse(dateString);
				java.sql.Date sqlDate = new java.sql.Date(date.getTime()); 
				testDates[i] = date;
				testSQLDates[i] = sqlDate;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String[] testFarewells = {"cya", "good bye", "bye there", "farewell", "good night", "i'm outtie", "pce dude"};
		String[] testGreetings = {"hey", "hello", "hi there", "greetings", "good evening", "sup", "sah dude"};
		int[] testInts = {121, 312, -145, 2, 0, 65, 4234};
		double[] testFloats = {23.123, 4321.344, 3.14, 420.666, 15.0, -5000.234, 0};
		String[] testIntStrings = {"123456", "-123456", "999999", "07123", "-0756", "099999", "099999"};
		String[] testIDStrings = {"123-456", "609-405", "916-155", "420-666", "980-534", "754-228", "111-111"};
		
		Object[][] testData = new Object[7][12];
		
		for(int i = 0; i < testGreetings.length; i++)
		{
			testData[i][0] = testGreetings[i];
			testData[i][1] = testFarewells[i];
			testData[i][2] = testStringDates[i];
			testData[i][3] = testDates[i];
			testData[i][4] = testSQLDates[i];
			testData[i][5] = testInts[i];
			testData[i][6] = testFloats[i];
			testData[i][7] = testIntStrings[i];
			testData[i][8] = testIDStrings[i];		
			testData[i][9] = "testCellRow" + i + "Col" + 9;
			testData[i][10] = "test2CellRow" + i + "Col" + 10;
			testData[i][11] = "beep";
		}
		return testData;
	}
	
	public static String[] getTestTableColumnNames()
	{
		String[] testColNames = {"Greeting", "Farewell", "String Date", "Jave Util Date", 
				"SQL Date", "Ints", "Floats", "Int Strings", "ID Strings", 
				"Testcol1", "TestCol2", "Test column with a really long name but small contents that will test column resizing"};
		return testColNames;
	}
	
	

}