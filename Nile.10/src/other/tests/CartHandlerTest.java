package other.tests;

import com.controller.handler.CartHandler;
import application.model.Cart;

public class CartHandlerTest {

	public static void main(String[] args) {
		CartHandler handler = new CartHandler();
		Cart cart = handler.getById("car006");
		System.out.println("GET CART car006");
		System.out.println(cart.toString());
	}

}
