package gui;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JSeparator;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;

public class CreateIncomingOrder {

	private JFrame frmWimsCreate;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateIncomingOrder window = new CreateIncomingOrder();
					window.frmWimsCreate.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CreateIncomingOrder() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmWimsCreate = new JFrame();
		frmWimsCreate.setTitle("WIMS - Create Incoming Order");
		frmWimsCreate.setBounds(100, 100, 279, 391);
		frmWimsCreate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmWimsCreate.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Pallets In Order", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(31, 74, 189, 158);
		frmWimsCreate.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblPalletId = new JLabel("Pallet ID: ");
		lblPalletId.setBounds(6, 26, 57, 14);
		panel.add(lblPalletId);
		
		JLabel lblPlaceholder_1 = new JLabel("Placeholder");
		lblPlaceholder_1.setBounds(73, 26, 89, 14);
		panel.add(lblPlaceholder_1);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(48, 131, 101, -61);
		panel.add(separator_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 51, 175, 92);
		panel.add(new ItemPanel());
		panel_1.setBorder(new TitledBorder(null, "Items on Pallet", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(6, 16, 163, 69);
		panel_1.add(separator);
		/*
		JLabel lblItemId = new JLabel("Item ID:");
		lblItemId.setBounds(6, 22, 51, 14);
		panel_1.add(lblItemId);
		
		textField_1 = new JTextField();
		textField_1.setBounds(60, 19, 105, 20);
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(130, 50, 35, 20);
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setBounds(60, 53, 53, 14);
		panel_1.add(lblQuantity);
		*/
		JLabel lblOrderId = new JLabel("Order ID: ");
		lblOrderId.setBounds(31, 11, 65, 14);
		frmWimsCreate.getContentPane().add(lblOrderId);
		
		JLabel lblPlaceholder = new JLabel("Placeholder");
		lblPlaceholder.setBounds(131, 11, 89, 14);
		frmWimsCreate.getContentPane().add(lblPlaceholder);
		
		JLabel lblOrigin = new JLabel("Origin: ");
		lblOrigin.setBounds(41, 36, 46, 14);
		frmWimsCreate.getContentPane().add(lblOrigin);
		
		textField = new JTextField();
		textField.setBounds(116, 36, 86, 20);
		frmWimsCreate.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnAddAdditionalItem = new JButton("Add Additional Item");
		btnAddAdditionalItem.setBounds(63, 243, 157, 23);
		frmWimsCreate.getContentPane().add(btnAddAdditionalItem);
		btnAddAdditionalItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.add(new ItemPanel());
			}
			
		});
		
		JButton btnCreate = new JButton("Create");
		btnCreate.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
					textField.setText("");
					textField_1.setText("");
					textField_2.setText("");
			}
		});
		btnCreate.setBounds(128, 324, 89, 23);
		frmWimsCreate.getContentPane().add(btnCreate);
		
		JButton btnAddAdditionalPallet = new JButton("Add Additional Pallet");
		btnAddAdditionalPallet.setBounds(31, 277, 171, 23);
		frmWimsCreate.getContentPane().add(btnAddAdditionalPallet);
	}
}