package other.tests;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.controller.fetcher.CartFetcher;
import com.controller.handler.CartHandler;

public class CartFetcherTest {

	public static void printResults(ResultSet results) {
		try {
			while(results.next()) {
				System.out.print(results.getString("cartId") + ":");
				System.out.print(results.getString("customerId") + ":");
				System.out.print(results.getString("itemId") + ":");
				System.out.print(results.getInt("quantity") + ":");
				System.out.print(results.getDouble("price") + ":");
				System.out.println();
			}
		} catch(SQLException e){
			System.err.println("\n" + e.getMessage());
		}
		
	}
	
	public static void main(String[] args){
		CartFetcher fetcher = new CartFetcher();
		System.out.println(fetcher.connector.toString());
		//fetches a cart by the cart ID
		System.out.println("**FETCH BY CART ID car001***");
		printResults(fetcher.fetchByCartId("car001"));
		//fetches a cart by the customer ID
		System.out.println("**FETCH BY CUSTOMER ID cdf002***");
		printResults(fetcher.fetchByCustomerId("cdf002"));
		//add item to cart
		System.out.println("**ADD ITEM hm009 TO CART car007***");
		fetcher.addCartItem("car007", "cus001", "hm009", 1);
		printResults(fetcher.fetchByCartId("car007"));
		System.out.println("**DELETE ITEM hm009 FROM CART car007 ***");
		fetcher.removeCartItem("car007", "hm009");
		printResults(fetcher.fetchByCartId("car007"));
		System.out.println("**CHANGE ITEM QUANTITY hm010 to 2 from car007***");
		fetcher.updateQuantity("car007", "hm010", 2);
		//fetch a item from the cart
		System.out.println("**FETCH ITEM aut001 FROM CART ID car001***");
		printResults(fetcher.fetchItemByCartId("car001", "aut001"));
		new CartHandler(fetcher).returnItem("car007", "hm002");
		System.out.println("**FETCH BY CART ID car006***");
		printResults(fetcher.fetchByCartId("car006"));
		fetcher.close();
	}

}
