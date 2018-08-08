package com.controller.servlets;
/**
 * Serves inventory data to inventory jsp.
 * @author Shane Bogard
 */

import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.controller.casts.ConnectorCast;
import com.controller.casts.ItemListCast;
import com.controller.fetcher.Connector;
import com.controller.handler.InventoryHandler;
import application.model.Item;

@WebServlet("/inventory")
public class InventoryServlet extends HttpServlet {
	private static final long serialVersionUID = -3845066719514538661L;
	private static final String LOWTOHIGH = "1";
	private static final String HIGHTOLOW = "2";
	private HttpSession session;
	private Connector connector = null;
	private InventoryHandler iHandle;
	private ArrayList<Item> inventory;
	private String dept;
	private String search;
	private String searchLink;
	private String sort;
	private String searchText;
       
    public InventoryServlet() {
        super();
        inventory = new ArrayList<Item>();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response){  
    	doProcess(request, response);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response){ 
    	doProcess(request, response);
    }
    
    private void doProcess(HttpServletRequest request, HttpServletResponse response) {	
    	setVariables(request, response);
    	//process request
    	processRequest();
      	//build search link
    	buildSearchLink();
    	try { //set attributes
    		if(sort.equals(LOWTOHIGH)) {
    			request.setAttribute("sortLow", "<mark>Price: Low to High</mark>");
    			request.setAttribute("sortHigh", "Price: High to Low");
    		}
    		else if(sort.equals(HIGHTOLOW)) {
    			request.setAttribute("sortLow", "Price: Low to High");
    			request.setAttribute("sortHigh", "<mark>Price: High to Low</mark>");
    		}else {
    			request.setAttribute("sortLow", "Price: Low to High");
    			request.setAttribute("sortHigh", "Price: High to Low");
    		}
    		request.setAttribute("searchText", searchText);
    		request.setAttribute("dept", dept);
        	request.setAttribute("inventory", inventory);
         	request.setAttribute("search", search);
         	request.setAttribute("searchLink", searchLink);
         	request.setAttribute("sort", sort);
         	request.getRequestDispatcher("inventory.jsp").forward(request, response);
    	}catch(Exception e) {
    		System.err.println(this.getClass().getName() + ":" + e.getMessage());
    	}finally {
    		
    	}
    }   
    
    private void setVariables(HttpServletRequest request, HttpServletResponse response) {
    	session = request.getSession(true); //get session
    	setConnector(session); //locate connection
      	iHandle = new InventoryHandler(connector); //create database handler
    	dept = (String)request.getParameter("dept"); //locate department for items lookup
    	if(dept == null) dept = "Inventory";
    	search = (String)request.getParameter("value");
    	if (search == null) search = (String)request.getAttribute("search");
    	if (search == null) {
    		searchText = "Search";
    		search = "";
    	}else {
    		searchText = search;
    	}
    	
    		
    	
    	System.out.println("SEARCHTEXT: " + searchText + " "); 
    	System.out.println("SEARCH: " + search + " "); 
    	sort = ((String)request.getParameter("sort") != null)  //determine sort parameters
    				? (String)request.getParameter("sort") : "";
    	System.out.println(sort);
    }
       
    private void setConnector(HttpSession session) {
    	if(session.isNew()) { //open new connection to database
    		if(connector != null){ //close existing connections if session is new
    			connector.closeConnection();
    		}
    		//open new connection
    		System.out.println("NEW CONNECTION");
        	connector = new Connector();
    		session.setAttribute("connector", connector);
    	}else { //use existing connection to database if session is not new
    		Connector oldConnector = new ConnectorCast().convert(session.getAttribute("connector"));
    		if(oldConnector != null) {
    			if(oldConnector.isClosed()) { //check if existing connection was closed
    				oldConnector.openConnection(); //reopen
    			}
    			System.out.println("CONNECTION CONFIRMED");
    			connector = oldConnector;
    		}else { //existing connection not found, reopen connection
        		if(connector != null){ //close existing connections if session is new
        			connector.closeConnection();
        		}
        		System.out.println("NEW CONNECTION");
            	connector = new Connector();
            	session.setAttribute("connector", connector);
    		}
    	}
    }
    
    private void buildSearchLink() {
    	if(dept != null && !sort.equals("")) { //search by dept and sort
    		searchLink = "inventory?dept=" + dept +"?sort=" + sort;
    	}else if(dept == null && !sort.equals("")){ //search and sort
    		searchLink = "inventory?sort=" + sort;
    	}else if(dept != null && sort.equals("")){ //search by dept
    		searchLink = "inventory?dept=" + dept;
    	}else { //search by all items no sort
    		searchLink = "inventory"; 
    	}
    	System.out.println("Search Destination:" + searchLink);
    }
    
    private void processRequest() {
    	boolean bool_search = (!search.equals(""));
    	boolean bool_sort = (sort.equals(HIGHTOLOW) || sort.equals(LOWTOHIGH));
    	boolean bool_dept = !dept.equalsIgnoreCase("Inventory");
    	boolean desc = sort.equals(HIGHTOLOW);
    	System.out.println("search:" + bool_search);
    	System.out.println("sort:" + bool_sort);
    	System.out.println("dept search:" + bool_dept);
    	System.out.println(search);
    	
    	if(!bool_dept && !bool_search) { //Inventory Page
    		if(!bool_sort) { //no sort on all inventory
    			inventory = iHandle.getAll();
    			System.out.println(inventory.toString());
    		} else { //sort on all inventory
    			inventory = iHandle.getAllSortBy("price", desc);
    		}
    	}else if(bool_dept && !bool_search){ //Department page
    		if(!bool_sort) { //no sort on department
    			inventory = iHandle.getByDept(dept);
    		} else { //sort on department
    			inventory = iHandle.getByDeptSortBy(dept, "price", desc);
    		}
    	}else {
    		if(!bool_sort) {
    			System.out.println("SEARCHING FOR: " + search);
    			inventory = iHandle.search(search);
    		}else {
    			inventory = iHandle.searchAndSort(search, "price", desc);
    			System.out.println("SEARCHING FOR: " + search);
    		}
    		dept = "Search: " + search;
    	}
    }
}


