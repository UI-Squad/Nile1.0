package com.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import application.model.Cart;


public class CartTag extends SimpleTagSupport{
	private Cart cart;
	
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	
	public void doTag() throws JspException, IOException{
		
	}

}
