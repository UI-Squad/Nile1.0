package com.controller.fetcher;
/**
 * @author Shane Bogard
 * Performs a wide array of operations on the database
 * including selections, insertions, updates and deletions.
 */

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public abstract class DataFetcher {
	/** Establishes connection to database. Allows for
	 * opening, closing and modifying of the connection
	 * to the database */
	public Connector connector;
	/** SQL PreparedStatement **/
	protected PreparedStatement preparedStatement;
	/** SQL ResultSet**/
	protected ResultSet resultSet;
	/** For mapping SQL prepared statements to Strings */
	protected HashMap<Integer, String> stringMap;
	/** For mapping SQL prepared statements to Integers */
	protected HashMap<Integer, Integer> intMap;
	/** For mapping SQL prepared statements to Doubles */
	protected HashMap<Integer, Double> doubleMap;
	/** For mapping SQL prepared statements to date object */
	protected HashMap<Integer, Date> dateMap;
	/** SQL statement used for queries and updates */
	protected String sql;
	
	/**
	 * Constructs a new DataFetcher consisting of a specified 
	 * Connector object.
	 * @param connector
	 */
	public DataFetcher(Connector connector) {
		this.connector = connector;
		preparedStatement = null;
		resultSet = null;
		stringMap = new HashMap<Integer, String>();
		intMap = new HashMap<Integer, Integer>();
		doubleMap = new HashMap<Integer, Double>();
		dateMap = new HashMap<Integer, Date>();
		if(connector.isClosed()) connector.openConnection();
	}
	
	/**
	 * Constructs a new default DataFetcher object that is not connected. 
	 */
	public DataFetcher() {
		this(new Connector());
	}
	
	/**
	 * Performs a SQL query on the database that returns a result. 
	 * @return ResultSet results of a SQL query on the database
	 */
	protected ResultSet fetchData(){
		try {
			//prepare query
			prepareStatement();
			//execute query
			resultSet = preparedStatement.executeQuery();
		} catch(SQLException e) {
			System.err.println(this.getClass().getName() + ":" + e.getMessage());
		}
		sql = null;
		return resultSet;
	}
	
	/**
	 * Performs a SQL query that makes an update to the database
	 */
	protected void updateData() {
		try {
			//prepare query
			prepareStatement();
			//execute query
			preparedStatement.executeUpdate();
		} catch(SQLException e) {
			System.err.println(this.getClass().getName() + ":" + e.getMessage());
		}
		sql = null;
	}
	
	/**
	 * Prepares the SQL statement for execution.
	 * @throws SQLException
	 */
	private void prepareStatement(){
		int values = stringMap.size();
		int index = 1;
		if(connector.isClosed()) {
			System.out.println("RE-ESTABLISHING CONNECTION...");
			connector.openConnection();
		}
		//SQL statement
		try {
			preparedStatement = connector.connect.prepareStatement(sql);
			//string values;
			while(values > 0) {
				if(stringMap.get(index) != null) {
					preparedStatement.setString(index, stringMap.get(index));
					values--;
				}
				index++;
			}
			//integer values
			index = 1;
			values = intMap.size();
			while(values > 0) {
				if(intMap.get(index) != null) {
					preparedStatement.setInt(index, intMap.get(index));
					values--;
				}
				index++;
			}
			//double values
			index = 1;
			values = doubleMap.size();
			while(values > 0) {
				if(doubleMap.get(index) != null) {
					preparedStatement.setDouble(index, doubleMap.get(index));
					values--;
				}
				index++;
			}
			//date values
			index = 1;
			values = dateMap.size();
			while(values > 0) {
				if(dateMap.get(index) != null) {
					preparedStatement.setDate(index, dateMap.get(index));
					values--;
				}
				index++;
			}
		}catch (SQLException e) {
			System.err.println(this.getClass().getName() + ":" + e.getMessage());
		}
	}
	
	/**
	 * Clears all mapping out of the HashMap objects
	 */
	public void clearMaps() {
		stringMap.clear();
		intMap.clear();
		doubleMap.clear();
		dateMap.clear();
	}
		
	/**
	 * Closes the preparedStatment, resultSet and connector.
	 */
	public void close() {
		try {
			if(preparedStatement != null)
				preparedStatement.close();
			if(resultSet != null)
				resultSet.close();
			if(!connector.isClosed())
				connector.closeConnection();;
		}
		catch (Exception e) {
			System.err.println(this.getClass().getName() + ":" + e.getMessage());
		}
	}
}
