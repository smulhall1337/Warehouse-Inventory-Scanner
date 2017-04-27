package gui;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePickerImpl;

import controller.ComponentProvider;
import controller.DBNamesManager;
import controller.MainWindowInfoController;
import controller.NumericDataDocument;

public class EntityAndFieldPanel extends JPanel {

	private static final int ENTITYFIELD_SELECTION_FONT_SIZE = 18;
	private static final Font LABEL_FONT = new Font("Tahoma", Font.PLAIN, ENTITYFIELD_SELECTION_FONT_SIZE);
	private static final Font COMBOBOX_FONT = new Font("Tahoma", Font.PLAIN, ENTITYFIELD_SELECTION_FONT_SIZE);
	private static final Font FIELD_MODIFIER_COMPONENT_FONT = new Font("Tahoma", Font.PLAIN, ENTITYFIELD_SELECTION_FONT_SIZE);

	//number of columns for field textboxes
	private static final int FIELD_OPTION_TEXTBOX_COLUMNS = 10;
	
	private static final String NUMERIC_FIELD_ENTRY_REGEXSTRING = "([+-]?\\d*\\.?\\d*)";
	private static final DecimalFormat NUMERIC_FIELD_ENTRY_FORMAT = new DecimalFormat(NUMERIC_FIELD_ENTRY_REGEXSTRING);
	private static final Boolean[] isManagementModifierValues = {true, false};
	
	//Combobox to select an entity type to view
	private JComboBox comboBoxEntityType;
	//Combobox to select a field of the currently selected entity
	private JComboBox comboBoxField;
	//Combobox to select "less than", "starting with", etc.
	private JComboBox comboBoxFieldModifier;
	//Labels that go along with comboboxes
	private JLabel lblDisplayInfoFor;
	private JLabel lblFieldWith; 
	
	
	private String currentTableEntity;
	
	//DatePicker to select date when a Date type field is selected
	private JDatePickerImpl dateFieldDatePicker;
	//JTextField to enter a search string when a String type field is selected
	private JTextField stringFieldTextField;
	//JFormattedTextField to enter a number when a numeric type field is selected
	private JFormattedTextField numericFieldTextField;
	
	private MainWindowInfoController infoController;
	private JComboBox flagFieldTrueFalseCombo;
	
	public EntityAndFieldPanel(MainWindowInfoController infoController)
	{
		super();
		this.infoController = infoController;
		infoController.setEntityAndFieldPanel(this);
		FlowLayout fl_entitySelectPanel = (FlowLayout) super.getLayout();
		fl_entitySelectPanel.setVgap(10);
		fl_entitySelectPanel.setAlignment(FlowLayout.LEFT);
		
		
		lblDisplayInfoFor = new JLabel("Display info for");
		lblDisplayInfoFor.setFont(LABEL_FONT);
		super.add(lblDisplayInfoFor);
		
		initializeEntityComboBox();
		
		lblFieldWith = new JLabel("with");
		lblFieldWith.setFont(LABEL_FONT);
		super.add(lblFieldWith);
		
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
				
					//display checkboxes for the newly selected entity's fields
					//and update the fields combobox to display these fields
					setFieldOptionVisibility(true);
					infoController.getColumnCheckBoxesPanel().displayFieldCheckBoxesForEntity(currentEntity);
					updateFieldsComboBox(currentEntity);
					if(currentEntity.equals(currentTableEntity))
					{
						infoController.getColumnCheckBoxesPanel().setAreCheckBoxesAreEnabled(currentEntity, true); //TODO double check this
						infoController.getColumnCheckBoxesPanel().clearErrorStatus();
					}else{
						infoController.getColumnCheckBoxesPanel().setAreCheckBoxesAreEnabled(currentEntity, false);
						infoController.getColumnCheckBoxesPanel().displayErrorStatus("Make a query first to show/hide columns");
					}
				
			}
		});
		comboBoxEntityType.setFont(COMBOBOX_FONT);
		//display the entities from the entity array
		comboBoxEntityType.setModel(new DefaultComboBoxModel(DBNamesManager.getEntityDisplayNames()));
//		if(lblCheckBoxesStatus == null)
//			initializeCheckBoxStatusLabel();
		infoController.getColumnCheckBoxesPanel().setAreCheckBoxesAreEnabled(this.getSelectedEntity(), false);
		infoController.getColumnCheckBoxesPanel().displayErrorStatus("Make a query first to show/hide columns"); //TODO add these to be constants
		super.add(comboBoxEntityType);
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
		super.add(comboBoxField);
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
		case DBNamesManager.WAREHOUSE_ENTITY_DISPLAYNAME:
			comboBoxField.setModel(new DefaultComboBoxModel(DBNamesManager.getAllWarehouseFieldDisplayNames()));
			break;
		case DBNamesManager.SUBLOCATION_ENTITY_DISPLAYNAME:
			comboBoxField.setModel(new DefaultComboBoxModel(DBNamesManager.getAllSublocationFieldDisplayNames()));
			break;
		case DBNamesManager.EMPLOYEE_ENTITY_DISPLAYNAME:
			comboBoxField.setModel(new DefaultComboBoxModel(DBNamesManager.getAllEmployeeFieldDisplayNames()));
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
		super.add(comboBoxFieldModifier);

		String currentField = (String) comboBoxField.getSelectedItem();
		
		initializeFieldModifierComponents();
		updateFieldModifierComponent(currentField);
		updateFieldModifierComboBox(currentField);
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
		infoController.getColumnCheckBoxesPanel().setVisible(visibility);
		//showColumnsForPanel.setVisible(visibility);
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
	 * Update the field modifiers combobox based on the given field
	 * @param fieldDisplayName the field to display modifiers for
	 */
	public void updateFieldModifierComboBox(String fieldDisplayName) {
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
		case DBNamesManager.FLAG_FIELD_TYPE_NAME:
			comboBoxFieldModifier.setModel(new DefaultComboBoxModel(DBNamesManager.getFlagFieldModifierStrings()));
			break;
		}
	}
	
	/**
	 * Update the user field modifier component based on the given field. Date types
	 * will display a date picker component, numeric types will have a number
	 * entry component, etc.
	 * @param fieldDisplayName the field to use to determine how to update the field modifier component
	 */
	public void updateFieldModifierComponent(String fieldDisplayName) {
		//clear the current field modifier component
		clearFieldModifierComponent();
		//get the data type of the field to update the component based on
		String fieldType = DBNamesManager.getFieldDataTypeByDisplayName(fieldDisplayName);
		System.out.println(fieldDisplayName + " IS TYPE " + fieldType);
		//Update the component based on the data type of the field
		switch (fieldType){
		case DBNamesManager.NUMERIC_FIELD_TYPE_NAME: 
			//if numeric, display a number entry text field
			//numericFieldTextField = new JFormattedTextField(NUMERIC_FIELD_ENTRY_FORMAT);;
			numericFieldTextField.setDocument(new NumericDataDocument());
			numericFieldTextField.setColumns(FIELD_OPTION_TEXTBOX_COLUMNS);
			numericFieldTextField.setFont(FIELD_MODIFIER_COMPONENT_FONT);
			super.add(numericFieldTextField);
			break;
		case DBNamesManager.STRING_FIELD_TYPE_NAME: 
			//if string, display a text field for the user's search string
			stringFieldTextField = new JTextField();
			stringFieldTextField.setColumns(FIELD_OPTION_TEXTBOX_COLUMNS);
			stringFieldTextField.setFont(FIELD_MODIFIER_COMPONENT_FONT);
			super.add(stringFieldTextField);
			break;
		case DBNamesManager.DATE_FIELD_TYPE_NAME:
			//if date, display a date picker
			dateFieldDatePicker = ComponentProvider.getDatePicker();
			dateFieldDatePicker.setFont(FIELD_MODIFIER_COMPONENT_FONT);
			super.add(dateFieldDatePicker);
			break;
		case DBNamesManager.FLAG_FIELD_TYPE_NAME:
			//if date, display a date picker
			flagFieldTrueFalseCombo = new JComboBox(isManagementModifierValues);
			flagFieldTrueFalseCombo.setFont(FIELD_MODIFIER_COMPONENT_FONT);
			super.add(flagFieldTrueFalseCombo);
			break;
		}
	}
	
	/**
	 * Initialize the components for field modifiers
	 */
	private void initializeFieldModifierComponents() {
		numericFieldTextField = new JFormattedTextField();
		stringFieldTextField = new JTextField();
		flagFieldTrueFalseCombo = new JComboBox();
		dateFieldDatePicker = ComponentProvider.getDatePicker();
		
		numericFieldTextField.setFont(FIELD_MODIFIER_COMPONENT_FONT);
		numericFieldTextField.setDocument(new NumericDataDocument());
		numericFieldTextField.setColumns(FIELD_OPTION_TEXTBOX_COLUMNS);
		numericFieldTextField.setFont(FIELD_MODIFIER_COMPONENT_FONT);
		
		stringFieldTextField.setFont(FIELD_MODIFIER_COMPONENT_FONT);
		
		dateFieldDatePicker.setFont(FIELD_MODIFIER_COMPONENT_FONT);
		
		flagFieldTrueFalseCombo.setFont(FIELD_MODIFIER_COMPONENT_FONT);
	}
	
	/**
	 * Clear the current field modifier component
	 */
	public void clearFieldModifierComponent() {
		if(numericFieldTextField.getParent() != null)
			super.remove(numericFieldTextField);
		if(stringFieldTextField.getParent() != null)
			super.remove(stringFieldTextField);
		if(flagFieldTrueFalseCombo.getParent() != null)
			super.remove(flagFieldTrueFalseCombo);
		if(dateFieldDatePicker.getParent() != null)
			super.remove(dateFieldDatePicker);
	}
	
	public String getSelectedField()
	{
		return (String) comboBoxField.getSelectedItem();
	}
	
	public String getSelectedEntity()
	{
		return (String) comboBoxEntityType.getSelectedItem();

	}
	
	public String getSelectedModifier()
	{
		return (String) comboBoxFieldModifier.getSelectedItem();

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
	public String getFieldModifierValue(String currentField) {
		String fieldType = DBNamesManager.getFieldDataTypeByDisplayName(currentField);
		switch (fieldType){
		case DBNamesManager.NUMERIC_FIELD_TYPE_NAME: 
			System.out.println("value from numeric text field");
			return numericFieldTextField.getText();
		case DBNamesManager.STRING_FIELD_TYPE_NAME: 
			return stringFieldTextField.getText();
		case DBNamesManager.DATE_FIELD_TYPE_NAME:
			return dateFieldDatePicker.getJFormattedTextField().getText();
		case DBNamesManager.FLAG_FIELD_TYPE_NAME:
			return ((Boolean) flagFieldTrueFalseCombo.getSelectedItem()).toString();
		}
		return null; //is not supposed to happen, if this happens there is an error
	}
	
	public String getFieldModifierValue() {
		
		return this.getFieldModifierValue(this.getSelectedField());
	}
	
	public boolean isModifierValueEntered()
	{
		//		the field isnt null		and the field isnt set to its default value
		String fieldModifierValue = this.getFieldModifierValue();
		System.out.println("Value entered=" + fieldModifierValue);
		return (fieldModifierValue != null) && (!fieldModifierValue.equals(DBNamesManager.getDefaultFieldModifierValue()));
	}
}
