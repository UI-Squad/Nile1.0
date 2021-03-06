package com.controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class productDetailServlet
 */
@WebServlet("/productdetails")
public class ProductDetailServlet extends HttpServlet {
	private static final long serialVersionUID = -2499835352572887548L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public ProductDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String itemName = request.getParameter("itemName");
		String itemDescription = request.getParameter("itemDescription");
		String itemPrice = request.getParameter("itemPrice");
		String itemID = request.getParameter("itemID");
		
		request.setAttribute("itemName", itemName);
		request.setAttribute("itemDescription", itemDescription);
		request.setAttribute("itemPrice", itemPrice);
		request.setAttribute("itemID", itemID);
		request.getRequestDispatcher("product.jsp").forward(request, response);
	}

}
