package other.tests;

import java.util.ArrayList;
import com.controller.handler.InventoryHandler;
import application.model.Item;

public class InventoryHandlerTest {
	
	public static void printInventory(ArrayList<Item> items) {
		for(Item item : items) {
			System.out.println(item.toString());
		}
	}

	public static void main(String[] args) {
		InventoryHandler handler = new InventoryHandler();
		System.out.println("PRINT ALL INVENTORY");
		printInventory(handler.getAll());
		System.out.println("\nPRINT ALL INVENTORY SORTED BY name ASCENDING");
		printInventory(handler.getAllSortBy("name", false));
		System.out.println("\nSEARCH FOR DUNE");
		printInventory(handler.search("Dune"));
		System.out.println("\nSEARCH FOR Table");
		printInventory(handler.search("Table"));
		handler.close();
	}

}
