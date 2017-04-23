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
import javax.swing.JComboBox;

public class PalletPanel extends JPanel {

	private static final int ARRAYWIDTH = 2;
	private String[][] palletList;
	private Container itemCont = new Container();
	private Dimension pref = new Dimension(350, 100);
	private boolean found;
	private JTextField txtPalletID;
	private JButton btnView, btnAdd, btnDelete;
	private JLabel lblPalletID, lblSubLocation;
	private static JComboBox cbSubLocation;

	/**
	 * Create the panel.
	 */
	public PalletPanel() {
		super();
		this.setPreferredSize(pref);
		this.setMinimumSize(pref);
		this.setMaximumSize(pref);
		this.setBounds(10, 50, 350, 100);
		this.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);
		createContents();
	}// PalletPanel() end

	public void createContents() {

		lblPalletID = new JLabel("Pallet ID:");
		lblPalletID.setBounds(10, 11, 55, 14);
		this.add(lblPalletID);
		
		lblSubLocation = new JLabel("Sublocation:");
		lblSubLocation.setBounds(10, 41, 75, 14);
		this.add(lblSubLocation);

		txtPalletID = new JTextField();
		txtPalletID.setEditable(false);
		txtPalletID.setColumns(10);
		txtPalletID.setBounds(75, 8, 180, 20);
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
				populateSubLocationCB();
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
		btnAdd.setBounds(265, 7, 75, 23);
		this.add(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("palletPanel.add"); //testing purposes delete later
				searchDB(getPalletID());
				txtPalletID.requestFocusInWindow();
				txtPalletID.setText("");
				btnDelete.setEnabled(true);
				cbSubLocation.setSelectedIndex(0);
			}
		});

		btnView = new JButton("View");
		btnView.setEnabled(false);
		btnView.setVisible(false);
		btnView.setBounds(10, 66, 75, 23);
		this.add(btnView);
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("palletPanel.view"); //testing purposes delete later
				PalletWindow palletWindow = new PalletWindow((String) PalletsInOrderPanel.getCurrentList().getSelectedValue());
			}
		});

		btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		btnDelete.setBounds(251, 66, 89, 23);
		this.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(OrderWindow.getFrame(), "Delete Pallet ID " + getSelectedPalletID() + " from this order? \nThis will remove its items too.");
				if (n == 0) { //if yes Delete
					//TODO remove selected pallet
					int index = PalletsInOrderPanel.getCurrentList().getSelectedIndex();
					if (index != -1) {
					    PalletsInOrderPanel.getListModel().remove(index);
					    OrderWindow.getPalletList().remove(index);
					}
					
					if (PalletsInOrderPanel.getListModel().isEmpty()) {
						btnDelete.setEnabled(false);
					}
				}
			}
		});
		
		cbSubLocation = new JComboBox();		
		cbSubLocation.setBounds(85, 39, 169, 20);
		this.add(cbSubLocation);
		cbSubLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (cbSubLocation.getSelectedIndex() != 0 && !(txtPalletID.equals(""))) { //if the selection isnt on the first in the box that says "SELECT ..."
					btnAdd.setEnabled(true);
					OrderWindow.getFrame().getRootPane().setDefaultButton(btnAdd);
					
					if (!OrderWindow.getFoundOrder()) {
						btnView.setEnabled(true);
						btnView.setVisible(true);
					}
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
		} else if (!Valid.notInCurrentList(palletID, PalletsInOrderPanel.getListModel())) { // if if is in the current list
			JOptionPane.showMessageDialog(this.getParent(),
					"A Pallet with that ID is already listed below, \nPlease try again.");
		} else {
			//make sure sublocation is selected
			String sublocation = (String) cbSubLocation.getSelectedItem();
			OrderWindow.addPalletToJList(palletID, sublocation);
			//OrderWindow.addID(palletID, PalletsInOrderPanel.getCurrentList(),PalletsInOrderPanel.getListModel());			
			
			
			
		}
	}//searchDB end
	
	public int getPalletIndex(String palletID) {
		int n = PalletsInOrderPanel.getCurrentList().getSelectedIndex();		
		return n;
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

	public static String getSelectedPalletID() {
		return (String) PalletsInOrderPanel.getCurrentList().getSelectedValue();
	}
	
	public static int getSelectedPalletIndex() {
		return PalletsInOrderPanel.getCurrentList().getSelectedIndex();
	}
	
	public void populateSubLocationCB() {
		ArrayList<String> subLocations = new ArrayList<String>();
		try {
			subLocations = SQL_Handler.getAvailableSubLocations();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(OrderWindow.getFrame(), "Error Finding Sublocations, \nPlease try again.");
		}
		if (!subLocations.isEmpty()) { //if the list isnt empty
			cbSubLocation.addItem("SELECT A SUBLOCATION");
			for (int i = 0; i < subLocations.size(); i++) { //for each item in subLocations
				cbSubLocation.addItem(subLocations.get(i));  	   //add it to the combobox				
				//TODO on the delete pallet button decrement the sublocation
			}
		}
	}//populateSubLocation
	
	
	
	/*
	 ********************************************************** 
	 ****************************************Enable and Disable
	 **********************************************************
	 */

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

	/*
	public void enableEdit() {
		btnEdit.setEnabled(true);
	}

	public void disableEdit() {
		btnEdit.setEnabled(false);
	} */

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
	
	public static int getCBSelectedIndex() {
		return cbSubLocation.getSelectedIndex();
	}
}// Class end
