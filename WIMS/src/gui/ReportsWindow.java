package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

import java.awt.BorderLayout;

import controller.DateLabelFormatter;
import controller.ErrorStatusReportable;
import controller.SQL_Handler;
import controller.WidthAdjuster;

import java.awt.FlowLayout;

import javax.swing.JTable;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultComboBoxModel;

import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Properties;

import javax.swing.JCheckBox;

import org.jdatepicker.impl.*;

public class ReportsWindow implements ErrorStatusReportable{

	private JFrame frame;
	private JTable table;
	private JPanel reportSelectionOptionsPanel;
	private JPanel reportSelectPanel;
	private JPanel reportOptionsPanel;
	private JComboBox comboBoxReportType;
	private JComboBox comboBoxShippedReceivedBeforeAfter;
	private JScrollPane reportTableScrollPane;
	private WidthAdjuster TableWidthAdjuster;
	private Connection DBConnection;
	private SQL_Handler SQLHandler;
	
	private static final int MIN_AGING_ITEMS_DAYS = 0;
	private static final int MAX_AGING_ITEMS_DAYS = 9999;
	private static final String AGING_ITEMS_REPORTNAME = "Aging Items";
	private static final String EMPLOYEES_REPORTNAME = "Employees";
	private static final String ITEM_OVERVIEW_REPORTNAME = "Item Overview";
	private static final String SHIPPED_BY_DATE_REPORTNAME = "Shipped by Date";
	private static final String RECEIVED_BY_DATE_REPORTNAME = "Received by Date";
	private static final String[] REPORT_TYPES = {AGING_ITEMS_REPORTNAME, EMPLOYEES_REPORTNAME, ITEM_OVERVIEW_REPORTNAME,
												 SHIPPED_BY_DATE_REPORTNAME, RECEIVED_BY_DATE_REPORTNAME};
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ReportsWindow window = new ReportsWindow();
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
	public ReportsWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		initializeReportFrame();
		
		initializeReportSelectionOptionsPanel();
		initializeReportSelectPanel();
		
		initializeReportComboBox();
		initializeGenerateButton();
		
		initializeReportOptionsPanel();
		
		initializeTable();
		initializeExport();
		initializeBorderStruts();
	}
	
	private void initializeReportFrame()
	{
		frame = new JFrame();
		frame.setTitle("WIMS - Reports");
		frame.setBounds(100, 100, 718, 534);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
	}
	
	private void initializeReportSelectionOptionsPanel() {
		reportSelectionOptionsPanel = new JPanel();
		frame.getContentPane().add(reportSelectionOptionsPanel, BorderLayout.NORTH);
		reportSelectionOptionsPanel.setLayout(new BorderLayout(0, 0));
	}

	private void initializeReportSelectPanel()
	{
		reportSelectPanel = new JPanel();
		FlowLayout fl_reportSelectPanel = (FlowLayout) reportSelectPanel.getLayout();
		fl_reportSelectPanel.setVgap(10);
		fl_reportSelectPanel.setAlignment(FlowLayout.LEFT);
		reportSelectionOptionsPanel.add(reportSelectPanel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Report Type:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		reportSelectPanel.add(lblNewLabel);
	}
	
	private void initializeReportComboBox()
		{
			comboBoxReportType = new JComboBox();
			comboBoxReportType.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					String itemSelected = (String) comboBoxReportType.getSelectedItem();
					displayOptionsForReport(itemSelected);
				}
			});
			comboBoxReportType.setFont(new Font("Tahoma", Font.PLAIN, 18));
			comboBoxReportType.setModel(new DefaultComboBoxModel(REPORT_TYPES));
			reportSelectPanel.add(comboBoxReportType);
		}
	
	private void displayOptionsForReport(String reportName)
	{
		clearCurrentOptions();
		switch (reportName){
		case AGING_ITEMS_REPORTNAME: 
			displayAgingItemsOptions();
			break;
		case EMPLOYEES_REPORTNAME: 
			displayEmployeeOptions();
			break;
		case ITEM_OVERVIEW_REPORTNAME:
			displayItemOverviewOptions();
			break;
		case SHIPPED_BY_DATE_REPORTNAME: 
			displayShippedByDateOptions();
			break;
		case RECEIVED_BY_DATE_REPORTNAME: 
			displayReceivedByDateOptions();
			break;
		}
	}

	private void clearCurrentOptions()
	{
		reportOptionsPanel.removeAll();
		reportOptionsPanel.revalidate();
		reportOptionsPanel.repaint();
	}
	
	private JPanel addPanelToOptions()
	{
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		reportOptionsPanel.add(panel);
		return panel;
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
    	return datePicker;
	}
	
	private void displayReportItemsExecutedByDateOptions(String action) {
		JPanel row1 = addPanelToOptions();
		JPanel row2 = addPanelToOptions();
		
		
		JLabel labelItemsShippedReceivedBy = new JLabel("Displayed items " + action + ":");
		
		JDatePickerImpl receivedDatePicker = getDatePicker();
		
		comboBoxShippedReceivedBeforeAfter = new JComboBox(new String[]{"Before", "After"});
		
		row1.add(labelItemsShippedReceivedBy);
		row2.add(comboBoxShippedReceivedBeforeAfter);
		row2.add(receivedDatePicker);
	}

	private void displayReceivedByDateOptions() {
		displayReportItemsExecutedByDateOptions("received");
	}
	
	private void displayShippedByDateOptions() {
		displayReportItemsExecutedByDateOptions("shipped");
	}

	private void displayItemOverviewOptions() {
		// TODO Auto-generated method stub
		
	}

	private void displayEmployeeOptions() {
		JPanel row1 = addPanelToOptions();
		JPanel row2 = addPanelToOptions();
		
		JCheckBox chckbxEmployees = new JCheckBox("Display Data about Non-Manager Employees");
		row1.add(chckbxEmployees);
		
		JCheckBox chckbxManagers = new JCheckBox("Display Data about Managers");
		row2.add(chckbxManagers);
	}

	private void displayAgingItemsOptions() {
		JPanel row1 = addPanelToOptions();
		
		JLabel lblItemsOlderThan = new JLabel("Display items older than:");
		row1.add(lblItemsOlderThan);
		
		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(MIN_AGING_ITEMS_DAYS);
	    formatter.setMaximum(MAX_AGING_ITEMS_DAYS);
	    formatter.setAllowsInvalid(false);
	    // If you want the value to be committed on each keystroke instead of focus lost
	    formatter.setCommitsOnValidEdit(true);
	    JFormattedTextField textFieldItemsOlderThan = new JFormattedTextField(formatter);

	    textFieldItemsOlderThan.addKeyListener(new KeyListener() {
	    	
			@Override
			public void keyPressed(KeyEvent e) {
				if(textFieldItemsOlderThan.getText().length() > 0)
				{
					//parse text to integer, safe because doc filter only allows ints
					int currentValue = Integer.parseInt(textFieldItemsOlderThan.getText());
					//when up is pressed, increment, when down is pressed, decrement
					if (e.getKeyCode() == KeyEvent.VK_UP && currentValue < MAX_AGING_ITEMS_DAYS) {
			            currentValue++;
			        }else if (e.getKeyCode() == KeyEvent.VK_DOWN && currentValue > MIN_AGING_ITEMS_DAYS) {
			            currentValue--;
			        }
					textFieldItemsOlderThan.setText("" + currentValue);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}
		});
	    
		row1.add(textFieldItemsOlderThan);
		textFieldItemsOlderThan.setColumns(3);
		
		JLabel lblDays = new JLabel("days");
		row1.add(lblDays);
	}
	
	private void initializeGenerateButton(){
		JPanel generateButtonPanel = new JPanel();
		FlowLayout fl_generateButtonPanel = (FlowLayout) generateButtonPanel.getLayout();
		fl_generateButtonPanel.setAlignment(FlowLayout.RIGHT);
		reportSelectionOptionsPanel.add(generateButtonPanel, BorderLayout.SOUTH);

		
		JButton generateButton = new JButton("Generate");
		generateButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
		generateButtonPanel.add(generateButton);
		generateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				generateReport((String) comboBoxReportType.getSelectedItem());
			}
		});
		generateButton.setHorizontalAlignment(SwingConstants.RIGHT);
	}
	
	private void generateReport(String reportName)
	{
		switch (reportName){
		case AGING_ITEMS_REPORTNAME: 
			displayAgingItemsReport();
			break;
		case EMPLOYEES_REPORTNAME: 
			displayEmployeeReport();
			break;
		case ITEM_OVERVIEW_REPORTNAME:
			displayItemOverviewReport();
			break;
		case SHIPPED_BY_DATE_REPORTNAME: 
			displayShippedByDateReport();
			break;
		case RECEIVED_BY_DATE_REPORTNAME: 
			displayReceivedByDateReport();
			break;
		}
	}
	
	private void displayReceivedByDateReport() {
		// TODO Auto-generated method stub
		
	}

	private void displayShippedByDateReport() {
		// TODO Auto-generated method stub
		
	}

	private void displayItemOverviewReport() {
		// TODO Auto-generated method stub
		
	}

	private void displayEmployeeReport() {
		try{
		ResultSet employeeResultSet = SQL_Handler.getAllEmp();
		List<String[]> employeeInfoList = SQL_Handler.getResultSetAsListOfArrays(employeeResultSet);
		String[] columnNames = SQL_Handler.getColumnNamesFromResultSet(employeeResultSet);
		populateTable(employeeInfoList, columnNames);
		}catch(SQLException ex)
		{
			JOptionPane.showMessageDialog(frame, "An unexpected database error occured when " 
					+ "trying to retrieve employee data", "Employee Query Failed", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	private void populateTable(List<String[]> dataList, String[] columnNames) {
		
        String[][] dataArrays = new String[dataList.size()][];
        dataArrays = dataList.toArray(dataArrays);
		populateTable(dataArrays, columnNames);
	}
	
	private void populateTable(String[][] dataList, String[] columnNames) {
		DefaultTableModel model = new DefaultTableModel(dataList, columnNames);
		table.setModel(model);
		model.fireTableDataChanged();
	}
	
	

	private void displayAgingItemsReport() {
		// TODO Auto-generated method stub
		
	}

	private void initializeReportOptionsPanel()
	{
		reportOptionsPanel = new JPanel();
		reportSelectionOptionsPanel.add(reportOptionsPanel, BorderLayout.CENTER);
		reportOptionsPanel.setLayout(new BoxLayout(reportOptionsPanel, BoxLayout.Y_AXIS));
		
		displayOptionsForReport((String) comboBoxReportType.getSelectedItem());
	}
	
	private void initializeTable()
	{
		reportTableScrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.getContentPane().add(reportTableScrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		TableWidthAdjuster = new WidthAdjuster(table);
		
		
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setFillsViewportHeight(true);
		Object[][] defaultData = new Object[26][26]; 
		String[] defaultColNames = new String[26];
		//MyTableModel tabelModel = new MyTableModel(defaultData, defaultColNames)
		table.setModel(new DefaultTableModel(defaultData, defaultColNames));

//		JTableHeader header = table.getTableHeader();
//		header.setUpdateTableInRealTime(true);
//		header.addMouseListener(tableModel.new ColumnListener(table));
//		header.setReorderingAllowed(true);
		reportTableScrollPane.setViewportView(table);
	}

	private void initializeExport(){
		JPanel exportPanel = new JPanel();
		frame.getContentPane().add(exportPanel, BorderLayout.SOUTH);
		exportPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnNewButton_1 = new JButton("Export");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 26));
		btnNewButton_1.setHorizontalAlignment(SwingConstants.RIGHT);
		exportPanel.add(btnNewButton_1);
	}

	private void initializeBorderStruts()
	{
		Component horizontalStrut = Box.createHorizontalStrut(20);
		frame.getContentPane().add(horizontalStrut, BorderLayout.WEST);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		frame.getContentPane().add(horizontalStrut_1, BorderLayout.EAST);
	}

	@Override
	public void displayErrorStatus(String errorText) {
		// TODO Auto-generated method stub
		
	}
}
