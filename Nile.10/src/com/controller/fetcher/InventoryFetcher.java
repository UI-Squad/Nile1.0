package com.controller.fetcher;
/**
 * Performs SQL queries and updates on the Inventory table in the database.
 * @author Shane Bogard
 */

import java.sql.ResultSet;

public class InventoryFetcher extends DataFetcher {

	/**
	 * Constructs a new InventoryFetcher object with an existing connector to the database.
	 * @param connector Connector object specifying an already existing connector to the database
	 */
	public InventoryFetcher(Connector connector) {
		super(connector);
	}
	
	/**
	 * Constructs a new InventoryFetcher object that creates a new connector to the database.
	 */
	public InventoryFetcher() {
		super();
	}
	
	/**
	 * Returns all rows from the Inventory table in the database.
	 * @return ResultSet results of a SQL query
	 */
	public ResultSet fetchAll() {
		clearMaps();
		sql = "SELECT i.* FROM Inventory i";
		return fetchData();
	}
	
	/**
	 * Returns all rows from the Inventory table matching the specified department
	 * @param dept String literal specifying the department
	 * @return ResultSet results of a SQL query
	 */
	public ResultSet fetchByDept(String dept) {
		clearMaps();
		sql = "SELECT i.* FROM Inventory i WHERE i.dept = ?";
		stringMap.put(1, dept);
		return fetchData();
	}
	
	/**
	 * Returns a row from the Inventory table matching the specified item ID.
	 * @param itemId String literal specifying the item Id
	 * @return ResultSet results of a SQL query
	 */
	public ResultSet fetchById(String itemId){
		clearMaps();
		sql = "SELECT i.* FROM Inventory i WHERE i.itemId = ?";
		stringMap.put(1, itemId);
		return fetchData();
	}
	
	/**
	 * Returns a row from the Inventory table matching the specified item ID.
	 * @param itemId String literal specifying the item Id
	 * @return ResultSet results of a SQL query
	 */
	public ResultSet fetchByName(String name){
		clearMaps();
		sql = "SELECT i.* FROM Inventory i WHERE i.name = ?";
		stringMap.put(1, name);
		return fetchData();
	}
	
	/**
	 * Adds a new row to the Inventory table in the SQL Database.
	 * @param itemId String literal specifying the item ID
	 * @param name String literal specifying the name of the item
	 * @param description String literal describing the item
	 * @param dept String literal specifying the department of the item
	 * @param price Double value specifying the price of the item
	 * @param inStock Integer value specifying the quantity of the item
	 */
	public void addItem(String itemId, String name, String description,
						String dept, double price, int inStock) {
		clearMaps();
		sql = "INSERT into `Inventory` values(?, ?, ?, ?, ?, ?)";
		stringMap.put(1, itemId);
		stringMap.put(2, name);
		stringMap.put(3, description);
		stringMap.put(4, dept);
		doubleMap.put(5, price);
		intMap.put(6, inStock);
		updateData();
	}
	
	/**
	 * Removes a row from the Inventory table specified by itemId
	 * @param itemId String literal specifying the Id of the item to remove
	 */
	public void removeItem(String itemId) {
		clearMaps();
		sql = "DELETE from Inventory WHERE itemId = ?";
		stringMap.put(1, itemId);
		updateData();
	}
	
}
