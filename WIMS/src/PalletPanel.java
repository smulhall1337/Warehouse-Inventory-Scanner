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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PalletPanel extends JPanel {

	private static final int ARRAYWIDTH = 2;
	private String[][] palletList;
	private Container itemCont = new Container();
	private Dimension pref = new Dimension(350, 75);
	private boolean found;
	private JTextField txtPalletID;
	private JButton btnView, btnAdd, btnEdit, btnDelete;
	private JLabel lblPalletID;

	/**
	 * Create the panel.
	 */
	public PalletPanel() {
		super();
		this.setPreferredSize(pref);
		this.setMinimumSize(pref);
		this.setMaximumSize(pref);
		this.setBounds(10, 50, 350, 60);
		this.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);
		createContents();
	}// PalletPanel() end

	public void createContents() {

		lblPalletID = new JLabel("Pallet ID:");
		lblPalletID.setBounds(10, 11, 55, 14);
		this.add(lblPalletID);

		txtPalletID = new JTextField();
		txtPalletID.setEditable(false);
		txtPalletID.setColumns(10);
		txtPalletID.setBounds(75, 8, 94, 20);
		this.add(txtPalletID);
		txtPalletID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				Valid.intInput(evt.getKeyChar(), evt);
			}
		});
		txtPalletID.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				btnAdd.setEnabled(true);
				OrderWindow.getFrame().getRootPane().setDefaultButton(btnAdd);
			}

			@Override
			public void focusLost(FocusEvent e) {
				//probably want to try to disable add when lose focus but doing
				//btnAdd.setEnabled(false);
				//stops the whole ActionListener	
			}
		});

		btnAdd = new JButton("Add");
		btnAdd.setEnabled(false);
		btnAdd.setBounds(179, 7, 75, 23);
		this.add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("palletPanel.add");
				searchDB(getPalletID());
				txtPalletID.requestFocusInWindow();
				txtPalletID.setText("");
			}
		});

		btnView = new JButton("View");
		btnView.setEnabled(false);
		btnView.setBounds(10, 41, 75, 23);
		this.add(btnView);
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("palletPanel.view");
				// TODO populate item list with this pallet's info
			}
		});

		btnEdit = new JButton("Edit");
		btnEdit.setEnabled(false);
		btnEdit.setBounds(122, 41, 89, 23);
		this.add(btnEdit);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new PalletWindow(getSelectedPalletID());
			}
		});

		btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		btnDelete.setBounds(251, 41, 89, 23);
		this.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(OrderWindow.getFrame(), "Delete Pallet ID " + getSelectedPalletID() + " from this order? \nThis will remove its items too.");
				if (n == 0) { //if yes Delete
					//TODO remove selected pallet
					//TODO remove selected pallet's items
				}
			}
		});
		

		itemCont.setLayout(new BoxLayout(itemCont, BoxLayout.Y_AXIS));
	}// createContents end

	public void searchDB(String palletID) {
		if (getPalletID().equals(""))
			return;
		try {
			found = SQL_Handler.palletInDB(palletID);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this.getParent(),
					"An error occurred trying to reach the database, \nPlease try again.");
		}
		if (found) { // if the palletID entered exists already
			JOptionPane.showMessageDialog(this.getParent(),
					"A Pallet with that ID already exists in the inventory, \nPlease try again.");
		} else if (!Valid.notInCurrentList(palletID, PalletInOrderPanel.getListModel())) { // if if is in the current list
			JOptionPane.showMessageDialog(this.getParent(),
					"A Pallet with that ID is already listed below, \nPlease try again.");
		} else {
			PalletInOrderPanel.addPallet(palletID);
		}
	}

	/**
	 * Getter method for txtPalleID
	 * 
	 * @return User input as String
	 */
	public String getPalletID() {
		String palletIDInput = txtPalletID.getText();
		if (Valid.validItemNumber(palletIDInput))
			return palletIDInput;
		else
			return "";
	}

	public String getSelectedPalletID() {
		return (String) PalletInOrderPanel.getCurrentPalletList().getSelectedValue();
	}

	/**
	 * Gets all the items from the ItemPanels of this PalletPanel
	 * 
	 * @return
	 */
	public ArrayList<String> getAllItems() {
		ArrayList<String> itemList = new ArrayList<String>(); // this is what
																// you return
		String temp = ""; // temp to hold the string to add
		for (Component c : itemCont.getComponents()) { // for each component in
														// the container holding
														// ItemPanels
			if (c instanceof JTextField && c.getName().equals("txtItemNumber")) { // if
																					// the
																					// selected
																					// Component
																					// is
																					// a
																					// JTextField
																					// and
																					// txtItemNumber
				temp = ((ItemPanel) c).getItemNumber(); // set temp to
														// txtItemNumber.getText()
														// and add it to the
														// list
				if (!(temp.equals(""))) // as long as its not empty
					itemList.add(temp);
			}
		}
		return itemList;
	}

	/**
	 * Gets all the item quantities from the ItemPanels of this PalletPanel
	 * 
	 * @return
	 */
	public ArrayList<String> getAllItemQuantities() {
		ArrayList<String> itemQList = new ArrayList<String>(); // this is what
																// you return
		String temp = ""; // temp to hold the string to add
		for (Component c : itemCont.getComponents()) { // for each component in
														// the container holding
														// ItemPanels
			if (c instanceof JTextField && c.getName().equals("txtItemQuantity")) { // if
																					// the
																					// selected
																					// Component
																					// is
																					// a
																					// JTextField
																					// and
																					// txtItemQuantity
				temp = ((ItemPanel) c).getItemNumber(); // set temp to
														// txtItemQuantity.getText()
														// and add it to the
														// list
				if (!(temp.equals(""))) // as long as its not empty
					itemQList.add(temp);
			}
		}
		return itemQList;
	}

	public void createPalletList() {
		ArrayList<String> itemList = getAllItems();
		ArrayList<String> itemQList = getAllItemQuantities();

		if (itemList.size() != itemQList.size()) { // if the two are not equal
													// sizes DO NOT CONTINUE
													// THERE IS AN ERROR IN THE
													// ENTRIES
			JOptionPane.showMessageDialog(this.getParent(),
					"WARNING! THE ITEM FIELDS MUST BE COMPLETELY FILLED OUT OR COMPLETELY BLANK \nPLEASE CHECK OVER YOUR ENTIRES");
			return;
		}

		for (int row = 0; row < itemList.size(); row++) {
			for (int col = 0; col < ARRAYWIDTH; col++) {
				if (col % 2 == 1) { // if the column is odd add itemList
					palletList[row][col] = itemList.get(row);
				} else { // else add itemQList
					palletList[row][col] = itemQList.get(row);
				}
			} // for col end
		} // for row end
	}// createPalletList end

	public String[][] getPalletList() {
		return palletList;
	}

	public void enableAdd() {
		btnAdd.setEnabled(true);
	}

	public void disableAdd() {
		btnAdd.setEnabled(false);
	}

	public void enableView() {
		btnView.setEnabled(true);
	}

	public void disableView() {
		btnView.setEnabled(false);
	}

	public void enableEdit() {
		btnEdit.setEnabled(true);
	}

	public void disableEdit() {
		btnEdit.setEnabled(false);
	}

	public void enableDelete() {
		btnDelete.setEnabled(true);
	}

	public void disableDelete() {
		btnDelete.setEnabled(false);
	}

	public void enableTxt() {
		txtPalletID.setEditable(true);
	}

	public void disableTxt() {
		txtPalletID.setEditable(false);
	}
}// Class end
