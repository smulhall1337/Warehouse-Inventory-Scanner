import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;

/**
 * This window handles entering and editing single items
 * @author Brian Krick
 *
 */
public class ScanWindow extends JFrame {
	private int itemWeight = 1, itemStock = 0, itemRestock = 0, itemAdd = 0;
	private String itemNumber, itemName, itemPrice = "0.00", input = ""; 	
	private boolean found, isM;
	public ArrayList<JCheckBox> itemTypeList = new ArrayList<JCheckBox>();
	private ArrayList<String> selectedTypes = new ArrayList<String>();
	private Vector v;
	
	private JFrame frame;
	private JLabel lblRequiredInformation, lblItemNumber, lblItemName, lblItemWeight, lblItemTypeselect, lblAdditionalItemInformation, lblFirstSeen;
	private JLabel lblPrice, lblCurrentStock, lblRestock;
	private JTextField txtItemNumber, txtItemName, txtItemWeight, txtPrice, txtCurrentStock, txtRestock, txtAdd;
	private JCheckBox chckbxItemPrice, chckbxCurrentStock, chckbxRestock, chckbxAdd;
	private JCheckBox chckbxFragile, chckbxFlammable, chckbxCorrosive, chckbxRadioActive, chckbxGlass, chckbxFurniture, chckbxOther;
	private JComboBox cbItemTypes;
	private JButton btnExit, btnSearch, btnSearchAgain, btnSubmit;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScanWindow window = new ScanWindow(true);  //shouldnt leave default to true
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}//main end

	public ScanWindow(boolean isManagement, String initialItemNumber) {
		this.itemNumber = initialItemNumber;
		this.isM = isManagement;
		initialize();
		btnSearch.doClick();
	}
	
	/**
	 * Create the application.
	 */
	public ScanWindow(boolean isManagement) {
		this.isM = isManagement;
		//this.isM = true;
		initialize();
	}//ScanWindow end

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 550);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setTitle("Please Scan or Enter Item Number");
		setLabels();
		setTextFieldsInfo();
		setInfoCheckBoxes();
		setItemTypeCheckBoxes();
		getItemTypes();
		setButtons();
		searchScreen();	
	}//initialize end
	
	//#############################################Validation

	//#############################################Validation end

	//#############################################Display Methods
	public void updateTextBoxes()
	{
		txtItemNumber.setText(itemNumber);
		txtItemName.setText(itemName);
		txtItemWeight.setText(Integer.toString(itemWeight));
		txtPrice.setText(itemPrice);
		txtCurrentStock.setText(Integer.toString(itemStock));
		txtRestock.setText(Integer.toString(itemRestock));
		if (found)
			txtAdd.setText("0");
	}
	private void updateVariables() {
		if (chckbxRestock.isSelected()) {
			itemRestock = Integer.parseInt(txtRestock.getText()); //set the reStock
		}
		else {
			itemRestock = 0;
		}
		if (chckbxCurrentStock.isSelected()) {
			itemStock = Integer.parseInt(txtCurrentStock.getText());  //set the currentStock
		}
		else {
			itemStock = 0;
		}
		if (chckbxItemPrice.isSelected()) {
			itemPrice = txtPrice.getText();  //set the price
		}
		else {
			itemPrice = "0.00";
		}
		itemNumber = txtItemNumber.getText();
		itemName = txtItemName.getText();
		itemWeight = Integer.parseInt(txtItemWeight.getText()); 
	}
	//##################Frame Creation Methods
	/**
	 * This method creates and sets lblFirstSeen, lblItemNumber, txtItemNumber, btnExit, and btnSearch onto the frame so the user can search the database for the item they want
	 */
	private void searchScreen() {
		//SEE
		lblItemNumber.setVisible(true);
		txtItemNumber.setVisible(true);
		txtItemNumber.setEditable(true);
		txtItemNumber.requestFocus();
		btnExit.setVisible(true);
		btnSearch.setVisible(true);
		frame.getRootPane().setDefaultButton(btnSearch);
		lblFirstSeen.setVisible(true);
		//HIDE
		lblRequiredInformation.setVisible(false); 		 
		lblItemName.setVisible(false); 
		lblItemWeight.setVisible(false); 
		lblItemTypeselect.setVisible(false); 
		lblAdditionalItemInformation.setVisible(false);		
		lblPrice.setVisible(false);
		lblCurrentStock.setVisible(false);
		lblRestock.setVisible(false);
		txtItemName.setVisible(false); 
		txtItemName.setEditable(false);
		txtItemWeight.setVisible(false); 
		txtItemWeight.setEditable(false);
		txtPrice.setVisible(false);
		txtPrice.setEditable(false);
		txtCurrentStock.setVisible(false); 
		txtCurrentStock.setEditable(false);
		txtRestock.setVisible(false); 
		txtRestock.setEditable(false);
		txtAdd.setVisible(false);
		txtAdd.setEditable(false);
		chckbxItemPrice.setVisible(false); 
		chckbxCurrentStock.setVisible(false); 
		chckbxRestock.setVisible(false); 
		chckbxAdd.setVisible(false);
		cbItemTypes.setVisible(false);		
		btnSubmit.setVisible(false);
		btnSearchAgain.setVisible(false);
	}//searchScreen end	
	
	private void foundManagerScreen() {
		//SEE
		lblRequiredInformation.setVisible(true); 
		lblItemNumber.setVisible(true); 
		lblItemName.setVisible(true); 
		lblItemWeight.setVisible(true); 
		lblItemTypeselect.setVisible(true); 
		lblAdditionalItemInformation.setVisible(true);
		txtItemNumber.setVisible(true);
		txtItemName.setVisible(true);
		txtItemWeight.setVisible(true);
		txtPrice.setVisible(true);
		txtCurrentStock.setVisible(true);
		txtRestock.setVisible(true);
		txtAdd.setVisible(true);
		txtAdd.requestFocus();
		chckbxItemPrice.setVisible(true); 
		chckbxCurrentStock.setVisible(true); 
		chckbxRestock.setVisible(true); 
		chckbxAdd.setVisible(true);
		cbItemTypes.setVisible(true);
		btnExit.setVisible(true);		
		btnSubmit.setVisible(true);
		btnSearchAgain.setVisible(true);
		frame.getRootPane().setDefaultButton(btnSubmit);
		
		//HIDE
		lblFirstSeen.setVisible(false);
		lblPrice.setVisible(false);
		lblCurrentStock.setVisible(false);
		lblRestock.setVisible(false);
		btnSearch .setVisible(false);
		//EDITABLE
		txtItemName.setEditable(true);		 
		txtItemWeight.setEditable(true);		
		txtAdd.setEditable(true);
		
		//NOT EDITABLE		
		txtItemNumber.setEditable(false);
		txtPrice.setEditable(false);		 
		txtCurrentStock.setEditable(false);		 
		txtRestock.setEditable(false);
		for (JCheckBox temp : itemTypeList) {
			temp.setEnabled(false);
		}
		
		/**
		chckbxFragile.setEnabled(false);
		chckbxFlammable.setEnabled(false); 
		chckbxCorrosive.setEnabled(false); 
		chckbxRadioActive.setEnabled(false); 		//why can the checkboxes be modified???????????
		chckbxGlass.setEnabled(false); 
		chckbxFurniture.setEnabled(false); 
		chckbxOther.setEnabled(false); */
		
		updateTextBoxes();
	}//foundManagerScreen end
	
	private void foundEmployeeScreen() {
		//SEE
		lblRequiredInformation.setVisible(true); 
		lblItemNumber.setVisible(true); 
		lblItemName.setVisible(true); 
		lblItemWeight.setVisible(true); 
		lblItemTypeselect.setVisible(true); 
		lblAdditionalItemInformation.setVisible(true);
		txtItemNumber.setVisible(true);
		txtItemName.setVisible(true);
		txtItemWeight.setVisible(true);
		txtPrice.setVisible(true);
		txtCurrentStock.setVisible(true);
		txtRestock.setVisible(true);
		txtAdd.setVisible(true);
		txtAdd.requestFocus();
		lblPrice.setVisible(true);
		lblCurrentStock.setVisible(true);
		lblRestock.setVisible(true);
		chckbxAdd.setVisible(true);
		chckbxAdd.setSelected(true);
		cbItemTypes.setVisible(true);
		btnExit.setVisible(true);		
		btnSubmit.setVisible(true);
		btnSearchAgain.setVisible(true);
		frame.getRootPane().setDefaultButton(btnSubmit);
		
		//HIDE
		lblFirstSeen.setVisible(false);
		chckbxItemPrice.setVisible(false); 
		chckbxCurrentStock.setVisible(false); 
		chckbxRestock.setVisible(false);
		btnSearch .setVisible(false);
		
		//EDITABLE				
		txtAdd.setEditable(true);
		
		//NOT EDITABLE	
		txtItemName.setEditable(false);		 
		txtItemWeight.setEditable(false);
		txtItemNumber.setEditable(false);
		txtPrice.setEditable(false);		 
		txtCurrentStock.setEditable(false);		 
		txtRestock.setEditable(false);
		for (JCheckBox temp : itemTypeList) {
			temp.setEnabled(false);
		}
		
		/**
		chckbxFragile.setEnabled(false);
		chckbxFlammable.setEnabled(false); 
		chckbxCorrosive.setEnabled(false); 
		chckbxRadioActive.setEnabled(false); 		//why can the checkboxes be modified???????????
		chckbxGlass.setEnabled(false); 
		chckbxFurniture.setEnabled(false); 
		chckbxOther.setEnabled(false); */
		
		updateTextBoxes();
	}

	private void notFoundManagerScreen() {
		//SEE
		lblRequiredInformation.setVisible(true); 
		lblItemNumber.setVisible(true); 
		lblItemName.setVisible(true); 
		lblItemWeight.setVisible(true); 
		lblItemTypeselect.setVisible(true); 
		lblAdditionalItemInformation.setVisible(true);
		txtItemNumber.setVisible(true);
		txtItemName.setVisible(true);
		txtItemWeight.setVisible(true);
		txtPrice.setVisible(true);
		txtCurrentStock.setVisible(true);
		txtRestock.setVisible(true);
		chckbxItemPrice.setVisible(true); 
		chckbxCurrentStock.setVisible(true); 
		chckbxRestock.setVisible(true);		
		cbItemTypes.setVisible(true);
		btnExit.setVisible(true);		
		btnSubmit.setVisible(true);
		btnSearchAgain.setVisible(true);
		frame.getRootPane().setDefaultButton(btnSubmit);
		
		//HIDE
		lblFirstSeen.setVisible(false);		
		btnSearch.setVisible(false);		
		lblPrice.setVisible(false);
		lblCurrentStock.setVisible(false);
		lblRestock.setVisible(false);
		txtAdd.setVisible(false);
		
		//EDITABLE				
		txtItemName.setEditable(true);		 
		txtItemWeight.setEditable(true);
		for (JCheckBox temp : itemTypeList) {
			temp.setEnabled(true);
		}
		
		//NOT EDITABLE	
		txtItemNumber.setEditable(false);
		txtPrice.setEditable(false);		 
		txtCurrentStock.setEditable(false);		 
		txtRestock.setEditable(false);
		txtAdd.setEditable(false);
		
		/**
		chckbxFragile.setEnabled(false);
		chckbxFlammable.setEnabled(false); 
		chckbxCorrosive.setEnabled(false); 
		chckbxRadioActive.setEnabled(false); 		//why can the checkboxes be modified???????????
		chckbxGlass.setEnabled(false); 
		chckbxFurniture.setEnabled(false); 
		chckbxOther.setEnabled(false); */
		
		updateTextBoxes();
	}
	
	private void notFoundEmployeeScreen() {
		//SEE
		lblRequiredInformation.setVisible(true); 
		lblItemNumber.setVisible(true); 
		lblItemName.setVisible(true); 
		lblItemWeight.setVisible(true); 
		lblItemTypeselect.setVisible(true); 
		lblAdditionalItemInformation.setVisible(true);
		txtItemNumber.setVisible(true);
		txtItemName.setVisible(true);
		txtItemWeight.setVisible(true);
		txtPrice.setVisible(true);
		txtCurrentStock.setVisible(true);
		txtRestock.setVisible(true);
		txtAdd.setVisible(true);
		txtAdd.requestFocus();
		chckbxItemPrice.setVisible(true); 
		chckbxCurrentStock.setVisible(true); 
		chckbxRestock.setVisible(true);
		chckbxAdd.setVisible(true);
		chckbxAdd.setSelected(true);
		cbItemTypes.setVisible(true);
		btnExit.setVisible(true);		
		btnSubmit.setVisible(true);
		btnSearchAgain.setVisible(true);
		frame.getRootPane().setDefaultButton(btnSubmit);
		
		//HIDE
		lblFirstSeen.setVisible(false);
		
		btnSearch .setVisible(false);
		lblPrice.setVisible(false);
		lblCurrentStock.setVisible(false);
		lblRestock.setVisible(false);
		//EDITABLE				
		txtAdd.setEditable(true);
		txtItemName.setEditable(true);		 
		txtItemWeight.setEditable(true);
		for (JCheckBox temp : itemTypeList) {
			temp.setEnabled(true);
		}
		//NOT EDITABLE	
		
		txtItemNumber.setEditable(false);
		txtPrice.setEditable(false);		 
		txtCurrentStock.setEditable(false);		 
		txtRestock.setEditable(false);
		
		
		/**
		chckbxFragile.setEnabled(false);
		chckbxFlammable.setEnabled(false); 
		chckbxCorrosive.setEnabled(false); 
		chckbxRadioActive.setEnabled(false); 		//why can the checkboxes be modified???????????
		chckbxGlass.setEnabled(false); 
		chckbxFurniture.setEnabled(false); 
		chckbxOther.setEnabled(false); */
		
		updateTextBoxes();	
	}
	//#############################################Display Methods end
	
	//#############################################SQL Calls
	/**
	 * This method calls on the SQL_Handler to search the database for the input by item_number, the boolean "found" is set by the results of itemInDB() and
	 * the TextFields are populated by the database if found returns true, otherwise the fields are left blank 
	 * @param input
	 */
	public void searchDB(String input) {
		try {			
			found = SQL_Handler.itemInDB(input);  //search the database using SQL_Handler for user input, set boolean found to result of search
			if (found) {	 //if the item is already in the database, notify the user and retrieve the info from the database assigning it accordingly
				//JOptionPane.showMessageDialog(frame, "Item Number " + txtItemNumber.getText() + " is in the inventory.");
				itemNumber = input;
				itemName = SQL_Handler.getItemName(input);
				itemPrice = SQL_Handler.getItemPrice(input);
				itemWeight = SQL_Handler.getItemWeight(input);
				itemStock = SQL_Handler.getItemCurrentStock(input);
				itemRestock = SQL_Handler.getItemRestock(input);
				frame.setTitle("Edit Item Information");
			}
			else {//if the item is not in the database, notify the user
				//JOptionPane.showMessageDialog(frame, "Item Number " + txtItemNumber.getText() + " is not in the inventory. Please enter that Item's Information");
				frame.setTitle("Enter New Item Information");				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//searchDB end
	
	/**
	 * This method calls SQL_Handler.getItemTypes() passing the itemNumber, then with the resulting list it checks off
	 * the checkboxes that apply to that specific item
	 */
	private void getItemTypes() {
		try {
			ArrayList<String> result = (ArrayList<String>) SQL_Handler.getItemTypes(itemNumber); //get all occurances of itemType in the db
			for (String temp : result) { //for each string in the results
				for (JCheckBox chckbxTemp : itemTypeList){ //for each checkbox in itemTypeList
					if (chckbxTemp.getText().equals(temp)) { //if the text of the checkbox is in the result list
						chckbxTemp.setSelected(true); //check that box
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}//setItemTypes

	private void newSubmit() {
		if (Valid.allEntriesValid(txtItemNumber.getText(), txtItemName.getText(), txtPrice.getText(), txtItemWeight.getText(), txtCurrentStock.getText(), txtRestock.getText())){
			updateVariables();
			
			try { //try connecting and inserting a new item using the info on screen, notify user of success
				Connection conn = SQL_Handler.getConnection();
				SQL_Handler.insertNewItem(txtItemNumber.getText(), txtItemName.getText(), itemPrice, itemWeight, itemStock, itemRestock);
				setItemTypes();
				JOptionPane.showMessageDialog(frame, "Item Number " + itemNumber + ": " + itemName + " Added to inventory");
				updateTextBoxes();
			} catch (SQLException e1) { //otherwise let the user know the item wasnt added and close without crashing
				JOptionPane.showMessageDialog(frame, "Failed to add Item Number " + itemNumber + ": " + itemName + " to inventory");
				System.exit(0);
				e1.printStackTrace();
			}//trycatch end			
		}//if allEntriesValid end
	}//newSubmit end
	
	private void fullItemUpdate() {
		try {
			updateVariables();
			SQL_Handler.fullItemUpdate(itemName, itemPrice, itemWeight, itemStock, itemRestock, itemNumber);
			JOptionPane.showMessageDialog(frame, "Updated item number: " + itemNumber);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "Failed to update item number: " + itemNumber);
			e.printStackTrace();
		}
	}
	
	private void addStock() {
		if (chckbxAdd.isSelected() && Valid.validInt(txtAdd.getText())) { //if the user is trying to add to an item stock
						try {
							itemAdd = Integer.parseInt(txtAdd.getText());
							itemNumber = txtItemNumber.getText();
							SQL_Handler.updateItemQtyByItemNum(itemAdd, itemNumber);
							if(itemAdd != 0)
								JOptionPane.showMessageDialog(frame, "Updated Item Number " + itemNumber + ": " + itemName + ", increased Current Stock by " + itemAdd);
							itemStock = SQL_Handler.getItemCurrentStock(itemNumber);
							updateTextBoxes();
						}catch (SQLException e2) {
							JOptionPane.showMessageDialog(frame, "Failed to update item " + itemNumber + ": " + itemName);							
							e2.printStackTrace();
							System.exit(0);
						}//trycatch end
					}//if theres anything to add to stock end
		else {
			try {
				itemAdd = 0;
				itemNumber = txtItemNumber.getText();
				SQL_Handler.updateItemQtyByItemNum(itemAdd, itemNumber);
				//JOptionPane.showMessageDialog(frame, "Updated Item Number " + itemNumber + ": " + itemName + ", increased Current Stock by " + itemAdd);
				itemStock = SQL_Handler.getItemCurrentStock(itemNumber);
				updateTextBoxes();
			}catch (SQLException e2) {
				JOptionPane.showMessageDialog(frame, "Failed to update item " + itemNumber + ": " + itemName);							
				e2.printStackTrace();
				System.exit(0);
			}//trycatch end
		}
					
	}//addStock end
	//#############################################SQL Calls end
	
	//#############################################Setters
	public void setTxtItemNumber(String S) {
		if (Valid.validID(S))
			txtItemNumber.setText(S);
	}
	
	public void setTxtItemName(String S) {
		txtItemName.setText(S);
	}
	
	public void setTxtItemWeight(String S) {
		if (Valid.validInt(S))
			txtItemWeight.setText(S);
	}
	
	public void setTxtPrice(String S) {
		if (Valid.validDouble(S))
			txtPrice.setText(S);
	}
	
	public void setTxtCurrentStock(String S) {
		if (Valid.validInt(S))
			txtCurrentStock.setText(S);
	}
	
	public void setTxtItemRestock(String S) {
		if (Valid.validInt(S))
			txtRestock.setText(S);
	}
	
	/**
	 * This method creates and sets all the labels to their intended values and places them onto the frame
	 */
	private void setLabels() {
		
		lblRequiredInformation = new JLabel("Required Item Information");
		lblRequiredInformation.setToolTipText("This information must be filled out properly to insert an item to the inventory.");
		lblRequiredInformation.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRequiredInformation.setBounds(25, 30, 174, 19);
		frame.getContentPane().add(lblRequiredInformation);
		
		lblFirstSeen = new JLabel("Please Scan the BarCode or Enter the ItemNumber");
		lblFirstSeen.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFirstSeen.setBounds(17, 0, 357, 20);
		frame.getContentPane().add(lblFirstSeen);
		
		lblItemNumber = new JLabel("Item Number");
		lblItemNumber.setToolTipText("Please enter the Item Number using a barcode scanner or numeric keypad.");
		lblItemNumber.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblItemNumber.setBounds(35, 60, 73, 15);
		frame.getContentPane().add(lblItemNumber);
		
		String IT = "Item Types";
		if (!found)										// TODO Change this in each screen
			IT = IT + " (Select All That Apply)";  //modify label to let user know if they need to add item types or not
		
		lblItemTypeselect = new JLabel(IT);
		lblItemTypeselect.setToolTipText("Check each Item Type that applies to the Item you are currently adding.");
		lblItemTypeselect.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblItemTypeselect.setBounds(35, 180, 195, 15);
		frame.getContentPane().add(lblItemTypeselect);
		
		lblAdditionalItemInformation = new JLabel("Additional Item Information");
		lblAdditionalItemInformation.setToolTipText("This information is not required to insert the item to the inventory.");
		lblAdditionalItemInformation.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAdditionalItemInformation.setBounds(25, 275, 180, 19);
		frame.getContentPane().add(lblAdditionalItemInformation);	
		
		lblItemName = new JLabel("Item Name");
		lblItemName.setToolTipText("Please enter the name of the Item as you would like it to be recorded in your inventory.");
		lblItemName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblItemName.setBounds(35, 100, 61, 15);
		frame.getContentPane().add(lblItemName);
		
		lblItemWeight = new JLabel("Item Weight");
		lblItemWeight.setToolTipText("Please enter the weight of the Item in pounds (lbs).");
		lblItemWeight.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblItemWeight.setBounds(35, 140, 70, 15);
		frame.getContentPane().add(lblItemWeight);

		lblPrice = new JLabel("Item Price");
		lblPrice.setToolTipText("Please enter the price of the Item in Dollar format \"0.00\""); 
		lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPrice.setBounds(35, 296, 97, 23);
		frame.getContentPane().add(lblPrice);
																			
		lblCurrentStock = new JLabel("Current Stock");
		lblCurrentStock.setToolTipText("Please enter the current stock of the Item.");
		lblCurrentStock.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCurrentStock.setBounds(35, 336, 101, 23);
		frame.getContentPane().add(lblCurrentStock);
		
		lblRestock = new JLabel("Restock Threshold");
		lblRestock.setToolTipText("Please enter the stock level which someone should be notified if the current stock goes under.");
		lblRestock.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblRestock.setBounds(35, 376, 127, 23);
		frame.getContentPane().add(lblRestock);
		
		
	}//setLabels end
	
	/**
	 * This method creates and sets all the textfields onto the frame along with implementing their listeners
	 */
	private void setTextFieldsInfo() {		
		txtItemNumber = new JTextField();
		txtItemNumber.setBounds(168, 58, 175, 20);
		frame.getContentPane().add(txtItemNumber);
		txtItemNumber.setColumns(10);
		txtItemNumber.setText("");
		txtItemNumber.setTransferHandler(null); //prevent copy paste into the field
		txtItemNumber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				Valid.intInput(evt.getKeyChar(), evt);
			}
		});//txtItemNumber Listener end
		
		txtItemName = new JTextField();
		txtItemName.setBounds(168, 98, 175, 20);
		frame.getContentPane().add(txtItemName);
		txtItemName.setColumns(10);		
		txtItemName.setTransferHandler(null); //prevent copy paste into the field
		
		txtItemWeight = new JTextField();
		txtItemWeight.setBounds(168, 138, 175, 20);
		frame.getContentPane().add(txtItemWeight);
		txtItemWeight.setColumns(10);
		txtItemWeight.setTransferHandler(null); //prevent copy paste into the field
		txtItemWeight.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				Valid.intInput(evt.getKeyChar(), evt);
			}
		});//txtItemWeight Listener end
		
		txtPrice = new JTextField();		
		txtPrice.setEditable(false);
		txtPrice.setBounds(168, 298, 175, 20);
		frame.getContentPane().add(txtPrice);
		txtPrice.setColumns(10);
		txtPrice.setTransferHandler(null); //prevent copy paste into the field
		txtPrice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				Valid.dblInput(evt.getKeyChar(), evt);
			}
		});//txtPrice Listener end
		
		txtCurrentStock = new JTextField();		
		txtCurrentStock.setEditable(false);
		txtCurrentStock.setBounds(168, 338, 175, 20);
		frame.getContentPane().add(txtCurrentStock);
		txtCurrentStock.setColumns(10);		
		txtCurrentStock.setTransferHandler(null); //prevent copy paste into the field
		txtCurrentStock.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				Valid.intInput(evt.getKeyChar(), evt);
			}
		});//txtCurrentStock Listener end
		
		txtRestock = new JTextField();		
		txtRestock.setEditable(false);
		txtRestock.setBounds(168, 378, 175, 20);
		frame.getContentPane().add(txtRestock);
		txtRestock.setColumns(10);	
		txtRestock.setTransferHandler(null); //prevent copy paste into the field
		txtRestock.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				Valid.intInput(evt.getKeyChar(), evt);
			}
		});//txtItemRestock Listener end
		
	
		txtAdd = new JTextField(); //if the item is in the db allow user to add to stock
		txtAdd.setText("");
		txtAdd.setEditable(true);
		txtAdd.setBounds(168, 416, 175, 20);
		frame.getContentPane().add(txtAdd);
		txtAdd.setColumns(10);
		txtAdd.setTransferHandler(null); //prevent copy paste into the field
		txtAdd.requestFocusInWindow(); //user focus
		txtAdd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				Valid.intInput(evt.getKeyChar(), evt);
			}
		});//txtAdd KeyListener end
		txtAdd.addFocusListener(new FocusListener() {
		    @Override
			public void focusGained(FocusEvent e) {
				if (txtAdd.getText().equals("0"))
					txtAdd.setText("");
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtAdd.getText().equals(""))
					txtAdd.setText("0");
			}

		    });//txtAdd FocusListener end
																			//TODO remove if
		//if the item was in the db populate the text fields with its info
		txtItemName.setText(itemName);
		txtItemWeight.setText(Integer.toString(itemWeight));
		txtPrice.setText(itemPrice);
		txtCurrentStock.setText(Integer.toString(itemStock));
		txtRestock.setText(Integer.toString(itemRestock));
	}//setTextFieldsInfo end
	
	/**
	 * This method creates and sets all the checkboxes under OptionalInfo onto 
	 * the frame along with implementing their listeners
	 */
	private void setInfoCheckBoxes() {
		chckbxItemPrice = new JCheckBox("Item Price");
		chckbxItemPrice.setToolTipText("Please enter the price of the Item in Dollar format \"0.00\""); 
		chckbxItemPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxItemPrice.setBounds(35, 296, 97, 23);
		frame.getContentPane().add(chckbxItemPrice);
		chckbxItemPrice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtPrice.setEditable(chckbxItemPrice.isSelected()); //set txtPrice to Editable if the checkbox is ticked
				if (chckbxItemPrice.isSelected() == false && !(found))
					txtPrice.setText(itemPrice);
			}
		});//chckbxItemPrice Listener end
		chckbxCurrentStock = new JCheckBox("Current Stock");
		chckbxCurrentStock.setToolTipText("Please enter the current stock of the Item."); 
		chckbxCurrentStock.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxCurrentStock.setBounds(35, 336, 101, 23);
		frame.getContentPane().add(chckbxCurrentStock);
		chckbxCurrentStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtCurrentStock.setEditable(chckbxCurrentStock.isSelected()); //set txtCurrentStock to Editable if the checkbox is ticked
				if (chckbxCurrentStock.isSelected() == false && !(found))
					txtCurrentStock.setText(Integer.toString(itemStock));
			}
		});//chckbxItemPrice Listener end
		chckbxRestock = new JCheckBox("Restock Threshold");
		chckbxRestock.setToolTipText("Please enter the stock level which someone should be notified if the current stock goes under.");
		chckbxRestock.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxRestock.setBounds(35, 376, 127, 23);
		frame.getContentPane().add(chckbxRestock);
		chckbxRestock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtRestock.setEditable(chckbxRestock.isSelected()); //set txtCurrentStock to Editable if the checkbox is ticked
				if (chckbxRestock.isSelected() == false && !(found))
					txtRestock.setText(Integer.toString(itemRestock));
			}
		});//chckbxItemPrice Listener end
	
	
	
		chckbxAdd = new JCheckBox("Add to Stock");
		chckbxAdd.setToolTipText("Please enter the amount of stock you are increasing the current stock by");
		chckbxAdd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxAdd.setBounds(35, 414, 99, 23);
		chckbxAdd.setSelected(true);
		frame.getContentPane().add(chckbxAdd);
		chckbxAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtAdd.setEditable(chckbxAdd.isSelected()); //set txtAdd to Editable if the checkbox is ticked
				if (chckbxAdd.isSelected() == false)
					txtAdd.setText("0");
			}
		});//chckbxAddToStock Listener end	
	
		
		updateTextBoxes();
	}//setInfoCheckBoxes end

	/**
	 * This method creates and sets all the ItemTypeCheckBoxes and puts them into a combobox
	 */
	private void setItemTypeCheckBoxes() {
		chckbxRadioActive = new JCheckBox("Radioactive");
		chckbxRadioActive.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(chckbxRadioActive);
				
		chckbxOther = new JCheckBox("Other");
		chckbxOther.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(chckbxOther);
		
		chckbxCorrosive = new JCheckBox("Corrosive");
		chckbxCorrosive.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(chckbxCorrosive);
		
		chckbxFragile = new JCheckBox("Fragile");
		chckbxFragile.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(chckbxFragile);
		
		chckbxFlammable = new JCheckBox("Flammable");
		chckbxFlammable.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(chckbxFlammable);
		
		chckbxGlass = new JCheckBox("Glass");
		chckbxGlass.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(chckbxGlass);
		
		chckbxFurniture = new JCheckBox("Furniture");
		chckbxFurniture.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(chckbxFurniture);
						
		itemTypeList.add(chckbxCorrosive);
		itemTypeList.add(chckbxFlammable);
		itemTypeList.add(chckbxFragile);
		itemTypeList.add(chckbxFurniture);
		itemTypeList.add(chckbxGlass);
		itemTypeList.add(chckbxRadioActive);
		itemTypeList.add(chckbxOther);		
		
		
		v = new Vector();
		v.add(chckbxCorrosive);
		v.add(chckbxFlammable);
		v.add(chckbxFragile);
		v.add(chckbxFurniture);
		v.add(chckbxGlass);
		v.add(chckbxRadioActive);
		v.add(chckbxOther);
		//frame.getContentPane().add(new CustomComboCheck(v));
		cbItemTypes = new CustomComboCheck(v, found);
		cbItemTypes.setBounds(35, 195, 308, 20);		
		frame.getContentPane().add(cbItemTypes);
	}

	/** 
	 * This method creates and sets all buttons onto the frame along with implementing their listeners
	 */
	private void setButtons()
	{
		btnExit = new JButton("Exit");		
		btnExit.setBounds(25, 455, 90, 23);
		frame.getContentPane().add(btnExit);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();;
			}
		});//btnExit Listener end
		
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Valid.validItemNumber(txtItemNumber.getText())) {
					searchDB(txtItemNumber.getText());
					if (found) {
						if (isM) {
							foundManagerScreen();
						}//if isM end
						else {
							foundEmployeeScreen();
						}					
					}//if found end
					else {
						if (isM) {
						notFoundManagerScreen();
						}//if isM end
						else {
							notFoundEmployeeScreen();
						}
					}//if not found end
				}//if validEntry end
				else {
				JOptionPane.showMessageDialog(frame, "Cannot Search for an empty entry, \nPlease enter or scan an Item Number");
				}//if invalidEntry end
			}//actionpPerformed end
		});//btnSearch Listener end
		btnSearch.setBounds(140, 455, 90, 23);
		frame.getContentPane().add(btnSearch);
		
		btnSearchAgain = new JButton("Search Another Item");
		btnSearchAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetEverything();
				searchScreen();
			}
		});
		btnSearchAgain.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnSearchAgain.setBounds(122, 455, 125, 23);
		frame.getContentPane().add(btnSearchAgain);
		
		btnSubmit = new JButton("Submit");	
		if (found) {
			btnSubmit.setText("Update");
		}
		btnSubmit.setBounds(255, 455, 90, 23);
		frame.getContentPane().add(btnSubmit);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				if (found) { //if the item is already in the data base
					if (isM) {
						fullItemUpdate();
					}
					addStock();					
				}//if found end
				else {
					newSubmit();
				}
				
			}//actionPerformed end
		});//btnSubmit end	
		
	}//setButtons end
		
	/**
	 * this method calls SQL_Handler.insertItemType() passing itemNumber and each box checked in the itemTypeList
	 */
	private void setItemTypes() {
		for (JCheckBox temp : itemTypeList) {
			if (temp.isSelected()) {
				try {
					SQL_Handler.insertItemType(txtItemNumber.getText(), temp.getText());
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
		}
	}//setItemTypes end

	private void resetEverything() {
		itemWeight = 1;
		itemStock = 0;
		itemRestock = 0;
		itemAdd = 0;
		itemNumber = "";
		itemName = "";
		itemPrice = "0.00";
		input = "";
		found = false;
		for (JCheckBox temp : itemTypeList) {
			temp.setSelected(false);
		}
		txtItemNumber.setText("");
		txtItemName.setText("");
		txtItemWeight.setText("");
		txtPrice.setText("");
		txtCurrentStock.setText("");
		txtRestock.setText("");
		txtAdd.setText("");
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
}//ScanWindow end

//COMBOBOX STUFF~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
class CustomComboCheck extends JComboBox {
	public CustomComboCheck(Vector v, boolean f) {
		super(v);
	
		//set renderer
		setRenderer(new ComboRenderer());
	
		//set listener
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				if (f == false) { //the check box should only change if item is not in db
					ourItemSelected();
				} 
				else {
					//do nothing
				}
			}
		});//listener end	
	}//constructor end
	
	private void ourItemSelected(){
		Object selected = getSelectedItem();
		if (selected instanceof JCheckBox) {
			JCheckBox chk = (JCheckBox) selected;
			chk.setSelected(!chk.isSelected()); 
			repaint();
			
			Object[] selections = chk.getSelectedObjects();
			if (selections != null) {
				for(Object lastItem: selections) {
				
				}//for end
			}//if not null
		}//instanceof end
		
		
	}//ourItemSelected end
}//customComboCheck end

class ComboRenderer implements ListCellRenderer {
	private JLabel label;
	@Override
	public Component getListCellRendererComponent(JList list, Object val, int index, boolean selected, boolean focused) {
		
		if(val instanceof Component) {
			Component c = (Component) val;
			if (selected) {
				c.setBackground(list.getSelectionBackground());
				c.setForeground(list.getSelectionForeground());				
			}//if selected end
			else {
				c.setBackground(list.getBackground());
				c.setForeground(list.getForeground());
			}
			return c;
		}//if instanceof Component end
		else {
			if (label == null) {
				label = new JLabel(val.toString());				
			}
			else {
				label.setText(val.toString());
			}
			return label;
		}//instanceof else end
	}//getListCellRendererComponent end
	
}//comboRenderer end