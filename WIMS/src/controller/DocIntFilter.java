package controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

public class DocIntFilter extends PlainDocument {

	// this regex matches 1 or more digits, optionally followed by a dash
	// and a number, followed by any number of numbers
	public static final String INT_REGEX = "[0-9]*";
	private String text = "";

	@Override
	public void insertString(int offset, String txt, AttributeSet a) {
		try {
			text = getText(0, getLength());
			if ((text + txt).matches(INT_REGEX)) {
				super.insertString(offset, txt, a);
			}
		} catch (Exception ex) {
			Logger.getLogger(EmployeeIDDocument.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
