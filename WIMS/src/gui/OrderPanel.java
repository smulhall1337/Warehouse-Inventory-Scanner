package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controller.Order;
import controller.Pallet;
import controller.SQL_Handler;
import controller.Valid;

import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class OrderPanel extends JPanel {
	private static JTextField txtDestination;
	private static JTextField txtOrigin;
	private static JTextField txtOrderNumber;
	private static JButton btnCheck;
	private static Dimension pref = new Dimension(350, 120);
	private JButton btnSearchAgain;
	private Order currentOrder;

	/**
	 * Create the panel.
	 */
	public OrderPanel() {
		setLayout(null);
		this.setPreferredSize(new Dimension(350, 120));
		this.setMinimumSize(pref);
		this.setMaximumSize(pref);
		
		JLabel lblOrderId = new JLabel("Order ID:");
		lblOrderId.setBounds(10, 22, 58, 14);
		add(lblOrderId);
		
		JLabel lblOrigin = new JLabel("Origin:");
		lblOrigin.setBounds(10, 47, 58, 14);
		add(lblOrigin);
		
		JLabel lblDestination = new JLabel("Destination:");
		lblDestination.setBounds(10, 72, 80, 14);
		add(lblDestination);
		
		txtOrderNumber = new JTextField();		
		txtOrderNumber.setText("");
		txtOrderNumber.setBounds(100, 19, 150, 20);
		this.add(txtOrderNumber);
		txtOrderNumber.setColumns(10);
		txtOrderNumber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				Valid.intInput(evt.getKeyChar(), evt);
			}
		});
		txtOrderNumber.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				OrderWindow.getFrame().getRootPane().setDefaultButton(btnCheck);
			}
		});
		
		txtOrigin = new JTextField();
		txtOrigin.setEditable(false);
		txtOrigin.setBounds(100, 44, 150, 20);
		this.add(txtOrigin);
		txtOrigin.setColumns(10);
		
		txtDestination = new JTextField();
		txtDestination.setEditable(false);
		txtDestination.setBounds(100, 69, 150, 20);
		this.add(txtDestination);
		txtDestination.setColumns(10);	
		
		
		btnCheck = new JButton("Check");		
		btnCheck.setBounds(260, 18, 89, 23);
		this.add(btnCheck);
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String orderNumber = getOrderNumber();
				//currentOrder.setOrderNumber(orderNumber);
				searchDB(orderNumber);					
			}
		});
		
		btnSearchAgain = new JButton("Search Again");
		btnSearchAgain.setVisible(false);
		btnSearchAgain.setBounds(239, 97, 110, 23);
		this.add(btnSearchAgain);
		btnSearchAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//CLEAR EVERYTHING AND START OVER			
			}
		});
		
	}//orderPanel() end
	
	/** 
	 * @param orderNumber
	 * @return boolean found or not depending on if the item is in the database
	 */
	public void searchDB(String orderNumber) {
		if (orderNumber.equals(""))							//if the txtField is empty do nothing
			return;
		boolean found = false;
		try {
			found = SQL_Handler.OrderInDB(orderNumber);		//try to find the item
		} catch (SQLException e1) {							//let user know there was an error
			JOptionPane.showMessageDialog(this.getParent(), "An error occurred trying to reach the database, \nPlease try again");			
		}
		if (found) {  										//if the order is found 
			// TODO fill out the Lists with info, SQL_Handler.allPalletsInOrder only returns one row with the rs			
			try {
				String o = SQL_Handler.getOrderOrigin(orderNumber);
				String d = SQL_Handler.getOrderDestination(orderNumber);
				OrderWindow.setCurrentOrder(orderNumber);
				OrderWindow.setFoundOrder(true);			
				postSearch();
				txtOrigin.setText(o);
				txtDestination.setText(d);    //set the origin and destination fields
				ArrayList<Pallet> PalletList = SQL_Handler.allPalletsInOrder(orderNumber);
				for (Pallet temp : PalletList) {
					OrderWindow.addPalletToJList(temp.getID(), temp.getSubLocation());
					
				}
				OrderWindow.getFrame().setTitle("View Existing Order");
				OrderWindow.disableCreate();
				OrderWindow.enableView();
			} catch (SQLException r) {
				JOptionPane.showMessageDialog(OrderWindow.getFrame(), "Error connecting to the database, \nPlease try again");
			}//try catch end
		}
		else { 												//otherwise move focus to Origin and allow palletID to be modified
			
			postSearch();
			OrderWindow.getCurrentPalletPanel().enableTxt();
			OrderWindow.getFrame().setTitle("Create A New Order");
			OrderWindow.enableCreate();
			OrderWindow.disableView();
			OrderWindow.setCurrentOrder(orderNumber);    	//create the order object with the input			
		}  
	}//searchDB end
	
	public void postSearch() {
		//btnSearchAgain.setVisible(true);    //not going to use rn
		btnCheck.setVisible(false);
		txtOrderNumber.setEditable(false);
		txtOrigin.setEditable(true);
		txtDestination.setEditable(true);
		txtOrigin.requestFocus();
	}
	
	
	/**
	 * Getter method for txtOrderSNumber
	 * @return User input as String
	 */
	public String getOrderNumber() {
		String orderNumberInput = txtOrderNumber.getText();
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
		String originInput= txtOrigin.getText();
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
		String destinationInput= txtDestination.getText();
		if (Valid.validString(destinationInput))
			return destinationInput;
		else
			return "";
	}
	
	public static void setTxtOrderNumber(String orderNumber) {
		txtOrderNumber.setText(orderNumber);
	}

	public static void clickCheck() {
		btnCheck.doClick();
	}
}//class end
