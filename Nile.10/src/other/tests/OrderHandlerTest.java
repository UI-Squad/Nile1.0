package other.tests;

import java.util.ArrayList;
import com.controller.handler.OrderHandler;
import application.model.Order;

public class OrderHandlerTest {

	public static void main(String[] args) {
		OrderHandler handler = new OrderHandler();
		ArrayList<Order> orders = new ArrayList<Order>(handler.getAll());
		System.out.println("***ALL ORDERS");
		for(Order order : orders) {
			System.out.println(order.toString());
		}
		System.out.println("***ORDER BY ID ni003");
		System.out.println(handler.getById("nio003"));

	}

}
