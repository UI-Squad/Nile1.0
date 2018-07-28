package com.controller.sorting;
/**
 * Enumerated type specifying the rows available to order the SQL
 * queries by.
 * @author Shane Bogard
 *
 */

public enum Columns {
	CARTID("cartId"),
	CUSTID("custID"),
	ITEMID("itemID"),
	QTY("quantity"),
	EMAIL("email"),
	FIRSTNM("firstNm"),
	MIDDLENM("middleNm"),
	LASTNM("lastNm"),
	ADDR("address"),
	PHNUM("phoneNum"),
	CURCART("curCart"),
	ITEMNM("name"),
	DEPT("dept"),
	PRICE("price"),
	INSTOCK("inStock");
	
	private String order;
	Columns(String order){
		this.order = order;
	}
	
	public String toString() {
		return order;
	}
	
	
}
