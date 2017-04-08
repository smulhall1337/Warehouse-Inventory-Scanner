

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

public class Main_Window {

	protected Shell shlWims;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Main_Window window = new Main_Window();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlWims.open();
		shlWims.layout();
		while (!shlWims.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlWims = new Shell();
		shlWims.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		shlWims.setSize(500, 425);
		shlWims.setText("WIMS");
		shlWims.setLayout(new FormLayout());
		
		Combo combo = new Combo(shlWims, SWT.NONE);
		FormData fd_combo = new FormData();
		fd_combo.right = new FormAttachment(0, 161);
		fd_combo.top = new FormAttachment(0, 10);
		fd_combo.left = new FormAttachment(0, 10);
		combo.setLayoutData(fd_combo);
		
		Menu menu = new Menu(shlWims, SWT.BAR);
		shlWims.setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.NONE);
		mntmFile.setText("File");
		
		Button btnSort = new Button(shlWims, SWT.CHECK);
		FormData fd_btnSort = new FormData();
		fd_btnSort.top = new FormAttachment(0, 39);
		fd_btnSort.left = new FormAttachment(0, 8);
		btnSort.setLayoutData(fd_btnSort);
		btnSort.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		btnSort.setText("Sort By:");
		
		Combo cmbSort = new Combo(shlWims, SWT.NONE);
		FormData fd_cmbSort = new FormData();
		fd_cmbSort.top = new FormAttachment(0, 39);
		fd_cmbSort.left = new FormAttachment(0, 70);
		cmbSort.setLayoutData(fd_cmbSort);
		
		Label lblShowColumns = new Label(shlWims, SWT.NONE);
		FormData fd_lblShowColumns = new FormData();
		fd_lblShowColumns.right = new FormAttachment(0, 404);
		fd_lblShowColumns.top = new FormAttachment(0, 10);
		fd_lblShowColumns.left = new FormAttachment(0, 292);
		lblShowColumns.setLayoutData(fd_lblShowColumns);
		lblShowColumns.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		lblShowColumns.setText("Show Columns For:");
		
		Button btnSearch = new Button(shlWims, SWT.NONE);
		FormData fd_btnSearch = new FormData();
		fd_btnSearch.right = new FormAttachment(0, 263);
		fd_btnSearch.top = new FormAttachment(0, 10);
		fd_btnSearch.left = new FormAttachment(0, 188);
		btnSearch.setLayoutData(fd_btnSearch);
		btnSearch.setText("Search");
		btnSearch.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Group group = new Group(shlWims, SWT.NONE);
		FormData fd_group = new FormData();
		fd_group.right = new FormAttachment(0, 130);
		fd_group.top = new FormAttachment(0, 61);
		fd_group.left = new FormAttachment(0, 18);
		group.setLayoutData(fd_group);
		group.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		
		Button btnAscending = new Button(group, SWT.RADIO);
		btnAscending.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		btnAscending.setBounds(10, 10, 90, 16);
		btnAscending.setText("Ascending");
		
		Button btnDescending = new Button(group, SWT.RADIO);
		btnDescending.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		btnDescending.setBounds(10, 32, 90, 16);
		btnDescending.setText("Descending");
	}
}
