package com.tags;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import application.model.Cart;


public class CartTag extends SimpleTagSupport{
	private Cart cart;
	
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	
	public void doTag() throws JspException, IOException{
		JspWriter out = getJspContext().getOut();
		if(cart.getSize() > 0) {
			out.println(result());
		}else {
			
		}
	}
	
	private String result() {
		String result = "";
		result += "<div class=\"row\">";
		result += "<div class=\"rightcolumn\">";
		result += "<div class=\"card\">";
		result += "<div id=\"w\">";
		result += "<header id=\"title\">";
		result += "<h1>Shopping Cart</h1>";
		result += "</header>";
		for(int i = 0; i < cart.getSize(); i++) {
			result += "<form name=\"removeItemForm" + i + "\" action=\"user?page=removeitem\" method=\"POST\">";
			result += "<input type=\"hidden\" name=\"itemID\" value=\""+ cart.getCartItems().get(i).getItemId()+"\">";
			result += "</form>";
		}
		result += "<div id=\"page\">";
		result += "<table id=\"cart\">";
		result += "<thead>";
		result += "<tr>";
		result += "<th class=\"first\"></th>";
		result += "<th class=\"second\">Qty</th>";
		result += "<th class=\"third\">Product</th>";
		result += "<th class=\"fourth\">Total</th>";
		result += "<th class=\"fifth\">&nbsp;</th>";
		result += "</tr>";
		result += "</thead>";
		result += "<tbody>";
		for(int i = 0; i < cart.getSize(); i++) {
			result += "<tr class=\"productItem\">";
			//picture
			result += "<td><img src=\"./productImages/"
			+ cart.getCartItems().get(i).getItemName() + ".jpg\" class=\"thumb\" " 
			+ "style=\"width: 140px\" alt=\"product\"></a></td>";
			//quantity
			result += "<td><input type=\"number\" value=\"" + cart.getCartItems().get(i).getQuantity()
					+ "\" min=\"0\" max=\"99\"class=\"qtyinput\"></td>";
			//name
			result += "<td>";
			result += cart.getCartItems().get(i).getItemName();
			result += "</td>";
			//total item price
			result += "<td>";
			result += "$" + cart.getItemTotal(cart.getCartItems().get(i));
			result += "</td>";
			//remove option
			result += "<td>";
			result += "<span class=\"remove\" onclick=\"document.removeItemForm"
					+ i + ".submit()\"><img src=\"Images/trash.png\" alt=\"X\"></span>";
			result += "</td>";
			result += "</tr>";
		}
		//tax and subtotal
		result += "<tr class=\"extracosts\">";
		result += "<td class=\"light\">Shipping:</td>";
		result += "<td colspan=\"2\">&nbsp;</td>";
		result += "<td colspan=\"2\"><span class=\"thick\">";
		result += cart.getCartTotal();
		result += "</span></td>";
		result += "</tr>";
		//checkout
		result += "<tr class=\"checkoutrow\">";
		result += "<td colspan=\"5\" class=\"checkout\"><a href=\"placeorder.jsp\">";
		result += "<button id=\"submitbtn\">Checkout</button></a>";
		result += "</td>";
		result += "</tr>";
		result += "</tbody>";
		result += "</table>";
		result += "</div>";
		result += "</div>";
		result += "</div>";
		result += "</div>";
		result += "</div>";
		return result;
	}
}
