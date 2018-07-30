package other.tests;
/**
 * @author Shane Bogard
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import com.controller.fetcher.InventoryFetcher;

public class InventoryFetcherTest {

	public static void printResults(ResultSet results) {
		try {
			while(results.next()) {
				System.out.print(results.getString("itemId") + ":");
				System.out.print(results.getString("name") + ":");
				System.out.print(results.getString("description") + ":");
				System.out.print(results.getString("dept") + ":");
				System.out.print(results.getDouble("price") + ":");
				System.out.print(results.getInt("inStock") + ":");
				System.out.println();
			}
		} catch(SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		InventoryFetcher fetcher = new InventoryFetcher();
		//System.out.println(fetcher.connector.toString());
		//System.out.println("***ALL INVENTORY***");
		//printResults(fetcher.fetchAll());
		//System.out.println("***BY AUTO DEPT***");
		//printResults(fetcher.fetchByDept("auto"));
		//System.out.println("***BY BOOKS DEPT***");
		//printResults(fetcher.fetchByDept("books"));
		//System.out.println("***BY ITEM by ID bk001***");
		//printResults(fetcher.fetchById("bk001"));
		//System.out.println("***BY ITEM by NAME 1984***");
		//printResults(fetcher.fetchByName("1984"));
		System.out.println("***ADD ITEM Test, Test, Test, Test, 99.99, 99***");
		fetcher.addItem("test", "test", "test", "test", 99.99, 99);
		printResults(fetcher.fetchByName("test"));
		System.out.println("***CHANGE TEST FIELDS***");
		fetcher.updateItem("test", "BLAH", "WORKED", "SOMETHING", 1.00, 100);
		printResults(fetcher.fetchByName("BLAH"));
		System.out.println("***UPDATE ITEM test STOCK 200***");
		fetcher.updateItemStock("test", 200);
		printResults(fetcher.fetchByName("BLAH"));
		System.out.println("***SUBTRACT ITEM test STOCK BY 150***");
		fetcher.subtractItemStock("test", 150);
		printResults(fetcher.fetchByName("BLAH"));
		System.out.println("***ADD ITEM test STOCK BY 25***");
		fetcher.addItemStock("test", 25);
		printResults(fetcher.fetchByName("BLAH"));
		System.out.println("***REMOVE ITEM by ID Test***");
		fetcher.removeItemById("test");
		System.out.println("***SEARCH FOR books***");
		printResults(fetcher.search("books"));
		//printResults(fetcher.fetchAll());
		//printResults(fetcher.fetchAllSortedBy(Columns.ITEMNM.toString(), false));
		//printResults(fetcher.fetchAllSortedBy(Columns.ITEMNM.toString(), true, Columns.ITEMNM, true));
		fetcher.close();
	}

}
