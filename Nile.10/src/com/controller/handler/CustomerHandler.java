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
import application.model.Cart;
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
		this(new CustomerFetcher(connector));
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
	public Customer getById(String customerId, String password) {
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
	public Customer getByEmail(String email, String password) {
		System.out.println("VERIFYING CUSTOMER");
		results = fetcher.fetchCustomerByEmail(email, password);
		parseResults();
		return customer;
	}
	
	/**
	 * Adds a new Customer to Customers
	 * @param email
	 * @param password
	 * @param customerId
	 * @param firstNm
	 * @param middleNm
	 * @param lastNm
	 * @param address
	 * @param phoneNum
	 * @param curCart
	 */
	public void add(String email, String password, String customerId, String firstNm,
						String middleNm, String lastNm, String address, String phoneNum, String curCart) {
		fetcher.addCustomer(email, password, customerId, firstNm, middleNm, lastNm, address, phoneNum, curCart);
	}
	
	/**
	 * Adds a new customer row to the Customers table.
	 * @param email
	 * @param password
	 * @param firstNm
	 * @param lastNm
	 */
	public void add(String email, String password, String firstNm, String lastNm) {
		fetcher.addCustomer(email, password, firstNm, lastNm);
	}
	
	/**
	 * Adds a new customer row to the Customers table.
	 * @param customer
	 * @param password
	 */
	public void add(Customer customer, String password) {
		add(customer.getEmail(), password, customer.getId(), customer.getName().getFirst(),
				customer.getName().getMiddle(), customer.getName().getLast(), customer.getAddress().toString(),
				customer.getPhoneNum(), customer.getCart().getCartId());
	}
	
	/**
	 * Updates a customer row in the Customers specified by the Customer ID
	 * @param customerId
	 * @param email
	 * @param firstNm
	 * @param middleNm
	 * @param lastNm
	 * @param address
	 * @param phoneNum
	 * @param curCart
	 */
	public void updateById(String customerId, String email, String firstNm, String middleNm,
					String lastNm, String address, String phoneNum, String curCart) {
		fetcher.updateCustomerById(customerId, email, firstNm, middleNm, lastNm, address, phoneNum, curCart);
	}
	
	/**
	 * Updates a customer row in the Customers specified by the Customer ID
	 * @param customerId
	 * @param email
	 * @param firstNm
	 * @param middleNm
	 * @param lastNm
	 * @param address
	 * @param phoneNum
	 */
	public void updateById(String customerId, String email, String firstNm, String middleNm,
			String lastNm, String address, String phoneNum) {
		fetcher.updateCustomerById(customerId, email, firstNm, middleNm, lastNm, address, phoneNum);
	}
	
	/**
	 * Updates a customer row in the Customers table specified by the Customer's email
	 * @param email
	 * @param firstNm
	 * @param middleNm
	 * @param lastNm
	 * @param address
	 * @param phoneNum
	 * @param curCart
	 */
	public void updateByEmail(String email, String firstNm, String middleNm, String lastNm, 
			String address, String phoneNum, String curCart) {
		fetcher.updateCustomerByEmail(email, firstNm, middleNm, lastNm, address, phoneNum);
	}
	

	/**
	 * Updates a customer row in the Customers table specified by the Customer's email
	 * @param email
	 * @param firstNm
	 * @param middleNm
	 * @param lastNm
	 * @param address
	 * @param phoneNum
	 * @param curCart
	 */
	public void updateByEmail(String email, String firstNm, String middleNm, String lastNm, 
			String address, String phoneNum) {
		fetcher.updateCustomerByEmail(email, firstNm, middleNm, lastNm, address, phoneNum);
	}
	
	/**
	 * Removes a customer row from the Customers table specified by customerId
	 * @param customerId String literal specifying the ID of the customer
	 */
	public void removebyId(String customerId) {
		fetcher.removeCustomerbyId(customerId);
	}
	
	/**
	 * Removes a customer row from the Customers table specified by an email
	 * @param emailString literal specifying the email of the customer
	 */
	public void removebyEmail(String email) {
		fetcher.removeCustomerbyEmail(email);
	}
	
	
	/**
	 * 
	 */
	protected void parseResults(){
		customer = null;
		try {
			while(results.next()) {
				Cart cart;
				String email = results.getString("email");
				if(email == null) email = "";
				String customerId = results.getString("customerId");
				if(customerId == null) customerId = "";
				String firstNm = results.getString("firstNm");
				if(firstNm == null) firstNm = "";
				String middleNm = results.getString("middleNm");
				if(middleNm == null) email = "";
				String lastNm = results.getString("lastNm");
				if(lastNm == null) lastNm = "";
				String address = results.getString("address");
				if(address == null) address = "";
				String phoneNm = results.getString("phoneNum");
				if(phoneNm == null) phoneNm = "";
				String cartId = results.getString("curCart");
				if(cartId != null) {
					cart = new CartHandler(fetcher.connector).getById(cartId);
					if(cart == null) cart = new Cart(cartId);
				}else {
					cart = new Cart();
				}
				customer = new Customer(email, customerId, new Name(firstNm, middleNm, lastNm), 
										new Address("address"), cart);
				
				/**Cart cart = new CartHandler(fetcher.connector).getById(results.getString("curCart"));
				if(cart == null) cart = new Cart();
				cart.setCartId(results.getString("curCart"));
				customer = new Customer(results.getString("email"), 
					results.getString("customerId"), 
					new Name(results.getString("firstNm"), 
							(results.getString("middleNm") != null) ? results.getString("middleNm") : "", 
							results.getString("lastNm")), 
					new Address((results.getString("address") != null) ? results.getString("address") : ""),
					results.getString("phoneNum"), 
					cart);
					*/
			}
		} catch(SQLException e) {
			System.err.println(this.getClass().getName() + ":" + e.getMessage());
		}
		
	}
}
