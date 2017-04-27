package controller;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class WIMSTable extends JTable{
	
	//the margin in each column after resizing
	private static final int TABLE_COLUMN_MARGIN = 25;
	private HashMap<String, TableColumn> headersToColumnsMap;
	private HashMap<String, Integer> headerToColumnIndexMap; 
	
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
	{
		if(getValueAt(row, column) != null)
		{
			Component c = super.prepareRenderer(renderer, row, column);
			DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer(){
			    @Override
			    public Component getTableCellRendererComponent(JTable arg0,Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
			         Component tableCellRendererComponent = super.getTableCellRendererComponent(arg0, arg1, arg2, arg3, arg4, arg5);
			         int align = DefaultTableCellRenderer.CENTER;
			         //if(condition){
			         //    align = DefaultTableCellRenderer.LEFT;
			         //}
			        ((DefaultTableCellRenderer)tableCellRendererComponent).setHorizontalAlignment(align);
			         return tableCellRendererComponent;
			    }
			};
			 this.getColumnModel().getColumn(column).setCellRenderer(cellRenderer);
				return c;
		}else
			return new DefaultTableCellRenderer();
		
	}
	 
	public void updateColumnWidths() 
	{
		for(int i =0; i <	this.getColumnCount(); i++)
		{
			//TODO sort this out, maybe check the difference between column header and data widths and do margins from that
			//TODO maybe make a method that lets you right click to change whether data is centered, etc.
			int headerWidth = this.getColumnHeaderWidth(i);
			int dataWidth = this.getColumnDataWidth(i);
			//System.out.println("(((IN UPDATECOLUMNWIDTHS))) GOT COLUMN WIDTH STUFF !!!");
			//double column margin to not have any hidden text when the black arrow appears
			//after double clicking to sort. this leaves room for the arrow
			int columnWidth = headerWidth; 
			if(dataWidth > headerWidth)
				columnWidth = dataWidth;
			else
				columnWidth = headerWidth;
			TableColumn column = this.getColumnModel().getColumn(i);
		    column.setMinWidth(columnWidth + TABLE_COLUMN_MARGIN);
		    column.setPreferredWidth(columnWidth + TABLE_COLUMN_MARGIN);
		    column.setWidth(columnWidth + TABLE_COLUMN_MARGIN);
			//System.out.println("(((IN UPDATECOLUMNWIDTHS))) FINISHED SETTING WIDTH STUFF !!!");
		}
		//System.out.println("(((IN UPDATECOLUMNWIDTHS))) DONE METHOD WHAT HAPPENS NOW !!!");

	}
	/**
	 * @param update whether to update the map based on table contents before getting it
	 * @return a map, where the key is a column header, and the value is that column's index in the table
	 */
	public HashMap<String,Integer> getColumnIndexByHeaderMap()
	{
		return getColumnIndexByHeaderMap(true);
	}
	
	/**
	 * @param update whether to update the map based on table contents before getting it
	 * @return a map, where the key is a column header, and the value is that column's index in the table
	 */
	public HashMap<String,Integer> getColumnIndexByHeaderMap(boolean update)
	{
		if(update == true)
		{
			HashMap<String,Integer> tempHeadersToColIndexMap = new HashMap<String,Integer>();
			for(int i =0; i < this.getColumnCount(); i++)
			{
				String nextColHeader = this.getColumnName(i);
				tempHeadersToColIndexMap.put(nextColHeader, i);
			}
			this.headerToColumnIndexMap = tempHeadersToColIndexMap;
			return this.headerToColumnIndexMap;
		}else{
			return this.headerToColumnIndexMap;
		}
	}
	
//	/**
//	 * @param update whether to update the map based on table contents before getting it
//	 * @return a map, where the key is a column header, and the value is that column in the table
//	 */
//	public HashMap<String,TableColumn> getTableColumnByHeaderMap(boolean update)
//	{
//		if(update == true)
//		{
//			HashMap<String,TableColumn> tempHeadersToColumnsMap = new HashMap<String,TableColumn>();
//			for(int i =0; i < this.getColumnCount(); i++)
//			{
//				Object identifier = 
//				TableColumn nextCol = this.getColumn(i);
//				String nextColHeader = this.getColumnName(i);
//				tempHeadersToColumnsMap.put(nextColHeader, nextCol);
//			}
//			this.headersToColumnsMap = tempHeadersToColumnsMap;
//			return this.headersToColumnsMap;
//		}else{
//			return this.headersToColumnsMap;
//		}
//	}
//	
//	/**
//	 * Update the map of column headers : columns, then return it.
//	 * @return a map, where the key is a column header, and the value is that column in the table
//	 */
//	public HashMap<String,TableColumn> getTableColumnByHeaderMap()
//	{
//		return getTableColumnByHeaderMap(true);
//	}
	
	public int getColumnHeaderWidth(int column)
	{
		if (this.getTableHeader() == null)
			return 0;

		TableColumn tableColumn = this.getColumnModel().getColumn(column);
		Object value = tableColumn.getHeaderValue();
		TableCellRenderer renderer = tableColumn.getHeaderRenderer();

		if (renderer == null)
		{
			renderer = this.getTableHeader().getDefaultRenderer();
		}

		Component c = renderer.getTableCellRendererComponent(this, value, false, false, -1, column);
		return c.getPreferredSize().width;
	}
	
	/*
	 *  Calculate the width based on the widest cell renderer for the
	 *  given column.
	 */
	private int getColumnDataWidth(int column)
	{
		//TODO error checking for no data
		//if (! isColumnDataIncluded) return 0;

		int preferredWidth = 0;
		int maxWidth = this.getColumnModel().getColumn(column).getMaxWidth();

		for (int row = 0; row < this.getRowCount(); row++)
		{
			if(this.getModel().getValueAt(row, column) == null)
			{
				return 10;
			}
			
			preferredWidth = Math.max(preferredWidth, this.getCellDataWidth(row, column));

			//  We've exceeded the maximum width, no need to check other rows

			if (preferredWidth >= maxWidth)
				break;
		}

		return preferredWidth;
	}
	
	/*
	 *  Get the preferred width for the specified cell
	 */
	private int getCellDataWidth(int row, int column)
	{
		//  Inovke the renderer for the cell to calculate the preferred width

		TableCellRenderer cellRenderer = this.getCellRenderer(row, column);
		Component c = this.prepareRenderer(cellRenderer, row, column);
		int width = c.getPreferredSize().width + this.getIntercellSpacing().width;

		return width;
	}
	
	public ArrayList<String> getColumnHeaders(){
		
		ArrayList<String> headers = new ArrayList<String>(this.getColumnCount());
		for(int i = 0; i< this.getColumnCount(); i++)
		{
			headers.add(this.getColumnName(i));
		}
		return headers;
	}
}
