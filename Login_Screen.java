package wims_v1;

import controller.SQL_Handler;

import java.sql.Connection;
import java.sql.SQLException;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

import java.util.concurrent.TimeUnit;


@SuppressWarnings("serial")
public class Login_Screen extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtEmpID;
	private JPasswordField txtPassword;
	private JLabel lblConnection;
	private boolean isSuccessfulConnection = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Login_Screen dialog = new Login_Screen();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Login_Screen() {
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		setResizable(false);
		
		//Set window properties
		setTitle("WIMS Login");
		setBounds(100, 100, 277, 145);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		createLabels();
		createTxtBoxes();
		createButtons();
		this.validate();
	}
	
	public void createLabels() {
		JLabel lblEmployeeID = new JLabel("Employee ID:");
		lblEmployeeID.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmployeeID.setBounds(10, 11, 82, 14);
		contentPanel.add(lblEmployeeID);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(10, 36, 82, 14);
		contentPanel.add(lblPassword);
		
		lblConnection = new JLabel("");
		lblConnection.setFont(new Font("Arial Unicode MS", Font.BOLD, 12));
		lblConnection.setForeground(new Color(165, 42, 42));
		lblConnection.setHorizontalAlignment(SwingConstants.CENTER);
		lblConnection.setBounds(10, 61, 241, 20);
		contentPanel.add(lblConnection);		
	}
	
	public void createButtons() {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setActionCommand("OK");
		buttonPane.add(btnSubmit);
		getRootPane().setDefaultButton(btnSubmit);
		
		btnSubmit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				executeBtnSubmit();
			}			
		});
	}
	
	public void createTxtBoxes() {
		txtEmpID = new JTextField();
		txtEmpID.setBounds(112, 8, 116, 20);
		contentPanel.add(txtEmpID);
		txtEmpID.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(112, 33, 116, 20);
		txtPassword.setEchoChar('*');
		contentPanel.add(txtPassword);
		txtPassword.setColumns(10);
	}
	
	/**
	 * Execute the tasks assigned to the submit button
	 */
	private void executeBtnSubmit() {
		try {
			Connection conn = SQL_Handler.getConnection();
			if (conn.isValid(130) && SQL_Handler.isValidUsernamePassword(txtEmpID.getText(), txtPassword.getPassword())) {
				isSuccessfulConnection = true;
				lblConnection.setText("Successfully Connected!");
				//Check user privileges
				//Wait a couple seconds then close login screen
				TimeUnit.SECONDS.sleep(1);
				//Notify driver to open main window
				//Open application window with correct privileges
				this.dispose();
			}
			else {
				lblConnection.setText("Invalid Employee ID or Password.");
			}
		} catch (SQLException exc) {  
			exc.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	public boolean isSuccessfulConnection() {
		return isSuccessfulConnection;
	}
}
