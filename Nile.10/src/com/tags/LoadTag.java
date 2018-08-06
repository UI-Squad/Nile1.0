package com.tags;
/**
 * Custom tag used for loading servlet objects into the jsp page
 * @author Shane Bogard
 */

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import com.controller.casts.ItemListCast;
import application.model.Item;

public class LoadTag extends SimpleTagSupport{
	private Boolean main;
	ArrayList<Item> items;
	
	public void setMain(String main) {
		this.main = Boolean.valueOf(main);
		if(this.main == null) this.main = false;
		System.out.println("MAINPAGE:" + main.toString());
	}
		
	public void doTag() throws JspException, IOException{
		String dept = (String)getJspContext().getAttribute("dept", PageContext.REQUEST_SCOPE);
		if(dept == null) dept = "Inventory";
		String sortOne = "inventory?sort=1";
		String sortTwo = "inventory?sort=2";
		String signOn = "Sign In";
		String signLink = "login.jsp";
		String search = (String)getJspContext().getAttribute("search", PageContext.REQUEST_SCOPE);
		if(search == null) search = "Search";
		String invLink = (dept.equals("Inventory")) ? 
				"<a href=\"inventory\" class=\"active\">Inventory</a>" :
				"<a href=\"inventory\">Inventory</a>";
		String searchLink = (String)getJspContext().getAttribute("searchLink", PageContext.REQUEST_SCOPE);
		if(searchLink == null) searchLink = "inventory";
		Boolean logged = (Boolean)getJspContext().getAttribute("logged", PageContext.SESSION_SCOPE);
		if(logged == null) logged = false;
		String sortLow = (String)getJspContext().getAttribute("sortLow", PageContext.REQUEST_SCOPE);
		if(sortLow == null) sortLow = "Price: Low to High";
		String sortHigh = (String)getJspContext().getAttribute("sortHigh", PageContext.REQUEST_SCOPE);
		if(sortHigh == null) sortHigh = "Price: High to Low";
		String searchText = (String)getJspContext().getAttribute("searchText", PageContext.REQUEST_SCOPE);
		System.out.println(search);
		System.out.println(searchLink);
		
		
		if(main.booleanValue() == false) {
			if(!dept.equalsIgnoreCase("Inventory")){
				sortOne = "inventory?sort=1&dept=" + dept; 
				sortTwo = "inventory?sort=2&dept=" + dept; 
			}
			items = new ItemListCast().convertList(getJspContext().getAttribute("inventory", PageContext.REQUEST_SCOPE)); 
			if(items == null) items = new ArrayList<Item>();
			getJspContext().setAttribute("items", items, PageContext.REQUEST_SCOPE);
			if(logged != null && logged == true){ 
				signOn = "Sign Out ";
				signLink = "user?page=logout";
			}
			}else {
			if(logged != null && logged == true){ 
				signOn = "Sign Out";
				signLink = "user?page=logout";
			}
		}
		
		try {
			System.out.println("LOADING OBJECTS...");
			//request scope
			getJspContext().setAttribute("sortLow", sortLow, PageContext.REQUEST_SCOPE);
			getJspContext().setAttribute("sortHigh", sortHigh, PageContext.REQUEST_SCOPE);
			getJspContext().setAttribute("searchLink", PageContext.REQUEST_SCOPE);
			getJspContext().setAttribute("search", search, PageContext.REQUEST_SCOPE);
			getJspContext().setAttribute("searchText", searchText, PageContext.REQUEST_SCOPE);
			getJspContext().setAttribute("dept", dept, PageContext.REQUEST_SCOPE);
			getJspContext().setAttribute("invLink", invLink, PageContext.REQUEST_SCOPE);
			getJspContext().setAttribute("sortOne", sortOne, PageContext.REQUEST_SCOPE);
			getJspContext().setAttribute("sortTwo", sortTwo, PageContext.REQUEST_SCOPE);
			//session scope
			getJspContext().setAttribute("logged", logged, PageContext.SESSION_SCOPE);
			getJspContext().setAttribute("signOn", signOn, PageContext.SESSION_SCOPE);
			getJspContext().setAttribute("signLink", signLink, PageContext.SESSION_SCOPE);
		} catch(Exception e) {
			System.err.println(this.getClass().getName() + ":" + e.getMessage());
		}
	}
}
