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

import controller.Order;
import controller.Pallet;
import controller.SQL_Handler;
import controller.SubLocation;
import controller.Valid;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JComboBox;

public class PalletPanel extends JPanel {

	private Container itemCont = new Container();
	private Dimension pref = new Dimension(350, 100);
	private boolean found;
	private JTextField txtPalletID;
	private JButton btnView, btnAdd, btnDelete;
	private JLabel lblPalletID, lblSubLocation;
	private static JComboBox cbSubLocation;
	private Order currentOrder;
	private Component source;

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
		source = lblPalletID.getParent().getParent();
		
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
				if (!OrderWindow.getFoundOrder()) //and not found
					populateSubLocationCB();	//populate the combobox when you focus on pallet id
				
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				//probably want to try to disable add when lose focus but doing
				//btnAdd.setEnabled(false);
				//stops the whole ActionListener
				if (txtPalletID.equals("")) { //if the palletID field is empty clear sublocation list
					btnAdd.setEnabled(false);
				}
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
				//TODO handle clicking this with nothing selected
				PalletWindow palletWindow = new PalletWindow((String) PalletsInOrderPanel.getCurrentList().getSelectedValue().toString());
			}
		});

		btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		btnDelete.setBounds(251, 66, 89, 23);
		this.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(getParent(), "Delete Pallet ID " + getSelectedPalletID() + " from this order? \nThis will remove its items too.");
				if (n == 0) { //if yes Delete
					//TODO remove selected pallet
					int index = PalletsInOrderPanel.getCurrentList().getSelectedIndex();
					if (index != -1) {
						Pallet p = (Pallet) PalletsInOrderPanel.getCurrentList().getSelectedValue();
						SubLocation sublocation = p.getSubLocation();
							if (sublocation.decrementCurrent()) {											//if you are able to increase the current count on the sub location so it cant be under 0 when creating the order								
							    PalletsInOrderPanel.getListModel().remove(index);
							    OrderWindow.getPalletList().remove(index);							    
							    ItemsInPalletPanel.getListModel().clear();	//clear item list model
							}
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
					
					if (!OrderWindow.getFoundOrder()) { //if the order isnt new
						btnView.setEnabled(true);
						btnView.setVisible(true);
					}
					else { //if the order is new
						
					}//newOrderCheck end
				}
			}
		});
		//TODO custom renderer so that it shows only sublocationName not toString()
		
		

		itemCont.setLayout(new BoxLayout(itemCont, BoxLayout.Y_AXIS));
	}// createContents end

	public void searchDB(String palletID) {
		if (getPalletID().equals(""))															//if the field is empty do nothing
			return;
		try {																					//search for pallet in db
			found = SQL_Handler.palletInDB(palletID);
		} catch (SQLException e) {																//notify user if error occurs
			JOptionPane.showMessageDialog(this.getParent(), "An error occurred trying to reach the database, \nPlease try again.");
		}
		if (found) { 																			//if the palletID entered exists already dont let them do it
			JOptionPane.showMessageDialog(this.getParent(),	"A Pallet with that ID already exists in the inventory, \nPlease try again.");
		} else if (!Valid.notInCurrentList(palletID, PalletsInOrderPanel.getListModel())) { 	// if if is in the current list dont let them do it
			JOptionPane.showMessageDialog(this.getParent(), "A Pallet with that ID is already listed below, \nPlease try again.");
		} else {																				//if the palletID entered does not exist yet
			
			if (cbSubLocation.getSelectedIndex() != 0) {										//make sure a sublocation is selected		
				SubLocation sublocation = (SubLocation) cbSubLocation.getSelectedItem();		//get the selected sublocation from the combobox
				if (sublocation.incrementCurrent()) {											//if you are able to decrease the current count on the sub location so it cant be over filled when creating the order
					OrderWindow.addPalletToJList(palletID, sublocation);						//add the pallet to the list
				}													
						
			}
			else {																				//if no sublocation is selected tell the user they cant do that
				JOptionPane.showMessageDialog(OrderWindow.getFrame(), "Please Select a Sub Location from the list provided.");
			}			
			
		}
	}//searchDB end
	
	

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

	public static Pallet getSelectedPalletID() {
		return (Pallet) PalletsInOrderPanel.getCurrentList().getSelectedValue();
	}
	
	public static int getSelectedPalletIndex() {
		return PalletsInOrderPanel.getCurrentList().getSelectedIndex();
	}
	
	public void populateSubLocationCB() {
		ArrayList<String> subLocationsStringList = new ArrayList<String>();
		try {
			subLocationsStringList = SQL_Handler.getAvailableSubLocations();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(OrderWindow.getFrame(), "Error Finding Sublocations, \nPlease try again.");
			OrderWindow.getFrame().requestFocus(); 							//stops an unbreakable loop if the sublocations cant be found
		}
		if (!subLocationsStringList.isEmpty()) { 							//if the list isnt empty
			cbSubLocation.addItem("SELECT A SUBLOCATION");					//add the first Selection
			for (int i = 0; i < subLocationsStringList.size(); i++) { 		//for each item in subLocations
				SubLocation s = createSub(subLocationsStringList.get(i));	//create a sublocation object from the list at the current index
				cbSubLocation.addItem(s);  	   								//add it to the combobox
				OrderWindow.getCurrentOrder().addToSubLocationList(s); 		//add it to the sublocationList too
				//TODO on the delete pallet button decrement the sublocation
			}
		}
	}//populateSubLocation
	
	
	/**
	 * Create a Sublocation Object using the locationCoordinate
	 * @param locationCoordinate
	 * @return
	 */
	public SubLocation createSub(String locationCoordinate) {
		SubLocation sL;
		String LC = locationCoordinate;
		int max = 0, current = 0;
		String wareHouse = "";
		
		try {
			max = SQL_Handler.getMaxFromSublocation(locationCoordinate);
			current = SQL_Handler.getCurrentFromSublocation(locationCoordinate);
			wareHouse = SQL_Handler.getWareHouseFromSublocation(locationCoordinate);			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		sL = new SubLocation(locationCoordinate, max, current, wareHouse);
		
		
		return sL;
	}
	
	
	public int getPalletIndex(String palletID) {
		int n = PalletsInOrderPanel.getCurrentList().getSelectedIndex();		
		return n;
	}
	
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
	
	public void setCurrentOrder(Order currentOrder) {
		this.currentOrder = currentOrder;
	}
}// Class end
