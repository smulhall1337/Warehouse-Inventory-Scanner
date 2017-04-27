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
import javax.swing.SwingUtilities;

import controller.FocusGrabber;
import controller.Order;
import controller.Pallet;
import controller.SQL_Handler;
import controller.SubLocation;
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
		txtSublocationName.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				SublocationWindow.getFrame().getRootPane().setDefaultButton(btnCheck);
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
		txtWarehouseID.setEditable(false);
		txtWarehouseID.setBounds(153, 90, 97, 20);
		add(txtWarehouseID);
		txtWarehouseID.setColumns(10);
		
		btnCheck = new JButton("Check");		
		btnCheck.setBounds(261, 14, 89, 23);
		this.add(btnCheck);	
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String LC = getLocationName();
				//currentOrder.setOrderNumber(orderNumber);
				searchDB(LC);					
			}
		});
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(283, 91, 57, 23);
		add(btnAdd);
		
		
	}//orderPanel() end
	
	/** 
	 * @param sublocationName
	 * @return boolean found or not depending on if the item is in the database
	 */
	public void searchDB(String LC) {
		String sublocationName = LC.toUpperCase();
		txtSublocationName.setText(sublocationName);
		if (sublocationName.equals(""))							//if the txtField is empty do nothing
			return;
		boolean found = false;
		try {
			found = SQL_Handler.SublocationInDB(sublocationName);		//try to find the sublocation
		} catch (SQLException e1) {										//let user know there was an error
			JOptionPane.showMessageDialog(this.getParent(), "An error occurred trying to reach the database, \nPlease try again");			
		}
		if (found) {  													//if the sublocation is found 			
			try {
				int current = SQL_Handler.getCurrentFromSublocation(sublocationName);  //get its info
				int max = SQL_Handler.getMaxFromSublocation(sublocationName);
				int index = SQL_Handler.getSimpleSubloIndex(sublocationName);
				String warehouseID = SQL_Handler.getWareHouseFromSublocation(sublocationName);				
				SublocationWindow.setCurrentSublocation(sublocationName, max, current, warehouseID); //set the current sublocation to this
				SublocationWindow.setFoundSublocation(true);										 //let the sublocationWindow know its found
				SubLocation sub = SublocationWindow.getCurrentSublocation();
				postSearch();
				txtCurrent.setText("" + current);
				txtMax.setText("" + max);    														//set the three text fields
				txtWarehouseID.setText("" + warehouseID);
				ArrayList<Pallet> PalletList = SQL_Handler.getPalletsBySublocation(index, sub);		//get all the pallets with that sublocation and add them to the list
				for (Pallet temp : PalletList) {
					SublocationWindow.addPalletToJList(temp.getID(), temp.getSubLocation());					
				}
				SublocationWindow.getFrame().setTitle("View Existing Sublocation");					//set the title of the frame
			} catch (SQLException r) {
				JOptionPane.showMessageDialog(SublocationWindow.getFrame(), "Error connecting to the database, \nPlease try again");
			}//try catch end			
		}
		else { 												//otherwise move focus to current and allow palletID to be modified
			
			postSearch();
			SublocationWindow.getCurrentPalletPanel().enableTxt();
			SublocationWindow.getFrame().setTitle("Create A New Sublocation");
		}  
		
	}//searchDB end
	
	public void postSearch() {
		//btnSearchAgain.setVisible(true);    //not going to use rn		
		btnCheck.setVisible(false);
		txtSublocationName.setEditable(false);
		txtCurrent.setEditable(true);
		txtMax.setEditable(true);
		txtWarehouseID.setEditable(true);
		SwingUtilities.invokeLater(new FocusGrabber(txtCurrent));
	}
	
	
	/**
	 * Getter method for txtOrderSNumber
	 * @return User input as String
	 */
	public String getLocationName() {
		String LCInput = txtSublocationName.getText();
		if (Valid.validString(LCInput))
			return LCInput;
		else
			return "";
	}
	
	/**
	 * Getter method for txtOrigin
	 * @return User input as String
	 */
	public static String getCurrent() {
		String currentPalletInput= txtCurrent.getText();
		if (Valid.validString(currentPalletInput))
			return currentPalletInput;
		else
			return "";
	}
	
	/**
	 * Getter method for txtDestination
	 * @return User input as String
	 */
	public static String getMax() {
		String maxInput= txtMax.getText();
		if (Valid.validString(maxInput))
			return maxInput;
		else
			return "";
	}
	
	public static void setTxtLocationName(String LC) {
		txtSublocationName.setText(LC);
	}

	public static void clickCheck() {
		btnCheck.doClick();
	}
}
