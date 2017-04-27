package gui;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;

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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.Timer;

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
import java.text.SimpleDateFormat;
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
	
	
	private static final String AGING_ITEMS_REPORTNAME = "Aging Items";
	private static final String PALLETS_WITH_SHORTAGES = "Pallets With Shortages/Overages";
	private static final String ITEMS_WITH_OVERAGES = "Items With Shortages/Overages";
	private static final String TOTAL_INV_WORTH = "Total Inventory Worth";
	private static final String TOP_X_PRICED_ITEMS = "Top X Priced Items";
	private static final String BOTTOM_X_PRICED_ITEMS = "Bottom X Priced Items";
	private static final String ITEMS_UNDER_RESTOCK = "Items Under Restock Threshold";
	private static final String PRICE_WEIGHT_RATIO = "Price/Weight Ratio";
	private static final String CUBIC_FT_WH = "Cubic Ft of Warehouse Inventory";
	private static final String SQUARE_FT_WH = "Square Ft of Warehouse Inventory";
	private static final String CUBIC_FT_ORDER = "Cubic Ft of Order";
	private static final String SQUARE_FT_ORDER = "Square Ft of Order";
	private static final String[] REPORT_TYPES = {AGING_ITEMS_REPORTNAME, PALLETS_WITH_SHORTAGES, ITEMS_WITH_OVERAGES,
		TOTAL_INV_WORTH, TOP_X_PRICED_ITEMS, BOTTOM_X_PRICED_ITEMS, ITEMS_UNDER_RESTOCK, PRICE_WEIGHT_RATIO,
		CUBIC_FT_WH, SQUARE_FT_WH, CUBIC_FT_ORDER, SQUARE_FT_ORDER};
	
	private static final String LOADING_GIF_ICON_NAME = "loading.gif";

	private static final String STANDARD_FONT_NAME = "Tahoma";
	private static final Font LOADING_STATUS_FONT = new Font(STANDARD_FONT_NAME, Font.PLAIN, 14);
	private static final Font BUTTON_FONT  = new Font(STANDARD_FONT_NAME, Font.PLAIN, 22);
	private static final Font QUERY_RESULT_STATUS_FONT = new Font(STANDARD_FONT_NAME, Font.PLAIN, 16);

	
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
	protected static final String DEFAULT_ERROR_TEXT = "WIMS_REPORT_Aging Items_1493238079064.csvasfasdfasdafsfdasdfafsfsdfasd";
	private String selectedReport = "";
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
	private JLabel lblOptionDescription;
	private JFormattedTextField textFieldItemsOlderThan;
	private JLabel lblDays;
	private JLabel lblReportOptions;
	private Container labelPanel;
	private SwingWorker<Boolean, Void> updateTableProcess;
	private SwingWorker<Boolean, Void> exportTableProcess;
	private JLabel lblLoadingIcon;
	private String lastWrittenFile = "";
	private boolean generateButtonPressed = false;
	private JFormattedTextField textFieldPriceItems;
	private JFormattedTextField textFieldWarehouseID;
	private JTextField textFieldOrderID;
	
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
		initializeOptionsPanel();
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
	
	private void initializeOptionsPanel(){
		optionPanel = new JPanel();
		wrapperPanel.add(optionPanel, BorderLayout.CENTER);
		GridBagLayout gbl_optionsPanel = new GridBagLayout();
		gbl_optionsPanel.columnWidths = new int[] {20, 100, 100, 0};
		gbl_optionsPanel.rowHeights = new int[] {0, 30, 30, 30, 30, 0};
		gbl_optionsPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_optionsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		optionPanel.setLayout(gbl_optionsPanel);
		Border borderLoweredBevel = BorderFactory.createLoweredBevelBorder();
		optionPanel.setBorder(borderLoweredBevel);
		JPanel emptyPanel = new JPanel();
		Component rigidAreaRight = Box.createRigidArea
				(new Dimension(ColumnHeaderControllerPanel.RIGHT_MARGIN, ColumnHeaderControllerPanel.RIGHT_MARGIN));
		//emptyPanel.add(rigidAreaRight);
		wrapperPanel.add(emptyPanel, BorderLayout.EAST);
		displayAgingItemsOptions();
	}
	
	private void initializeButtonsPanel(){
		buttonsPanel = new JPanel();
		wrapperPanel.add(buttonsPanel, BorderLayout.SOUTH);
		FlowLayout fl_buttonsPanel = (FlowLayout) buttonsPanel.getLayout();
		fl_buttonsPanel.setAlignment(FlowLayout.RIGHT);
		BoxLayout bl_buttonsPanel = new BoxLayout(buttonsPanel, BoxLayout.X_AXIS);
		buttonsPanel.setLayout(bl_buttonsPanel);
		
		initializeErrorLabel();
		initializeLoadingLabel();
		initializeGenerateButton();
		initializeExportButton();
		Component rigidAreaRight = Box.createRigidArea(new Dimension(MainWindow.UPDATE_BUTTON_RIGHT_SPACING, MainWindow.UPDATE_BUTTON_RIGHT_SPACING));
		buttonsPanel.add(rigidAreaRight);
		buttonsPanel.setMaximumSize(buttonsPanel.getSize());
		buttonsPanel.setMinimumSize(buttonsPanel.getSize());
		//buttonsPanel.setPreferredSize(buttonsPanel.getSize());
		/*
		 * updateButtonPanel = new JPanel();
			allOptionsPanel.add(updateButtonPanel, BorderLayout.SOUTH);
			updateButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			BoxLayout updatePanelLayout = new BoxLayout(updateButtonPanel, BoxLayout.X_AXIS);
			updateButtonPanel.setLayout(updatePanelLayout);
			
			lblErrorStatus = new JLabel("Placeholder text");
			lblErrorStatus.setForeground(updateButtonPanel.getBackground());
			lblErrorStatus.setFont(QUERY_RESULT_STATUS_FONT);
			Box errorStatusBox = Box.createHorizontalBox();
		    errorStatusBox.add(lblErrorStatus);
		    errorStatusBox.add(Box.createGlue());
			updateButtonPanel.add(errorStatusBox);
			
			lblLoadingIcon = new JLabel("Getting info...");
			ImageIcon loadingGif = new ImageIcon(getClass().getClassLoader().getResource(LOADING_GIF_ICON_NAME));
			lblLoadingIcon.setFont(LOADING_STATUS_FONT);
			lblLoadingIcon.setIcon(loadingGif);
			lblLoadingIcon.setVisible(false);
			lblLoadingIcon.setHorizontalTextPosition(SwingConstants.LEFT);
			updateButtonPanel.add(lblLoadingIcon);
			
			updateButton = new JButton("Update");
			updateButton.setFont(BUTTON_FONT);
			updateButtonPanel.add(updateButton);
			
			Component rigidAreaRight = Box.createRigidArea(new Dimension(UPDATE_BUTTON_RIGHT_SPACING, UPDATE_BUTTON_RIGHT_SPACING));
			updateButtonPanel.add(rigidAreaRight);
		 */
	}
	
	private void initializeErrorLabel() {
		//create a new label for error status and make the text invisible at first
		lblErrorStatus = new JLabel(DEFAULT_ERROR_TEXT);
		lblErrorStatus.setForeground(buttonsPanel.getBackground());
		lblErrorStatus.setFont(QUERY_RESULT_STATUS_FONT);
		lblErrorStatus.setHorizontalAlignment(SwingConstants.LEFT);
		//make the error status stick to the left
		Box errorStatusBox = Box.createHorizontalBox();
		errorStatusBox.add(Box.createGlue());
	    errorStatusBox.add(lblErrorStatus);
	    //errorStatusBox.setMinimumSize(lblErrorStatus.getMaximumSize());
	    //errorStatusBox.setPreferredSize(lblErrorStatus.getMaximumSize());
	    buttonsPanel.add(errorStatusBox);
	}

	private void initializeLoadingLabel(){
		lblLoadingIcon = new JLabel("Getting info............");
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
				generateButtonPressed = true;
				updateTableProcess = new SwingWorker<Boolean, Void>() {

			        @Override
			        protected Boolean doInBackground() throws Exception 
			        {
			        	lblLoadingIcon.setVisible(true);
			        	//update the table and save whether it was successfully updated
			            boolean success = generateReport(comboBoxReportType.getSelectedItem().toString()); //TODO 
			            
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
							JOptionPane.showMessageDialog(null, successMessage);
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
				selectedReport = itemSelected;
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
		case TOP_X_PRICED_ITEMS: 
			displayTopXPricedItemsOptions();
			break;
		case BOTTOM_X_PRICED_ITEMS: 
			displayBottomXPricedItemsOptions();
			break;
		case CUBIC_FT_WH:
			displayCubicFtWHOptions();
			break;
		case SQUARE_FT_WH:
			displaySquareFtWHOptions();
			break;
		case CUBIC_FT_ORDER:
			displayCubicFtOrderOptions();
			break;
		case SQUARE_FT_ORDER:
			displaySquareFtOrderOptions();
			break;
		}
	}

	private void displayOrderID(){
		textFieldOrderID = new JFormattedTextField();
		textFieldOrderID.setDocument(new DocIntFilter());
		textFieldOrderID.setColumns(10);
		addRowToOptions(lblOptionDescription, textFieldOrderID);
	}
	
	private void displayWarehouseID(){
		textFieldWarehouseID = new JFormattedTextField();
		textFieldWarehouseID.setDocument(new DocIntFilter());
		textFieldWarehouseID.setColumns(10);
		addRowToOptions(lblOptionDescription, textFieldWarehouseID);
	}
	
	private void displaySquareFtOrderOptions() {
		lblOptionDescription = new JLabel("Display the square footage of the order with ID");
		displayOrderID();
	}

	private void displayCubicFtOrderOptions() {
		lblOptionDescription = new JLabel("Display the cubic footage of the order with ID");
		displayOrderID();
	}

	private void displaySquareFtWHOptions() {
		lblOptionDescription = new JLabel("Display the cubic footage of the warehouse with ID");
		displayWarehouseID();
	}

	private void displayCubicFtWHOptions() {
		lblOptionDescription = new JLabel("Display the cubic footage of the warehouse with ID");
		displayWarehouseID();
	}

	private void clearCurrentOptions()
	{
		nextOptionRow = STARTING_OPTION_ROW;
		optionPanel.removeAll();
		optionPanel.revalidate();
		optionPanel.repaint();
	}


	private void displayBottomXPricedItemsOptions() {
		lblOptionDescription = new JLabel("Display the items with the bottom ");
		textFieldPriceItems = new JFormattedTextField();
		textFieldPriceItems.setDocument(new DocIntFilter());
		textFieldPriceItems.setColumns(3);
		lblDays = new JLabel("prices");
		addRowToOptions(lblOptionDescription, textFieldPriceItems, lblDays);
	}

	private void displayTopXPricedItemsOptions() {
		lblOptionDescription = new JLabel("Display the items with the top ");
		textFieldPriceItems = new JFormattedTextField();
		textFieldPriceItems.setDocument(new DocIntFilter());
		textFieldPriceItems.setColumns(3);
		lblDays = new JLabel("prices");
		addRowToOptions(lblOptionDescription, textFieldPriceItems, lblDays);
		
	}

	private void displayAgingItemsOptions() {
		lblOptionDescription = new JLabel("Display items older than:");
	    textFieldItemsOlderThan = new JFormattedTextField();
	    textFieldItemsOlderThan.setDocument(new DocIntFilter());
		textFieldItemsOlderThan.setColumns(3);
		lblDays = new JLabel("days");
		addRowToOptions(lblOptionDescription, textFieldItemsOlderThan, lblDays);
	}
	
	private boolean generateReport(String reportName)
	{
		try{
			ResultSet result = SQL_Handler.getAllItemsUnderRestock();
			int x;
			String warehouseID;
			String orderID;
			switch (reportName){
			case AGING_ITEMS_REPORTNAME: 
				String dayString = textFieldItemsOlderThan.getText();
				result = SQL_Handler.getAgingItems(getAgingItemDays());
				break;
			case PALLETS_WITH_SHORTAGES: 
				result =  SQL_Handler.getPalletsWithOverages();
				break;
			case ITEMS_WITH_OVERAGES:
				result = SQL_Handler.getOverageItems();
				break;
			case TOTAL_INV_WORTH: 
				result = SQL_Handler.getTotalInvWorth();
				break;
			case TOP_X_PRICED_ITEMS: 
				x = getTopX();
				result = SQL_Handler.getTopXPricedItems(x);
				break;
			case BOTTOM_X_PRICED_ITEMS: 
				x = getTopX();
				result = SQL_Handler.getBottomXPricedItems(x);
				break;
			case ITEMS_UNDER_RESTOCK: 
				result = SQL_Handler.getAllItemsUnderRestock();
				break;
			case PRICE_WEIGHT_RATIO: 
				result = SQL_Handler.getItemsWithPriceToWeight();
				break;
			case CUBIC_FT_WH:
				warehouseID = getWarehouseID();
				result = SQL_Handler.getTotalCubicFootageInWarehouse(warehouseID);
				break;
			case SQUARE_FT_WH:
				warehouseID = getWarehouseID();
				result = SQL_Handler.getTotalSquareFootageInWarehouse(warehouseID);
				break;
			case CUBIC_FT_ORDER:
				orderID = getOrderID();
				result = SQL_Handler.getTotalCubicFootageInOrder(orderID);
				break;
			case SQUARE_FT_ORDER:
				orderID = getOrderID();
				result = SQL_Handler.getTotalSquareFootageInOrder(orderID);
				break;	
			}
			return displayGenericReport(reportName, result);
		}catch(SQLException ex){
			JOptionPane.showMessageDialog(this, "An unexpected database error occured when " 
					+ "trying to display " + reportName + " data", reportName + " Query Failed", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
	}
	
	private String getOrderID() {
		return textFieldOrderID.getText();
	}

	private String getWarehouseID() {
		return textFieldWarehouseID.getText();
	}

	private int getTopX() {
		// TODO Auto-generated method stub
		String xString = textFieldPriceItems.getText();
		int x;
		if(xString.equals(""))
			x = 10;
		else
			x = Integer.parseInt(xString);
		return x;
	}

	private int getAgingItemDays() {
		String dayString = textFieldItemsOlderThan.getText();
		int days;
		if(dayString.equals(""))
			days = 30;
		else
			days = Integer.parseInt(dayString);
		return days;
	}

	private boolean displayGenericReport(String reportName, ResultSet result) throws SQLException{
			lblLoadingIcon.setText("Generating reportName report...");
			Object[][] resultDataArray = SQL_Handler.getResultSetAs2DObjArray(result);
			String[] columnNames = SQL_Handler.getColumnNamesFromResultSet(result);
			infoController.getMainWindow().updateTable(resultDataArray, columnNames);
			displaySuccessStatus("Displaying report for " + reportName);
			return true;
	}

	private boolean exportReport() {
		try {
			String timeStamp = new SimpleDateFormat("MM-dd-yy-hh;mm;ss").format(new java.util.Date());
			String type = "";
			if(this.generateButtonPressed)
				type = infoController.getTableEntity();
			else
				type = this.selectedReport;
			File writeFile = new File("./WIMS-" 
					+ type + "-(" + timeStamp + ")" + ".csv");
			ExcelExporter.saveTable(infoController.getMainTable(), writeFile);
			this.lastWrittenFile = writeFile.getCanonicalPath();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
			return false;
		}
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
	
	public void clearErrorStatus()
	{
		lblErrorStatus.setText(DEFAULT_ERROR_TEXT);
		lblErrorStatus.setForeground(buttonsPanel.getBackground());
	}
	

	@Override
	public void displayNeutralStatus(String neutralText) {
		displayStatusForTime(neutralText, NEUTRAL_DISPLAY_COLOR, ERROR_DISPLAY_TIME_MS);
	}
	
	@Override
	public void displayErrorStatus(String errorText) {
		displayStatusForTime(errorText, ERROR_DISPLAY_COLOR, ERROR_DISPLAY_TIME_MS);
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
}
