package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

import controller.ComponentProvider;
import controller.DBNamesManager;
import controller.ErrorStatusReportable;
import controller.MainWindowInfoController;
import controller.SQL_Handler;
import controller.WIMSTable;
import controller.WIMSTableModel;
import controller.WidthAdjuster;

public class MainWindow implements ErrorStatusReportable{

	private JFrame frame;
	
	//Fonts for different UI elements
	private static final String STANDARD_FONT_NAME = "Tahoma";
	private static final int SMALLER_COMPONENT_FONT_SIZE = 14;
	private static final int TABLE_FONT_SIZE = 14;
	private static final Font LABEL_FONT = new Font(STANDARD_FONT_NAME, Font.PLAIN, 18);
	private static final Font QUERY_RESULT_STATUS_FONT = new Font(STANDARD_FONT_NAME, Font.PLAIN, 16);
	private static final Font LOADING_STATUS_FONT = new Font(STANDARD_FONT_NAME, Font.PLAIN, 14);
	private static final Font MENUBAR_FONT = new Font(STANDARD_FONT_NAME, Font.PLAIN, SMALLER_COMPONENT_FONT_SIZE);
	private static final Font BUTTON_FONT = new Font(STANDARD_FONT_NAME, Font.PLAIN, 28);
	
	
	private static final Font TABLE_FONT = new Font("Tahoma", Font.PLAIN, TABLE_FONT_SIZE);
	private static final Font TABLE_HEADER_FONT = new Font("Tahoma", Font.PLAIN, TABLE_FONT_SIZE);
	
	
	//Max width for when only max height is going to be restricted (setMaximumSize requires height&width)
	private static final int IRRELEVANT_MAX_DIMENSION = Integer.MAX_VALUE;
	private static final int IRRELEVANT_MIN_DIMENSION = 1;
	
	//Dimension constants for entire application window
	private static final int DEFAULT_WINDOW_WIDTH = 1000;
	private static final int DEFAULT_WINDOW_HEIGHT = 700;
	private static final int MIN_WINDOW_WIDTH = 975;
	private static final int MIN_WINDOW_HEIGHT = 675;
	
	//Dimension constants for top menubar
	private static final int MIN_MENUBAR_PANEL_WIDTH = MIN_WINDOW_WIDTH;
	private static final int MIN_MENUBAR_PANEL_HEIGHT = 25;
	private static final int MAX_MENUBAR_PANEL_WIDTH = IRRELEVANT_MAX_DIMENSION;
	private static final int MAX_MENUBAR_PANEL_HEIGHT = 25;
	
	//Dimension constants for options panel (the panel containing checkboxes)
	static final int MAX_TAB_PANEL_HEIGHT = 265;
	static final int MAX_TAB_PANEL_WIDTH = MIN_WINDOW_WIDTH - 35;
	private static final Dimension MAX_TAB_DIM = new Dimension(MAX_TAB_PANEL_WIDTH, MAX_TAB_PANEL_HEIGHT);
	
	//How many pixels will be between the table panel and the edge of the window
	static final int TABLE_PANEL_MARGIN = 20;
	//how far with the update button be from the right
	public static final int UPDATE_BUTTON_RIGHT_SPACING = 50;
	//how many pixels will be to the left/right of the options panel
	public static final int OPTIONSPANEL_MAX_LEFT_SPACE = 50;
	private static final int OPTIONSPANEL_MAX_RIGHT_SPACE = 50;
		
	
	
	private ColumnHeaderControllerPanel showColumnsForPanel;
	
	//Wrapper panel for the table scrollpane
	private JPanel tablePanel;
	//Scrollpane for the table
	private JScrollPane mainTableScrollPane;
	//main table in scrollpane, a custom table class
	private WIMSTable mainTable;
	//width adjuster manager for table
	private WidthAdjuster TableWidthAdjuster;
	
	private String loggedInEmpID;
	private boolean loggedInIsManager;
	
	
	//wrapper panel that contains entity&field selection panel, showcolumnsfor panel,
	//and update button panel
	private JPanel allOptionsPanel;
	//panel that contains entity, field, and field options selection
	private EntityAndFieldPanel entityAndFieldSelectPanel;
	
	//components for update button panel
	private JPanel updateButtonPanel;
	private JButton updateButton;
	private JLabel lblErrorStatus;
	//whether error is active, used so multiple threads arent created
	private boolean errorIsActive;
	//how long to display error messages for, in milliseconds
	private Timer errorDisplayTimer;
	protected static final int ERROR_DISPLAY_TIME_MS = 7000;
	protected static final int SUCCESS_DISPLAY_TIME_MS = 15000;
	protected static final Color ERROR_DISPLAY_COLOR = Color.RED;//new Color(112, 211, 0);
	protected static final Color SUCCESS_DISPLAY_COLOR = new Color(58, 193, 0);
	protected static final Color NEUTRAL_DISPLAY_COLOR = Color.BLACK;//new Color(112, 211, 0);
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
	private JMenuItem modifyPallet;

	

	private JLabel lblLoadingIcon;
	private String currentTableEntity; 
	
	private static final String LOADING_GIF_ICON_NAME = "loading.gif";

	private static final String BUILDING_QUERY_STATUS_MESSAGE = "Building query...";

	private static final String EXECUTING_QUERY_STATUS_MESSAGE = "Fetching data from database...";

	private static final String UPDATING_TABLE_STATUS_MESSAGE = "Updating table with data...";

	private MainWindowInfoController infoController;

	private int tableSelectedIndex;

	private JTabbedPane tabsWithReports;

	private ReportsPanel reportsPanel;

	private static final String TEST_EMP_ID = "894189";
	private static final boolean TEST_EMP_ISMANAGER = true;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainWindow window = new MainWindow("123456",true);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//TODO change to query database
	public MainWindow(String loggedInEmpID, boolean loggedInIsManager){
		this.loggedInEmpID = loggedInEmpID;
		this.loggedInIsManager = loggedInIsManager;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		initializeMainFrame();
		initializeMenuBar();
		initializeInfoController();
		if(this.loggedInIsManager)
		{
			initializeTabbedPanel();
			initializeAllOptionsPanel();
			initializeReportsPanel();
		}else{
			initializeAllOptionsPanel();
		}
		initializeTablePanel();
	}

	private void initializeReportsPanel() {
		//Initialize the panel that contains the entity/field/search comboboxes
		reportsPanel = new ReportsPanel(infoController);
		tabsWithReports.addTab("Reports", reportsPanel);
	}

	private void initializeTabbedPanel() {
		// TODO Auto-generated method stub
		UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(2,0,0,0));
		UIManager.getDefaults().put("TabbedPane.tabsOverlapBorder", false);
		tabsWithReports = new JTabbedPane();
		frame.getContentPane().add(tabsWithReports);
	}

	private void initializeInfoController() {
		currentTableEntity = "";
		infoController = new MainWindowInfoController(this);
		showColumnsForPanel = new ColumnHeaderControllerPanel(infoController);
		entityAndFieldSelectPanel = new EntityAndFieldPanel(infoController);
		
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
		if(this.loggedInIsManager)
			initializeManagerMenu();
	}
	
	/**
	 * Initialize the items submenu (and all of its JMenuItems) on the menubar
	 */
	private void initializeItemsMenu()
	{
		manageItemsMenu = new JMenu("Manage Items");
		manageItemsMenu.setFont(MENUBAR_FONT);
		menubar.add(manageItemsMenu);
		
		addItem = new JMenuItem("Add or Edit Item");
		manageItemsMenu.add(addItem);
		addItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				launchScanWindow();
			}
		});
		
//		updateItem = new JMenuItem("Update Item in Database");
//		manageItemsMenu.add(updateItem);
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
		scanInOrderMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				launchOrderWindow();
			}
		});
		
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
				launchManageEmployees(loggedInEmpID);
			}
		});
		
		extraMenuItem = new JMenuItem("An extra menu item to be filled out later");
		manageEmployeesMenu.add(extraMenuItem);
	}

	private void launchManageEmployees(String initializerEmpID)
	{
		ManageEmployees manageEmployeesWindow = new ManageEmployees(this.loggedInEmpID, this.loggedInIsManager, initializerEmpID);
		manageEmployeesWindow.getFrame().setVisible(true);
	}
	
	private void launchScanWindow(String initialItemNumber){
		ScanWindow scanWindow = new ScanWindow(this.loggedInIsManager, initialItemNumber);
		scanWindow.getFrame().setVisible(true);
	}
	
	private void launchScanWindow(){
		ScanWindow scanWindow = new ScanWindow(this.loggedInIsManager);
		scanWindow.getFrame().setVisible(true);
	}
	
	private void launchPalletWindow(String initialPalletNumber){
		if(this.loggedInIsManager){
		PalletWindow palletWindow = new PalletWindow(initialPalletNumber);
		palletWindow.getFrame().setVisible(true);
		}
	}
	
	private void launchPalletWindow(){
		if(this.loggedInIsManager){
		PalletWindow palletWindow = new PalletWindow();
		palletWindow.getFrame().setVisible(true);
		}
	}
	
	private void launchOrderWindow(){
		OrderWindow orderWindow = new OrderWindow(this.loggedInIsManager);
		orderWindow.getFrame().setVisible(true);
	}
	
	private void launchOrderWindow(String orderID){
		OrderWindow orderWindow = new OrderWindow(this.loggedInIsManager, orderID);
		orderWindow.getFrame().setVisible(true);
	}
	
	private void launchWarehouseWindow(String warehouseID) {
		WarehouseWindow warehouseWindow = new WarehouseWindow();
		warehouseWindow.getFrame().setVisible(true);
	}
	
	/**
	 * Initialize the reports submenu (and all of its JMenuItems) on the menubar
	 */
	private void initializeManagerMenu()
	{
		reportsMenu = new JMenu("Manager Options");
		reportsMenu.setFont(MENUBAR_FONT);
		menubar.add(reportsMenu);
		
		reportMenu = new JMenuItem("Reports");
		reportsMenu.add(reportMenu);
		
		modifyPallet = new JMenuItem("Modify Pallet");
		reportsMenu.add(modifyPallet);
		modifyPallet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				launchPalletWindow();
			}
		});
		
		reportsMenu.add(manageEmployeesItem);
		menubar.remove(manageEmployeesMenu);
	}
	
	/**
	 * Initialize the panel that contains the entity, field, and field options selection,
	 * as well as the selection of what fields to show columns for, and the update button panel.
	 */
	private void initializeAllOptionsPanel() {
		
		//Initialize the panel that contains the entity/field/search comboboxes
		allOptionsPanel = new JPanel();
		allOptionsPanel.setMaximumSize(new Dimension(MAX_TAB_PANEL_WIDTH, MAX_TAB_PANEL_HEIGHT));
		allOptionsPanel.setLayout(new BorderLayout(0, 0));
		
		//A wrapper panel with a boxlayout so that the maximum size of the entity selection
		//and field columns selection panel is respected. Needed since borderlayout does
		//not respect maximum size.
		JPanel resizingPanelForOptions = new JPanel();
		BoxLayout resizingPanelLayout = new BoxLayout(resizingPanelForOptions, BoxLayout.X_AXIS);
		resizingPanelForOptions.setLayout(resizingPanelLayout);
		//Add a blank panel so there is a panel on the left that can resize to fill the empty space
		//needed to keep the main panel in the middle when the window resizes
		JPanel leftPanelForSpacing = new JPanel();
		leftPanelForSpacing.setMaximumSize(new Dimension(OPTIONSPANEL_MAX_LEFT_SPACE, IRRELEVANT_MAX_DIMENSION));
		leftPanelForSpacing.add(Box.createRigidArea(new Dimension(TABLE_PANEL_MARGIN, IRRELEVANT_MIN_DIMENSION)));
		resizingPanelForOptions.add(leftPanelForSpacing);
		//Add the panel to the middle
		resizingPanelForOptions.add(allOptionsPanel);
		//Add a blank panel so there is a panel on the right that can resize to fill the empty space
		//needed to keep the main panel in the middle when the window resizes
		resizingPanelForOptions.add(new JPanel());
		//Give the panel an etched border
		Border borderEtched = BorderFactory.createEtchedBorder();
		allOptionsPanel.setBorder(borderEtched);
		if(this.loggedInIsManager){
			tabsWithReports.addTab("Main", resizingPanelForOptions);
		}else{
			frame.getContentPane().add(resizingPanelForOptions);
		}
		
		initializeShowColumnsForPanel();
		initializeEntityAndFieldSelectPanel();
		
		initializeUpdateButtonPanel();
	}
	
	private void initializeShowColumnsForPanel() {
		showColumnsForPanel = new ColumnHeaderControllerPanel(infoController);
		allOptionsPanel.add(showColumnsForPanel, BorderLayout.CENTER);
	}

	private void initializeEntityAndFieldSelectPanel(){
		//TODO add this to a fields
		entityAndFieldSelectPanel = new EntityAndFieldPanel(infoController);
		allOptionsPanel.add(entityAndFieldSelectPanel, BorderLayout.NORTH);
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
		lblErrorStatus.setFont(QUERY_RESULT_STATUS_FONT);
		//make the error status stick to the left
		Box errorStatusBox = Box.createHorizontalBox();
	    errorStatusBox.add(lblErrorStatus);
	    errorStatusBox.add(Box.createGlue());
		updateButtonPanel.add(errorStatusBox);
		
		//create a label to have the loading gif as its icon
		lblLoadingIcon = new JLabel("Getting info...");
		ImageIcon loadingGif = new ImageIcon(getClass().getClassLoader().getResource(LOADING_GIF_ICON_NAME));
		//lblLoadingIcon.setForeground(updateButtonPanel.getBackground());
		lblLoadingIcon.setFont(LOADING_STATUS_FONT);
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
				String entityName = entityAndFieldSelectPanel.getSelectedEntity();
				String fieldName = entityAndFieldSelectPanel.getSelectedField();
				String fieldModifier = entityAndFieldSelectPanel.getSelectedModifier();
				String fieldModifierValue = entityAndFieldSelectPanel.getFieldModifierValue(fieldName);
				System.out.println("field modifier value is " + fieldModifierValue);
				updateTableProcess = new SwingWorker<Boolean, Void>() {

			        @Override
			        protected Boolean doInBackground() throws Exception {
			        	lblLoadingIcon.setVisible(true);
			        	//update the table and save whether it was successfully updated
			            boolean success = updateTableBasedOnSelection(entityName, fieldName, fieldModifier, fieldModifierValue);
			            boolean modifierValueEntered = entityAndFieldSelectPanel.isModifierValueEntered();
		            	String statusMessageEnding = entityName;
		            	if(modifierValueEntered)
		            		statusMessageEnding = statusMessageEnding + " with " + fieldName + " " + fieldModifier + " " + fieldModifierValue;
		            	statusMessageEnding += ".";
			            if(success) //if the table was successfully updated
			            {
			            	//activate the checkboxes for this entity and clear the checkbox warning
				            showColumnsForPanel.setAreCheckBoxesAreEnabled(entityName, true); //TODO double check this
				            showColumnsForPanel.clearErrorStatus(); 
				            //clear the current error status
							clearErrorStatus();
							//update the column widths of the table
							mainTable.updateColumnWidths();
							//if there are results, display the success message for the input query
							String successMessage = "Displaying results for " + statusMessageEnding;
							displaySuccessStatus(successMessage);
			            }else{ //if the table was not successfully updated 
			            	//show the error that there were no results for the input query
			            	String error = "There are no results for " + statusMessageEnding;
							displayErrorStatus(error);
			            }
			            return success;
			        }

			        @Override
			        protected void done() {
			        	//System.out.println("done!!!!!!!!!!!!!!!!!");
			        	//when the update finishes
			        	//ugly fix to bug of scrollpane not showing scrollbar unless you resize it after updating the table
			        	int scrollPaneOrigWidth = mainTableScrollPane.getWidth();
			        	int scrollPaneOrigHeight = mainTableScrollPane.getHeight();
			        	mainTableScrollPane.setSize(scrollPaneOrigWidth+1, scrollPaneOrigHeight+1);
			        	mainTableScrollPane.setSize(scrollPaneOrigWidth, scrollPaneOrigHeight);
			        	//update the current table entity to whatever entity was selected when the table updated
			        	//making the loading icon invisible
			        	lblLoadingIcon.setVisible(false);
			        }
			    };
			    updateTableProcess.execute();
			}
		});
		Component rigidAreaRight = Box.createRigidArea(new Dimension(UPDATE_BUTTON_RIGHT_SPACING, UPDATE_BUTTON_RIGHT_SPACING));
		updateButtonPanel.add(rigidAreaRight);
	}
	
	
	/**
	 * Update the table from the database based on the selected entities, fields, and inputs
	 * @param entityName the type of the entities that will be displayed in the table
	 * @param fieldName the name of the selected field in the field modifier
	 * @param fieldModifier the description in the field modifier, i.e. "less than" or "starting with"
	 * @param fieldModifierValue the value the user entered in the field modifier entry field
	 * @return true if update successful, and false otherwise
	 */
	public boolean updateTableBasedOnSelection(String entityName,
			String fieldName, String fieldModifier, String fieldModifierValue) {
		// TODO finish this functionality to interact with the database

		// we are looking at an actual entity
		// say that the query is being built on the loading status
		lblLoadingIcon.setText(BUILDING_QUERY_STATUS_MESSAGE);
		// get the database variable for the selected entity
		String dbEntityName = DBNamesManager
				.getEntityDatabaseVariableByDisplayName(entityName);

		boolean modifierValueEntered = entityAndFieldSelectPanel
				.isModifierValueEntered();
		ResultSet queryResult = null;
		Object[][] data;
		String[] columnNames;
		try {
			// try executing the query
			// say that the query is being executed on the loading status
			lblLoadingIcon.setText(EXECUTING_QUERY_STATUS_MESSAGE);
			// if the selected entity is items
			if (entityName.equals(DBNamesManager.getItemEntityDisplayname())) 
			{
				// call separate queries to handle item categories and put them
				// into one column
				if (modifierValueEntered) 
				{
					String dbFieldName = DBNamesManager
							.getFieldDatabaseVariableFieldByDisplayName(fieldName);
					queryResult = SQL_Handler.getAllFromItemsWithCategory(
							dbFieldName, fieldModifier, fieldModifierValue);
				} else 
				{
					queryResult = SQL_Handler.getAllFromItemsWithCategory();
				}
			} else if (entityName.equals(DBNamesManager.getEmployeeEntityDisplayname()))
			{
				if (modifierValueEntered) 
				{
					String dbFieldName = DBNamesManager
							.getFieldDatabaseVariableFieldByDisplayName(fieldName);
					queryResult = SQL_Handler.getEmployeesNoSalt(dbFieldName, fieldModifier, fieldModifierValue);
				} else 
				{
					queryResult =  SQL_Handler.getEmployeesNoSalt();
				}
			}else if (entityName.equals(DBNamesManager.getPalletEntityDisplayname())) 
			{
				// call separate queries to handle item categories and put them
				// into one column
				if (modifierValueEntered) 
				{
					String dbFieldName = DBNamesManager
							.getFieldDatabaseVariableFieldByDisplayName(fieldName);
					queryResult = SQL_Handler.getPalletsWithSubloCoord(
							dbFieldName, fieldModifier, fieldModifierValue);
				} else 
				{
					queryResult = SQL_Handler.getPalletsWithSubloCoord(); //TODO //SQL_Handler.getPalletsWithSubloCoord();
				}
			} else if (modifierValueEntered) 
			{ // if there is a modifier value
												// get the database variable for
												// the selected field
				String dbFieldName = DBNamesManager
						.getFieldDatabaseVariableFieldByDisplayName(fieldName);
				// get the modifier string, i.e. "less than 10"
				queryResult = SQL_Handler.getAllFromTable(dbEntityName,
						dbFieldName, fieldModifier, fieldModifierValue);
			} else 
			{// there is no modifier value
				queryResult = SQL_Handler.getAllFromTable(dbEntityName);
			}

			// save the data and column names into arrays
			data = controller.SQL_Handler.getResultSetAs2DObjArray(queryResult);
			columnNames = controller.SQL_Handler
					.getColumnNamesFromResultSet(queryResult);

			// if the results arent empty, if there is a next value
			if (queryResult.next()) 
			{
				// move up one so we dont skip the first value
				// result.previous();


				// say that the table is being updated on the loading status
				lblLoadingIcon.setText(UPDATING_TABLE_STATUS_MESSAGE);

				// change the DB variable column names to the display names
				//SQL_Handler.updateColumnNamesToDisplayNames(columnNames); //TODO
				
				// update the data in the table to have the queried data and
				// display column names
				int updateTableRows = data.length;
				updateTable(data, columnNames);
				currentTableEntity = entityName;

				// now that we have the data, return whether it actually has
				// data in it just in case
				boolean success = updateTableRows > 0;
				return success;
			} else 
			{ // the query result was empty, return false
						// TODO delete this sysout
				System.out.println("UHHHHH says the query is empty?");
				return false;
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
			// System.out.println("sql exception caught");
			ComponentProvider.showDBConnectionError(frame);
			return false;
		}

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
		initializeTableScrollPane();
		
		String[] initColNames = getTestTableColumnNames();
		Object[][] initData = getTestTableData();
		updateTable(initData, initColNames);

		//mainTableScrollPane.setViewportView(mainTable);
		initializeTablePanelBorderSpacing();
	}
	
	protected void handleSelectionEvent(ListSelectionEvent e) {
	    if (e.getValueIsAdjusting())
	        return;
	    //get the row and column in the view
	    
	}
	
	void updateTable(Object[][] data, String[] columnNames)
	{
		//make a new wimstable, override the viewport tracking so autoresize and scroll is utilized
		SQL_Handler.updateColumnNamesToDisplayNames(columnNames);
		mainTable = new WIMSTable();
		mainTable.setFont(TABLE_FONT);
		WIMSTableModel tabelModel = new WIMSTableModel(data, columnNames);
		
		updateTableResizeBasedOnScrollPane();
		
		mainTable.setFillsViewportHeight(true);
		mainTable.setModel(tabelModel);
		tableSelectedIndex =-1;
		mainTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ListSelectionModel selectionModel = mainTable.getSelectionModel();
		
		selectionModel.addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		        handleSelectionEvent(e);
		    }
		});
		mainTable.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent me) {
		        JTable table =(JTable) me.getSource();
		        Point p = me.getPoint();
		        int row = table.rowAtPoint(p);
		        int col = table.columnAtPoint(p);
		        if (me.getClickCount() == 2) {
		        	showOptionMenuForDataAt(row, col);
		        }
		    }
		});
		
		mainTable.setRowSorter(new TableRowSorter(tabelModel));
		TableWidthAdjuster = new WidthAdjuster(mainTable);
		
		mainTable.updateColumnWidths();
		if(mainTableScrollPane != null)
			mainTableScrollPane.setViewportView(mainTable);
	}
	
	private void updateTable(Object[][] data, String[] columnNames, boolean clearEntity){
		if(clearEntity)
			this.currentTableEntity = "";
		updateTable(data, columnNames);
	}
	
	private void showOptionMenuForDataAt(int viewRowIndex, int viewColIndex){
//		int viewRowIndex = mainTable.getSelectedRow();
//	    int viewColIndex = mainTable.getSelectedColumn();
	    
	    //get the column header
	    String colHeader = mainTable.getColumnName(viewColIndex);
	    WIMSTableModel model = (WIMSTableModel) mainTable.getModel();
	    //get the corresponding row & col index from the model
	    int modelRowIndex = mainTable.getRowSorter().convertRowIndexToModel(viewRowIndex);
	    int modelColIndex = mainTable.getColumn(colHeader).getModelIndex();
	    
	    Object[] row = model.getRowAt(modelRowIndex);
	    showMenuForRow(colHeader, row);
	    //get the value in this cell in the model
	    //Object value = model.getValueAt(modelRowIndex, modelColIndex);
	    //showMenuForValue(colHeader, value);
	    
	    //System.out.println("Cell selected at (row" + viewRowIndex + ",col" + viewColIndex
	    //		+ ") " + "(" + colHeader + ") " +  value);
	    //System.out.println(model.getValueAt(modelRowIndex, modelColIndex));
	}
	
	private void showMenuForValue(String header, Object value)
	{
		try{
			String valueString = (String) value;
			switch(header){
			case DBNamesManager.ITEM_NUMBER_FIELD_DISPLAYNAME:
				//TODO display item menu
				//here valueString = the selected item number
				System.out.println("Selected Item Number: " + valueString);
				launchScanWindow(valueString);
				break;
			case DBNamesManager.PALLET_ID_FIELD_DISPLAYNAME:
				//TODO display order menu
				//here valueString = the selected pallet id
				System.out.println("Selected Pallet ID: " + valueString);
				launchPalletWindow(valueString);
				break;
			case DBNamesManager.ORDER_NUM_FIELD_DISPLAYNAME:
				//here valueString = the selected order number
				System.out.println("Selected Order Number: " + valueString);
				launchOrderWindow(valueString);
				break;
			case DBNamesManager.WAREHOUSE_ID_FIELD_DISPLAYNAME:
				//TODO display warehouse menu
				//here valueString = the selected warehouse id
				System.out.println("Selected Warehouse ID: " + value);
				if(this.loggedInIsManager)
					launchWarehouseWindow(valueString);
				break;
			case DBNamesManager.SUBLOCATION_LOC_COORD_DISPLAYNAME:
				//TODO sublocation menu/functionality
				//here valueString = the selected sublocation coordinate
				System.out.println("Selected Sublocation Coordinate: " + valueString);
				break;
			case DBNamesManager.EMPLOYEE_ID_DISPLAYNAME:
				//here valueString = the selected employeeID
				System.out.println("Selected Employee ID: " + valueString);
				launchManageEmployees(valueString);
				break;
			}
			
		}catch (ClassCastException ex)
		{
			//do nothing, this means a cell was clicked that was
			//not an ID. All IDs are strings
			return;
		}
	}

	private void showMenuForRow(String colHeader, Object[] row)
	{
		String currentEntity = entityAndFieldSelectPanel.getSelectedEntity();
		String IDHeaderName = getIDHeaderForEntity(currentEntity);
		ArrayList<String> columnHeaders = mainTable.getColumnHeaders();
		int idNdx = columnHeaders.indexOf(IDHeaderName);
		if(idNdx >= 0)
		{
			//get the id from this row and launch the appropriate window
			String idValue = row[idNdx].toString();
			showMenuForValue(IDHeaderName, idValue);
		}else{
			displayErrorStatus("The " + IDHeaderName + " column must be visible to launch"
					+ " the " + currentEntity + " menu via clicking the table");
		}
	}
	
	private String getIDHeaderForEntity(String entityName){
		String IDHeaderName = null;
		switch(entityName){
		case DBNamesManager.ITEM_ENTITY_DISPLAYNAME:
			IDHeaderName = DBNamesManager.getItemNumberFieldDisplayname();
			break;
		case DBNamesManager.PALLET_ENTITY_DISPLAYNAME:
			IDHeaderName = DBNamesManager.getPalletIdFieldDisplayname();
			break;
		case DBNamesManager.ORDER_ENTITY_DISPLAYNAME:
			IDHeaderName = DBNamesManager.getOrderNumFieldDisplayname();
			break;
		case DBNamesManager.SUBLOCATION_ENTITY_DISPLAYNAME:
			IDHeaderName = DBNamesManager.getSublocationSimpleIndexDisplayname();
			break;
		case DBNamesManager.EMPLOYEE_ENTITY_DISPLAYNAME:
			IDHeaderName = DBNamesManager.getEmployeeIdDisplayname();
			break;
		case DBNamesManager.WAREHOUSE_ENTITY_DISPLAYNAME:
			IDHeaderName = DBNamesManager.getWarehouseIdFieldDisplayname();
			break;
		}
		return IDHeaderName;
	}
	
	
	private void updateTableResizeBasedOnScrollPane() {
		//mainTable.updateColumnWidths();
		if (mainTable.getPreferredSize().width < mainTableScrollPane.getWidth()) {
        	mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
        } else {
        	mainTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }
	}

	private void initializeTableScrollPane()
	{
		mainTableScrollPane = new JScrollPane(mainTable);
		tablePanel.add(mainTableScrollPane);
		mainTableScrollPane.setViewportView(mainTable);
		mainTableScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		mainTableScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mainTableScrollPane.addComponentListener(new ComponentAdapter() {
		    @Override
		    public void componentResized(final ComponentEvent e) {
		    	updateTableResizeBasedOnScrollPane();
		    }
		});
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
	
	public void displayErrorStatus(String errorText)
	{
		displayStatusForTime(errorText, ERROR_DISPLAY_COLOR, ERROR_DISPLAY_TIME_MS);
	}
	
	public void clearErrorStatus()
	{
		lblErrorStatus.setText("");
	}
	

	@Override
	public void displayNeutralStatus(String neutralText) {
		displayStatusForTime(neutralText, NEUTRAL_DISPLAY_COLOR, ERROR_DISPLAY_TIME_MS);
	}
	

	@Override
	public void displaySuccessStatus(String successText) {
		displayStatusForTime(successText, SUCCESS_DISPLAY_COLOR, SUCCESS_DISPLAY_TIME_MS);
	}
	
	public void displayStatusForTime(String statusText, Color textColor, int milliseconds)
	{
		if(!statusText.equals(lblErrorStatus.getText()))
		{
			lblErrorStatus.setForeground(textColor);
    		lblErrorStatus.setText(statusText);
			errorDisplayTimer = new Timer(milliseconds, new ActionListener() {
			    public void actionPerformed(ActionEvent evt) {
		    		clearErrorStatus();
		    }
		});
			errorDisplayTimer.setRepeats(false);
			errorDisplayTimer.start();
		}
	}

	public WIMSTable getDisplayTable() {
		return mainTable;
	}

	public EntityAndFieldPanel getEntityAndFieldPanel() {
		return entityAndFieldSelectPanel;
	}

	public ColumnHeaderControllerPanel getColumnCheckBoxesPanel() {
		return showColumnsForPanel;
	}
	
	public String getCurrentTableEntity()
	{
		return currentTableEntity;
	}
	
	public JScrollPane getMainTableScrollPane(){
		return this.mainTableScrollPane;
	}
	
	public JLabel getLoadingLabel(){
		return this.lblLoadingIcon;
	}
}