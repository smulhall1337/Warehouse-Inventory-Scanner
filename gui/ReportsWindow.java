package gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Text;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;


public class ReportsWindow {
	private static class ContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			return new Object[0];
		}
		public void dispose() {
		}
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}

	protected Shell shell;
	private Table tableReportData;
	private static final String[] reportNames = {"Employee Info", "Aging Items"};
	private Text textDisplayItemsOlder;
	private Label lblDisplayItemsOlder;
	private Label lblDays;
	private Combo comboReportType;
	private Label lblReportType;
	private Button btnExport;
	private Button btnNewButton;
	private TableViewer tableViewerReports;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ReportsWindow window = new ReportsWindow();
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
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(743, 642);
		shell.setText("SWT Application");
		
		createReportTypeLabel();
		createGenerateButton();
		createReportTypeCombo();
		createAgingItemsOptions();
		createTable();
		createExportButton();
	}
	
	/**
	* creates the main table area
	*/
	protected void createTable() {
		tableViewerReports = new TableViewer(shell, SWT.BORDER|SWT.H_SCROLL|SWT.V_SCROLL|SWT.FULL_SELECTION);
		tableReportData = tableViewerReports.getTable();
		tableReportData.setBounds(10, 190, 715, 357);
		tableReportData.setHeaderVisible(true);
		tableReportData.setLinesVisible(true);
		tableViewerReports.setContentProvider(new ContentProvider());
	}
	
	/**
	* sets up the columns
	* @param titles the column titles
	* @param bounds the boundaries of each column
	* @param values the values to be contained in each column
	*/
	protected void createTableColumns(String[] titles, int[] bounds, String[] values) {	
        	TableViewerColumn column = createTableViewerColumn(titles[0], bounds[0], 0);
        	column.setLabelProvider(new ColumnLabelProvider(){
            		public String getText(Object element) {
                		return super.getText(element);
            		}//getText
		});//setLabelProvider
        	tableViewerReports.setInput(values);
	}//createTableColumns
	
	/**
	* creates each individual column in the table 
	* @param header the header of the column
	* @param width the width of each column
	* @param idx the index where the new column will be placed
	* @return column the new column 
	*/
	private TableViewerColumn createTableViewerColumn(String header, int width, int idx) {
        	TableViewerColumn column = new TableViewerColumn(tableViewerReports, SWT.LEFT, idx);
        	column.getColumn().setText(header);
        	column.getColumn().setWidth(width);
        	column.getColumn().setResizable(true);
        	column.getColumn().setMoveable(true);
        	return column;
    	}
	
	/**
	* Create the "Report Type" label
	*/
	protected void createReportTypeLabel() {
		lblReportType = new Label(shell, SWT.NONE);
		lblReportType.setFont(SWTResourceManager.getFont("Segoe UI", 18, SWT.NORMAL));
		lblReportType.setBounds(25, 10, 147, 34);
		lblReportType.setText("Report Type:");
	}
	/**
	* Creates the combo box for selecting the report type 
	*/
	protected void createReportTypeCombo() {
		comboReportType = new Combo(shell, SWT.NONE);
		comboReportType.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				displayReportOptions(reportNames[comboReportType.getSelectionIndex()]);
			}//widgetSelected
		});//addSelectionListener
		comboReportType.setBounds(170, 21, 156, 97);
		comboReportType.setItems(reportNames);
	}//createReportTypeCombo
	
	/**
	* Creates the Aging items options 
	*/
	protected void createAgingItemsOptions() {
		lblDisplayItemsOlder = new Label(shell, SWT.NONE);
		lblDisplayItemsOlder.setBounds(14, 83, 139, 23);
		lblDisplayItemsOlder.setText("Display Items Older Than:");
		
		textDisplayItemsOlder = new Text(shell, SWT.BORDER);
		textDisplayItemsOlder.setBounds(159, 80, 44, 23);
		
		lblDays = new Label(shell, SWT.NONE);
		lblDays.setBounds(209, 83, 55, 15);
		lblDays.setText("days");
		
		setAgingItemOptionVisibility(false);
	}
	
	/**
	* create the "Export" button
	*/
	protected void createExportButton() {
		btnExport = new Button(shell, SWT.NONE);
		btnExport.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));
		btnExport.setFont(SWTResourceManager.getFont("Segoe UI Light", 22, SWT.NORMAL));
		btnExport.setBounds(581, 553, 144, 56);
		btnExport.setText("Export");
	}
	
	/**
	* create the "Generate" button
	*/
	protected void createGenerateButton() {
		btnNewButton = new Button(shell, SWT.NONE);
		createTable();
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				//TODO
				String[][] tableInfo = {{"header", "header2", "header3"}, {"value", "value2", "value3"}};
				int[] bounds = {100, 100, 100};
				createTableColumns(tableInfo[0], bounds, tableInfo[1]);
			}//mouseDown	
		});//addMouseListener
		btnNewButton.setFont(SWTResourceManager.getFont("Segoe UI Light", 20, SWT.NORMAL));
		btnNewButton.setBounds(594, 128, 131, 56);
		btnNewButton.setText("Generate");
	}//createGenerateButton
	
	private void fillTable(String[][] tableInfo) {
		for(int col = 0; col < tableInfo[0].length; col++) {
		    TableColumn nextCol = new TableColumn(tableReportData, SWT.CENTER);

		    for (int row = 0; row < tableInfo.length; row++) {
		        TableItem item = new TableItem(tableReportData, SWT.NONE);
		        item.setText(tableInfo[row]);
		    }
		}
		tableReportData.redraw();
	}
	
	private void displayReportOptions(String type) {
		if(type.equals(reportNames[0]))
		{
			displayEmployeeOptions();
		}else if(type.equals(reportNames[1])){
			displayAgingItemOptions();
		}
	}
	
	private void displayAgingItemOptions() {
		hideAllReportOptions();
		setAgingItemOptionVisibility(true);
	}

	private void setAgingItemOptionVisibility(boolean visible) {
		lblDisplayItemsOlder.setVisible(visible);
		lblDays.setVisible(visible);
		textDisplayItemsOlder.setVisible(visible);
	}

	private void hideAllReportOptions() {
		setAgingItemOptionVisibility(false);
		setEmployeeOptionVisibility(false);
	}

	private void setEmployeeOptionVisibility(boolean visible) {
		// TODO Auto-generated method stub
		
	}

	private void displayEmployeeOptions() {
		hideAllReportOptions();
		setEmployeeOptionVisibility(true);
	}
}
