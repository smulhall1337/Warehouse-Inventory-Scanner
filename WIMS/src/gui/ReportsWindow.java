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

import controller.ComponentProvider;
import controller.DateLabelFormatter;
import controller.ErrorStatusReportable;
import controller.MainWindowInfoController;
import controller.SQL_Handler;
import controller.WidthAdjuster;

import java.awt.FlowLayout;

import javax.swing.JTable;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.border.Border;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JCheckBox;

import org.jdatepicker.impl.*;

public class ReportsWindow extends JPanel implements ErrorStatusReportable {

	private JTable table;
	private JPanel reportSelectPanel;
	private JPanel reportOptionsPanel;
	private JComboBox comboBoxReportType;
	private JComboBox comboBoxShippedReceivedBeforeAfter;
	private MainWindowInfoController infoController;
	private SwingWorker<Boolean, Void> updateTableProcess;
	private int maxWidth;
	private int maxHeight;
	private JFrame frame;
	private JPanel outerPanel;
	
	private static final int MIN_AGING_ITEMS_DAYS = 0;
	private static final int MAX_AGING_ITEMS_DAYS = 9999;
	private static final String AGING_ITEMS_REPORTNAME = "Aging Items";
	private static final String EMPLOYEES_REPORTNAME = "Employees";
	private static final String ITEM_OVERVIEW_REPORTNAME = "Item Overview";
	private static final String SHIPPED_BY_DATE_REPORTNAME = "Shipped by Date";
	private static final String RECEIVED_BY_DATE_REPORTNAME = "Received by Date";
	private static final String[] REPORT_TYPES = {AGING_ITEMS_REPORTNAME, EMPLOYEES_REPORTNAME, ITEM_OVERVIEW_REPORTNAME,
												 SHIPPED_BY_DATE_REPORTNAME, RECEIVED_BY_DATE_REPORTNAME};
	
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					ReportsWindow window = new ReportsWindow();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public ReportsWindow(MainWindowInfoController controller, int maxWidth, int maxHeight) {
		this.infoController = controller;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		initializeReportFrame();
		initializeOuterPanel();
		initializeReportSelectPanel();
		
		initializeReportComboBox();
		initializeGenerateButton();
		
		initializeReportOptionsPanel();
		
		initializeExport();
		initializeBorderStruts();
	}
	
	private void initializeReportFrame()
	{
		frame = new JFrame();
		frame.setTitle("WIMS - Reports");
		frame.setBounds(100, 100, 718, 534);
		frame.setMaximumSize(new Dimension(maxWidth, maxHeight));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
	}
	
	private void initializeOuterPanel(){
		
		//A wrapper panel with a boxlayout so that the maximum size of the entity selection
		//and field columns selection panel is respected. Needed since borderlayout does
		//not respect maximum size.
		JPanel resizingPanel = new JPanel();
		BoxLayout resizingPanelLayout = new BoxLayout(resizingPanel, BoxLayout.X_AXIS);
		resizingPanel.setLayout(resizingPanelLayout);
		//Add a blank panel so there is a panel on the left that can resize to fill the empty space
		JPanel leftPanelForSpacing = new JPanel();
		//leftPanelForSpacing.setMaximumSize(new Dimension(OPTIONSPANEL_MAX_LEFT_SPACE, IRRELEVANT_MAX_DIMENSION));
		//leftPanelForSpacing.add(Box.createRigidArea(new Dimension(TABLE_PANEL_MARGIN, IRRELEVANT_MIN_DIMENSION)));
		resizingPanel.add(leftPanelForSpacing);
		//Add the panel to the middle
		outerPanel = new JPanel();
		outerPanel.setMaximumSize(new Dimension(maxWidth, maxHeight));
		outerPanel.setLayout(new BorderLayout(0, 0));
		resizingPanel.add(outerPanel);
		//Add a blank panel so there is a panel on the right that can resize to fill the empty space
		resizingPanel.add(new JPanel());
		//Give the panel an etched border
		Border borderEtched = BorderFactory.createEtchedBorder();
		outerPanel.setBorder(borderEtched);
		outerPanel.add(new JLabel("sfas testing"), BorderLayout.NORTH);
		
		frame.getContentPane().add(resizingPanel);
	}

	private void initializeReportSelectPanel()
	{
		reportSelectPanel = new JPanel();
		FlowLayout fl_reportSelectPanel = (FlowLayout) reportSelectPanel.getLayout();
		fl_reportSelectPanel.setVgap(10);
		fl_reportSelectPanel.setAlignment(FlowLayout.LEFT);
		frame.getContentPane().add(reportSelectPanel, BorderLayout.NORTH);
		
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
	
	private void displayReportItemsExecutedByDateOptions(String action) {
		JPanel row1 = addPanelToOptions();
		JPanel row2 = addPanelToOptions();
		
		
		JLabel labelItemsShippedReceivedBy = new JLabel("Displayed items " + action + ":");
		
		JDatePickerImpl receivedDatePicker = ComponentProvider.getDatePicker();
		
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
		frame.getContentPane().add(generateButtonPanel, BorderLayout.SOUTH);

		
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
		Object[][] employeeInfoObj = SQL_Handler.getResultSetAs2DObjArray(employeeResultSet);
		
		
		String[] columnNames = SQL_Handler.getColumnNamesFromResultSet(employeeResultSet);
		//infoController.getMainWindow().
		}catch(SQLException ex)
		{
			JOptionPane.showMessageDialog(frame, "An unexpected database error occured when " 
					+ "trying to retrieve employee data", "Employee Query Failed", JOptionPane.ERROR_MESSAGE);
		}
		
	}

//	private void updateTable(){
//		updateTableProcess = new SwingWorker<Boolean, Void>() {
//
//	        @Override
//	        protected Boolean doInBackground() throws Exception {
//	        	//update the table and save whether it was successfully updated
//	            boolean success = updateTableBasedOnSelection(entityName, fieldName, fieldModifier, fieldModifierValue);
//	            boolean modifierValueEntered = entityAndFieldSelectPanel.isModifierValueEntered();
//            	String statusMessageEnding = entityName;
//            	if(modifierValueEntered)
//            		statusMessageEnding = statusMessageEnding + " with " + fieldName + " " + fieldModifier + " " + fieldModifierValue;
//            	statusMessageEnding += ".";
//	            if(success) //if the table was successfully updated
//	            {
//	            	//activate the checkboxes for this entity and clear the checkbox warning
//		            showColumnsForPanel.setAreCheckBoxesAreEnabled(entityName, true); //TODO double check this
//		            showColumnsForPanel.clearErrorStatus(); 
//		            //clear the current error status
//					clearErrorStatus();
//					//update the column widths of the table
//					mainTable.updateColumnWidths();
//					//if there are results, display the success message for the input query
//					String successMessage = "Displaying results for " + statusMessageEnding;
//					displaySuccessStatus(successMessage);
//	            }else{ //if the table was not successfully updated 
//	            	//show the error that there were no results for the input query
//	            	String error = "There are no results for " + statusMessageEnding;
//					displayErrorStatus(error);
//	            }
//	            return success;
//	        }
//
//	        @Override
//	        protected void done() {
//	        	//System.out.println("done!!!!!!!!!!!!!!!!!");
//	        	//when the update finishes
//	        	//ugly fix to bug of scrollpane not showing scrollbar unless you resize it after updating the table
//	        	int scrollPaneOrigWidth = mainTableScrollPane.getWidth();
//	        	int scrollPaneOrigHeight = mainTableScrollPane.getHeight();
//	        	mainTableScrollPane.setSize(scrollPaneOrigWidth+1, scrollPaneOrigHeight+1);
//	        	mainTableScrollPane.setSize(scrollPaneOrigWidth, scrollPaneOrigHeight);
//	        	//update the current table entity to whatever entity was selected when the table updated
//	        	//making the loading icon invisible
//	        	lblLoadingIcon.setVisible(false);
//	        }
//	    };
//	    updateTableProcess.execute();
//	}
//});
//Component rigidAreaRight = Box.createRigidArea(new Dimension(UPDATE_BUTTON_RIGHT_SPACING, UPDATE_BUTTON_RIGHT_SPACING));
//updateButtonPanel.add(rigidAreaRight);
//	}
//	

	private void displayAgingItemsReport() {
		// TODO Auto-generated method stub
		
	}

	private void initializeReportOptionsPanel()
	{
		reportOptionsPanel = new JPanel();
		frame.getContentPane().add(reportOptionsPanel, BorderLayout.CENTER);
		reportOptionsPanel.setLayout(new BoxLayout(reportOptionsPanel, BoxLayout.Y_AXIS));
		
		displayOptionsForReport((String) comboBoxReportType.getSelectedItem());
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

	@Override
	public void clearErrorStatus() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayNeutralStatus(String neutralText) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displaySuccessStatus(String successText) {
		// TODO Auto-generated method stub
		
	}
}
