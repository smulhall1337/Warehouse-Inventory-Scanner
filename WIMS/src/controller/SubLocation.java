package controller;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import gui.OrderWindow;

public class SubLocation {
	private int simple_sublo_index, current, max;
	private String subLocationName, warehouse;
	private final String SINGLESPACE = " space available", MULTISPACE = " spaces available";
	private ArrayList<Pallet> palletList;
	
	public SubLocation(String subLocationName, int max, int current, String wareHouseID) {
		//setSimple_sublo_index(simple_sublo_index);
		setSubLocationName(subLocationName);
		setMax(max);
		setCurrent(current);
		setWarehouse(wareHouseID);
	}

	public int getSimple_sublo_index() {
		int i;
		try { 
			i = SQL_Handler.getSimpleSubloIndex(subLocationName);
		} catch (SQLException e) {
			i = 0;
		}
		return i;
	}

	public int getCurrent() {
		return current;
	}

	public int getMax() {
		return max;
	}

	public String getSubLocationName() {
		return subLocationName;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setSimple_sublo_index(int simple_sublo_index) {
		this.simple_sublo_index = simple_sublo_index;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public void setSubLocationName(String subLocationName) {
		this.subLocationName = subLocationName;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	
	public boolean incrementCurrent() {
		if (current < max) {
			current++;
			return true;
		}
		else {
			JOptionPane.showMessageDialog(OrderWindow.getFrame(), subLocationName + " is full, \nSelect another");
			return false;
		}
	}
	
	public boolean decrementCurrent() {
		if (current > 0) {
			current--;
			return true;
		}
		else {
			JOptionPane.showMessageDialog(OrderWindow.getFrame(), subLocationName + " is empty, \nSelect another");
			return false;
		}
	}
	
	public String toString() {
		int remaining = max - current;
		if (remaining == 1)		
			return subLocationName + " with " + remaining + SINGLESPACE;
		else
			return subLocationName + " with " + remaining + MULTISPACE;
	}
	
	public ArrayList<Pallet> getPalletList() {
		return palletList;
	}
	
}
