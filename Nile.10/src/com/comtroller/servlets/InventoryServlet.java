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
       
    public InventoryServlet() {
        super();

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    	      throws ServletException, IOException {  
    	/*HttpSession session = request.getSession(true);
    	Controller controller;
    	if(session.isNew()) {
    		controller = new Controller();
    		session.setAttribute("controller", controller);
    	}else {
    	  	controller = (Controller)session.getAttribute("controller");
    	}*/
    	//ArrayList<Item> inventory = controller.inventoryHandle().getAll();
    	Connector connector = new Connector();
    	ArrayList<Item> inventory = new Controller(connector).inventoryHandle().getAll();
    	request.setAttribute("inventory", inventory);
    	connector.closeConnection();
    	request.getRequestDispatcher("inventory.jsp").forward(request, response);
    }
}


