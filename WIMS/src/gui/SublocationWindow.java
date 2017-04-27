package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.Item;
import controller.LabelFactory;
import controller.Order;
import controller.Pallet;
import controller.SQL_Handler;
import controller.SubLocation;

public class SublocationWindow {

	private static JFrame frame;
	private static final int LRHEIGHT = 400, LWIDTH = 375, RWIDTH = 290, BHEIGHT = 50, BWIDTH = LWIDTH + RWIDTH;
	private Dimension LSIZE = new Dimension(LWIDTH, LRHEIGHT), RSIZE = new Dimension(RWIDTH, LRHEIGHT), BSIZE = new Dimension(BWIDTH, BHEIGHT);
	private JPanel leftPanel = new JPanel();
	private	JPanel rightPanel = new JPanel();
	private	JPanel bottomPanel = new JPanel();
	private JButton btnExit = new JButton("Exit"), btnCreateOrder = new JButton("Create Order"), btnViewBarcodes = new JButton("View Barcodes");
	private JList palletJList = new JList(), itemJList = new JList();
	
	private static  SubLocation currentSublocation;
	
	private static boolean foundSublocation = false;
	private static boolean isM;
	
	//private static ArrayList<Pallet> palletList = new ArrayList<Pallet>();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SublocationWindow window = new SublocationWindow(true);  //i dont think this will ever run but if it does need to change it from default true
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
	public SublocationWindow(boolean isManagement) {
		this.isM = isManagement;
		initialize();
	}
	
	public SublocationWindow(boolean isManagement, String locationCoordinate) {
		this.isM = isManagement;
		initialize();
		SublocationPanel.setTxtLocationName(locationCoordinate);
		SublocationPanel.clickCheck();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 740, 479);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Enter Sublocation Name");
		frame.getContentPane().setLayout(null);
		leftPanel.setLocation(0, 0);
		leftPanel.setSize(400, 400);
		frame.getContentPane().add(leftPanel);
		rightPanel.setSize(325, 400);
		rightPanel.setLocation(400, 0);
		frame.getContentPane().add(rightPanel);
		bottomPanel.setSize(694, 50);
		bottomPanel.setLocation(0, 400);
		frame.getContentPane().add(bottomPanel);
		
		
		/*
		 *********************************************
		 **********************LEFT PANEL
		 *********************************************
		 */
		leftPanel.setMinimumSize(LSIZE);
		leftPanel.setMaximumSize(LSIZE);
		leftPanel.setPreferredSize(LSIZE);
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		SublocationPanel sublocationPanel = new SublocationPanel();
		leftPanel.add(sublocationPanel);
		PalletsInOrderPanel palletInOrderPanel = new PalletsInOrderPanel();
		palletInOrderPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		leftPanel.add(palletInOrderPanel);
		
		
		/*
		 *********************************************
		 **********************Right PANEL
		 *********************************************
		 */
		rightPanel.setMinimumSize(RSIZE);
		rightPanel.setMaximumSize(RSIZE);
		rightPanel.setPreferredSize(RSIZE);
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		ItemsInPalletPanel itemsInPalletPanel = new ItemsInPalletPanel();
		rightPanel.add(itemsInPalletPanel);
		
		
		/*
		 *********************************************
		 **********************Bottom PANEL
		 *********************************************
		 */
		bottomPanel.setMinimumSize(BSIZE);
		bottomPanel.setMaximumSize(BSIZE);
		bottomPanel.setPreferredSize(BSIZE);
		bottomPanel.setLayout(null);

		btnExit.setBounds(10, 16, 89, 23);
		bottomPanel.add(btnExit);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		
		frame.setVisible(true);
	}//initialize end
	
	/*
	 ************************************************************************ 
	 *************************************************************ListMethods
	 ************************************************************************ 
	 */
	
	
	public static void addPalletToJList(String palletID, SubLocation sublocation) {
		Pallet p = new Pallet(palletID, sublocation);							//create a new pallet object with palletID and sublocation
		int index = PalletsInOrderPanel.getCurrentList().getSelectedIndex();	//get the current Pallet selected index
		PalletsInOrderPanel.getListModel().addElement(p);						//add the new pallet to the list model
		PalletsInOrderPanel.getCurrentList().setSelectedIndex(index);
        PalletsInOrderPanel.getCurrentList().ensureIndexIsVisible(index);
        currentSublocation.getPalletList().add(p);									//add the new pallet to the Order Object
        
	}
	
	public static void addItemToJList(String itemNumber, int itemQuantity) {
		Item i = new Item(itemNumber, itemQuantity);
		int index = PalletsInOrderPanel.getCurrentList().getSelectedIndex();
		Pallet p = currentSublocation.getPalletList().get(index);
		p.addItem(i);
		ItemsInPalletPanel.getListModel().addElement(i);
		ItemsInPalletPanel.getCurrentList().setSelectedIndex(index);
		ItemsInPalletPanel.getCurrentList().ensureIndexIsVisible(index);
		//get currently selected pallet and add this item
	}
	
	/*
	 ************************************************************************ 
	 **********************************************************Getter Methods
	 ************************************************************************ 
	 */
	
	/**
	 * Get the management variable
	 * @return isM true for manager, false for employee
	 */
	public static boolean getManagement() {
		return isM;
	}
	
	/**
	 * Get if the order was found
	 * @return foundOrder true for existing order, false for new order
	 */
	public static boolean getFoundOrder() {
		return foundSublocation;
	}
	
	public static void setFoundSublocation(boolean f) {
		foundSublocation = f;
	}
	
	/**
	 * Get the frame
	 * @return OrderWindow frame
	 */
	public static JFrame getFrame() {
		return frame;
	}
	
	public static SubLocation getCurrentSublocation() {
		return currentSublocation;
	}
	
	public static void setCurrentSublocation(String id) {
		try {
			int current = SQL_Handler.getCurrentFromSublocation(id);
			int max = SQL_Handler.getMaxFromSublocation(id);
			int index = SQL_Handler.getSimpleSubloIndex(id);
			String warehouseID = SQL_Handler.getWareHouseFromSublocation(id);
			currentSublocation = new SubLocation(id, max, current, warehouseID);
		} catch (SQLException e) {
			
		}		
	}
	
	public static void setCurrentSublocation(String id, int max, int current, String warehouseID) {
		currentSublocation = new SubLocation(id, max, current, warehouseID);
	}
	
	/**
	 * Get the current PalletPanel section, used to call PalletPanel methods from other sections of the window
	 * @return PalletPanel section above the PalletList on the left
	 */
	public static PalletPanel getCurrentPalletPanel() {
		return PalletsInOrderPanel.getPalletPanel();
	}
	
	/**
	 * Get the current ItemPanel section, used to call ItemPanel methods from other sections of the window
	 * @return ItemPanel section above the ItemList on the right
	 */
	public static ItemPanel getCurrentItemPanel() {
		return ItemsInPalletPanel.getCurrentItemPanel();
	}
	

}
