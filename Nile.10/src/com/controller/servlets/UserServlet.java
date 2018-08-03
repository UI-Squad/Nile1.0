package com.controller.servlets;
/**
 * @author Shane Bogard
 */

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.controller.casts.ConnectorCast;
import com.controller.casts.CustomerCast;
import com.controller.fetcher.Connector;
import application.model.Customer;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session;
	private Connector connector;
	private Customer user;
	
    public void doGet(HttpServletRequest request, HttpServletResponse response){  
    	doProcess(request, response);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response){ 
    	doProcess(request, response);
    }
    
    public void doProcess(HttpServletRequest request, HttpServletResponse response) {
    	session = request.getSession(true);
    	setConnector(session);
    	setUser(session);
    	try {
    		if(request.getParameter("page").equals("cart")) { //cart page
    			request.getRequestDispatcher("cart.jsp").forward(request, response);
    		}
    	}catch(Exception e) {
    		System.err.println(this.getClass().getName() + ":" + e.getMessage());
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
    
    private void setUser(HttpSession session) {
    	String guestEmail = "Guest";
		String guestId = "Guest";
    	if(session.isNew()) { //create new customer
    		user = new Customer(guestEmail, guestId);
    		System.out.println("NEW USER CREATED");
    		session.setAttribute("user", user);
    	}else { //customer already exists
    		Customer oldUser = new CustomerCast().convert(session.getAttribute("user"));
    		if(oldUser != null) {
    			System.out.println("USER LOCATED");
    			user = oldUser;
    		}else {
    			System.out.println("USER NOT FOUND");
    			user = new Customer(guestEmail, guestId);
    			session.setAttribute("user", user);
    		}
    		System.out.println("USERID:" + user.getId());
    	}
    }

}
