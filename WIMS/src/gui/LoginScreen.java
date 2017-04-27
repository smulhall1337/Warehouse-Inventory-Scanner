package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.SQL_Handler;

public class LoginScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 163582323048204913L;
	private JFrame frame;
	private JTextField emp_txtBox;
	private JLabel lblEmpID;
	private JPasswordField pw_txtBox;
	private JLabel lblPassword;
	private boolean isSuccessfulConnection;
	private JLabel lblConnection;
	private JButton btnLogin;
	private int nextOptionRow; // the index of the next option row in the
								// gridbaglayout
	private static final int STARTING_OPTION_ROW = 0;
	private static final int DEFAULT_WINDOW_WIDTH = 275;
	private static final int DEFAULT_WINDOW_HEIGHT = 145;
			
	private JPanel panelEntryFields;
	private JPanel panelLogin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginScreen window = new LoginScreen();
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
	public LoginScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// set to false so users must enter password first
		isSuccessfulConnection = false;
		nextOptionRow = STARTING_OPTION_ROW;
		initializeFrame();
		initializeEntryPanel();
		initializeLoginPanel();
		initializeBottomBorder();
	}
	
	private void launchMainWindow() throws SQLException {
		MainWindow mainWindow = new MainWindow(emp_txtBox.getText(), SQL_Handler.isEmployeeManager(emp_txtBox.getText()));
		mainWindow.getFrame().setVisible(true);
	}

	private void initializeEntryPanel() {
		panelEntryFields = new JPanel();
		frame.getContentPane().add(panelEntryFields);
		GridBagLayout gbl_panelEntryFields = new GridBagLayout();
		gbl_panelEntryFields.columnWidths = new int[] {20, 100, 140, 60};
		gbl_panelEntryFields.rowHeights = new int[] {0, 30, 30};
		panelEntryFields.setLayout(gbl_panelEntryFields);
		initializeEmployeeIDComponents();
		initalizePasswordComponents();
	}

	private void initializeLoginPanel() {
		panelLogin = new JPanel();
		panelLogin.setAlignmentY(Component.TOP_ALIGNMENT);
		panelLogin.setLayout(new BoxLayout(panelLogin, BoxLayout.Y_AXIS));
		frame.getContentPane().add(panelLogin);
		initializeLoginButton();
		initializeConnectionLabel();
	}

	private void initializeFrame() {
		frame = new JFrame();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int xPos = (dim.width/2-frame.getSize().width/2)-(DEFAULT_WINDOW_WIDTH/2);
		int yPos = (dim.height/2-frame.getSize().height/2)-(DEFAULT_WINDOW_HEIGHT/2);
		frame.setBounds(xPos, yPos, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
		frame.setTitle("WIMS - Login");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(
				new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
	}

	private void initializeLoginButton() {
		btnLogin = new JButton();
		btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLogin.setText("Login");
		btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 13));
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				executeBtnSubmit();
			}
		});

		btnLogin.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				executeBtnSubmit();
			}

			// do nothing on other events
			@Override
			public void focusLost(FocusEvent arg0) {
			}
		});
		panelLogin.add(btnLogin);
	}

	private void initalizePasswordComponents() {

		pw_txtBox = new JPasswordField();
		// pw_txtBox.setBounds(86, 37, 163, 21);
		pw_txtBox.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					executeBtnSubmit();
				}
			}

			// do nothing on other events
			public void keyReleased(KeyEvent keyEvent) {
			}

			public void keyTyped(KeyEvent keyEvent) {
			}
		});

		lblPassword = new JLabel("Password:");
		lblPassword.setBackground(Color.CYAN);
		// lblPassword.setBounds(10, 40, 70, 15);

		addRowToWindow(lblPassword, pw_txtBox);
	}

	private void initializeEmployeeIDComponents() {
		lblEmpID = new JLabel("Employee ID:");

		emp_txtBox = new JTextField();
		addRowToWindow(lblEmpID, emp_txtBox);
		emp_txtBox.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					executeBtnSubmit();
				}
			}

			// do nothing on other events
			public void keyReleased(KeyEvent keyEvent) {
			}

			public void keyTyped(KeyEvent keyEvent) {
			}
		});

		addRowToWindow(lblEmpID, emp_txtBox);
	}

	private void initializeConnectionLabel() {

		lblConnection = new JLabel();
		lblConnection.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblConnection.setHorizontalAlignment(JLabel.CENTER);
		lblConnection.setVerticalAlignment(JLabel.CENTER);
		lblConnection.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblConnection.setText("Waiting for action");
		lblConnection.setForeground(panelLogin.getBackground());
		panelLogin.add(lblConnection);
	}

	private void executeBtnSubmit() {
		try {
			lblConnection.setVisible(true);
			Connection conn = SQL_Handler.getConnection();
			if (conn.isValid(130)
					&& SQL_Handler.isValidUsernamePassword(getEmpIDTxt(),
							getPwTxt())) {
				isSuccessfulConnection = true;
				lblConnection.setForeground(Color.GREEN);
				lblConnection.setText("Successfully Connected!");
				// Check user privileges
				// Wait a couple seconds then close login screen
				TimeUnit.SECONDS.sleep(1);
				// Open application window with correct privileges
				this.frame.setVisible(false);
				this.dispose();
				launchMainWindow();
			} else {
				lblConnection.setForeground(Color.RED);
				lblConnection.setText("Invalid Employee ID or Password.");
			}
		} catch (SQLException exc) {
			lblConnection.setForeground(Color.RED);
			lblConnection.setText("There was a problem connecting to the server.");
			exc.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	private void addRowToWindow(JLabel optionLabel, JTextField optionTextField) {
		
		GridBagConstraints gbc_optionLabel = new GridBagConstraints();
		gbc_optionLabel.anchor = GridBagConstraints.EAST;
		gbc_optionLabel.insets = new Insets(0, 0, 5, 5);
		gbc_optionLabel.gridx = 1;
		gbc_optionLabel.gridy = nextOptionRow;
		panelEntryFields.add(optionLabel, gbc_optionLabel);

		optionTextField.setColumns(15);
		GridBagConstraints gbc_optionTextField = new GridBagConstraints();
		gbc_optionTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_optionTextField.insets = new Insets(0, 0, 5, 5);
		gbc_optionTextField.anchor = GridBagConstraints.WEST;
		gbc_optionTextField.gridx = 2;
		gbc_optionTextField.gridy = nextOptionRow;
		panelEntryFields.add(optionTextField, gbc_optionTextField);

		nextOptionRow++;
	}
	
	public void initializeBottomBorder()
	{
		Component rigidAreaBottom = Box.createRigidArea(new Dimension(5, 5));
		frame.getContentPane().add(rigidAreaBottom);
	}

	public boolean isSuccessfulConnection() {
		return isSuccessfulConnection;
	}

	public String getEmpIDTxt() {
		return emp_txtBox.getText();
	}

	public String getPwTxt() {
		String passwordText = new String(pw_txtBox.getPassword());
		return passwordText;
	}
	
	public JFrame getFrame()
	{
		return this.frame;
	}
}