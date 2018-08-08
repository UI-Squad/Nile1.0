package com.tags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import application.model.Order;

public class OrderTag extends SimpleTagSupport{
	private Order order;
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public void doTag() throws JspException, IOException{
		JspWriter out = getJspContext().getOut();
		if(order.getCart().getSize() > 0) {
			out.println(result());
		}else {
			
		}
	}
	
	public String result() {
		String result = "";
		result += "<div class=\"row\">";
		result += "<div class=\"rightcolumn\">";
		result += "<div class=\"card\">";
		result += "<h1>Thank you for your order!</h1>";
		result += "<h2>Order Confirmation Number:" + order.getOrderId() + "</h2>";
		result += "<br>";
		for(int i = 0; i < order.getCart().getSize(); i++) {
			result += "<h3>" + order.getCart().getCartItems().get(i).getItemName() + "</h3>";
			result += "<h3>Quantity: " + order.getCart().getCartItems().get(i).getQuantity() + "&nbsp;&nbsp;&nbsp;&nbsp;";
			result += "Price: $" + order.getCart().getCartItems().get(i).getPrice() + "</h3>";
			result += "<hr>";
		}
		result += "<h3> Total Price: " + order.getCart().getCartTotal() + "</h3>";
		result += "</div>";
		result += "</div>";
		result += "</div>";
		return result;
	}

}
