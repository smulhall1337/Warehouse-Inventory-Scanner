package gui;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import controller.FocusGrabber;
import controller.Order;
import controller.SQL_Handler;
import controller.Valid;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;

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
	private Order currentOrder;
	private Component source;

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
		
		lblItemQuantity = new JLabel("Quantity:");
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
				btnAdd.setEnabled(false);
				if (source instanceof OrderWindow)
					OrderWindow.getFrame().getRootPane().setDefaultButton(btnCheck);
				else 
					SublocationWindow.getFrame().getRootPane().setDefaultButton(btnCheck);
			}
		});
		txtItemNumber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				Valid.intInput(evt.getKeyChar(), evt);
			}
		});
		source = txtItemNumber.getParent().getParent();
		
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
		txtItemQuantity.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				btnAdd.setEnabled(true);
				if (source instanceof OrderWindow)
					OrderWindow.getFrame().getRootPane().setDefaultButton(btnAdd);
				else 
					SublocationWindow.getFrame().getRootPane().setDefaultButton(btnAdd);
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
				boolean ism;
				Component source = txtItemNumber.getParent().getParent().getParent();
				if (source instanceof OrderWindow)
					ism = OrderWindow.getManagement();
				else 
					ism = SublocationWindow.getManagement();				 
				String currentItemNumber = ItemsInPalletPanel.getCurrentList().getSelectedValue().toString();
				ScanWindow scanWindow = new ScanWindow(ism, currentItemNumber);
				scanWindow.getFrame().setVisible(true);
			}
		});
		
		btnAdd = new JButton("Add");
		btnAdd.setVisible(false);
		btnAdd.setBounds(175, 49, 75, 23);
		this.add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String itemID = txtItemNumber.getText();
				String itemQuantity = txtItemQuantity.getText();
				int itemQty = Integer.parseInt(itemQuantity);
				boolean allGood = Valid.validInt(itemID);
				if (allGood) { //the itemNumber has already been verified to turn this button on so if the itemQty is okay					
					if (source instanceof OrderWindow)
						OrderWindow.addItemToJList(itemID, itemQty);
					else 
						SublocationWindow.addItemToJList(itemID, itemQty);					
					int index = PalletPanel.getSelectedPalletIndex();
					//OrderWindow.getPalletList().get(index).addItem(itemID, itemQty); //add the item to the pallet selected on the palletList TODO 	
					txtItemNumber.setText("");
					txtItemNumber.requestFocus();
					txtItemQuantity.setText("");
					txtItemQuantity.setEditable(false);
				}
				 
				//if so add the fields here to a new itemList
				//if so add the itemList to the AllItems
			}
		});
		
		
		
		
	}//create contents end
	
	
	/*
	 ********************************************************** 
	 ***************************************************Search
	 **********************************************************
	 */
	/** 
	 * Searches the DataBase for the itemNumber entered in the parameter.
	 * If the entry is blank, No action is taken.
	 * An error message is shown to the user if the database is unreachable.
	 * If the item is already known, the focus is set to the itemQuantity field and the Add button is shown.
	 * If the item is not known, the user is prompted to add this new item number to the database,
	 * A YES response is followed by opening the scan window while a NO response is followed by asking the user to reinput the value
	 * @param itemNumber
	 * @return boolean found or not depending on if the item is in the database
	 */
	public void searchDB(String itemNumber) {
		if (itemNumber.equals(""))
			return;
		if (!Valid.notInCurrentList(itemNumber, ItemsInPalletPanel.getListModel())) {
			String message = "That Item has already been added to this Pallet";
			if (source instanceof OrderWindow)
				JOptionPane.showMessageDialog(OrderWindow.getFrame(), message);
			else
				JOptionPane.showMessageDialog(SublocationWindow.getFrame(), message);			
		}
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
		btnAdd.setEnabled(true);
	}
	
	/**
	 * A method that allows the user to enter a new item into the database from the Orders window
	 * @param itemNumber
	 */
	public void askAddItem(String itemNumber) {
		int n = JOptionPane.showConfirmDialog(this.getParent(), "This Item is not currently in the inventory, would you like to add it?", "Add this Item?", JOptionPane.YES_NO_OPTION);
		System.out.println(n);
		if (n == 0) { //yes response
			ScanWindow scanWindow = new ScanWindow(OrderWindow.getManagement(), itemNumber);
			scanWindow.getFrame().setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(this.getParent(), "Please enter a known item number or add that item to the inventory");	
			SwingUtilities.invokeLater(new FocusGrabber(txtItemNumber));
		}
		
	}//askAddItem end
	
	
	
	/*
	 ********************************************************** 
	 ***************************************************Getters
	 **********************************************************
	 */
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
	
	
	/*
	 ********************************************************** 
	 ****************************************Enable and Disable 
	 ********************************************************** 
	 *Allow other sections of the OrderWindow to enable and disable 
	 *the fields and buttons as needed
	 */

	
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
		txtItemNumber.requestFocus();
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
	
	public void setCurrentOrder(Order currentOrder) {
		this.currentOrder = currentOrder;
	}

	
}//Class end
// TODO delete item from list
// TODO delete should only be available if the boxes are empty?