package com.controller.casts;
/**
 * Checks if an object is an ArrayList of item objects
 * and returns an ArrayList of item objects.
 * @author Shane Bogard
 */

import java.util.ArrayList;
import application.model.Item;

public class ItemListCast {

	public ArrayList<Item> convertList(Object obj){
		ArrayList<Item> items = new ArrayList<Item>();
		if(obj instanceof ArrayList<?>) {
			ArrayList<?> list = (ArrayList<?>)obj;
			for(Object o : list) {
				if(o instanceof Item) {
					items.add((Item)o);
				}
			}
		}
		return items;
	}
	
	public Item convertItem(Object obj) {
		Item item = null;
		if(obj instanceof Item) {
			item = (Item)obj;
		}
		return item;
	}
	
	
}
