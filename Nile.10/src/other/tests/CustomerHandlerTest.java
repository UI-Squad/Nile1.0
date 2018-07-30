package other.tests;

import com.controller.handler.CustomerHandler;
import application.model.Customer;

public class CustomerHandlerTest {

	public static void main(String[] args) {
		CustomerHandler handler = new CustomerHandler();
		System.out.println("GET CUSTOMER CUS001");
		Customer customer = handler.getById("cus001", "password");
		System.out.println(customer.toString());
		System.out.println("GET CUSTOMER jjones@email.com ");
		customer = handler.getByEmail("jjones@email.com", "password");
		System.out.println(customer.toString());
	}

}
