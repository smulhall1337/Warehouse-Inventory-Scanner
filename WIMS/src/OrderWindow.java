import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class OrderWindow extends JFrame {

	private static JFrame frame;
	private static final int LRHEIGHT = 400, LWIDTH = 375, RWIDTH = 290, BHEIGHT = 50, BWIDTH = LWIDTH + RWIDTH;
	private Dimension LSIZE = new Dimension(LWIDTH, LRHEIGHT), RSIZE = new Dimension(RWIDTH, LRHEIGHT), BSIZE = new Dimension(BWIDTH, BHEIGHT);
	private JPanel leftPanel = new JPanel();
	private	JPanel rightPanel = new JPanel();
	private	JPanel bottomPanel = new JPanel();
	private JButton btnExit = new JButton("Exit"), btnCreateOrder = new JButton("Create Order"), btnViewBarcodes = new JButton("View Barcodes");
	private JList palletJList = new JList(), itemJList = new JList();
	
	private static Order currentOrder;
	
	private static boolean foundOrder = false;
	private static boolean isM;
	
	//private static ArrayList<Pallet> palletList = new ArrayList<Pallet>();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrderWindow window = new OrderWindow(true);  //i dont think this will ever run but if it does need to change it from default true
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
	public OrderWindow(boolean isManagement) {
		this.isM = isManagement;
		initialize();
	}
	
	public OrderWindow(boolean isManagement, String orderNumber) {
		this.isM = isManagement;
		initialize();
		OrderPanel.setTxtOrderNumber(orderNumber);
		OrderPanel.clickCheck();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 740, 479);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Enter Order Number");
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
		OrderPanel orderPanel = new OrderPanel();
		leftPanel.add(orderPanel);
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
		
		btnCreateOrder.setBounds(434, 16, 115, 23);
		bottomPanel.add(btnCreateOrder);
		btnCreateOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createWholeOrder();
			}
		});
		
		btnViewBarcodes.setBounds(559, 16, 125, 23);
		bottomPanel.add(btnViewBarcodes);
		btnViewBarcodes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//call Barcode stuff
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
        currentOrder.getPalletList().add(p);									//add the new pallet to the Order Object
        
	}
	
	public static void addItemToJList(String itemNumber, int itemQuantity) {
		Item i = new Item(itemNumber, itemQuantity);
		int index = PalletsInOrderPanel.getCurrentList().getSelectedIndex();
		Pallet p = currentOrder.getPalletList().get(index);
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
		return foundOrder;
	}
	
	public static void setFoundOrder(boolean f) {
		foundOrder = f;
	}
	
	/**
	 * Get the frame
	 * @return OrderWindow frame
	 */
	public static JFrame getFrame() {
		return frame;
	}
	
	public static Order getCurrentOrder() {
		return currentOrder;
	}
	
	public static void setCurrentOrder(String id) {
		currentOrder = new Order(id);
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
	
	public static ArrayList<Pallet> getPalletList() {
		return currentOrder.getPalletList();
	}
	
	
	
	/*
	 ************************************************************************ 
	 **********************************************************Create Order**
	 ************************************************************************ 
	 */
	public void createWholeOrder() {
				
		try {
			String receivedBy = "", shippedBy = "";
			java.sql.Date datePlaced = new java.sql.Date(Calendar.getInstance().getTime().getTime());		//setting necessary variables
			java.sql.Date dateShipped = datePlaced;
			java.sql.Date dateDelivered = datePlaced;
			
			SQL_Handler.insertNewOrder(currentOrder.getOrderNumber(), OrderPanel.getOrigin(), OrderPanel.getDestination(), receivedBy, shippedBy, datePlaced, dateShipped, dateDelivered);  //create order in DB
			
			
			ArrayList<Pallet> allPallets = currentOrder.getPalletList();
			for (Pallet tempPallet : allPallets) {																			//for each pallet
				tempPallet.addThisToDB();																					//add the pallet to the db
				for (Item tempItem : tempPallet.getAllItems()) {															//for each item on that pallet					
					SQL_Handler.addItemsToPallet(tempPallet.getID(), tempItem.getItemNumber(), tempItem.getQuantity());		//make the connection to the two
					SQL_Handler.updateItemQtyByItemNum(tempItem.getQuantity(), tempItem.getItemNumber()); 					//update the stock of each item
					SQL_Handler.updateSublocation(tempPallet.getSubLocation().getSubLocationName(), tempPallet.getSubLocation().getCurrent()); 	//update the sublocation's current pallet quantity
				}
			
			JOptionPane.showMessageDialog(frame, "Order added successfully");	
			}
			
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(frame, "Error creating order please make sure all fields are filled out properly");
			e.printStackTrace();
		}
		
	}//createWholeOrder end
	
	
	
	

}//Class end
