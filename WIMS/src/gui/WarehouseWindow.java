package gui;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import controller.SQL_Handler;
import controller.Valid;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.JFormattedTextField;

public class WarehouseWindow extends JFrame{

	private JFrame frmAddWarehouse;
	private JTextField ID;
	private JTextField name;
	private JTextField address;
	private JTextField city;
	private JTextField state;
	private JTextField zip;
	private JTextField telephone;
	private JTextField email;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WarehouseWindow window = new WarehouseWindow();
					window.frmAddWarehouse.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WarehouseWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAddWarehouse = new JFrame();
		frmAddWarehouse.setTitle("Add Warehouse");
		frmAddWarehouse.setBounds(100, 100, 391, 318);
		frmAddWarehouse.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmAddWarehouse.getContentPane().setLayout(null);
		
		JLabel lblWarehouseId = new JLabel("Warehouse ID");
		lblWarehouseId.setBounds(41, 25, 91, 16);
		frmAddWarehouse.getContentPane().add(lblWarehouseId);
		
		JLabel lblWarehouseNam = new JLabel("Warehouse Name");
		lblWarehouseNam.setBounds(41, 53, 126, 16);
		frmAddWarehouse.getContentPane().add(lblWarehouseNam);
		
		JLabel lblStreetAddress = new JLabel("Street Address");
		lblStreetAddress.setBounds(41, 81, 126, 16);
		frmAddWarehouse.getContentPane().add(lblStreetAddress);
		
		JLabel lblNewLabel = new JLabel("City");
		lblNewLabel.setBounds(41, 109, 126, 16);
		frmAddWarehouse.getContentPane().add(lblNewLabel);
		
		JLabel lblState = new JLabel("State");
		lblState.setBounds(41, 137, 61, 16);
		frmAddWarehouse.getContentPane().add(lblState);
		
		JLabel lblZip = new JLabel("ZIP");
		lblZip.setBounds(41, 165, 61, 16);
		frmAddWarehouse.getContentPane().add(lblZip);
		
		JLabel lblTelephoneNumber = new JLabel("Telephone Number");
		lblTelephoneNumber.setBounds(41, 193, 126, 16);
		frmAddWarehouse.getContentPane().add(lblTelephoneNumber);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(41, 221, 61, 16);
		frmAddWarehouse.getContentPane().add(lblEmail);
		
		JButton add = new JButton("Add Warehouse");
		add.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					System.out.println(ID.getText());
					SQL_Handler.addNewWarehouse(ID.getText(), city.getText(), state.getText(), address.getText(), Integer.parseInt(zip.getText()), 
							name.getText(), telephone.getText(), email.getText());
					JOptionPane.showMessageDialog(frmAddWarehouse, "Warehouse added");
					dispose();
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(frmAddWarehouse, "Error adding warehouse", "Warehouse add error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(frmAddWarehouse, "Error adding warehouse", "Warehouse add error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		add.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
			}
		});
		add.setBounds(220, 261, 165, 29);
		frmAddWarehouse.getContentPane().add(add);
		
		ID = new JTextField();
		ID.setBounds(210, 20, 130, 26);
		frmAddWarehouse.getContentPane().add(ID);
		ID.setColumns(10);
		
		name = new JTextField();
		name.setColumns(10);
		name.setBounds(210, 48, 130, 26);
		frmAddWarehouse.getContentPane().add(name);
		
		address = new JTextField();
		address.setColumns(10);
		address.setBounds(210, 76, 130, 26);
		frmAddWarehouse.getContentPane().add(address);
		
		city = new JTextField();
		city.setColumns(10);
		city.setBounds(210, 104, 130, 26);
		frmAddWarehouse.getContentPane().add(city);
		
		state = new JTextField();
		state.setColumns(10);
		state.setBounds(210, 132, 130, 26);
		frmAddWarehouse.getContentPane().add(state);
		
		zip = new JTextField();
		zip.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				Valid.intInput(evt.getKeyChar(), evt);
			}
		});
		zip.setColumns(10);
		zip.setBounds(210, 160, 130, 26);
		frmAddWarehouse.getContentPane().add(zip);
		
		telephone = new JTextField();
		telephone.setColumns(10);
		telephone.setBounds(210, 188, 130, 26);
		frmAddWarehouse.getContentPane().add(telephone);
		
		email = new JTextField();
		email.setColumns(10);
		email.setBounds(210, 216, 130, 26);
		frmAddWarehouse.getContentPane().add(email);
	}

	public Component getFrame() {
		// TODO Auto-generated method stub
		return frmAddWarehouse;
	}
}
