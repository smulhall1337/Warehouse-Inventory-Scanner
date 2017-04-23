package controller;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

public class WidthAdjuster extends MouseAdapter {
    public WidthAdjuster(JTable table) {
        this.table = table;
        table.getTableHeader().addMouseListener(this);
    }

    public void mousePressed(MouseEvent evt){
        if (evt.getClickCount() > 1 && usingResizeCursor())
            resize(getLeftColumn(evt.getPoint()));
    }

    private JTableHeader getTableHeader(){
        return table.getTableHeader();
    }

    private boolean usingResizeCursor() {
        Cursor cursor = getTableHeader().getCursor();
        return cursor.equals(EAST) || cursor.equals(WEST);
    }

    private static final Cursor EAST = Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
    private static final Cursor WEST = Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);

    //if near the boundary, will choose left column
    private int getLeftColumn(Point pt) {
        pt.x -= EPSILON;
        return getTableHeader().columnAtPoint(pt);
    }

    private void resize(int col) {
        TableColumnModel tcm = table.getColumnModel();
        TableColumn tc = tcm.getColumn(col);
        TableCellRenderer tcr = tc.getHeaderRenderer();
        if (tcr == null)
           tcr = table.getTableHeader().getDefaultRenderer();
        Object obj = tc.getHeaderValue();
        Component comp = tcr.getTableCellRendererComponent(table, obj, false, false, 0, 0);
        int maxWidth = comp.getPreferredSize().width;

        for(int i=0, ub = table.getRowCount(); i!=ub; ++i) {
            tcr = table.getCellRenderer(i, col);
            obj = table.getValueAt(i, col);
            comp = tcr.getTableCellRendererComponent(table, obj, false, false, i, col);
            int w = comp.getPreferredSize().width;
            if (w > maxWidth)
                maxWidth = w;
        }
        maxWidth += 10; //and room to grow...
        tc.setPreferredWidth(maxWidth); //remembers the value
        tc.setWidth(maxWidth);          //forces layout, repaint
    }

    private JTable table;
    private static final int EPSILON = 5;   //boundary sensitivity
}