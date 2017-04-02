import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import java.sql.Connection;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;

public class ScanInWindow {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ScanInWindow window = new ScanInWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//main end

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}//open end

	/**
	 * Create contents of the window.
	 */
	public void createContents() {
		shell = new Shell();
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		shell.setSize(350, 450);
		shell.setText("Scan Item In");
		
		
		//Create Labels from top to bottom~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		Label lblReqInfo = new Label(shell, SWT.NONE);
		lblReqInfo.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		lblReqInfo.setBounds(10, 40, 140, 15);
		lblReqInfo.setText("Required Item Information");
		
		Label lblItemNumber = new Label(shell, SWT.NONE);
		lblItemNumber.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		lblItemNumber.setBounds(58, 65, 71, 15);
		lblItemNumber.setText("Item Number");
		
		Label lblItemName = new Label(shell, SWT.NONE);
		lblItemName.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		lblItemName.setBounds(70, 90, 59, 15);
		lblItemName.setText("Item Name");
		
		Label lblItemWeight = new Label(shell, SWT.NONE);
		lblItemWeight.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		lblItemWeight.setBounds(64, 118, 65, 15);
		lblItemWeight.setText("Item Weight");
		
		Label lblItemHazardType = new Label(shell, SWT.NONE);
		lblItemHazardType.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		lblItemHazardType.setBounds(36, 149, 93, 15);
		lblItemHazardType.setText("Item Hazard Type");
		
		Label lblOptionalInfo = new Label(shell, SWT.NONE);
		lblOptionalInfo.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		lblOptionalInfo.setBounds(10, 218, 139, 15);
		lblOptionalInfo.setText("Optional Item Information");
		
		//Create TextBoxes from top to bottom~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		Text txtItemNumber = new Text(shell, SWT.BORDER);
		txtItemNumber.setBounds(135, 61, 175, 21);
		txtItemNumber.addListener(SWT.Verify, new Listener()
				{
					public void handleEvent(Event e)
						{	
							e.doit = validOnlyNumber(e.text);	//checks if the input is only a digit				
						}//handle event end					
				});//add listener end
		
		Text txtItemName = new Text(shell, SWT.BORDER);
		txtItemName.setBounds(135, 87, 175, 21);
		
		Text txtItemWeight = new Text(shell, SWT.BORDER);
		txtItemWeight.setBounds(135, 115, 175, 21);
		txtItemWeight.addListener(SWT.Verify, new Listener()
		{
			public void handleEvent(Event e)
				{
					e.doit = validWithDecimal(e.text);	//checks if the input is either a digit or a decimal point						
				}//handle event end
		});//add listener end
		
		Text txtItemRestock = new Text(shell, SWT.BORDER);
		txtItemRestock.setEnabled(false);
		txtItemRestock.setBounds(135, 239, 175, 21);
		txtItemRestock.addListener(SWT.Verify, new Listener()
		{
			public void handleEvent(Event e)
				{	
					e.doit = validOnlyNumber(e.text);	//checks if the input is only a digit				
				}//handle event end					
		});//add listener end
		
		Text txtCurrentStock = new Text(shell, SWT.BORDER);
		txtCurrentStock.setEnabled(false);
		txtCurrentStock.setBounds(135, 266, 175, 21);
		txtCurrentStock.addListener(SWT.Verify, new Listener()
		{
			public void handleEvent(Event e)
				{	
					e.doit = validOnlyNumber(e.text);	//checks if the input is only a digit				
				}//handle event end					
		});//add listener end
		
		Text txtPrice = new Text(shell, SWT.BORDER);
		txtPrice.setEnabled(false);
		txtPrice.setBounds(135, 293, 175, 21);
		txtPrice.addListener(SWT.Verify, new Listener()
		{
			public void handleEvent(Event e)
				{	
					e.doit = validWithDecimal(e.text);	//checks if the input is only a digit or decimal point				
				}//handle event end					
		});//add listener end
		
		//Create Buttons from top to bottom~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		Button btnRestockThreshold = new Button(shell, SWT.CHECK);
		btnRestockThreshold.setBounds(10, 241, 118, 16);
		btnRestockThreshold.setText("Restock Threshold");
		btnRestockThreshold.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		btnRestockThreshold.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtItemRestock.setEnabled(btnRestockThreshold.getSelection()); //set txtItemRestock to Enabled if the checkbox is ticked
				if (btnRestockThreshold.getSelection() == false)
					txtItemRestock.setText("");
			}
		});		
		
		Button btnCurrentStock = new Button(shell, SWT.CHECK);
		btnCurrentStock.setBounds(10, 268, 119, 16);
		btnCurrentStock.setText("Current Stock");
		btnCurrentStock.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		btnCurrentStock.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtCurrentStock.setEnabled(btnCurrentStock.getSelection()); //set txtCurrentStock to Enabled if the checkbox is ticked
				if (btnCurrentStock.getSelection() == false)
					txtCurrentStock.setText("");
			}
		});
		
		
		Button btnPrice = new Button(shell, SWT.CHECK);
		btnPrice.setBounds(10, 295, 93, 16);
		btnPrice.setText("Price");
		btnPrice.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		btnPrice.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				txtPrice.setEnabled(btnPrice.getSelection()); //set txtItemRestock to Enabled if the checkbox is ticked
				if (btnPrice.getSelection() == false)
					txtPrice.setText("");
				
			}
		});
		
						//SUBMIT BUTTON!~~~~~~~~~~~~~~~~~~~~~~
		Button btnSubmit = new Button(shell, SWT.NONE);
		btnSubmit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int cStock = 0, rStock = 0, weight;  //default values for unchecked boxes
				double price = 0.00;
				if (stringIsDouble(txtItemWeight.getText()) && 
						stringIsNotEmpty(txtItemNumber.getText()) && stringIsNotEmpty(txtItemName.getText()) ) //if the first boxes inputs are valid
				{
					if (btnRestockThreshold.getSelection() && stringIsNotEmpty(txtItemRestock.getText()) )//if restock threshold button is checked and its not empty
					{
						rStock = Integer.parseInt(txtItemRestock.getText()); //set the reStock
					}
					if (btnCurrentStock.getSelection() && stringIsNotEmpty(txtCurrentStock.getText()) )//if current stock button is checked and its not empty
					{
						cStock = Integer.parseInt(txtCurrentStock.getText());  //set the currentStock
					}
					if (btnPrice.getSelection() && stringIsDouble(txtItemRestock.getText()) )//if price button is checked and it has a double
					{
						price = Double.parseDouble(txtPrice.getText());  //set the price
					}
					weight = Integer.parseInt(txtItemWeight.getText());  //need to change weight and price to doubles from int and string (respectively) in the SQL_Handler
					
					//check if item is already in database, if yes fill out the info, if no continue below
					
					try {
						Connection conn = SQL_Handler.getConnection();
						SQL_Handler.insertNewItem(txtItemNumber.getText(), txtItemName.getText(), txtPrice.getText(), weight, cStock, rStock);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}//trycatch end
				}//if 1st boxes valid end	
				
				
				
			}//widgetSelected end
		});//Listener end
		btnSubmit.setBounds(235, 356, 75, 25);
		btnSubmit.setText("Submit");		
			
		//Create ComboBox that can only accept items from given selection~~~~~~~~~~~~~~~~~~~~~~~~~~~
		final Combo HazardCombo = new Combo(shell, SWT.READ_ONLY);
		HazardCombo.setBounds(135, 146, 175, 23);
			//generically populate combobox, can add querey to populate the HazardOptions array later if preferred
		String[] HazardOptions = new String[]{"None", "Flammable", "Fragile", "Corrosive", "Radioactive", "Other"};
		for(int i = 0; i < HazardOptions.length; i++)
		{
			HazardCombo.add(HazardOptions[i]);
		}
		HazardCombo.select(0); //set default option to none	
		

	}//createContents end
	
	/**
	 * True if the string is not empty, false if the string is ""
	 * @param s
	 * @return boolean
	 */
	private boolean stringIsNotEmpty(String s)
	{
		if (!(s.equals("")))
		{
			return true;
		}
		else
		{
			System.out.println("Error of Empty String");
			return false;
		}
	}
	
	/**
	 * True if the string can be converted to a double
	 * @param s
	 * @return boolean
	 */
	private boolean stringIsDouble(String s)
	{
		try 
		{
			Double.parseDouble(s);
			return true;
		} catch (Exception ee)
		{
			System.out.println("Error in String to Double conversion");
			return false;
		}
	}
		
	/**
	 * returns False if the entry is anything other than 0-9
	 * @param myString
	 * @return boolean
	 */
	private boolean validOnlyNumber(String myString)
	{
		char[] chars = new char[myString.length()];
		myString.getChars(0,  chars.length, chars, 0);
		for (int i = 0; i < chars.length; i++)
		{
			if (!('0' <= chars[i] && chars[i] <= '9'))
			{
				return false;  //if the input is not a digit from 0-9 return false for invalid input
			}//if end
		}//for end
		return true; 
	}//validOnlyNumber end
	
	
	/**
	 * returns False if the entry is anything other than 0-9 or .
	 * need to add check so only one decimal can be used
	 * @param myString
	 * @return
	 */
	private boolean validWithDecimal(String myString)
	{
		char[] chars = new char[myString.length()];
		myString.getChars(0,  chars.length, chars, 0);		
		for (int i = 0; i < chars.length; i++)
		{
			if (!(('0' <= chars[i] && chars[i] <= '9') || chars[i] == '.') )
			{
				return false; //if the input is not a digit from 0-9 or a '.' return false for invalid input
			}//if end
		}//for end
		return true;
		
	}//validWithDecimal end

	
	
}//class end



