package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import controller.DBNamesManager;
import controller.ErrorStatusReportable;
import controller.MainWindowInfoController;

public class ColumnHeaderControllerPanel extends JPanel implements ErrorStatusReportable{
	
	private static final int SMALLER_COMPONENT_FONT_SIZE = 14;
	private static final Font CHECKBOX_FONT = new Font("Tahoma", Font.PLAIN, SMALLER_COMPONENT_FONT_SIZE);
	private static final Font CHECKBOX_STATUS_FONT = new Font("Tahoma", Font.PLAIN, SMALLER_COMPONENT_FONT_SIZE);
	private static final Font LABEL_FONT = new Font("Tahoma", Font.PLAIN, 18);
	
	static final int RIGHT_MARGIN = 100;
	
	
	private JPanel checkBoxesPanel;
	//Panel for "Show columns for:" label
	private JPanel showColumnsForLabelPanel;
	private JLabel lblCheckBoxesStatus;
	
	//HashMaps that map each field to a checkbox, used for the "show columns for" checkboxes
	//Maps are <Key, Value> = <FieldName, CheckBoxForFieldName>
	private HashMap<String, JCheckBox> palletFieldsCheckBoxesMap;
	private HashMap<String, JCheckBox> itemFieldsCheckBoxesMap;
	private HashMap<String, JCheckBox> orderFieldsCheckBoxesMap;
	private HashMap<String, JCheckBox> warehouseFieldsCheckBoxesMap;
	private HashMap<String, JCheckBox> sublocationFieldsCheckBoxesMap;
	private HashMap<String, JCheckBox> employeeFieldsCheckBoxesMap;

	//maps the header to a percentage of where it was in the table columns
	private HashMap<String, Float> headersToOldColPosMap;
	//a map where the column header is the key, and the column is the value
	private HashMap <String,TableColumn> headersToDeletedColumnMap; //TODO maybe add a show all columns button

	//the index of the next option row in the gridbaglayout. Used with addRowToOptions()
	//to manage the adding of field name checkboxes to that panel	
	private int nextOptionRow;
	//initial starting option row, this is the value the layout defaults to when it is cleared
	private static final int STARTING_OPTION_ROW = 1;
	private MainWindowInfoController infoController;
	
	public ColumnHeaderControllerPanel(MainWindowInfoController infoController)
	{
		super.setLayout(new BorderLayout());
		this.infoController = infoController;
		infoController.setColumnCheckBoxesPanel(this);
		initializeAllHashMaps();
		initializeLabelPanel();
		initializeCheckBoxesPanel();
	}

	private void initializeCheckBoxesPanel() {
		//Create an empty panel for spacing on the right of the showColumnsFor panel
		JPanel emptyPanel = new JPanel();
		Component rigidAreaRight = Box.createRigidArea(new Dimension(RIGHT_MARGIN, RIGHT_MARGIN));
		emptyPanel.add(rigidAreaRight);
		
		super.add(emptyPanel, BorderLayout.EAST);
		//super.setLayout(new BorderLayout());
		//Create the showColumnsForPanel
		checkBoxesPanel = new JPanel();
		//Give the panel a lowered bevel border
		Border borderLoweredBevel = BorderFactory.createLoweredBevelBorder();
		checkBoxesPanel.setBorder(borderLoweredBevel);
		//Set the panel to have a gridbaglayout
		GridBagLayout gbl_entityOptionsPanel = new GridBagLayout();
		gbl_entityOptionsPanel.columnWidths = new int[] {20, 100, 100, 100};
		gbl_entityOptionsPanel.rowHeights = new int[] {0, 30, 30, 30, 30, 0};
		gbl_entityOptionsPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_entityOptionsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};
		checkBoxesPanel.setLayout(gbl_entityOptionsPanel);
	
		super.add(checkBoxesPanel, BorderLayout.CENTER);
		
		if(lblCheckBoxesStatus == null)
			initializeCheckBoxStatusLabel();
		
		//Set the initial state of the panel to update based on the initial state of the entity combobox
		displayFieldCheckBoxesForEntity(infoController.DEFAULT_ENTITY_NAME);
	}

	/**
	 * Initialize the hashmaps for checkboxes and field data types
	 */
	private void initializeAllHashMaps()
	{
		headersToDeletedColumnMap = new HashMap<String, TableColumn>();
		headersToOldColPosMap = new HashMap<String, Float>();
		palletFieldsCheckBoxesMap = getCheckBoxHashMap(DBNamesManager.getAllPalletFieldDisplayNames());
		itemFieldsCheckBoxesMap = getCheckBoxHashMap(DBNamesManager.getAllItemFieldDisplayNames());
		orderFieldsCheckBoxesMap = getCheckBoxHashMap(DBNamesManager.getAllOrderFieldDisplayNames());
		warehouseFieldsCheckBoxesMap = getCheckBoxHashMap(DBNamesManager.getAllWarehouseFieldDisplayNames());
		sublocationFieldsCheckBoxesMap = getCheckBoxHashMap(DBNamesManager.getAllSublocationFieldDisplayNames());
		employeeFieldsCheckBoxesMap = getCheckBoxHashMap(DBNamesManager.getAllEmployeeFieldDisplayNames());
	}
	
	/**
	 * Initialize the panel that contains the "Show columns for:" header label
	 */
	private void initializeLabelPanel()
	{
		//Initialize the panel for the "show columns for:" label
		showColumnsForLabelPanel = new JPanel();
		JLabel showColumnHeaders = new JLabel("Show columns for:");
		showColumnHeaders.setFont(LABEL_FONT);
		showColumnsForLabelPanel.add(showColumnHeaders);
		super.add(showColumnsForLabelPanel, BorderLayout.WEST);
	}
	
	/**
	 * Generic method for getting a HashMap of checkboxes for a given
	 * array of fields
	 * @param fields the fields to create checkboxes for
	 * @return a HashMap, where 
	 * 			key:	a field from the fields[] parameter, and
	 * 			value:	a checkbox for the field denoted by the key
	 */
	public HashMap<String, JCheckBox> getCheckBoxHashMap(String[] fields) {
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
					TableColumnModel tcm = infoController.getMainTable().getColumnModel();
					TableColumn correspondingColumn;
					int correspondingColNdx;
					//if the box is being unchecked
					if(!selected)
					{
						//the column that corresponds to the selected field 
						correspondingColumn = infoController.getMainTable().getColumn(nextField);
						//model index of the column to remove
						int oldColModelNdx = tcm.getColumnIndex(nextField);
						//view index of the column to remove
						int oldColViewNdx = infoController.getMainTable().convertColumnIndexToView(oldColModelNdx);
						//percentage of how far the index is across the view from the left
						float oldColNdxPercent = ((float) oldColViewNdx)/infoController.getMainTable().getColumnCount();
						
						System.out.println("old percent distance from left: " + oldColNdxPercent + "%");
						
						//put the position into the map by its field name
						headersToOldColPosMap.put(nextField,oldColNdxPercent);
						//remove the column from the view
						infoController.getMainTable().removeColumn(correspondingColumn);
						headersToDeletedColumnMap.put(nextField, correspondingColumn);
					}else{ //else the box is being checked
						//get the corresponding column from the hidden columns map
						correspondingColumn = headersToDeletedColumnMap.get(nextField);
						//get the percentage of how far from the left it was before
						float correspondingNdxPercent = headersToOldColPosMap.get(nextField);
						//find the current column index that is that far from the left
						correspondingColNdx = (int) (correspondingNdxPercent*(infoController.getMainTable().getColumnCount()-1));
						
						System.out.println("col count: " + (infoController.getMainTable().getColumnCount()-1));
						System.out.println("percent distance from left: " + correspondingNdxPercent + "%");
						System.out.println("calculated col ndx: " + correspondingColNdx);
						if(correspondingNdxPercent > 0.0 && correspondingColNdx == 0)
							correspondingColNdx = 1;
						//add the column back to the table
						infoController.getMainTable().addColumn(correspondingColumn);
						//move it to roughly the same position it was before it was hidden
						try{
							infoController.getMainTable().moveColumn((infoController.getMainTable().getColumnCount()-1), correspondingColNdx);
						}catch(IllegalArgumentException ex)
						{
							infoController.getMainTable().moveColumn((infoController.getMainTable().getColumnCount()-1), 1);
						}
						//remove from the list of hidden columns
						headersToDeletedColumnMap.remove(nextField);
						//tcm.addColumn(correspondingColumn);
					}
				}
				//TODO add toggle hide/show all columns
				//TODO fix bug of when a query for an entity type fails, the checkboxes should reset
			});
			map.put(nextField, nextBox);
		}
		return map;
	}
	
	public void setAreCheckBoxesAreEnabled(String entityName, boolean areActive)
	{
		HashMap<String, JCheckBox> checkBoxes = getCheckBoxMapForEntity(entityName);
		Iterator<String> checkBoxItty = checkBoxes.keySet().iterator();
		while(checkBoxItty.hasNext())
		{
			checkBoxes.get(checkBoxItty.next()).setEnabled(areActive);
		}
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
	public void displayFieldCheckBoxesForEntity(String entityName)
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
		case DBNamesManager.WAREHOUSE_ENTITY_DISPLAYNAME:
			displayFieldCheckBoxesOptions(warehouseFieldsCheckBoxesMap);
			break;
		case DBNamesManager.SUBLOCATION_ENTITY_DISPLAYNAME:
			displayFieldCheckBoxesOptions(sublocationFieldsCheckBoxesMap);
			break;
		case DBNamesManager.EMPLOYEE_ENTITY_DISPLAYNAME:
			displayFieldCheckBoxesOptions(employeeFieldsCheckBoxesMap);
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
		case DBNamesManager.WAREHOUSE_ENTITY_DISPLAYNAME:
			return warehouseFieldsCheckBoxesMap;
		case DBNamesManager.SUBLOCATION_ENTITY_DISPLAYNAME:
			return sublocationFieldsCheckBoxesMap;
		case DBNamesManager.EMPLOYEE_ENTITY_DISPLAYNAME:
			return employeeFieldsCheckBoxesMap;
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
		checkBoxesPanel.removeAll();
		checkBoxesPanel.revalidate();
		checkBoxesPanel.repaint();
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
		checkBoxesPanel.add(rigidAreaLeft, gbc_rigidAreaLeft);
		
		//if the first column is null, add nothing
		if(firstColumn != null)
		{
			GridBagConstraints gbc_firstColumn = new GridBagConstraints();
			gbc_firstColumn.anchor = GridBagConstraints.WEST;
			gbc_firstColumn.insets = new Insets(0, 0, 5, 5);
			gbc_firstColumn.gridx = 1; //first column
			gbc_firstColumn.gridy = nextOptionRow; //add on the nextOptionRow. this keeps track of the next row
			checkBoxesPanel.add(firstColumn, gbc_firstColumn);
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
			checkBoxesPanel.add(secondColumn, gbc_secondColumn);
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
			checkBoxesPanel.add(thirdColumn, gbc_thirdColumn);
		}
		
		//add the first two columns to the row
		addRowToFieldCheckboxes(firstColumn, secondColumn);
	}

	@Override
	public void displayErrorStatus(String errorText) {
		lblCheckBoxesStatus.setText(errorText);
		lblCheckBoxesStatus.setForeground(Color.RED);
	}

	@Override
	public void clearErrorStatus() {
		lblCheckBoxesStatus.setText("");
	}

	@Override
	public void displayNeutralStatus(String neutralText) {
		lblCheckBoxesStatus.setText(neutralText);
		lblCheckBoxesStatus.setForeground(Color.BLACK);
	}

	@Override
	public void displaySuccessStatus(String successText) {
		lblCheckBoxesStatus.setText(successText);
		lblCheckBoxesStatus.setForeground(Color.RED);
	}
	
	public TableColumn getDeletedColumn(String header)
	{
		return this.headersToDeletedColumnMap.get(header);
	}
}
