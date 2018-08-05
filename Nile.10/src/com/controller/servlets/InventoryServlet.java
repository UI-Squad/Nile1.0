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
	private ItemListCast itemCast;
	private ArrayList<Item> inventory;
	private String dept;
	private String search;
	private String sort;
       
    public InventoryServlet() {
        super();
        inventory = new ArrayList<Item>();
        itemCast = new ItemListCast();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response){  
    	doProcess(request, response);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response){ 
    	doProcess(request, response);
    }
    
    public void doProcess(HttpServletRequest request, HttpServletResponse response) {
    	session = request.getSession(true);
    	setConnector(session);
      	iHandle = new InventoryHandler(connector);
    	dept = (String)request.getParameter("dept");
    	search = (String)request.getParameter("value");
    	sort = ((String)request.getParameter("sort") != null) 
    				? (String)request.getParameter("sort") :
    				"";	
    	try {
    		switch(sort) { //sorting
    			case LOWTOHIGH: //sort low to high
    				if(dept != null) {
    					inventory = new InventoryHandler(connector).getByDeptSortBy(dept.toLowerCase(), "price", false);
    				}else {
    					inventory = new InventoryHandler(connector).getAllSortBy("price", false);
    					dept = "Inventory";
    				}
    				break;
    			case HIGHTOLOW: //sort high to low 
    				if(dept != null) {
    					inventory = new InventoryHandler(connector).getByDeptSortBy(dept.toLowerCase(), "price", true);
    				}else {
    					inventory = new InventoryHandler(connector).getAllSortBy("price", true);
    					dept = "Inventory";
    				}
    				break;
    			default: //no sorting
    				if(dept != null) {
    					inventory = new InventoryHandler(connector).getByDept(dept.toLowerCase());
    				}else {
    					inventory = new InventoryHandler(connector).getAll();
    					dept = "Inventory";
    				}
    				break;
    		}
     		
    		/**if(request.getParameter("dept") == null){ //inventory page
    			request.setAttribute("dept", "Inventory");
            	String sort = ((String)request.getParameter("sort") != null) 
            			? (String)request.getParameter("sort") :
            				""; //sorting value
            	String search = (String)request.getParameter("value");
            	//sorting and searching
            	switch(sort) {
            		case LOWTOHIGH:
            			if(search != null) {
            				inventory = new InventoryHandler(connector).searchAndSort(search, "price", false);
            			}else {
            				inventory = new InventoryHandler(connector).getAllSortBy("price", false);
            			}
            			break;
            		case HIGHTOLOW:
                		if(search != null) {
                			inventory = new InventoryHandler(connector).searchAndSort(search, "price", true);
                		}else {
                			inventory = new InventoryHandler(connector).getAllSortBy("price", true);
                		}	
            			break;
            		default:
                		if(search != null) {
                			inventory = new InventoryHandler(connector).search(search);
                		}else {
                			inventory = new InventoryHandler(connector).getAll();
                		}	
            	}
    		}else { //by dept
    			
    		}
            
            	/**if(sort.equals(LOWTOHIGH)) {
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
            	}*/
    		/**}else { //categories page
    			String dept = (String)request.getParameter("dept");
    			request.setAttribute("dept", request.getParameter("dept"));
    			inventory = new InventoryHandler(connector).getByDept(dept.toLowerCase());
    		}*/
    		request.setAttribute("dept", dept);
        	request.setAttribute("inventory", inventory);
         	session.setAttribute("searchValue", search);
        	request.getRequestDispatcher("inventory.jsp").forward(request, response);
    	}catch(Exception e) {
    		System.err.println(this.getClass().getName() + ":" + e.getMessage());
    	}finally {
    		//connector.closeConnection();
    	}
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
}


