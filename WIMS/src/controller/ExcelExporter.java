package controller;

/*******************************/
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;

public abstract class ExcelExporter {
	public ExcelExporter() {
	}

	public static void exportTable(JTable table, File file) throws IOException {
		System.out.println("in export table");
		System.out.println(file);
		TableModel model = table.getModel();
		FileWriter out = new FileWriter(file);

		for (int i = 0; i < model.getColumnCount(); i++) {
			out.write(model.getColumnName(i) + "\t");
		}
		out.write("\n");
		for (int i = 0; i < model.getRowCount(); i++) {
			for (int j = 0; j < model.getColumnCount(); j++) {
				out.write(model.getValueAt(i, j).toString() + "\t");
			}
			out.write("\n");
		}
		out.close();
		System.out.println("write out to: " + file);
	}

	public static void saveTable(JTable table, File file) throws IOException {
		WIMSTableModel model = (WIMSTableModel) table.getModel();
		BufferedWriter bfw = new BufferedWriter(new FileWriter(file));
		for (int i = 0; i < model.getColumnCount(); i++) {
			bfw.write(model.getColumnName(i));
			bfw.write(",");
		}

		int rows = model.getRowCount();
		int cols = model.getColumnCount();
		for (int row = 0; row < rows; row++) {
			Object[] rowArray = model.getRowAt(row);
			bfw.newLine();
			for (int col = 0; col < cols; col++) 
			{
				String nextVal = rowArray[col] + "";
				if(nextVal.equals("null"))
					nextVal = "";
				bfw.write(nextVal);
				bfw.write(",");
			}
		}
		bfw.close();
	}
}