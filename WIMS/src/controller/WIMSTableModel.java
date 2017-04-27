package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
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
	}


	@Override
	public boolean isCellEditable(int row, int column) {
	   //all cells false
	   return false;
	}
	 
    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    @Override
    public Class getColumnClass(int col) {
    	for(int row = 0; row< getRowCount(); row++)
    	{
    		if(getValueAt(row, col) != null)
    			return getValueAt(row, col).getClass();
    	}
    	return null;
    }
    
    public Object[] getRowAt(int row)
    {
    	Object[] rowValues = new Object[this.getColumnCount()];
    	for(int col = 0; col < rowValues.length; col++)
    	{
    		rowValues[col] = this.getValueAt(row, col);
    	}
    	return rowValues;
    }
    
    public String getColumnName(int col){
    	return super.getColumnName(col);
    }
      
}