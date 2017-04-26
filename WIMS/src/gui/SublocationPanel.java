package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Order;
import controller.Pallet;
import controller.SQL_Handler;
import controller.Valid;

public class SublocationPanel extends JPanel {

	private static JTextField txtMax, txtCurrent, txtSublocationName, txtWarehouseID;
	private static JButton btnCheck;
	private static Dimension pref = new Dimension(350, 120);
	private boolean found;

	/**
	 * Create the panel.
	 */
	public SublocationPanel() {
		setLayout(null);
		this.setPreferredSize(new Dimension(350, 120));
		this.setMinimumSize(pref);
		this.setMaximumSize(pref);
		
		JLabel lblSublocationName = new JLabel("Sublocation Name:");
		lblSublocationName.setBounds(10, 18, 97, 14);
		add(lblSublocationName);
		
		JLabel lblSublocationCurrent = new JLabel("Current Pallet Count:");
		lblSublocationCurrent.setBounds(10, 43, 108, 14);
		add(lblSublocationCurrent);
		
		JLabel lblmax = new JLabel("Max Pallet Count:");
		lblmax.setBounds(10, 68, 97, 14);
		add(lblmax);
		
		JLabel lblWarehouseId = new JLabel("Warehouse ID:");
		lblWarehouseId.setBounds(10, 93, 97, 14);
		add(lblWarehouseId);
		
		txtSublocationName = new JTextField();		
		txtSublocationName.setText("");
		txtSublocationName.setBounds(153, 15, 97, 20);
		this.add(txtSublocationName);
		txtSublocationName.setColumns(10);
		txtSublocationName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				Valid.intInput(evt.getKeyChar(), evt);
			}
		});
		txtSublocationName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				OrderWindow.getFrame().getRootPane().setDefaultButton(btnCheck);
			}
		});
		
		txtCurrent = new JTextField();
		txtCurrent.setEditable(false);
		txtCurrent.setBounds(153, 40, 97, 20);
		this.add(txtCurrent);
		txtCurrent.setColumns(10);
		
		txtMax = new JTextField();
		txtMax.setEditable(false);
		txtMax.setBounds(153, 65, 97, 20);
		this.add(txtMax);
		txtMax.setColumns(10);	
		
		txtWarehouseID = new JTextField();
		txtWarehouseID.setBounds(153, 90, 97, 20);
		add(txtWarehouseID);
		txtWarehouseID.setColumns(10);
		
		btnCheck = new JButton("Check");		
		btnCheck.setBounds(261, 14, 89, 23);
		this.add(btnCheck);	
		
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(283, 91, 57, 23);
		add(btnAdd);
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String orderNumber = getOrderNumber();
				//currentOrder.setOrderNumber(orderNumber);
				searchDB(orderNumber);					
			}
		});
		
	}//orderPanel() end
	
	/** 
	 * @param sublocationName
	 * @return boolean found or not depending on if the item is in the database
	 */
	public void searchDB(String sublocationName) {
		if (sublocationName.equals(""))							//if the txtField is empty do nothing
			return;
		boolean found = false;
		try {
			found = SQL_Handler.SublocationInDB(sublocationName);		//try to find the sublocation
		} catch (SQLException e1) {							//let user know there was an error
			JOptionPane.showMessageDialog(this.getParent(), "An error occurred trying to reach the database, \nPlease try again");			
		}
		if (found) {  										//if the order is found 
			// TODO fill out the Lists with info, SQL_Handler.allPalletsInOrder only returns one row with the rs			
			try {
				int current = SQL_Handler.getCurrentFromSublocation(sublocationName);
				int max = SQL_Handler.getMaxFromSublocation(sublocationName);
				OrderWindow.setFoundOrder(true);			
				postSearch();
				txtCurrent.setText("" + current);
				txtMax.setText("" + max);    //set the origin and destination fields
				ArrayList<Pallet> PalletList = SQL_Handler.allPalletsInOrder(sublocationName);
				for (Pallet temp : PalletList) {
					OrderWindow.addPalletToJList(temp.getID(), temp.getSubLocation());
					
				}
				OrderWindow.getFrame().setTitle("View Existing Order");
			} catch (SQLException r) {
				JOptionPane.showMessageDialog(OrderWindow.getFrame(), "Error connecting to the database, \nPlease try again");
			}//try catch end
		}
		else { 												//otherwise move focus to Origin and allow palletID to be modified
			
			postSearch();
			OrderWindow.getCurrentPalletPanel().enableTxt();
			OrderWindow.getFrame().setTitle("Create A New Order");
			
			OrderWindow.setCurrentOrder(sublocationName);    	//create the order object with the input			
		}  
	}//searchDB end
	
	public void postSearch() {
		//btnSearchAgain.setVisible(true);    //not going to use rn
		btnCheck.setVisible(false);
		txtSublocationName.setEditable(false);
		txtCurrent.setEditable(true);
		txtMax.setEditable(true);
		txtCurrent.requestFocus();
	}
	
	
	/**
	 * Getter method for txtOrderSNumber
	 * @return User input as String
	 */
	public String getOrderNumber() {
		String orderNumberInput = txtSublocationName.getText();
		if (Valid.validItemNumber(orderNumberInput))
			return orderNumberInput;
		else
			return "";
	}
	
	/**
	 * Getter method for txtOrigin
	 * @return User input as String
	 */
	public static String getOrigin() {
		String originInput= txtCurrent.getText();
		if (Valid.validString(originInput))
			return originInput;
		else
			return "";
	}
	
	/**
	 * Getter method for txtDestination
	 * @return User input as String
	 */
	public static String getDestination() {
		String destinationInput= txtMax.getText();
		if (Valid.validString(destinationInput))
			return destinationInput;
		else
			return "";
	}
	
	public static void setTxtOrderNumber(String orderNumber) {
		txtSublocationName.setText(orderNumber);
	}

	public static void clickCheck() {
		btnCheck.doClick();
	}
}
