import java.sql.SQLException;

public class Item {
	private String itemNumber;
	private int quantity, weight;
	
	public Item(String itemNumber, int quantity) {
		this.itemNumber = itemNumber;
		this.quantity = quantity;
		setWeight();
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public int getQuantity() {
		return quantity;
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
}
