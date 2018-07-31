package com.comtroller.servlets;
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
import com.controller.Controller;
import com.controller.fetcher.Connector;

import application.model.Item;

@WebServlet("/inventory")
public class InventoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String LOWTOHIGH = "1";
	private static final String HIGHTOLOW = "2";
       
    public InventoryServlet() {
        super();

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    	      throws ServletException, IOException {  
    	ArrayList<Item> inventory = new ArrayList<Item>(); //arrayList for JSP page
    	Connector connector = new Connector(); //connector to database
    	String sort = ((String)request.getParameter("value") != null) 
    			? (String)request.getParameter("value") :
    				""; //sorting value
    	//sorting
    	if(sort.equals(LOWTOHIGH)) {
    		inventory = new Controller(connector).inventoryHandle().getAllSortBy("price", false);
    	}else if(sort.equals(HIGHTOLOW)) {
    		inventory = new Controller(connector).inventoryHandle().getAllSortBy("price", true);
    	}else {
    		inventory = new Controller(connector).inventoryHandle().getAll();
    	}
    	request.setAttribute("inventory", inventory);
    	connector.closeConnection();
    	request.getRequestDispatcher("inventory.jsp").forward(request, response);
    }
}


