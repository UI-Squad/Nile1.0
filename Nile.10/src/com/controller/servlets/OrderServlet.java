package com.controller.servlets;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import com.controller.Controller;
import com.controller.casts.CartCast;
import com.controller.casts.ConnectorCast;
import com.controller.casts.CustomerCast;
import com.controller.fetcher.Connector;
import application.model.Cart;
import application.model.Customer;
import application.model.Order;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = -4761109274259559078L;
	private Connector connector;
	private Controller control;
	private HttpSession session;
	private Order order;
	private Customer user;
	
	public OrderServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response) {
    	session = request.getSession(true);
    	setConnector(session);
    	control = new Controller(connector);
		
		try {
			place(request, response);
		} catch(Exception e) {
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
    
	private void place(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cart cart = new CartCast().convert(session.getAttribute("cart"));
		if(cart != null) {
			System.out.println("PLACING ORDER...");
			control.orderHandle().add(cart.getCartId()); 
			order = control.orderHandle().getByCartId(cart.getCartId());
			System.out.println("CREATING NEW CART FOR CUSTOMER");
			user = new CustomerCast().convert(session.getAttribute("user"));
			user.setCart(new Cart("car" + generateId(session.getId())));
			control.customerHandle().updateCurrentCart(user.getId(), user.getCart().getCartId());
			session.setAttribute("user", user);
			session.setAttribute("cart", user.getCart());
			request.setAttribute("order", order);
		}else {
			System.err.println("ERROR: COULD NOT PLACE ORDER");
		}
		request.getRequestDispatcher("confirm.jsp").forward(request, response);
	}
		
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
	    return myHash.substring(myHash.length()-3, myHash.length());
	}
	
	
}
