/**
 * 
 */
package com.ibm.orderservice.model;

import io.swagger.annotations.ApiModelProperty;
/**
 * @author RavikumarPothannagar
 *
 */
public class RequestOrder {


	
	@ApiModelProperty(notes="User name")
	String username;

	@ApiModelProperty(notes="Delivery address")
	String address;
	
	@ApiModelProperty(notes="Item name")
	String item;
	
	@ApiModelProperty(notes="bill amount")
	int amount;
 

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
