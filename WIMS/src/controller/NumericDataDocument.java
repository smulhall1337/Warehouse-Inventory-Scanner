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

public class NumericDataDocument extends PlainDocument {

	//this regex matches 1 or more digits, optionally followed by a dash and a number, followed by any number of numbers
	//private static final String NUMERIC_FIELD_ENTRY_REGEXSTRING = "([+-]?\\d*\\.?\\d*)";
	public static final String NUMERIC_REGEX = "([+-]?\\d*\\.?\\d*)"; 
	private String text = "";
	private int minInputLength;
	private int maxInputLength;

	@Override
	public void insertString(int offset, String txt, AttributeSet a) {
	    try {
	        text = getText(0, getLength());
	        if ((text + txt).matches(NUMERIC_REGEX)) {
	            super.insertString(offset, txt, a);
	        }
	     } catch (Exception ex) {
	        Logger.getLogger(IDDocument.class.getName()).log(Level.SEVERE, null, ex);
	     }
	}
//	public DocIntFilter(int minInputLength, int maxInputLength) {
//		this.minInputLength = minInputLength;
//		this.maxInputLength = maxInputLength;
//	}
//	
//	@Override
//	public void insertString(FilterBypass fb, int offset, String string,
//			AttributeSet attr) throws BadLocationException {
//
//		Document doc = fb.getDocument();
//		StringBuilder sb = new StringBuilder();
//		sb.append(doc.getText(0, doc.getLength()));
//		sb.insert(offset, string);
//		if ((text + txt).matches(NUMERIC_REGEX)) {
//            super.insertString(offset, txt, a);
//        }
//		if (test(sb.toString())) {
//			super.insertString(fb, offset, string, attr);
//		} else {
//			
//		}
//	}
//	
//	public boolean isCorrectLength(String text)
//	{
//		return text.length() > this.minInputLength
//				&& text.length() <= this.maxInputLength;
//	}
//	
//	private boolean test(String text) {
//		try {
//			Integer.parseInt(text);
//			if (isCorrectLength(text))
//				return true;
//			return false;
//		} catch (NumberFormatException e) {
//			return false;
//		}
//	}
//
//	@Override
//	public void replace(FilterBypass fb, int offset, int length, String text,
//			AttributeSet attrs) throws BadLocationException {
//
//		Document doc = fb.getDocument();
//		StringBuilder sb = new StringBuilder();
//		sb.append(doc.getText(0, doc.getLength()));
//		sb.replace(offset, offset + length, text);
//
//		if (test(sb.toString())) {
//			super.replace(fb, offset, length, text, attrs);
//		} else {
//			
//		}
//
//	}
//
//	@Override
//	public void remove(FilterBypass fb, int offset, int length)
//			throws BadLocationException {
//		Document doc = fb.getDocument();
//		StringBuilder sb = new StringBuilder();
//		sb.append(doc.getText(0, doc.getLength()));
//		sb.delete(offset, offset + length);
//
//		if (test(sb.toString())) {
//			super.remove(fb, offset, length);
//		} else {
//			
//		}
//
//	}
}