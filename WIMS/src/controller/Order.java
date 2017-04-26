package controller;

import java.awt.Container;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.SQLException;

public class Order {
	private String orderNumber, origin, destination, receivedBy, shippedBy;
	private Date placed, shipped, delivered;
	
	private static ArrayList<Pallet> palletList = new ArrayList<Pallet>();
	private static ArrayList<Item> everyItemList = new ArrayList<Item>();
	private static ArrayList<SubLocation> sublocationList = new ArrayList<SubLocation>();
	
	/**
	 * new Order constructor
	 */
	public Order(String number) {
		this.orderNumber = number;		
		
	}

	
	
	public void addOrderToDB(String orderNumber, String origin, String destination, String receivedBy, String shippedBy, Date placed, Date shipped, Date delivered) {
		
		try {
			SQL_Handler.insertNewOrder(orderNumber, origin, destination, receivedBy, shippedBy, placed, shipped, delivered);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	
	/*
	 **********************************************************************
	 ********************************************************************** 
	 ********************List Methods**************************************
	 ********************************************************************** 
	 ********************************************************************** 
	 */
	public static void addToPalletList (Pallet p) {
		palletList.add(p);
	}
	
	public static void addToSubLocationList (SubLocation s) {
		sublocationList.add(s);
	}
	
	
	
	/*
	 **********************************************************************
	 ********************************************************************** 
	 ********************Getters and Setters*******************************
	 ********************************************************************** 
	 ********************************************************************** 
	 */
	
	public String getOrderNumber() {
		return orderNumber;
	}

	public String getOrigin() {
		return origin;
	}

	public String getDestination() {
		return destination;
	}

	public String getReceivedBy() {
		return receivedBy;
	}

	public String getShippedBy() {
		return shippedBy;
	}

	public Date getPlaced() {
		return placed;
	}

	public Date getShipped() {
		return shipped;
	}

	public Date getDelivered() {
		return delivered;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public void setReceivedBy(String receivedBy) {
		this.receivedBy = receivedBy;
	}

	public void setShippedBy(String shippedBy) {
		this.shippedBy = shippedBy;
	}

	public void setPlaced(Date placed) {
		this.placed = placed;
	}

	public void setShipped(Date shipped) {
		this.shipped = shipped;
	}

	public void setDelivered(Date delivered) {
		this.delivered = delivered;
	}

	public static ArrayList<Pallet> getPalletList() {
		return palletList;
	}

	public static ArrayList<Item> getEveryItemList() {
		//loop thru the pallet list call getAllItems() loop thru and add each item
		for (Pallet p : palletList) {
			ArrayList<Item> currentPalletItems = p.getAllItems();
			for (Item temp : currentPalletItems) {
				everyItemList.add(temp);
			}
		}
		return everyItemList;
	}
	
	public static ArrayList<SubLocation> getSublocationList() {
		return sublocationList;
	}

	
	
}//Order class end
