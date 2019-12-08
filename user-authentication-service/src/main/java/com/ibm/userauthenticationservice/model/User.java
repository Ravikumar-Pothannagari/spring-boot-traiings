/**
 * 
 */
package com.ibm.userauthenticationservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author RavikumarPothannagar
 *
 */

@Entity
@ApiModel(description="User details with token")
public class User {
	
	@Id
	@GeneratedValue
	private Long userid;
	
	@ApiModelProperty(notes="User name")
	String username;
	
	@ApiModelProperty(notes="User password")
	String password;
	
	@ApiModelProperty(notes="Generated User token")
	String usertoken;

	
	public User() {
	
	}
	
	/**
	 * @param userid
	 * @param username
	 * @param password
	 * @param usertoken
	 */
	public User(Long userid, String username, String password, String usertoken) {
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.usertoken = usertoken;
	}

	/**
	 * @return the userid
	 */
	public Long getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(Long userid) {
		this.userid = userid;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the usertoken
	 */
	public String getUsertoken() {
		return usertoken;
	}

	/**
	 * @param usertoken the usertoken to set
	 */
	public void setUsertoken(String usertoken) {
		this.usertoken = usertoken;
	}
	
}
