/**
 * 
 */
package com.ibm.userauthenticationservice.model;

/**
 * @author RavikumarPothannagar
 *
 */
public class ResponseUser {
	
	User user;
	String status;
	
	public ResponseUser() {
		
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
