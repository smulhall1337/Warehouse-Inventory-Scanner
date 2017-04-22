import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
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
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class OrderWindow extends JFrame {

	private static JFrame frame;
	private static final int LRHEIGHT = 400, LWIDTH = 375, RWIDTH = 290, BHEIGHT = 50, BWIDTH = LWIDTH + RWIDTH;
	private Dimension LSIZE = new Dimension(LWIDTH, LRHEIGHT), RSIZE = new Dimension(RWIDTH, LRHEIGHT), BSIZE = new Dimension(BWIDTH, BHEIGHT);
	private JPanel leftPanel = new JPanel();
	private	JPanel rightPanel = new JPanel();
	private	JPanel bottomPanel = new JPanel();
	private JButton btnExit = new JButton("Exit"), btnCreateOrder = new JButton("Create Order"), btnViewBarcodes = new JButton("View BarCodes");
	private JList palletJList = new JList(), itemJList = new JList();
	
	private static boolean foundOrder = false;
	private static boolean isM;
	private ArrayList<String> palletList = new ArrayList<String>();			//stores all pallet id's
	private ArrayList<String> currentItemList = new ArrayList<String>();	//stores currently selected pallet's items
	private ArrayList<ArrayList> allItemList = new ArrayList<ArrayList>();  //stores all lists of items
	
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
		PalletInOrderPanel palletInOrderPanel = new PalletInOrderPanel();
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
				//call all SQL stuff
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
	
	public static boolean getManagement() {
		return isM;
	}
	
	public static boolean getFoundOrder() {
		return foundOrder;
	}
	
	public static JFrame getFrame() {
		return frame;
	}
	
	public static PalletPanel getCurrentPalletPanel() {
		return PalletInOrderPanel.getPalletPanel();
	}
	
	public static ItemPanel getCurrentItemPanel() {
		return ItemsInPalletPanel.getItemPanel();
	}

}//Class end
