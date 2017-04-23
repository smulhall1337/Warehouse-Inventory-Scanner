import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PalletWindow extends JFrame{

	private static final String NUMREGEX = "\\d+", FIRSTLINE = "Search for a different pallet id ",
			SECONDLINE = "WARNING: THIS WILL NOT SAVE ANY MODIFICATIONS THAT HAVEN'T BEEN UPDATED YET!";
	private int changed = 0;
	private JFrame frame;	
	private JLabel lblIOP, lblPalletID, lblItemNumber, lblItemCount, lblLocation;
	private JComboBox cbOnPallet;
	private JTextField txtPalletID, txtItemNumber, txtItemCount, txtLocation;
	private JButton btnExit, btnChange, btnS, btnUp, btnDown;
	private boolean found = false;
	private ArrayList<String> itemNumberList = new ArrayList<String>();
	private ArrayList<String> itemNameList = new ArrayList<String>();
	private ArrayList<Integer> itemCountList = new ArrayList<Integer>();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PalletWindow window = new PalletWindow();
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
	public PalletWindow() {
		initialize();
		SearchScreen();
	}
	
	public PalletWindow(String initialPalletID){
		initialize();
		SearchScreen();
		this.txtPalletID.setText(initialPalletID);
		this.btnS.doClick();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 350);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		lblPalletID = new JLabel("Pallet ID");
		lblPalletID.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPalletID.setBounds(35, 47, 45, 15);
		lblPalletID.setToolTipText("Please enter the pallet ID number using a BarCode Scanner or the key pad.");
		frame.getContentPane().add(lblPalletID);
		
		lblLocation = new JLabel("Pallet Location");
		lblLocation.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLocation.setBounds(35, 78, 79, 15);
		frame.getContentPane().add(lblLocation);
		
		lblIOP = new JLabel("Items on Pallet");
		lblIOP.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIOP.setBounds(35, 109, 82, 15);
		lblIOP.setToolTipText("A list of all items currently on the selected pallet.");
		frame.getContentPane().add(lblIOP);
		
		lblItemNumber = new JLabel("Item Number");
		lblItemNumber.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblItemNumber.setBounds(35, 140, 73, 15);
		lblItemNumber.setToolTipText("Item Number for currently selected item.");
		frame.getContentPane().add(lblItemNumber);
		
		lblItemCount = new JLabel("Item Count");
		lblItemCount.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblItemCount.setBounds(35, 180, 63, 15);
		lblItemCount.setToolTipText("Please enter or modify the count of the selected item on this pallet.");
		frame.getContentPane().add(lblItemCount);
		
		txtPalletID = new JTextField();
		txtPalletID.setTransferHandler(null); //prevent copy paste into the field
		txtPalletID.setBounds(160, 45, 199, 20);
		frame.getContentPane().add(txtPalletID);
		txtPalletID.setColumns(10);
		txtPalletID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				Valid.intInput(evt.getKeyChar(), evt);
			}
		});
		
		txtLocation = new JTextField();
		txtLocation.setBounds(160, 76, 199, 20);
		frame.getContentPane().add(txtLocation);
		txtLocation.setColumns(10);
		
		txtItemNumber = new JTextField();
		txtItemNumber.setBounds(160, 138, 199, 20);
		frame.getContentPane().add(txtItemNumber);
		txtItemNumber.setColumns(10);
		
		txtItemCount = new JTextField();		
		txtItemCount.setTransferHandler(null); //prevent copy paste into the field
		txtItemCount.setBounds(160, 178, 100, 20);
		frame.getContentPane().add(txtItemCount);
		txtItemCount.setColumns(10);
		txtItemCount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				Valid.intInput(evt.getKeyChar(), evt);
			}
		});
		txtItemCount.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent evt) {
				if (!Valid.validString(txtItemCount.getText())) {
					int i = cbOnPallet.getSelectedIndex();
					String temp = Integer.toString(itemCountList.get(i));
					txtItemCount.setText(temp);
				}
			}
		});
		
		cbOnPallet = new JComboBox();		
		cbOnPallet.setBounds(160, 107, 199, 20);
		cbOnPallet.setToolTipText("WARNING: CHANGING SELECTIONS BEFORE UPDATING WILL NOT SAVE INFORMATION!");
		frame.getContentPane().add(cbOnPallet);
		cbOnPallet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (found)
					changed = 0;
					setFields();
			}
		});
		
		btnExit = new JButton("Exit");		
		btnExit.setBounds(35, 236, 89, 23);
		btnExit.setToolTipText("Exit this window");
		frame.getContentPane().add(btnExit);		
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		btnChange = new JButton("Change Pallet");		
		btnChange.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnChange.setBounds(134, 236, 126, 23);
		btnChange.setToolTipText("<html>" + FIRSTLINE + "<br>" + SECONDLINE);
		frame.getContentPane().add(btnChange);
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SearchScreen();
				clearEntries();				
			}
		});
		
		btnS = new JButton("S");		
		btnS.setBounds(270, 236, 89, 23);
		frame.getContentPane().add(btnS);
		frame.getRootPane().setDefaultButton(btnS);
		btnS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (found) {
					try {
						update();
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(frame, "Unable to Update Pallet");
						e1.printStackTrace();
					}
				}
				else {
					try {
						searchForPallet(txtPalletID.getText());
					} catch (SQLException e1) {						
						e1.printStackTrace();
					}						
				}//else end
			}//actionPerformed end
		});
		
		btnUp = new JButton("+");		
		btnUp.setBounds(318, 177, 41, 23);
		frame.getContentPane().add(btnUp);
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modifyCount("up");
			}
		});
		
		btnDown = new JButton("-");		
		btnDown.setBounds(270, 177, 41, 23);
		frame.getContentPane().add(btnDown);
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modifyCount("down");
			}
		});		
		
		
	}//initialize end
	
	//#############################################SQL Calls
	/**
	 * Calls SQL_Handler.palletInDB to search the database for the palletID passed in
	 * @param palletID String form of the PalletID
	 * @throws SQLException 
	 */
	private void searchForPallet(String palletID) throws SQLException {
		found = SQL_Handler.palletInDB(palletID); //check db for palletID
		if (found){
			populateLists(palletID);			
			EditScreen(); //change the screen
			txtLocation.setText(SQL_Handler.getPalletLocation(palletID));  
		}//if end
		else{ //otherwise tell user and reset the text field to try again
			JOptionPane.showMessageDialog(frame, "Pallet ID " + palletID + " is not found. Please try again or search a different Pallet ID.");
			txtPalletID.setText("");
			txtPalletID.requestFocus();
		}//else end
		for (String temp : itemNameList) { //for each item in itemNameList
			cbOnPallet.addItem(temp);  	   //add it to the combobox
		}
	}//searchForPallet end
	
	/**
	 * Calls SQL_Handler.getItemsOnPallet to populate itemNumberList.
	 * Using itemNumberList, set the same index of itemNameList 
	 * @throws SQLException 
	 */
	private void populateLists(String palletID) throws SQLException {
		itemNumberList = SQL_Handler.getItemsOnPallet(palletID);
		for (String temp : itemNumberList) {  //for each item on itemNumberList
			itemNameList.add(SQL_Handler.getItemName(temp)); //add itemName to itemNameList
			itemCountList.add(SQL_Handler.getItemCountOnPallet(palletID, temp)); //add itemCount to itemCountList
		}
		
	}
	
	private void update() throws SQLException {
		if (Valid.validString(txtItemCount.getText()) && Valid.validInt(txtItemCount.getText())) {
			int currentIndex = cbOnPallet.getSelectedIndex();
			int intItemCountEntry = Integer.parseInt(txtItemCount.getText());
			int totalPieceCount = 0;
			itemCountList.set(currentIndex, intItemCountEntry);
			for (int i : itemCountList) {
				totalPieceCount += i;
			}
			SQL_Handler.updateItemOnPallet(txtPalletID.getText(), txtItemNumber.getText(), Integer.parseInt(txtItemCount.getText()));
			SQL_Handler.updatePieceCount(txtPalletID.getText(), totalPieceCount);
			SQL_Handler.updateItemQtyByItemNum(changed, txtItemNumber.getText());
			JOptionPane.showMessageDialog(frame, "Updated " + cbOnPallet.getSelectedItem().toString() + ".");
			changed = 0;  //after you update changed needs to be zero because whatever is currently on screen is the zero
		}
		else {
			JOptionPane.showMessageDialog(frame, "Cannot Accept the input in the Item Count Field.");
		}
	}
	
	//#############################################SQL Calls end
	
	//#############################################Validation
	
	
	private void modifyCount(String modify) {
		int temp = Integer.parseInt(txtItemCount.getText());
		switch (modify) {
		case "up" : 
			temp++;
			changed++;
			break;
		case "down" : 
			temp--;	
			changed--;
			break;
		}
		if (temp < 0) { //cant go negative
			temp = 0;
		}
		txtItemCount.setText(Integer.toString(temp));
	}
	
	//#############################################Validation end
	
	//#############################################Screen Creation
	/**
	 * Using the currently selected itemName from the combo box, set the text fields for itemNumber and itemCount
	 */
	private void setFields() {
		if (!(cbOnPallet.getSelectedItem().equals(""))) {
			String selection = cbOnPallet.getSelectedItem().toString();
			int index = itemNameList.indexOf(selection);
			txtItemNumber.setText(itemNumberList.get(index));
			txtItemCount.setText(itemCountList.get(index).toString());
		}
	}	
	
	/**
	 * Reset txtPalletID and found before calling SearchScreen
	 */
	private void clearEntries() {
		txtPalletID.setText("");
		txtItemNumber.setText("");
		txtItemCount.setText("");
		found = false;
		itemNumberList.clear();
		itemNameList.clear();
		itemCountList.clear();
		cbOnPallet.removeAllItems();
		changed = 0;
	}
	
	private void SearchScreen() {
		frame.setTitle("Enter or Scan Pallet ID");
		lblIOP.setVisible(false);
		lblPalletID.setVisible(true);
		lblLocation.setVisible(false);
		lblItemNumber.setVisible(false);
		lblItemCount.setVisible(false);		
		cbOnPallet.setVisible(false);
		txtPalletID.setVisible(true);
		txtPalletID.requestFocus();
		txtPalletID.setEditable(true);
		txtLocation.setVisible(false);
		txtItemNumber.setVisible(false);
		txtItemCount.setVisible(false);
		btnChange.setVisible(false);
		btnS.setText("Search");
		btnS.setToolTipText("Search for pallet id number entered above");
		btnUp.setVisible(false);
		btnDown.setVisible(false);
	}//SearchScreen end
	
	private void EditScreen() {
		frame.setTitle("Edit Pallet ID " + txtPalletID.getText());
		lblIOP.setVisible(true);
		lblPalletID.setVisible(true);
		lblLocation.setVisible(true);
		lblItemNumber.setVisible(true);
		lblItemCount.setVisible(true);		
		cbOnPallet.setVisible(true);
		txtPalletID.setVisible(true);
		txtPalletID.setEditable(false);
		txtLocation.setVisible(true);
		txtLocation.setEditable(false);
		txtItemNumber.setVisible(true);
		txtItemNumber.setEditable(false);
		txtItemCount.setVisible(true);
		btnChange.setVisible(true);
		btnS.setText("Update");
		btnS.setToolTipText("Update current selection on current pallet");
		btnUp.setVisible(true);
		btnDown.setVisible(true);	
	}//EditScreen end
	
	public JFrame getFrame() {
		return this.frame;
	}
}//Pallet Window end


	/**
	 * TODO: 
	 * error in change pallet logic
	 * add notes section
	 */