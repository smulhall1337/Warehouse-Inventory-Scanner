package controller;
import java.awt.event.KeyEvent;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 * Class for checking entries
 * @author Brian
 *
 */
abstract public class Valid {
	private static final String NUMREGEX = "\\d+", PRICEREGEX = "[1-9]+([.][0-9]{1,2})?";
	
	/**
	 * True if the string is not empty, false if the string is ""
	 * @param s
	 * @return boolean
	 */
	public static boolean validString(String s) {
		if (!(s.equals(""))) {
			return true;
		}
		else {
			System.out.println("Error: Empty String");
			return false;
		}
	}//validString end
	
	/**
	 * True if the string can be converted to a price matching [1-9]+([.][0-9]{1,2})?
	 * @param s
	 * @return boolean
	 */
	public static boolean validPrice(String s) {
		/*try {
			Double.parseDouble(s);
			return true;
		} catch (Exception ee) {
			System.out.println("Error: Input not valid, requires Double");
			return false;
		} */
		
		return (s.matches(PRICEREGEX));
	}//validDouble end
	
	/**
	 * True if the string can be converted to a double
	 * @param s
	 * @return boolean
	 */
	public static boolean validDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (Exception ee) {
			System.out.println("Error: Input not valid, requires Double");
			return false;
		} 
	}//validDouble end
	
	/**
	 * True if the string can be converted to an id matching \\d+
	 * @param s
	 * @return boolean
	 */
	public static boolean validID(String s) {	
		return (s.matches(NUMREGEX));
	}//validID end
	
	public static boolean validInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception ee) {
			System.out.println("Error: Input not valid, requires Integer");
			return false;
		}		
	}
	
	/**
	 * True if itemNumber text field is not empty and has an integer
	 * @return
	 */
	public static boolean validItemNumber(String s) {
		if (validString(s) && validID(s)) {
			return true;
		} 
		else {
			System.out.println("Invalid Entry for Item Number");
			return false;
		}
	}
	
	/**
	 * True if itemName text field is not empty
	 * @return
	 */
	public static boolean validItemName(String s) {
		if (validString(s)) {
			return true;
		}
		else {
			System.out.println("Invalid Entry for Item Name");
			return false;
		}
	}
	
	/**
	 * True if itemWeight text field is not empty and has an integer
	 * @return
	 */
	public static boolean validItemWeight(String s) {
		if (validString(s) && validInt(s)) {
				return true;
			} 
			else {
				System.out.println("Invalid Entry for Item Weight");
				return false;
			}
	}
	
	/**
	 * True if itemPrice text field is not empty and has a double
	 * @return
	 */
	public static boolean validItemPrice(String s) {
		if (validString(s) && validPrice(s)) {
				return true;
			} 
			else {
				System.out.println("Invalid Entry for Item Price, must be at least $1.00, nothing you're storing is cheaper than that cmon.");
				return false;
			}
	}
	
	/**
	 * True if Current Stock text field is not empty and has an integer
	 * @return
	 */
	public static boolean validCStock(String s) {
		if (validString(s) && validInt(s)) {
				return true;
			} 
			else {
				System.out.println("Invalid Entry for Current Stock");
				return false;
			}
	}
	
	/**
	 * True if Restock text field is not empty and has an integer
	 * @return
	 */
	public static boolean validRStock(String s) {
		if (validString(s) && validInt(s)) {
				return true;
			} 
			else {
				System.out.println("Invalid Entry for Restock Threshold");
				return false;
			}
	}
	
	/**
	 * True if Add to Stock text field is not empty and has an integer
	 * @return
	 */
	public static boolean validAdd(String s) {
		if (validString(s) && validInt(s)) {
				return true;
			} 
			else {
				System.out.println("Invalid Entry for Add to Stock");
				return false;
			}
	}
	
	/**
	 * True if every text field is not empty and has an appropriate entry
	 * @param INum itemNumber String
	 * @param IName itemName String
	 * @param IW itemWeight String
	 * @param IP itemPrice String
	 * @param ICS itemCurrentStock String
	 * @param IRS itemRestockThreshold String
	 * @return boolean
	 */
	public static boolean allEntriesValid(String INum, String IName, String IW, String IP, String ICS, String IRS) {
			return validItemNumber(INum) && validItemName(IName) && validItemWeight(IW)
					&& validItemPrice(IP) && validCStock(ICS) && validRStock(IRS);	
		
	}
	
	/**
	 * True if every text field is not empty and has an appropriate entry
	 * @param INum itemNumber String
	 * @param IName itemName String
	 * @param IW itemWeight String
	 * @param IP itemPrice String
	 * @param ICS itemCurrentStock String
	 * @param IRS itemRestockThreshold String
	 * @Param IA itemAdd String
	 * @return boolean
	 */
	public static boolean allEntriesValid(String INum, String IName, String IW, String IP, String ICS, String IRS, String IA) {
		return validItemNumber(INum) && validItemName(IName) && validItemWeight(IW)
				&& validItemPrice(IP) && validCStock(ICS) && validRStock(IRS) && validAdd(IA);
	}
	
	/**
	 * Input checker that only accepts digits(and backspace/delete) as input
	 * @param c 
	 * @param evt
	 */
	public static void intInput(char c, KeyEvent evt) {
		if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
			evt.consume();
		}
	}//IntInput end
	
	public static void dblInput(char c, KeyEvent evt) {
		if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE || c == KeyEvent.VK_PERIOD || c == KeyEvent.VK_DECIMAL)) {
			evt.consume();
		}
	}//DblInput end
	
	/**
	 * This method loops through the current listModel and compares the input
	 * to make sure it is not already present in the list
	 * @param palletID
	 * @return true if the input is NOT FOUND
	 */
	public static boolean notInCurrentList(String ID, DefaultListModel listModel) {
		boolean NotInCurrentList = true; //assume its not in the list
		
		for (int i=0; i < listModel.getSize(); i++) {
			if (ID.equals(listModel.getElementAt(i))) //if it is return false
				NotInCurrentList = false;
		}
		
		return NotInCurrentList;
	}

}
