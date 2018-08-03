package com.controller.servlets;
/**
 * @author Shane Bogard
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import com.controller.Controller;
import com.controller.casts.ConnectorCast;
import com.controller.casts.CustomerCast;
import com.controller.fetcher.Connector;
import application.model.Customer;
import application.model.Item;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session;
	private Connector connector;
	private Customer user;
	private Controller control;
	
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
    	control = new Controller(connector);
    	try {
    		if(request.getParameter("page").equals("cart")) { //cart page
    			request.getRequestDispatcher("cart.jsp").forward(request, response);
    		}else if(request.getParameter("page").equals("additem")) { //add item to cart
    			String itemId = request.getParameter("itemID");
    			int qty = Integer.parseInt(request.getParameter("numberOfItem"));
    			Item item = control.inventoryHandle().getItemById(itemId);
    			if(item != null) { //null check
    				if(item.getQuantity() >= qty) { //quantity check
    					//add item to user's cart
    					item.setQuantity(qty);
    					user.getCart().addToCart(item);
    					control.inventoryHandle().subtractQuantity(itemId, qty);
    	    			//add cart item to DB
    	    			control.cartHandle().addItem(user.getCart().getCartId(), 
    	    					user.getId(), itemId, qty);
    	    			System.out.println("ITEM: " + item.getItemName() + " ADDED TO CART");
    	    			request.getRequestDispatcher("cart.jsp").forward(request, response);
    				}
    			}
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
		String guestCartId = generateId(session.getId());
    	if(session.isNew()) { //create new customer
    		user = new Customer(guestEmail, guestId, guestCartId);
    		System.out.println("NEW USER CREATED");
    		session.setAttribute("user", user);
    	}else { //customer already exists
    		Customer oldUser = new CustomerCast().convert(session.getAttribute("user"));
    		if(oldUser != null) {
    			System.out.println("USER LOCATED");
    			user = oldUser;
    		}else {
    			System.out.println("USER NOT FOUND");
    			user = new Customer(guestEmail, guestId, guestCartId);
    			session.setAttribute("user", user);
    		}
    	}
		System.out.println("USERID:" + user.getId());
		System.out.println("CARTID:" + user.getCart().getCartId());
    }
    
	/**
	 * 
	 * @param str
	 * @return 
	 */
	private String generateId(String str) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	    md.update(str.getBytes());
	    byte[] digest = md.digest();
	    String myHash = DatatypeConverter.printHexBinary(digest).toLowerCase();	
	    return myHash.substring(myHash.length()-6, myHash.length());
	}

}
