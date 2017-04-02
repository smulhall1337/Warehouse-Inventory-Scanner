import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ScanWindow {

	private JFrame frame;
	private int itemNumber, itemWeight, itemStock = 0, itemRestock = 0, itemAdd = 0;
	private String itemName, itemPrice = "0.00"; 
	private boolean found;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ScanWindow window = new ScanWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}//main end

	/**
	 * Create the application.
	 */
	public ScanWindow() {
		String input = "";
		new CustomDialog(frame).setVisible(true); //create a custom dialog to request input,
		input = CustomDialog.getInput(); //CustomDialog does not allow input other than integers

		try {//convert entry to int and assign, if there is no input (user selected cancel or hit X on dialogbox) close the window
			itemNumber = Integer.parseInt(input); 
		} catch (Exception e) {
			System.exit(0);
		}
		
		try {			
			found = SQL_Handler.itemInDB(input);  //search the database using SQL_Handler for user input, set boolean found to result of search
			if (found) {	 //if the item is already in the database, notify the user and retrieve the info from the database assigning it accordingly
				JOptionPane.showMessageDialog(frame, "Item Number " + itemNumber + " is in the inventory!");
				itemName = SQL_Handler.getItemName(input);
				itemPrice = SQL_Handler.getItemPrice(input);
				itemWeight = SQL_Handler.getItemWeight(input);
				itemStock = SQL_Handler.getItemCurrentStock(input);
				itemRestock = SQL_Handler.getItemRestock(input);
			}
			else //if the item is not in the database, notify the user
				JOptionPane.showMessageDialog(frame, "Item Number " + itemNumber + " is not in the inventory! Please enter that Item's Information");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		initialize();
	}//ScanWindow end

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		if (found)
			frame.setTitle("Edit Item Information");
		else
			frame.setTitle("Enter New Item Information");
		
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Labels~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
		JLabel lblRequiredInformation = new JLabel("Required Item Information");
		lblRequiredInformation.setToolTipText("This information must be filled out properly to insert an item to the inventory");
		lblRequiredInformation.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRequiredInformation.setBounds(25, 30, 174, 19);
		frame.getContentPane().add(lblRequiredInformation);
		
		JLabel lblItemNumber = new JLabel("Item Number");
		lblItemNumber.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblItemNumber.setBounds(35, 60, 73, 15);
		frame.getContentPane().add(lblItemNumber);
		
		JLabel lblItemName = new JLabel("Item Name");
		lblItemName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblItemName.setBounds(35, 100, 61, 15);
		frame.getContentPane().add(lblItemName);
		
		JLabel lblItemWeight = new JLabel("Item Weight");
		lblItemWeight.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblItemWeight.setBounds(35, 140, 70, 15);
		frame.getContentPane().add(lblItemWeight);
		
		JLabel lblItemTypeselect = new JLabel("Item Type (Select All That Apply)");
		lblItemTypeselect.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblItemTypeselect.setBounds(25, 180, 186, 15);
		frame.getContentPane().add(lblItemTypeselect);
		
		JLabel lblOptionalItemInformation = new JLabel("Optional Item Information");
		lblOptionalItemInformation.setToolTipText("This information is not required to insert the item to the inventory");
		lblOptionalItemInformation.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblOptionalItemInformation.setBounds(25, 275, 170, 19);
		frame.getContentPane().add(lblOptionalItemInformation);
		
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~TextFields~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		JTextField txtItemNumber = new JTextField();				
		txtItemNumber.setEditable(false);
		txtItemNumber.setBounds(168, 58, 175, 20);
		frame.getContentPane().add(txtItemNumber);
		txtItemNumber.setColumns(10);
		txtItemNumber.setText(Integer.toString(itemNumber));
		txtItemNumber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				IntInput(evt.getKeyChar(), evt);
			}
		});//txtItemNumber Listener end
		
		JTextField txtItemName = new JTextField();
		txtItemName.setBounds(168, 98, 175, 20);
		frame.getContentPane().add(txtItemName);
		txtItemName.setColumns(10);
		txtItemName.setText(itemName);
		
		JTextField txtItemWeight = new JTextField();
		txtItemWeight.setBounds(168, 138, 175, 20);
		frame.getContentPane().add(txtItemWeight);
		txtItemWeight.setColumns(10);
		txtItemWeight.setText(Integer.toString(itemWeight));
		txtItemWeight.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				IntInput(evt.getKeyChar(), evt);
			}
		});//txtItemWeight Listener end
		
		JTextField txtPrice = new JTextField();		
		txtPrice.setEditable(false);
		txtPrice.setBounds(168, 298, 175, 20);
		frame.getContentPane().add(txtPrice);
		txtPrice.setColumns(10);
		txtPrice.setText(itemPrice);
		txtPrice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				DblInput(evt.getKeyChar(), evt);
			}
		});//txtPrice Listener end
		
		JTextField txtCurrentStock = new JTextField();		
		txtCurrentStock.setEditable(false);
		txtCurrentStock.setBounds(168, 338, 175, 20);
		frame.getContentPane().add(txtCurrentStock);
		txtCurrentStock.setColumns(10);
		txtCurrentStock.setText(Integer.toString(itemStock));
		txtCurrentStock.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				IntInput(evt.getKeyChar(), evt);
			}
		});//txtCurrentStock Listener end
		
		JTextField txtItemRestock = new JTextField();		
		txtItemRestock.setEditable(false);
		txtItemRestock.setBounds(168, 378, 175, 20);
		frame.getContentPane().add(txtItemRestock);
		txtItemRestock.setColumns(10);
		txtItemRestock.setText(Integer.toString(itemRestock));
		txtItemRestock.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				IntInput(evt.getKeyChar(), evt);
			}
		});//txtItemRestock Listener end
		
		JTextField txtAdd = new JTextField();
		txtAdd.setText("0");
		txtAdd.setEditable(false);
		txtAdd.setBounds(168, 416, 175, 20);
		frame.getContentPane().add(txtAdd);
		txtAdd.setColumns(10);
		txtAdd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				IntInput(evt.getKeyChar(), evt);
			}
		});//txtAdd Listener end
		
		
		if (found) {
			txtItemNumber.setEditable(false);
			txtItemName.setEditable(false);
			txtItemWeight.setEditable(false);
		}
		
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~CheckBoxes~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		//    Once management flag is set up, add " && isManagement" to .setEditable() to only allow management to modify sensitive data like price current stock and restock
		JCheckBox chckbxItemPrice = new JCheckBox("Item Price");
		chckbxItemPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxItemPrice.setBounds(35, 296, 97, 23);
		frame.getContentPane().add(chckbxItemPrice);
		chckbxItemPrice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtPrice.setEditable(chckbxItemPrice.isSelected()); //set txtPrice to Editable if the checkbox is ticked
				if (chckbxItemPrice.isSelected() == false && !(found))
					txtPrice.setText("0.00");
			}
		});//chckbxItemPrice Listener end
		
		JCheckBox chckbxCurrentStock = new JCheckBox("Current Stock");
		chckbxCurrentStock.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxCurrentStock.setBounds(35, 336, 101, 23);
		frame.getContentPane().add(chckbxCurrentStock);
		chckbxCurrentStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtCurrentStock.setEditable(chckbxCurrentStock.isSelected()); //set txtCurrentStock to Editable if the checkbox is ticked
				if (chckbxCurrentStock.isSelected() == false && !(found))
					txtCurrentStock.setText("0");
			}
		});//chckbxItemPrice Listener end
		
		JCheckBox chckbxRestockThreshold = new JCheckBox("Restock Threshold");
		chckbxRestockThreshold.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxRestockThreshold.setBounds(35, 376, 127, 23);
		frame.getContentPane().add(chckbxRestockThreshold);
		chckbxRestockThreshold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtItemRestock.setEditable(chckbxRestockThreshold.isSelected()); //set txtCurrentStock to Editable if the checkbox is ticked
				if (chckbxRestockThreshold.isSelected() == false && !(found))
					txtItemRestock.setText("0");
			}
		});//chckbxItemPrice Listener end
		
		JCheckBox chckbxAdd = new JCheckBox("Add to Stock");
		chckbxAdd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxAdd.setBounds(35, 414, 99, 23);
		frame.getContentPane().add(chckbxAdd);
		chckbxAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtAdd.setEditable(chckbxAdd.isSelected()); //set txtAdd to Editable if the checkbox is ticked
				if (chckbxAdd.isSelected() == false)
					txtAdd.setText("0");
			}
		});//chckbxAddToStock Listener end
		
		//item types
		JCheckBox chckbxRadioactive = new JCheckBox("Radioactive");
		chckbxRadioactive.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxRadioactive.setBounds(25, 228, 81, 23);
		frame.getContentPane().add(chckbxRadioactive);
		
		JCheckBox chckbxOther = new JCheckBox("Other");
		chckbxOther.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxOther.setBounds(109, 228, 53, 23);
		frame.getContentPane().add(chckbxOther);
		
		JCheckBox chckbxCorrosive = new JCheckBox("Corrosive");
		chckbxCorrosive.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxCorrosive.setBounds(168, 202, 71, 23);
		frame.getContentPane().add(chckbxCorrosive);
		
		JCheckBox chckbxFragile = new JCheckBox("Fragile");
		chckbxFragile.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxFragile.setBounds(109, 202, 57, 23);
		frame.getContentPane().add(chckbxFragile);
		
		JCheckBox chckbxFlam = new JCheckBox("Flammable");
		chckbxFlam.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxFlam.setBounds(25, 202, 75, 23);
		frame.getContentPane().add(chckbxFlam);	
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Buttons~~~~~~~~~~~~~~~~~~~~~~~~~~~
		JButton btnExit = new JButton("Exit");		
		btnExit.setBounds(25, 456, 89, 23);
		frame.getContentPane().add(btnExit);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});//btnExit Listener end
		
		JButton btnSubmit = new JButton("Submit");		
		btnSubmit.setBounds(254, 456, 89, 23);
		frame.getContentPane().add(btnSubmit);
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				if (!(found) && validInt(txtItemWeight.getText()) && //if the item is NOT in the database already and first boxes inputs are valid
						validString(txtItemNumber.getText()) && validString(txtItemName.getText())) {
					if (chckbxRestockThreshold.isSelected() && validString(txtItemRestock.getText())) { //if restock threshold button is checked and its not empty
						itemRestock = Integer.parseInt(txtItemRestock.getText()); //set the reStock
					}
					if (chckbxCurrentStock.isSelected() && validString(txtCurrentStock.getText())) {//if current stock button is checked and its not empty
						itemStock = Integer.parseInt(txtCurrentStock.getText());  //set the currentStock
					}
					if (chckbxItemPrice.isSelected() && validDouble(txtPrice.getText())) {//if price button is checked and it has a double
						itemPrice = txtPrice.getText();  //set the price
					}
					itemWeight = Integer.parseInt(txtItemWeight.getText()); 
					
					try { //try connecting and inserting a new item using the info on screen, notify user of success
						Connection conn = SQL_Handler.getConnection();
						SQL_Handler.insertNewItem(txtItemNumber.getText(), txtItemName.getText(), itemPrice, itemWeight, itemStock, itemRestock);
						JOptionPane.showMessageDialog(frame, "Item " + itemNumber + ": " + itemName + " Added to inventory");
					} catch (SQLException e1) { //otherwise let the user know the item wasnt added and close without crashing
						JOptionPane.showMessageDialog(frame, "Failed to add item " + itemNumber + ": " + itemName + " to inventory");
						System.exit(0);
						e1.printStackTrace();
					}//trycatch end
				}//if 1st boxes valid end
				
				if (found) { //if the item is already in the data base
					if (chckbxAdd.isSelected() && validInt(txtAdd.getText())) { //if the user is trying to add to an item stock
						try {
							itemAdd = Integer.parseInt(txtAdd.getText());
							SQL_Handler.updateItemQtyByItemNum(itemAdd, Integer.toString(itemNumber));
						}catch (SQLException e2) {
							JOptionPane.showMessageDialog(frame, "Failed to update item " + itemNumber + ": " + itemName);							
							System.exit(0);
							e2.printStackTrace();
						}//trycatch end
					}//if theres anything to add to stock end
					
					//this is where the management section goes
					//if the user is a manager, check all the fields are properly filled out and update the item
					
				}//if found end
			}//actionPerformed end
		});//btnSubmit end	
		
	}//initialize end
	
	
	
	
	
	/**
	 * True if the string is not empty, false if the string is ""
	 * @param s
	 * @return boolean
	 */
	public static boolean validString(String s) {
		if (!(s.equals(""))) {
			return true;
		}
		else {
			System.out.println("Error: Empty String");
			return false;
		}
	}//validString end
	
	/**
	 * True if the string can be converted to a double
	 * @param s
	 * @return boolean
	 */
	public static boolean validDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (Exception ee) {
			System.out.println("Error: Input not valid, requires Double");
			return false;
		}
	}//validDouble end
	
	/**
	 * True if the string can be converted to an integer
	 * @param s
	 * @return boolean
	 */
	public static boolean validInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception ee) {
			System.out.println("Error: Input not valid, requires Integer");
			return false;
		}
	}//validInt end
	
	/**
	 * Input checker that only accepts digits(and backspace/delete) as input
	 * @param c 
	 * @param evt
	 */
	public void IntInput(char c, KeyEvent evt) {
		if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
			evt.consume();
		}
	}//IntInput end
	
	public void DblInput(char c, KeyEvent evt) {
		if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE || c == KeyEvent.VK_PERIOD || c == KeyEvent.VK_DECIMAL)) {
			evt.consume();
		}
	}//DblInput end
	
	//for multiple iterations? it would probably be better to just put it on a loop from the mainwindow tho
	public void reset()
	{
		itemNumber = 0;
		itemWeight = 1;
		itemStock = 0;
		itemRestock = 0;
		itemName = "";
		itemPrice = "0.00";
		found = false;
	}//reset end
}//ScanWindow end
