package com.controller.casts;
import application.model.Customer;

public class CustomerCast {

	private Customer customer;
		
	public Customer convert(Object obj) {
		if(obj instanceof Customer) {
			customer = (Customer)obj;
		}else {
			customer = null;
		}
		return customer;
	}
}
