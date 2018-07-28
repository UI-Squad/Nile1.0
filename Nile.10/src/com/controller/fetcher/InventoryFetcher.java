package com.controller.fetcher;
/**
 * Performs SQL queries and updates on the Inventory table in the database.
 * @author Shane Bogard
 * @author Manuel Ben Bravo
 */

import java.sql.ResultSet;
import com.controller.sorting.Columns;

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
	 * 
	 * @param col1
	 * @param desc1
	 * @param col2
	 * @param desc2
	 * @return
	 */
	public ResultSet fetchAllSortedBy(Columns col1, boolean desc1,
										Columns col2, boolean desc2) {
		clearMaps();
		sql = "Select i.* FROM Inventory i ORDER BY " + col1.toString()
				+ ((desc1) ? " DESC" : " ASC") + ", " 
				+ col2.toString() + ((desc2) ? " DESC" : " ASC");
		return fetchData();
	}
	
	/**
	 * Returns all rows from the Inventory table ordered by specified Column
	 * @param column 
	 * @param descending
	 * @return ResultSet results of a SQL query
	 */
	public ResultSet fetchAllSortedBy(Columns column, boolean descending) {
		clearMaps();
		sql = "Select i.* FROM Inventory i ORDER BY " + column.toString()
				+ ((descending) ? " DESC" : " ASC");
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
	 * Changes a specified item's inStock field in the Inventory table. 
	 * @param itemId String literal specifying the itemId
	 * @param quantity Integer value specifying the new quantity in stock
	 */
	public void updateItem(String itemId, String name, String description, 
						String dept, double price, int inStock) {
		clearMaps();
		sql = "UPDATE Inventory i SET i.name = ?, i.description = ?, i.dept = ?, "
				+ "i.price = ?, i.inStock = ? WHERE i.itemId = ?";
		stringMap.put(1, name);
		stringMap.put(2, description);
		stringMap.put(3, dept);
		doubleMap.put(4, price);
		intMap.put(5, inStock);
		stringMap.put(6, itemId);
		updateData();
	}
	
	/**
	 * Changes the stock quantity of this item in the Inventory table.
	 * @param itemId String literal specifying the item ID
	 * @param inStock String literal specifying
	 */
	public void updateItemStock(String itemId, int inStock) {
		clearMaps();
		sql = "UPDATE Inventory i SET i.inStock = ? WHERE i.itemID = ?";
		intMap.put(1, inStock);
		stringMap.put(2, itemId);
		updateData();
	}
	
	/**
	 * Adds stock to the specified item in the Inventory table.
	 * @param itemId String literal specifying the item ID
	 * @param addAmt Integer value specifying the amount to add to the item's stock
	 */
	public void addItemStock(String itemId, int addAmt) {
		clearMaps();
		sql = "UPDATE Inventory i SET i.instock = i.instock + ? WHERE i.itemId = ?";
		intMap.put(1, addAmt);
		stringMap.put(2, itemId);
		updateData();
	}
	
	/**
	 * Subtracts stock from the specified item in the Inventory table.
	 * @param itemId String literal specifying the item ID
	 * @param subAmt Integer value specifying the amount to subtract from the item's stock
	 */
	public void subtractItemStock(String itemId, int subAmt) {
		clearMaps();
		sql = "UPDATE Inventory i SET i.instock = i.instock - ? WHERE i.itemId = ?";
		intMap.put(1, subAmt);
		stringMap.put(2, itemId);
		updateData();
	}
	
	public ResultSet search(String search) {
		clearMaps();
		sql = "SELECT i.* from Inventory i WHERE i.name LIKE ? ? ? OR i.description LIKE ? ? ? "
				+ "OR i.dept LIKE ? ? ?";
		stringMap.put(1, "%");
		stringMap.put(2, search);
		stringMap.put(3, "%");
		stringMap.put(4, "%");
		stringMap.put(5, search);
		stringMap.put(6, "%");
		stringMap.put(7, "%");
		stringMap.put(8, search);
		stringMap.put(9, "%");
		return fetchData();
	}
	
	
	/**
	 * Removes a row from the Inventory table specified by itemId
	 * @param itemId String literal specifying the Id of the item to remove
	 */
	public void removeItemById(String itemId) {
		clearMaps();
		sql = "DELETE from Inventory WHERE itemId = ?";
		stringMap.put(1, itemId);
		updateData();
	}
	
}
