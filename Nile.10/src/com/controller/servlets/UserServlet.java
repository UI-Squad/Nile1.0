package com.controller.servlets;
/**
 * @author Shane Bogard
 */

import java.io.IOException;
import java.io.PrintWriter;
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
	private PrintWriter write;
	private Boolean logged = false;
	private Object obj = null;
	private String email;
	private String password;
	
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{  
    	doProcess(request, response);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{ 
    	doProcess(request, response);
    }
    
    public void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	session = request.getSession(true);
    	setConnector(session);
    	control = new Controller(connector);
    	String page = (String)request.getParameter("page");
    	email = null;
    	password = null;
    	try {
    		switch(page) {
    		case "login":
    			login(request, response);
    			break;
    		case "register":
    			register(request, response);
    			break;
    		case "cart":	//cart page
    			displayCart(request, response);
    			break;
    		case "additem": //add item to cart
    			addItem(request, response);
    			break;
    		case "removeitem":
    			removeItem(request, response);
    			break;
    		case "logout": //logout
    			logout(request, response);
    			break;
    		default:
    			System.err.println("USER DISPATCHER ERROR");
    			break;
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
    
    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		write = response.getWriter();
		email = request.getParameter("email");
		password = request.getParameter("password");
		if(email != null && password != null) { //existing user
			user = control.customerHandle().getByEmail(request.getParameter("email"),
					request.getParameter("password"));
			if(user == null) { //login failed
				write.println("<script type=\"text/javascript\">");  
				write.println("alert('Invalid email/password. Please try again.');");  
				write.println("window.location.replace(\"login.jsp\");");
				write.println("</script>"); 
			}
			if(user.getCart().getSize() > 0) {
				control.cartHandle().returnCart(user.getCart().getCartId());	
			}
		}else { //first time login
			setUser(session);
		}
		System.out.println("LOGIN " + user.getId() + " CONFIRMED");	
		session.setAttribute("user", user);
		logged = true;
		session.setAttribute("logged", logged);
		session.setAttribute("cart", user.getCart());
		request.getRequestDispatcher("Website.jsp").forward(request, response);
    }
    
    
    
    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		password = request.getParameter("psw");
		String repassword = request.getParameter("psw-repeat"); 
		verifyPw(password, repassword);
		String firstNm =request.getParameter("FirstName");
		String lastNm = request.getParameter("LastName");
		email = request.getParameter("email");
		System.out.println("email" + email);
		System.out.println("password" + password);
		System.out.println("firstNm" + firstNm);
		System.out.println("lastNm" + lastNm);
		control.customerHandle().add(email, repassword, firstNm, lastNm);
		System.out.println("ADDED TO DATABASE");
		user = control.customerHandle().getByEmail(email, repassword);
		System.out.println(user.toString());
		session.setAttribute("user", user);
		session.setAttribute("cart", user.getCart());	
		request.getRequestDispatcher("user?page=login").forward(request, response);
    }
    
    private void addItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			setUser(session);
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
				}
			}
			session.setAttribute("cart", user.getCart());
			request.getRequestDispatcher("cart.jsp").forward(request, response);
    }
    
    private void removeItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setUser(session);
		//get itemId and Quantity
		String itemId = request.getParameter("itemID");
		System.out.println("REMOVING: " + itemId);
		int qty = user.getCart().getItem(itemId).getQuantity();
		System.out.println("QUANTITY: " + qty);
		//remove from cart and add back into inventory
		control.cartHandle().removeItem(user.getCart().getCartId(), itemId);
		user.getCart().removeItem(itemId);
		control.inventoryHandle().addQuantity(itemId, qty);
		//forward back to cart page
		System.out.println("ITEM: " + itemId + " REMOVE FROM CART");
		session.setAttribute("cart", user.getCart());
		request.getRequestDispatcher("cart.jsp").forward(request, response);
    }
    
    private void displayCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setUser(session);
		session.setAttribute("cart", user.getCart());
		request.getRequestDispatcher("cart.jsp").forward(request, response);
    }
    
    private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logged = false;
		connector.closeConnection();
		session.invalidate();
		System.out.println("USER " + user.getId() + " LOGGED OUT");
		response.sendRedirect("Website.jsp");
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
	
	private void verifyPw(String password, String repassword)
	{
		if(password == null || repassword == null || !password.equals(repassword)) {//passwords do not match 
			write.println("<script type=\"text/javascript\">");  
			write.println("alert('Passwords do not match. Please try again.');");  
			write.println("window.location.replace(\"register.jsp\");");
			write.println("</script>"); 
		}
	}
}
