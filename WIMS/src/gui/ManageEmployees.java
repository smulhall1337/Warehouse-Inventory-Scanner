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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;

import controller.ComponentProvider;
import controller.DBNamesManager;
import controller.ErrorStatusReportable;
import controller.FocusGrabber;
import controller.IDDocument;
import controller.NameDocument;
import controller.SQL_Handler;

public class ManageEmployees extends JFrame implements ErrorStatusReportable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1917137272419519085L;
	
	private static final String ADD_EMPLOYEE_ACTION_NAME = "Add Employee";
	private static final String CHANGE_EMPLOYEE_PASS_ACTION_NAME = "Change Employee Password";
	private static final String DELETE_EMPLOYEE_ACTION_NAME = "Delete Employee";
	private static final String UPDATE_EMPLOYEE_ACTION_NAME = "Update Employee Info";
	private static final String[] ACTIONS = {ADD_EMPLOYEE_ACTION_NAME, 
			CHANGE_EMPLOYEE_PASS_ACTION_NAME, DELETE_EMPLOYEE_ACTION_NAME, UPDATE_EMPLOYEE_ACTION_NAME};
	
	private static final String ADD_EMPLOYEE_BUTTON_TEXT = "Add Employee";
	private static final String CHANGE_EMPLOYEE_PASS_BUTTON_TEXT = "Change Password";
	private static final String DELETE_EMPLOYEE_BUTTON_TEXT = "Delete Employee";
	private static final String UPDATE_EMPLOYEE_BUTTON_TEXT = "Update";
	private static final String DEFAULT_BUTTON_TEXT = "Perform";
	
	private static final String DEFAULT_EMPLOYEE_ID = "";
	
	private static final int PASSWORD_FIELD_COLUMNS = 25;
	private static final int OPTIONS_LEFT_MARGIN = 10;
	private static final int STARTING_OPTION_ROW = 0;
	private static final Font BUTTON_FONT = new Font("Tahoma", Font.PLAIN, 22);
	private Pattern passwordPattern;
	private Matcher passwordMatcher;

	private static final String PASSWORD_PATTERN =
          "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})";
	private static final String PASSWORD_REQUIREMENT_DESCRIPTION = 
			"Passwords must be 8-20 characters with at least one digit,"
			+ "\n" + "one upper case letter, one lower case letter,"
	 		+ "\n" + "and one special symbol (“@#$%”)";
	
	private boolean loggedInEmpIsManager;
	private String loggedInEmployeeID;
	private String initialEmployeeID;
	
	private JFrame frame;
	private JTextField textFieldTempPassword;
	private JPanel panelSelectAction;
	private JPanel panelActionOptions;
	private JComboBox comboBoxSelectAction;
	private JLabel labelEmployeeID;
	private JLabel labelEmployeeName;
	private JLabel labelWarehouseID;
	private JFormattedTextField formattedTextFieldEmployeeName;
	private JFormattedTextField formattedTextFieldEmployeeID;
	private JComboBox comboBoxWarehouseID;
	private JLabel labelCurrentPassword;
	private JLabel labelNewPassword;
	private JLabel labelTempPassword;
	private JPasswordField passFieldCurrentPass;
	private JPasswordField passFieldNewPass;
	private JCheckBox checkBoxIsManager;
	private JPanel panelPerformAction;
	private JButton btnPerformAction;
	private int nextOptionRow; //the index of the next option row in the gridbaglayout

	private String[] warehouseIDs;

	private String[] warehouseNames;

	private ArrayList<String> warehouseInfo;

	private String[] warehouseCities;

	private JTextArea errorStatusTextArea;

	private Timer errorDisplayTimer;
	
	private String errorTab;

	private ActionListener idActionListener;

	private FocusListener idFocusListener;
	
	private static final String TEST_EMP_ID = "894189";
	private static final boolean TEST_EMP_ISMANAGER = true;

	private static final int WINDOW_WIDTH = 575;
	private static final int WINDOW_HEIGHT = 375;
	
	private static final int MIN_WINDOW_WIDTH = 575;
	private static final int MIN_WINDOW_HEIGHT = WINDOW_HEIGHT;
	private static final Dimension MIN_WINDOW_DIM = new Dimension(MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT);
	
	private static final int MAX_WINDOW_WIDTH = WINDOW_WIDTH;
	private static final int MAX_WINDOW_HEIGHT = 350;
	private static final Dimension MAX_WINDOW_DIM = new Dimension(MAX_WINDOW_WIDTH, MAX_WINDOW_HEIGHT);

	protected static final int ERROR_DISPLAY_TIME_MS = 15000;
	protected static final int SUCCESS_DISPLAY_TIME_MS = 10000;
	protected static final Color ERROR_DISPLAY_COLOR = Color.RED;//new Color(112, 211, 0);
	protected static final Color SUCCESS_DISPLAY_COLOR = new Color(58, 193, 0);
	protected static final Color NEUTRAL_DISPLAY_COLOR = Color.BLACK;//new Color(112, 211, 0);

	private static final int ERROR_STATUS_ROWS = 4;

	private static final int ERROR_STATUS_COLUMNS = 30;
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageEmployees window = new ManageEmployees(TEST_EMP_ID, TEST_EMP_ISMANAGER);
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
	public ManageEmployees(String loggedInEmployeeID, boolean loggedInEmpIsManager) {
		this.loggedInEmpIsManager = loggedInEmpIsManager;
		this.loggedInEmployeeID = loggedInEmployeeID;
		this.initialEmployeeID = loggedInEmployeeID;
		initialize();
	}
	
	public ManageEmployees(String loggedInEmployeeID, boolean isManager, String initializerEmployeeID)
	{
		this.loggedInEmpIsManager = isManager;
		this.loggedInEmployeeID = loggedInEmployeeID;
		if(loggedInEmpIsManager)
			this.initialEmployeeID = initializerEmployeeID;
		else
			this.initialEmployeeID = loggedInEmployeeID;
		initialize();
		if(this.loggedInEmpIsManager){
			comboBoxSelectAction.setSelectedItem(UPDATE_EMPLOYEE_ACTION_NAME);
			displayOptionsForAction(UPDATE_EMPLOYEE_ACTION_NAME);
		}
	}

	/**
	 * Load the fields for the employee with the corresponding employee id
	 * @param employeeID the employee to display the fields for
	 * @return true if loading the fields was successful, false otherwise
	 */
	private boolean loadFieldsForID(String employeeID){
		if(employeeID.equals(""))
			return false;
		try {
			if(SQL_Handler.employeeExists(employeeID))
			{
				String employeeName = SQL_Handler.getEmployeeNameByID(employeeID);
				String wareHouseID = SQL_Handler.getEmployeeWarehouseByEmpID(employeeID);
				boolean isManager = SQL_Handler.isEmployeeManager(employeeID);
				formattedTextFieldEmployeeID.setText(employeeID);
				formattedTextFieldEmployeeName.setText(employeeName);
				int whNdx = Arrays.asList(warehouseIDs).indexOf(wareHouseID);
				comboBoxWarehouseID.setSelectedIndex(whNdx);
				//comboBoxWarehouseID.setSelectedItem(wareHouseID);
				//comboBoxWarehouseID.setEnabled(true);
				//formattedTextFieldEmployeeName.setEnabled(true);
				checkBoxIsManager.setSelected(isManager);
				return true;
			}else{
				displayErrorStatus("There is no employee with ID " + employeeID 
						+ ". Enter an existing ID or select another employee action.");
				 this.initialEmployeeID = "";
				 formattedTextFieldEmployeeName.setText("");
				 return false;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			ComponentProvider.showDBConnectionError(frame);
			return false;
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		initializeFrame();
		initializeWarehouseInfoArrays();
		passwordPattern = Pattern.compile(PASSWORD_PATTERN);
		nextOptionRow = STARTING_OPTION_ROW;
		initializeLabels();
		initializeInputFields();
		//if the current user is a manager, show all options
		if(loggedInEmpIsManager)
		{
			initializeActionSelection();
			initializeActionOptionsPanel();
			initializePerformActionPanel();
			initializePerfomActionButton((String) comboBoxSelectAction.getSelectedItem());
		}else{ //if the current user is a regular employee, only show change password options
			initializeActionOptionsPanel(CHANGE_EMPLOYEE_PASS_ACTION_NAME);
			initializePerformActionPanel();
			initializePerfomActionButton(CHANGE_EMPLOYEE_PASS_ACTION_NAME);
		}
	}
	
	private void initializeWarehouseInfoArrays() {
		
		try {
			warehouseIDs = SQL_Handler.getWarehouseIDs();
			warehouseNames = SQL_Handler.getWarehouseNames();
			warehouseCities = SQL_Handler.getWarehouseCities();
			warehouseInfo = new ArrayList<String>();
			for(int ndx = 0; ndx < warehouseIDs.length; ndx++)
			{
				warehouseInfo.add(warehouseNames[ndx] + " in " + warehouseCities[ndx] + " (ID: " + warehouseIDs[ndx] + ")");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			ComponentProvider.showDBConnectionError(frame);
			e.printStackTrace();
		}
	}

	private void initializeFrame(){
		frame = new JFrame("WIMS - Manage Employees");
		frame.setBounds(700, 400, WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setMinimumSize(MIN_WINDOW_DIM);
		frame.setMaximumSize(MAX_WINDOW_DIM);
	}
	
	private void initializeLabels() {
		labelEmployeeID = new JLabel(DBNamesManager.getEmployeeIdDisplayname() + ":");
		labelEmployeeName = new JLabel(DBNamesManager.getEmployeeNameDisplayname()+":");
		labelWarehouseID = new JLabel(DBNamesManager.getWarehouseIdFieldDisplayname() + ":");
		labelCurrentPassword = new JLabel("Current Password:");
		labelTempPassword = new JLabel("Temporary Password:");
		labelNewPassword = new JLabel("New Password:");
	}
	
	private void initializeInputFields() {
		formattedTextFieldEmployeeID = getEmployeeIDTextField();
		formattedTextFieldEmployeeName = ComponentProvider.getNameTextField();		
		comboBoxWarehouseID = new JComboBox();
		comboBoxWarehouseID.setModel(new DefaultComboBoxModel(warehouseInfo.toArray()));
		passFieldCurrentPass = getPasswordField();
		passFieldNewPass = getPasswordField();
		textFieldTempPassword = new JTextField();
		textFieldTempPassword.setColumns(15);
		checkBoxIsManager = new JCheckBox("Employee is a manager");
	}

	private void initializeActionSelection()
	{
		initializeActionSelectPanel();
		initializeActionSelectCombo();
	}
	
	private void initializeActionSelectPanel()
	{
		panelSelectAction = new JPanel();
		frame.getContentPane().add(panelSelectAction);
		panelSelectAction.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
	}
	
	private void initializePerformActionPanel()
	{
		panelPerformAction = new JPanel();
		frame.getContentPane().add(panelPerformAction);
		panelPerformAction.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
	}
	
	private void initializePerfomActionButton(String actionName)
	{
		clearPerformActionPanel();
		switch (actionName){
		case ADD_EMPLOYEE_ACTION_NAME: 
			btnPerformAction = new JButton(ADD_EMPLOYEE_BUTTON_TEXT);
			break;
		case CHANGE_EMPLOYEE_PASS_ACTION_NAME: 
			btnPerformAction = new JButton(CHANGE_EMPLOYEE_PASS_BUTTON_TEXT);
			break;
		case DELETE_EMPLOYEE_ACTION_NAME:
			btnPerformAction = new JButton(DELETE_EMPLOYEE_BUTTON_TEXT);
			break;
		case UPDATE_EMPLOYEE_ACTION_NAME:
			btnPerformAction = new JButton(UPDATE_EMPLOYEE_BUTTON_TEXT);
			break;
		default: 
			btnPerformAction = new JButton(DEFAULT_BUTTON_TEXT);
			break;
		}
		
		btnPerformAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if(comboBoxSelectAction != null  && 
						((String) comboBoxSelectAction.getSelectedItem()).equals(errorTab))
				{
					String employeeID = formattedTextFieldEmployeeID.getText();
					displayErrorStatus("There is no employee with ID " + employeeID 
							+ ". Enter an existing ID or select another employee action.");
					SwingUtilities.invokeLater(new FocusGrabber(formattedTextFieldEmployeeID));
				}else{
					performEmployeeChange(actionName);
				}
			}
		});
		
		btnPerformAction.setFont(BUTTON_FONT);
		//btnPerformAction.setHorizontalAlignment(SwingConstants.RIGHT);
		btnPerformAction.setVisible(true);
		if(errorStatusTextArea == null){
			errorStatusTextArea = new JTextArea("");
			errorStatusTextArea.setLineWrap(true);
			errorStatusTextArea.setWrapStyleWord(true);
			errorStatusTextArea.setBackground(panelPerformAction.getBackground());
			errorStatusTextArea.setColumns(ERROR_STATUS_COLUMNS);
			errorStatusTextArea.setRows(ERROR_STATUS_ROWS);
		}
		panelPerformAction.add(errorStatusTextArea);
		panelPerformAction.add(btnPerformAction);
	}
	
	private void performEmployeeChange(String actionName)
	{
		switch (actionName){
		case ADD_EMPLOYEE_ACTION_NAME: 
			addEnteredEmployee();
			break;
		case CHANGE_EMPLOYEE_PASS_ACTION_NAME: 
			changeEnteredPassword();
			break;
		case DELETE_EMPLOYEE_ACTION_NAME:
			deleteEnteredEmployee();
			break;
		case UPDATE_EMPLOYEE_ACTION_NAME:
			updateEnteredEmployee();
			break;
		}
	}
	
	private void updateEnteredEmployee() {
		String employeeID = formattedTextFieldEmployeeID.getText();
		String employeeName = formattedTextFieldEmployeeName.getText();
		String warehouseID = getSelectedWarehouseID();
		boolean isManager = checkBoxIsManager.isSelected();
		try {
			String oldEmployeeName = SQL_Handler.getEmployeeNameByID(employeeID); 
			String oldWarehouseID = SQL_Handler.getEmployeeWarehouseByEmpID(employeeID);
			boolean oldIsManager = SQL_Handler.isEmployeeManager(employeeID);
			int reply = JOptionPane.showConfirmDialog(frame, 
					 "Employee ID: " +  employeeID + "\n"
				   + "Name: " +  oldEmployeeName + " ---> " + employeeName + "\n"
				   + "Warehouse ID: " +  oldWarehouseID + " ---> " + warehouseID + "\n"
				   + "Status: " +  position(oldIsManager) + " ---> " + position(isManager) + "\n", 
					 "Confirm Employee Update", JOptionPane.OK_CANCEL_OPTION);
			    if (reply == JOptionPane.OK_OPTION)
			    {
			    	SQL_Handler.updateEmployee(employeeID, employeeName, isManager, warehouseID);
			    	displaySuccessStatus("Employee successfully updated.");
			    }
		} catch (SQLException e) {
			e.printStackTrace();
			ComponentProvider.showDBConnectionError(frame);
		}
	}

	private String getSelectedWarehouseID() {
		return warehouseIDs[comboBoxWarehouseID.getSelectedIndex()];
	}

	private void changeEnteredPassword() {
		final String currentPass = String.valueOf(passFieldCurrentPass.getPassword());
		final String newPass = String.valueOf(passFieldNewPass.getPassword());
		try{
			String currentID = formattedTextFieldEmployeeID.getText();
			if(SQL_Handler.employeeExists(currentID))
			{
				boolean validCurrentPass = SQL_Handler.isValidUsernamePassword(currentID, currentPass);
				boolean validNewPass = validatePassword(newPass);
				String empName = SQL_Handler.getEmployeeNameByID(currentID);
				boolean isManager = SQL_Handler.isEmployeeManager(currentID);
				if(!validCurrentPass || !validNewPass){
					String errorString = "";
					if(!validCurrentPass)
						errorString = "The entered password is incorrect. ";
					if(!validNewPass)
						errorString = errorString + "The newly entered password is invalid. " + PASSWORD_REQUIREMENT_DESCRIPTION;
					if(!errorString.equals("")){
						displayErrorStatus(errorString);
					}
				}else{
					SQL_Handler.updateEmployeePW(currentID, newPass);
					displaySuccessStatus("Password for " 
					+  this.getEmployeeDescription(currentID, empName, isManager) + " successfully changed.");
				}
			}else{
				String error = "Employee with ID " + currentID + " does not exist. Go to the " 
						+ ADD_EMPLOYEE_ACTION_NAME + " section to add an employee.";
				JOptionPane.showMessageDialog(frame, error, "Employee does not exist", JOptionPane.ERROR_MESSAGE);
				displayErrorStatus(error);
			}
		}catch(SQLException ex)
		{
			//TODO what if the current employee has been removed
			ex.printStackTrace();
			JOptionPane.showMessageDialog(frame, "There was a problem reaching the database.", 
			 		"Database Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void addEnteredEmployee() {
		String employeeID = formattedTextFieldEmployeeID.getText();
		String employeeName = formattedTextFieldEmployeeName.getText();
		String warehouseID = warehouseIDs[comboBoxWarehouseID.getSelectedIndex()];
		boolean isManager = checkBoxIsManager.isSelected();
		String password = textFieldTempPassword.getText();
		try {
			if(SQL_Handler.employeeExists(employeeID))
			{
				String existingEmployeeName = SQL_Handler.getEmployeeNameByID(employeeID);
				boolean existingEmpIsManager = SQL_Handler.isEmployeeManager(employeeID);
				String updateInfo = "They cannot be added again. ";
				if(existingEmpIsManager)
				{
					updateInfo = "Since they are a manager, you are not permitted to edit their information." + "\n"  
							+ "Please contact an administrator or " + existingEmployeeName + " for assistance.";
				}else{
					updateInfo = "You can edit their information by entering their ID in the" + "\n" 
								+ "\"Update Employee Info\" section of this menu.";
				}
				JOptionPane.showMessageDialog(frame, 
						this.getEmployeeDescription(employeeID, existingEmployeeName, existingEmpIsManager) + " already exists." + "\n"
								+ updateInfo, "Employee Already Exists", JOptionPane.WARNING_MESSAGE);
			}else{
			
				int reply = JOptionPane.showConfirmDialog(frame, "Are you sure you want to add " 
					 + this.getEmployeeDescription(employeeID, employeeName, isManager) 
					 + "\n" + "and temporary password " + password + "?", 
					 "Confirm Add Employee", JOptionPane.OK_CANCEL_OPTION);
			    if (reply == JOptionPane.OK_OPTION)
			    {
			    	SQL_Handler.insertNewEmployee(employeeID, employeeName, isManager, password, warehouseID);
			    	displaySuccessStatus("Employee "  + employeeName +  "with ID " + employeeID + " added to database.");
			    }
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ComponentProvider.showDBConnectionError(frame);
		}
	}

	private void deleteEnteredEmployee() {
		String employeeID = formattedTextFieldEmployeeID.getText();
		String employeeName = formattedTextFieldEmployeeName.getText();
		boolean isManager = checkBoxIsManager.isSelected();
		try {
			 int reply = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete " 
					 + this.getEmployeeDescription(employeeID, employeeName, isManager) + "?" + 
					 "\n" + "They will be gone from the database permanently.", 
					 "Confirm Employee Deletion", JOptionPane.OK_CANCEL_OPTION);
			    if (reply == JOptionPane.OK_OPTION)
			    {
			    	SQL_Handler.deleteEmployee(employeeID);
			    	displaySuccessStatus("Employee "  + employeeName + " deleted.");
			    }
		} catch (SQLException e) {
			e.printStackTrace();
			ComponentProvider.showDBConnectionError(frame);
		}
	}
	
	private void clearPerformActionPanel()
	{
		panelPerformAction.removeAll();
		panelPerformAction.revalidate();
		panelPerformAction.repaint();
	}
	
	private void initializeActionSelectCombo()
	{
		comboBoxSelectAction = new JComboBox();
		comboBoxSelectAction.setFont(new Font("Tahoma", Font.PLAIN, 18));
		comboBoxSelectAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				String itemSelected = (String) comboBoxSelectAction.getSelectedItem();
				displayOptionsForAction(itemSelected);
				initializePerfomActionButton(itemSelected);
			}
		});
		comboBoxSelectAction.setModel(new DefaultComboBoxModel(ACTIONS));
		panelSelectAction.add(comboBoxSelectAction);
	}
	
	private void initializeActionOptionsPanel()
	{
		initializeActionOptionsPanel((String) comboBoxSelectAction.getSelectedItem());
	}
	
	private void initializeActionOptionsPanel(String Action)
	{
		panelActionOptions = new JPanel();
		Border borderLoweredBevel = BorderFactory.createLoweredBevelBorder();
		panelActionOptions.setBorder(borderLoweredBevel);
		frame.getContentPane().add(panelActionOptions, BorderLayout.SOUTH);
		GridBagLayout gbl_panelActionOptions = new GridBagLayout();
		gbl_panelActionOptions.columnWidths = new int[] {20, 100, 50, 0};
		gbl_panelActionOptions.rowHeights = new int[] {30, 30, 30, 30};
		gbl_panelActionOptions.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelActionOptions.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		panelActionOptions.setLayout(gbl_panelActionOptions);
		displayOptionsForAction(Action);
	}
	
	private void clearCurrentOptions()
	{
		panelActionOptions.removeAll();
		panelActionOptions.revalidate();
		panelActionOptions.repaint();
		nextOptionRow = STARTING_OPTION_ROW;
	}
	
	private void displayOptionsForAction(String reportName)
	{
		clearCurrentOptions();
		loadFieldsForID(initialEmployeeID);
		switch (reportName){
		case ADD_EMPLOYEE_ACTION_NAME: 
			displayAddEmployeeOptions();
			break;
		case CHANGE_EMPLOYEE_PASS_ACTION_NAME: 
			displayChangeEmployeePassOptions();
			break;
		case DELETE_EMPLOYEE_ACTION_NAME:
			displayDeleteEmployeeOptions();
			break;
		case UPDATE_EMPLOYEE_ACTION_NAME:
			displayUpdateEmployeeOptions();
			break;
		}
	}
	
	private void displayUpdateEmployeeOptions() {
		
		formattedTextFieldEmployeeName.setEditable(true);
		//formattedTextFieldEmployeeName.setEnabled(true);
		try {
			int whNdx = Arrays.asList(warehouseIDs).indexOf(SQL_Handler.getEmployeeWarehouseByEmpID(this.initialEmployeeID));
			comboBoxWarehouseID.setSelectedIndex(whNdx);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		comboBoxWarehouseID.setEnabled(true);
		checkBoxIsManager.setEnabled(true);
		addIDActionListener();
		addRowToOptions(labelEmployeeID, formattedTextFieldEmployeeID);
		addRowToOptions(labelEmployeeName, formattedTextFieldEmployeeName);
		addRowToOptions(labelWarehouseID, comboBoxWarehouseID);
		addRowToOptions(null, checkBoxIsManager);
	}

	

	private void displayChangeEmployeePassOptions() {
		
		//formattedTextFieldEmployeeID = this.getIDTextField();
		//managers can edit anyones password
		
		//TODO
		//also managers shouldnt have to enter the current password to change a password
		//TODO if current entered employee ID is a manager, make this require their password
		//formattedTextFieldEmployeeID.setEnabled(true);
		formattedTextFieldEmployeeID.setEditable(this.loggedInEmpIsManager);
		addIDActionListener();
		addRowToOptions(labelEmployeeID, formattedTextFieldEmployeeID);
		addRowToOptions(labelCurrentPassword, passFieldCurrentPass);
		addRowToOptions(labelNewPassword, passFieldNewPass);
		System.out.println(formattedTextFieldEmployeeID.isEditable());
		System.out.println(formattedTextFieldEmployeeID.isEnabled());
	}

    private String name(Component c) {
        return (c == null) ? null : c.getName();
    }
	private void displayDeleteEmployeeOptions() {

		formattedTextFieldEmployeeName.setEditable(false);
		//formattedTextFieldEmployeeName.setEnabled(false);
		comboBoxWarehouseID.setEnabled(false);
		checkBoxIsManager.setEnabled(false);
		addIDActionListener();
		addRowToOptions(labelEmployeeID, formattedTextFieldEmployeeID);		
		addRowToOptions(labelEmployeeName, formattedTextFieldEmployeeName);
		addRowToOptions(labelWarehouseID, comboBoxWarehouseID);
		addRowToOptions(null, checkBoxIsManager);
	}

	
	private void addIDActionListener(){
		removeIDListeners();
		idActionListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
            	String employeeID = formattedTextFieldEmployeeID.getText();
            	//if the employee exists and fields are loaded successfully
            	if(loadFieldsForID(employeeID) && 
            			!((String)comboBoxSelectAction.getSelectedItem()).equals(CHANGE_EMPLOYEE_PASS_ACTION_NAME));
            		btnPerformAction.requestFocusInWindow();
            }
        };
		idFocusListener = new FocusListener() 
		{

			public void focusLost(FocusEvent e) {
				try {
					if (!e.getOppositeComponent().equals(comboBoxSelectAction)
							&& !e.isTemporary()) {
						String employeeID = formattedTextFieldEmployeeID
								.getText();
						try {
							if (!SQL_Handler.employeeExists(employeeID)) 
							{
								displayErrorStatus("There is no employee with ID "
										+ employeeID
										+ ". Enter an existing ID or select another employee action.");
								SwingUtilities.invokeLater(new FocusGrabber(
										formattedTextFieldEmployeeID));
								errorTab = (String) comboBoxSelectAction
										.getSelectedItem();
							} else {
								if(!employeeID.equals(loggedInEmployeeID) && SQL_Handler.isEmployeeManager(employeeID))
								{
									displayErrorStatus("Managers cannot edit other managers' information. "
											+ "See an admin for assistance.");
									SwingUtilities.invokeLater(new FocusGrabber(formattedTextFieldEmployeeID));
								}else{
									loadFieldsForID(employeeID);
								}
							}
						} catch (SQLException e1) {
							ComponentProvider.showDBConnectionError(frame);
							e1.printStackTrace();
						}
					}
				} catch (NullPointerException npex) {

				}
			}
			
			@Override
		    public void focusGained(FocusEvent e) {
		        
			}
		};
		formattedTextFieldEmployeeID.addActionListener(idActionListener);
		formattedTextFieldEmployeeID.addFocusListener(idFocusListener);
	}
	
	private void removeIDListeners() {
		formattedTextFieldEmployeeID.removeActionListener(idActionListener);
		formattedTextFieldEmployeeID.removeFocusListener(idFocusListener);
		
	}

	private String getEmployeeDescription(String ID, String name, boolean isManager) {
		String description = "Employee ";
		if(isManager)
			description = "Manager ";
		description = description + name + " with ID " + ID;
		return description;
	}

	private String position(boolean isManager)
	{
		if(isManager)
			return "manager";
		else
			return "employee";
	}
	
	private void displayAddEmployeeOptions()
	{
		removeIDListeners();
		formattedTextFieldEmployeeID.setText("");
		formattedTextFieldEmployeeName.setText("");
		formattedTextFieldEmployeeName.setEditable(true);
		comboBoxWarehouseID.setEnabled(true);
		checkBoxIsManager.setSelected(false);
		checkBoxIsManager.setEnabled(true);
		addRowToOptions(labelEmployeeID, formattedTextFieldEmployeeID);
		addRowToOptions(labelEmployeeName, formattedTextFieldEmployeeName);
		addRowToOptions(labelTempPassword, textFieldTempPassword);
		addRowToOptions(labelWarehouseID, comboBoxWarehouseID);
		addRowToOptions(null, checkBoxIsManager);
	}
	
	/**
	 * Add a row of options to the gridbaglayout in the options panel, in two columns.
	 * @param firstColumn the component to place in the first column in this row
	 * @param secondColumn the component to place in the second column in this row
	 */
	private void addRowToOptions(Component firstColumn, Component secondColumn)
	{
		Component rigidAreaLeft = Box.createRigidArea(new Dimension(OPTIONS_LEFT_MARGIN, 0));
		GridBagConstraints gbc_rigidAreaLeft = new GridBagConstraints();
		gbc_rigidAreaLeft.anchor = GridBagConstraints.WEST;
		gbc_rigidAreaLeft.insets = new Insets(0, 0, 5, 5);
		gbc_rigidAreaLeft.gridx = 0;
		gbc_rigidAreaLeft.gridy = nextOptionRow;
		panelActionOptions.add(rigidAreaLeft, gbc_rigidAreaLeft);
		
		if(firstColumn != null)
		{
			GridBagConstraints gbc_optionLabel = new GridBagConstraints();
			gbc_optionLabel.anchor = GridBagConstraints.EAST;
			gbc_optionLabel.insets = new Insets(0, 0, 5, 5);
			gbc_optionLabel.gridx = 1;
			gbc_optionLabel.gridy = nextOptionRow;
			panelActionOptions.add(firstColumn, gbc_optionLabel);
		}
		if(secondColumn != null)
		{
			GridBagConstraints gbc_optionTextField = new GridBagConstraints();
			gbc_optionTextField.fill = GridBagConstraints.HORIZONTAL;
			gbc_optionTextField.insets = new Insets(0, 0, 5, 5);
			gbc_optionTextField.anchor = GridBagConstraints.WEST;
			gbc_optionTextField.gridx = 2;
			gbc_optionTextField.gridy = nextOptionRow;
			panelActionOptions.add(secondColumn, gbc_optionTextField);
		}
		
		nextOptionRow++;
	}

	  /**
	   * Validate password with regular expression
	   * @param password password for validation
	   * @return true valid password, false invalid password
	   */
	public boolean validatePassword(final String password){
		  passwordMatcher = passwordPattern.matcher(password);
		  return passwordMatcher.matches();
	  }
	  
	  /**
	   * Get the current employee ID for actions
	   * @return
	   */
	public String getCurrentEmployeeID()
	  {
		  //if the logged in exployee is a manager
		  if(this.loggedInEmpIsManager)
			  return formattedTextFieldEmployeeID.getText();
		  else{//otherwise they are a regular employee
			  //so return the ID of the employee that is logged in
			  return this.loggedInEmployeeID;
		  }
	  }
	
	
	
	private JFormattedTextField getEmployeeIDTextField()
	{
		JFormattedTextField formattedTextFieldEmployeeID = ComponentProvider.getIDTextField();
		formattedTextFieldEmployeeID.setEditable(this.loggedInEmpIsManager); 
		formattedTextFieldEmployeeID.setText(this.initialEmployeeID);
		return formattedTextFieldEmployeeID;
	}

	private JPasswordField getPasswordField()
	{
		JPasswordField passField = new JPasswordField();
		passField.setColumns(PASSWORD_FIELD_COLUMNS);
		return passField;
	}
	
	public JFrame getFrame()
	{
		return this.frame;
	}


	@Override
	public void clearErrorStatus()
	{
		errorStatusTextArea.setText("");
	}
	
	@Override
	public void displayErrorStatus(String errorText)
	{
		displayStatusForTime(errorText, ERROR_DISPLAY_COLOR, ERROR_DISPLAY_TIME_MS);
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
		System.out.println("error");
		if(statusText != null && errorStatusTextArea != null && !statusText.equals(errorStatusTextArea.getText()))
		{
			errorStatusTextArea.setForeground(textColor);
			errorStatusTextArea.setText(statusText);
			errorDisplayTimer = new Timer(milliseconds, new ActionListener() {
			    public void actionPerformed(ActionEvent evt) {
		    		clearErrorStatus();
		    }
		});
			errorDisplayTimer.setRepeats(false);
			errorDisplayTimer.start();
		}
	}
}
