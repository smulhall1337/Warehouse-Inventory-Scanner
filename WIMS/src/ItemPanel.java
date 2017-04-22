import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

@SuppressWarnings("serial")
public class ItemPanel extends JPanel{
	private static JTextField txtItemNumber;
	private static JTextField txtItemQuantity;
	private static JButton btnCheck, btnEdit, btnDelete, btnAdd;
	private JLabel lblItemNumber, lblItemQuantity;
	private Dimension pref = new Dimension(270,100);

	public ItemPanel(){
		super();
		this.setPreferredSize(new Dimension(270, 120));
		this.setMinimumSize(pref);
		this.setMaximumSize(pref);
		this.setBounds(10, 50, 175, 100);
		this.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.setLayout(null);
		createContents();
	}
	
	private void createContents() {
		lblItemNumber = new JLabel("Item ID:");
		lblItemNumber.setBounds(10, 22, 50, 14);
		this.add(lblItemNumber);
		
		lblItemQuantity = new JLabel("Quantity");
		lblItemQuantity.setBounds(10, 53, 53, 14);
		this.add(lblItemQuantity);
		
		txtItemNumber = new JTextField();
		txtItemNumber.setEditable(false);
		txtItemNumber.setBounds(70, 19, 95, 20);		
		txtItemNumber.setColumns(10);
		this.add(txtItemNumber);
		txtItemNumber.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent evt) {
				btnCheck.setEnabled(true);
				OrderWindow.getFrame().getRootPane().setDefaultButton(btnCheck);
			}
		});
		txtItemNumber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				Valid.intInput(evt.getKeyChar(), evt);
			}
		});
		
		txtItemQuantity = new JTextField();
		txtItemQuantity.setEditable(false);		
		txtItemQuantity.setBounds(105, 50, 60, 20);		
		txtItemQuantity.setColumns(10);
		this.add(txtItemQuantity);
		txtItemQuantity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				Valid.intInput(evt.getKeyChar(), evt);
			}
		});
		
		btnCheck = new JButton("Check");
		btnCheck.setEnabled(false);
		btnCheck.setBounds(175, 18, 75, 23);
		add(btnCheck);
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemNumber = getItemNumber();
				searchDB(itemNumber);				
			}
		});
		
		btnDelete = new JButton("Delete");	
		btnDelete.setEnabled(false);
		btnDelete.setBounds(175, 86, 75, 23);
		this.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//remove selected item from list
			}
		});
				
		btnEdit = new JButton("Edit");
		btnEdit.setEnabled(false);
		btnEdit.setBounds(10, 86, 70, 23);
		this.add(btnEdit);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//call ScanWindow on currently selected item
			}
		});
		
		btnAdd = new JButton("Add");
		btnAdd.setVisible(false);
		btnAdd.setBounds(175, 49, 75, 23);
		this.add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//check if txtItemQuantity is good
				//if so add the item to the list below
				//if so add the fields here to a new itemList
				//if so add the itemList to the AllItems
			}
		});
		
		
		
		
	}//create contents end
	
	/** 
	 * @param itemNumber
	 * @return boolean found or not depending on if the item is in the database
	 */
	public void searchDB(String itemNumber) {
		if (itemNumber.equals(""))
			return;
		boolean found = false;
		try {
			found = SQL_Handler.itemInDB(itemNumber);	//try to find the item
		} catch (SQLException e1) {//let user know there was an error
			JOptionPane.showMessageDialog(this.getParent(), "An error occurred trying to reach the database, \nPlease try again");			
		}
		if (found) {  //if the item is found
			txtItemQuantity.setEditable(true);
			txtItemQuantity.requestFocus(); //setfocus to itemQuantity
			btnAdd.setVisible(true);
		}
		else { //otherwise ask them if they want to add that item
			askAddItem(itemNumber);
		}
	}
	
	/**
	 * A method that allows the user to enter a new item into the database from the Orders window
	 * @param itemNumber
	 */
	public void askAddItem(String itemNumber) {
		int n = JOptionPane.showConfirmDialog(
				    this.getParent(),
				    "This Item is not currently in the inventory, would you like to add it?",
				    "Add this Item?",
				    JOptionPane.YES_NO_OPTION);
		System.out.println(n);
		if (n == 0) { //yes response
			ScanWindow scanWindow = new ScanWindow(OrderWindow.getManagement(), itemNumber);
			scanWindow.getFrame().setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(this.getParent(), "Please enter a known item number or add that item to the inventory");	
		}
		
	}
	
	/**
	 * Getter method for txtItemNumber
	 * @return User input as String
	 */
	public String getItemNumber() {
		String itemNumberInput = txtItemNumber.getText();
		if (Valid.validItemNumber(itemNumberInput))
			return itemNumberInput;
		else
			return "";
	}
	
	/**
	 * Getter method for txtItemQuantity
	 * @return User input as an integer, defaults to 0 if the entry cannot be converted
	 */
	public int getItemQuantity() {
		String itemQuantityInput = txtItemQuantity.getText();
		if (Valid.validInt(itemQuantityInput))
			return Integer.parseInt(itemQuantityInput);
		else
			return 0;
	}
	
	
	public static void enableCheck() {
		btnCheck.setEnabled(true);
	}
	
	public static void disableCheck() {
		btnCheck.setEnabled(false);
	}
	
	public static void enableAdd() {
		btnCheck.setEnabled(true);
	}
	
	public static void disableAdd() {
		btnCheck.setEnabled(false);
	}
	
	public static void enableEdit() {
		btnEdit.setEnabled(true);
	}
	
	public static void disableEdit() {
		btnEdit.setEnabled(false);
	}
	
	public static void enableDelete() {
		btnDelete.setEnabled(true);
	}
	
	public static void disableDelete() {
		btnDelete.setEnabled(true);
	}
	
	public static void enableTxtItemNumber() {
		txtItemNumber.setEditable(true);
	}
	
	public static void disableTxtItemNumber() {
		txtItemNumber.setEditable(false);
	}
	
	public static void enableTxtItemQuantity() {
		txtItemQuantity.setEditable(true);
	}
	
	public static void disableTxtItemQuantity() {
		txtItemQuantity.setEditable(false);
	}
	
	
	
}//Class end
// TODO delete item from list
// TODO delete should only be available if the boxes are empty?