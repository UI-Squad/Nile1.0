package com.controller.casts;
import application.model.Cart;

public class CartCast {
	private Cart cart;
	
	public Cart convert(Object obj) {
		if(obj instanceof Cart) {
			cart = (Cart)obj;
		}else {
			cart = null;
		}
		return cart;
	}
}
