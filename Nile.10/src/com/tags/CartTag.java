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
		int spaces = 45;
		String spacing = spacing(spaces);
		
		for (int i = 0; i < cart.getSize(); i++) {
			out.println("<form name=\"removeItemForm"+i+"\" action=\"user?page=removeitem\" method=\"POST\">");
			out.println("<input type=\"hidden\" name=\"itemID\" value=\""+ cart.getCartItems().get(i).getItemId()+"\">");
			out.println("</form><br>");
		}
		out.println("<div id=\"page\">");
		out.println("<thead>");
		out.println("<tr>");
		out.println("<th class=\"first\"></th>");
		out.println("<th class=\"second\">" + spacing + "Qty" + spacing + "</th>");
		out.println("<th class=\"third\">Product" + spacing + "</th>");
		out.println("<th class=\"fourth\">Total</th>");
		out.println("<th class=\"fifth\">&nbsp;</th>");
		out.println("</tr>");
		out.println("</thead>");
		out.println("<tbody>");
		for (int i = 0; i < cart.getSize(); i++) {
			String itemName = cart.getCartItems().get(i).getItemName();
			int itemQty = cart.getCartItems().get(i).getQuantity();
			double itemTotal = cart.getCartItems().get(i).getPrice() * cart.getCartItems().get(i).getQuantity();
			out.println("<tr class=\"productItem\"><br>");

			//Picture
			out.println("<td><img src=\"./productImages/"
			+ itemName + ".jpg\" class=\"thumb\" " 
			+ "style=\"width: 140px\" alt=\"product\"></a></td>");
			
			// Quantity
			out.println("<td><input type=\"number\" value=\"" + spacing + itemQty
					+ "\" min=\"0\" max=\"99\"class=\"qtyinput\"></td>");
			//Name
			out.println("<td>");
			out.println(spacing(29) + itemName);
			out.println("</td>");
			//Total Item Price
			out.println("<td>");
			out.println(spacing((spaces - 5) - itemName.length()) + "$" + itemTotal);
			out.println("</td>");
			// Remove option 
			out.println("<td>");
			out.println("<span class=\"remove\" onclick=\"document.removeItemForm"+i+".submit()\"><img src=\"Images/trash.png\" alt=\"X\"></span>");
			out.println("</td>");
			out.println("</tr><br>");
		}
		out.println("<tr class=\"extracosts\">");
		out.println("<tr class=\"extracosts\">");
		out.println("<td class=\"light\">Shipping:</td>");
		out.println("<td colspan=\"2\" class=\"light\"></td>");
		out.println("<td>Free</td>");
		out.println("<td>&nbsp;</td>");
		out.println("</tr>");
		out.println("<tr class=\"totalprice\">");
		out.println("<td class=\"light\">Total:</td>");
		out.println("<td colspan=\"2\">&nbsp;</td>");
		out.println("<td colspan=\"2\"><span class=\"thick\"> ");
		out.println(cart.getCartTotal());
		out.println("</span></td>");
		out.println("</tr>");
		out.println("<tr class=\"checkoutrow\">");
		out.println("<td colspan=\"5\" class=\"checkout\"><a href=\"placeorder.jsp\">");
		out.println("<button id=\"submitbtn\">Checkout</button></a>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</tbody>");
		out.println("</table>");
		out.println("</div>");
	}

	private String spacing(int num) {
		String result = "";
		for(int i = 0; i < num; i++) {
			result += "&nbsp;";
		}
		return result;
	}
}
