package com.controller.handler;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import com.controller.fetcher.Connector;
import com.controller.fetcher.OrderFetcher;
import application.model.Order;

public class OrderHandler extends DataHandler<OrderFetcher>{
	
	/** */
	private ArrayList<Order> orders;
	
	/**
	 * 
	 * @param fetcher
	 */
	public OrderHandler(OrderFetcher fetcher) {
		super(fetcher);
		orders = new ArrayList<Order>();
	}
	
	/**
	 * 
	 * @param connector
	 */
	public OrderHandler(Connector connector) {
		this(new OrderFetcher(connector));
	}
	
	/**
	 * 
	 */
	public OrderHandler() {
		this(new OrderFetcher());
	}
	
	/**
	 * Returns all the Orders
	 * @return ArrayList of order objects
	 */
	public ArrayList<Order> getAll(){
		results = fetcher.fetchAll();
		parseResults();
		return orders;
	}
	
	/**
	 * 
	 * @param orderId
	 * @return
	 */
	public Order getById(String orderId) {
		results = fetcher.fetchOrderById(orderId);
		parseResults();
		return orders.get(0);
	}
	
	/**
	 * 
	 * @param cartId
	 * @return
	 */
	public Order getByCartId(String cartId) {
		results = fetcher.fetchOrderByCart(cartId);
		parseResults();
		return orders.get(0);
	}
	
	/**
	 * Adds a new row to the Order table.
	 * @param orderId
	 * @param cartId
	 * @param orderDt
	 * @param shipDt
	 * @param trackNm
	 */
	public void add(String cartId, Date orderDt, Date shipDt, String trackNm) {
		fetcher.addOrder(cartId, orderDt, shipDt, trackNm);
	}
	
	/**
	 * Adds a new row to the Order table.
	 * @param orderId
	 * @param cartId
	 * @param orderDt
	 * @param shipDt
	 */
	public void add(String cartId, Date orderDt, Date shipDt) {
		fetcher.addOrder(cartId, orderDt, shipDt);
	}
	
	/**
	 * Adds a new row to the Order table.
	 * @param orderId
	 * @param cartId
	 * @param orderDt
	 */
	public void add(String cartId, Date orderDt) {
		fetcher.addOrder(cartId, orderDt);
	}
	
	/**
	 * Adds a new row to the Order table.
	 * @param orderId
	 * @param cartId
	 * @param orderDt
	 */
	public void add(String cartId) {
		fetcher.addOrder(cartId);
	}
	
	/**
	 * Updates an order row in the Order table.
	 * @param orderId String literal specifying the Order ID
	 */
	public void updateById(String orderId, String cartId, Date orderDt, Date shipDt, String trackNm) {
		fetcher.updateOrderById(orderId, cartId, orderDt, shipDt, trackNm);
	}
	
	/**
	 * Updates an order row in the Order table.
	 * @param orderId String literal specifying the Order ID
	 */
	public void updateByCartId(String cartId, String orderId, Date orderDt, Date shipDt, String trackNm) {
		fetcher.updateOrderByCart(cartId, orderId, orderDt, shipDt, trackNm);
	}
	
	/**
	 * Removes a row from the Order table.
	 * @param orderId String literal specifying the orderId
	 */
	public void removeById(String orderId) {
		fetcher.removeOrderById(orderId);
	}
	
	/**
	 * Removes a row from the Order table.
	 * @param cartId String literal specifying the cartId
	 */
	public void removeByCart(String cartId) {
		fetcher.removeOrderByCart(cartId);
	}
	
	/**
	 * 
	 */
	protected void parseResults(){
		orders.clear();
		try {
			while(results.next()) {
				Calendar orderDt = Calendar.getInstance();
				orderDt.setTime(results.getDate("orderDt"));
				Calendar shipDt = Calendar.getInstance();
				if(results.getDate("shipDt") != null) {
					shipDt.setTime(results.getDate("shipDt"));
				}else {
					shipDt.set(1900, 0, 0, 0, 0);
				}
				orders.add(new Order(results.getString("orderId"), 
						new CartHandler(fetcher.connector).getById(results.getString("cartId")), 
						orderDt, 
						shipDt, 
						results.getString("trackNm")));
			}
		}catch(SQLException e) {
			System.err.println(this.getClass().getName() + ":" + e.getMessage());
		}
		
	}
}
