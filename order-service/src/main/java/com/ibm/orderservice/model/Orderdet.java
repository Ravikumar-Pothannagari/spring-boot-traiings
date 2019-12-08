/**
 * 
 */
package com.ibm.orderservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author RavikumarPothannagar
 *
 */
@Entity
public class Orderdet {
	

	
	@Id
	@GeneratedValue
	private Long orderid;
	
	@ApiModelProperty(notes="User name")
	String username;
	
	@ApiModelProperty(notes="Delivery address")
	String address;
	
	@ApiModelProperty(notes="Item name")
	String item;
	
	@ApiModelProperty(notes="bill amount")
	int amount;

	@ApiModelProperty(notes="Transaction token")
	String transtoken;
	
	@ApiModelProperty(notes="User token")
	String usertoken;
	
	public Orderdet() {	
	}


	
	public Orderdet(Long orderid,  String username,String address, String item, int amount, String transtoken, String usertoken) {
		super();
		this.orderid = orderid;
		this.username=username;
		this.address = address;
		this.item = item;
		this.amount = amount;
		this.transtoken = transtoken;
		this.usertoken = usertoken;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getTranstoken() {
		return transtoken;
	}

	public void setTranstoken(String transtoken) {
		this.transtoken = transtoken;
	}

	
	public String getUsertoken() {
		return usertoken;
	}

	public void setUsertoken(String usertoken) {
		this.usertoken = usertoken;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Long getOrderid() {
		return orderid;
	}

	public void setOrderid(Long orderid) {
		this.orderid = orderid;
	}

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

}
