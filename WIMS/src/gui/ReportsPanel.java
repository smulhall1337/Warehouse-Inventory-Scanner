package gui;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.border.Border;
import javax.swing.text.NumberFormatter;

import org.jdatepicker.impl.JDatePickerImpl;

import controller.ComponentProvider;
import controller.DocIntFilter;
import controller.ErrorStatusReportable;
import controller.ExcelExporter;
import controller.MainWindowInfoController;
import controller.SQL_Handler;

public class ReportsPanel extends JPanel implements ErrorStatusReportable {
	
	private static final int MIN_AGING_ITEMS_DAYS = 0;
	private static final int MAX_AGING_ITEMS_DAYS = 9999;
	private static final String AGING_ITEMS_REPORTNAME = "Aging Items";
	private static final String EMPLOYEES_REPORTNAME = "Employees";
	private static final String ITEM_OVERVIEW_REPORTNAME = "Item Overview";
	private static final String SHIPPED_BY_DATE_REPORTNAME = "Shipped by Date";
	private static final String RECEIVED_BY_DATE_REPORTNAME = "Received by Date";
	private static final String[] REPORT_TYPES = {AGING_ITEMS_REPORTNAME, EMPLOYEES_REPORTNAME, ITEM_OVERVIEW_REPORTNAME,
												 SHIPPED_BY_DATE_REPORTNAME, RECEIVED_BY_DATE_REPORTNAME};
	
	private static final String LOADING_GIF_ICON_NAME = "loading.gif";

	private static final String STANDARD_FONT_NAME = "Tahoma";
	private static final Font LOADING_STATUS_FONT = new Font(STANDARD_FONT_NAME, Font.PLAIN, 14);
	private static final Font BUTTON_FONT  = new Font(STANDARD_FONT_NAME, Font.PLAIN, 22);
	private JComboBox comboBoxReportType;
	private JPanel selectionPanel;
	private JPanel optionPanel;
	private JPanel buttonsPanel;
	private JPanel wrapperPanel;
	private JPanel leftSpacer;
	private JPanel rightSpacer;
	//the index of the next option row in the gridbaglayout. Used with addRowToOptions()
	//to manage the adding of field name checkboxes to that panel	
	private int nextOptionRow;
	//initial starting option row, this is the value the layout defaults to when it is cleared
	private static final int STARTING_OPTION_ROW = 1;
	private Dimension maxDim;
	private MainWindowInfoController infoController;
	private JButton btnGenerate;
	private JButton btnExport;
	private JLabel lblReportType;
	private JLabel labelItemsShippedReceivedBy;
	private JDatePickerImpl receivedDatePicker;
	private Component comboBoxShippedReceivedBeforeAfter;
	private JCheckBox chckbxEmployees;
	private JCheckBox chckbxManagers;
	private JLabel lblItemsOlderThan;
	private JFormattedTextField textFieldItemsOlderThan;
	private JLabel lblDays;
	private JLabel lblReportOptions;
	private Container labelPanel;
	private SwingWorker<Boolean, Void> updateTableProcess;
	private SwingWorker<Boolean, Void> exportTableProcess;
	private JLabel lblLoadingIcon;
	private String lastWrittenFile = "";
	
	public ReportsPanel(MainWindowInfoController controller) {
		this.infoController = controller;
		this.nextOptionRow = STARTING_OPTION_ROW;
		initialize();
	}
	
	private void initialize(){
		initializeFrame();
		initializeWrapperPanel();
	}
	
	private void initializeFrame(){
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}
	
	private void initializeWrapperPanel(){
		leftSpacer = new JPanel();
		leftSpacer.setMaximumSize(new Dimension(MainWindow.OPTIONSPANEL_MAX_LEFT_SPACE, Integer.MAX_VALUE));
		leftSpacer.add(Box.createRigidArea(new Dimension(MainWindow.TABLE_PANEL_MARGIN,  1)));
		this.add(leftSpacer);
		wrapperPanel = new JPanel();
		this.add(wrapperPanel);
		wrapperPanel.add(new JButton("heyyyyy"));
		wrapperPanel.setLayout(new BorderLayout(0,0));
		rightSpacer = new JPanel();
		this.add(rightSpacer);
		Dimension dim = new Dimension(MainWindow.MAX_TAB_PANEL_WIDTH, MainWindow.MAX_TAB_PANEL_HEIGHT);
		wrapperPanel.setMaximumSize(dim);
		//wrapperPanel.setMinimumSize(dim);
		//wrapperPanel.setPreferredSize(dim);
		//Give the panel an etched border
		Border borderEtched = BorderFactory.createEtchedBorder();
		wrapperPanel.setBorder(borderEtched);
		initializeSelectionPanel();
		initializeLabelPanel();
		intializeOptionsPanel();
		initializeButtonsPanel();
	}
	
	private void initializeLabelPanel() {
		labelPanel = new JPanel();
		BoxLayout labelLayout = new BoxLayout(labelPanel, BoxLayout.Y_AXIS);
		labelPanel.setLayout(labelLayout);
		labelPanel.add(Box.createGlue());
		lblReportOptions = new JLabel("Report Options:");
		lblReportOptions.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblReportOptions.setMaximumSize( lblReportOptions.getPreferredSize() );
		
		labelPanel.add(lblReportOptions);
		labelPanel.add(new JPanel(), BorderLayout.SOUTH);
		wrapperPanel.add(lblReportOptions, BorderLayout.WEST);
	}

	private void initializeSelectionPanel(){
		selectionPanel = new JPanel();
		selectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		wrapperPanel.add(selectionPanel, BorderLayout.NORTH);
		intializeReportSelector();
	}
	
	private void intializeOptionsPanel(){
		optionPanel = new JPanel();
		wrapperPanel.add(optionPanel, BorderLayout.CENTER);
		GridBagLayout gbl_optionsPanel = new GridBagLayout();
		gbl_optionsPanel.columnWidths = new int[] {20, 100, 100, 100};
		gbl_optionsPanel.rowHeights = new int[] {0, 30, 30, 30, 30, 0};
		gbl_optionsPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_optionsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		optionPanel.setLayout(gbl_optionsPanel);
		Border borderLoweredBevel = BorderFactory.createLoweredBevelBorder();
		optionPanel.setBorder(borderLoweredBevel);
		JPanel emptyPanel = new JPanel();
		Component rigidAreaRight = Box.createRigidArea
				(new Dimension(ColumnHeaderControllerPanel.RIGHT_MARGIN, ColumnHeaderControllerPanel.RIGHT_MARGIN));
		emptyPanel.add(rigidAreaRight);
		wrapperPanel.add(emptyPanel, BorderLayout.EAST);
	}
	
	private void initializeButtonsPanel(){
		buttonsPanel = new JPanel();
		wrapperPanel.add(buttonsPanel, BorderLayout.SOUTH);
		FlowLayout fl_buttonsPanel = (FlowLayout) buttonsPanel.getLayout();
		fl_buttonsPanel.setAlignment(FlowLayout.RIGHT);
		
		initializeLoadingLabel();
		initializeGenerateButton();
		initializeExportButton();
		Component rigidAreaRight = Box.createRigidArea(new Dimension(MainWindow.UPDATE_BUTTON_RIGHT_SPACING, MainWindow.UPDATE_BUTTON_RIGHT_SPACING));
		buttonsPanel.add(rigidAreaRight);
	}
	
	private void initializeLoadingLabel(){
		lblLoadingIcon = new JLabel("Getting info...");
		ImageIcon loadingGif = new ImageIcon(getClass().getClassLoader().getResource(LOADING_GIF_ICON_NAME));
		//lblLoadingIcon.setForeground(updateButtonPanel.getBackground());
		lblLoadingIcon.setFont(LOADING_STATUS_FONT);
		lblLoadingIcon.setIcon(loadingGif);
		lblLoadingIcon.setVisible(false);
		lblLoadingIcon.setHorizontalTextPosition(SwingConstants.LEFT);
		buttonsPanel.add(lblLoadingIcon);
	}
	private void initializeGenerateButton(){
		btnGenerate = new JButton("Generate");
		buttonsPanel.add(btnGenerate);
		btnGenerate.setFont(BUTTON_FONT);
		btnGenerate.setHorizontalAlignment(SwingConstants.RIGHT);
		btnGenerate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent ae) {
				updateTableProcess = new SwingWorker<Boolean, Void>() {

			        @Override
			        protected Boolean doInBackground() throws Exception 
			        {
			        	lblLoadingIcon.setVisible(true);
			        	//update the table and save whether it was successfully updated
			            boolean success = generateReport(comboBoxReportType.getSelectedItem().toString()); //TODO //updateTableBasedOnSelection(;
			            
			            if(success) //if the table was successfully updated
			            {
				            //clear the current error status
							clearErrorStatus();
							//update the column widths of the table
							infoController.getMainTable().updateColumnWidths();
							//if there are results, display the success message for the input query
							String successMessage = "Displaying results for " 
									+ comboBoxReportType.getSelectedItem().toString() + ".";
							displaySuccessStatus(successMessage);
			            }else{ //if the table was not successfully updated 
			            	//show the error that there were no results for the input query
			            	String error = "There are no results in the database for this report.";
							displayErrorStatus(error);
			            }
			            return success;
			        }

			        @Override
			        protected void done() {
			        	//when the update finishes
			        	//ugly fix to bug of scrollpane not showing scrollbar unless you resize it after updating the table
			        	int scrollPaneOrigWidth = infoController.getScrollPane().getWidth();
			        	int scrollPaneOrigHeight = infoController.getScrollPane().getHeight();
			        	infoController.getScrollPane().setSize(scrollPaneOrigWidth+1, scrollPaneOrigHeight+1);
			        	infoController.getScrollPane().setSize(scrollPaneOrigWidth, scrollPaneOrigHeight);
			        	//update the current table entity to whatever entity was selected when the table updated
			        	//making the loading icon invisible
			        	lblLoadingIcon.setVisible(false);
			        }
			    };
			    updateTableProcess.execute();
			}
		});
	}
	
	private void initializeExportButton(){
		btnExport = new JButton("Export");
		btnExport.setFont(BUTTON_FONT);
		btnExport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				System.out.println("EXPORT PERFORMED");
				exportTableProcess = new SwingWorker<Boolean, Void>() {
					@Override
			        protected Boolean doInBackground() throws Exception 
			        {
						System.out.println("starting background process");
			        	lblLoadingIcon.setVisible(true);
			        	//update the table and save whether it was successfully updated
			            boolean success = exportReport(); //TODO 
			            
			            if(success) //if the table was successfully updated
			            {
				            //clear the current error status
							clearErrorStatus();
							//update the column widths of the table
							//if there are results, display the success message for the input query
							String successMessage = "Report exported to " + lastWrittenFile + ".";
							displaySuccessStatus(successMessage);
			            }else{ //if the table was not successfully updated 
			            	//show the error that there were no results for the input query
			            	String error = "There was an error exporting this report.";
							displayErrorStatus(error);
			            }
			            return success;
			        }

			        @Override
			        protected void done() {
			        	lblLoadingIcon.setVisible(false);
			        }
			    };
			    exportTableProcess.execute();
			}
		});
		buttonsPanel.add(btnExport);
	}

	private void intializeReportSelector(){
		lblReportType = new JLabel("Report Type:");
		lblReportType.setFont(new Font("Tahoma", Font.PLAIN, 18));
		selectionPanel.add(lblReportType);
	
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
		selectionPanel.add(comboBoxReportType);
		wrapperPanel.add(selectionPanel, BorderLayout.NORTH);
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
		nextOptionRow = STARTING_OPTION_ROW;
		optionPanel.removeAll();
		optionPanel.revalidate();
		optionPanel.repaint();
	}
	
	private void displayReportItemsExecutedByDateOptions(String action) {
		
		labelItemsShippedReceivedBy = new JLabel("Displayed items " + action + ":");
		receivedDatePicker = ComponentProvider.getDatePicker();
		comboBoxShippedReceivedBeforeAfter = new JComboBox(new String[]{"Before", "After", "On"});
		
		addRowToOptions(labelItemsShippedReceivedBy, null);
		addRowToOptions(comboBoxShippedReceivedBeforeAfter, null);
		addRowToOptions(receivedDatePicker, null);
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
		chckbxEmployees = new JCheckBox("Display Data about Non-Manager Employees");
		chckbxManagers = new JCheckBox("Display Data about Managers");
		addRowToOptions(chckbxEmployees, chckbxManagers);
	}

	private void displayAgingItemsOptions() {
		lblItemsOlderThan = new JLabel("Display items older than:");
	    textFieldItemsOlderThan = new JFormattedTextField();
	    textFieldItemsOlderThan.setDocument(new DocIntFilter());
		textFieldItemsOlderThan.setColumns(3);
		lblDays = new JLabel("days");
	}
	
	private boolean displayReceivedByDateReport() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean displayShippedByDateReport() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean displayItemOverviewReport() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean displayEmployeeReport() {
		try{
			lblLoadingIcon.setText("Generating employee report...");
		ResultSet employeeResultSet = SQL_Handler.getAllEmp();
		Object[][] employeeInfoObj = SQL_Handler.getResultSetAs2DObjArray(employeeResultSet);
		String[] columnNames = SQL_Handler.getColumnNamesFromResultSet(employeeResultSet);
		infoController.getMainWindow().updateTable(employeeInfoObj, columnNames);
		//infoController.getMainWindow().
		}catch(SQLException ex)
		{
			JOptionPane.showMessageDialog(this, "An unexpected database error occured when " 
					+ "trying to retrieve employee data", "Employee Query Failed", JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

	private boolean displayAgingItemsReport() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean generateReport(String reportName)
	{
		switch (reportName){
		case AGING_ITEMS_REPORTNAME: 
			return displayAgingItemsReport();
		case EMPLOYEES_REPORTNAME: 
			return displayEmployeeReport();
		case ITEM_OVERVIEW_REPORTNAME:
			return displayItemOverviewReport();
		case SHIPPED_BY_DATE_REPORTNAME: 
			return displayShippedByDateReport();
		case RECEIVED_BY_DATE_REPORTNAME: 
			return displayReceivedByDateReport();
		}
		return false;
	}
	
	private boolean exportReport() {
		try {
			File writeFile = new File("./" 
		+ "WIMS_REPORT_" + this.comboBoxReportType.getSelectedItem() + "_" + System.currentTimeMillis());
			//writeFile.createNewFile(); 
			System.out.println("writing table");
			ExcelExporter.saveTable(infoController.getMainTable(), writeFile);
			System.out.println("done file writing");
			this.lastWrittenFile = writeFile.getAbsolutePath();
			System.out.println(lastWrittenFile + "aaa");
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
			return false;
		}
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
	
	/**
	 * Add a row of components to the report options panel. Any or all components can be null,
	 * where (a) blank space(s) will be left in the row.
	 * @param firstColumn	the component to add in the first column of this row
	 * @param secondColumn	the component to add in the second column of this row
	 */
	private void addRowToOptions(Component firstColumn, Component secondColumn)
	{
		//add a rigid area to the left to put a slight left margin before the checkboxes
		Component rigidAreaLeft = Box.createRigidArea(new Dimension(20, 20));
		GridBagConstraints gbc_rigidAreaLeft = new GridBagConstraints();
		gbc_rigidAreaLeft.anchor = GridBagConstraints.WEST;
		gbc_rigidAreaLeft.insets = new Insets(0, 0, 5, 5);
		gbc_rigidAreaLeft.gridx = 0;
		gbc_rigidAreaLeft.gridy = nextOptionRow;
		optionPanel.add(rigidAreaLeft, gbc_rigidAreaLeft);
		
		//if the first column is null, add nothing
		if(firstColumn != null)
		{
			GridBagConstraints gbc_firstColumn = new GridBagConstraints();
			gbc_firstColumn.anchor = GridBagConstraints.WEST;
			gbc_firstColumn.insets = new Insets(0, 0, 5, 5);
			gbc_firstColumn.gridx = 1; //first column
			gbc_firstColumn.gridy = nextOptionRow; //add on the nextOptionRow. this keeps track of the next row
			optionPanel.add(firstColumn, gbc_firstColumn);
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
			optionPanel.add(secondColumn, gbc_secondColumn);
		}
		//now that we are done adding to this row, increment the counter that tracks the next row index
		nextOptionRow++;
	}
	
	/**
	 * Add a row of components to the options display panel. Any or all components can be null,
	 * for which (a) blank space(s) will be left in the row.
	 * @param firstColumn	the component to add in the first column of this row
	 * @param secondColumn	the component to add in the second column of this row
	 * @param thirdColumn	the component to add in the third column of this row
	 */
	private void addRowToOptions(Component firstColumn, Component secondColumn, Component thirdColumn)
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
			optionPanel.add(thirdColumn, gbc_thirdColumn);
		}
		
		//add the first two columns to the row
		addRowToOptions(firstColumn, secondColumn);
	}
}
