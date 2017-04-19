package controller;

import javax.swing.table.AbstractTableModel;

public class WIMSTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6120753010500056633L;
	private String[] columnNames;
	private Object[][] data;
	
	public WIMSTableModel(Object[][] data, String[] columnNames)
	{
		super();
		this.columnNames = columnNames;
		this.data = data;
	}
	
	/*
     * JTable uses this method to determine the default renderer/ editor for
     * each cell. If we didn't implement this method, then the last column
     * would contain text ("true"/"false"), rather than a check box.
     */
	@Override
    public Class getColumnClass(int c) {
      return getValueAt(0, c).getClass();
    }

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return data.length;
	}

    public String getColumnName(int col) {
      return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
      return data[row][col];
    }
}