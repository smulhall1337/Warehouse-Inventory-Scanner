package wims_v1;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.util.concurrent.TimeUnit;

import java.sql.Connection;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class Login_Screen extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text emp_txtBox;
	private Text pw_txtBox;
	private boolean isSuccessfulConnection = false;
	private Label lblConnection;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public Login_Screen(Shell parent, int style) {
		super(parent, style);
		setText("WIMS Login");
		open();
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.TITLE);
		shell.setTouchEnabled(true);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		shell.setSize(275, 150);
		shell.setText(getText());
		
		Label lblEmpID = new Label(shell, SWT.NONE);
		lblEmpID.setAlignment(SWT.RIGHT);
		lblEmpID.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		lblEmpID.setBounds(10, 10, 70, 15);
		lblEmpID.setText("Employee ID:");
		
		emp_txtBox = new Text(shell, SWT.BORDER);
		emp_txtBox.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		emp_txtBox.setBounds(86, 7, 163, 21);
		emp_txtBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.CR || e.keyCode == SWT.LF) {
					executeBtnSubmit();
				}
			}
		});
		
		Label lblPassword = new Label(shell, SWT.NONE);
		lblPassword.setAlignment(SWT.RIGHT);
		lblPassword.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		lblPassword.setBounds(10, 40, 70, 15);
		lblPassword.setText("Password:");
		
		pw_txtBox = new Text(shell, SWT.PASSWORD | SWT.BORDER);
		pw_txtBox.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		pw_txtBox.setBounds(86, 37, 163, 21);
		pw_txtBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.CR || e.keyCode == SWT.LF) {
					executeBtnSubmit();
				}
			}
		});
		
		lblConnection = new Label(shell, SWT.NONE);
		lblConnection.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblConnection.setAlignment(SWT.CENTER);
		lblConnection.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblConnection.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		lblConnection.setBounds(10, 60, 239, 15);
		
		Button btnLogin = new Button(shell, SWT.NONE);
		shell.setDefaultButton(btnLogin);
		btnLogin.setBounds(96, 81, 75, 25);
		btnLogin.setText("Login");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				executeBtnSubmit();
			}
		});
		
		// Since login button is default, enter key selects it -> perform action
		btnLogin.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				executeBtnSubmit();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
	}
	
	/**
	 * Execute the tasks assigned to the submit button
	 */
	private void executeBtnSubmit() {
		try {
			Connection conn = SQL_Handler.getConnection();
			if (conn.isValid(130) && SQL_Handler.isValidUsernamePassword(emp_txtBox.getText(), pw_txtBox.getText())) {
				isSuccessfulConnection = true;
				lblConnection.setText("Successfully Connected!");
				//Check user privileges
				//Wait a couple seconds then close login screen
				TimeUnit.SECONDS.sleep(1);
				shell.dispose();
				//Notify driver to open main window
				//Open application window with correct privileges											
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
	
	/**
	 * Whether or not the connection was successful
	 * @return true if the connection was successful, false otherwise
	 */
	public boolean isSuccessfulConnection() {
		return isSuccessfulConnection;
	}
}
