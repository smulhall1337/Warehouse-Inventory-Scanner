package controller;

/*******************************/
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
public abstract class ExcelExporter  {
    public ExcelExporter() { }
    public static void exportTable(JTable table, File file) throws IOException {
    	System.out.println("in export table");
    	System.out.println(file);
        TableModel model = table.getModel();
        FileWriter out = new FileWriter(file);

        for(int i=0; i < model.getColumnCount(); i++) {
            out.write(model.getColumnName(i) + "\t");
        }
        out.write("\n");
        for(int i=0; i< model.getRowCount(); i++) {
            for(int j=0; j < model.getColumnCount(); j++) {
                out.write(model.getValueAt(i,j).toString()+"\t");
            }
            out.write("\n");
        }
        out.close();
        System.out.println("write out to: " + file);
    }
    
    public static void saveTable(JTable table, File file)throws IOException
    {
    	System.out.println("in save table");
    	System.out.println(file);
      BufferedWriter bfw = new BufferedWriter(new FileWriter(file));
      for(int i = 0 ; i < table.getColumnCount() ; i++)
      {
    	  System.out.println(i);
        bfw.write(table.getColumnName(i));
        bfw.write("\t");
      }

      for (int i = 0 ; i < table.getRowCount(); i++)
      {
        bfw.newLine();
        System.out.println(table.getColumnCount() + "COL COUNT");
        for(int j = 0 ; j < table.getColumnCount();j++)
        {
          bfw.write((String)(table.getValueAt(i,j)));
          bfw.write("\t");
          System.out.println(j);
        }
      }
      bfw.close();
    }
}