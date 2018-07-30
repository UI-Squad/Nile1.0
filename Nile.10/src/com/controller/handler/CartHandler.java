package com.controller.handler;
/**
 * @author Shane Bogard
 */

import java.sql.SQLException;
import com.controller.fetcher.CartFetcher;
import com.controller.fetcher.CustomerFetcher;
import com.controller.fetcher.InventoryFetcher;
import application.model.Cart;
import application.model.Item;

public class CartHandler extends DataHandler<CartFetcher>{
	
	/** **/
	private Cart cart;
	
	/** **/
	private String cartId;

	/**
	 * 
	 */
	public CartHandler() {
		super(new CartFetcher());
		cart = new Cart();
		cartId = "";
	}
	
	/**
	 * 
	 * @param cartId
	 * @return
	 */
	public Cart getCartById(String cartId) {
		this.cartId = cartId;
		results = fetcher.fetchByCartId(cartId);
		parseResults();
		return cart;
	}
	
	/**
	 * Adds an item to the cart.
	 * @param cartId
	 * @param customerId
	 * @param itemId
	 * @param quantity
	 */
	public void addCartItem(String cartId, String customerId, String itemId, int quantity) {
		fetcher.addCartItem(cartId, customerId, itemId, quantity);
	}
	
	/**
	 * Adds an item to the cart located in the database.
	 * @param cartId
	 * @param customerId
	 * @param item
	 */
	public void addCartItem(String cartId, String customerId, Item item) {
		addCartItem(cartId, customerId, item.getItemId(), item.getQuantity());
	}
	
	/**
	 * Updates the quantity of an item contained with a cart located in the database
	 * @param cartId
	 * @param itemId
	 * @param quantity
	 */
	public void updateQuantity(String cartId, String itemId, int quantity) {
		fetcher.updateQuantity(cartId, itemId, quantity);
	}
	
	/**
	 * Removes an item from a Cart located in the database
	 * @param cartId
	 * @param itemId
	 */
	public void removeItem(String cartId, String itemId) {
		fetcher.removeCartItem(cartId, itemId);
	}
	
	/**
	 * Removes an item from a Cart located in the database
	 * @param cartId
	 * @param item
	 */
	public void removeItem(String cartId, Item item) {
		removeItem(cartId, item.getItemId());
	}
	
	/**
	 * Returns a cart item back to inventory.
	 * @param cartId
	 * @param itemId
	 */
	public void returnItem(String cartId, String itemId) {
		results = fetcher.fetchItemByCartId(cartId, itemId);
		try {
			results.next();
			int addAmt = results.getInt("quantity");
			fetcher.removeCartItem(cartId, itemId);
			new InventoryFetcher(getFetcher().connector).addItemStock(itemId, addAmt);
		}catch(SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	
	/**
	 * Returns the cart of a registered Customer
	 * @param customerId String literal specifying the Customer Id of the registered customer
	 * @return Cart object
	 */
	public Cart getRegisteredCart(String customerId) {
		//find registered customer's current cart.
		results = new CustomerFetcher(getFetcher().connector).fetchRegisteredCartId(customerId);
		try {
			results.next();
			cartId = results.getString("curCart");
			results = fetcher.fetchByCartId(cartId);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		parseResults();
		return cart;
	}
	
	/**
	 * Removes all items in a cart located in the database.
	 * @param cartId
	 */
	public void clearById(String cartId) {
		fetcher.clearCartByCartId(cartId);
	}
	
	/**
	 * Removes all items in a cart located in the database.
	 * @param customerId
	 */
	public void clearByCustomerId(String customerId) {
		fetcher.clearCartByCustomerId(customerId);
	}
		
	/**
	 * 
	 */
	protected void parseResults(){
		try {
			cart = new Cart(cartId);
			while(results.next()) {
				cart.addToCart(new Item((results.getString("itemId") != null ? results.getString("itemId") : ""), 
							(results.getString("name") != null) ? results.getString("name") : "", 
							(results.getString("description") != null) ? results.getString("description") : "", 
							(results.getString("dept") != null) ? results.getString("dept") : "",
							results.getDouble("price"),
							results.getInt("quantity")));
			}
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		
	}
}
