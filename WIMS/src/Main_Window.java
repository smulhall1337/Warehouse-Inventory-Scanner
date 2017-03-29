package wims_v1;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import java.sql.SQLException;
import java.util.ArrayList;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.MenuItem;

public class Main_Window {
	
	private static class ContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			return new Object[0];
		}
		public void dispose() {
		}
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	protected Shell shlWims;
	private TableViewer tblViewerMain;
	private Table tblMain;

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
		try {
			createContents();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	 * @throws SQLException when sub-procedure calls throw SQLException
	 */
	protected void createContents() throws SQLException {
		shlWims = new Shell();
		shlWims.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		shlWims.setSize(500, 425);
		shlWims.setText("WIMS");
		shlWims.setLayout(null);
		
		createLabels();
		createComboBoxes();
		createButtons();
		createTable();
		
		Menu menu = new Menu(shlWims, SWT.BAR);
		shlWims.setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.NONE);
		mntmFile.setText("File");

	}
	
	/**
	 * Create the buttons for the window
	 */
	private void createButtons() {
		Group group = new Group(shlWims, SWT.NONE);
		group.setBounds(18, 61, 120, 51);
		group.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		
		Button btnDescending = new Button(group, SWT.RADIO);
		btnDescending.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btnDescending.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		btnDescending.setBounds(10, 32, 102, 16);
		btnDescending.setText("Descending");
		
		Button btnAscending = new Button(group, SWT.RADIO);
		btnAscending.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btnAscending.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		btnAscending.setBounds(10, 10, 102, 16);
		btnAscending.setText("Ascending");
		
		Button btnSearch = new Button(shlWims, SWT.NONE);
		btnSearch.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btnSearch.setBounds(207, 8, 75, 25);
		btnSearch.setText("Search");
		
		Button btnSort = new Button(shlWims, SWT.CHECK);
		btnSort.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		btnSort.setBounds(8, 39, 69, 16);
		btnSort.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		btnSort.setText("Sort By:");
	}
	
	/**
	 * Create the combo boxes for the window
	 * @throws SQLException when query cannot be executed
	 */
	private void createComboBoxes() throws SQLException {
		
		Combo cmbSort = new Combo(shlWims, SWT.READ_ONLY);
		cmbSort.setBounds(83, 39, 91, 23);
		
		Combo comboTables = new Combo(shlWims, SWT.READ_ONLY);
		comboTables.setBounds(10, 10, 164, 23);
		ArrayList<String> tableNames = SQL_Handler.getTableNames();
		for (String s : tableNames) {
			comboTables.add(s);
		}
		comboTables.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					cmbSort.removeAll();
					String table = comboTables.getText();
					ArrayList<String> colNames = SQL_Handler.getTableColumnNames(table);
					for (String s : colNames) {
						cmbSort.add(s);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		});
			
	}
	
	/**
	 * Create the labels for the window
	 */
	private void createLabels() {
		Label lblShowColumns = new Label(shlWims, SWT.NONE);
		lblShowColumns.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblShowColumns.setBounds(292, 10, 126, 15);
		lblShowColumns.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_CYAN));
		lblShowColumns.setText("Show Columns For:");
	}
	
	/*
	 * ALL METHODS FOLLOWING THIS ARE NOT TESTED OR WORKING
	 */
	protected void createTable() {
		
		tblViewerMain = new TableViewer(shlWims, SWT.BORDER |SWT.H_SCROLL | 
				SWT.V_SCROLL | SWT.FULL_SELECTION);
		tblMain = tblViewerMain.getTable();
		tblMain.setBounds(10, 135, 464, 221);
		tblMain.setHeaderVisible(true);
		tblMain.setLinesVisible(true);
		tblViewerMain.setContentProvider(new ContentProvider());
	}
	
	protected void createTableColumns(String[] titles, int[] bounds, String[] values)
	{
		
        TableViewerColumn column = createTableViewerColumn(titles[0], bounds[0], 0);
        column.setLabelProvider(new ColumnLabelProvider(){
            public String getText(Object element) {
                return super.getText(element);
            }
        });
        tblViewerMain.setInput(values);
	}
	
	private TableViewerColumn createTableViewerColumn(String header, int width, int idx) 
    {
        TableViewerColumn column = new TableViewerColumn(tblViewerMain, SWT.LEFT, idx);
        column.getColumn().setText(header);
        column.getColumn().setWidth(width);
        column.getColumn().setResizable(true);
        column.getColumn().setMoveable(true);
        return column;
    }
	
}
