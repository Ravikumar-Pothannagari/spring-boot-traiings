/**
 * 
 */
package com.ibm.currencyconvertor.exception;

/**
 * @author RavikumarPothannagar
 *
 */
public class ClientServiceException extends Exception {

	private static final long serialVersionUID = -2448453385537644000L;
	
	public ClientServiceException(String message) {
		super(message);
	}
	
	public ClientServiceException(String message, Throwable t) {
		super(message, t);
	}
}
