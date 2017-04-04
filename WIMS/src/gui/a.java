package gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

class MyTableModel extends AbstractTableModel {
  protected int sortCol = 0;

  protected boolean isSortAsc = true;

  protected int m_result = 0;
  protected int columnsCount = 1;
  Vector vector = new Vector();
  public MyTableModel(Object[][] data, String[] columnNames) {
    
  }

  @Override
public int getRowCount() {
    return vector == null ? 0 : vector.size();
  }

  @Override
public int getColumnCount() {
    return columnsCount;
  }

  @Override
public String getColumnName(int column) {
    String str = "data";
    if (column == sortCol)
      str += isSortAsc ? " >>" : " <<";
    return str;
  }

  @Override
public boolean isCellEditable(int nRow, int nCol) {
    return false;
  }

  @Override
public Object getValueAt(int nRow, int nCol) {
    if (nRow < 0 || nRow >= getRowCount())
      return "";
    if(nCol>1){
      return "";
    }
    return vector.elementAt(nRow);
  }

  public String getTitle() {
    return "data ";
  }

  class ColumnListener extends MouseAdapter {
    protected JTable table;

    public ColumnListener(JTable t) {
      table = t;
    }

    @Override
	public void mouseClicked(MouseEvent e) {
      TableColumnModel colModel = table.getColumnModel();
      int columnModelIndex = colModel.getColumnIndexAtX(e.getX());
      int modelIndex = colModel.getColumn(columnModelIndex)
          .getModelIndex();

      if (modelIndex < 0)
        return;
      if (sortCol == modelIndex)
        isSortAsc = !isSortAsc;
      else
        sortCol = modelIndex;

      for (int i = 0; i < columnsCount; i++) { 
        TableColumn column = colModel.getColumn(i);
        column.setHeaderValue(getColumnName(column.getModelIndex()));
      }
      table.getTableHeader().repaint();

      Collections.sort(vector,new MyComparator(isSortAsc));
      table.tableChanged(new TableModelEvent(MyTableModel.this));
      table.repaint();
    }
  }
}

class MyComparator implements Comparator {
  protected boolean isSortAsc;

  public MyComparator( boolean sortAsc) {
    isSortAsc = sortAsc;
  }

  @Override
public int compare(Object o1, Object o2) {
    if (!(o1 instanceof Integer) || !(o2 instanceof Integer))
      return 0;
    Integer s1 = (Integer) o1;
    Integer s2 = (Integer) o2;
    int result = 0;
    result = s1.compareTo(s2);
    if (!isSortAsc)
      result = -result;
    return result;
  }

  @Override
public boolean equals(Object obj) {
    if (obj instanceof MyComparator) {
      MyComparator compObj = (MyComparator) obj;
      return compObj.isSortAsc == isSortAsc;
    }
    return false;
  }
}
