package com.controller;

import com.controller.fetcher.Connector;
import com.controller.handler.CartHandler;
import com.controller.handler.CustomerHandler;
import com.controller.handler.InventoryHandler;
import com.controller.handler.OrderHandler;

public class Controller {
	
	private Connector connector = new Connector();
	
	private CartHandler cartHandler = new CartHandler(connector);
	
	private CustomerHandler customerHandler = new CustomerHandler(connector);
	
	private OrderHandler orderHandler = new OrderHandler(connector);
	
	private InventoryHandler inventoryHandler = new InventoryHandler(connector);
	
	
	
}
