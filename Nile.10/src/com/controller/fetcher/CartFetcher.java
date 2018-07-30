package com.controller.fetcher;
/**
 * @author Shane Bogard
 * Performs SQL queries on the Carts table in the database.
 */

import java.sql.ResultSet;

public class CartFetcher extends DataFetcher{
	
	/**
	 * Constructs a new CartFetcher object with an existing database connector.
	 * @param connector Connector object specifying an already existing connector
	 */
	public CartFetcher(Connector connector) {
		super(connector);
	}
	
	/**
	 * Constructs a new CartFetcher object that creates a new connector to the database.
	 */
	public CartFetcher() {
		super();
	}
	
	/**
	 * Returns all data fields from the Carts table and the price
	 * of each matching item from the Inventory table in the GUI Database
	 * that matches the specified cartId.
	 * @param cartId A string literal representing the cart id
	 * @return ResultSet the resulSet of the SQL query
	 */
	public ResultSet fetchByCartId(String cartId) {
		clearMaps();
		sql = "SELECT c.*, i.price FROM Carts c, Inventory i WHERE c.cartId = ? AND c.itemId = i.itemId";
		stringMap.put(1, cartId);
		return fetchData();
	}
	
	/**
	 * Returns all data fields from the Carts table and the price
	 * of each matching item from the Inventory table in the GUI Database
	 * that matches the specified cartId.
	 * @param cartId A string literal representing the cart id
	 * @return ResultSet the resulSet of the SQL query
	 */
	public ResultSet fetchByCustomerId(String custId) {
		clearMaps();
		sql = "SELECT c.*, i.price FROM Carts c, Inventory i WHERE c.customerId = ? AND c.itemId = i.itemId";
		stringMap.put(1, custId);
		return fetchData();
	}
	
	/**
	 * 
	 * @return
	 */
	public ResultSet fetchItemByCartId(String cartId, String itemId) {
		clearMaps();
		sql = "SELECT c.*, i.price FROM Carts c, Inventory i WHERE c.cartId = ? "
				+ "AND c.itemId = ? AND c.itemId = i.itemId";
		stringMap.put(1, cartId);
		stringMap.put(2, itemId);
		return fetchData();
	}
	
	/** 
	 * Adds a new row to the Carts table/
	 * @param cartId String literal specifying the cartId
	 * @param customerId String literal specifying the customerId
	 * @param itemId String literal specifying the itemId
	 * @param quantity String literal specifying the quantity
	 */
	public void addCartItem(String cartId, String customerId, String itemId, int quantity) {
		clearMaps();
		sql = "INSERT into `Carts` values(?, ?, ?, ?)";
		stringMap.put(1, cartId);
		stringMap.put(2, customerId);
		stringMap.put(3, itemId);
		intMap.put(4, quantity);
		updateData();
	}
	
	/**
	 * Changes the quantity of an item in the Carts table specified by the cartId and itemId.
	 * @param cartId String literal specifying the cartId
	 * @param itemId String literal specifying the itemId
	 * @param quantity Integer value specifying the new quantity for the cart item
	 */
	public void updateQuantity(String cartId, String itemId, int quantity) {
		clearMaps();
		sql = "UPDATE Carts c SET c.quantity = ? WHERE c.cartId = ? AND c.itemId = ?";
		intMap.put(1, quantity);
		stringMap.put(2, cartId);
		stringMap.put(3,  itemId);
		updateData();
	}
		
	/**
	 * Removes a row from the Carts table specified by cartId and itemId.
	 * @param cartId String literal specifying the cartId
	 * @param itemId String literal specifying the itemId
	 */
	public void removeCartItem(String cartId, String itemId) {
		clearMaps();
		sql = "DELETE from Carts WHERE cartId = ? AND itemId = ?";
		stringMap.put(1,  cartId);
		stringMap.put(2, itemId);
		updateData();
	}
	
	/**
	 * Removes all items from a Cart in the Carts table specified by CartId/
	 * @param cartId String literal specifying the cart id
	 */
	public void clearCartByCartId(String cartId) {
		clearMaps();
		sql = "DELETE from Carts WHERE cartId = ?";
		stringMap.put(1,  cartId);
		updateData();
	}
	
	
	
	/**
	 * Removes all items from a Cart in the Carts table specified by Customer Id.
	 * @param customerId String literal specifying the customerId
	 */
	public void clearCartByCustomerId(String customerId) {
		clearMaps();
		sql = "DELETE from Carts WHERE customerId = ?";
		stringMap.put(1,  customerId);
		updateData();
	}
	
	public void returnItem(String cartId, String itemId) {
		clearMaps();
		
	}
}


