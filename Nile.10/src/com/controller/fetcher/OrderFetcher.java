package com.controller.fetcher;
/**
 * @author Shane Bogard
 */

import java.sql.Date;
import java.sql.ResultSet;
import java.util.Calendar;

public class OrderFetcher extends DataFetcher {
	
	public OrderFetcher(Connector connector) {
		super(connector);
	}
	
	public OrderFetcher() {
		super();
	}
	
	public ResultSet fetchAll() {
		clearMaps();
		sql = "SELECT o.* FROM Orders o";
		return fetchData();
	}
	
	/**
	 * Returns a row from the Order table specified by orderId
	 * @param orderId String literal specifying the order ID
	 * @return ResultSet results of a sql query
	 */
	public ResultSet fetchOrderById(String orderId) {
		clearMaps();
		sql = "SELECT o.* FROM Orders o WHERE o.orderId = ?";
		stringMap.put(1, orderId);
		return fetchData();
	}
	
	/**
	 * Returns a row from the Order table specified by orderId
	 * @param cartId String literal specifying the cart ID
	 * @return ResultSet results of a sql query
	 */
	public ResultSet fetchOrderByCart(String cartId) {
		clearMaps();
		sql = "SELECT o.* FROM Orders o WHERE o.cartId = ?";
		stringMap.put(1, cartId);
		return fetchData();
	}
	
	/**
	 * Adds a new row to the Order table.
	 * @param orderId
	 * @param cartId
	 * @param orderDt
	 * @param shipDt
	 * @param trackNm
	 */
	public void addOrder(String cartId, Date orderDt, Date shipDt, String trackNm) {
		clearMaps();
		sql = "INSERT INTO Orders (cartId, orderDt, shipDt, trackNm) VALUES (?, ?, ?, ?)";
		stringMap.put(1, cartId);
		dateMap.put(2, orderDt);
		dateMap.put(3, shipDt);
		stringMap.put(4,trackNm);
		updateData();
	}
	
	/**
	 * Adds a new row to the Order table.
	 * @param orderId
	 * @param cartId
	 * @param orderDt
	 * @param shipDt
	 */
	public void addOrder(String cartId, Date orderDt, Date shipDt) {
		clearMaps();
		sql = "INSERT INTO Orders (cartId, orderDt, shipDt) VALUES (?, ?, ?)";
		stringMap.put(1, cartId);
		dateMap.put(2, orderDt);
		dateMap.put(3, shipDt);
		updateData();
	}
	
	/**
	 * Adds a new row to the Order table.
	 * @param orderId
	 * @param cartId
	 * @param orderDt
	 */
	public void addOrder(String cartId, Date orderDt) {
		clearMaps();
		sql = "INSERT INTO Orders (cartId, orderDt) VALUES (?, ?)";
		stringMap.put(1, cartId);
		dateMap.put(2, orderDt);
		updateData();
	}
		
	/**
	 * Adds a new row to the Order table.
	 * @param cartId
	 */
	public void addOrder(String cartId) {
		clearMaps();
		sql = "INSERT INTO Orders (cartId, orderDt) VALUES (?, ?)";
		stringMap.put(1, cartId);
		dateMap.put(2, new Date(Calendar.getInstance().getTimeInMillis()));
		updateData();
	}
	
	/**
	 * Updates an order row in the Order table.
	 * @param orderId String literal specifying the Order ID
	 */
	public void updateOrderById(String orderId, String cartId, Date orderDt, Date shipDt, String trackNm) {
		clearMaps();
		sql = "UPDATE Orders o SET o.cartId = ?, o.orderDt = ?, o.shipDt = ?, o.trackNm = ? "
				+ "WHERE o.orderId = ?";
		stringMap.put(1, cartId);
		dateMap.put(2, orderDt);
		dateMap.put(3, shipDt);
		stringMap.put(4, trackNm);
		stringMap.put(5, orderId);
		updateData();
	}
	
	/**
	 * Updates an order row in the Order table.
	 * @param orderId String literal specifying the Order ID
	 */
	public void updateOrderByCart(String cartId, String orderId, Date orderDt, Date shipDt, String trackNm) {
		clearMaps();
		sql = "UPDATE Orders o SET o.orderId = ?, o.orderDt = ?, o.shipDt = ?, o.trackNm = ? "
				+ "WHERE o.cartId = ?";
		stringMap.put(1, orderId);
		dateMap.put(2, orderDt);
		dateMap.put(3, shipDt);
		stringMap.put(4, trackNm);
		stringMap.put(5, cartId);
		updateData();
	}
	
	/**
	 * Removes a row from the Order table.
	 * @param orderId String literal specifying the orderId
	 */
	public void removeOrderById(String orderId) {
		clearMaps();
		sql = "DELETE FROM Orders WHERE orderId = ?";
		stringMap.put(1, orderId);
		updateData();
	}
	
	/**
	 * Removes a row from the Order table.
	 * @param cartId String literal specifying the cartId
	 */
	public void removeOrderByCart(String cartId) {
		clearMaps();
		sql = "DELETE FROM Orders WHERE cartId = ?";
		stringMap.put(1, cartId);
		updateData();
	}

}
