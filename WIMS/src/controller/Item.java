package controller;

import java.sql.SQLException;

public class Item {
	private String itemNumber, itemName;
	private int quantity, weight;
	
	public Item(String itemNumber, int quantity) {
		this.itemNumber = itemNumber;
		this.quantity = quantity;
		setWeight();
		setName();
	}
	
	public String getItemName() {
		return itemName;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public String getQuantityAsString() {
		return "" + quantity;
	}
	
	public int getWeight() {
		return weight;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	private void setWeight() {
		try {
			this.weight = SQL_Handler.getItemWeight(itemNumber);
		} catch (SQLException e) {
			weight = 0;
		}
	}
	
	public void setName() {
		try {
			this.itemName = SQL_Handler.getItemName(itemNumber);
		} catch (SQLException e) {
			this.itemName = "";
		}
	}
	
	public String toString() {
		return itemNumber;		
	}
}
