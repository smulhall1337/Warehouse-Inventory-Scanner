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
	private Text usr_txtBox;
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
		shell.setSize(255, 145);
		shell.setText(getText());
		
		Label lblUsername = new Label(shell, SWT.NONE);
		lblUsername.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		lblUsername.setBounds(10, 10, 55, 15);
		lblUsername.setText("Username:");
		
		usr_txtBox = new Text(shell, SWT.BORDER);
		usr_txtBox.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		usr_txtBox.setBounds(71, 10, 163, 21);
		
		Label lblPassword = new Label(shell, SWT.NONE);
		lblPassword.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		lblPassword.setBounds(10, 50, 55, 15);
		lblPassword.setText("Password:");
		
		Label connection_lbl = new Label(shell, SWT.NONE);
		connection_lbl.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		connection_lbl.setBounds(10, 10, 224, 15);
		connection_lbl.setVisible(false);
		
		Label url_lbl = new Label(shell, SWT.NONE);
		url_lbl.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		url_lbl.setBounds(10, 50, 224, 15);
		url_lbl.setVisible(false);
		
		pw_txtBox = new Text(shell, SWT.BORDER);
		pw_txtBox.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		pw_txtBox.setBounds(71, 50, 163, 21);
		
		Button btnLogin = new Button(shell, SWT.NONE);
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				try {
					Connection conn = SQL_Handler.getConnection(usr_txtBox.getText(), pw_txtBox.getText());
					if (conn.isValid(130)) {
						usr_txtBox.setVisible(false);
						pw_txtBox.setVisible(false);
						lblUsername.setVisible(false);
						lblPassword.setVisible(false);
						connection_lbl.setText("Successfully Connected To:");
						url_lbl.setText(conn.getMetaData().getURL());
						url_lbl.setVisible(true);
						connection_lbl.setVisible(true);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(81, 81, 75, 25);
		btnLogin.setText("Login");
		
	}
	
}
