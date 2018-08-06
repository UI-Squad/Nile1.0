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
	private static final long serialVersionUID = 1L;
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
    	//build search link
    	buildSearchLink();
    	//process request
    	processRequest();
    	
    		/*switch(sort) { //sorting
    			case LOWTOHIGH: //sort low to high
    				if(dept != null) { //by dept
    					if(!search.equals("Search")) { //search by
    						inventory = iHandle.searchByDeptAndSort(dept, search, "price", false);
    					}else { //no search
    						inventory = iHandle.getByDeptSortBy(dept.toLowerCase(), "price", false);
    					}			
    				}else {  //by all items
    					if(!search.equals("Search")){ //search by
    						inventory = iHandle.searchAndSort(search, "price", false);
    					}else { //no search
    						inventory = iHandle.getAllSortBy("price", false);
        					dept = "Inventory";
    					}
    				}
    				break;
    			case HIGHTOLOW: //sort high to low 
    				if(dept != null) {  //by dept
    					if(!search.equals("Search")){ //search by
    						inventory = iHandle.searchByDeptAndSort(dept, search, "price", true);
    					}else {//no search
    						inventory = iHandle.getByDeptSortBy(dept.toLowerCase(), "price", true);
    					}	
    				}else {  //by all items
    					if(!search.equals("Search")){ //search by
    						inventory = iHandle.searchAndSort(search, "price", true);
    					}else { //no search
    						inventory = iHandle.getAllSortBy("price", true);
        					dept = "Inventory";
    					}
    				}
    				break;
    			default: //no sorting
    				if(dept != null) {  //by dept
    					if(!search.equals("Search")) { //search by
    						inventory = iHandle.searchByDept(dept, search);
    					}else { //no search
    						inventory = iHandle.getAll();
        					dept = "Inventory";
    					}
    					inventory = iHandle.getByDept(dept.toLowerCase());
    				}else {  //by all items
    					if(!search.equals("Search")) {
    						inventory = iHandle.search(search);
    					}else { //no search
    						inventory = iHandle.getAll();
        					dept = "Inventory";
    					}
    				}
    				break;
    		} //end processing
*/    		
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
    	searchText = (String)request.getParameter("value"); //determine search parameters
    	if (searchText != null) {
    		search = searchText;
    	}
    	else {
    		search = "";
    		searchText = "Search";
    	}
    	System.out.println("SEARCHTEXT: " + searchText + " "); 
    	System.out.println("SEARCH: " + search + " "); 
    	sort = ((String)request.getParameter("sort") != null)  //determine sort parameters
    				? (String)request.getParameter("sort") : "";
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
    	System.out.println(bool_search);
    	System.out.println(bool_sort);
    	System.out.println(bool_dept);
    	System.out.println(search);
    	
    	if(!bool_dept) { //Inventory Page
    		if(!bool_sort) { //no sort on all inventory
    			if(!bool_search) { //no search on non sorted inventory
    				inventory = iHandle.getAll();
    				System.out.println(inventory.toString());
    			}else {  //search on non sorted inventory
    				inventory = iHandle.search(search);
    			}
    		} else { //sort on all inventory
    			if(!bool_search) { //no search on sorted inventory
    				inventory = iHandle.getAllSortBy("price", desc);
    			}else {  //search on sorted inventory
    				inventory = iHandle.searchAndSort(search, "price", desc);
    			}
    		}
    	}else { //Department page
    		if(!bool_sort) { //no sort on department
    			if(!bool_search) { //no search on non sorted department
    				inventory = iHandle.getByDept(dept);
    			}else {  //search on non sorted department
    				inventory = iHandle.searchByDept(dept, search);
    			}
    		} else { //sort on department
    			if(!bool_search) { //no search on sorted department
    				inventory = iHandle.getByDeptSortBy(dept, "price", desc);
    			}else {  //search on sorted department
    				inventory = iHandle.searchByDeptAndSort(dept, search, "price", desc);
    			}
    		}
    	}
    }
}


