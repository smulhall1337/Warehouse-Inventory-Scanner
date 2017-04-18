package gui;

import java.awt.BorderLayout;
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
import java.sql.SQLException;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import controller.EmployeeIDDocument;
import controller.NameDocument;
import controller.SQL_Handler;

public class ManageEmployees extends JFrame{

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
	
	private static final int EMPLOYEE_NAME_TEXTFIELD_COLUMNS = 25;
	private static final int EMPLOYEE_ID_TEXTFIELD_COLUMNS = 25;
	private static final int PASSWORD_FIELD_COLUMNS = 25;
	private static final int OPTIONS_LEFT_MARGIN = 10;
	private static final int STARTING_OPTION_ROW = 0;
	
	private Pattern passwordPattern;
	private Matcher passwordMatcher;

	private static final String PASSWORD_PATTERN =
          "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})";
	private static final String PASSWORD_REQUIREMENT_DESCRIPTION = 
			"Passwords must be 8-20 characters with at least one digit,"
			+ "\n" + "one upper case letter, one lower case letter,"
	 		+ "\n" + "and one special symbol (“@#$%”)";
	
	private boolean isManager;
	
	private JFrame frame;
	private JTextField textFieldTempPassword;
	private JPanel panelSelectAction;
	private JPanel panelActionOptions;
	private JComboBox comboBoxSelectAction;
	private JLabel labelEmployeeID;
	private JLabel labelEmployeeName;
	private JFormattedTextField formattedTextFieldEmployeeName;
	private JFormattedTextField formattedTextFieldEmployeeID;
	private JLabel labelCurrentPassword;
	private JLabel labelNewPassword;
	private JLabel labelTempPassword;
	private JPasswordField passFieldCurrentPass;
	private JPasswordField passFieldNewPass;
	private JCheckBox checkBoxIsManager;
	private JPanel panelPerformAction;
	private int nextOptionRow; //the index of the next option row in the gridbaglayout
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageEmployees window = new ManageEmployees();
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
	//public ManageEmployees(String loggedInEmployeeID, boolean isManager)
	public ManageEmployees() {
		isManager = true;
		initialize(isManager);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(boolean isManager) {
		frame = new JFrame("WIMS - Manage Employees");
		frame.setBounds(700, 400, 450, 260);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		passwordPattern = Pattern.compile(PASSWORD_PATTERN);
		nextOptionRow = STARTING_OPTION_ROW;
		initializeLabels();
		if(isManager)
		{
			initializeActionSelection();
			initializeActionOptionsPanel();
			initializePerformActionPanel();
			initializePerfomActionButton((String) comboBoxSelectAction.getSelectedItem());
		}else{
			initializeActionOptionsPanel(CHANGE_EMPLOYEE_PASS_ACTION_NAME);
			initializePerformActionPanel();
			initializePerfomActionButton(CHANGE_EMPLOYEE_PASS_ACTION_NAME);
		}
	}
	
	private void initializeLabels() {
		labelEmployeeID = new JLabel("Employee ID:");
		labelEmployeeName = new JLabel("Name:");
		labelCurrentPassword = new JLabel("Current Password:");
		labelTempPassword = new JLabel("Temporary Password:");
		labelNewPassword = new JLabel("New Password:");
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
		JButton btnPerformAction;
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
				performEmployeeChange(actionName);
			}
		});
		
		btnPerformAction.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnPerformAction.setHorizontalAlignment(SwingConstants.RIGHT);
		btnPerformAction.setVisible(true);
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
		// TODO Auto-generated method stub
	}

	private void deleteEnteredEmployee() {
		// TODO Auto-generated method stub
	}

	private void changeEnteredPassword() {
		final String currentPass = String.valueOf(passFieldCurrentPass.getPassword());
		final String newPass = String.valueOf(passFieldNewPass.getPassword());
		try{
		//TODO check if employee ID exists, check 
		String currentID = formattedTextFieldEmployeeID.getText();
		//boolean employeeExists = 
		boolean validCurrentPass = SQL_Handler.isValidUsernamePassword(currentID, currentPass);
		boolean validNewPass = validatePassword(newPass);
		if(!validCurrentPass){
			JOptionPane.showMessageDialog(frame, "The entered password is incorrect.", 
			 		"Incorrect Password", JOptionPane.ERROR_MESSAGE);
		}else if(!validNewPass)
		{
			 JOptionPane.showMessageDialog(frame, "The newly entered password is invalid."
			 		+ PASSWORD_REQUIREMENT_DESCRIPTION, 
			 		"Invalid Password", JOptionPane.ERROR_MESSAGE);
		}
		}catch(SQLException ex)
		{
			//TODO what if the current employee has been removed
			JOptionPane.showMessageDialog(frame, "There was a problem reaching the database.", 
			 		"Database Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void addEnteredEmployee() {
		//TODO check if employee already exists
		String currentID = formattedTextFieldEmployeeID.getText();
		String currentName = formattedTextFieldEmployeeName.getText();
		boolean isManager = checkBoxIsManager.isSelected();
		// TODO Auto-generated method stub
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
		checkBoxIsManager = new JCheckBox("Employee is a manager");
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
	
	private JFormattedTextField getEmployeeIDTextField()
	{
		formattedTextFieldEmployeeID = new JFormattedTextField();
		EmployeeIDDocument idDoc = new EmployeeIDDocument();
		formattedTextFieldEmployeeID.setDocument(idDoc);
		formattedTextFieldEmployeeID.setColumns(EMPLOYEE_ID_TEXTFIELD_COLUMNS);
		return formattedTextFieldEmployeeID;
	}
	
	private JPasswordField getPasswordField()
	{
		JPasswordField passField = new JPasswordField();
		passField.setColumns(PASSWORD_FIELD_COLUMNS);
		return passField;
	}
	
	
	private void displayUpdateEmployeeOptions() {
		formattedTextFieldEmployeeID = getEmployeeIDTextField();
		
		formattedTextFieldEmployeeName = getEmployeeNameTextField();
		textFieldTempPassword = new JTextField();
		
		addRowToOptions(labelEmployeeID, formattedTextFieldEmployeeID);
		addRowToOptions(labelEmployeeName, formattedTextFieldEmployeeName);
		addRowToOptions(null, checkBoxIsManager);
	}

	private JFormattedTextField getEmployeeNameTextField() {
		NameDocument employeeNameDoc = new NameDocument();
		formattedTextFieldEmployeeName = new JFormattedTextField();
		formattedTextFieldEmployeeName.setDocument(employeeNameDoc);
		formattedTextFieldEmployeeName.setColumns(EMPLOYEE_NAME_TEXTFIELD_COLUMNS);
		return formattedTextFieldEmployeeName;
	}

	private void displayChangeEmployeePassOptions() {
		
	    formattedTextFieldEmployeeID = getEmployeeIDTextField();
	    formattedTextFieldEmployeeID.setEditable(this.isManager); //managers can edit anyones password
	    
	    //TODO make the employee ID field start as currently logged in employee's ID
	    //if they arent a manager, it will be uneditable / a label
	    //also managers shouldnt have to enter the current password to change a password
		addRowToOptions(labelEmployeeID, formattedTextFieldEmployeeID);
		
		//TODO if current entered employee ID is a manager, make this require their password
		passFieldCurrentPass = getPasswordField();
		addRowToOptions(labelCurrentPassword, passFieldCurrentPass);
		
		passFieldNewPass = getPasswordField();
		addRowToOptions(labelNewPassword, passFieldNewPass);
	}

	private void displayDeleteEmployeeOptions() {
		formattedTextFieldEmployeeID = new JFormattedTextField();
		
		addRowToOptions(labelEmployeeID, formattedTextFieldEmployeeID);
		
		//TODO make this check database from ID, put name in field for you
		formattedTextFieldEmployeeName = getEmployeeNameTextField();
		addRowToOptions(labelEmployeeName, formattedTextFieldEmployeeName);
	}
	
	private void displayAddEmployeeOptions()
	{
		formattedTextFieldEmployeeID = getEmployeeIDTextField();
		
		addRowToOptions(labelEmployeeID, formattedTextFieldEmployeeID);
		
		formattedTextFieldEmployeeName = getEmployeeNameTextField();
		
		addRowToOptions(labelEmployeeName, formattedTextFieldEmployeeName);
		
		textFieldTempPassword = new JTextField();
		textFieldTempPassword.setColumns(15);
		
		addRowToOptions(labelTempPassword, textFieldTempPassword);
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
	
	public JFrame getFrame()
	{
		return this.frame;
	}
}
