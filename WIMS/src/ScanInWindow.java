import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

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
	protected void createContents() {
		shell = new Shell();
		shell.setSize(350, 450);
		shell.setText("Scan Item In");
		
		//Create Labels from top to bottom
		Label lblReqInfo = new Label(shell, SWT.NONE);
		lblReqInfo.setBounds(10, 40, 140, 15);
		lblReqInfo.setText("Required Item Information");
		
		Label lblItemNumber = new Label(shell, SWT.NONE);
		lblItemNumber.setBounds(58, 65, 71, 15);
		lblItemNumber.setText("Item Number");
		
		Label lblItemName = new Label(shell, SWT.NONE);
		lblItemName.setBounds(70, 90, 59, 15);
		lblItemName.setText("Item Name");
		
		Label lblItemWeight = new Label(shell, SWT.NONE);
		lblItemWeight.setBounds(64, 118, 65, 15);
		lblItemWeight.setText("Item Weight");
		
		Label lblItemHazardType = new Label(shell, SWT.NONE);
		lblItemHazardType.setBounds(36, 149, 93, 15);
		lblItemHazardType.setText("Item Hazard Type");
		
		Label lblOptionalInfo = new Label(shell, SWT.NONE);
		lblOptionalInfo.setBounds(10, 218, 139, 15);
		lblOptionalInfo.setText("Optional Item Information");
		
		//Create TextBoxes from top to bottom
		Text txtItemNumber = new Text(shell, SWT.BORDER);
		txtItemNumber.setBounds(135, 61, 175, 21);
		
		Text txtItemName = new Text(shell, SWT.BORDER);
		txtItemName.setBounds(135, 87, 175, 21);
		
		Text txtItemWeight = new Text(shell, SWT.BORDER);
		txtItemWeight.setBounds(135, 115, 175, 21);
		
		Text txtItemRestock = new Text(shell, SWT.BORDER);
		txtItemRestock.setEnabled(false);
		txtItemRestock.setBounds(135, 239, 175, 21);
		
		Text txtCurrentStock = new Text(shell, SWT.BORDER);
		txtCurrentStock.setEnabled(false);
		txtCurrentStock.setBounds(135, 266, 175, 21);
		
		Text txtPrice = new Text(shell, SWT.BORDER);
		txtPrice.setEnabled(false);
		txtPrice.setBounds(135, 293, 175, 21);
		
		//Create Buttons from top to bottom
		Button btnRestockThreshold = new Button(shell, SWT.CHECK);	
		btnRestockThreshold.setBounds(10, 241, 118, 16);
		btnRestockThreshold.setText("Restock Threshold");
		
		Button btnCurrentStock = new Button(shell, SWT.CHECK);
		btnCurrentStock.setBounds(10, 268, 119, 16);
		btnCurrentStock.setText("Current Stock");
		
		Button btnPrice = new Button(shell, SWT.CHECK);
		btnPrice.setBounds(10, 295, 93, 16);
		btnPrice.setText("Price");
		
		Button btnSubmit = new Button(shell, SWT.NONE);
		btnSubmit.setBounds(235, 356, 75, 25);
		btnSubmit.setText("Submit");		
			
		//Create ComboBox that can only accept items from given selection
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
	
}//class end
