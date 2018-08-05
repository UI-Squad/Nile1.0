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
		String invLink = (dept.equals("Inventory")) ? 
				"<a href=\"inventory\" class=\"active\">Inventory</a>" :
				"<a href=\"inventory\">Inventory</a>";
		
					
		Boolean logged = (Boolean)getJspContext().getAttribute("logged", PageContext.SESSION_SCOPE);
		if(logged == null) logged = false;
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
			getJspContext().setAttribute("dept", dept, PageContext.REQUEST_SCOPE);
			getJspContext().setAttribute("invLink", invLink, PageContext.REQUEST_SCOPE);
			getJspContext().setAttribute("logged", logged, PageContext.SESSION_SCOPE);
			getJspContext().setAttribute("signOn", signOn, PageContext.SESSION_SCOPE);
			getJspContext().setAttribute("signLink", signLink, PageContext.SESSION_SCOPE);
			getJspContext().setAttribute("sortOne", sortOne, PageContext.REQUEST_SCOPE);
			getJspContext().setAttribute("sortTwo", sortTwo, PageContext.REQUEST_SCOPE);
			//getJspBody().invoke(null);
		} catch(Exception e) {
			System.err.println(this.getClass().getName() + ":" + e.getMessage());
		}
	}
}
