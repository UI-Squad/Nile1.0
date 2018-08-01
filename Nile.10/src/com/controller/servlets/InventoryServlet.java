package com.controller.servlets;
/**
 * Serves inventory data to inventory jsp.
 * @author Shane Bogard
 */

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.controller.fetcher.Connector;
import com.controller.handler.InventoryHandler;

import application.model.Item;

@WebServlet("/inventory")
public class InventoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String LOWTOHIGH = "1";
	private static final String HIGHTOLOW = "2";
       
    public InventoryServlet() {
        super();

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response){  
    	doProcess(request, response);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response){ 
    	doProcess(request, response);
    }
    
    public void doProcess(HttpServletRequest request, HttpServletResponse response) {
    	Connector connector = new Connector();
    	ArrayList<Item> inventory = new ArrayList<Item>(); //arrayList for JSP page
    	try {
    		if(request.getParameter("dept") == null){ //inventory page
    			request.setAttribute("dept", "Inventory");
            	String sort = ((String)request.getParameter("sort") != null) 
            			? (String)request.getParameter("sort") :
            				""; //sorting value
            	String search = (String)request.getParameter("value");
            	//sorting and searching
            	if(sort.equals(LOWTOHIGH)) {
            		if(search != null) {
            			inventory = new InventoryHandler(connector).searchAndSort(search, "price", false);
            		}else{
            			inventory = new InventoryHandler(connector).getAllSortBy("price", false);
            		}
            	}else if(sort.equals(HIGHTOLOW)) {
            		if(search != null) {
            			inventory = new InventoryHandler(connector).searchAndSort(search, "price", true);
            		}else {
            			inventory = new InventoryHandler(connector).getAllSortBy("price", true);
            		}		
            	}else {
            		if(search != null) {
            			inventory = new InventoryHandler(connector).search(search);
            		}else {
            			inventory = new InventoryHandler(connector).getAll();
            		}	
            	}
    		}else { //categories page
    			String dept = (String)request.getParameter("dept");
    			request.setAttribute("dept", request.getParameter("dept"));
    			inventory = new InventoryHandler(connector).getByDept(dept.toLowerCase());
    		}
        	request.setAttribute("inventory", inventory);
        	request.getRequestDispatcher("inventory.jsp").forward(request, response);
    	}catch(Exception e) {
    		System.err.println(this.getClass().getName() + ":" + e.getMessage());
    	}finally {
    		connector.closeConnection();
    	}
    }
   
}


