/**
 * 
 */
package com.ibm.orderservice.model;

/**
 * @author RavikumarPothannagar
 *
 */
public class ResponseOrder {
	

	private Orderdet order;
	private String Status;
	private String StatusMsg;
	

	public ResponseOrder() {
		
	}
	
	
	public ResponseOrder(Orderdet order, String status) {
		super();
		this.order = order;
		Status = status;
	}


	public Orderdet getOrder() {
		return order;
	}
	public void setOrder(Orderdet order) {
		this.order = order;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getStatusMsg() {
		return StatusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		StatusMsg = statusMsg;
	}

}
