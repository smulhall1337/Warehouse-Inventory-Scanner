package controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

/**
*
* @author cpp-qt
*/
public class NameDocument extends PlainDocument {

public static final int MIN_NAME_LENGTH = 0;
public static final int MAX_NAME_LENGTH = 45;

//this regex matches characters from any language, as well as dashes and apostrophes.
public static final String NAME_REGEX = "^[\\p{L} .'-]+$"; 
private String text = "";

@Override
public void insertString(int offset, String txt, AttributeSet a) {
    try {
        text = getText(0, getLength());
        if ((text + txt).matches(NAME_REGEX)) {
            super.insertString(offset, txt, a);
        }
     } catch (Exception ex) {
        Logger.getLogger(NameDocument.class.getName()).log(Level.SEVERE, null, ex);
     }

    }
}