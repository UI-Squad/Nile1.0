package com.controller.fetcher;
/**
 * @author Shane Bogard
 * Allows for opening and closing of connections to the UI database.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

	/** database user name */
	private static final String USER = "uiuser";
	/** database user password */
	private static final String PASS = "uipassword1";
	
	/**  mySQL connection object **/
	public Connection connect;
	
	/**
	 * 
	 * @param connect
	 */
	public Connector(Connection connect) {
		this.connect = connect;
	}
	
	/**
	 * 
	 */
	public Connector() {
		this(null);
	}
	
	/**
	 * Opens a connection to the UI_Database.
	 * @throws SQLException 
	 */
	public void openConnection(){
		try {
			// This will load the MySQL driver, each DBMS has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			if(connect == null) {
				System.out.println("OPENING CONNECTION...");
				connect = DriverManager.getConnection("jdbc:mysql://45.17.26.63/ui_database?serverTimezone=UTC", USER, PASS);
			}else if(connect != null && !connect.isValid(5)) {
				connect.close();
				connect = DriverManager.getConnection("jdbc:mysql://45.17.26.63/ui_database?serverTimezone=UTC", USER, PASS);
			}
			
			System.out.println("CONNECTION SUCCESSFUL...");
		} catch (Exception e) {
			System.err.println(this.getClass().getName() + ":" + e.getMessage());
		}
	}
		
	/**
	 * Closes the connection to the Database.
	 */
	public void closeConnection() {
		System.out.println("CLOSING CONNECTION...");
		try {
			if(connect != null) {
				connect.close();
				System.out.println("CONNECTION CLOSED");
			}
		}
		catch (Exception e) {
			System.err.println(this.getClass().getName() + ":" + e.getMessage());
		}
	}
	
	/**
	 * Closes the current Connection object and changes it to a new specified
	 * connection object.
	 * @param connect A Connection object
	 */
	public void setConnection(Connection connect) {
		closeConnection();
		this.connect = connect;
	}
		
	/**
	 * 
	 * @return
	 * @throws SQLException 
	 */
	public boolean isClosed(){
		if(connect == null) return true;
		try {
			return connect.isClosed();
		}
		catch (SQLException e) {
			System.err.println(this.getClass().getName() + ":" + e.getMessage());
		}
		return false;
		
	}	
	
	/**
	 * Returns a string literal specifying if this Connect
	 */
	public String toString() {
		return (!isClosed()) ? "Connected" : "Not Connected";
	}
}
