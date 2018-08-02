package com.controller.servlets;
/**
 * Serves inventory data to inventory jsp.
 * @author Shane Bogard
 */

import java.io.IOException;
import java.util.ArrayList;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.controller.casts.ConnectorCast;
import com.controller.fetcher.Connector;
import com.controller.handler.InventoryHandler;

import application.model.Item;

@WebServlet("/inventory")
public class InventoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LOWTOHIGH = "1";
	private static final String HIGHTOLOW = "2";
	private HttpSession session;
	private Connector connector;
	private ArrayList<Item> inventory;
       
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
    
    public void doProcess(HttpServletRequest request, HttpServletResponse response) {
    	inventory.clear();
    	session = request.getSession(true);
    	setConnector(session);
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
    		//connector.closeConnection();
    	}
    }   
   
    private void setConnector(HttpSession session) {
    	Connector oldConnector = new ConnectorCast().convert(session.getAttribute("connector"));
    	if(session.isNew() &&  (connector == null || oldConnector == null || 
    			oldConnector.isClosed() || connector.isClosed())) {
    		System.out.println("NEW CONNECTION");
    		connector = new Connector();
    		session.setAttribute("connector", connector);
    	}
    }
}


