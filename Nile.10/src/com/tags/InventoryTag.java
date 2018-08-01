package com.tags;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import application.model.Item;

public class InventoryTag extends SimpleTagSupport{
	private ArrayList<Item> items;

	public void setInventory(ArrayList<Item> items) {
		this.items = items;
	}
	
	public void doTag() throws JspException, IOException{
		JspWriter out = getJspContext().getOut();
		if(items == null) items = new ArrayList<Item>();
		for(int i = 0; i < items.size(); i++){
			String itemName = items.get(i).getItemName();
			String itemDescription = items.get(i).getDescription();
			double itemPrice = items.get(i).getPrice();
			String itemSummary = "";
			String itemID = items.get(i).getItemId();
			if(itemDescription.length() > 30){
				itemSummary = (itemDescription.substring(0, 24) + "...");
			}else{
				itemSummary=itemDescription;
			}
			out.println("<div class=\"grid-container\"><div class=\"item1\">");
			
			//ItemProductForm
			out.println("<form name=\"itemNameForm"+i+"\" action=\"productDetailServlet\" method=\"POST\">");
			out.println("<input type=\"hidden\" name=\"itemName\" value=\""+itemName+"\">");
			out.println("<input type=\"hidden\" name=\"itemDescription\" value=\""+itemDescription+"\">");
			out.println("<input type=\"hidden\" name=\"itemPrice\" value=\""+itemPrice+"\">");
			out.println("<input type=\"hidden\" name=\"itemID\" value=\""+itemID+"\">");
			out.println("</form>");
			
			//Product Listing Information
			out.println("<a href=\"#\" onclick=\"document.itemNameForm"+i+".submit()\">");
			out.println("<img src=\"./productImages/"
			+ itemName + ".jpg\" align=\"middle\" style=\"width: 170px\" alt=\"product\"></a>");
			out.println("</div>");
			out.println("<div class=\"item2\">");
			out.println("<a href=\"#\" onclick=\"document.itemNameForm"+i+".submit()\">"+itemName+"</a>");
			out.println("</div><div class=\"item3\"></div>");
			out.println("<div class=\"item4\">");
			out.println(itemSummary);
			out.println("</div>");
			out.println("<div class=\"item5\">");
			out.println("$"+itemPrice);
			out.println("</div><div class=\"item6\"></div>");		
			out.println("<div class=\"item7\"></div></div>");	
			out.println("<hr>");
		}
	}
}
