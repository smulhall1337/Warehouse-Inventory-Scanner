import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class OrderWindow {

	private static JFrame frame;
	private Dimension minimumSize = new Dimension(375,800);
	private JPanel topPanel = new JPanel(), centerPanel = new JPanel(), bottomPanel = new JPanel();
	private boolean found = false;
	private PalletInOrderPanel palletInOrderPanel;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrderWindow window = new OrderWindow();
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
	public OrderWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 400, 825);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(minimumSize);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

		
		topPanel.setLayout(new FlowLayout());
		frame.getContentPane().add(topPanel);
		centerPanel.setLayout(new BorderLayout());
		frame.getContentPane().add(centerPanel);
		frame.getContentPane().add(bottomPanel);
		bottomPanel.setLayout(new FlowLayout());
		
		//TOP SECTION
		JLabel lblOrderNumber = new JLabel("Order Number");
		JTextField txtOrderNumber = new JTextField();
		txtOrderNumber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				Valid.intInput(evt.getKeyChar(), evt);
			}
		});
		txtOrderNumber.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				String input = txtOrderNumber.getText();
				if (input.equals("")) {
					JOptionPane.showMessageDialog(frame, "Please enter the Order Number first before you do anything else");
					txtOrderNumber.requestFocus();
					return;
				}
				
				try {
					found = SQL_Handler.OrderInDB(input);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				if (found) { //if an order with that number already exists, dont let them enter that
					JOptionPane.showMessageDialog(frame, "That Order Number already exists, you cannot create a duplicate");
					txtOrderNumber.setText("");
					txtOrderNumber.requestFocus();
				}
				else { //that order number does not exist so continue
					txtOrderNumber.setEditable(false);
					palletInOrderPanel.setVisible(true);
				}
			}
		});
		txtOrderNumber.setColumns(10);
		txtOrderNumber.requestFocus();
		topPanel.add(lblOrderNumber);
		topPanel.add(txtOrderNumber);
		JLabel lblOrigin = new JLabel("Origin:");
		JTextField txtOrigin = new JTextField();
		txtOrigin.setColumns(10);
		topPanel.add(lblOrigin);
		topPanel.add(txtOrigin);
		
		//MIDDLE SECTION
		palletInOrderPanel = new PalletInOrderPanel();
		centerPanel.add(palletInOrderPanel);
		palletInOrderPanel.setVisible(false);
		
		//BOTTOM SECTION
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		
		JButton btnCreate = new JButton("Create");
		btnCreate.setEnabled(false);
		bottomPanel.add(btnExit);
		bottomPanel.add(btnCreate);
		
	}//initialize end
	
	
	

}//Class end
