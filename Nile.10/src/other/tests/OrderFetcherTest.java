package other.tests;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import com.controller.fetcher.OrderFetcher;

public class OrderFetcherTest {
	
	public static void printResults(ResultSet results) {
		try {
			while(results.next()) {
				System.out.print(results.getString("orderId") + ":");
				System.out.print(results.getString("cartId") + ":");
				System.out.print(results.getDate("orderDt") + ":");
				System.out.print(results.getDate("shipDt") + ":");
				System.out.print(results.getString("trackNm") + ":");
				System.out.println();
			}
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		OrderFetcher fetcher = new OrderFetcher();
		Calendar calender = Calendar.getInstance();
		calender.set(2017, 02, 05);
		Date date = new Date(calender.getTimeInMillis());
		System.out.println("**FETCH ORDER ID nio001***");
		printResults(fetcher.fetchOrderById("nio001"));
		System.out.println("***ADD ORDER cartId = TEST, orderDt = 2018-02-05**");
		fetcher.addOrder("TEST", date);
		System.out.println("***PRINT ALL ORDERS**");
		printResults(fetcher.fetchAll());
		System.out.println("***FETCH ORDER BY CARTID TEST***");
		printResults(fetcher.fetchOrderByCart("TEST"));
		System.out.println("***UPDATE ORDER BY CARTID TEST to CURRENT DAY SHIP***");
		fetcher.updateOrderByCart("TEST", "123ABC", new Date(Calendar.getInstance().getTimeInMillis()), 
				new Date(Calendar.getInstance().getTimeInMillis()), "1231312ABASD");
		System.out.println("***FETCH ORDER BY CARTID TEST***");
		printResults(fetcher.fetchOrderByCart("TEST"));
		System.out.println("***REMOVE ORDER BY CARTID TEST***");
		fetcher.removeOrderByCart("TEST");
		System.out.println("***PRINT ALL ORDERS**");
		printResults(fetcher.fetchAll());
	}

}
