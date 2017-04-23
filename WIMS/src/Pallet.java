import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import com.ibm.icu.text.SimpleDateFormat;

public class Pallet {
	private String palletID, sublocation, notes, orderNumber;
	private ArrayList<Item> items = new ArrayList<Item>();
	private int weight, pieceCount;
	private final int length = 10, width = 10, height = 10;
	
	public Pallet(String palletID, String sublocation) {
		this.palletID = palletID;
		this.sublocation = sublocation;
		
	}
	
	public void addItem(String itemNumber, int itemQty) {
		items.add(new Item(itemNumber, itemQty));
	}
	
	public void removeItem(String itemNumber) {
		int n = ItemsInPalletPanel.getCurrentList().getSelectedIndex();
		items.remove(n);
	}
	
	public int getWeight() {
		for (Item temp : items) {
			weight += temp.getWeight();
		}
		return weight;
	}
	
	public int getPieceCount() {		
		for (Item temp : items) {
			pieceCount += temp.getQuantity();
		}
		return pieceCount;
	}
	
	public void addThisToDB() {
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd").format(new java.util.Date());
		weight = getWeight();
		pieceCount = getPieceCount();
		try {
			SQL_Handler.insertNewPallet(palletID, pieceCount, weight, length, width, height, timeStamp, "", notes, orderNumber, sublocation);
		} catch (SQLException e) {
			
		}
	}
	
	public void printThis() {
		System.out.println("Pallet ID: " + palletID + " is stored in sublocation " + sublocation + ". It has " + getPieceCount() + " items on it!");
	}

	//Getters and Setters
	public String getID() {
		return palletID;		
	}
	
	public String getSubLocation() {
		return sublocation;
	}
	
	public ArrayList<Item> getAllItems() {
		ArrayList<Item> fullItemList = new ArrayList<Item>();
		for (Item temp : items) {
			fullItemList.add(temp);
		}
		return fullItemList;
	}

	
	
	
}//Pallet end
