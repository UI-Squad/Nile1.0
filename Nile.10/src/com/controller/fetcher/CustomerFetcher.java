package com.controller.fetcher;
/**
 * @author Shane Bogard
 * @author Manuel Ben Bravo
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import javax.xml.bind.DatatypeConverter;

public class CustomerFetcher extends DataFetcher {

	
	public CustomerFetcher(Connector connector) {
		super(connector);
	}
	
	public CustomerFetcher() {
		super();
	}
	
	/**
	 * Hash Password Method 
	 * @param password
	 * @return String for Password
	 */
	private String hashPassword(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	    md.update(password.getBytes());
	    byte[] digest = md.digest();
	    String myHash = DatatypeConverter.printHexBinary(digest).toLowerCase();	
	    return myHash;
	}
	
	/**
	 * Returns a row from the Customers table specified by an email and password.
	 * @param email String literal specifying the email belonging to the customer
	 * @param password String literal specifying the password belonging to the customer
	 * @return ResultSet results of an SQL query
	 */
	public ResultSet fetchCustomerByEmail(String email, String password) {
		clearMaps();
		sql = "SELECT c.email, c.customerId, c.firstNm, c.middleNm, c.lastNm, c.address, "
				+ "c.phoneNum, c.curCart from Customers c WHERE c.email = ? AND c.password = ?";
		stringMap.put(1, email);
		stringMap.put(2, hashPassword(password));
		return fetchData();
	}
	
	/**
	 * Returns a row from the Customers table specified by an email and password.
	 * @param customerId String literal specifying the ID belonging to the customer
	 * @param password String literal specifying the password belonging to the customer
	 * @return ResultSet results of an SQL query
	 */
	public ResultSet fetchCustomerById(String customerId, String password) {
		clearMaps();
		sql = "SELECT c.email, c.customerId, c.firstNm, c.middleNm, c.lastNm, c.address, "
				+ "c.phoneNum, c.curCart from Customers c WHERE c.customerId = ? AND c.password = ?";
		stringMap.put(1, customerId);
		stringMap.put(2, hashPassword(password));
		return fetchData();
	}
	
	/**
	 * 
	 * @param customerId
	 * @return
	 */
	public ResultSet fetchRegisteredCartId(String customerId) {
		clearMaps();
		sql = "SELECT c.curCart from Customers c WHERE c.customerId = ?";
		stringMap.put(1, customerId);
		return fetchData();
	}
	
	/**
	 * Adds a new customer row to the Customers table.
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
	public void addCustomer(String email, String password, String customerId, String firstNm,
							String middleNm, String lastNm, String address, int phoneNum, String curCart) {
		clearMaps();
		sql = "INSERT INTO Customers VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		stringMap.put(1, email);
		stringMap.put(2, hashPassword(password));
		stringMap.put(3, customerId);
		stringMap.put(4, firstNm);
		stringMap.put(5, middleNm);
		stringMap.put(6, lastNm);
		stringMap.put(7, address);
		intMap.put(8, phoneNum);
		stringMap.put(9, curCart);
		updateData();
	}
	
	/**
	 * Adds a new customer row to the Customers table.
	 * @param email
	 * @param password
	 * @param firstNm
	 * @param lastNm
	 */
	public void addCustomer(String email, String password, String firstNm, String lastNm) {
		clearMaps();
		sql = "INSERT INTO Customers (email, password, firstNm, lastNm) VALUES (?, ?, ?, ?)";
		stringMap.put(1, email);
		stringMap.put(2, hashPassword(password));
		stringMap.put(3, firstNm);
		stringMap.put(4, lastNm);
		updateData();
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
	public void updateCustomerById(String customerId, String email, String firstNm, String middleNm,
					String lastNm, String address, String phoneNum, String curCart) {
		clearMaps();
		sql = "UPDATE Customers c SET c.email = ?, c.firstNm = ?, c.middleNm = ?, c.lastNm = ?, "
				+ "c.address = ?, c.phoneNum = ?, c.curCart = ? WHERE c.customerId = ?";
		stringMap.put(1, email);
		stringMap.put(2, firstNm);
		stringMap.put(3, middleNm);
		stringMap.put(4, lastNm);
		stringMap.put(5, address);
		stringMap.put(6, phoneNum);
		stringMap.put(7, curCart);
		stringMap.put(8, customerId);
		updateData();
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
	public void updateCustomerById(String customerId, String email, String firstNm, String middleNm,
			String lastNm, String address, String phoneNum) {
		clearMaps();
		sql = "UPDATE Customers c SET c.email = ?, c.firstNm = ?, c.middleNm = ?, c.lastNm = ?, "
				+ "c.address = ?, c.phoneNum = ? WHERE c.customerId = ?";
		stringMap.put(1, email);
		stringMap.put(2, firstNm);
		stringMap.put(3, middleNm);
		stringMap.put(4, lastNm);
		stringMap.put(5, address);
		stringMap.put(6, phoneNum);
		stringMap.put(7, customerId);
		updateData();
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
	public void updateCustomerByEmail(String email, String firstNm, String middleNm, String lastNm, 
			String address, String phoneNum, String curCart) {
		clearMaps();
		sql = "UPDATE Customers c SET c.firstNm = ?, c.middleNm = ?, c.lastNm = ?, "
				+ "c.address = ?, c.phoneNum = ?, c.curCart = ? WHERE c.email = ?";
		stringMap.put(1, firstNm);
		stringMap.put(2, middleNm);
		stringMap.put(3, lastNm);
		stringMap.put(4, address);
		stringMap.put(5, phoneNum);
		stringMap.put(6, curCart);
		stringMap.put(7, email);
		updateData();
		
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
	public void updateCustomerByEmail(String email, String firstNm, String middleNm, String lastNm, 
			String address, String phoneNum) {
		clearMaps();
		sql = "UPDATE Customers c SET c.firstNm = ?, c.middleNm = ?, c.lastNm = ?, "
				+ "c.address = ?, c.phoneNum = ? WHERE c.email = ?";
		stringMap.put(1, firstNm);
		stringMap.put(2, middleNm);
		stringMap.put(3, lastNm);
		stringMap.put(4, address);
		stringMap.put(5, phoneNum);
		stringMap.put(6, email);
		updateData();
	}
	
	/**
	 * Removes a customer row from the Customers table specified by customerId
	 * @param customerId String literal specifying the ID of the customer
	 */
	public void removeCustomerbyId(String customerId) {
		clearMaps();
		sql = "DELETE FROM Customers WHERE customerId = ?";
		stringMap.put(1, customerId);
		updateData();
	}
	
	/**
	 * Removes a customer row from the Customers table specified by an email
	 * @param emailString literal specifying the email of the customer
	 */
	public void removeCustomerbyEmail(String email) {
		clearMaps();
		sql = "DELETE FROM Customers WHERE email = ?";
		stringMap.put(1, email);
		updateData();
	}
	
	
}
