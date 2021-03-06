package com.tags;
/**
 * @author Shane Bogard
 */

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class CategoriesTag extends SimpleTagSupport{
	private String category;
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public void doTag() throws JspException, IOException{
		JspWriter out = getJspContext().getOut();
		if(category == null) category = "";
		String electronics = (!category.equals("Electronics")) ?
				"<a href=\"inventory?dept=Electronics\">Electronics</a>" :
				"<a href=\"inventory?dept=Electronics\" class = \"active\">Electronics</a>";
		String clothing = (!category.equals("Clothing")) ?
				"<a href=\"inventory?dept=Clothing\">Clothing</a>" :
				"<a href=\"inventory?dept=Clothing\" class = \"active\">Clothing</a>";
		String books = (!category.equals("Books")) ?
				"<a href=\"inventory?dept=Books\">Books</a>" :
				"<a href=\"inventory?dept=Books\" class = \"active\">Books</a>";
		String auto = (!category.equals("Auto")) ?
				"<a href=\"inventory?dept=Auto\">Automotive</a>" :
				"<a href=\"inventory?dept=Auto\" class = \"active\">Automotive</a>";
		String home = (!category.equals("Home")) ?
				"<a href=\"inventory?dept=Home\">Home</a>" :
				"<a href=\"inventory?dept=Home\" class = \"active\">Home</a>";
		String viewAll = (!category.equals("viewAll"))  ? "<a href=\"viewAll.jsp\">View All</a>" :
				"<a href=\"viewAllPage.jsp\" class=\"active\">View All</a>";
		
		out.println(electronics);
		out.println(clothing);
		out.println(books);
		out.println(auto);
		out.println(home);
		out.println(viewAll);	
	}
}
