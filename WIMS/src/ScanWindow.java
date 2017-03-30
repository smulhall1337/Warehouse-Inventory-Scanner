import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ScanWindow {

	private JFrame frame;

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
	}

	/**
	 * Create the application.
	 */
	public ScanWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JCheckBox chckbxCorrosive = new JCheckBox("Corrosive");
		chckbxCorrosive.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxCorrosive.setBounds(168, 202, 71, 23);
		frame.getContentPane().add(chckbxCorrosive);
		
		JCheckBox chckbxFragile = new JCheckBox("Fragile");
		chckbxFragile.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxFragile.setBounds(109, 202, 57, 23);
		frame.getContentPane().add(chckbxFragile);
		
		//item types
		JCheckBox chckbxFlam = new JCheckBox("Flammable");
		chckbxFlam.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxFlam.setBounds(25, 202, 75, 23);
		frame.getContentPane().add(chckbxFlam);		
		
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
		txtItemNumber.setBounds(168, 58, 175, 20);
		frame.getContentPane().add(txtItemNumber);
		txtItemNumber.setColumns(10);
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
		
		JTextField txtItemWeight = new JTextField();
		txtItemWeight.setBounds(168, 138, 175, 20);
		frame.getContentPane().add(txtItemWeight);
		txtItemWeight.setColumns(10);
		txtItemWeight.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				DblInput(evt.getKeyChar(), evt);
			}
		});//txtItemWeight Listener end
		
		JTextField txtPrice = new JTextField();
		txtPrice.setEnabled(false);
		txtPrice.setBounds(168, 298, 175, 20);
		frame.getContentPane().add(txtPrice);
		txtPrice.setColumns(10);
		txtPrice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				DblInput(evt.getKeyChar(), evt);
			}
		});//txtPrice Listener end
		
		JTextField txtCurrentStock = new JTextField();
		txtCurrentStock.setEnabled(false);
		txtCurrentStock.setBounds(168, 338, 175, 20);
		frame.getContentPane().add(txtCurrentStock);
		txtCurrentStock.setColumns(10);
		txtCurrentStock.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				IntInput(evt.getKeyChar(), evt);
			}
		});//txtCurrentStock Listener end
		
		JTextField txtItemRestock = new JTextField();
		txtItemRestock.setEnabled(false);
		txtItemRestock.setBounds(168, 378, 175, 20);
		frame.getContentPane().add(txtItemRestock);
		txtItemRestock.setColumns(10);
		txtItemRestock.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				IntInput(evt.getKeyChar(), evt);
			}
		});//txtItemRestock Listener end
		
		// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~CheckBoxes~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		
		JCheckBox chckbxItemPrice = new JCheckBox("Item Price");
		chckbxItemPrice.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxItemPrice.setBounds(35, 296, 97, 23);
		frame.getContentPane().add(chckbxItemPrice);
		chckbxItemPrice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtPrice.setEnabled(chckbxItemPrice.isSelected()); //set txtPrice to Enabled if the checkbox is ticked
				if (chckbxItemPrice.isSelected() == false)
					txtPrice.setText("");
			}
		});//chckbxItemPrice Listener end
		
		JCheckBox chckbxCurrentStock = new JCheckBox("Current Stock");
		chckbxCurrentStock.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxCurrentStock.setBounds(35, 336, 101, 23);
		frame.getContentPane().add(chckbxCurrentStock);
		chckbxCurrentStock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtCurrentStock.setEnabled(chckbxCurrentStock.isSelected()); //set txtCurrentStock to Enabled if the checkbox is ticked
				if (chckbxCurrentStock.isSelected() == false)
					txtCurrentStock.setText("");
			}
		});//chckbxItemPrice Listener end
		
		JCheckBox chckbxRestockThreshold = new JCheckBox("Restock Threshold");
		chckbxRestockThreshold.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxRestockThreshold.setBounds(35, 376, 127, 23);
		frame.getContentPane().add(chckbxRestockThreshold);
		chckbxRestockThreshold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtItemRestock.setEnabled(chckbxRestockThreshold.isSelected()); //set txtCurrentStock to Enabled if the checkbox is ticked
				if (chckbxRestockThreshold.isSelected() == false)
					txtItemRestock.setText("");
			}
		});//chckbxItemPrice Listener end
		
		JCheckBox chckbxRadioactive = new JCheckBox("Radioactive");
		chckbxRadioactive.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxRadioactive.setBounds(25, 228, 81, 23);
		frame.getContentPane().add(chckbxRadioactive);
		
		JCheckBox chckbxOther = new JCheckBox("Other");
		chckbxOther.setFont(new Font("Tahoma", Font.PLAIN, 11));
		chckbxOther.setBounds(109, 228, 53, 23);
		frame.getContentPane().add(chckbxOther);
		
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Buttons~~~~~~~~~~~~~~~~~~~~~~~
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
				int cStock = 0, rStock = 0, weight;  //default values for unchecked boxes
				double price = 0.00;
				if (validDouble(txtItemWeight.getText()) && 
						validString(txtItemNumber.getText()) && validString(txtItemName.getText()) ) //if the first boxes inputs are valid
				{
					if (chckbxRestockThreshold.isSelected() && validString(txtItemRestock.getText()) )//if restock threshold button is checked and its not empty
					{
						rStock = Integer.parseInt(txtItemRestock.getText()); //set the reStock
					}
					if (chckbxCurrentStock.isSelected() && validString(txtCurrentStock.getText()) )//if current stock button is checked and its not empty
					{
						cStock = Integer.parseInt(txtCurrentStock.getText());  //set the currentStock
					}
					if (chckbxItemPrice.isSelected() && validDouble(txtItemRestock.getText()) )//if price button is checked and it has a double
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
				
			}
		});//btnSubmit end
		
		
	}//initialize end
	
	
	
	
	
	/**
	 * True if the string is not empty, false if the string is ""
	 * @param s
	 * @return boolean
	 */
	private boolean validString(String s)
	{
		if (!(s.equals("")))
		{
			return true;
		}
		else
		{
			System.out.println("Error: Empty String");
			return false;
		}
	}//validString end
	
	/**
	 * True if the string can be converted to a double
	 * @param s
	 * @return boolean
	 */
	private boolean validDouble(String s)
	{
		try 
		{
			Double.parseDouble(s);
			return true;
		} catch (Exception ee)
		{
			System.out.println("Error: String to Double conversion");
			return false;
		}
	}//validDouble end
	
	/**
	 * True if the string can be converted to an integer
	 * @param s
	 * @return boolean
	 */
	private boolean validInt(String s)
	{
		try 
		{
			Integer.parseInt(s);
			return true;
		} catch (Exception ee)
		{
			System.out.println("Error: String to Integer conversion");
			return false;
		}
	}//validDouble end
	
	/**
	 * Input checker that only accepts digits(and backspace/delete) as input
	 * @param c 
	 * @param evt
	 */
	public void IntInput(char c, KeyEvent evt)
	{
		if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE))
		{
			evt.consume();
		}
	}//validInt end
	
	public void DblInput(char c, KeyEvent evt)
	{
		if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE || c == KeyEvent.VK_PERIOD || c == KeyEvent.VK_DECIMAL))
		{
			evt.consume();
		}
	}
	
}
