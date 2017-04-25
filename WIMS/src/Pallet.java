import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Pallet {
	private String palletID, notes = "", orderNumber = OrderWindow.getCurrentOrder().getOrderNumber();
	private SubLocation sublocation;
	private ArrayList<Item> items = new ArrayList<Item>();
	private int weight, pieceCount;
	private final int length = 10, width = 10, height = 10;
	
	public Pallet(String palletID, SubLocation sublocation) {
		this.palletID = palletID;
		this.sublocation = sublocation;
	}
	
	public void addItem(Item i) {
		items.add(i);
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
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		weight = getWeight();
		pieceCount = getPieceCount();
		try {
			SQL_Handler.insertNewPallet(palletID, pieceCount, weight, length, width, height, date, date, notes, orderNumber, sublocation.getSimple_sublo_index());
			//add items to the pallets_items table
		} catch (SQLException e) {
			
		}
	}
	
	public void printThis() {
		System.out.println("Pallet ID: " + palletID + " is stored in sublocation " + sublocation.getSubLocationName() + ". It has " + getPieceCount() + " items on it!");
	}

	//Getters and Setters
	public String getID() {
		return palletID;		
	}
	
	public SubLocation getSubLocation() {
		return sublocation;
	}
	
	public ArrayList<Item> getAllItems() {
		ArrayList<Item> fullItemList = new ArrayList<Item>();
		for (Item temp : items) {
			fullItemList.add(temp);
		}
		return fullItemList;
	}

	public String toString() {
		return palletID;
	}

	
	
	
}//Pallet end
