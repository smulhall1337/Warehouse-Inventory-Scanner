package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import controller.SQL_Handler;
import controller.Valid;

import java.awt.Component;
import java.awt.event.KeyAdapter;

public class PalletPanel extends JPanel {

	private static final int ARRAYWIDTH = 2;
	private String[][] palletList;
	private Container itemCont = new Container();
	private Dimension pref = new Dimension(275,300);
	private JTextField txtPalletID;
	private JButton btnAddItem, btnCheck;
	private boolean found;
	
	// TODO Scrolling is messed up ask Paul about it
	
	/**
	 * Create the panel.
	 */
	public PalletPanel() {
		super();
		this.setBounds(10, 50, 275, 300);
		this.setBorder(new TitledBorder(null, "Pallet", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(pref);
		
		itemCont.setLayout(new BoxLayout(itemCont, BoxLayout.Y_AXIS));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		this.add(topPanel);
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		this.add(centerPanel);
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		this.add(bottomPanel);
		
		//TOP SECTION
		JLabel lblPalletID = new JLabel("Pallet ID: ");
		txtPalletID = new JTextField();
		txtPalletID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				Valid.intInput(evt.getKeyChar(), evt);
			}
		});
		txtPalletID.setColumns(15);	
		btnCheck = new JButton("Check");
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = txtPalletID.getText();
				if (Valid.validItemNumber(input)) {
					try {
						found = SQL_Handler.palletInDB(input);
					} catch (Exception ee) {
						JOptionPane.showMessageDialog(centerPanel,
								"Could not connect the database, \nPlease check the connection and try again");
					}
					if (found) { //if a pallet with that id is already in the data base
						JOptionPane.showInternalMessageDialog(centerPanel,
								"A pallet with that ID already exists, \nPlease try a different ID Number");
						txtPalletID.setText("");
						txtPalletID.requestFocus();
					} 
					else {
						btnAddItem.setEnabled(true);
						btnCheck.setEnabled(false);
						txtPalletID.setEditable(false);
					}//found end
				}//validID end
				else {
					JOptionPane.showInternalMessageDialog(centerPanel,
							"Nothing was entered, \nPlease try an actual ID Number");
				}
			}//actionPerformed end
		});//btnCheck Listener end
		
		topPanel.add(lblPalletID);
		topPanel.add(txtPalletID);
		topPanel.add(btnCheck);
		
		
		//MIDDLE SECTION
		JScrollPane scrollPane = new JScrollPane();
		centerPanel.add(scrollPane, BorderLayout.NORTH);		
		
		//BOTTOM SECTION
		btnAddItem = new JButton("Add Additional Item");
		btnAddItem.setEnabled(false);
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemCont.add(new ItemPanel());				
				scrollPane.setViewportView(itemCont);
			}
		});
		bottomPanel.add(btnAddItem);
		//setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtPalletID, scrollPane, topPanel}));
	}
	
	/**
	 * Gets all the items from the ItemPanels of this PalletPanel
	 * @return
	 */
	public ArrayList<String> getAllItems() {
		ArrayList<String> itemList = new ArrayList<String>();							//this is what you return
		String temp = "";  									  							//temp to hold the string to add
		for (Component c : itemCont.getComponents()) {		  							//for each component in the container holding ItemPanels
			if (c instanceof JTextField && c.getName().equals("txtItemNumber")) {		//if the selected Component is a JTextField and txtItemNumber
				temp = ((ItemPanel) c).getItemNumber();		  							//set temp to txtItemNumber.getText() and add it to the list
				if (!(temp.equals("")))													//as long as its not empty
					itemList.add(temp);
			}
		}
		return itemList;
	}
	
	/**
	 * Gets all the item quantities from the ItemPanels of this PalletPanel
	 * @return
	 */
	public ArrayList<String> getAllItemQuantities() {
		ArrayList<String> itemQList = new ArrayList<String>();							//this is what you return
		String temp = "";  									  							//temp to hold the string to add
		for (Component c : itemCont.getComponents()) {		  							//for each component in the container holding ItemPanels
			if (c instanceof JTextField && c.getName().equals("txtItemQuantity")) {		//if the selected Component is a JTextField and txtItemQuantity
				temp = ((ItemPanel) c).getItemNumber();		  							//set temp to txtItemQuantity.getText() and add it to the list
				if (!(temp.equals("")))													//as long as its not empty
					itemQList.add(temp);
			}
		}
		return itemQList;
	}
	
	public void createPalletList() {
		ArrayList<String> itemList = getAllItems();
		ArrayList<String> itemQList = getAllItemQuantities();
		
		if (itemList.size() != itemQList.size()) {  //if the two are not equal sizes DO NOT CONTINUE THERE IS AN ERROR IN THE ENTRIES
			JOptionPane.showMessageDialog(this.getParent(), "WARNING! THE ITEM FIELDS MUST BE COMPLETELY FILLED OUT OR COMPLETELY BLANK \nPLEASE CHECK OVER YOUR ENTIRES");
			return;
		}
		
		for (int row = 0; row < itemList.size(); row++) {
			for (int col = 0; col < ARRAYWIDTH; col++) {
				if (col % 2 == 1) { //if the column is odd add itemList
					palletList[row][col] = itemList.get(row);
				}
				else {  //else add itemQList
					palletList[row][col] = itemQList.get(row);
				}
			}//for col end
		}//for row end
	}//createPalletList end
	
	public String[][] getPalletList() {
		return palletList;
	}
	

}//Class end