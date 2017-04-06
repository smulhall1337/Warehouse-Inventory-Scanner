package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Container;

import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageEmployees {

	private static final String ADD_EMPLOYEE_ACTION_NAME = "Add Employee";
	private static final String CHANGE_EMPLOYEE_PASS_ACTION_NAME = "Change Employee Password";
	private static final String DELETE_EMPLOYEE_ACTION_NAME = "Delete Employee";
	private static final String[] ACTIONS = {ADD_EMPLOYEE_ACTION_NAME, 
			CHANGE_EMPLOYEE_PASS_ACTION_NAME, DELETE_EMPLOYEE_ACTION_NAME};
	
	
	private JFrame frame;
	private JTextField textFieldTempPassword;
	private JPanel panelSelectAction;
	private JPanel panelActionOptions;
	private JComboBox comboBoxSelectAction;
	private int nextOptionRow; //the index of the next option row in the gridbaglayout
	private static final int STARTING_OPTION_ROW = 1;

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
	public ManageEmployees() {
		initialize(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(boolean isManager) {
		frame = new JFrame();
		frame.setBounds(100, 100, 422, 214);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nextOptionRow = STARTING_OPTION_ROW;
		if(isManager)
		{
			initializeActionSelection();
			initializeActionOptionsPanel();
		}else{
			initializeActionOptionsPanel(CHANGE_EMPLOYEE_PASS_ACTION_NAME);
		}
	}
	
	private void initializeActionSelection()
	{
		initializeActionSelectPanel();
		initializeActionSelectCombo();
	}
	
	private void initializeActionSelectPanel()
	{
		panelSelectAction = new JPanel();
		frame.getContentPane().add(panelSelectAction, BorderLayout.NORTH);
		panelSelectAction.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
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
		frame.getContentPane().add(panelActionOptions, BorderLayout.SOUTH);
		GridBagLayout gbl_panelAddEmployee = new GridBagLayout();
		gbl_panelAddEmployee.columnWidths = new int[] {50, 150, 30, 0};
		gbl_panelAddEmployee.rowHeights = new int[] {0, 30, 0, 30, 0, 30, 0};
		gbl_panelAddEmployee.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelAddEmployee.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		panelActionOptions.setLayout(gbl_panelAddEmployee);
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
		}
	}
	
	
	private void displayChangeEmployeePassOptions() {
		
		JLabel labelEmployeeID = new JLabel("Employee ID:");
		JFormattedTextField formattedTextFieldEmployeeID = new JFormattedTextField();
		addRowToOptions(labelEmployeeID, formattedTextFieldEmployeeID);
		
		JLabel labelCurrentPass = new JLabel("Current Password:");
		JPasswordField passFieldCurrentPass = new JPasswordField();
		addRowToOptions(labelCurrentPass, passFieldCurrentPass);
		
		JLabel labelNewPass = new JLabel("New Password:");
		JPasswordField passFieldNewPass = new JPasswordField();
		addRowToOptions(labelNewPass, passFieldNewPass);
	}

	private void displayDeleteEmployeeOptions() {
		JLabel labelEmployeeID = new JLabel("Employee ID:");
		JFormattedTextField formattedTextFieldEmployeeID = new JFormattedTextField();
		
		addRowToOptions(labelEmployeeID, formattedTextFieldEmployeeID);
		
		JLabel labelEmployeeName = new JLabel("Employee Name:");
		JFormattedTextField formattedTextFieldEmployeeName = new JFormattedTextField();
		addRowToOptions(labelEmployeeName, formattedTextFieldEmployeeName);
	}

	private void displayChangeEmployeePassOptions(String employeeID) {
		// TODO Auto-generated method stub
	}
	
	private void displayAddEmployeeOptions()
	{
		JLabel labelEmployeeID = new JLabel("Employee ID:");
		JFormattedTextField formattedTextFieldEmployeeID = new JFormattedTextField();
		
		addRowToOptions(labelEmployeeID, formattedTextFieldEmployeeID);
		
		JLabel labelEmployeeName = new JLabel("Name:");		
		JFormattedTextField formattedTextFieldEmployeeName = new JFormattedTextField();
		formattedTextFieldEmployeeName.setColumns(25);
		
		addRowToOptions(labelEmployeeName, formattedTextFieldEmployeeName);
		
		JLabel labelTempPassword = new JLabel("Temporary Password:");
		textFieldTempPassword = new JTextField();
		textFieldTempPassword.setColumns(15);
		
		addRowToOptions(labelTempPassword, textFieldTempPassword);
	}
	
	private void addRowToOptions(JLabel optionLabel, JTextField optionTextField)
	{
		GridBagConstraints gbc_optionLabel = new GridBagConstraints();
		gbc_optionLabel.anchor = GridBagConstraints.EAST;
		gbc_optionLabel.insets = new Insets(0, 0, 5, 5);
		gbc_optionLabel.gridx = 0;
		gbc_optionLabel.gridy = nextOptionRow;
		panelActionOptions.add(optionLabel, gbc_optionLabel);
		
		optionTextField.setColumns(20);
		GridBagConstraints gbc_optionTextField = new GridBagConstraints();
		gbc_optionTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_optionTextField.insets = new Insets(0, 0, 5, 5);
		gbc_optionTextField.anchor = GridBagConstraints.WEST;
		gbc_optionTextField.gridx = 1;
		gbc_optionTextField.gridy = nextOptionRow;
		panelActionOptions.add(optionTextField, gbc_optionTextField);
		
		nextOptionRow++;
	}
}
