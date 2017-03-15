package wims_v1;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.sql.Connection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class Login_Screen extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text emp_txtBox;
	private Text pw_txtBox;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public Login_Screen(Shell parent, int style) {
		super(parent, style);
		setText("WIMS Login");
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
		shell = new Shell(getParent(), SWT.SHELL_TRIM);
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
		
		Label lblPassword = new Label(shell, SWT.NONE);
		lblPassword.setAlignment(SWT.RIGHT);
		lblPassword.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		lblPassword.setBounds(10, 40, 70, 15);
		lblPassword.setText("Password:");
		
		pw_txtBox = new Text(shell, SWT.PASSWORD | SWT.BORDER);
		pw_txtBox.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		pw_txtBox.setBounds(86, 37, 163, 21);
		
		Label lblConnection = new Label(shell, SWT.NONE);
		lblConnection.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblConnection.setAlignment(SWT.CENTER);
		lblConnection.setForeground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblConnection.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		lblConnection.setBounds(10, 60, 239, 15);
		
		Button btnLogin = new Button(shell, SWT.NONE);
		btnLogin.setBounds(81, 81, 75, 25);
		btnLogin.setText("Login");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				try {
					Connection conn = SQL_Handler.getConnection();
					if (conn.isValid(130) && SQL_Handler.isValidUsernamePassword(getEmpIDTxt(), getPwTxt())) {
						lblConnection.setText("Successfully Connected!");
						//Check user privileges
						//Open application window with correct privileges
						//Close login screen
					}
					else {
						lblConnection.setText("Invalid Employee ID or Password.");
					}
				} catch (Exception e1) { //What is this intended to catch? Bad connection? 
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});		
	}
	
	public String getEmpIDTxt() {
		return emp_txtBox.getText();
	}
	
	public String getPwTxt() {
		return pw_txtBox.getText();
	}
}
