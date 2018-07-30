package com.controller.handler;
/**
 * @author Shane Bogard
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import com.controller.fetcher.Connector;
import com.controller.fetcher.DataFetcher;

public abstract class DataHandler <T extends DataFetcher> {

	/** Performs queries and updates on the database */
	protected T fetcher;
	
	/** Results of an SQL query */
	protected ResultSet results;
	
	public DataHandler(T fetcher) {
		this.fetcher = fetcher;
		results = null;
	}
	
	/**
	 * 
	 * @return
	 */
	public T getFetcher() {
		return fetcher;
	}
		
	/**
	 * Opens a connection to the database if one does not already exists
	 */
	public void connect() {
		if(fetcher.connector.isClosed()) fetcher.connector = new Connector();
	}
	
	/**
	 * Closes all connections, ResultSets and PreparedStatements.
	 */
	public void close() {
		fetcher.close();
	}
	
	/** Parses the results of the database query into model logic */
	protected abstract void parseResults() throws SQLException; 
}
