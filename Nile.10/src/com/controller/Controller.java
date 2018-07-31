package com.controller;

import com.controller.fetcher.Connector;
import com.controller.handler.CartHandler;
import com.controller.handler.CustomerHandler;
import com.controller.handler.InventoryHandler;
import com.controller.handler.OrderHandler;

import application.model.Customer;

public class Controller {
	
	private CartHandler cartHandler;
	
	private CustomerHandler customerHandler;
	
	private OrderHandler orderHandler;
	
	private InventoryHandler inventoryHandler;
	
	public Controller(Connector connector) {
		cartHandler = new CartHandler(connector);
		customerHandler = new CustomerHandler(connector);
		orderHandler = new OrderHandler(connector);
		inventoryHandler = new InventoryHandler(connector);
	}
	
	public Controller() {
		this(new Connector());
	}
	
	/**
	 * 
	 * @return
	 */
	public CartHandler cartHandle() {
		return cartHandler;
	}
	
	/**
	 * 
	 * @return
	 */
	public CustomerHandler customerHandle() {
		return customerHandler;
	}
	
	public InventoryHandler inventoryHandle() {
		return inventoryHandler;
	}
	
	/**
	 * 
	 * @return
	 */
	public OrderHandler orderHandle(){
		return orderHandler;
	}
}
