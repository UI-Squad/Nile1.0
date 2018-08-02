package com.tags;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import application.model.Item;

public class InventoryTag extends SimpleTagSupport{
	private String dept;
	private ArrayList<Item> inventory;
	
	public void setDept(String dept) {
		this.dept = dept;
	}

	public void setInventory(ArrayList<Item> items) {
		this.inventory = items;
	}
	
	public void doTag() throws JspException, IOException{
		JspWriter out = getJspContext().getOut();
		if(inventory == null) inventory = new ArrayList<Item>();
		out.println("<h2>" + dept + "</h2>");		
		for(int i = 0; i < inventory.size(); i++){
			String itemName = inventory.get(i).getItemName();
			String itemDescription = inventory.get(i).getDescription();
			double itemPrice = inventory.get(i).getPrice();
			String itemSummary = "";
			String itemID = inventory.get(i).getItemId();
			if(itemDescription.length() > 30){
				itemSummary = (itemDescription.substring(0, 24) + "...");
			}else{
				itemSummary=itemDescription;
			}
			out.println("<div class=\"grid-container\"><div class=\"item1\">");
			
			//ItemProductForm
			out.println("<form name=\"itemNameForm"+i+"\" action=\"productdetails\" method=\"POST\">");
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
