package controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

/**
*
* @author cpp-qt
*/
public class IDDocument extends PlainDocument {

public static final int MIN_ID_LENGTH = 0;
public static final int MAX_ID_LENGTH = 45;

//this regex matches 1 or more digits, optionally followed by a dash and a number, followed by any number of numbers
public static final String EMPLOYEE_ID_REGEX = "[0-9]+-?[0-9]*?{" + MIN_ID_LENGTH + "," + MAX_ID_LENGTH + "}"; 
private String text = "";

@Override
public void insertString(int offset, String txt, AttributeSet a) {
    try {
        text = getText(0, getLength());
        if ((text + txt).matches(EMPLOYEE_ID_REGEX)) {
            super.insertString(offset, txt, a);
        }
     } catch (Exception ex) {
        Logger.getLogger(IDDocument.class.getName()).log(Level.SEVERE, null, ex);
     }

    }
}