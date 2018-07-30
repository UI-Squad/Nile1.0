package other.tests;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.controller.fetcher.CustomerFetcher;

public class CustomerFetcherTest {

	public static void printResults(ResultSet results) {
		try {
			while(results.next()) {
				System.out.print(results.getString("email") + ":");
				System.out.print(results.getString("customerId") + ":");
				System.out.print(results.getString("firstNm") + ":");
				System.out.print(results.getString("middleNm") + ":");
				System.out.print(results.getString("lastNm") + ":");
				System.out.print(results.getString("address") + ":");
				System.out.print(results.getString("phoneNum") + ":");
				System.out.print(results.getString("curCart") + ":");
				System.out.println();
			}
		} catch(SQLException e){
			System.err.println("\n" + e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		CustomerFetcher fetcher = new CustomerFetcher();
		System.out.println("***GET CUSTOMERS BY ID cus001");
		printResults(fetcher.fetchCustomerById("cus001", "password"));
		System.out.println("***GET CUSTOMERS BY EMAIL jjones@email.com");
		printResults(fetcher.fetchCustomerByEmail("jjones@email.com", "password"));
		System.out.println("***ADD CUSTOMER Person@email.com, password, Person, Who");
		fetcher.addCustomer("Person@email.com", "password", "Person", "Who");
		System.out.println("***GET CUSTOMERS BY EMAIL Person@email.com");
		printResults(fetcher.fetchCustomerByEmail("Person@email.com", "password"));
		System.out.println("***REMOVE CUSTOMERS BY EMAIL Person@email.com");
		fetcher.removeCustomerbyEmail("Person@email.com");
		System.out.println("***UPDATE CUSTOMERS BY ID cus001");
		fetcher.updateCustomerById("cus001", "bobsmith@gmail.com", 
				"Bob", "Jones", "Smith", "test address" , "5551112222");
		System.out.println("***GET CUSTOMERS BY ID cus001");
		printResults(fetcher.fetchCustomerById("cus001", "password"));
		//close connection
		fetcher.close();
		System.out.println(fetcher.connector.toString());
	}

}
