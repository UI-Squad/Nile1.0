package com.controller.handler;
/**
 * @author Shane Bogard
 */

import java.sql.SQLException;
import java.util.ArrayList;
import com.controller.fetcher.Connector;
import com.controller.fetcher.InventoryFetcher;
import application.model.Item;

public class InventoryHandler extends DataHandler<InventoryFetcher>{
	
	private ArrayList<Item> items;
			
	public InventoryHandler(InventoryFetcher fetcher) {
		super(fetcher);
		this.items = new ArrayList<Item>();
	}
	
	public InventoryHandler(Connector connector) {
		this(new InventoryFetcher(connector));
	}
	
	public InventoryHandler() {
		this(new InventoryFetcher());
	}
	
	/**
	 * Returns an item from the Database specified by the itemId
	 * @param itemId String literal specifying the itemId
	 * @return Item class object
	 */
	public Item getItemById(String itemId) {
		results = fetcher.fetchById(itemId);
		parseResults();
		return items.get(0);
	}
	
	/**
	 * Returns an item from the Database specified by the item name.
	 * @param nameString literal specifying the name of the item
	 * @return class object
	 */
	public Item getItemByName(String name) {
		results = fetcher.fetchByName(name);
		parseResults();
		return items.get(0);
	}
	
	/**
	 * Returns an item from the Database specified by the department of the item.
	 * @param dept literal specifying the department the item belongs to
	 * @return class object
	 */
	public Item getItemByDept(String dept) {
		results = fetcher.fetchByDept(dept);
		parseResults();
		return items.get(0);
	}
	
	/**
	 * 
	 * @param itemId
	 * @param name
	 * @param description
	 * @param dept
	 * @param price
	 * @param inStock
	 */
	public void addItem(String itemId, String name, String description,
			String dept, double price, int inStock) {
		fetcher.addItem(itemId, name, description, dept, price, inStock);
	}
	
	/**
	 * 
	 * @param item
	 */
	public void addItem(Item item) {
		addItem(item.getItemId(), item.getItemName(), item.getDescription(),
				item.getDept(), item.getPrice(), item.getQuantity());
	}
	
	/**
	 * Changes the attributes of an Item.
	 * @param itemId
	 * @param name
	 * @param description
	 * @param dept
	 * @param price
	 * @param inStock
	 */
	public void updateItem(String itemId, String name, String description, 
			String dept, double price, int inStock) {
		fetcher.updateItem(itemId, name, description, dept, price, inStock);
	}
	
	/**
	 * Changes the attributes of an item specified by an Item Object
	 * @param item
	 */
	public void updateItem(Item item) {
		updateItem(item.getItemId(), item.getItemName(), item.getDescription(),
					item.getDept(), item.getPrice(), item.getQuantity());
	}
	
	/**
	 * 
	 * @param itemId
	 * @param qty
	 */
	public void updateStock(String itemId, int qty) {
		fetcher.updateItemStock(itemId, qty);
	}
	
	/**
	 * 
	 * @param item
	 */
	public void updateStock(Item item) {
		updateStock(item.getItemId(), item.getQuantity());
	}
	
	/**
	 * 
	 * @param itemId
	 * @param addAmt
	 */
	public void addQuantity(String itemId, int addAmt) {
		fetcher.addItemStock(itemId, addAmt);
	}
	
	/**
	 * 
	 * @param itemId
	 * @param subAmt
	 */
	public void subtractQuantity(String itemId, int subAmt) {
		fetcher.subtractItemStock(itemId, subAmt);
	}
	
	/**
	 * Returns all items in Inventory.
	 * @return ArrayList of item objects
	 */
	public ArrayList<Item> getAll(){
		results = fetcher.fetchAll();
		parseResults();
		return items;
	}
	
	/**
	 * Returns all items in Inventory sorted by specified column
	 * @param column specifies the column to sort by
	 * @param descending true - sort in descending order
	 * 					 false - sort in ascending order
	 * @return ArrayList of item objects
	 */
	public ArrayList<Item> getAllSortBy(String col1, boolean desc1, 
											String col2, boolean desc2){
		results = fetcher.fetchAllSortedBy(col1.toString(), desc1, col2.toString(), desc2);
		parseResults();
		return items;
	}
	
	/**
	 * Returns all items in Inventory sorted by specified column
	 * @param column specifies the column to sort by
	 * @param descending true - sort in descending order
	 * 					 false - sort in ascending order
	 * @return ArrayList of item objects
	 */
	public ArrayList<Item> getAllSortBy(String column, boolean descending){
		results = fetcher.fetchAllSortedBy(column.toString(), descending);
		parseResults();
		return items;
	}
	
	/**
	 * 
	 * @param dept
	 * @return
	 */
	public ArrayList<Item> getByDept(String dept){
		results = fetcher.fetchByDept(dept);
		parseResults();
		return items;
	}
	
	public ArrayList<Item> getByDeptSortBy(String dept, String column, boolean descending){
		results = fetcher.fetchByDeptSortedBy(dept, column, descending);
		parseResults();
		return items;
	}
	
	/**
	 * Performs a search for items in the Inventory database.
	 * @param search String literal specifying the search parameters
	 * @return ArrayList of item objects
	 */
	public ArrayList<Item> search(String search){
		results = fetcher.search(search);
		parseResults();
		return items;
	}
	
	/**
	 * 
	 * @param search
	 * @param column
	 * @param descending
	 * @return
	 */
	public ArrayList<Item> searchAndSort(String search, String column, boolean descending){
		results = fetcher.searchAndSort(search, column, descending);
		parseResults();
		return items;
	}
	
	/**
	 * 
	 * @param itemId
	 */
	public void removeItemById(String itemId) {
		fetcher.removeItemById(itemId);
	}
	
	/**
	 * 
	 * @param item
	 */
	public void removeItem(Item item) {
		removeItemById(item.getItemId());
	}
		
	/**
	 * Parses the current resultSet into an ArrayList of item objects
	 */
	protected void parseResults(){
		items.clear();
		try {
			while(results.next()) {
				items.add(new Item(results.getString("itemId"), 
							results.getString("name"), 
							results.getString("description"),
							results.getString("dept"), 
							results.getDouble("price"), 
							results.getInt("inStock")));
			}
		} catch(SQLException e) {
			System.err.println(this.getClass().getName() + ":" + e.getMessage());
		}
	}
}
