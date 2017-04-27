package controller;

import java.util.Properties;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public abstract class ComponentProvider {

	private static final int EMPLOYEE_NAME_TEXTFIELD_COLUMNS = 25;
	private static final int ID_TEXTFIELD_COLUMNS = 25;
	
	/**
	 * Get a date picker component
	 * @return a date picker component
	 */
	public static JDatePickerImpl getDatePicker()
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
    	datePicker.setTextEditable(true);
    	return datePicker;
	}
	
	public static void showDBConnectionError(JFrame frame)
	{
		JOptionPane.showMessageDialog(frame, 
				"Error connecting to database. Check your connection and the database status.", 
				"Database Error", JOptionPane.ERROR_MESSAGE);
	}
	
	public static JFormattedTextField getNameTextField() {
		NameDocument employeeNameDoc = new NameDocument();
		JFormattedTextField formattedTextFieldEmployeeName = new JFormattedTextField();
		formattedTextFieldEmployeeName.setDocument(employeeNameDoc);
		formattedTextFieldEmployeeName.setColumns(EMPLOYEE_NAME_TEXTFIELD_COLUMNS);
		return formattedTextFieldEmployeeName;
	}
	
	public static JFormattedTextField getIDTextField()
	{
		JFormattedTextField formattedTextFieldID = new JFormattedTextField();
		IDDocument idDoc = new IDDocument();
		formattedTextFieldID.setDocument(idDoc);
		formattedTextFieldID.setColumns(ID_TEXTFIELD_COLUMNS);
		return formattedTextFieldID;
	}
}
