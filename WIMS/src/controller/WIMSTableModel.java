package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class WIMSTableModel extends DefaultTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6120753010500056633L;
	//private List<String> columnNames;
    //private List<Object[]> data;
//	private String[] columnNames;
//	private Object[][] data;
	
	public WIMSTableModel(Object[][] data, String[] columnNames)
	{
		super(data, columnNames);
//		this.columnNames = columnNames;
//		this.data = data;
	}
	
//	@Override
//    public int getColumnCount() {
//        return columnNames.length;
//    }
//
//   @Override
//    public int getRowCount() {
//       //int length = data.length;
//       //int datalength = Integer.parseInt(length);
//       return data.length;
//    }
//
//    @Override
//    public String getColumnName(int col) {
//        return columnNames[col].toString();
//    }
//
//    @Override
//    public Object getValueAt(int row, int col) {
//        return data[row][col];
//    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
//
//    /*
//     * Don't need to implement this method unless your table's
//     * data can change.
//     */
//    @Override
//    public void setValueAt(Object value, int row, int col) {
//        data[row][col] = value;
//        fireTableCellUpdated(row, col);
//    }
//	
//	/*
//     * JTable uses this method to determine the default renderer/ editor for
//     * each cell. If we didn't implement this method, then the last column
//     * would contain text ("true"/"false"), rather than a check box.
//     */
//	@Override
//    public Class getColumnClass(int c) {
//      return getValueAt(0, c).getClass();
//    }
//
//	@Override
//	public int getColumnCount() {
//		return columnNames.size();
//	}
//
//	@Override
//	public int getRowCount() {
//		return data.size();
//	}
//
//	@Override
//    public String getColumnName(int col) {
//      return columnNames.get(col);
//    }
//
//    @Override
//    public Object getValueAt(int row, int col) {
//      return data.get(row)[col];
//    }
    
//    /*
//     * Don't need to implement this method unless your table's
//     * data can change.
//     */
//    public void setValueAt(Object value, int row, int col) {
//        data.get(row)[col] = value;
//        fireTableCellUpdated(row, col);
//    }
//    
//    public void updateTable(Object[][] data, String[] columnNames)
//    {
//    	super.removeRow(row);
//    	super.addRow(rowData);
//    	
//    	System.out.println("in update table");
//    	System.out.println("getting row count");
//    	int rowCount = getRowCount();
//    	System.out.println("clearing data array field");
//	    System.out.println("firing rows deleted 0," + rowCount);
//		//this.fireTableRowsDeleted(0, rowCount);
//		System.out.println("looping through data of length " + data.length);
//		int length = data.length;
//		for(int i = 0; i < length; i++)
//		{
//			this.addRow(data[i]);
//		}
//		System.out.println("Firing table data changed");
//		super.setColumnIdentifiers(columnNames);
//    	this.columnNames = Arrays.asList(columnNames);
//		fireTableDataChanged();
//    }
    
//    public void addRow(Object[] row) {
//        int rowCount = getRowCount();
//        data.add(row);
//        System.out.println("update row fired");
//        fireTableRowsInserted(rowCount, rowCount);
//    }         
}