package com.controller.handler;
/**
 * @author Shane Bogard
 * @author Manuel Ben Bravo 
 */

import java.sql.SQLException;
import com.controller.handler.CartHandler;
import com.controller.fetcher.Connector;
import com.controller.fetcher.CustomerFetcher;
import application.model.Address;
import application.model.Customer;
import application.model.Name;

public class CustomerHandler extends DataHandler<CustomerFetcher> {
	
	/** the customer */
	private Customer customer;
	
	/**
	 * Constructs a new CustomerHandler with a existing fetcher.
	 * @param fetcher
	 */
	public CustomerHandler(CustomerFetcher fetcher) {
		super(fetcher);
	}
	
	/**
	 * Constructs a new CustomerHandler with an existing connector to the database.
	 * @param connector
	 */
	public CustomerHandler(Connector connector) {
		this();
		fetcher.connector = connector;
	}
	
	/**
	 * Constructs a new CustomerHandler that establishes a new connector to the database.
	 */
	public CustomerHandler() {
		this(new CustomerFetcher());
	}
	
	/**
	 * Returns a Customer specified by customerId and password.
	 * @param customerId
	 * @param email
	 * @return Customer object
	 */
	public Customer getCustomerById(String customerId, String password) {
		System.out.println("VERIFYING CUSTOMER");
		results = fetcher.fetchCustomerById(customerId, password);
		parseResults();
		return customer;
	}
	
	/**
	 * Returns a Customer specified by email and password.
	 * @param email
	 * @param password
	 * @return Customer object
	 */
	public Customer getCustomerByEmail(String email, String password) {
		System.out.println("VERIFYING CUSTOMER");
		results = fetcher.fetchCustomerByEmail(email, password);
		parseResults();
		return customer;
	}
	
	
	
	/**
	 * 
	 */
	protected void parseResults(){
		customer = null;
		try {
			while(results.next()) {
				customer = new Customer(results.getString("email"), 
					results.getString("customerId"), 
					new Name(results.getString("firstNm"), 
							(results.getString("middleNm") != null) ? results.getString("middleNm") : "", 
							results.getString("lastNm")), 
					new Address((results.getString("address") != null) ? results.getString("address") : ""),
					results.getString("phoneNum"), 
					new CartHandler(fetcher.connector).getCartById(results.getString("curCart")));
			}
		} catch(SQLException e) {
			System.err.println(this.getClass().getName() + ":" + e.getMessage());
		}
		
	}
}
