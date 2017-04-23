package controller;

import java.awt.ScrollPane;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class WIMSTableScrollPane extends ScrollPane
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5904465776331521879L;
	private JTable table;
    public WIMSTableScrollPane(JTable table)
    {
    	table = new JTable(){
            public boolean getScrollableTracksViewportWidth()
            {
                return getPreferredSize().width < getParent().getWidth();
            }
        };
        table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
        final JScrollPane scrollPane = new JScrollPane( table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
    }
    
    public void setViewportView(JTable table)
    {
    	this.table = table;
    }
}