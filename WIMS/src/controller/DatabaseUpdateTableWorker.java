package controller;

import gui.ErrorStatusReportable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingWorker;

public class DatabaseUpdateTableWorker extends SwingWorker<Integer, String> {

	private ErrorStatusReportable window;
	private WIMSTable table;
	private JLabel statusLabel;
	private Object[][] data;
	private String[] columnNames;
	private String entityName;
	private String fieldName;
	private String fieldModifier;
	private String fieldModifierValue;
	
	public DatabaseUpdateTableWorker(ErrorStatusReportable window, WIMSTable table, JLabel statusLabel, 
			Object[][] data, String[] columnNames, String entityName, String fieldName, String fieldModifier, String fieldModifierValue) {
		this.window = window;
		this.table = table;
		this.data = data;
		this.columnNames = columnNames;
		this.statusLabel = statusLabel;
		this.entityName = entityName;
		this.fieldName = fieldName;
		this.fieldModifier = fieldModifier;
		this.fieldModifierValue = fieldModifierValue;
	}
	
	//TODO add constructor that doesnt have to take field modifier values for use in reportswindow
	
	@Override
	protected Integer doInBackground() throws Exception {
		// Start
		//(String entityName, String fieldName, String fieldModifier, String fieldModifierValue)
			//TODO finish this functionality to interact with the database
			if (entityName.equals(DBNamesManager.getAllEntitySpecifierDisplayname())) 
			{
			} else {
			String dbEntityName = DBNamesManager.getEntityDatabaseVariableByDisplayName(entityName);
			String query = "SELECT * FROM " + dbEntityName;
			//if the user has entered a modifier value
			boolean modifierValueEntered = (fieldModifierValue == null) 
										|| (!fieldModifierValue.equals(DBNamesManager.getDefaultFieldModifierValue()));
			if (modifierValueEntered) 
			{
				String dbFieldName = DBNamesManager.getFieldDatabaseVariableFieldByDisplayName(fieldName);
				String modifierString = getQueryModifierString(fieldModifier,
						fieldModifierValue);
				query = query + " WHERE " + dbFieldName + modifierString;
			}
			try {
			ResultSet result = controller.SQL_Handler.executeCustomQuery(query);
			if(result.next())
			{
				Object[][] data = controller.SQL_Handler.getResultSetAs2DObjArray(result);			
				String[] columnNames = controller.SQL_Handler.getColumnNamesFromResultSet(result);
	
				WIMSTableModel tabelModel = new WIMSTableModel(data, columnNames);
				table.setModel(tabelModel);
			}else{
				String error = "There are no results for " + entityName;
				if(modifierValueEntered)
					error = error + " with " + fieldName + " " + fieldModifier + " " + fieldModifierValue;
				error += ".";
				window.displayErrorStatus(error);
			}
			table.updateColumnWidths();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//TODO make it update the table neatly, probably in another method
		//TODO THISIS BAD
	
		
		
		publish("Querying database...");
		setProgress(1);

		// More work was done
		publish("Loading table...");
		setProgress(10);

		// Complete
		publish("Complete");
		setProgress(100);
		return 1;
	}

	@Override
	protected void process(List<String> chunks) {
		// Messages received from the doInBackground() (when invoking the
		// publish() method)
		for (String status : chunks) {
			window.displayErrorStatus(status);
         }
	}
	
	private String getQueryModifierString(String fieldModifier, String fieldModifierValue) {
		String modifierString = "";
		switch (fieldModifier){
		case DBNamesManager.NUMERIC_FIELD_LESS_THAN: 
			modifierString = " < " + fieldModifierValue;
			break;
		case DBNamesManager.NUMERIC_FIELD_GREATER_THAN: 
			modifierString = " > " + fieldModifierValue;
			break;
		case DBNamesManager.NUMERIC_FIELD_EQUAL_TO:
			modifierString = " = " + fieldModifierValue;
			break;
		case DBNamesManager.STRING_FIELD_STARTING_WITH:
			modifierString = " LIKE " + fieldModifierValue + "%";
			break;
		case DBNamesManager.STRING_FIELD_ENDING_WITH:
			modifierString = " LIKE " + "%" + fieldModifierValue;
			break;
		case DBNamesManager.STRING_FIELD_CONTAINS:
			modifierString = " LIKE " + "%" + fieldModifierValue + "%";
			break;
		case DBNamesManager.STRING_FIELD_THAT_IS:
			modifierString = " = " + fieldModifierValue;
			break;
		case DBNamesManager.DATE_FIELD_BEFORE:
			modifierString = " < " + "\'" + fieldModifierValue + "\')";
			break;
		case DBNamesManager.DATE_FIELD_AFTER:
			modifierString = " > " + "\'" + fieldModifierValue + "\')";
			break;
		case DBNamesManager.DATE_FIELD_ON:
			modifierString = " = " + "\'" + fieldModifierValue + "\')";
			break;
		}
		return modifierString;
	}
}