<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix = "nt" uri = "WEB-INF/custom.tld"%>
<% 
	String dept = (String)request.getAttribute("dept"); 
	Boolean logged = (Boolean)session.getAttribute("logged"); 
	String signOn = "Sign In";
	String signLink = "login.jsp";
	if(logged != null && logged == true){ 
		signOn = "Sign Out ";;
		signLink = "user?page=logout";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Product Details</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
	
<link rel="stylesheet" href="./css/defaultStyle.css">

<style type="text/css">
* {
	box-sizing: border-box;
}

a{
	color: black;
}

a:link {
    text-decoration: none;
}

a:visited {
    text-decoration: none;
}

a:hover {
    text-decoration: none;
    color:blue;
}

a:active {
    text-decoration: underline;
}

body {
	font-family: Arial;
	padding: 10px;
	background: #f1f1f1;
}

.addToCartButton {
	background-color: #333;
	border: none;
	color: white;
	padding: 15px 32px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
}

.addToCartButton:hover {
	background-color: #1e90ff; 
}

}

/* Create three equal columns that floats next to each other */
.column {
	float: left;
	width: 33.33%;
	padding: 10px;
	height: 425px; /* Should be removed. Only for demonstration */
}

/* Header/Blog Title */
.header {
	padding: 30px;
	text-align: center;
	background: white;
}

.header h1 {
	font-size: 50px;
}

/* Style the top navigation bar */
.topnav, .dropbtn {
	overflow: hidden;
	background-color: #333;
}

/* Style the topnav links */
.topnav a {
	float: left;
	display: block;
	color: #f2f2f2;
	text-align: center;
	padding: 14px 16px;
	text-decoration: none;
	font-size: 17px;
}

/* Change color on hover */
.topnav a:hover, .dropdown:hover {
	background-color: #ddd;
	color: black;
}

/* Style the "active" element to highlight the current page */
.topnav a.active {
	background-color: #4CAF50;
	color: white;
}

/* Styling for the dropdown menu in the navigation bar  */
.dropdown {
	float: left;
	overflow: hidden;
}

.dropdown .dropbtn {
	font-size: 16px;
	border: none;
	outline: none;
	color: white;
	padding: 14px 16px;
	background-color: inherit;
	font-family: inherit;
	margin: 0;
}

.dropdown-content {
	display: none;
	position: absolute;
	background-color: #f9f9f9;
	min-width: 160px;
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
	z-index: 1;
}

.dropdown-content a {
	float: none;
	color: black;
	padding: 12px 16px;
	text-decoration: none;
	display: block;
	text-align: left;
}

.dropdown-content a:hover {
	background-color: #ddd;
}

.dropdown:hover .dropdown-content {
	display: block;
}
/* Styling for dropdown in nav bar ends here*/
.topnav .search-container {
	float: right;
}

/* Style the search box inside the navigation bar */
.topnav input[type=text] {
	padding: 6px;
	border: none;
	margin-top: 8px;
	font-size: 16px;
}

.topnav .search-container button {
	float: right;
	padding: 6px 10px;
	margin-top: 8px;
	margin-right: 16px;
	background: #ddd;
	font-size: 18px;
	border: none;
	cursor: pointer;
}

.topnav .search-container button:hover {
	background: #ccc;
}

/* Clear floats after the columns */
.row:after {
	content: "";
	display: table;
	clear: both;
}



/* Create two unequal columns that floats next to each other */
/* Left column */
.leftcolumn {
	position: -webkit-sticky;
	position: sticky;
	float: left;
	width: 20%;
	background-color: #f1f1f1;
	padding-right: 20px;
	top: 20px;
}

/* Right column */
/* Right column */
.rightcolumn {
	float: right;
	width: 100%;
}

/* Fake image */
.fakeimg {
	background-color: #aaa;
	width: 100%;
	padding: 20px;
}

/* Add a card effect for articles */
.card {
	background-color: white;
	padding: 20px;
	margin-top: 20px;
}

/* Clear floats after the columns */
.row:after {
	content: "";
	display: table;
	clear: both;
}

/* Footer */
.footer {
	padding: 20px;
	text-align: center;
	background: #ddd;
	margin-top: 20px;
}

/* Responsive layout - when the screen is less than 800px wide, make the 
two columns stack on top of each other instead of next to each other */
@media screen and (max-width: 800px) {
	.col-container {
		width: 100%;
		padding: 0;
	}
}

/* Responsive layout - when the screen is less than 400px wide, make the 
navigation links stack on top of each other instead of next to each other */
@media screen and (max-width: 400px) {
	.topnav .search-container {
		float: none;
	}
	.topnav a, .topnav input[type=text], .topnav .search-container button {
		float: none;
		display: block;
		text-align: left;
		width: 100%;
		margin: 0;
		padding: 14px;
	}
	.topnav input[type=text] {
		border: 1px solid #ccc;
	}
}
</style>

<!-- Script that validates there is input in the search bar  -->
<script type="text/javascript">
	function validateForm() {
		var x = document.forms["searchBar"]["value"].value;
		if (x == "") {
			/* Do nothing if search bar is empty  */
			return false;
		}
		return true;
	}
</script>

</head>
<body>

	<div class="header">
		<img src="./Images/siteLogo.jpeg" style="height: 300px;" alt="">
	</div>

	<!-- Navigation bar on the top of the menu  -->
	<div class="topnav">
		<a href="Website.jsp">Home</a>

		<!-- Drop down sub menu for categories in navigation bar  -->
		<div class="dropdown">
			<button class="dropbtn">
				Categories <i class="fa fa-caret-down"></i>
			</button>
			<div class="dropdown-content">
				<nt:Categories category = "<%=dept%>" />
			</div>
		</div>

		<a href="user?page=cart">Cart</a> <a href="inventory">Inventory</a>
		<a href="<%= signLink %> style="float: right"><%= signOn %></a>

		<!-- Search Bar -->
		<div class="search-container">
			<form name="searchBar" action="searchResponseServlet"
				onsubmit="return validateForm()" method="POST">
				<input type="text" name="value" placeholder="Search">
				<button type="submit">
					<i class="fa fa-search"></i>
				</button>
			</form>
		</div>
	</div>

	<!-- Inventory page below nav bar -->
	<div class="row">
		<div class="rightcolumn">
			<div class="card">
			<h4> <a href="javascript:history.back()"> < Back to product listings</a> </h4>
				
				
				<div class="first-column">	
				<br>
				<br>			
				<div><img src="./productImages/
					${itemName}.jpg" align="right" style="width: 240px" alt="product"></div> 
				</div>

				<div class="second-column">
				
				<br>
				<h2>${itemName}</h2>
				
				<hr>
				<br>
				<br>
				<p>${itemDescription}</p>
				<br>
				<br>
				<br>
				<p>$${itemPrice}</p>
				
				<form name="itemForm" action="user?page=additem" method="POST">
				<select name="numberOfItem" style="width: 70px;">
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
									<option value="10">10</option>
				</select>
								

								
				<br>
				<br>
				<br>
					
				<input type="hidden" name="itemID" value="${itemID}">
				</form>
				<a href="#" onclick="document.itemForm.submit();document.body.style.cursor='wait';return true;">
				<button type="button" class="addToCartButton">Add To Cart</button></a>				
					
										
				</div>
				
			</div>
			<!-- end divider for card  -->

		</div>
		<!-- End row divider  -->
	</div>
	
		<div class="footer">
		<h2>
			<a href="contactUsPage.jsp"><font color="000000">Contact Us</font></a>
		</h2>
	</div>
</body>
</html>