import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class OrderPanel extends JPanel {
	private JTextField txtDestination, txtOrigin, txtOrderNumber;
	private JButton btnCheck;
	private static Dimension pref = new Dimension(350, 120);
	private JButton btnSearchAgain;

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
		if (orderNumber.equals(""))
			return;
		boolean found = false;
		try {
			found = SQL_Handler.OrderInDB(orderNumber);	//try to find the item
		} catch (SQLException e1) {//let user know there was an error
			JOptionPane.showMessageDialog(this.getParent(), "An error occurred trying to reach the database, \nPlease try again");			
		}
		if (found) {  //if the order is found 
			// TODO fill out the Lists with info
			
			
			OrderWindow.getFrame().setTitle("View Existing Order");
		}
		else { //otherwise move focus to Origin and allow palletID to be modified
			btnSearchAgain.setVisible(true);
			btnCheck.setVisible(false);
			txtOrderNumber.setEditable(false);
			txtOrigin.setEditable(true);
			txtDestination.setEditable(true);
			txtOrigin.requestFocus();
			OrderWindow.getCurrentPalletPanel().enableTxt();
			OrderWindow.getFrame().setTitle("Create A New Order");
		}
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

}//class end
